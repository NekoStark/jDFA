package it.unifi.ing.dfa.model;

public class Symbol {

	private Character character;
	
	public Symbol(Character name) {
		this.character = name;
	}
	
	@Override
	public int hashCode() {
		return character.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Symbol))
			return false;
		Symbol other = (Symbol) obj;
		return character.equals(other.character);
	}
	
	@Override
	public String toString() {
		return character.toString();
	}
}
