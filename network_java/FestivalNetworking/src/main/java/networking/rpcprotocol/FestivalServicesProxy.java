package networking.rpcprotocol;

import dtos.AngajatDto;
import dtos.BiletDto;
import dtos.ConcertDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.FestivalException;
import services.IFestivalObserver;
import services.IFestivalService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FestivalServicesProxy implements IFestivalService {

    private String host;
    private int port;

    private static Logger logger = LogManager.getLogger(FestivalServicesProxy.class);

    private IFestivalObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public FestivalServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection() throws FestivalException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            logger.error("Error initializing connection "+e);
            logger.error(e.getStackTrace());
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private Response readResponse() throws FestivalException {
        Response response=null;
        try{
            response=qresponses.take();
        } catch (InterruptedException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return response;
    }

    private void sendRequest(Request request)throws FestivalException {
        logger.debug("Sending request {} ",request);
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new FestivalException("Error sending object "+e);
        }

    }

    private void closeConnection() {
        logger.debug("Closing connection");
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }

    }


    @Override
    public void login(String username, String password, IFestivalObserver client) throws FestivalException {
        initializeConnection();
        AngajatDto angajat=new AngajatDto();
        angajat.setNume(username);
        angajat.setPassword(password);
//        logger.info(angajat.getNume()+" , "+angajat.getPassword());
        Request req = new Request.Builder().data(angajat).type(RequestType.LOGIN).build();
        sendRequest(req);
        Response rsp = readResponse();
        if(rsp.type() == ResponseType.OK){
            this.client=client;
            return;
        }
        if (rsp.type()== ResponseType.ERROR){
            String err=rsp.data().toString();
            closeConnection();
            throw new FestivalException(err);
        }
    }

    @Override
    public void logout(AngajatDto angajat) throws FestivalException {
        Request req = new Request.Builder().type(RequestType.LOGOUT).data(angajat).build();
        sendRequest(req);
        Response rsp = readResponse();
        if(rsp.type() == ResponseType.OK){
            closeConnection();
            return;
        }
        if (rsp.type()== ResponseType.ERROR){
            String err=rsp.data().toString();
            throw new FestivalException(err);
        }
    }

    @Override
    public Collection<ConcertDto> getConcerts() throws FestivalException {
        Request req = new Request.Builder().type(RequestType.GET_CONCERTE).build();
        sendRequest(req);
        Response rsp = readResponse();
        if(rsp.type() == ResponseType.ERROR){
            String err=rsp.data().toString();
            throw new FestivalException(err);
        }
        Collection<ConcertDto> concerts=(Collection<ConcertDto>) rsp.data();
        return concerts;
    }

    @Override
    public void sellTickets(ConcertDto concert, int nrBilete) throws FestivalException {
        BiletDto bilet = new BiletDto(concert,nrBilete);
        Request req = new Request.Builder().type(RequestType.VANZARE).data(bilet).build();
        sendRequest(req);
        Response rsp = readResponse();
        if(rsp.type() == ResponseType.ERROR){
            String err=rsp.data().toString();
            throw new FestivalException(err);
        }
    }

    private void handleUpdate(Response response) throws FestivalException {
        if(response.type() == ResponseType.UPDATE_CONCERT)
        {
            logger.info("Received update concert");
            try{
                client.updateBilete((ConcertDto) response.data());
            } catch (FestivalException e){
                logger.error(e);
                logger.error(e.getStackTrace());
            }

        }
    }

    private boolean isUpdate(Response response){
        return response.type()== ResponseType.UPDATE_CONCERT;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    logger.debug("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            logger.error(e);
                            logger.error(e.getStackTrace());
                        }
                    }
                } catch (IOException|ClassNotFoundException e) {
                    logger.error("Reading error "+e);
                }
            }
        }
    }
}
