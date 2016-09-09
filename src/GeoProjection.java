package com.tobee.gis.coordconv;

public class GeoProjection
{
	public enum GeoEllips 
	{
		kBessel1984(0),
		kWgs84(1),
		kGRS80(2);
		
		int val;      
	   
		GeoEllips( int v )   {      this.val = v;     }  
		public void setvalue(int v)   {      this.val = v;     }  
		public int value()   {       return val;      } 
	};
	
	public enum GeoSystem {
		kGeographic(0),
		kTmWest(1),
		kTmMid(2),
		kTmEast(3), 
		kKatec(4),
		kUtm52(5),
		kUtm51(6);
		
		final int val;      
	   
		GeoSystem( int v ) {         this.val = v;     }      
		public int value()   {          return val;      } 
	};
	
}