package com.tobee.gis.coordconv;

public class GeoException extends Exception
{
	private String reason;
	private int errorCode;
	
	public GeoException(String reason)
	{
		this.reason = reason;
	}
	
	
	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}
	
	public int getErrorCode()
	{
		return errorCode;
	}
	
	public String getMessage()
	{
		return reason;
	}
	
}