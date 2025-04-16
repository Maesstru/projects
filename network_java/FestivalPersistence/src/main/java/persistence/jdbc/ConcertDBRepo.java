package persistence.jdbc;

import models.Concert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.IConcertRepository;
import persistence.memory.MemoryRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

public class ConcertDBRepo extends MemoryRepo<Long, Concert> implements IConcertRepository {


    private JdbcUtils jdbc;
    private static final Logger logger= LogManager.getLogger();

    public ConcertDBRepo() {}

    public ConcertDBRepo(Properties properties) {
        jdbc = new JdbcUtils(properties);
        load();
    }

    private void load() {
        logger.info("Loading angajati...");
        Connection conn = jdbc.getConnection();
        try(PreparedStatement ps = conn.prepareStatement("select * from concerte"))
        {
            ResultSet rs = ps.executeQuery();
            map.clear();
            while(rs.next()) {
                Long id = rs.getLong("id");
                LocalDateTime data = rs.getTimestamp("data").toLocalDateTime();
                Integer locuri_disponibile = rs.getInt("locuri_disponibile");
                Long locatie = rs.getLong("locatie");
                Long artist = rs.getLong("artist");
                Integer locuri_ocupate = rs.getInt("locuri_ocupate");

                map.put(id,new Concert(id,data,locatie,artist,locuri_disponibile,locuri_ocupate));
            }
        }
        catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public void save(Concert concert) {
        super.save(concert);
    }

    @Override
    public void update(Concert concert) {
        logger.traceEntry();
        Connection connection = jdbc.getConnection();
        try(PreparedStatement ps = connection.prepareStatement("update concerte set locuri_disponibile=?, locuri_ocupate=? where id=?")) {
            ps.setInt(1,concert.getDisponibile());
            ps.setInt(2,concert.getOcupate());
            ps.setLong(3,concert.getId());
            ps.executeUpdate();
        }
        catch(SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        super.update(concert);
    }
}
