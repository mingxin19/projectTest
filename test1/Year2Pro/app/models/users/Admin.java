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
        File myfile = new File("public/files", "report.txt");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        List<Appointment> appList = Appointment.findAll();
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(myfile)))){
            out.write("Report " + dateFormat.format(date) + " \n\n");
            out.write("Appointments\n");
            for(Appointment apps : appList){
                out.write(apps.toString()+"\n");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}