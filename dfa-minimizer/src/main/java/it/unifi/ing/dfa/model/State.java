package it.unifi.ing.dfa.model;

public class State {

	private String name;
	
	public State(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof State))
			return false;
		State other = (State) obj;
		return name.equals(other.name);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
