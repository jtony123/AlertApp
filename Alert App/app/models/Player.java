package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;
 
@Entity
public class Player extends Model {

	@Required
	public String playername;
	
	@Required
	public Integer playernumber;
	public Date dateadded;
	
	@Required
	@ManyToOne
	public Client coach;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public Set<Category> categories;
	
	@OneToMany(mappedBy="player", cascade=CascadeType.ALL)
	public List<GPSData> gpsdata;
	
	public Player(String playerName, Integer playerNumber, Client coach){
		this.playername = playerName;
		this.playernumber = playerNumber;
		this.coach = coach;
		this.dateadded = new Date();
		
		this.gpsdata = new ArrayList<GPSData>();
		this.categories = new TreeSet<Category>();
	}
	
	public Player addGPSData(Date date, int t_ttime) {
		
		GPSData newGPSData = new GPSData(this, this.playernumber, date, t_ttime).save();
	    this.gpsdata.add(newGPSData);
	    this.save();
	    return this;
	}
	
	public Player previous() {
	    return Player.find("dateadded < ? order by dateadded desc", dateadded).first();
	}
	 
	public Player next() {
	    return Player.find("dateadded > ? order by dateadded asc", dateadded).first();
	}
	
	
	/**
	 * Categorising a player
	 * @param name
	 * @return
	 */
	public Player categoriseItWith(String name){
		categories.add(Category.findOrCreateByName(name));
		return this;
	}
	
	/**
	 * Get players in a specific category
	 * @param name
	 * @return
	 */
	public static List<Player> findCategorisedWith(String category) {
		
		return Player.find("select distinct p from Player p join p.categories as t where t.name = ?", category).fetch();		
		
	}
	
	/**
	 * Get players belonging to several categories
	 * @param name
	 * @return
	 */
	public static List<Player> findCategorisededWith(String... categories) {
	    return Player.find(
	            "select distinct p from Player p join p.categories as t where t.name in (:categories) group by p.id, p.playername, p.coach having count(t.id) = :size"
	    ).bind("tags", categories).bind("size", categories.length).fetch();
	}
	
	public String toString() {
	    return playername;
	}
	
	
}
