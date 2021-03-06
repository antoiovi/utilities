package com.antoiovi.logging;

public class OutputStandard implements IOutput {

	private boolean error;
	private boolean warning;
	private boolean info;
	private boolean debug;
	private boolean trace;
	
	
	@Override
	public void setLevel(Level level) {
		switch (level){
		case ERROR:
			error=true;
			warning=false;
			info=false;
			debug=false;
			trace=false;
			break;
		case WARNING:
			error=true;
			warning=true;
			info=false;
			debug=false;
			trace=false;
			break;
		case INFO:
		 
			error=true;
			warning=true;
			info=true;
			debug=false;
			trace=false;
			 
 
			break;
		case DEBUG:
			error=true;
			warning=true;
			info=true;
			debug=true;
			trace=false;
			 
			break;
		case TRACE:
			error=true;
			warning=true;
			info=true;
			debug=true;
			trace=true;
			break;
			default:
				error=false;
				warning=false;
				info=false;
				debug=false;
				trace=false;
		}
		 
	}

	@Override
	public void logError(Object msg) {
		if(error)
		{
			System.out.println(msg);
		}
	}

	@Override
	public void logWarning(Object msg) {
		if(warning)
		{
			System.out.println(msg);
		}
	}

	@Override
	public void logInfo(Object msg) {
		if(info)
		{
			System.out.println(msg);
		}
	}

	@Override
	public void logDebug(Object msg) {
		if(debug)
		{
			System.out.println(msg);
		}
	}

	@Override
	public void logTrace(Object msg) {
		if(trace)
		{
			System.out.println(msg);
		}
	}

	@Override
	public void clear() {
 
	}

	@Override
	public void setMessage(Object msg) {
 
	}

	@Override
	public void appendMessage(Object msg) {
 
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public boolean isWarning() {
		return warning;
	}

	public void setWarning(boolean warning) {
		this.warning = warning;
	}

	public boolean isInfo() {
		return info;
	}

	public void setInfo(boolean info) {
		this.info = info;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isTrace() {
		return trace;
	}

	public void setTrace(boolean trace) {
		this.trace = trace;
	}

	
	
}
