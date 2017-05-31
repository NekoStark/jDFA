package it.unifi.ing.jdfa.ops;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Symbol;
import it.unifi.ing.dfa.model.Transition;
import it.unifi.ing.dfa.model.operation.DFAOperation;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.IndistinguishableStateFinderStrategy;

public class MinimizeOperation implements DFAOperation {

	private IndistinguishableStateFinderStrategy strategy;
	private DFA result;
	
	public MinimizeOperation(IndistinguishableStateFinderStrategy strategy) {
		this.strategy = strategy;
	}
	
	@Override
	public void execute(DFA dfa) {
		//1. remove unreachable states
		UnreachableStatesRemoveOperation op = new UnreachableStatesRemoveOperation();
		dfa.execute(op);
		DFA purged = op.getResult();
		
		//2. create minimized states using a strategy
		Set<Set<State>> partition = strategy.apply(purged);
		
		//3. create new transition for the minimized dfa
		Set<Transition> transitions = partition.stream()
										.map(g -> createTransitions(purged, partition, g))
										.flatMap(Collection::stream)
										.collect(Collectors.toSet());
		
		Set<State> states = partition.stream().map(g -> createState(g)).collect(Collectors.toSet());
		State startState = createState(find(partition, purged.getStartState()));
		Set<State> acceptingStates = purged.getAcceptingStates()
										.stream()
										.map(a -> createState(find(partition, a)))
										.collect(Collectors.toSet());
				
		result = new DFA(states, purged.getAlphabet(), transitions, startState, acceptingStates);
	}
	
	public DFA getResult() {
		return result;
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
	
	@Override
	public String asString() {
		return result == null? "call execute before this method" : result.execute(new FormatAsDigraph()).asString();
	}
	
}
