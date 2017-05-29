package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.IndistinguishableStateFinderStrategy;

public class TableFillingStrategy implements IndistinguishableStateFinderStrategy {

	private Set<Pair> pairs;
	
	public TableFillingStrategy() {
		pairs = new HashSet<>();
	}
	
	@Override
	public Set<Set<State>> apply(DFA dfa) {
		executeTableFilling(dfa);
		
		return TableFillingPairMerger.merge(dfa.getStates(), pairs);
	}
	
	/**
	 * 
	 * @param dfa
	 * @return
	 */
	Set<Pair> executeTableFilling(DFA dfa) {
		initPairs(dfa.getStates());
		
		//base
		dfa.getStates().forEach(s -> markOppositeState(s, dfa));
		
		//step
		Set<Pair> untouched;
		do {
			untouched = getUnmarkedPairs();
			
			untouched.forEach(p -> 
				dfa.getAlphabet().forEach(c -> {
					State r = dfa.getNextState(p.getOne(), c);
					State s = dfa.getNextState( p.getOther(p.getOne()), c);
					
					if(!r.equals(s) && getPair(r, s).isMarked() != null && getPair(r, s).isMarked()) {
						p.mark();
					} else {
						p.touch();
					}
					
				})
			); 
			
		} while(untouched.size() != getUnmarkedPairs().size());

		return pairs;
	}
	
	
	/**
	 * if state is accepting, marks other states that are not accepting
	 * if state is not accepting, marks other states that are accepting
	 */
	private void markOppositeState(State s, DFA dfa) {
		pairs.stream()
			.filter(p -> p.contains(s))
			.forEach(p -> {
				State other = p.getOther(s);
				if( (dfa.getAcceptingStates().contains(s) && !dfa.getAcceptingStates().contains(other)) || 
						!dfa.getAcceptingStates().contains(s) && dfa.getAcceptingStates().contains(other)) {
					p.mark();
				}
			});
	}
	
	/**
	 * search for a specific pair
	 */
	private Pair getPair(State s1, State s2) {
		return this.pairs.stream().filter(p -> p.equals(new Pair(s1, s2))).findFirst().orElse(null);
	}
	
	/**
	 * gets the marked pairs
	 */
	private Set<Pair> getUnmarkedPairs() {
		return this.pairs.stream().filter(p -> p.isMarked() == null || !p.isMarked()).collect(Collectors.toSet());
	}

	/**
	 * creates all possible pairs from dfa states
	 */
	private void initPairs(Set<State> states) {
		for(State a : states) {
			for(State b : states) {
				if(!a.equals(b)) {
					pairs.add( new Pair(a, b) );
				}
				
			}			
		}
	}
	
}
