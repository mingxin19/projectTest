package models.users;

import models.gallery.*;
import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Table(name="user")
@DiscriminatorValue("artist")
@Entity
public class Artist extends User{

    @OneToMany(mappedBy="artist", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();
    
    @OneToMany(mappedBy="artist", cascade = CascadeType.ALL)
    private List<Tattoo> tattoos = new ArrayList<>();


    public Artist(){
 
    }
    public Artist(String email,String role,String name,String password){
        super(email, role, name, password);
    }

    public void setAppointments(List<Appointment> appointments){
        this.appointments = appointments;
    }
    public List<Appointment> getAppointments(){
        return appointments;
    }

    public List<Tattoo> getTattoos(){
        return tattoos;
    }
    public void setTattoos(List<Tattoo> tattoos){
        this.tattoos = tattoos;
    }

    

    public static Finder<Long, Artist> find = new Finder<Long,Artist>(Artist.class);

    public static final List<Artist> findAll(){
        return Artist.find.all();
    }

    
    public static Artist getArtistById(String id){
        if(id == null){
            return null;
        }else{
            return find.query().where().eq("email",id).findUnique();
        }
    }

    public static Map<String,String> options(){
        LinkedHashMap<String,String> options = new LinkedHashMap<>();
        for (Artist art: Artist.findAll()) {
            options.put(art.getEmail(), art.getName());
        }
        return options;
    }
}