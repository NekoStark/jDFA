package it.unifi.ing.dfa.model;

import java.util.Collections;
import java.util.Set;

import it.unifi.ing.dfa.model.exception.ErrorStateReachedException;
import it.unifi.ing.dfa.model.exception.IllegalStateDefinitionException;
import it.unifi.ing.dfa.model.exception.IllegalSymbolDefinitionException;
import it.unifi.ing.dfa.model.exception.IllegalTransitionDefinitionException;
import it.unifi.ing.dfa.model.operation.DFAOperation;

public class DFA {

	private Set<State> states;
	private Set<Symbol> alphabet;
	private Set<Transition> transitions;
	private State startState;
	private Set<State> acceptingStates;

	public DFA(Set<State> states, Set<Symbol> alphabet, Set<Transition> transitions, State startState,
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

	public State getNextState(State from, Symbol symbol) {
		Transition result = transitions.stream()
				.filter(t -> from.equals( t.getFrom() ) && symbol.equals(t.getSymbol()))
				.findFirst()
				.orElse(null);
		
		if(result == null) {
			throw new ErrorStateReachedException("transition from state " + from + 
					"for symbol " + symbol + " not defined");
		}
		
		return result.getTo();
	}
	
	public void execute(DFAOperation op) {
		op.execute(this);
	}
	
	//
	// EQUALS & HASHCODE
	//
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + states.hashCode();
		result = prime * result + alphabet.hashCode();
		result = prime * result + transitions.hashCode();
		result = prime * result + startState.hashCode();
		result = prime * result + acceptingStates.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DFA))
			return false;
		DFA other = (DFA) obj;
		return states.equals(other.getStates()) &&
				alphabet.equals(other.getAlphabet()) &&
				transitions.equals(other.getTransitions()) &&
				startState.equals(other.getStartState()) &&
				acceptingStates.equals(other.getAcceptingStates());
	}
	
	
	//
	// GETTER METHODS
	//

	public Set<State> getStates() {
		return Collections.unmodifiableSet(states);
	}

	public Set<Symbol> getAlphabet() {
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
				throw new IllegalStateDefinitionException("State is null");
			}
		});
	}
	
	static final void verifyAlphabet(Set<Symbol> alphabet) {
		alphabet.forEach(s -> {
			if (s == null) {
				throw new IllegalSymbolDefinitionException("Symbol is null");
			}
		});
	}

	static final void verifyTransitions(Set<State> states, Set<Symbol> alphabet, Set<Transition> transitions) {
		transitions.forEach(t -> {
			if (t == null) {
				throw new IllegalTransitionDefinitionException("Transition is null");
			}
			if (!states.contains(t.getFrom()) || !states.contains(t.getTo())) {
				throw new IllegalTransitionDefinitionException(
						"Transition contains undefined states. Defined states are " + states);
			}
			if (!alphabet.contains(t.getSymbol())) {
				throw new IllegalTransitionDefinitionException("Transition contains undefined symbol. Alphabet is " + alphabet);
			}
		});
	}

	static final void verifyStartState(Set<State> states, State startState) {
		if (startState == null) {
			throw new IllegalStateDefinitionException("Starting state is null");
		}
		if (!states.contains(startState)) {
			throw new IllegalStateDefinitionException("Starting state is undefined. Defined states are " + states);
		}
	}

	static final void verifyAcceptingStates(Set<State> states, Set<State> acceptingStates) {
		acceptingStates.forEach(s -> {
			if (s == null) {
				throw new IllegalStateDefinitionException("Accepting State is null");
			}
			if (!states.contains(s)) {
				throw new IllegalStateDefinitionException("Accepting State with name " + s.getName() + " already defined");
			}
		});
	}

	@Override
	public String toString() {
		StringBuilder expected = new StringBuilder();
		expected.append("<")
				.append(this.getStates()).append(", ")
				.append(this.getAlphabet()).append(", ")
				.append(this.getTransitions()).append(", ")
				.append(this.getStartState()).append(", ")
				.append(this.getAcceptingStates()).append(">");
		
		return expected.toString();
	}
	
}
