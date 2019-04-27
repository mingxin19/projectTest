package models.users;

import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
@Table(name="user")
@DiscriminatorValue("c")
public class Customer extends User{

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="AID")
    private Appointment appointment;
    
    
    public Customer(){

    }
    public Customer(String email,String role,String name,String phoneNumber,String password){
        super(email, role, name, password);
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }

    public Appointment getAppointment(){
        return appointment;
    }
    public void setAppointment(Appointment appointment){
        this.appointment=appointment;
    }

    public static Finder<Long, Customer> find = new Finder<Long,Customer>(Customer.class);

    public static final List<Customer> findAll(){
        return Customer.find.all();
    }

    
    public static Customer getCustomerById(String id){
        if(id == null){
            return null;
        }else{
            return find.query().where().eq("email",id).findUnique();
        }
    }

    @Override
    public String toString(){
        String s;
        s = super.toString() + String.format(" %s ", phoneNumber);
        

        return s;

    }
}