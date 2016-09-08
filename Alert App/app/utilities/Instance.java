package utilities;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Anthony Jackson
 *
 *	Instance hold all the information pertaining to each instance(line)
 *	of the dataset being analysed.
 */
public class Instance {

	private int instanceId;
	private List<AttributeValue> attributes;
	
	/**
	 * @param id
	 */
	public Instance(int id) {
		this.instanceId = id;
		attributes = new ArrayList<AttributeValue>();
	}
	
	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}


	public List<AttributeValue> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeValue> attributes) {
		this.attributes = attributes;
	}


	public void addAttributeValue(Double d, String string) {		
		
		AttributeValue av = new AttributeValue();
		if(d!=null){
			av.setDoubleValue(d);
		} else {
			av.setStringValue(string);
		}				
		attributes.add(av);		
	}
	
	public String toString(){
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.getInstanceId()+", ");
		for(AttributeValue att : this.getAttributes()){
			sb.append(att.getValue()+", ");
		}		
	
		return sb.toString();		
	}	
}
