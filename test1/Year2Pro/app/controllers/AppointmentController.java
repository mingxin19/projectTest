package controllers;

import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

import java.io.IOException;
import java.awt.image.*;
import javax.imageio.*;
import org.imgscalr.*;

import models.users.*;
import views.html.*;

public class AppointmentController extends Controller{
    private FormFactory formFactory;

    @Inject
    public AppointmentController(FormFactory f){
        this.formFactory=f;
    }





    public Result custApp(){
        Form<Appointment> appForm = formFactory.form(Appointment.class);
        return ok(custApp.render(appForm,User.getUserById(session().get("email"))));
    }

    public Result custAppSubmit(){
        Form<Appointment> appForm = formFactory.form(Appointment.class).bindFromRequest();
        Appointment tempApp = appForm.get();
        Boolean appBoolean = false;
        List<Appointment> appList = tempApp.getArtist().getAppointments();
        for(Appointment apps : appList){
               if(tempApp.getDate() == apps.getDate()){
                    if(tempApp.getTime().equalsIgnoreCase(apps.getTime())){
                        appBoolean = true;
                    }
                }
            
        }
        if(appForm.hasErrors()){
            flash("error", "Bad Request");
            return badRequest(custApp.render(appForm,User.getUserById(session().get("email"))));
        }else{
            Customer c = Customer.getCustomerById(session().get("email"));
            if(c.getAppointment()!=null){
                flash("error", "Appointment for your email address is booked already.");
                
            }else{
                if(appBoolean == true){
                    flash("error", "This Date and Time for this artist is booked please choose another");
                }else{
                Appointment newApp = appForm.get();
                
                newApp.setCustomer(c);
                newApp.setPaid(false);
                c.setAppointment(newApp);
                newApp.save();
                c.update();
            
                MultipartFormData<File> data = request().body().asMultipartFormData();
                FilePart<File> image = data.getFile("upload");
                String saveImageMessage = saveFile(newApp.getId(), image, newApp.getCustomer().getEmail());

                flash("success", "Appointment for " + newApp.getCustomer().getName() + " is booked.");
            }
        }
            return redirect(controllers.routes.AppointmentController.custApp());
    
            
            
        }
    }





    public Result appointment(){
        Form<Appointment> appForm = formFactory.form(Appointment.class);
        Form<Customer> custForm = formFactory.form(Customer.class);
        return ok(appointment.render(custForm,appForm,User.getUserById(session().get("email"))));
    }

    public Result appointmentSubmit(){
        Form<Appointment> appForm = formFactory.form(Appointment.class).bindFromRequest();
        Form<Customer> custForm = formFactory.form(Customer.class).bindFromRequest();
        Appointment tempApp = appForm.get();
        Boolean appBoolean = false;
        List<Appointment> appList = tempApp.getArtist().getAppointments();
        for(Appointment apps : appList){
               if(tempApp.getDate() == apps.getDate()){
                    if(tempApp.getTime().equalsIgnoreCase(apps.getTime())){
                        appBoolean = true;
                    }
                }
            
        }
        if(appForm.hasErrors()||custForm.hasErrors()){
            flash("error", "Bad Request");
            return badRequest(appointment.render(custForm,appForm,User.getUserById(session().get("email"))));
        }else{
            Customer tempCust = custForm.get();
            if(User.getUserById(tempCust.getEmail()) != (null)){
                flash("error", "This email is registered, login to book appointment.");
            }else{
                if(appBoolean == true){
                    flash("error", "This Date and Time for this artist is booked please choose another");
                }else{

                Appointment newApp = appForm.get();
                tempCust.setPassword(tempCust.getPassword());
                newApp.setCustomer(tempCust);
                newApp.setPaid(false);
                tempCust.save();
                newApp.save();

                MultipartFormData<File> data = request().body().asMultipartFormData();
                FilePart<File> image = data.getFile("upload");
                String saveImageMessage = saveFile(newApp.getId(), image, newApp.getCustomer().getEmail());
            
                flash("success", "Appointment for " + newApp.getCustomer().getName() + " is booked. " + saveImageMessage + " uploaded.");
            }
        }
            return redirect(controllers.routes.AppointmentController.appointment());
    
            
            
        }
    }




    @Security.Authenticated(Secured.class)
    @Transactional
    @With(AuthAdmin.class)
    public Result deleteAppointment(int id){
        Appointment app = Appointment.getAppointmentById(id);
        app.delete();
        flash("success", "Appointment has been deleted.");
        return redirect(controllers.routes.HomeController.adminProfile());
    }

    





    
    public Result updateAppointment(int id){
        Appointment app;
        Customer cust;
        Form<Appointment> appForm;
        Form<Customer> custForm;
            try{
             app = Appointment.getAppointmentById(id);
             cust = app.getCustomer();
             app.update();
             cust.update();
             appForm = formFactory.form(Appointment.class).fill(app);
             custForm = formFactory.form(Customer.class).fill(cust);
         }catch(Exception e){
             return badRequest("error");
         }
         return redirect(controllers.routes.HomeController.customerProfile());
     }




    public String saveFile(int id, FilePart<File> uploaded, String custEmail) {
        
        if (uploaded != null) {
            
            String mimeType = uploaded.getContentType();
            if (mimeType.startsWith("image/")) {
                
                String fileName = uploaded.getFilename();
                
                String extension = "";
                int i = fileName.lastIndexOf('.');
                if (i >= 0) {
                    extension = fileName.substring(i + 1);
                }
                
                File file = uploaded.getFile();
                
                File dir = new File("public/images/appImages");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 3) Actually save the file.
                File newFile = new File("public/images/appImages/", id + "." + extension);
                if (file.renameTo(newFile)) {
                    try {
                        BufferedImage img = ImageIO.read(newFile); 
                        BufferedImage scaledImg = Scalr.resize(img, 50);
                        
                        if (ImageIO.write(scaledImg, extension, new File("public/images/appImages/", id + custEmail + "display.jpg"))) {
                            return "/ file uploaded and Profile created.";
                        } else {
                            return "/ file uploaded but profile creation failed.";
                        }
                    } catch (IOException e) {
                        return "/ file uploaded but profile creation failed.";
                    }
                } else {
                    return "/ file upload failed.";
                }
    
            }
        }
        return "/ no image file.";
    }
}