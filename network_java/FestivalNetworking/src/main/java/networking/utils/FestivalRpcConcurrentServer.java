package networking.utils;

import networking.rpcprotocol.FestivalClientWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.IFestivalService;

import java.net.Socket;

public class FestivalRpcConcurrentServer extends AbstractConcurrentServer {
    private IFestivalService festivalService;
    private static Logger logger = LogManager.getLogger(FestivalRpcConcurrentServer.class);
    public FestivalRpcConcurrentServer(int port, IFestivalService festivalService) {
        super(port);
        this.festivalService = festivalService;
        logger.info("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        // ChatClientRpcWorker worker=new ChatClientRpcWorker(chatServer, client);
        FestivalClientWorker worker=new FestivalClientWorker(festivalService, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        logger.info("Stopping services ...");
    }
}
