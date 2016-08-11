package jdbcworkshop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbcp2.Utils;

/**
 * JDBC implementation of User DAO
 * @author Jakub Venglar at <jakub.venglar@aspectworks.com/>
 */
public final class JdbcUserDao implements UserDao {

    private static JdbcUserDao instance;
    private DataSource dataSource;

    private JdbcUserDao() {
        this.dataSource = SqliteDataSource.getInstance();
    }

    public static JdbcUserDao getInstance() {
        if (instance == null) {
            instance = new JdbcUserDao();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User newUser = new User(user);
        String sql = "INSERT INTO users (id, name, surname, birthYear) VALUES (null, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getBirthYear());
            preparedStatement.executeUpdate();
            connection.commit();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            newUser.setId(resultSet.getInt(1));
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                throw new RuntimeException(ex1);
            }
        } finally {
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
        return newUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(User user) {
        return new TransactionCallback<User>() {

            @Override
            protected User callback(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
                try {
                    String sql = "UPDATE users SET name = ?, surname = ?, birthYear = ? WHERE id = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, user.getName());
                    preparedStatement.setString(2, user.getSurname());
                    preparedStatement.setInt(3, user.getBirthYear());
                    preparedStatement.setInt(4, user.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return new User(user);
            }
        }.runInTransaction();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(User user) {

        new TransactionCallback<Object>() {

            @Override
            protected Object callback(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
                try {
                    String sql = "DELETE FROM users WHERE id = ?";
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, user.getId());
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                return null;
            }
        }.runInTransaction();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT id, name, surname, birthYear FROM users";
        List<User> users = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setBirthYear(resultSet.getInt("birthYear"));
                users.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
        return users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Integer id) {
        Connection connection = dataSource.getConnection();
        String sql = "SELECT id, name, surname, birthYear FROM users WHERE id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setBirthYear(resultSet.getInt("birthYear"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
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

    }

}
