package it.unifi.ing.dfa.model.exception;

public class IllegalTransitionDefinitionException extends RuntimeException {

	private static final long serialVersionUID = 486633443640532180L;

	public IllegalTransitionDefinitionException(String message) {
		super(message);
	}

}
