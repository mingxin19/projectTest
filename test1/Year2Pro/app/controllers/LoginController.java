package controllers;

import play.mvc.*;
import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import models.users.*;
import views.html.*;

public class LoginController extends Controller{
    private FormFactory formFactory;

    @Inject
    public LoginController(FormFactory f){
        this.formFactory=f;
    }

    public Result login(){
        Form<Login> loginForm = formFactory.form(Login.class);
        return ok(login.render(loginForm,User.getUserById(session().get("email"))));
    }

    public Result loginSubmit(){
        Form<Login> loginForm = formFactory.form(Login.class).bindFromRequest();

        if(loginForm.hasErrors()){
            return badRequest(login.render(loginForm,User.getUserById(session().get("email"))));
        }else{
            session().clear();
            session("email",loginForm.get().getEmail());
            return redirect(controllers.routes.HomeController.index());
        }
    }

    public Result logout(){
        session().clear();
        flash("success","You have been logged out");
        return redirect(routes.LoginController.login());
    }

    public Result registerUser() {
        Form<Customer> userForm = formFactory.form(Customer.class);
        return ok(registerUser.render(userForm,User.getUserById(session().get("email"))));
    }

    public Result registerUserSubmit() {
    Form<Customer> newUserForm = formFactory.form(Customer.class).bindFromRequest();

    if (newUserForm.hasErrors()) {
        return badRequest(registerUser.render(newUserForm,User.getUserById(session().get("email"))));
    } else {
        Customer newUser = newUserForm.get();

        

        if(User.getUserById(newUser.getEmail())==null){
            newUser.setPassword(User.passwordHash(newUser.getPassword()));//Damn this is ugly but doing it any other way breaks the password matching validation.
            newUser.save();
        }else{
            newUser.setPassword(User.passwordHash(newUser.getPassword()));
            newUser.update();
        }
        flash("success", "User " + newUser.getName() + " was registered.");
        
        return redirect(controllers.routes.LoginController.login()); 
        }
    }
    

}