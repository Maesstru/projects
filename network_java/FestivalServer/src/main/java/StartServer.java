import networking.utils.AbstractServer;
import networking.utils.FestivalRpcConcurrentServer;
import networking.utils.ServerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IFestivalService;

import java.util.Properties;

public class StartServer {
    private static int defaultPort=55555;
    private static final Logger logger= LogManager.getLogger();

    public static void main(String[] args) {

        ApplicationContext networkContext = new ClassPathXmlApplicationContext("ServiceConfig.xml");

        AbstractServer server = (FestivalRpcConcurrentServer) networkContext.getBean("festivalServer");

        try {
            server.start();
        } catch (ServerException e) {
            logger.error("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                logger.error("Error stopping server "+e.getMessage());
            }
        }
    }
}
