package models.gallery;

import java.util.*;
import javax.persistence.*;
import io.ebean.*;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Style extends Model{

    @Id
    private Long id;
    @Constraints.Required
    private String styleName;

    @OneToMany(mappedBy="style", cascade = CascadeType.ALL)
    private List<Tattoo> tattoos;

    public Style(Long id, String styleName){
        this.id = id;
        this.styleName = styleName;
    }
    public Style(Long id, String styleName, List<Tattoo> tattoos){
        this.id = id;
        this.styleName = styleName;
        this.tattoos = tattoos;
    }

    public void setId(Long id){
        this.id = id;
    }
    public Long getId(){
        return id;
    }
    public void setStyle(String styleName){
        this.styleName = styleName;
    }
    public String getStyle(){
        return styleName;
    }
    public void setTattoos(List<Tattoo> tattoos){
        this.tattoos = tattoos;
    }
    public List<Tattoo> getTattoos(){
        return tattoos;
    }

    public static final Finder<Long, Style> find = new Finder<>(Style.class);

    public static final List<Style> findAll(){
        return Style.find.all();
    }

    public static Map<String,String> options(){
        LinkedHashMap<String,String> options = new LinkedHashMap<>();
        for (Style s: Style.findAll()) {
            options.put(s.getId().toString(), s.getStyle());
        }
        return options;
    }
}