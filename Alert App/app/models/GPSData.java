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
	public Date dataDate;
	
	@Required
	public int tT_Time;
	
	public GPSData (Player player, int playerId, Date dataDate, int tttime){
		
		this.player = player;
		this.playerIdentityNo = playerId;
		this.dataDate = dataDate;
		this.tT_Time = tttime;
		
	}
	
	public String toString() {
	    return player.playername;
	}

}
