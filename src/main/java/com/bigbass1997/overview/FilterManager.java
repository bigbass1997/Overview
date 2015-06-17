package com.bigbass1997.overview;

import java.util.ArrayList;
import java.util.Hashtable;

import com.bigbass1997.overview.util.Util;

public class FilterManager {
	
	public static Hashtable<Filters, ArrayList<String>> filters;
	
	public static boolean containsFilterMaterial(Filters filterType, String data){
		for(String s : filters.get(filterType)){
			if(data.contains(s)){
				if(ConfigManager.debug) Util.log.info("data: '" + data + "' contains '" + s + "'");
				return true;
			}
		}
		return false;
	}
}
