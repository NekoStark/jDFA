package it.unifi.ing.jdfa.ops;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.builder.DFABuilder;

public class AcceptanceOperationTest {

	private DFA dfa;
	
	@Before
	public void setUp() {
		dfa = new DFABuilder()
					.states("s1", "s2")
					.alphabet('0', '1')
					.transition("s1", '0', "s2")
					.transition("s1", '1', "s1")
					.transition("s2", '0', "s1")
					.transition("s2", '1', "s2")
					.acceptingStates("s1")
					.startState("s1")
					.get();
	}
	
	@Test
	public void testAccepts() {
		AcceptanceOperation op = new AcceptanceOperation(dfa);
		assertTrue(op.verify("00100"));
		assertFalse(op.verify("01100"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptsIllegalSymbols() {
		assertTrue(new AcceptanceOperation(dfa).verify("001A0"));
	}

}
