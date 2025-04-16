package persistence.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class ArtistDBRepoTest {
    @Test
    public void initTest(){
        ApplicationContext context=new ClassPathXmlApplicationContext("RepoConfig.xml");

        ArtistDBRepo repo=(ArtistDBRepo)context.getBean("artistDBRepo");
        assert !repo.findAll().isEmpty();
    }
}