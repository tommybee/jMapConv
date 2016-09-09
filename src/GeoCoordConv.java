package com.tobee.gis.coordconv;

import com.tobee.gis.coordconv.GeoProjection.GeoEllips;
import com.tobee.gis.coordconv.GeoProjection.GeoSystem;
import com.tobee.gis.support.MutableDouble;
import com.tobee.gis.support.MutableInteger;

public class GeoCoordConv
{
	private static final double PI = 3.14159265358979;
	private static final double EPSLN = 0.0000000001;
//	private static final double S2R = 4.84813681109536E-06 ;// sin(1")
	
	//http://earth-info.nga.mil/GandG/publications/tm8358.1/tr83581b.html#ZZ17
	//Molodenskiy Transformation Constants to Convert from Local Datum to WGS 84 
	private static final double X_W2B = 128;
	private static final double Y_W2B = -481;
	private static final double Z_W2B = -664;
	
	
	// Internal Value for Tm2Geo
	private double m_dSrcE0, m_dSrcE1, m_dSrcE2, m_dSrcE3;
	private double m_dSrcE, m_dSrcEs, m_dSrcEsp;
	private double m_dSrcMl0, m_dSrcInd;

	// Internal Value for Geo2Tm
	private double m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3;
	private double m_dDstE, m_dDstEs, m_dDstEsp;
	private double m_dDstMl0, m_dDstInd;

	// Internal Value for DatumTrans
	private double m_dTemp;
	private double m_dEsTemp;
	private int m_iDeltaX;
	private int m_iDeltaY;
	private int m_iDeltaZ;
	private double m_dDeltaA, m_dDeltaF;
	
	public static void D2R(double degree, MutableDouble dd) {dd.setDouble(degree * PI / 180.0);}
	public static void R2D(double radian, MutableDouble dd) {dd.setDouble(radian * 180.0 / PI);}
	

// Ellips/System Type
	GeoEllips m_eSrcEllips;
	GeoSystem m_eSrcSystem;
	GeoEllips m_eDstEllips;
	GeoSystem m_eDstSystem;

	// Ellips Factor List
	private static double[] m_arMajor = new double[3];
	private static double[] m_arMinor = new double[3];

	// System Factor List 
	private static double[] m_arScaleFactor = new double[7];
	private static double[] m_arLonCenter = new double[7];
	private static double[] m_arLatCenter = new double[7];
	private static double[] m_arFalseNorthing = new double[7];
	private static double[] m_arFalseEasting = new double[7];
	
	
	public void Clear()
	{
		m_dSrcE0=m_dSrcE1=m_dSrcE2=m_dSrcE3=0;
		m_dSrcE=m_dSrcEs=m_dSrcEsp=0;
		m_dSrcMl0=m_dSrcInd=0;
	
		m_dDstE0=m_dDstE1=m_dDstE2=m_dDstE3=0;
		m_dDstE=m_dDstEs=m_dDstEsp=0;
		m_dDstMl0=m_dDstInd=0;
	
		m_dTemp=0;
		m_dEsTemp=0;
		m_iDeltaX=0;
		m_iDeltaY=0;
		m_iDeltaZ=0;
		m_dDeltaA=m_dDeltaF=0;
	}
	
	
	private void SetEllipsFactor()
	{
		m_arMajor[GeoEllips.kBessel1984.val] = 6377397.155;
		m_arMinor[GeoEllips.kBessel1984.val] = 6356078.96325;
		
		m_arMajor[GeoEllips.kWgs84.val] = 6378137.0;
		m_arMinor[GeoEllips.kWgs84.val] = 6356752.3142;
			
		m_arMajor[GeoEllips.kGRS80.val] = 6378137.0;
		m_arMinor[GeoEllips.kGRS80.val] = 6356752.31414;
		
		return;
	}
	
