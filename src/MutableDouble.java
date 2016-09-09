package com.tobee.gis.support;


public class MutableDouble implements Comparable<Double> {
    
  	private Double value;
		
		
		public MutableDouble()
		{
			value = null;
		}
		
		public MutableDouble(Double value)
		{
			this.value = value;
		}
		
		public boolean isValueSet()
		{
			return value == null;
		}
		
		public void setDouble(Double value)
		{
			this.value = value;
		}
		
		public void setDouble(double value)
		{
			this.value = value;
		}
		
		public Double getDouble()
		{
			return value;
		}
		
		public void increaseDouble()
		{
			this.value++;
		}
		
		
		public String toString() {
 			return value.toString();
		}
		
		public int compareTo(Double anotherDouble) {
		    return Double.compare(value.doubleValue(), anotherDouble.doubleValue());
		}
    
}