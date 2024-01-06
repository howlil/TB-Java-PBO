package tubes.pbo.database;

import com.zaxxer.hikari.*;

import tubes.pbo.database.data.ConnectionUtil;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolTest {
    

    @Test
    void testHikariCP(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driber");
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/kopimas");
        config.setUsername("root");
        config.setPassword("");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10*60_000);

        
        try {
            HikariDataSource dataSource = new HikariDataSource();
            Connection connection = dataSource.getConnection();
            connection.close();
            dataSource.close();            
        } catch (SQLException exception) {
            fail(exception);
        }
    }

    @Test
    void testUtil() throws SQLException{
        Connection connection = ConnectionUtil.getDataSource().getConnection();
    }
}
