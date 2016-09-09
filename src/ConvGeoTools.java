package com.tobee.gis.coordconv;

/**
* ■gt-metadata-2.7.4.jar
* ■gt-opengis-2.7.4.jar
* ■gt-referencing-2.7.4.jar
* ■jsr-275-1.0-beta-2.jar
* ■vecmath-1.3.2.jar
*/
import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.tobee.gis.coordconv.GeoProjection.GeoEllips;
import com.tobee.gis.coordconv.GeoProjection.GeoSystem;
import com.tobee.gis.support.ESPGCode;
import com.tobee.gis.support.MutableDouble;
import com.tobee.gis.support.MutableInteger;

public class ConvGeoTools {

	private CoordinateReferenceSystem sourceCRS;
	private CoordinateReferenceSystem targetCRS;
	
	private GeoEllips eSrcEllips; 
	private GeoSystem eSrcSystem; 
	private GeoEllips eDstEllips;
	private GeoSystem eDstSystem;
	
	
	//if the source ellipsoid is grs80
	// then setting sourceCRS and destination ellipsoid
	// if destination ellipsoid must be one of them
	// - GRS80 EAST     -> EPSG:5187
	// - GRS80 MID      -> EPSG:5186
	// - GRS80 WEST     -> EPSG:5185
	// - Bessel EAST    -> EPSG:5176
	// - Bessel WEST    -> EPSG:5173
	// - Bessel MID     -> EPSG:5174
	// - WGS84 elipsoid -> EPSG:4326
	public ConvGeoTools(
		GeoEllips eSrcEllips, GeoSystem eSrcSystem, 
		GeoEllips eDstEllips, GeoSystem eDstSystem
	)	{
		this.eSrcEllips = eSrcEllips; 
		this.eSrcSystem = eSrcSystem; 
		this.eDstEllips = eDstEllips;
		this.eDstSystem = eDstSystem;
		selectProperSrcEllips((eSrcEllips==GeoEllips.kGRS80? true:false));
		selectProperDestEllips((eDstEllips==GeoEllips.kGRS80? true:false));
	}
	
	private void selectProperSrcEllips(boolean isGRS80)
	{
		if(isGRS80)
		{
			switch(eSrcSystem.val)
			{
				case 0: //GeoSystem.kGeographic 
					this.sourceCRS = ESPGCode.GRS80;
					break;
				case 1://GeoSystem.kTmWest
					this.sourceCRS = ESPGCode.GRS80_WEST;
					break;
				case 2://GeoSystem.kTmMid
					this.sourceCRS = ESPGCode.GRS80_MID;
					break;
				case 3://GeoSystem.kTmEast
					this.sourceCRS = ESPGCode.GRS80_EAST;
					break;
				case 4://GeoSystem.kKatec
					this.sourceCRS = ESPGCode.KATEC;
					break;
				case 5://GeoSystem.kUtm52
					this.targetCRS = ESPGCode.UTM52N;
					break;
				case 6://GeoSystem.kUtm51
					this.targetCRS = ESPGCode.UTM51N;
					break;
			}
		}
		else
		{
			switch(eSrcSystem.val)
			{
				case 0: //GeoSystem.kGeographic 
					this.sourceCRS = ESPGCode.WGS84;
					break;
				case 1://GeoSystem.kTmWest
					this.sourceCRS = ESPGCode.BESSEL_WEST;
					break;
				case 2://GeoSystem.kTmMid
					this.sourceCRS = ESPGCode.BESSEL_MID;
					break;
				case 3://GeoSystem.kTmEast
					this.sourceCRS = ESPGCode.BESSEL_EAST;
					break;
				case 4://GeoSystem.kKatec
					this.sourceCRS = ESPGCode.KATEC;
					break;
				case 5://GeoSystem.kUtm52
					this.targetCRS = ESPGCode.UTM52N;
					break;
				case 6://GeoSystem.kUtm51
					this.targetCRS = ESPGCode.UTM51N;
					break;
			}
		}
		
	}
	
