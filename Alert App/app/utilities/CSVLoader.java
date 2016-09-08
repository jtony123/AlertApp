package utilities;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import models.GPSData;
import models.Player;
import play.jobs.OnApplicationStart;


public class CSVLoader {

	String header;
	List<String> datapoints = new ArrayList<String>();
	
	List<Instance> instances = new ArrayList<Instance>();
	List<String> headers = new ArrayList<String>();
	
	
	
	public String getHeader() {
		return header;
	}

	public List<String> getDatapoints() {
		return datapoints;
	}


	public void loadCSVFile(String fileName){
		BufferedReader fileReader = null;
		String line ="";
		int lineCounter = 0;
		int tokenCount = 0;
		
		try {
			fileReader = new BufferedReader(new FileReader(fileName));
			
			// this first line should contain the attribute labels
			header = fileReader.readLine();
			
			//Read the file line by line

			while ((line = fileReader.readLine()) != null) {
				datapoints.add(line);

			} // end while loop
			
			fileReader.close();
		} catch (IOException e) {
			// TODO return an error to the user
			e.printStackTrace();
		} 
	}
	
	
	
	
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
			
			for ( int i=0; i<tokens.length-1; ++i){
				headers.add(tokens[i]);
				System.out.print(tokens[i] + " ");
			}
			System.out.println();
			
			tokenCount = tokens.length;
//			for ( int i=0; i<tokens.length-1; ++i){
//				attributeLabels.add(tokens[i]);
//			}
		
			//Read the file line by line

			while ((line = fileReader.readLine()) != null) {
				++lineCounter;
				tokens = line.split(",");

				if (tokens.length > 0 && tokens.length == tokenCount) {
					
					System.out.println(line);

//					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//					String x = tokens[0];
//					try {
//						Date inputDate = df.parse(tokens[1].replace("\"",""));
//					} catch (ParseException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					//System.out.println("date = "+inputDate);
//					int dayNumber = Integer.parseInt(tokens[1]);
//					int tT_Time;
//					if(tokens[2].equalsIgnoreCase("na") | tokens[2].equalsIgnoreCase("") ){
//						tT_Time = 0;
//					} else {
//						tT_Time = Integer.parseInt(tokens[2]);
//					}
//					
//					int tT_Distance ;
//					if(tokens[3].equalsIgnoreCase("na") | tokens[3].equalsIgnoreCase("")){
//						tT_Distance  = 0;
//					} else {
//						tT_Distance  = Integer.parseInt(tokens[3]);
//					}
//					
//					int tHigh_Intensity_Distance ;
//					if(tokens[4].equalsIgnoreCase("na") | tokens[4].equalsIgnoreCase("") ){
//						tHigh_Intensity_Distance  = 0;
//					} else {
//						tHigh_Intensity_Distance  = Integer.parseInt(tokens[4]);
//					}
//					
//					Instance ins = new Instance(lineCounter);
//					
//					for ( int i=0; i<tokens.length-1; ++i){
//						ins.addAttributeValue(null, tokens[i]);
//					}
//					instances.add(ins);
//					System.out.println();

				}
				//++lineCounter;
			} // end while loop
			
			for(Instance ins : instances){
				
				//System.out.println(ins.toString());
			}
			
			fileReader.close();
		} catch (IOException e) {
			// TODO return an error to the user
			e.printStackTrace();
		} 
	}
	
}


