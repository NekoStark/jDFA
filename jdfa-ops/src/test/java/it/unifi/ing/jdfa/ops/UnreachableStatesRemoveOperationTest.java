package it.unifi.ing.jdfa.ops;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.builder.DFABuilder;

public class UnreachableStatesRemoveOperationTest {

	private DFA dfa, purged;
	
	@Test
	public void test() {
		UnreachableStatesRemoveOperation op = new UnreachableStatesRemoveOperation();
		op.execute(dfa);
		DFA result = op.getResult();

		assertTrue(result.equals( purged ));
	}
	
	@Test
	public void testAsString() {
		UnreachableStatesRemoveOperation op = new UnreachableStatesRemoveOperation();
		assertEquals("call execute before this method", op.asString());
		
		op.execute(dfa);
		DFA result = op.getResult();
		assertEquals(result.toString(), op.asString());
	}
	
	@Before
	public void setUp() {
		dfa = new DFABuilder()
				.states("a, b, c, d, e")
				.alphabet('0')
				.transition("a", '0', "b")
				.transition("b", '0', "a")
				.transition("d", '0', "a")
				.transition("c", '0', "d")
				.startState("a")
				.acceptingStates("a")
				.get();
		
		purged = new DFABuilder()
				.states("a, b")
				.alphabet('0')
				.transition("a", '0', "b")
				.transition("b", '0', "a")
				.startState("a")
				.acceptingStates("a")
				.get();
	}
	
}