	void SetSystemFactor(boolean isGRS80)
	{
		m_arScaleFactor[GeoSystem.kGeographic.val] = 1;
		m_arLonCenter[GeoSystem.kGeographic.val] = 0.0;
		m_arLatCenter[GeoSystem.kGeographic.val] = 0.0;
		m_arFalseNorthing[GeoSystem.kGeographic.val] = 0.0;
		m_arFalseEasting[GeoSystem.kGeographic.val] = 0.0;
		
		if(isGRS80)
		{
			m_arScaleFactor[GeoSystem.kTmWest.val] = 1;
			m_arLonCenter[GeoSystem.kTmWest.val] = 2.18171200985643;
			m_arLatCenter[GeoSystem.kTmWest.val] = 0.663225115757845;
			m_arFalseNorthing[GeoSystem.kTmWest.val] = 600000.0;
			m_arFalseEasting[GeoSystem.kTmWest.val] = 200000.0;
		
			m_arScaleFactor[GeoSystem.kTmMid.val] = 1;
			m_arLonCenter[GeoSystem.kTmMid.val] = 2.21661859489632;
			m_arLatCenter[GeoSystem.kTmMid.val] = 0.663225115757845;
			m_arFalseNorthing[GeoSystem.kTmMid.val] = 600000.0;
			m_arFalseEasting[GeoSystem.kTmMid.val] = 200000.0;
		
			m_arScaleFactor[GeoSystem.kTmEast.val] = 1;
			m_arLonCenter[GeoSystem.kTmEast.val] = 2.2515251799362;
			m_arLatCenter[GeoSystem.kTmEast.val] = 0.663225115757845;
			m_arFalseNorthing[GeoSystem.kTmEast.val] = 600000.0;
			m_arFalseEasting[GeoSystem.kTmEast.val] = 200000.0;
		}
		else
		{
			m_arScaleFactor[GeoSystem.kTmWest.val] = 1;
			m_arLonCenter[GeoSystem.kTmWest.val] = 2.18171200985643;
			m_arLatCenter[GeoSystem.kTmWest.val] = 0.663225115757845;
			m_arFalseNorthing[GeoSystem.kTmWest.val] = 500000.0;
			m_arFalseEasting[GeoSystem.kTmWest.val] = 200000.0;
		
			m_arScaleFactor[GeoSystem.kTmMid.val] = 1;
			m_arLonCenter[GeoSystem.kTmMid.val] = 2.21661859489632;
			m_arLatCenter[GeoSystem.kTmMid.val] = 0.663225115757845;
			m_arFalseNorthing[GeoSystem.kTmMid.val] = 500000.0;
			m_arFalseEasting[GeoSystem.kTmMid.val] = 200000.0;
		
			m_arScaleFactor[GeoSystem.kTmEast.val] = 1;
			m_arLonCenter[GeoSystem.kTmEast.val] = 2.2515251799362;
			m_arLatCenter[GeoSystem.kTmEast.val] = 0.663225115757845;
			m_arFalseNorthing[GeoSystem.kTmEast.val] = 500000.0;
			m_arFalseEasting[GeoSystem.kTmEast.val] = 200000.0;
		}
		
		m_arScaleFactor[GeoSystem.kKatec.val] = 0.9999;
		m_arLonCenter[GeoSystem.kKatec.val] = 2.23402144255274;
		m_arLatCenter[GeoSystem.kKatec.val] = 0.663225115757845;
		m_arFalseNorthing[GeoSystem.kKatec.val] = 600000.0;
		m_arFalseEasting[GeoSystem.kKatec.val] = 400000.0;
	
		m_arScaleFactor[GeoSystem.kUtm52.val] = 0.9996;
		m_arLonCenter[GeoSystem.kUtm52.val] = 2.25147473507269;
		m_arLatCenter[GeoSystem.kUtm52.val] = 0.0;
		m_arFalseNorthing[GeoSystem.kUtm52.val] = 0.0;
		m_arFalseEasting[GeoSystem.kUtm52.val] = 500000.0;
	
		m_arScaleFactor[GeoSystem.kUtm51.val] = 0.9996;
		m_arLonCenter[GeoSystem.kUtm51.val] = 2.14675497995303;
		m_arLatCenter[GeoSystem.kUtm51.val] = 0.0;
		m_arFalseNorthing[GeoSystem.kUtm51.val] = 0.0;
		m_arFalseEasting[GeoSystem.kUtm51.val] = 500000.0;
	}
	
