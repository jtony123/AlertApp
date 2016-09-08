package models;

import java.io.File;
import java.util.*;
import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public class CSVFile extends Model {
	
	
	
	@Required
	public Player player;
	
	
	//public byte[] file;
	
	public String fileName;
	public String contentType;
	
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
//	public byte[] getFile() {
//		return file;
//	}
//	public void setFile(byte[] file) {
//		this.file = file;
//	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	 

}
