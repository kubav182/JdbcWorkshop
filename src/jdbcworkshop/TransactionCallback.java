package jdbcworkshop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.commons.dbcp2.Utils;

/**
 *
 * @author Jakub Venglar at <jakub.venglar@aspectworks.com/>
 * @param <T>
 */
public abstract class TransactionCallback<T> {

    protected SqliteDataSource dataSource = SqliteDataSource.getInstance();

    protected T runInTransaction() {
        Connection connection = dataSource.getConnection();
        T t = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            t = callback(connection, preparedStatement, resultSet);
            connection.commit();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                throw new RuntimeException(ex1);
            }
        } finally{
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            Utils.closeQuietly(connection);
        }

        return t;
    }

    protected abstract T callback(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet);

}