	public GeoCoordConv(
			GeoEllips eSrcEllips, GeoSystem eSrcSystem, 
			GeoEllips eDstEllips, GeoSystem eDstSystem
	)	{
		Clear();
	
	  // Set Ellips factor
		SetEllipsFactor();
		
		// Set System Factor
		SetSystemFactor((eDstEllips.val == GeoEllips.kGRS80.val ? true:false)||(eSrcEllips.val == GeoEllips.kGRS80.val ? true:false));
		
		SetSrcType(eSrcEllips, eSrcSystem);
		SetDstType(eDstEllips, eDstSystem);
		InitDatumVar(eSrcEllips,eDstEllips);
	}
	
	///////////////////////////////////////////////
	// Set Internal Values
	private void SetSrcType(GeoEllips eSrcEllips, GeoSystem eSrcSystem)
	{
		// Set Type Factor
		m_eSrcEllips = eSrcEllips;
		m_eSrcSystem = eSrcSystem;
		
		double temp = m_arMinor[eSrcEllips.val] / m_arMajor[eSrcEllips.val];
		m_dSrcEs = 1.0 - temp * temp;
		m_dSrcE = Math.sqrt(m_dSrcEs);
		m_dSrcE0 = e0fn(m_dSrcEs);
		m_dSrcE1 = e1fn(m_dSrcEs);
		m_dSrcE2 = e2fn(m_dSrcEs);
		m_dSrcE3 = e3fn(m_dSrcEs);
		m_dSrcMl0 = m_arMajor[eSrcEllips.val] * mlfn(m_dSrcE0, m_dSrcE1, m_dSrcE2, m_dSrcE3, m_arLatCenter[m_eSrcSystem.val]);
		m_dSrcEsp = m_dSrcEs / (1.0 - m_dSrcEs);
	
		if (m_dSrcEs < 0.00001)
		   m_dSrcInd = 1.0;
		else
		   m_dSrcInd = 0.0;
	
//		InitDatumVar(eSrcEllips);
	}
	
	// Set Internal Values
	private void SetDstType(GeoEllips eDstEllips, GeoSystem eDstSystem)
	{
		// Set Type Factor
		m_eDstEllips = eDstEllips;
		m_eDstSystem = eDstSystem;
	
		double temp = m_arMinor[eDstEllips.val] / m_arMajor[eDstEllips.val];
		
		m_dDstEs = 1.0 - temp * temp;
		m_dDstE = Math.sqrt(m_dDstEs);
		m_dDstE0 = e0fn(m_dDstEs);
		m_dDstE1 = e1fn(m_dDstEs);
		m_dDstE2 = e2fn(m_dDstEs);
		m_dDstE3 = e3fn(m_dDstEs);
		m_dDstMl0 = m_arMajor[eDstEllips.val] * mlfn(m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3, m_arLatCenter[m_eDstSystem.val]);
		m_dDstEsp = m_dDstEs / (1.0 - m_dDstEs);
	
		if (m_dDstEs < 0.00001)
		   m_dDstInd = 1.0;
		else
		   m_dDstInd = 0.0;
	
//		InitDatumVar(eDstEllips);
	}
	
