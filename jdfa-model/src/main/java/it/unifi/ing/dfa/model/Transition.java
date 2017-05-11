package it.unifi.ing.dfa.model;

public class Transition {

	private State from;
	private State to;
	private Character symbol;

	public Transition(State from, Character symbol, State to) {
		this.from = from;
		this.to = to;
		this.symbol = symbol;
	}

	public State getFrom() {
		return from;
	}

	public State getTo() {
		return to;
	}

	public Character getSymbol() {
		return symbol;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + from.hashCode();
		result = prime * result + to.hashCode();
		result = prime * result + symbol.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Transition))
			return false;
		Transition other = (Transition) obj;
		return from.equals(other.from) && 
				to.equals(other.to) && 
				symbol.equals(other.symbol);
	}
	
	@Override
	public String toString() {
		return from.getName() + 
				" -[" + symbol + 
				"]-> " + to.getName();
	}

}
