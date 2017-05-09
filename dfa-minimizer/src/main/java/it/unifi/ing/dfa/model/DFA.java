package it.unifi.ing.dfa.model;

import java.util.HashSet;
import java.util.Set;

public class DFA {

	private Set<State> states;
	private Set<Symbol> alphabet;
	private Set<Transition> transitions;
	private State startState;
	private Set<State> acceptingStates;
	
	DFA() {
		states = new HashSet<>();
		alphabet = new HashSet<>();
		transitions = new HashSet<>();
		acceptingStates = new HashSet<>();
	}
	
	public DFA(State[] states, Symbol[] alphabet, Transition[] transitions, State startState,
			State[] acceptingStates) {
		this();
		this.setStates(states);
		this.setAlphabet(alphabet);
		this.setTransitions(transitions);
		this.setStartState(startState);
		this.setAcceptingStates(acceptingStates);
	}

	Set<State> getStates() {
		return states;
	}
	
	void setStates(State... states) {
		for (State state : states) {
			if(state == null) {
				throw new IllegalArgumentException("State is null");
			}
			if (!this.states.add(state)) {
				throw new IllegalArgumentException("State with name " + state.getName() + " already defined");
			}
		}
	}
	
	Set<Symbol> getAlphabet() {
		return alphabet;
	}
	
	void setAlphabet(Symbol...symbols) {
		for (Symbol symbol : symbols) {
			if(symbol == null) {
				throw new IllegalArgumentException("Symbol is null");
			}
			if (!alphabet.add(symbol)) {
				throw new IllegalArgumentException("Symbol " + symbol.getCharacter()+ " already defined");
			}
		}
	}
	
	Set<Transition> getTransitions() {
		return transitions;
	}
	
	void setTransitions(Transition...transitions) {
		for(Transition transition : transitions) {
			if(transition == null) {
				throw new IllegalArgumentException("Transition is null");
			}
			if(!states.contains(transition.getFrom()) || !states.contains(transition.getTo()) ) {
				throw new IllegalArgumentException("Transition contains undefined states. Defined states are " + states);
			}
			if(!alphabet.contains(transition.getSymbol())) {
				throw new IllegalArgumentException("Transition contains undefined symbol. Alphabet is " + alphabet);
			}
			if (!this.transitions.add(transition)) {
				throw new IllegalArgumentException("Transition " + transition + " already defined");
			}
		}
	}
	
	State getStartState() {
		return startState;
	}
	void setStartState(State startState) {
		if(startState == null) {
			throw new IllegalArgumentException("Starting state is null");
		}
		if(!states.contains(startState)) {
			throw new IllegalArgumentException("Starting state is undefined. Defined states are " + states);
		}
		this.startState = startState;
	}

	Set<State> getAcceptingStates() {
		return acceptingStates;
	}
	void setAcceptingStates(State...states) {
		for (State state : states) {
			if(state == null) {
				throw new IllegalArgumentException("Accepting State is null");
			}
			if (!this.acceptingStates.add(state)) {
				throw new IllegalArgumentException("Accepting State with name " + state.getName() + " already defined");
			}
		}
	}
	
}
