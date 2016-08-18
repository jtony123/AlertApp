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
	
	public Blob playerPhoto;
	
	@Required
	@ManyToOne
	public Client coach;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public Set<Category> categories;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	public Set<Question> questions;
	
	@OneToMany(mappedBy="player", cascade=CascadeType.ALL)
	public List<GPSData> gpsdata;
	
	@OneToMany(mappedBy="player", cascade=CascadeType.ALL)
	public List<PreTrain> preTrain;
	
	@OneToMany(mappedBy="player", cascade=CascadeType.ALL)
	public List<PostTrain> postTrain;
	
	public Player(String playerName, Integer playerNumber, Blob playerPhoto, Client coach){
		this.playername = playerName;
		this.playernumber = playerNumber;
		if(playerPhoto != null){
			this.playerPhoto=playerPhoto;
		}
		this.playerPhoto=playerPhoto;
		this.coach = coach;
		this.dateadded = new Date();
		
		this.gpsdata = new ArrayList<GPSData>();
		this.preTrain = new ArrayList<PreTrain>();
		this.postTrain = new ArrayList<PostTrain>();
		this.categories = new TreeSet<Category>();
		// all players are automatically categorised in All
		this.categoriseItWith("All");
		
		this.questions = new TreeSet<Question>();
		
	}
	
	public Player addGPSData(Date date, int t_ttime) {
		
		GPSData newGPSData = new GPSData(this, this.playernumber, date, "", t_ttime).save();
	    this.gpsdata.add(newGPSData);
	    this.save();
	    return this;
	}
	
	public Player addPreTrain(Date date, String answer, boolean isComplete) {
		
		PreTrain preTrain = new PreTrain(this, date, "", false, isComplete);
		this.preTrain.add(preTrain);
		this.save();
		return this;
	}
	
	public Player addPostTrain(Date date, String comment, boolean outOfRange, boolean isComplete) {
		
		PostTrain postTrain = new PostTrain(this, date, comment, outOfRange, isComplete);
		this.postTrain.add(postTrain);
		this.save();
		return this;
	}
	
	public Player updatePhoto (Blob photo){
		
		this.playerPhoto=photo;
		this.save();
		return this;
	}
	
	public Player previous() {

		return Player.find("select distinct p from Player p where p.coach=?1 AND p.playernumber < ?2 AND p.categories.size > ?3 order by playernumber desc", this.coach, playernumber, 0).first();
		
	}
	 
	public Player next() {
		
		
	    return Player.find("select distinct p from Player p where p.coach=?1 AND p.playernumber > ?2 AND p.categories.size > ?3 order by playernumber asc", this.coach, playernumber, 0).first();
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
	 * Get players in a specific category
	 * @param name
	 * @return
	 */
	public static List<Player> findClientsPlayersCategorisedWith(Client client, String category) {
		
		return Player.find("select distinct p from Player p join p.categories c where p.coach=?1 AND c.name=?2", client, category).fetch();		
		
	}
	

	
	/**
	 * Get players belonging to several categories
	 * @param name
	 * @return
	 */
	public static List<Player> findCategorisededWith(String... categories) {
	    return Player.find(
	            "select distinct p from Player p join p.categories as t where t.name in (:categories) group by p.id, p.playername, p.coach having count(t.id) = :size"
	    ).bind("categories", categories).bind("size", categories.length).fetch();
	}
	
	public Player questionItWith(String q){
		questions.add(Question.findOrCreateByName(q));
		return this;
	}
	
	public String toString() {
	    return playername;
	}
	
	
}
