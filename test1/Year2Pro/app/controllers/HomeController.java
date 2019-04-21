package controllers;

import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import models.*;
import models.users.*;
import models.gallery.*;
import views.html.*;



public class HomeController extends Controller {

    
    private FormFactory formFactory;
    private Environment e;

    @Inject
    public HomeController(FormFactory f,Environment env){
        this.formFactory = f;
        this.e = env;
    }

    public Result index() {
        List<Tattoo> tattooList = Tattoo.findForHome();
        return ok(index.render(User.getUserById(session().get("email")), tattooList, e));
    }

/*    public Result onsale(Long cat){
        List<ItemOnSale> itemList = null;
        List<Category> categoryList = Category.findAll();

        if(cat == 0){
            itemList = ItemOnSale.findAll();
        } else {
            itemList = Category.find.ref(cat).getItems();
        }
        return ok(onsale.render(itemList,categoryList,User.getUserById(session().get("email"))));
        }
    */    


    

    public Result customerProfile(){
        
        List<Appointment> appList = null;
        
        appList= Appointment.findAll();

        return ok(customerProfile.render(appList, User.getUserById(session().get("email"))));
    }

    public Result artistProfile(){
        
        List<Artist> artList = null;
        List<Appointment> appList = null;
        
        artList = Artist.findAll();
        appList= Appointment.findAll();

        return ok(artistProfile.render(artList, appList, User.getUserById(session().get("email"))));
    }

    public Result adminProfile(){
        List<Customer> custList = null;
        List<Artist> artList = null;
        List<Appointment> appList = null;
        custList = Customer.findAll();
        artList = Artist.findAll();
        appList= Appointment.findAll();

        return ok(adminProfile.render(custList, artList, appList, (Admin) User.getUserById(session().get("email"))));
    }

/*    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    public Result addItem(){
        Form<ItemOnSale> itemForm = formFactory.form(ItemOnSale.class);
        return ok(addItem.render(itemForm,User.getUserById(session().get("email"))));
    }
*/
/*    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    @Transactional
    public Result addItemSubmit(){
        Form<ItemOnSale> newItemForm = formFactory.form(ItemOnSale.class).bindFromRequest();

        if(newItemForm.hasErrors()){
            return badRequest(addItem.render(newItemForm,User.getUserById(session().get("email"))));
        }else{
            ItemOnSale newItem = newItemForm.get();
            if(newItem.getId() == null){
                newItem.save();
                flash("success","Item "+newItem.getName()+" was added.");
            }else{
                newItem.update();
                flash("success","Item "+newItem.getName()+" was updated.");
            }
            
            return redirect(controllers.routes.HomeController.onsale(0));
        }
    }
*/
/*    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    @Transactional
    public Result deleteItem(Long id){
        ItemOnSale.find.ref(id).delete();
        flash("success","Item has been deleted.");
        return redirect(controllers.routes.HomeController.onsale(0));
    }
*/
/*    @Security.Authenticated(Secured.class)
    @With(AuthAdmin.class)
    public Result updateItem(Long id){
        ItemOnSale i;
        Form<ItemOnSale> itemForm;

        try{
            i = ItemOnSale.find.byId(id);
            itemForm = formFactory.form(ItemOnSale.class).fill(i);
        }catch(Exception e){
            return badRequest("error"+e.getMessage());
        }
        return ok(addItem.render(itemForm,User.getUserById(session().get("email"))));
        }
*/
    

    @Security.Authenticated(Secured.class)
    @Transactional
    public Result addCustomerSubmit(){
        Form<Customer> newCustForm = formFactory.form(Customer.class).bindFromRequest();
        if (newCustForm.hasErrors()) {
            return badRequest(addCustomer.render(newCustForm,User.getUserById(session().get("email"))));
        } else {
            Customer newCust = newCustForm.get();
    
            if(Customer.getCustomerById(newCust.getEmail())==null){
                newCust.setPassword(Customer.passwordHash(newCust.getPassword()));
                newCust.save();
            }else{
                newCust.update();
            }
            flash("success", "User " + newCust.getName() + " was added/updated.");
            return redirect(controllers.routes.HomeController.adminProfile());             
        }
    
}

@Security.Authenticated(Secured.class)
    @Transactional
    public Result addArtistSubmit(){
        Form<Artist> newArtistForm = formFactory.form(Artist.class).bindFromRequest();
        if (newArtistForm.hasErrors()) {
            return badRequest(addArtist.render(newArtistForm,User.getUserById(session().get("email"))));
        } else {
            Artist newArtist = newArtistForm.get();
    
            if(Artist.getArtistById(newArtist.getEmail())==null){
                newArtist.setPassword(Artist.passwordHash(newArtist.getPassword()));
                newArtist.save();
            }else{
                newArtist.update();
            }
            flash("success", "User " + newArtist.getName() + " was added/updated.");
            return redirect(controllers.routes.HomeController.adminProfile());             
        }
    
}


    


    @Security.Authenticated(Secured.class)
    public Result addCustomer() {
        Form<Customer> custForm = formFactory.form(Customer.class);
        return ok(addCustomer.render(custForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    public Result addArtist() {
        Form<Artist> artForm = formFactory.form(Artist.class);
        return ok(addArtist.render(artForm,User.getUserById(session().get("email"))));
    }

 
    @Security.Authenticated(Secured.class)
    @Transactional
    @With(AuthAdmin.class)
    public Result deleteUser(String email){
        User u = User.getUserById(email);
        u.delete();
        flash("success", "User has been deleted.");
        return redirect(controllers.routes.HomeController.adminProfile());
    }

    @Security.Authenticated(Secured.class)
    public Result updateCustomer(String email){
        Customer c;
        Form<Customer> custForm;
        try{
            c = Customer.getCustomerById(email);
            c.update();
            custForm = formFactory.form(Customer.class).fill(c);
        }catch(Exception e){
            return badRequest("error");
        }
        return ok(addCustomer.render(custForm,User.getUserById(session().get("email"))));
    }

    @Security.Authenticated(Secured.class)
    public Result updateArtist(String email){
        Artist art;
        Form<Artist> artForm;
        try{
            art = Artist.getArtistById(email);
            art.update();
            artForm = formFactory.form(Artist.class).fill(art);
        }catch(Exception e){
            return badRequest("error");
        }
        return ok(addArtist.render(artForm,User.getUserById(session().get("email"))));
    }
    
}
