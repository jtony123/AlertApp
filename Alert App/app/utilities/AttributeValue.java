package utilities;
/**
 * 
 */

/**
 * @author Anthony Jackson
 *
 */
public class AttributeValue {

	// TODO :  update this class to make use of generics
	private Double doubleValue;
	private String stringValue;
	
	public Object getValue(){
		if(doubleValue != null){
			return doubleValue;
		} else {
			return stringValue;
		}
	}
	
	
	/**
	 * @return the doubleValue
	 */
	public Double getDoubleValue() {
		return doubleValue;
	}
	/**
	 * @param doubleValue the doubleValue to set
	 */
	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}
	/**
	 * @return the stringValue
	 */
	public String getStringValue() {
		return stringValue;
	}
	/**
	 * @param stringValue the stringValue to set
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}


	
	
}

