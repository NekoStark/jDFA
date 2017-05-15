package it.unifi.ing.jdfa.ops.minimizer.strategy;

import java.util.Set;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.jdfa.ops.minimizer.strategy.tablefilling.Pair;

public interface EquivalentStateFinderStrategy {

	Set<Pair> find(DFA dfa);
	
}
