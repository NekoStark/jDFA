package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.ing.dfa.model.State;

public class Pair {

	private State state;
	private State otherState;
	private Boolean marked;
	
	Pair(State s1, State s2) {
		if(s1 == null || s2 == null || s1.equals(s2)) {
			throw new IllegalArgumentException("states must be differents");
		}
		state = s1;
		otherState = s2;
	}
	
	public Boolean isMarked() {
		return marked;
	}
	public void mark() {
		marked = true;
	}
	public void touch() {
		marked = false;
	}
	
	public boolean contains(State state) {
		return this.state.equals(state) || this.otherState.equals(state);
	}
	
	public State getOne() {
		return state;
	}
	
	public State getOther(State state) {
		if(!this.contains(state)) {
			throw new IllegalArgumentException("invalid state: " + state);
		}
		
		return this.state.equals(state) ? otherState : this.state;
	}
	
	public Set<State> asSet() {
		return Stream.of(state, otherState).collect(Collectors.toSet());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state.hashCode() + otherState.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pair))
			return false;
		Pair other = (Pair) obj;
		return (state.equals(other.state) && otherState.equals(other.otherState)) ||
				(otherState.equals(other.state) && state.equals(other.otherState));
	}
	
	@Override
	public String toString() {
		return "("+state+ ", "+otherState+")";
	}
	
}