	private void selectProperDestEllips(boolean isGRS80)
	{
		if(isGRS80)
		{
			switch(eDstSystem.val)
			{
				case 0: //GeoSystem.kGeographic 
					this.targetCRS = ESPGCode.GRS80;
					break;
				case 1://GeoSystem.kTmWest
					this.targetCRS = ESPGCode.GRS80_WEST;
					break;
				case 2://GeoSystem.kTmMid
					this.targetCRS = ESPGCode.GRS80_MID;
					break;
				case 3://GeoSystem.kTmEast
					this.targetCRS = ESPGCode.GRS80_EAST;
					break;
				case 4://GeoSystem.kKatec
					this.targetCRS = ESPGCode.KATEC;
					break;
				case 5://GeoSystem.kUtm52
					this.targetCRS = ESPGCode.UTM52N;
					break;
				case 6://GeoSystem.kUtm51
					this.targetCRS = ESPGCode.UTM51N;
					break;
			}
		}
		else
		{
			switch(eDstSystem.val)
			{
				case 0: //GeoSystem.kGeographic 
					this.targetCRS = ESPGCode.WGS84;
					break;
				case 1://GeoSystem.kTmWest
					this.targetCRS = ESPGCode.BESSEL_WEST;
					break;
				case 2://GeoSystem.kTmMid
					this.targetCRS = ESPGCode.BESSEL_MID;
					break;
				case 3://GeoSystem.kTmEast
					this.targetCRS = ESPGCode.BESSEL_EAST;
					break;
				case 4://GeoSystem.kKatec
					this.targetCRS = ESPGCode.KATEC;
					break;
				case 5://GeoSystem.kUtm52
					this.targetCRS = ESPGCode.UTM52N;
					break;
				case 6://GeoSystem.kUtm51
					this.targetCRS = ESPGCode.UTM51N;
					break;
			}
		}
		
	}
	
	boolean doTransform(CoordsInfo coordsinfo, boolean isReverseXY) throws Exception 
	{
		MutableDouble dOutX = null, dOutY = null;
		MutableInteger iDeg=null, iMin=null;
		MutableDouble dSec=null;
		double dInX=0, dInY=0;
		
		if (eSrcSystem == GeoSystem.kGeographic)
		{
			// Get Geographic Value
			dOutX = new MutableDouble();
			dOutY = new MutableDouble();
			
			String tmpLon = coordsinfo.getsInLongitude();
			String tmpLat = coordsinfo.getsInLatitude();
			
			if(tmpLon.equals(""))
				tmpLon = "0";
				
			if(tmpLat.equals(""))
				tmpLat = "0";
				
			dInX = Double.valueOf(tmpLon);
			dInY = Double.valueOf(tmpLat);
			
			coordTransform(dInX, dInY, dOutX, dOutY);
			
		}
		else {
			// Get Geographic Value
			dOutX = new MutableDouble();
			dOutY = new MutableDouble();
			
			dInX = Double.valueOf(coordsinfo.getsInX());
			dInY = Double.valueOf(coordsinfo.getsInY());
			
			if(eDstSystem == GeoSystem.kGeographic)
				coordTransform(dInY, dInX, dOutX, dOutY);
			else
				coordTransform(dInX, dInY, dOutX, dOutY);
		}
		
		if (eDstSystem == GeoSystem.kGeographic)
		{
			coordsinfo.setsOutX("");
			coordsinfo.setsOutY("");
			double tmpX = dOutX.getDouble();
			double tmpY = dOutY.getDouble();
			
			// 경위도 값 디스플레이
			coordsinfo.setsOutLongitude(String.format("%.10f", tmpY));
			coordsinfo.setsOutLatitude(String.format("%.10f", tmpX));
			
			iDeg=new MutableInteger();
			iMin=new MutableInteger();
			dSec=new MutableDouble();
			
			GeoCoordConv.D2Dms(dOutY, iDeg, iMin, dSec);
			coordsinfo.setsOutXDegree(String.format("%d", iDeg.getInteger()));
			coordsinfo.setsOutXMinute(String.format("%d", iMin.getInteger()));
			coordsinfo.setsOutXSecond(String.format("%.4f", dSec.getDouble()));
			
			iDeg=new MutableInteger();
			iMin=new MutableInteger();
			dSec=new MutableDouble();
			
			GeoCoordConv.D2Dms(dOutX, iDeg, iMin, dSec);
			
			coordsinfo.setsOutYDegree(String.format("%d", iDeg.getInteger()));
			coordsinfo.setsOutYMinute(String.format("%d", iMin.getInteger()));
			coordsinfo.setsOutYSecond(String.format("%.4f", dSec.getDouble()));
		}	
		else 
		{
			
			coordsinfo.setsOutX(String.format("%.4f", dOutY.getDouble()));
			coordsinfo.setsOutY(String.format("%.4f", dOutX.getDouble()));
			
			if(eSrcSystem != GeoSystem.kGeographic)
			{
				coordsinfo.setsOutX(String.format("%.4f", dOutX.getDouble()));
				coordsinfo.setsOutY(String.format("%.4f", dOutY.getDouble()));
			}
		}
		
		
		return true;
	}
	
