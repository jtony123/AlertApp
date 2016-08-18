package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class PreTrain extends Model {

	@Required
	@ManyToOne
	public Player player;
	
	@Required
	public Date inputDate;
	
	public String comment;
	
	public boolean outOfRange;
	
	@Required
	public boolean isComplete;
	
	public PreTrain (Player player, Date preTrainDate, String comment, boolean outOfRange, boolean isComplete){
		
		this.player = player;	
		this.inputDate = preTrainDate;
		this.comment = comment;
		this.outOfRange = outOfRange;
		this.isComplete = isComplete;
		
	}
	
	
}
