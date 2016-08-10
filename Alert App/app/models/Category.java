package models;
 
import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;
 
@Entity
public class Category extends Model implements Comparable<Category> {
 
	@Required
    public String name;
 
    private Category(String name) {
        this.name = name;
    }
 
    public String toString() {
        return name;
    }
 
    public int compareTo(Category otherCategory) {
        return name.compareTo(otherCategory.name);
    }
    
    public static Category findOrCreateByName(String name) {
        Category category = Category.find("byName", name).first();
        if(category == null) {
        	category = new Category(name);
        }
        return category;
    }
    
    
    public static List<Map> getCloud() {
        List<Map> result = Category.find(
            "select new map(t.name as category, count(p.id) as pound) from Player p join p.categories as t group by t.name order by t.name"
        ).fetch();
        return result;
    }
   
}
