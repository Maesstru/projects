package festival.server;

import dtos.ConcertDto;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.FestivalException;
import services.IFestivalObserver;

import static org.junit.jupiter.api.Assertions.*;

class FestivalServicesImpTest {

    @Test
    void login() {
        ApplicationContext context=new ClassPathXmlApplicationContext("ServiceConfig.xml");

        FestivalServicesImp srv = (FestivalServicesImp) context.getBean("festivalService");

        IFestivalObserver mockObserver = new IFestivalObserver() {

            @Override
            public void updateBilete(ConcertDto concert) throws FestivalException {

            }
        };
        assertNotNull(srv);
        System.out.println(srv);
        srv.login("admin","admin",mockObserver);
        try{
            srv.login("nuexista","admin",mockObserver);
        } catch (Exception e){
            assert true;
        }
    }

    @Test
    void getConcerts() {
    }
}