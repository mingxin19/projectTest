package models.users;

import java.util.*;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlID;


import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="appointment")
public class Appointment extends Model{

    @Id
    private int apmNumber;

    // @Constraints.Required
    @OneToOne
    private Customer customer;

    @Constraints.Required
    @ManyToOne
    private Artist artist;

    @Constraints.Required
    @Temporal(TemporalType.DATE)
    private Date date;

    @Constraints.Required
    private String time;
    
    @Constraints.Required
    private String description;

    @Constraints.Required
    private String size;

    @Constraints.Required
    private String placement;

    @Constraints.Required
    private String colour;

    public Appointment(){

    }

    public Appointment(Date date, String time, Customer customer, Artist artist, String desc, String size, String place, String colour){
        this.date = date;
        this.time = time;
        this.customer = customer;
        this.artist = artist;
        this.description = desc;
        this.size = size;
        this.placement = place;
        this.colour = colour;
    }
    public Appointment(Date date, String time, Artist artist, String desc, String size, String place, String colour){
        this.date = date;
        this.time = time;
        this.artist = artist;
        this.description = desc;
        this.size = size;
        this.placement = place;
        this.colour = colour;
    } 
 
    public int getId(){
        return apmNumber;
    }
    public void setId(int id){
        apmNumber=id;
    }

    public String getDateString(){
        return String.format("%1$td %1$tB %1$tY",date);
    }

    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }

    public Artist getArtist(){
        return artist;
    }
    public void setArtist(Artist artist){
        this.artist = artist;
    }
    public Customer getCustomer(){
        return customer;
    }
    public void setCustomer(Customer cust){
        this.customer = cust;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getSize(){
        return size;
    }
    public void setSize(String size){
        this.size = size;
    }

    public String getPlacement(){
        return placement;
    }
    public void setPlacement(String placement){
        this.placement = placement;
    }

    public String getColour(){
        return colour;
    }
    public void setColour(String colour){
        this.colour = colour;
    }

    public static Finder<Long, Appointment> find = new Finder<Long,Appointment>(Appointment.class);

    public static final List<Appointment> findAll(){
        return Appointment.find.all();
    }

    public static Appointment getAppointmentById(int id){
        if(id == 0){
            return null;
        }else{
            return find.query().where().eq("apm_number",id).findUnique();
        }
    }

    

}