	public ConvGeoTools(CoordinateReferenceSystem sourceCrs, CoordinateReferenceSystem targetCrs) 
	{
		this.sourceCRS = sourceCrs;
		this.targetCRS = targetCrs;
	}

	void coordTransformTM2GRS80()
	{
		try {
			this.sourceCRS = CRS.decode("EPSG:5186");
			this.targetCRS = CRS.decode("EPSG:4019");
		} catch (NoSuchAuthorityCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void coordTransformGRS802TM()
	{
		try {
			//BesselTM to GRS80 lonlat
			//EPSG:2097 mid
			//EPSG:5174 modi+mid
			//EPSG:5186 grs80+mid
			//Y: 576391.5875
			//X: 267196.2552
			CoordinateReferenceSystem s = CRS.decode("EPSG:5186");
			CoordinateReferenceSystem d = CRS.decode("EPSG:4019");
			
			MathTransform mathTransform  = CRS.findMathTransform(s, d, true);   
			DirectPosition2D srcDirectPosition2D = new DirectPosition2D(s, 197199.2, 450759.5 ); 
			
			DirectPosition2D destDirectPosition2D = new DirectPosition2D();
			
			mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
			
			System.out.println("output point: " + destDirectPosition2D);
			
			System.out.println("Inverse of output point: "
			  + mathTransform.inverse().transform(destDirectPosition2D, null));
			
			
			s = CRS.decode("EPSG:2097");
			d = CRS.decode("EPSG:4019");
			
			mathTransform  = CRS.findMathTransform(s, d, true);   
			srcDirectPosition2D = new DirectPosition2D(s, 267196.2552, 576391.5875); 
			
			destDirectPosition2D = new DirectPosition2D();
			
			mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
			
			System.out.println("output point: " + destDirectPosition2D);
			
			System.out.println("Inverse of output point: "
			  + mathTransform.inverse().transform(destDirectPosition2D, null));
			
			s = CRS.decode("EPSG:5174");
			d = CRS.decode("EPSG:4019");
			
			mathTransform  = CRS.findMathTransform(s, d, true);   
			srcDirectPosition2D = new DirectPosition2D(s, 267196.2552, 576391.5875); 
			
			destDirectPosition2D = new DirectPosition2D();
			
			mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
			
			System.out.println("output point: " + destDirectPosition2D);
			
			System.out.println("Inverse of output point: "
			  + mathTransform.inverse().transform(destDirectPosition2D, null));
			
			
			s = CRS.decode("EPSG:5174");
			d = CRS.decode("EPSG:4326");
			
			mathTransform  = CRS.findMathTransform(s, d, true);   
			srcDirectPosition2D = new DirectPosition2D(s, 197199.2, 450759.5); 
			
			destDirectPosition2D = new DirectPosition2D();
			
			mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
			
			System.out.println("output point: " + destDirectPosition2D);
			
			System.out.println("Inverse of output point: "
			  + mathTransform.inverse().transform(destDirectPosition2D, null));
			
			
		} catch (NoSuchAuthorityCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	void coordTransform(double x, double y, MutableDouble ox, MutableDouble oy) throws Exception 
	{
		boolean lenient = true; 
		
		//System.out.println("SOURCE=================> " + sourceCRS);
		//System.out.println("DESTINATION=================> " + targetCRS);
		
		MathTransform mathTransform  = CRS.findMathTransform(sourceCRS, targetCRS, lenient);  
		
		DirectPosition2D srcDirectPosition2D = null;
		
		srcDirectPosition2D = new DirectPosition2D(sourceCRS, x, y); 
		
		
		DirectPosition2D destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		ox.setDouble(destDirectPosition2D.x);
		oy.setDouble(destDirectPosition2D.y);
		
		
		//coordTransformGRS802TM();
		
		//System.out.println("output point: " + destDirectPosition2D);
		
		//System.out.println("Inverse of output point: "
		 // + mathTransform.inverse().transform(destDirectPosition2D, null));
		return;
	}
	
	public static void main(String[]args) throws NoSuchAuthorityCodeException, FactoryException, MismatchedDimensionException, TransformException
	{
		CoordinateReferenceSystem s = null;
		CoordinateReferenceSystem d = null;
		DirectPosition2D srcDirectPosition2D = null;
		DirectPosition2D destDirectPosition2D = null;
		MathTransform mathTransform  = null;
		
		//Bessel1841 -> WGS84 input: inverse output : reverse 
		s = CRS.decode("EPSG:5174");
		d = CRS.decode("EPSG:4326");
		
		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 450759.5, 197199.2); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[Bessel1841 -> WGS84]output point: " + destDirectPosition2D);
		
		System.out.println("[Bessel1841 -> WGS84]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		//WGS84 -> Bessel1841  input: reverse output : inverse
		s = CRS.decode("EPSG:4326");
		d = CRS.decode("EPSG:5174");
		
		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 37.556887759981734, 126.97118852332342); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[WGS84 -> Bessel1841]output point: " + destDirectPosition2D);
		
		System.out.println("[WGS84 -> Bessel1841]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		//GRS80 MID -> GRS80 lonlat input: reverse output: reverse
		s = CRS.decode("EPSG:5186");
		d = CRS.decode("EPSG:4019");
		
		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 576391.5875, 267196.2552  ); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[GRS80_MID -> GRS80_lonlat]output point: " + destDirectPosition2D);
		
		System.out.println("[GRS80_MID -> GRS80_lonlat]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		//GRS80_lonlat -> GRS80_MID input: reverse output: inverse
		s = CRS.decode("EPSG:4019");
		d = CRS.decode("EPSG:5186");

		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 37.784831643147434, 127.7628212445506  ); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[GRS80_lonlat -> GRS80_MID]output point: " + destDirectPosition2D);
		
		System.out.println("[GRS80_lonlat -> GRS80_MID]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		//GRS80_lonlat -> WGS84 input: reverse output: reverse
		s = CRS.decode("EPSG:4019");
		d = CRS.decode("EPSG:4326");

		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 37.784831643147434, 127.7628212445506  ); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[GRS80_lonlat -> WGS84]output point: " + destDirectPosition2D);
		
		System.out.println("[WGS84 -> GRS80_lonlat]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		
		//GRS80_MID -> WGS84 input: inverse output: reverse
		s = CRS.decode("EPSG:5186");
		d = CRS.decode("EPSG:4326");

		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 576391.5875, 267196.2552  ); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[GRS80_MID -> WGS84]output point: " + destDirectPosition2D);
		
		System.out.println("[GRS80_MID -> WGS84]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		//WGS84 -> GRS80_MID input: reverse output: reverse
		s = CRS.decode("EPSG:4326");
		d = CRS.decode("EPSG:5186");

		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 37.78483168022847, 127.7628212445506  ); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[WGS84 -> GRS80_MID]output point: " + destDirectPosition2D);
		
		System.out.println("[WGS84 -> GRS80_MID]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		
		//GRS80_MID -> Bessel1841 input: reverse output: inverse
		s = CRS.decode("EPSG:5186");
		d = CRS.decode("EPSG:5174");

		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 576391.5875, 267196.2552  ); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[GRS80_MID -> Bessel1841]output point: " + destDirectPosition2D);
		
		System.out.println("[GRS80_MID -> Bessel1841]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
		
		//Bessel1841  -> GRS80_MID input: inverse output: inverse
		s = CRS.decode("EPSG:5186");
		d = CRS.decode("EPSG:5174");

		mathTransform  = CRS.findMathTransform(s, d, true);   
		srcDirectPosition2D = new DirectPosition2D(s, 266934.15526483994, 476327.69771018764  ); 
		
		destDirectPosition2D = new DirectPosition2D();
		
		mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);   
		
		System.out.println("[Bessel1841  -> GRS80_MID]output point: " + destDirectPosition2D);
		
		System.out.println("[Bessel1841  -> GRS80_MID]Inverse of output point: "
		  + mathTransform.inverse().transform(destDirectPosition2D, null));
	}
}

