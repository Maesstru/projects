package persistence.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

class AngajatDBRepoTest {

    @Test
    void findByCredentials() {
        ApplicationContext context=new ClassPathXmlApplicationContext("RepoConfig.xml");

        AngajatDBRepo repo = (AngajatDBRepo) context.getBean("angajatDBRepo");

        assert !repo.findAll().isEmpty();
        assert repo.findByCredentials("admin","admin") != null;
        repo.findAll().forEach(System.out::println);
    }
}