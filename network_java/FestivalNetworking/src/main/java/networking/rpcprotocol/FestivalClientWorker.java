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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class FestivalClientWorker implements Runnable , IFestivalObserver {
    private IFestivalService server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    private static Logger logger = LogManager.getLogger(FestivalClientWorker.class);

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    public FestivalClientWorker(IFestivalService server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException|ClassNotFoundException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error("Error "+e);
        }
    }
    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).getType();
        logger.debug("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            logger.debug("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }

        return response;
    }

    private Response handleLOGOUT(Request request){
        logger.debug("Handle LOGOUT");
        AngajatDto angajat = (AngajatDto)request.getData();
        try{
            server.logout(angajat);
            connected=false;
            return okResponse;
        } catch (FestivalException e) {
            logger.error(e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }

    }

    private Response handleLOGIN(Request request){
        logger.debug("Login request ..."+request.getType());
        AngajatDto udto=(AngajatDto) request.getData();
        try {
            server.login(udto.getNume(), udto.getPassword(), this);
            return okResponse;
        } catch (FestivalException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_CONCERTE(Request request){
        logger.debug("Get concerte request ..."+request.getType());
        try{
            return new Response.Builder().type(ResponseType.GET_CONCERTE).data(server.getConcerts()).build();
        } catch (FestivalException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleVANZARE(Request request){
        logger.debug("Vanzare request ..."+request.getType());
        try{
            BiletDto bilet = (BiletDto) request.getData();
            server.sellTickets(bilet.getConcert(), bilet.getNrLocuri());
            return okResponse;
        } catch (FestivalException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        logger.debug("sending response {}",response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }


    @Override
    public void updateBilete(ConcertDto concert) throws FestivalException {
        logger.debug("updateBilete concert ..."+concert);
        Response resp =new Response.Builder().type(ResponseType.UPDATE_CONCERT).data(concert).build();
        try{
            sendResponse(resp);
        } catch (IOException e){
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }
}
