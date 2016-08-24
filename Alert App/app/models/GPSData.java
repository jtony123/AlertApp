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
	
	public int dayNumber;
	
	public String comment;
	
	public int tT_Time;
	public int tT_Distance;
	public int tHigh_Intensity_Distance;
	
	public GPSData (Player player){
		
		this.player = player;
		
	}
	
	public void addGPSData(Date inputDate, int dayNumber, int tT_Time, int tT_Distance, int tHigh_Intensity_Distance){
		this.inputDate=inputDate;
		this.dayNumber=dayNumber;
		this.tT_Time=tT_Time;
		this.tT_Distance=tT_Distance;
		this.tHigh_Intensity_Distance=tHigh_Intensity_Distance;
		this.save();
	}
	
	public String toString() {
	    return player.playername;
	}

}
