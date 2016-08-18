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
	
	@Required
	public int playerIdentityNo;
	
	@Required
	public Date inputDate;
	
	public String comment;
	
	@Required
	public int tT_Time;
	
	public GPSData (Player player, int playerId, Date dataDate, String comment, int tttime){
		
		this.player = player;
		this.playerIdentityNo = playerId;
		this.inputDate = dataDate;
		this.comment=comment;
		this.tT_Time = tttime;
		
	}
	
	public String toString() {
	    return player.playername;
	}

}
