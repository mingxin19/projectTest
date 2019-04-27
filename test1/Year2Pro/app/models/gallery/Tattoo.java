package models.gallery;

import models.users.*;
import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;
import java.util.Collections;

@Table(name = "tattoo")
@Entity
public class Tattoo extends Model {

    @Id
    private Long id;

    private String name;
 
    @ManyToOne
    private Style style;

    @ManyToOne
    private Artist artist;

    public Tattoo(Long id){
        this.id = id;
    }
    public Tattoo(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Tattoo(Long id, String name, Style style){
        this.id = id;
        this.name = name;
        this.style = style;
        
    }

    public Tattoo(Long id, String name, Style style, Artist artist){
        this.id = id;
        this.name = name;
        this.style = style;
        this.artist = artist;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Style getStyle(){
        return style;
    }
    public void setStyle(Style style){
        this.style = style;
    }
    public Artist getArtist(){
        return artist;
    }
    public void setArtist(Artist artist){
        this.artist = artist;
    }

    public static final Finder<Long, Tattoo> find = new Finder<>(Tattoo.class);

    public static final List<Tattoo> findAll(){
        return Tattoo.find.all();
    }

    public static final List<Tattoo> findForHome(){
        List<Tattoo> tattooList = new ArrayList<Tattoo>();
        List<Tattoo> tattoos = Tattoo.findAll();
        List<Long> ids = new ArrayList<Long>();
        
        for (Tattoo tattoo : tattoos) {
            Long id = tattoo.getId();
            ids.add(id);
        }
        Collections.shuffle(ids);
        for (int i = 0; i < 3; i++) {
            if(getTattooById(ids.get(i)) != null){
            tattooList.add(getTattooById(ids.get(i)));
        }
        }
     
        return tattooList;
    }

    public static Tattoo getTattooById(Long id){
        if(id == null){
            return null;
        }else{
            return find.query().where().eq("id",id).findUnique();
        }
    }
}