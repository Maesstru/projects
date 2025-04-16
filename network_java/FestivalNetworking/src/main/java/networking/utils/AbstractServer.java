package networking.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server=null;
    private static Logger logger = LogManager.getLogger(AbstractServer.class);
    public AbstractServer( int port){
        this.port=port;
    }

    public void start() throws ServerException {
        try{
            logger.info("Starting server at port: "+port);
            server=new ServerSocket(port);
            logger.info("Server started");
            while(true){
                logger.info("Waiting for clients ...");
                Socket client=server.accept();
                logger.info("Client connected ...");
                processRequest(client);
            }
        } catch (IOException e) {
            throw new ServerException("Starting server errror ",e);
        }finally {
            stop();
        }
    }

    protected abstract  void processRequest(Socket client);
    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
