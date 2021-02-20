package fr.tse.mobility.exception;

public class LoginOrPasswordIncorrectException extends RuntimeException {

	public LoginOrPasswordIncorrectException() {
		super("Login or Password incorrect");
	}
	
}
