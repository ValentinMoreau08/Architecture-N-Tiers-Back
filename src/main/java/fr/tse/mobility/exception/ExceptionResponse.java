package fr.tse.mobility.exception;

import javax.servlet.http.HttpServletRequest;

public class ExceptionResponse {

	private String error;
	private String message;
	private String path;
	
	public ExceptionResponse(String error, String message, String path) {
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public ExceptionResponse(HttpServletRequest req, Exception ex) {
		this.error = ex.getClass().getSimpleName();
		this.message = ex.getMessage();
		this.path = req.getRequestURI();
	}
	
	public String getError() {return error;}
	public void setError(String error) {this.error = error;}
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	public String getPath() {return path;}
	public void setPath(String path) {this.path = path;}
	
}