	///////////////////////////////////////////
	// Main Convert Function
	void Conv(double dInX, double dInY, MutableDouble dOutX, MutableDouble dOutY)
	throws GeoException
	{
		MutableDouble dInLon=new MutableDouble(), dInLat=new MutableDouble();
		MutableDouble dOutLon=new MutableDouble(), dOutLat=new MutableDouble();
	
		//MutableDouble dTmX=new MutableDouble(), dTmY=new MutableDouble();
	
		// Convert to Radian Geographic
		if (m_eSrcSystem == GeoSystem.kGeographic)
		{
			D2R(dInX, dInLon);
			D2R(dInY,dInLat);
			//System.out.println(dInLon.getDouble() + "------:----------" + dInLat.getDouble());
		}
		else
		{
			// Geographic calculating
			Tm2Geo(dInX, dInY, dInLon, dInLat);
		}
	
		if (m_eSrcEllips == m_eDstEllips)
		{
			dOutLon = dInLon;
			dOutLat = dInLat;
			//System.out.println(dOutLon.getDouble() + ":yyyyy" + dOutLat.getDouble());
		}
		else 
		{
			// Datum transformation using molodensky function
			DatumTrans(dInLon.getDouble(), dInLat.getDouble(), dOutLon, dOutLat);
			//System.out.println(dOutLon.getDouble() + ":" + dOutLat.getDouble());
		}
	
		// now we should make a output. but it depends on user options
		if (m_eDstSystem == GeoSystem.kGeographic) // if output option is latitude & longitude
		{
			//System.out.println("[if output option is latitude & longitude]  "+dOutLon.getDouble() + ":" + dOutLat.getDouble());
			R2D(dOutLon.getDouble(),dOutX);
			R2D(dOutLat.getDouble(),dOutY);
			
			//System.out.println("[if output option is latitude & longitude]  "+dOutX + ":" + dOutY);
		}
		else // if output option is cartesian systems
	  {
			// TM or UTM calculating
			//System.out.println("[TM or UTM calculating]  "+dOutLon.getDouble() + ":" + dOutLat.getDouble());
			Geo2Tm(dOutLon.getDouble(), dOutLat.getDouble(), dOutX, dOutY);
	
			//dOutX = dTmX;
			//dOutY = dTmY;
			
			//System.out.println("...." + dOutX.getDouble() + ":" + dOutY.getDouble());
	  }
	}
	
