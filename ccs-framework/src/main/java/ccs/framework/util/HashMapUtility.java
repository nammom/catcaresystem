package ccs.framework.util;

import java.util.HashMap;
import java.util.Map;

public class HashMapUtility<T,K> {

	private Map<T,K> map = new HashMap<T,K>();
			
	public static <T,K> HashMapUtility<T,K> create(){
		return new HashMapUtility<T,K>();
	}
	
	public HashMapUtility<T,K> add(T t, K k){
		map.put(t, k);
		return this;
	}
	
	public HashMapUtility<T,K> add(Map<T,K> map){
		map.putAll(map);
		return this;
	}
	
	public Map<T,K> toMap(){
		return map;
	}
}
