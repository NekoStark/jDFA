package it.unifi.ing.jdfa.ops;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Transition;

public class AcceptanceOperationTest {

	private AcceptanceOperation op;
	
	@Before
	public void setUp() {
		State st1 = new State("S1");
		State st2 = new State("S2");

		Character s1 = '0';
		Character s2 = '1';

		op = new AcceptanceOperation(
			new DFA(
					set(st1, st2), 
					set(s1, s2), 
					set(
							new Transition(st1, s1, st2), 
							new Transition(st1, s2, st1), 
							new Transition(st2, s1, st1), 
							new Transition(st2, s2, st2)
						), 
					st1, 
					set(st1))
		);
	}
	
	@Test
	public void testAccepts() {
		assertTrue(op.verify("00100"));
		assertFalse(op.verify("01100"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptsIllegalSymbols() {
		assertTrue(op.verify("001A0"));
	}
	
	//
	// UTIL METHODS
	//

	@SafeVarargs
	private final <T> Set<T> set(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}
}
