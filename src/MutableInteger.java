package com.tobee.gis.support;

public class MutableInteger implements Comparable<Integer> {
    
  	private Integer value;
		
		public MutableInteger()
		{
			value = null;
		}
		
		public MutableInteger(Integer value)
		{
			this.value = value;
		}
		
		public boolean isValueSet()
		{
			return value == null;
		}
		
		public void setInteger(Integer value)
		{
			this.value = value;
		}
		
		public Integer getInteger()
		{
			return value;
		}
		
		public void increaseInteger()
		{
			this.value++;
		}
		
		
		public String toString() {
 			return value.toString();
		}
    
    
 		public int compareTo(Integer anotherInteger) {
 		    return (value.intValue() < anotherInteger.intValue()) ? -1 : ((value.intValue() == anotherInteger.intValue()) ? 0 : 1);       
 		}
}