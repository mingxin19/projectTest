package models.users;

import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Table(name="user")
@DiscriminatorValue("admin")
@Entity
public class Admin extends User{

    public Admin(){

    }

    public Admin(String email,String name,String role,String password){
        super(email,name,role,password);
    }

    public static final Finder<Long,Admin> find = new Finder<>(Admin.class);
    
    public static final List<Admin> findAll(){
        return Admin.find.all();
    }
}