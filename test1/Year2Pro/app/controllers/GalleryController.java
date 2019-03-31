package controllers;

import play.mvc.*;

import views.html.*;

import play.api.Environment;
import play.data.*;
import play.db.ebean.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import models.*;
import models.users.*;
import models.gallery.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

import java.io.IOException;
import java.awt.image.*;
import javax.imageio.*;
import org.imgscalr.*;

public class GalleryController extends Controller{
    private FormFactory formFactory;
    private Environment e;
    
    @Inject
    public GalleryController(FormFactory f,Environment env) {
        
        this.formFactory = f;
        this.e = env;
    }

    public Result galleries(Long style){
        List<Tattoo> tattooList = null;
        List<Style> styleList = Style.findAll();

        if(style == 0){
            tattooList = Tattoo.findAll();
        } else {
            tattooList = Style.find.ref(style).getTattoos();
        }

        return ok(galleries.render(tattooList, styleList, User.getUserById(session().get("email")),e));
    }
    @Security.Authenticated(Secured.class)
    public Result deleteTattoo(Long id){
        Tattoo tat = Tattoo.getTattooById(id);
        tat.delete();
        flash("success", "Tattoo has been deleted.");
        return redirect(controllers.routes.GalleryController.galleries(0));
        
    }

    @Security.Authenticated(Secured.class)
    public Result addTattoo(){
        Form<Tattoo> tattooForm = formFactory.form(Tattoo.class);
        return ok(addTattoo.render(tattooForm, User.getUserById(session().get("email"))));
    }
    
    @Security.Authenticated(Secured.class)
    public Result addTattooSubmit(){
        Form<Tattoo> newTattooForm = formFactory.form(Tattoo.class).bindFromRequest();

        if(newTattooForm.hasErrors()){
            return badRequest(addTattoo.render(newTattooForm, User.getUserById(session().get("email"))));
        } else {
            Tattoo newTattoo = newTattooForm.get();

            if(Tattoo.getTattooById(newTattoo.getId()) == null){
                Artist newArtist = Artist.getArtistById(session().get("email"));
                newTattoo.setArtist(newArtist);
                newTattoo.save();
            } else {
                newTattoo.update();
            }

            MultipartFormData<File> data = request().body().asMultipartFormData();
       
            FilePart<File> image = data.getFile("upload");
    
            String saveTattooMessage = saveFileT(newTattoo.getName(), image);
            flash("success", "Tattoo " + newTattoo.getName() + " was added/updated.");
            return redirect(controllers.routes.GalleryController.galleries(0));    
        }
    }

    public String saveFileT(String id, FilePart<File> uploaded) {
        
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
                
                File dir = new File("public/images/tattooImages");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 3) Actually save the file.
                File newFile = new File("public/images/tattooImages/", id + "." + extension);
                if (file.renameTo(newFile)) {
                    try {
                        BufferedImage img = ImageIO.read(newFile); 
                        BufferedImage scaledImg = Scalr.resize(img, 300);
                        
                        if (ImageIO.write(scaledImg, extension, new File("public/images/tattooImages/", id + "display.jpg"))) {
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
    