
package jdbcworkshop;

import java.sql.Connection;

/**
 *
 * @author Jakub Venglar at <jakub.venglar@aspectworks.com/>
 */
public interface DataSource {
    
    public Connection getConnection();
    
}
