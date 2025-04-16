package persistence.jdbc;

import models.Angajat;
import persistence.IAngajatRepository;
import persistence.memory.MemoryRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AngajatDBRepo extends MemoryRepo<Long,Angajat> implements IAngajatRepository {

    private JdbcUtils jdbc;
    private static final Logger logger = LogManager.getLogger();

    public AngajatDBRepo() {}

    public AngajatDBRepo(Properties properties) {
        jdbc = new JdbcUtils(properties);
        load();
    }

    private void load() {
        logger.info("Loading angajati...");
        Connection conn = jdbc.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("select * from angajati"))
        {
            ResultSet rs = ps.executeQuery();
            map.clear();
            while(rs.next()) {
                Long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("parola");
                map.put(id,new Angajat(id,username,password));
            }
        }
        catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public void save(Angajat angajat) {

    }

    @Override
    public void update(Angajat angajat) {

    }

    @Override
    public Angajat findByCredentials(String username, String password) {
        return map.values().stream().
                filter(e -> e.getUsername().equals(username) && e.getPassword().equals(password)).
                findFirst().orElse(null);
    }
}
