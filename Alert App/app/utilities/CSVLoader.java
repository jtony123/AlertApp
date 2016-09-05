package utilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import models.GPSData;
import models.Player;
import play.jobs.OnApplicationStart;


public class CSVLoader {

	
	
	
	public void loadFile(String fileName){
		BufferedReader fileReader = null;
		String line ="";
		String[] tokens;
		int lineCounter = 0;
		int tokenCount = 0;
		
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			
			// this first line should contain the attribute labels
			line = fileReader.readLine();
			tokens = line.split(",");
			
//			for ( int i=0; i<tokens.length-1; ++i){
//				System.out.print(tokens[i] + " ");
//			}
//			System.out.println();
			
			tokenCount = tokens.length;
//			for ( int i=0; i<tokens.length-1; ++i){
//				attributeLabels.add(tokens[i]);
//			}
		
			//Read the file line by line

			while ((line = fileReader.readLine()) != null) {

				tokens = line.split(",");

				if (tokens.length > 0 && tokens.length == tokenCount) {

					// second token should be player id
					Player player = Player.find("byPlayernumber", Integer.parseInt(tokens[1])).first();
					//System.out.println("player found = "+player.playername);
					Date inputDate = new Date(tokens[0]);
					//System.out.println("date = "+inputDate);
					int dayNumber = Integer.parseInt(tokens[2]);
					int tT_Time;
					if(tokens[3].equalsIgnoreCase("na")){
						tT_Time = 0;
					} else {
						tT_Time = Integer.parseInt(tokens[3]);
					}
					
					int tT_Distance ;
					if(tokens[4].equalsIgnoreCase("na")){
						tT_Distance  = 0;
					} else {
						tT_Distance  = Integer.parseInt(tokens[4]);
					}
					
					int tHigh_Intensity_Distance ;
					if(tokens[5].equalsIgnoreCase("na")){
						tHigh_Intensity_Distance  = 0;
					} else {
						tHigh_Intensity_Distance  = Integer.parseInt(tokens[5]);
					}
					
					player.addGPSData(inputDate, dayNumber, tT_Time, tT_Distance, tHigh_Intensity_Distance);
					
//					for ( int i=0; i<tokens.length-1; ++i){
//						System.out.print(tokens[i] + " ");
//					}
//					System.out.println();

				}
				++lineCounter;
			}
			
			fileReader.close();
		} catch (IOException e) {
			// TODO return an error to the user
			e.printStackTrace();
		} 
	}
	
}


