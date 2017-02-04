package cn.aaron.ablog.base;

import java.util.HashMap;
import java.util.Map;

/**
*TODO
*@author Aaron
*@date 2017年2月4日
*/
public class SystemProperty {
	
	private Map<String, String> resources = new HashMap<String, String>();
	
	public SystemProperty(){}
	
	public SystemProperty(Map<String, String> resources){
		this.resources = resources;
	}

	public Map<String, String> getResources() {
		return resources;
	}

	public String getProperty(String key,String defaultVal){
		String val = this.resources.get(key);
		if(val==null){
			val = defaultVal;
		}
		return val;
	}
	
	public String getProperty(String key){
		return getProperty(key, null);
	}
	
}
