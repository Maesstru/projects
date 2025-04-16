package persistence.jdbc;

import models.Artist;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.IArtistRepository;
import persistence.memory.MemoryRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ArtistDBRepo extends MemoryRepo<Long, Artist> implements IArtistRepository {

    private JdbcUtils jdbc;
    private static final Logger logger= LogManager.getLogger();

    public ArtistDBRepo() {}

    public ArtistDBRepo(Properties properties) {
        jdbc = new JdbcUtils(properties);
        load();
    }

    private void load() {
        logger.info("Loading angajati...");
        Connection conn = jdbc.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("select * from artisti"))
        {
            ResultSet rs = ps.executeQuery();
            map.clear();
            while(rs.next()) {
                Long id = rs.getLong("id");
                String nume = rs.getString("nume");
                map.put(id,new Artist(id,nume));
            }
        }
        catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }

    }

}
