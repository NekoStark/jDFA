package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.builder.DFABuilder;

public class TableFillingPairMergerTest {

	private DFA dfa;
	
	@Test
	public void test() {
		Set<Pair> pairs = new TableFillingStrategy().executeTableFilling(dfa);
		
		Set<Set<State>> grouped = new HashSet<>();
		grouped.add( group("a", "b") );
		grouped.add( group("c", "d", "e") );
		grouped.add( group("f") );
		
		assertEquals(grouped, TableFillingPairMerger.merge(dfa.getStates(), pairs));		
	}
	
	@Before
	public void setUp() {
		dfa = new DFABuilder()
				.states("a, b, c, d, e, f")
				.alphabet('0', '1')
				.transition("a", '0', "b")
				.transition("a", '1', "c")
				.transition("b", '0', "a")
				.transition("b", '1', "d")
				.transition("c", '0', "e")
				.transition("c", '1', "f")
				.transition("d", '0', "e")
				.transition("d", '1', "f")
				.transition("e", '0', "e")
				.transition("e", '1', "f")
				.transition("f", '0', "f")
				.transition("f", '1', "f")
				.startState("a")
				.acceptingStates("c, d, e")
				.get();
	}
	
	//
	// UTIL METHODS
	//
	
	private Set<State> group(String... states) {
		return Arrays.asList(states)
					.stream()
					.map(s -> new State(s))
					.collect(Collectors.toSet());
	}
}
