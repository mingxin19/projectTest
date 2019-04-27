package controllers;

import models.payment.*;
import models.*;
import models.users.*;
import models.gallery.*;
import java.util.ArrayList;
import java.util.List;
import views.html.*;
import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;
import javax.inject.Inject;

public class CheckoutController extends Controller {

    private FormFactory formFactory;

    @Inject
    public CheckoutController(FormFactory f){
        this.formFactory=f;
    }

 
    // @Value("pk_test_j9w3VvsuOsTLJIc1z2QKio3p00Sy40IX2N")
    // private String stripePublicKey;
 
    // @RequestMapping("/checkout")
    // public String checkout() {
    //     model.addAttribute("amount", 50 * 100); // in cents
    //     model.addAttribute("stripePublicKey", stripePublicKey);
    //     model.addAttribute("currency", ChargeRequest.Currency.EUR);
    //     return "checkout";
    // }
    //Can't make this work at all, no idea how to handle the result that comes back from Stripe after initial token exchange.

    public Result checkout(int id){
        
        Appointment app;
        Customer cust;
        Form<Appointment> appForm;
        Form<Customer> custForm;
            try{        
            app = Appointment.getAppointmentById(id);
            cust = app.getCustomer();
            cust.getAppointment().setPaid(true);
            app.update();
            cust.update();
            appForm = formFactory.form(Appointment.class).fill(app);
            custForm = formFactory.form(Customer.class).fill(cust);
        }catch(Exception e){
            return badRequest("error");
         }
        

        flash("success","You have paid for your appointment.");
        return redirect(controllers.routes.HomeController.customerProfile());
    }
     
}