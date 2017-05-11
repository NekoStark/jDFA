package it.unifi.ing.dfa.model;

import java.util.Collections;
import java.util.Set;

public class DFA {

	private Set<State> states;
	private Set<Character> alphabet;
	private Set<Transition> transitions;
	private State startState;
	private Set<State> acceptingStates;

	public DFA(Set<State> states, Set<Character> alphabet, Set<Transition> transitions, State startState,
			Set<State> acceptingStates) {
		verifyStates(states);
		this.states = states;

		verifyAlphabet(alphabet);
		this.alphabet = alphabet;

		verifyTransitions(states, alphabet, transitions);
		this.transitions = transitions;

		verifyStartState(states, startState);
		this.startState = startState;

		verifyAcceptingStates(states, acceptingStates);
		this.acceptingStates = acceptingStates;
	}

	public State getNextState(State from, Character symbol) {
		Transition result = transitions.stream()
				.filter(t -> from.equals( t.getFrom() ) && symbol.equals(t.getSymbol()))
				.findFirst()
				.orElse(null);
		
		return result == null ? null : result.getTo();
	}
	
	//
	// GETTER METHODS
	//

	public Set<State> getStates() {
		return Collections.unmodifiableSet(states);
	}

	public Set<Character> getAlphabet() {
		return Collections.unmodifiableSet(alphabet);
	}

	public Set<Transition> getTransitions() {
		return Collections.unmodifiableSet(transitions);
	}
	
	public State getStartState() {
		return startState;
	}

	public Set<State> getAcceptingStates() {
		return Collections.unmodifiableSet(acceptingStates);
	}

	//
	// VERIFY METHODS
	//
	
	static final void verifyStates(Set<State> states) {
		states.forEach(s -> {
			if (s == null) {
				throw new IllegalArgumentException("State is null");
			}
		});
	}
	
	static final void verifyAlphabet(Set<Character> alphabet) {
		alphabet.forEach(s -> {
			if (s == null) {
				throw new IllegalArgumentException("Symbol is null");
			}
		});
	}

	static final void verifyTransitions(Set<State> states, Set<Character> alphabet, Set<Transition> transitions) {
		transitions.forEach(t -> {
			if (t == null) {
				throw new IllegalArgumentException("Transition is null");
			}
			if (!states.contains(t.getFrom()) || !states.contains(t.getTo())) {
				throw new IllegalArgumentException(
						"Transition contains undefined states. Defined states are " + states);
			}
			if (!alphabet.contains(t.getSymbol())) {
				throw new IllegalArgumentException("Transition contains undefined symbol. Alphabet is " + alphabet);
			}
		});
	}

	static final void verifyStartState(Set<State> states, State startState) {
		if (startState == null) {
			throw new IllegalArgumentException("Starting state is null");
		}
		if (!states.contains(startState)) {
			throw new IllegalArgumentException("Starting state is undefined. Defined states are " + states);
		}
	}

	static final void verifyAcceptingStates(Set<State> states, Set<State> acceptingStates) {
		acceptingStates.forEach(s -> {
			if (s == null) {
				throw new IllegalArgumentException("Accepting State is null");
			}
			if (!states.contains(s)) {
				throw new IllegalArgumentException("Accepting State with name " + s.getName() + " already defined");
			}
		});
	}

}
