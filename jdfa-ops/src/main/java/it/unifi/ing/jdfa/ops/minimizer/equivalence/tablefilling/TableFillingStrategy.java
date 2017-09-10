package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Symbol;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.IndistinguishableStateFinderStrategy;

public class TableFillingStrategy implements IndistinguishableStateFinderStrategy {

	private Set<Pair> pairs;
	
	public TableFillingStrategy() {
		pairs = new HashSet<>();
	}
	
	@Override
	public Set<Set<State>> apply(DFA dfa) {
		// marks pairs of distinguishable states
		executeTableFilling(dfa);
		
		// merges pairs in set of equivalent states
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
		markOppositeStates(dfa);
		
		//step
		Set<Pair> untouched;
		do {
			System.out.println("=====");
			
			untouched = getUnmarkedPairs();
			untouched.forEach( p -> markState(dfa, p) );
			
		} while(untouched.size() != getUnmarkedPairs().size());

		return pairs;
	}
	
	
	/**
	 * if state is accepting, marks other states that are not accepting.
	 * if state is not accepting, marks other states that are accepting.
	 */
	private void markOppositeStates(DFA dfa) {
		pairs.stream()
			.forEach(p -> {
				State s = p.getOne();
				State other = p.getOther(s);
				if( (dfa.getAcceptingStates().contains(s) && !dfa.getAcceptingStates().contains(other)) || 
						!dfa.getAcceptingStates().contains(s) && dfa.getAcceptingStates().contains(other)) {
					System.out.println( p );
					p.mark();
					
				} else {
					p.touch();
					
				}
			});
	}
	
	/**
	 * takes a pair of states and checks if reading a symbol the transition leads
	 * to a distinguishable pair of states. If that's the case, the new pair is marked
	 */
	private void markState(DFA dfa, Pair p) {
		for(Symbol c : dfa.getAlphabet()) {
			State r = dfa.getNextState( p.getOne(), c );
			State s = dfa.getNextState( p.getOther(p.getOne()), c );
			
			if(!r.equals(s) && getPair(r, s).isMarked()) {
				p.mark();
				System.out.println( p + " for symbol " + c);
				return;
				
			}
		}
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
		return this.pairs.stream().filter(p -> !p.isMarked()).collect(Collectors.toSet());
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
