package jdbcworkshop;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author Jakub Venglar at <jakub.venglar@aspectworks.com/>
 */
public class SqliteDataSource implements DataSource{

    private static SqliteDataSource instance;
    private BasicDataSource basicDataSource;

    private SqliteDataSource()  {
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.sqlite.JDBC");
        basicDataSource.setUrl("jdbc:sqlite:test.db");
        basicDataSource.setMinIdle(5);
        basicDataSource.setMaxIdle(20);
        basicDataSource.setDefaultAutoCommit(false);
        List<String> sql = new ArrayList<>();
        
        sql.add("CREATE TABLE IF NOT EXISTS USERS " +
                   "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                   "name TEXT NOT NULL," + 
                   "surname INT NOT NULL," + 
                   "birthYear INT)");
        basicDataSource.setConnectionInitSqls(sql);

    }

    public static SqliteDataSource getInstance() {
        if (instance == null) {
            try {
                instance = new SqliteDataSource();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            } 
        }

        return instance;
    }

    @Override
    public Connection getConnection() {
        try {
            return basicDataSource.getConnection();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
