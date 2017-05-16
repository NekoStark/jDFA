package it.unifi.ing.jdfa.ops.minimizer;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Symbol;
import it.unifi.ing.dfa.model.Transition;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.UndistinguishableStateFinderStrategy;

public class Minimizer {

	public DFA execute(DFA dfa, UndistinguishableStateFinderStrategy strategy) {
		//TODO: 1. remove unreachable states

		//2. create minimized states using a strategy
		Set<Set<State>> partition = strategy.apply(dfa);
		
		//3. create new transition for the minimized dfa
		Set<Transition> transitions = partition.stream()
										.map(g -> createTransitions(dfa, partition, g))
										.flatMap(Collection::stream)
										.collect(Collectors.toSet());
		
		Set<State> states = partition.stream().map(g -> createState(g)).collect(Collectors.toSet());
		State startState = createState(find(partition, dfa.getStartState()));
		Set<State> acceptingStates = dfa.getAcceptingStates().stream().map(a -> createState(find(partition, a))).collect(Collectors.toSet());
				
		return new DFA(states, dfa.getAlphabet(), transitions, startState, acceptingStates);
	}
	
	
	private Set<Transition> createTransitions(DFA dfa, Set<Set<State>> partition, Set<State> group) {
		State stateInGroup = group.iterator().next();
		
		return dfa.getAlphabet().stream()
					.map(symbol -> createTransition(
										createState(group), 
										dfa.getNextState(stateInGroup, symbol), 
										symbol, 
										partition))
					.collect(Collectors.toSet());
				
	}
	
	private Transition createTransition(State from, State originalTo, Symbol symbol, Set<Set<State>> partition) {
		return new Transition(from, symbol, createState(find(partition, originalTo)));
		
	}
	
	private State createState(Set<State> group) {
		return new State(group.stream()
				.map(State::getName)
				.sorted()
				.collect(Collectors.joining("")));
	}
	
	private Set<State> find(Set<Set<State>> partition, State state) {
		return partition.stream()
					.filter(s -> s.contains(state))
					.findAny()
					.get();
	}
	
}
