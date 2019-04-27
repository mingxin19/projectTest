package models.users;

import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



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

    public void writeReport(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        DateFormat dateFormatReport = new SimpleDateFormat("yyyy_MM_dd");
        String dateString = dateFormatReport.format(date);
        File myfile = new File("public/files", dateString + ".txt");
        int noPaid = 0, noNotPaid = 0;
        double amountPaid = 0.0;

        List<Artist> artList = Artist.findAll();
        List<Customer> custList = Customer.findAll();
        int regCust = 0, unRegCust = 0;
        List<Appointment> appList = Appointment.findAll();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(myfile)))){
            out.write("Report " + dateFormat.format(date) + " by " + getName() + " \n");
            out.write("\nList of Customers\n");
            for(Customer cust : custList){
                if(cust.getPassword().equals("FindAWayToGenerateARandonNumber")){
                    unRegCust++;
                } else {
                    regCust++;
                }
                out.write(cust.toString() + "\n");
            }
            out.write("No. of registered Customers: " +  regCust + "\n");
            out.write("No. of unregistered Customers: " +  unRegCust + "\n");
            out.write("\nList of Artists\n");
            for(Artist art : artList){
                out.write(art.toString() + "\n");
                out.write("Artist " + art.getName() + " has " + art.getAppointments().size() + " appointment(s)\n");
            }
            out.write("\nList of Appointments\n");
            for(Appointment apps : appList){
                out.write(apps.toString()+"\n");
                if(apps.getPaid() == true){
                    noPaid++;
                    amountPaid += 50.0;
                } else {
                    noNotPaid++;
                }
            }
            out.write("No. of appointments: " + appList.size() + "\n");
           
            
            out.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        
    }
}
}