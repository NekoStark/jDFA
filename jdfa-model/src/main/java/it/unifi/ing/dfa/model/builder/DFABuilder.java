package it.unifi.ing.dfa.model.builder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Symbol;
import it.unifi.ing.dfa.model.Transition;

public class DFABuilder {

	private Set<State> states = new HashSet<>();
	private Set<Symbol> alphabet = new HashSet<>();
	private Set<Transition> transitions = new HashSet<>();
	private State startState;
	private Set<State> acceptingStates = new HashSet<>();

	public DFABuilder states(String state) {
		return this.states(state.trim().split(","));
	}

	public DFABuilder states(String... states) {
		Arrays.asList(states).forEach(s -> this.states.add(new State(s.trim())));
		return this;
	}

	public DFABuilder alphabet(Character... symbols) {
		Arrays.asList(symbols).forEach(s -> this.alphabet.add(new Symbol(s)));
		return this;
	}

	public DFABuilder transition(String from, Character symbol, String to) {
		this.transitions.add(new Transition(new State(from.trim()), new Symbol(symbol), new State(to.trim())));
		return this;
	}

	public DFABuilder startState(String state) {
		this.startState = new State(state.trim());
		return this;
	}

	public DFABuilder acceptingStates(String state) {
		return this.acceptingStates(state.trim().split(","));
	}

	public DFABuilder acceptingStates(String... states) {
		Arrays.asList(states).forEach(s -> this.acceptingStates.add(new State(s.trim())));
		return this;
	}

	public DFA get() {
		return new DFA(states, alphabet, transitions, startState, acceptingStates);
	}

}
