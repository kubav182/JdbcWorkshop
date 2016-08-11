
package jdbcworkshop;

/**
 *
 * @author Jakub Venglar at <jakub.venglar@aspectworks.com/>
 */
public class User {
    
    private Integer id;
    private String name;
    private String surname;
    private Integer birthYear;

    public User() {
    }
    
    public User(User user){
        id = user.id;
        name = user.name;
        surname = user.surname;
        birthYear = user.birthYear;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", birthYear=" + birthYear + '}';
    }
    
}
