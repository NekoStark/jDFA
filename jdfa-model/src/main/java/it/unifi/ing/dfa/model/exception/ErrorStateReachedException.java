package it.unifi.ing.dfa.model.exception;

public class ErrorStateReachedException extends RuntimeException {

	private static final long serialVersionUID = 2251642715135487347L;

	public ErrorStateReachedException(String message) {
		super(message);
	}
	
}
