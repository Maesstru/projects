package persistence.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class LocatieDBRepoTest {
    @Test
    public void createTest()
    {
        ApplicationContext context=new ClassPathXmlApplicationContext("RepoConfig.xml");

        LocatieDBRepo repo=(LocatieDBRepo)context.getBean("locatieDBRepo");

        assertNotNull(repo);
        assertFalse(repo.findAll().isEmpty());
    }
}