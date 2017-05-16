package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import it.unifi.ing.dfa.model.State;

public class TableFillingPairMerger {

	TableFillingPairMerger() { 
	}
	
	static Set<Set<State>> merge(Set<State> dfaStates, Set<Pair> pairs) {
		Set<Pair> filtered = pairs.stream()
								.filter(p -> !p.isMarked())
								.collect(Collectors.toSet());
		
		return dfaStates.stream()
				.map(s -> groupBy(s, filtered))
				.collect(Collectors.toSet());
	}
	
	private static Set<State> groupBy(State state, Set<Pair> pairs) {
		Set<State> result = pairs.stream()
				.filter(p -> p.contains(state))
				.map(Pair::asSet)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
		
		return result.isEmpty() ? Collections.singleton(state) : result;
		
	}
	
}