	/////////////////////////////////////////////
	// Global Utility Function
	public static final void D2Dms(
		MutableDouble _dInDecimalDegree, 
		MutableInteger _iOutDegree, MutableInteger _iOutMinute, MutableDouble _dOutSecond
	) throws GeoException
	{
		
		double dTmpMinute =0;
		int iOutDegree, iOutMinute;
		double dOutSecond;
		double dInDecimalDegree;
		
		if(_dInDecimalDegree.isValueSet())
		{
			
			throw new GeoException("[D2Dms]dInDecimalDegree is NULL");
		}
		
		dInDecimalDegree = _dInDecimalDegree.getDouble();
		
		iOutDegree = (int)dInDecimalDegree;
		dTmpMinute = (dInDecimalDegree - iOutDegree) * 60.0;
		iOutMinute = (int)dTmpMinute;
		dOutSecond = (dTmpMinute - iOutMinute) * 60.0;
		
		//System.out.println(":"+iOutDegree+":"+dTmpMinute+":"+iOutMinute+":"+dOutSecond);
		
		if ((dOutSecond+0.00001) >= 60.0)
		{
			if (iOutMinute == 59)
			{
				iOutDegree++;
				iOutMinute = 0;
				dOutSecond = 0.0;
			}
			else {
				iOutMinute++;
				dOutSecond = 0.0;
			}
		}
		
		_iOutDegree.setInteger(iOutDegree);
		_iOutMinute.setInteger(iOutMinute);
		_dOutSecond.setDouble(dOutSecond);
	}
	
	
	// Molodensky Datum Transformation function for general datum transformation.
	// Coded by Shin, Sanghee(endofcap@geo.giri.co.kr) 24th Feb, 1999
	// Reference manual : DEFENSE MAPPING AGENCY TECHNICAL MANUAL 8358.1
	// You can read above manual in this home page. http://164.214.2.59/GandG/tm83581/toc.htm
	// Converted to C++ by Jang, Byyng-jin(jangbi@taff.co.kr) 20th Ap
	void DatumTrans(double dInLon, double dInLat, MutableDouble _dOutLon, MutableDouble _dOutLat)
	{
		double dOutLon, dOutLat;
		double dRm, dRn;
		double dDeltaPhi, dDeltaLamda;
	//	double dDeltaH;
	
	//System.out.println(dInLon+"5555555:555555555"+dInLon);
	
		dRm = m_arMajor[m_eSrcEllips.val] * (1.0-m_dEsTemp) / Math.pow(1.0-m_dEsTemp*Math.sin(dInLat)*Math.sin(dInLat), 1.5);
		dRn = m_arMajor[m_eSrcEllips.val] / Math.sqrt(1.0 - m_dEsTemp*Math.sin(dInLat)*Math.sin(dInLat));
	
		dDeltaPhi = 
			(
				(
					(
						(-m_iDeltaX*Math.sin(dInLat)*Math.cos(dInLon) - m_iDeltaY*Math.sin(dInLat)*Math.sin(dInLon)) + m_iDeltaZ*Math.cos(dInLat)
					) + 
						m_dDeltaA*dRn*m_dEsTemp*Math.sin(dInLat)*Math.cos(dInLat)/m_arMajor[m_eSrcEllips.val]
				) + 
						m_dDeltaF*(dRm/m_dTemp+dRn*m_dTemp)*Math.sin(dInLat)*Math.cos(dInLat)
			)/dRm;
		dDeltaLamda = (-m_iDeltaX * Math.sin(dInLon) + m_iDeltaY * Math.cos(dInLon)) / (dRn * Math.cos(dInLat));
	//	dDeltaH = iDeltaX * Math.cos(dInLat) * Math.cos(dInLon) + iDeltaY * Math.cos(dInLat) * Math.Math.sin(dInLon) + iDeltaZ * Math.sin(dInLat) - dDeltaA * m_arMajor[eSrcEllips] / dRn + dDeltaF * dTemp * dRn * Math.sin(dInLat) * Math.sin(dInLat);
	
		dOutLat = dInLat + dDeltaPhi;
		dOutLon = dInLon + dDeltaLamda;
		
		_dOutLon.setDouble(dOutLon);
		_dOutLat.setDouble(dOutLat);
	}
	
	// function for converting longitude, latitude to TM X, Y
	void Geo2Tm(double lon, double lat, MutableDouble _x, MutableDouble _y)
		throws GeoException
	{
		double x, y;
		double delta_lon; // Delta longitude (Given longitude - center longitude)
		double sin_phi, cos_phi; // sin and cos value
		double al, als; // temporary values
		double b, c, t, tq; // temporary values
		double con, n, ml; // cone constant, small m
	
		// LL to TM Forward equations from here
		delta_lon = lon - m_arLonCenter[m_eDstSystem.val];
		sin_phi = Math.sin(lat);
		cos_phi = Math.cos(lat);
	
		if (m_dDstInd != 0) 
		{
			b = cos_phi * Math.sin(delta_lon);
			if ((Math.abs(Math.abs(b) - 1.0)) < 0.0000000001)
			{
				_x = _y = null;
				throw new GeoException("지정하신 점이 무한대로 갑니다");
			}
		}
		else 
		{
			b = 0;
			x = 0.5 * m_arMajor[m_eDstEllips.val] * m_arScaleFactor[m_eDstSystem.val] * Math.log((1.0 + b) / (1.0 - b));
			con = Math.acos(cos_phi * Math.cos(delta_lon) / Math.sqrt(1.0 - b * b));
			if (lat < 0)
			{
			con = -con;
			y = m_arMajor[m_eDstEllips.val] * m_arScaleFactor[m_eDstSystem.val] * (con - m_arLatCenter[m_eDstSystem.val]);
			}
		}
	
		al = cos_phi * delta_lon;
		als = al * al;
		c = m_dDstEsp * cos_phi * cos_phi;
		tq = Math.tan(lat);
		t = tq * tq;
		con = 1.0 - m_dDstEs * sin_phi * sin_phi;
		n = m_arMajor[m_eDstEllips.val] / Math.sqrt(con);
		ml = m_arMajor[m_eDstEllips.val] * mlfn(m_dDstE0, m_dDstE1, m_dDstE2, m_dDstE3, lat);
	
		x = m_arScaleFactor[m_eDstSystem.val] * n * al * (1.0 + als / 6.0 * (1.0 - t + c + als / 20.0 * (5.0 - 18.0 * t + t * t + 72.0 * c - 58.0 * m_dDstEsp))) + m_arFalseEasting[m_eDstSystem.val];
		y = m_arScaleFactor[m_eDstSystem.val] * (ml - m_dDstMl0 + n * tq * (als * (0.5 + als / 24.0 * (5.0 - t + 9.0 * c + 4.0 * c * c + als / 30.0 * (61.0 - 58.0 * t + t * t + 600.0 * c - 330.0 * m_dDstEsp))))) + m_arFalseNorthing[m_eDstSystem.val];
		
		
		
		_x.setDouble(x); 
		_y.setDouble(y);
		
		
		//System.out.println("Geo2Tm"+_x.getDouble()+"::"+_y.getDouble());
		
	}
	
