package persistence.jdbc;

import models.Locatie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.ILocatieRepository;
import persistence.memory.MemoryRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class LocatieDBRepo extends MemoryRepo<Long,Locatie> implements ILocatieRepository {

    private JdbcUtils jdbc;
    private static final Logger logger= LogManager.getLogger();

    public LocatieDBRepo() {}

    public LocatieDBRepo(Properties properties) {
        jdbc = new JdbcUtils(properties);
        load();
    }

    private void load() {
        logger.info("Loading locatii...");
        Connection conn = jdbc.getConnection();
        map.clear();
        try(PreparedStatement ps = conn.prepareStatement("select * from locatii"))
        {
            try(ResultSet rs = ps.executeQuery())
            {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String oras = rs.getString("oras");
                    String strada = rs.getString("strada");
                    String numar = rs.getString("numar");

                    map.put(id, new Locatie(id,oras, strada, numar));
                }
            } catch (SQLException e) {
                logger.error(e);
            }
        } catch(SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void save(Locatie locatie) {

    }

    @Override
    public void update(Locatie locatie) {

    }
}
