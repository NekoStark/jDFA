package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.builder.DFABuilder;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling.Pair;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling.TableFillingStrategy;

public class TableFillingStrategyTest {

	private DFA dfa;
	
	@Test
	public void test() {
		Set<Pair> result = new TableFillingStrategy().executeTableFilling(dfa);
		
		assertEquals(15, result.size());
		
		// accepting states (c, d, e) are never equivalent with not-accepting states
		assertNotEquivalentPair(result, "a", "c");
		assertNotEquivalentPair(result, "a", "d");
		assertNotEquivalentPair(result, "a", "e");
		assertNotEquivalentPair(result, "b", "c");
		assertNotEquivalentPair(result, "b", "d");
		assertNotEquivalentPair(result, "b", "e");
		assertNotEquivalentPair(result, "f", "c");
		assertNotEquivalentPair(result, "f", "d");
		assertNotEquivalentPair(result, "f", "e");
		
		// other states are computed by the table-filling algorithm
		assertEquivalentPair(result, "a", "b");
		assertEquivalentPair(result, "c", "d");
		assertEquivalentPair(result, "c", "e");
		assertEquivalentPair(result, "d", "e");
		
		assertNotEquivalentPair(result, "a", "f");
		assertNotEquivalentPair(result, "b", "f");
	}
	
	//
	// ASSERT & UTILS METHODS
	//
	
	private void assertEquivalentPair(Set<Pair> result, String s1, String s2) {
		Pair resultPair = filterResult(result, s1, s2);
		assertFalse(resultPair.isMarked());
	}
	
	private void assertNotEquivalentPair(Set<Pair> result, String s1, String s2) {
		Pair resultPair = filterResult(result, s1, s2);
		assertTrue(resultPair.isMarked());
	}

	private Pair filterResult(Set<Pair> result, String s1, String s2) {
		Pair filterPair = new Pair(new State(s1), new State(s2));
		Pair resultPair = result.stream().filter(p -> p.equals(filterPair)).findFirst().get();
		return resultPair;
	}
	
	//
	// INIT
	//
	
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
}