	// function for converting TM X,Y to Longitude and Latitude
	void Tm2Geo(double x, double y, MutableDouble _lon, MutableDouble _lat)
		throws GeoException
	{
		double lon, lat;
		double con; // temporary angles
		double phi; // temporary angles
		double delta_Phi; // difference between longitudes
		long i; // counter variable
		double sin_phi, cos_phi, tan_phi; // sin cos and tangent values
		double c, cs, t, ts, n, r, d, ds; // temporary variables
		double f, h, g, temp; // temporary variables
		
		final long max_iter = 6; // maximun number of iterations
	
		if (m_dSrcInd != 0)
		{
			f = Math.exp(x / (m_arMajor[m_eSrcEllips.val] * m_arScaleFactor[m_eSrcSystem.val]));
			g = 0.5 * (f - 1.0 / f);
			temp = m_arLatCenter[m_eSrcSystem.val] + y / (m_arMajor[m_eSrcEllips.val] * m_arScaleFactor[m_eSrcSystem.val]);
			h = Math.cos(temp);
			con = Math.sqrt((1.0 - h * h) / (1.0 + g * g));
			lat = asinz(con);
	
			if (temp < 0) 
				lat *= -1;
	
			if ((g == 0) && (h == 0))
				lon = m_arLonCenter[m_eSrcSystem.val];
			else
				lon = Math.atan(g / h) + m_arLonCenter[m_eSrcSystem.val];
		}
	
		// TM to LL inverse equations from here
	  
		x -= m_arFalseEasting[m_eSrcSystem.val];
		y -= m_arFalseNorthing[m_eSrcSystem.val];
	
		con = (m_dSrcMl0 + y / m_arScaleFactor[m_eSrcSystem.val]) / m_arMajor[m_eSrcEllips.val];
		phi = con;
	
		i = 0;
		while(true)
		{
			delta_Phi = ((con + m_dSrcE1 * Math.sin(2.0 * phi) - m_dSrcE2 * Math.sin(4.0 * phi) + m_dSrcE3 * Math.sin(6.0 * phi)) / m_dSrcE0) - phi;
			phi = phi + delta_Phi;
			if (Math.abs(delta_Phi) <= EPSLN) break;
	
			if (i >= max_iter) 
				throw new GeoException("Latitude failed to converge");
	
			i++;
		}
	
		if (Math.abs(phi) < (PI / 2))
		{
			sin_phi = Math.sin(phi);
			cos_phi = Math.cos(phi);
			tan_phi = Math.tan(phi);
			c = m_dSrcEsp * cos_phi * cos_phi;
			cs = c * c;
			t = tan_phi * tan_phi;
			ts = t * t;
			con = 1.0 - m_dSrcEs * sin_phi * sin_phi;
			n = m_arMajor[m_eSrcEllips.val] / Math.sqrt(con);
			r = n * (1.0 - m_dSrcEs) / con;
			d = x / (n * m_arScaleFactor[m_eSrcSystem.val]);
			ds = d * d;
			lat = phi - (n * tan_phi * ds / r) * (0.5 - ds / 24.0 * (5.0 + 3.0 * t + 10.0 * c - 4.0 * cs - 9.0 * m_dSrcEsp - ds / 30.0 * (61.0 + 90.0 * t + 298.0 * c + 45.0 * ts - 252.0 * m_dSrcEsp - 3.0 * cs)));
			lon = m_arLonCenter[m_eSrcSystem.val] + (d * (1.0 - ds / 6.0 * (1.0 + 2.0 * t + c - ds / 20.0 * (5.0 - 2.0 * c + 28.0 * t - 3.0 * cs + 8.0 * m_dSrcEsp + 24.0 * ts))) / cos_phi);
		}
		else
		{
			lat = PI*0.5 * Math.sin(y);
			lon = m_arLonCenter[m_eSrcSystem.val];
		}
		
		_lon.setDouble(lon); 
		_lat.setDouble(lat);
	}
	
