
package jdbcworkshop;

import java.util.List;

/**
 * DAO interface for User object. 
 * 
 * @author Jakub Venglar at <jakub.venglar@aspectworks.com/>
 */
public interface UserDao {
    
    /**
     * Persists user.
     * @param user User object to be persist
     * @return instance of persisted User
     */
    public User save(User user);
    
    /**
     * Updates user.
     * @param user User Object to be updated
     * @return instance of updated User
     */
    public User update(User user);
    
    /**
     * Deletes user.
     * @param user User object to be deleted
     */
    public void delete(User user);
    
    /**
     * Finds all users.
     * @return list of users or empty collection if there is no user
     */
    public List<User> findAll();
    
    /**
     * Finds user by its id.
     * @param id id of user to be found
     * @return instance of User or null if there is no such user
     */
    public User findById(Integer id);
    
}
