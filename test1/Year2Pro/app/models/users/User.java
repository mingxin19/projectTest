package models.users;

import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("user")
public class User extends Model {
    @Id
    private String email;
    private String role;
    @Constraints.Required
    private String name;
    @Constraints.Required
    private String password;

    public User() {

    }

    public User(String email, String role, String name, String password) {
        this.email = email;
        this.role = role;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Finder<Long, User> find = new Finder<Long, User>(User.class);

    public static User authenticate(String email, String password) {
        if (email.equalsIgnoreCase("admin@tattoo.com")) {
            return find.query().where().eq("email", email).eq("password", password).findUnique();
        } else if (email.equalsIgnoreCase("andrew@artist.com")) {
            return find.query().where().eq("email", email).eq("password", password).findUnique();
        } else {
            return User.authenticateHashed(email, password);
        }

    }

   

    public static User authenticateHashed(String email, String password) {
        User user = find.query().where().eq("email", email).findUnique();
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    public static User getUserById(String id) {
        if (id == null) {
            return null;
        } else {
            return find.query().where().eq("email", id).findUnique();
        }
    }

    // public static final List<User> findAll(){
    // return User.find.all();
    // }

    public static String passwordHash(String password) {
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        return passwordHash;
    }

    @Override
    public String toString(){
        String s;
        s = String.format("%s ", email) 
        + String.format(" %s ", name);
        

        return s;

    }
}
