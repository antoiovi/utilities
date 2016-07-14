package com.antoiovi.logging;

public interface IOutput {

	public void setLevel(IOutput.Level level);
	
	public void logError(Object msg);
 
	public void logWarning(Object msg);
	 
	public void logInfo(Object msg) ;

	public void logDebug(Object msg);
	 
	public void logTrace(Object msg) ;
	
	public void clear();
	public void setMessage();
	public void appendMessage();
	
	public class Level{
		public static final String ERROR="ERROR";
		public static final String WARNING="WARNING";
		public static final String INFO="INFO";
		public static final String DEBUG="DEBUG";
		public static final String TRACE="TRACE";
	}
}
