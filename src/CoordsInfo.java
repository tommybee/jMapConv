package com.tobee.gis.coordconv;

import com.tobee.gis.coordconv.GeoProjection.GeoEllips;
import com.tobee.gis.coordconv.GeoProjection.GeoSystem;
import com.tobee.gis.support.MutableDouble;
import com.tobee.gis.support.MutableInteger;

public class CoordsInfo
{
	private String m_sInLongitude;
	private String m_sInLatitude;
	private String m_sOutX;
	private String m_sOutY;
	//private int		 m_iInCboSystem;
	private String m_sInX;
	private String m_sInXDegree;
	private String m_sInXMinute;
	private String m_sInXSecond;
	private String m_sInY;
	private String m_sInYDegree;
	private String m_sInYMinute;
	private String m_sInYSecond;
	private String m_sOutLatitude;
	private String m_sOutLongitude;
	private String m_sOutXDegree;
	private String m_sOutXMinute;
	private String m_sOutXSecond;
	private String m_sOutYDegree;
	private String m_sOutYMinute;
	private String m_sOutYSecond;
	
	
	public CoordsInfo()
	{
		m_sInLongitude=	m_sInLatitude=m_sOutX=m_sOutY=
		m_sInX=m_sInXDegree=m_sInXMinute=m_sInXSecond=m_sInY=
		m_sInYDegree=m_sInYMinute=m_sInYSecond=m_sOutLatitude=m_sOutLongitude=
		m_sOutXDegree=m_sOutXMinute=
		m_sOutXSecond=m_sOutYDegree=
		m_sOutYMinute=m_sOutYSecond=null;
	}
	
	public String getsInLongitude(){return m_sInLongitude;}
	public String getsInLatitude(){return m_sInLatitude;}
	public String getsOutX(){return m_sOutX;}
	public String getsOutY(){return m_sOutY;}
	public String getsInX(){return m_sInX;}
	public String getsInXDegree(){return m_sInXDegree;}
	public String getsInXMinute(){return m_sInXMinute;}
	public String getsInXSecond(){return m_sInXSecond;}
	public String getsInY(){return m_sInY;}
	public String getsInYDegree(){return m_sInYDegree;}
	public String getsInYMinute(){return m_sInYMinute;}
	public String getsInYSecond(){return m_sInYSecond;}
	public String getsOutLatitude(){return m_sOutLatitude;}
	public String getsOutLongitude(){return m_sOutLongitude;}
	public String getsOutXDegree(){return m_sOutXDegree;}
	public String getsOutXMinute(){return m_sOutXMinute;}
	public String getsOutXSecond(){return m_sOutXSecond;}
	public String getsOutYDegree(){return m_sOutYDegree;}
	public String getsOutYMinute(){return m_sOutYMinute;}
	public String getsOutYSecond(){return m_sOutYSecond;}
	
	public void setsInLongitude(String sInLongitude){ m_sInLongitude = sInLongitude;}
	public void setsInLatitude(String sInLatitude){ m_sInLatitude = sInLatitude;}
	
	public void setsOutX(String sOutX){ m_sOutX = sOutX;}
	public void setsOutY(String sOutY){ m_sOutY= sOutY;}
	
	public void setsInX(String sInX){ m_sInX = sInX;}
	public void setsInY(String sInY){ m_sInY = sInY;}
	
	public void setsInXDegree(String sInXDegree){ m_sInXDegree = sInXDegree;}
	public void setsInXMinute(String sInXMinute){ m_sInXMinute = sInXMinute;}
	public void setsInXSecond(String sInXSecond){ m_sInXSecond = sInXSecond;}
	
	public void setsInYDegree(String sInYDegree){ m_sInYDegree = sInYDegree;}
	public void setsInYMinute(String sInYMinute){ m_sInYMinute = sInYMinute;}
	public void setsInYSecond(String sInYSecond){ m_sInYSecond = sInYSecond;}
	
	public void setsOutLatitude(String sOutLatitude){ m_sOutLatitude = sOutLatitude;}
	public void setsOutLongitude(String sOutLongitude){ m_sOutLongitude = sOutLongitude;}
	
	public void setsOutXDegree(String sOutXDegree){ m_sOutXDegree = sOutXDegree;}
	public void setsOutXMinute(String sOutXMinute){ m_sOutXMinute = sOutXMinute;}
	public void setsOutXSecond(String sOutXSecond){ m_sOutXSecond = sOutXSecond;}
	
	public void setsOutYDegree(String sOutYDegree){ m_sOutYDegree = sOutYDegree;}
	public void setsOutYMinute(String sOutYMinute){ m_sOutYMinute = sOutYMinute;}
	public void setsOutYSecond(String sOutYSecond){ m_sOutYSecond = sOutYSecond;}
	
}