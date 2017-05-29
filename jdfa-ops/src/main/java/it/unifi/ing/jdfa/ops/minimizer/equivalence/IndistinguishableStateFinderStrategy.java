package it.unifi.ing.jdfa.ops.minimizer.equivalence;

import java.util.Set;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;

@FunctionalInterface
public interface IndistinguishableStateFinderStrategy {

	/**
	 * called on a dfa, returns a set of undistinguishable states sets
	 * @param dfa
	 * @return
	 */
	Set<Set<State>> apply(DFA dfa);
	
}
