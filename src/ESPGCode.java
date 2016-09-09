package com.tobee.gis.support;

import org.geotools.referencing.CRS;
import org.geotools.referencing.ReferencingFactoryFinder;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CRSFactory;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * 5175=
.append("PROJCS[\"Korean 1985 / Modified Central Belt Jeju\",  ")
.append("	GEOGCS[\"Korean 1985\",  ")
.append("		DATUM[\"Korean Datum 1985\",  ")
.append("			SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]],  ")
.append("			AUTHORITY[\"EPSG\",\"6162\"]],  ")
.append("		PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
.append("		UNIT[\"degree\", 0.017453292519943295],  ")
.append("			AXIS[\"Geodetic longitude\", EAST],  ")
.append("			AXIS[\"Geodetic latitude\", NORTH],  ")
.append("			AUTHORITY[\"EPSG\",\"4162\"]],  ")
.append("		PROJECTION[\"Transverse_Mercator\", AUTHORITY[\"EPSG\",\"9807\"]],  ")
.append("		PARAMETER[\"central_meridian\", 127.00289027777775],  ")
.append("		PARAMETER[\"latitude_of_origin\", 38.0],  ")
.append("		PARAMETER[\"scale_factor\", 1.0],  ")
.append("		PARAMETER[\"false_easting\", 200000.0],  ")
.append("		PARAMETER[\"false_northing\", 550000.0],  ")
.append("		UNIT[\"m\", 1.0],  ")
.append("		AXIS[\"Easting\", EAST], AXIS[\"Northing\", NORTH],  ")
.append("		AUTHORITY[\"EPSG\",\"5175\"]] ")
.toString();
 *
 */

public class ESPGCode {
	public static CoordinateReferenceSystem GRS80; //경위도
	public static CoordinateReferenceSystem GRS80_EAST;
	public static CoordinateReferenceSystem GRS80_WEST;
	public static CoordinateReferenceSystem GRS80_MID;
	public static CoordinateReferenceSystem GRS80_UTM_K;//UTM-K (GRS80)
	public static CoordinateReferenceSystem BESSEL_EAST;
	public static CoordinateReferenceSystem BESSEL_WEST;
	public static CoordinateReferenceSystem BESSEL_MID;
	public static CoordinateReferenceSystem KATEC;
	public static CoordinateReferenceSystem WGS84 ; //경위도
	public static CoordinateReferenceSystem UTM51N ; //UTM51N (WGS84)
	public static CoordinateReferenceSystem UTM52N ; //UTM52N (WGS84)
	
	static CoordinateReferenceSystem creatKatecCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korean_1985_Korea_Central_Belt\", ")
				.append("	GEOGCS[\"GCS_Korean_Datum_1985\", ")
				.append("		DATUM[\"D_Korean_Datum_1985\", ")
				.append("		SPHEROID[\"Bessel_1841\",6377397.155,299.1528128]], ")
				.append("		PRIMEM[\"Greenwich\",0.0], ")
				.append("		UNIT[\"Degree\",0.0174532925199433]], ")
				.append("	PROJECTION[\"Transverse_Mercator\"], ")
				.append("	PARAMETER[\"False_Easting\",400000.0], ")
				.append("	PARAMETER[\"False_Northing\",600000.0], ")
				.append("	PARAMETER[\"Central_Meridian\",128.0], ")
				.append("	PARAMETER[\"Scale_Factor\",0.9999], ")
				.append("	PARAMETER[\"Latitude_Of_Origin\",38.0], ")
				.append("      	UNIT[\"Meter\",1.0]]").toString();

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatGRS80CRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"PCS_ITRF2000_TM\", ")
				.append("	GEOGCS[\"GCS_ITRF_2000\", ")
				.append("		DATUM[\"D_ITRF_2000\", ")
				.append("		SPHEROID[\"GRS 1980\", 6378137.0, 298.257222101,  ")
				.append("		              AUTHORITY[\"EPSG\",\"7019\"]],  ")
				.append("		              AUTHORITY[\"EPSG\",\"6019\"]],  ")
				.append("		              PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]], UNIT[\"degree\", 0.017453292519943295], A ")
				.append("		               XIS[\"Geodetic longitude\", EAST],  ")
				.append("		              AXIS[\"Geodetic latitude\", NORTH], AUTHORITY[\"EPSG\",\"4019\"]]")
				.toString();
				/*		"
				.append("			SPHEROID[\"GRS_1980\",6378137.0,298.257222101]], ")
				.append("		PRIMEM[\"Greenwich\",0.0], ")
				.append("		UNIT[\"Degree\",0.0174532925199433]], ")
				.append("	PROJECTION[\"Transverse_Mercator\"], ")
				.append("	PARAMETER[\"False_Easting\",200000.0], ")
				.append("	PARAMETER[\"False_Northing\",500000.0], ")
				.append("	PARAMETER[\"Central_Meridian\",127.0], ")
				.append("	PARAMETER[\"Scale_Factor\",1.0], ")
				.append("	PARAMETER[\"Latitude_Of_Origin\",38.0], ")
				.append("	UNIT[\"Meter\",1.0]] ")
				.toString();
				*/
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatGRS80EASTCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korea 2000 / East Belt 2010\", ")
				.append("	GEOGCS[\"Korea 2000\",  ")
				.append("		DATUM[\"Geocentric datum of Korea\",  ")
				.append("		SPHEROID[\"GRS 1980\", 6378137.0, 298.257222101, AUTHORITY[\"EPSG\",\"7019\"]],  ")
				.append("		TOWGS84[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], AUTHORITY[\"EPSG\",\"6737\"]],  ")
				.append("	PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("	UNIT[\"degree\", 0.017453292519943295],  ")
				.append("		AXIS[\"Geodetic longitude\", EAST],  ")
				.append("		AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"4737\"]],  ")
				.append("	PROJECTION[\"Transverse_Mercator\", AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("	PARAMETER[\"central_meridian\", 129.0],  ")
				.append("	PARAMETER[\"latitude_of_origin\", 38.0],  ")
				.append("	PARAMETER[\"scale_factor\", 1.0],  ")
				.append("	PARAMETER[\"false_easting\", 200000.0],  ")
				.append("	PARAMETER[\"false_northing\", 600000.0],  ")
				.append("	UNIT[\"m\", 1.0],  ")
				.append("	AXIS[\"Easting\", EAST],  ")
				.append("	AXIS[\"Northing\", NORTH],  ")
				.append("	AUTHORITY[\"EPSG\",\"5187\"]] ")
				.toString();	
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatGRS80WESTCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korea 2000 / West Belt 2010\",  ")
				.append("	GEOGCS[\"Korea 2000\",  ")
				.append("		DATUM[\"Geocentric datum of Korea\",  ")
				.append("		SPHEROID[\"GRS 1980\", 6378137.0, 298.257222101, AUTHORITY[\"EPSG\",\"7019\"]],  ")
				.append("		TOWGS84[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], AUTHORITY[\"EPSG\",\"6737\"]],  ")
				.append("	PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("	UNIT[\"degree\", 0.017453292519943295],  ")
				.append("		AXIS[\"Geodetic longitude\", EAST],  ")
				.append("		AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"4737\"]],  ")
				.append("	PROJECTION[\"Transverse_Mercator\",  ")
				.append("	AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("	PARAMETER[\"central_meridian\", 125.0],  ")
				.append("	PARAMETER[\"latitude_of_origin\", 38.0],  ")
				.append("	PARAMETER[\"scale_factor\", 1.0],  ")
				.append("	PARAMETER[\"false_easting\", 200000.0],  ")
				.append("	PARAMETER[\"false_northing\", 600000.0],  ")
				.append("	UNIT[\"m\", 1.0],  ")
				.append("	AXIS[\"Easting\", EAST],  ")
				.append("	AXIS[\"Northing\", NORTH],  ")
				.append("	AUTHORITY[\"EPSG\",\"5185\"]] ")
				.toString();
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatGRS80MIDCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korea 2000 / Central Belt 2010\",  ")
				.append("	GEOGCS[\"Korea 2000\",  ")
				.append("		DATUM[\"Geocentric datum of Korea\",  ")
				.append("		SPHEROID[\"GRS 1980\", 6378137.0, 298.257222101, AUTHORITY[\"EPSG\",\"7019\"]],  ")
				.append("		TOWGS84[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], AUTHORITY[\"EPSG\",\"6737\"]],  ")
				.append("	PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("	UNIT[\"degree\", 0.017453292519943295],  ")
				.append("		AXIS[\"Geodetic longitude\", EAST],  ")
				.append("		AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"4737\"]],  ")
				.append("	PROJECTION[\"Transverse_Mercator\",  ")
				.append("	AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("	PARAMETER[\"central_meridian\", 127.0],  ")
				.append("	PARAMETER[\"latitude_of_origin\", 38.0],  ")
				.append("	PARAMETER[\"scale_factor\", 1.0],  ")
				.append("	PARAMETER[\"false_easting\", 200000.0],  ")
				.append("	PARAMETER[\"false_northing\", 600000.0],  ")
				.append("	UNIT[\"m\", 1.0],  ")
				.append("	AXIS[\"Easting\", EAST],  ")
				.append("	AXIS[\"Northing\", NORTH],  ")
				.append("	AUTHORITY[\"EPSG\",\"5186\"]] ")
				.toString();
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatGRS80UTM_KCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korea 2000 / Unified CS\",  ")
				.append("	GEOGCS[\"Korea 2000\",  ")
				.append("		DATUM[\"Geocentric datum of Korea\",  ")
				.append("		SPHEROID[\"GRS 1980\", 6378137.0, 298.257222101, AUTHORITY[\"EPSG\",\"7019\"]],  ")
				.append("		TOWGS84[0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0], AUTHORITY[\"EPSG\",\"6737\"]],  ")
				.append("	PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("	UNIT[\"degree\", 0.017453292519943295],  ")
				.append("		AXIS[\"Geodetic longitude\", EAST],  ")
				.append("		AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"4737\"]],  ")
				.append("	PROJECTION[\"Transverse_Mercator\", AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("	PARAMETER[\"central_meridian\", 127.5],  ")
				.append("	PARAMETER[\"latitude_of_origin\", 38.0],  ")
				.append("	PARAMETER[\"scale_factor\", 0.9996],  ")
				.append("	PARAMETER[\"false_easting\", 1000000.0],  ")
				.append("	PARAMETER[\"false_northing\", 2000000.0],  ")
				.append("	UNIT[\"m\", 1.0],  ")
				.append("	AXIS[\"Easting\", EAST],  ")
				.append("	AXIS[\"Northing\", NORTH],  ")
				.append("	AUTHORITY[\"EPSG\",\"5179\"]] ")
				.toString();
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatBESSLEEASTCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korean 1985 / Modified East Belt\",  ")
				.append("	GEOGCS[\"Korean 1985\",  ")
				.append("		DATUM[\"Korean Datum 1985\",  ")
				.append("			SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]],  ")
				.append("			AUTHORITY[\"EPSG\",\"6162\"]],  ")
				.append("		PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("		UNIT[\"degree\", 0.017453292519943295],  ")
				.append("			AXIS[\"Geodetic longitude\", EAST],  ")
				.append("			AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("			AUTHORITY[\"EPSG\",\"4162\"]],  ")
				.append("		PROJECTION[\"Transverse_Mercator\", AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("		PARAMETER[\"central_meridian\", 129.0028902777777],  ")
				.append("		PARAMETER[\"latitude_of_origin\", 38.0], ")
				.append("		PARAMETER[\"scale_factor\", 1.0],  ")
				.append("		PARAMETER[\"false_easting\", 200000.0],  ")
				.append("		PARAMETER[\"false_northing\", 500000.0],  ")
				.append("		UNIT[\"m\", 1.0], AXIS[\"Easting\", EAST],  ")
				.append("		AXIS[\"Northing\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"5176\"]]    ")
				.toString();
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatBESSLEWESTCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korean 1985 / Modified West Belt\",  ")
				.append("	GEOGCS[\"Korean 1985\",  ")
				.append("		DATUM[\"Korean Datum 1985\",  ")
				.append("			SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]],  ")
				.append("			AUTHORITY[\"EPSG\",\"6162\"]],  ")
				.append("		PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("			UNIT[\"degree\", 0.017453292519943295],  ")
				.append("				AXIS[\"Geodetic longitude\", EAST],  ")
				.append("				AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("				AUTHORITY[\"EPSG\",\"4162\"]],  ")
				.append("		PROJECTION[\"Transverse_Mercator\", AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("		PARAMETER[\"central_meridian\", 125.00289027777778],  ")
				.append("		PARAMETER[\"latitude_of_origin\", 38.0],  ")
				.append("		PARAMETER[\"scale_factor\", 1.0],  ")
				.append("		PARAMETER[\"false_easting\", 200000.0],  ")
				.append("		PARAMETER[\"false_northing\", 500000.0],  ")
				.append("		UNIT[\"m\", 1.0],  ")
				.append("		AXIS[\"Easting\", EAST],  ")
				.append("		AXIS[\"Northing\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"5173\"]] ")
				.toString();
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatBESSLEMIDCRSFromWKT() throws Exception {
        // START SNIPPET: crsFromWKT
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt = 
			new StringBuffer()
				.append("PROJCS[\"Korean 1985 / Modified Central Belt\",  ")
				.append("	GEOGCS[\"Korean 1985\",  ")
				.append("		DATUM[\"Korean Datum 1985\",  ")
				.append("			SPHEROID[\"Bessel 1841\", 6377397.155, 299.1528128, AUTHORITY[\"EPSG\",\"7004\"]],  ")
				.append("			AUTHORITY[\"EPSG\",\"6162\"]],  ")
				.append("		PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("		UNIT[\"degree\", 0.017453292519943295],  ")
				.append("			AXIS[\"Geodetic longitude\", EAST],  ")
				.append("			AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("			AUTHORITY[\"EPSG\",\"4162\"]],  ")
				.append("		PROJECTION[\"Transverse_Mercator\", AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("		PARAMETER[\"central_meridian\", 127.00289027777775],  ")
				.append("		PARAMETER[\"latitude_of_origin\", 38.0],  ")
				.append("		PARAMETER[\"scale_factor\", 1.0],  ")
				.append("		PARAMETER[\"false_easting\", 200000.0],  ")
				.append("		PARAMETER[\"false_northing\", 500000.0], UNIT[\"m\", 1.0],  ")
				.append("		AXIS[\"Easting\", EAST],  ")
				.append("		AXIS[\"Northing\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"5174\"]] ")
				.toString();
//GEOGCS["Unknown datum based upon the GRS 1980 ellipsoid",
//DATUM["Not_specified_based_on_GRS_1980_ellipsoid",
//SPHEROID["GRS 1980",6378137,298.257222101,AUTHORITY["EPSG","7019"]],AUTHORITY["EPSG","6019"]],
//PRIMEM["Greenwich",0,AUTHORITY["EPSG","8901"]],UNIT["degree",0.01745329251994328,AUTHORITY["EPSG","9122"]],AUTHORITY["EPSG","4019"]]

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        // END SNIPPET: crsFromWKT
		return crs;
	}
	
	static CoordinateReferenceSystem creatWgs84CRSFromWKT() throws Exception {
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt
			= new StringBuffer()
				.append("GEOGCS[\"WGS 84\", ")
				.append("	DATUM[\"WGS_1984\", ")
				.append("		SPHEROID[\"WGS 84\",6378137,298.257223563, ")
				.append("			AUTHORITY[\"EPSG\",\"7030\"]], ")
				.append("		AUTHORITY[\"EPSG\",\"6326\"]], ")
				.append("	PRIMEM[\"Greenwich\",0, ")
				.append("	AUTHORITY[\"EPSG\",\"8901\"]], ")
				.append("	UNIT[\"degree\",0.01745329251994328, ")
				.append("	AUTHORITY[\"EPSG\",\"9122\"]], ")
				.append("	AUTHORITY[\"EPSG\",\"4326\"]] ")
				.toString();
				



		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        
		return crs;
	}
	
	static CoordinateReferenceSystem creatUTM52NCRSFromWKT() throws Exception {
		CRSFactory crsFactory = ReferencingFactoryFinder.getCRSFactory(null); 
		 
		String wkt
			= new StringBuffer()
				.append("PROJCS[\"WGS 84 / UTM zone 52N\", ")
				.append("	GEOGCS[\"WGS 84\",  ")
				.append("		DATUM[\"World Geodetic System 1984\",  ")
				.append("			SPHEROID[\"WGS 84\", 6378137.0, 298.257223563, AUTHORITY[\"EPSG\",\"7030\"]],  ")
				.append("			AUTHORITY[\"EPSG\",\"6326\"]],  ")
				.append("			PRIMEM[\"Greenwich\", 0.0, AUTHORITY[\"EPSG\",\"8901\"]],  ")
				.append("		UNIT[\"degree\", 0.017453292519943295],  ")
				.append("			AXIS[\"Geodetic longitude\", EAST],  ")
				.append("			AXIS[\"Geodetic latitude\", NORTH],  ")
				.append("			AUTHORITY[\"EPSG\",\"4326\"]],  ")
				.append("		PROJECTION[\"Transverse_Mercator\", AUTHORITY[\"EPSG\",\"9807\"]],  ")
				.append("		PARAMETER[\"central_meridian\", 129.0],  ")
				.append("		PARAMETER[\"latitude_of_origin\", 0.0],  ")
				.append("		PARAMETER[\"scale_factor\", 0.9996],  ")
				.append("		PARAMETER[\"false_easting\", 500000.0],  ")
				.append("		PARAMETER[\"false_northing\", 0.0],  ")
				.append("		UNIT[\"m\", 1.0],  ")
				.append("		AXIS[\"Easting\", EAST],  ")
				.append("		AXIS[\"Northing\", NORTH],  ")
				.append("		AUTHORITY[\"EPSG\",\"32652\"]] ")
				.toString();

		CoordinateReferenceSystem crs = crsFactory.createFromWKT(wkt);
        
		return crs;
	}
	
	
	
	static 
	{
		try {
			GRS80        = CRS.decode("EPSG:4019");//creatGRS80CRSFromWKT();
			GRS80_EAST   = CRS.decode("EPSG:5187");//creatGRS80EASTCRSFromWKT();
			GRS80_WEST   = CRS.decode("EPSG:5185");//creatGRS80WESTCRSFromWKT();
			GRS80_MID    = CRS.decode("EPSG:5186");//creatGRS80MIDCRSFromWKT();
			GRS80_UTM_K  = CRS.decode("EPSG:5179");//creatGRS80UTM_KCRSFromWKT();
			BESSEL_EAST  = CRS.decode("EPSG:5176");//creatBESSLEEASTCRSFromWKT();
			BESSEL_WEST  = CRS.decode("EPSG:5173");//creatBESSLEWESTCRSFromWKT();
			BESSEL_MID   = CRS.decode("EPSG:5174");//creatBESSLEMIDCRSFromWKT();
			WGS84        = CRS.decode("EPSG:4326");//creatWgs84CRSFromWKT();
			UTM51N       = CRS.decode("EPSG:32651");
			UTM52N       = CRS.decode("EPSG:32652");//creatUTM52NCRSFromWKT();
			KATEC        = creatKatecCRSFromWKT();
		} catch (NoSuchAuthorityCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
