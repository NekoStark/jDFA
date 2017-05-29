package it.unifi.ing.jdfa.ops;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Transition;
import it.unifi.ing.dfa.model.operation.DFAOperation;

public class UnreachableStatesRemoveOperation implements DFAOperation {

	private DFA result;
	
	@Override
	public void execute(DFA dfa) {
		Set<State> reachableStates = new HashSet<>();
		Deque<State> explored = new LinkedList<>();
		explored.push(dfa.getStartState());
		
		while(!explored.isEmpty()) {
			State current = explored.pop();
			reachableStates.add(current);
			
			dfa.getAlphabet().forEach(s -> {
				State next = dfa.getNextState(current, s);
				if(reachableStates.add(next)) {
					explored.push(next);
				}
			});
		}
		
		Set<Transition> transitions = dfa.getTransitions().stream()
										.filter(t -> reachableStates.contains(t.getFrom()))
										.collect(Collectors.toSet());
		Set<State> acceptingStates = dfa.getAcceptingStates().stream()
										.filter(s -> reachableStates.contains(s))
										.collect(Collectors.toSet());
		
		result = new DFA(reachableStates, dfa.getAlphabet(), transitions, dfa.getStartState(), acceptingStates);
		
	}
	
	public DFA getResult() {
		return result;
	}

	@Override
	public String asString() {
		return result == null? "call execute before this method" : result.toString();
	}

}