	//////////////////////////////////////////
	// Internal Value calculation Function
	double e0fn(double x)
	{
		return 1.0 - 0.25 * x * (1.0 + x / 16.0 * (3.0 + 1.25 * x));
	}
	
	double e1fn(double x)
	{
		return 0.375 * x * (1.0 + 0.25 * x * (1.0 + 0.46875 * x));
	}
	
	double e2fn(double x)
	{
		return 0.05859375 * x * x * (1.0 + 0.75 * x);
	}
	
	double e3fn(double x)
	{
		return x * x * x * (35.0 / 3072.0);
	}
	
	double e4fn(double x)
	{
		double con, com;
	
	    con = 1.0 + x;
	    com = 1.0 - x;
	    return Math.sqrt(Math.pow(con, con) * Math.pow(com, com));
	}
	
	double mlfn(double e0, double e1, double e2, double e3, double phi)
	{
		return e0 * phi - e1 * Math.sin(2.0 * phi) + e2 * Math.sin(4.0 * phi) - e3 * Math.sin(6.0 * phi);
	}
	
	double asinz(double value)
	{
	    if (Math.abs(value) > 1.0)
			value = (value>0?1:-1);
	    
	    return Math.asin(value);
	}
	
	void InitDatumVar(GeoEllips eSrcEllips, GeoEllips eDestEllips)
	{
		int iDefFact;
	
		// direction factor for datum transformation
		// eg) 
		//	   Bessel to Bessel would be 0
		//     WGS84 to  Bessel would be 1
		//     BEssel to WGS84 would be -1
		//     GRS80 to  Bessel would be 1
		//     BEssel to GRS80 would be -1
		int tmpSval = (eSrcEllips == GeoEllips.kGRS80 ? 1 : eSrcEllips.val);
		int tmpDval = (eDestEllips == GeoEllips.kGRS80 ? 1 : eDestEllips.val);
		
		//iDefFact = eSrcEllips.val - eDestEllips.val;
		iDefFact = tmpSval - tmpDval;
		
		m_iDeltaX = (int)(iDefFact * X_W2B);
		m_iDeltaY = (int)(iDefFact * Y_W2B);
		m_iDeltaZ = (int)(iDefFact * Z_W2B);
		
		m_dTemp = m_arMinor[eSrcEllips.val] / m_arMajor[eSrcEllips.val];
		//double dF = 1.0 - m_dTemp; // flattening
		m_dEsTemp = 1.0 - m_dTemp * m_dTemp; // e2
	
		m_dDeltaA = m_arMajor[eDestEllips.val] - m_arMajor[eSrcEllips.val]; // output major axis - input major axis
		m_dDeltaF = m_arMinor[eSrcEllips.val] / m_arMajor[eSrcEllips.val] - m_arMinor[eDestEllips.val] / m_arMajor[eDestEllips.val]; // Output Flattening - input flattening
	}
	
}


