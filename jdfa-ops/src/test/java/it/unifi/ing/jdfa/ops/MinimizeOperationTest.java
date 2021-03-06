package it.unifi.ing.jdfa.ops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.builder.DFABuilder;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling.TableFillingStrategy;

public class MinimizeOperationTest {

	private DFA dfa, minimized;
	
	@Test
	public void test() {
		MinimizeOperation op = new MinimizeOperation(new TableFillingStrategy());
		op.execute(dfa);
		DFA result = op.getResult();

		assertTrue(result.equals( minimized ));
	}
	
	@Test
	public void testAsString() {
		MinimizeOperation op = new MinimizeOperation(new TableFillingStrategy());
		assertEquals("call execute before this method", op.asString());
		
		op.execute(dfa);
		assertEquals(op.getResult().execute(new FormatAsDigraph()).asString(), op.asString());
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
		
		minimized = new DFABuilder()
				.states("ab, cde, f")
				.alphabet('0', '1')
				.transition("ab", '0', "ab")
				.transition("ab", '1', "cde")
				.transition("cde", '0', "cde")
				.transition("cde", '1', "f")
				.transition("f", '0', "f")
				.transition("f", '1', "f")
				.startState("ab")
				.acceptingStates("cde")
				.get();
	}
	
}
