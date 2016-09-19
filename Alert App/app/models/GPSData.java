package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;
 
@Entity
public class GPSData extends Model {
	
	@Required
	@ManyToOne
	public Player player;
	
	public Date inputDate;
	
	
	public GPSData (Player player){
		
		this.player = player;
		
	}
	
	public void addGPSData(Date inputDate){
		this.inputDate=inputDate;
		this.save();
	}
	
	public String toString() {
	    return player.playername;
	}

}
