package com.tobee.gis.coordconv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.StringTokenizer;


import com.tobee.gis.coordconv.GeoProjection.GeoEllips;
import com.tobee.gis.coordconv.GeoProjection.GeoSystem;

import com.tobee.gis.support.MutableDouble;
import com.tobee.gis.support.MutableInteger;

public class MapConv
{
	//private GeoCoordConv m_CoordConv;

	public MapConv()
	{
		/*
		m_CoordConv 
			= new GeoCoordConv(
					GeoEllips.kBessel1984, 
					GeoSystem.kGeographic, 
					GeoEllips.kBessel1984, 
					GeoSystem.kTmMid);
				*/
		//m_CoordConv = null;
	}
	
	public void calcCoordConv(
		GeoEllips InCboEllips, GeoSystem InCboSystem, 
		GeoEllips OutCboEllips, GeoSystem OutCboSystem,
		CoordsInfo coordsinfo
	)	throws GeoException{
		ConvGeoTools geotoolsconv = new ConvGeoTools(InCboEllips,InCboSystem,OutCboEllips,OutCboSystem);
			
		try {
			if(InCboSystem == GeoSystem.kGeographic || OutCboSystem == GeoSystem.kGeographic)
				geotoolsconv.doTransform(coordsinfo,true);
			else
				geotoolsconv.doTransform(coordsinfo,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public void calcCoordConvOld(
			GeoEllips InCboEllips, GeoSystem InCboSystem, 
			GeoEllips OutCboEllips, GeoSystem OutCboSystem,
			CoordsInfo coordsinfo
		)	throws GeoException{
			double dInX=0, dInY=0;
		
			MutableDouble dOutX = null, dOutY = null;
			MutableDouble dOutLon = null, dOutLat =null;
			MutableInteger iDeg=null, iMin=null;
			MutableDouble dSec=null;
			
			GeoCoordConv CoordConv 
				= new GeoCoordConv(InCboEllips,InCboSystem,OutCboEllips,OutCboSystem);
			
			// 우선 경위도로 변환
			//m_CoordConv.SetSrcType(InCboEllips, InCboSystem);
			//m_CoordConv.SetDstType(OutCboEllips, OutCboSystem);
			//System.out.println(""+InCboEllips+":"+InCboSystem+":"+OutCboEllips+":"+OutCboSystem+":");
			// Display Result
			if (InCboSystem == GeoSystem.kGeographic)
			{
				// Get Geographic Value
				dOutLon = new MutableDouble();
				dOutLat = new MutableDouble();
				
				String tmpLon = coordsinfo.getsInLongitude();
				String tmpLat = coordsinfo.getsInLatitude();
				
				if(tmpLon.equals(""))
					tmpLon = "0";
					
				if(tmpLat.equals(""))
					tmpLat = "0";
					
				dInX = Double.valueOf(tmpLon);
				dInY = Double.valueOf(tmpLat);
				
				//System.out.println(""+dInX + ":rrrrrr" + dInY);
				CoordConv.Conv(dInX, dInY, dOutLon, dOutLat);
				//System.out.println(dOutLon.getDouble()+"rrrr:"+dOutLat.getDouble());
			}
			else {
				// Get Geographic Value
				dOutLon = new MutableDouble();
				dOutLat = new MutableDouble();
				
				dInX = Double.valueOf(coordsinfo.getsInX());
				dInY = Double.valueOf(coordsinfo.getsInY());
				CoordConv.Conv(dInX, dInY, dOutLon, dOutLat);
				CoordConv.Clear();
				CoordConv = null;
			}
			
			
			
			// 경위도 값 디스플레이
			coordsinfo.setsOutLongitude(String.format("%.10f", dOutLon.getDouble()));
			coordsinfo.setsOutLatitude(String.format("%.10f", dOutLat.getDouble()));
			
			iDeg=new MutableInteger();
			iMin=new MutableInteger();
			dSec=new MutableDouble();
			
			GeoCoordConv.D2Dms(dOutLon, iDeg, iMin, dSec);
			coordsinfo.setsOutXDegree(String.format("%d", iDeg.getInteger()));
			coordsinfo.setsOutXMinute(String.format("%d", iMin.getInteger()));
			coordsinfo.setsOutXSecond(String.format("%.4f", dSec.getDouble()));
			
			iDeg=new MutableInteger();
			iMin=new MutableInteger();
			dSec=new MutableDouble();
			
			GeoCoordConv.D2Dms(dOutLat, iDeg, iMin, dSec);
			
			coordsinfo.setsOutYDegree(String.format("%d", iDeg.getInteger()));
			coordsinfo.setsOutYMinute(String.format("%d", iMin.getInteger()));
			coordsinfo.setsOutYSecond(String.format("%.4f", dSec.getDouble()));
			
			// TM 값 디스플레이
			if (OutCboSystem == GeoSystem.kGeographic)
			{
				coordsinfo.setsOutX("");
				coordsinfo.setsOutY("");
			}
			else 
			{
				CoordConv	
					= new GeoCoordConv(InCboEllips,InCboSystem,OutCboEllips,OutCboSystem);
				dOutX = new MutableDouble();
				dOutY = new MutableDouble();
				
				//m_CoordConv.SetSrcType(InCboEllips, InCboSystem);
				//m_CoordConv.SetDstType(OutCboEllips, OutCboSystem);
				CoordConv.Conv(dInX, dInY, dOutX, dOutY);
		
				coordsinfo.setsOutX(String.format("%.4f", dOutX.getDouble()));
				coordsinfo.setsOutY(String.format("%.4f", dOutY.getDouble()));
				CoordConv.Clear();
			}
			
			CoordConv = null;
		}
	
	public void calcFileCoordConv(
		String PathName,
		GeoEllips InCboEllips, GeoSystem InCboSystem, 
		GeoEllips OutCboEllips, GeoSystem OutCboSystem
	)	throws GeoException, FileNotFoundException, IOException
	{
		
		System.out.println("Path is " + PathName);
		
		GeoCoordConv CoordConv 
			= new GeoCoordConv(InCboEllips,InCboSystem,OutCboEllips,OutCboSystem);
		
		BufferedReader rd 
			= new BufferedReader(
					new InputStreamReader(
						new FileInputStream(PathName)
					)
				);
				
		String line = null;
		
		String outFile = PathName.substring(0,PathName.lastIndexOf(".")) + "_Output.txt";
		
		System.out.println(outFile);
		
		PrintWriter out
			= new PrintWriter(
					new BufferedWriter(
						new OutputStreamWriter(
							new FileOutputStream(
								outFile
							)
						)
					)
				);					
						
		boolean isGeographic = (OutCboSystem == GeoSystem.kGeographic) ? true : false;
		
		double vIn_X = 0;
		double vIn_Y = 0;
		
		
		int cnt = 1;
		
		// 출력 옵션에 맞춰 내보내보자..
		if (isGeographic)		// 경위도 출력이면...
		{
			while((line = rd.readLine()) != null)
			{
				StringTokenizer st = new StringTokenizer(line, " ");
				
				MutableDouble vOut_X = new MutableDouble();
				MutableDouble vOut_Y = new MutableDouble();
				
				vIn_X = Double.valueOf(st.nextToken()).doubleValue();
				vIn_Y = Double.valueOf(st.nextToken()).doubleValue();
				
				//m_CoordConv.SetSrcType(InCboEllips, InCboSystem);
				//m_CoordConv.SetDstType(OutCboEllips, OutCboSystem);
				CoordConv.Conv(vIn_X, vIn_Y, vOut_X, vOut_Y);
				
				out.println(String.format("%.10f %.10f\n", vOut_X.getDouble(), vOut_Y.getDouble()));
				
				vOut_X = vOut_Y = null;
				st = null;
			}
			CoordConv.Clear();
			CoordConv = null;
		}
		else		// 투영좌표 출력이면...
		{
			
			while((line = rd.readLine()) != null)
			{
				MutableDouble vOut_X = new MutableDouble();
				MutableDouble vOut_Y = new MutableDouble();
				StringTokenizer st = new StringTokenizer(line, " ");
				
				vIn_X = Double.valueOf(st.nextToken()).doubleValue();
				vIn_Y = Double.valueOf(st.nextToken()).doubleValue();
				
				//m_CoordConv.SetSrcType(InCboEllips, InCboSystem);
				//m_CoordConv.SetDstType(OutCboEllips, OutCboSystem);
				CoordConv.Conv(vIn_X, vIn_Y, vOut_X, vOut_Y);
				out.println(String.format("%.3f %.3f\n", vOut_X.getDouble(), vOut_Y.getDouble()));
				
				vOut_X = vOut_Y = null;
				st = null;
			}
			
			CoordConv.Clear();
			CoordConv = null;
				
		}
		
		out.flush();
		out.close();
		rd.close();
		rd = null;
		out = null;
	}
	
	
	public final void D2Dms(
		MutableDouble _dInDecimalDegree, 
		MutableInteger _iOutDegree, MutableInteger _iOutMinute, 
		MutableDouble _dOutSecond
	) throws GeoException
	{
		
		GeoCoordConv.D2Dms(_dInDecimalDegree,_iOutDegree,_iOutMinute,_dOutSecond);
		return;
	}
	
	private static CoordsInfo convCoords(
		String srcE, String srcS, 
		String destE, String destS,
		String srcX, String srcY,
		String srcLat,String srcLon
	) throws GeoException
	{
		boolean isOK = false;
		GeoEllips eSrcEllips=null;
		GeoSystem eSrcSystem=null;
		GeoEllips eDstEllips=null;
		GeoSystem eDstSystem=null;
		
		String []Ellipse = new String[] { "Bessel 1841", "WGS84", "GRS80" };
		
		String []Systems = new String[] { "Geographic", 
										"TM Korea West", "TM Korea Middle",  
										"TM Korea East", "KATEC", "UTM 52N", "UTM 51N" };
							
		for(int i = 0; i<Ellipse.length;i++)
		{
			if(srcE.equals(Ellipse[i]))
			{
				if(GeoEllips.kBessel1984.val == i)
				{
					eSrcEllips = GeoEllips.kBessel1984;
				}
				else if(GeoEllips.kWgs84.val == i)
				{
					eSrcEllips = GeoEllips.kWgs84;
				}
				else if(GeoEllips.kGRS80.val == i)
				{
					eSrcEllips = GeoEllips.kGRS80;
				}
				else
				{
					isOK = false;
					break;	
				}	
				isOK = true;
				break;
			}
		}
		
		if(!isOK)
		{
			System.out.printf("Wrong Source Ellipsoid....[%s]", srcE);
			return null;
		}
		else isOK = false;
		
		for(int i = 0; i<Systems.length;i++)
		{
			
			if(srcS.equals(Systems[i]))
			{
				if(GeoSystem.kGeographic.val == i)
				{
					eSrcSystem = GeoSystem.kGeographic;
				}
				else if(GeoSystem.kTmWest.val == i)
				{
					eSrcSystem = GeoSystem.kTmWest;
				}
				else if(GeoSystem.kTmMid.val == i)
				{
					eSrcSystem = GeoSystem.kTmMid;
				}
				else if(GeoSystem.kTmEast.val == i)
				{
					eSrcSystem = GeoSystem.kTmEast;
				}
				else if(GeoSystem.kUtm52.val == i)
				{
					eSrcSystem = GeoSystem.kUtm52;
				}
				else if(GeoSystem.kUtm51.val == i)
				{
					eSrcSystem = GeoSystem.kUtm51;
				}
				else
				{
					isOK = false;
					break;	
				}
				isOK = true;		
				break;
			}
		}
		
		if(!isOK)
		{
			System.out.printf("Wrong Source System....[%s]", srcS);
			return null;
		}
		else isOK = false;
		
		for(int i = 0; i<Ellipse.length;i++)
		{
			if(destE.equals(Ellipse[i]))
			{
				if(GeoEllips.kBessel1984.val == i)
					eDstEllips = GeoEllips.kBessel1984;
				else if(GeoEllips.kWgs84.val == i)
					eDstEllips = GeoEllips.kWgs84;
				else if(GeoEllips.kGRS80.val == i)
					eDstEllips = GeoEllips.kGRS80;
				else
				{
					isOK = false;
					break;	
				}
				isOK = true;		
				break;
			}
		}
		
		if(!isOK)
		{
			System.out.printf("Wrong Target Ellipsoid....[%s]", destE);
			return null;
		}
		else isOK = false;
		
		for(int i = 0; i<Systems.length;i++)
		{
			if(destS.equals(Systems[i]))
			{
				if(GeoSystem.kGeographic.val == i)
					eDstSystem = GeoSystem.kGeographic;
				else if(GeoSystem.kTmWest.val == i)
					eDstSystem = GeoSystem.kTmWest;
				else if(GeoSystem.kTmMid.val == i)
					eDstSystem = GeoSystem.kTmMid;
				else if(GeoSystem.kTmEast.val == i)
					eDstSystem = GeoSystem.kTmEast;
				else if(GeoSystem.kUtm52.val == i)
					eDstSystem = GeoSystem.kUtm52;
				else if(GeoSystem.kUtm51.val == i)
					eDstSystem = GeoSystem.kUtm51;
				else
				{
					isOK = false;
					break;	
				}
				isOK = true;		
				break;
			}
		}
		
		if(!isOK)
		{
			System.out.printf("Wrong Target System....[%s]", destS);
			return null;
		}
		
		MapConv mapConv = new MapConv();
		CoordsInfo coordsinfo = new CoordsInfo();
		
		coordsinfo.setsInLongitude(srcLon);
	  coordsinfo.setsInLatitude(srcLat);
		coordsinfo.setsInX(srcX);
		coordsinfo.setsInY(srcY);
		
		mapConv.calcCoordConv(
			eSrcEllips, eSrcSystem, 
			eDstEllips, eDstSystem,
			coordsinfo
		);
		
		return coordsinfo;
	}
/*	
	public static void main(String[] args) throws GeoException
	{
		String srcE=null, srcS=null, destE=null, destS=null;
		String srcX=null, srcY=null, srcLat=null, srcLon=null;
		srcE=System.getProperty("srcElip");
		srcS=System.getProperty("srcSys");
		destE=System.getProperty("targetElip");
		destS=System.getProperty("targetSys");
		
		srcX=System.getProperty("srcX");
		srcY=System.getProperty("srcY");
		srcLat=System.getProperty("srcLat");
		srcLon=System.getProperty("srcLon");
		
		CoordsInfo coordsinfo = convCoords(srcE, srcS, destE, destS, srcX,srcY,srcLat,srcLon);
		
		System.out.println("위도:" + coordsinfo.getsOutLatitude());
		System.out.println("경도:" + coordsinfo.getsOutLongitude());
		
		System.out.println("X좌표:" + coordsinfo.getsOutX());
		System.out.println("Y좌표:" + coordsinfo.getsOutY());
		
	}
*/
}

