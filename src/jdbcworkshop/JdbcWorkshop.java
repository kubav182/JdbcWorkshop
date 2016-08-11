package jdbcworkshop;

/**
 * @author Jakub Venglar at <jakub.venglar@aspectworks.com/>
 */
public class JdbcWorkshop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SqliteDataSource.getInstance().getConnection();
        UserDao dao = JdbcUserDao.getInstance();
        
        User user = new User();
        user.setName("Jmeno");
        user.setSurname("Prijmeni");
        user.setBirthYear(2005);
        
        User newUser = dao.save(user);
        System.out.println(newUser);
        
        User searchedUser = dao.findById(newUser.getId());
        System.out.println(searchedUser);
        
        System.out.println(dao.findAll().size());
        
        dao.delete(newUser);
        
        System.out.println(dao.findAll().size());
    }

}
