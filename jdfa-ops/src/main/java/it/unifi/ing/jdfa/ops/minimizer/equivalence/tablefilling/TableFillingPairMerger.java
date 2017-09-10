package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import static java.util.stream.Collectors.toSet;

import java.util.Collections;
import java.util.Set;

import it.unifi.ing.dfa.model.State;

public class TableFillingPairMerger {

	TableFillingPairMerger() { 
	}
	
	static Set<Set<State>> merge(Set<State> dfaStates, Set<Pair> pairs) {
		// filters the equivalent pairs of states
		Set<Pair> filtered = pairs.stream()
								.filter(p -> !p.isMarked())
								.collect( toSet() );
		
		// merges the pairs together
		return dfaStates.stream()
				.map(s -> groupBy(s, filtered))
				.collect( toSet() );
	}
	
	/**
	 * given a state, merges all the pairs that contains that state in a set,
	 * or returns a singleton set with the state if no pair contains it
	 */
	private static Set<State> groupBy(State state, Set<Pair> pairs) {
		Set<State> result = pairs.stream()
				.filter(p -> p.contains(state))
				.flatMap(p -> p.asSet().stream() )
				.collect( toSet() );
		
		return result.isEmpty() ? Collections.singleton(state) : result;
		
	}
	
}
