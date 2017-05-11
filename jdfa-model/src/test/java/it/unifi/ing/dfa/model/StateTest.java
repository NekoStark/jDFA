package it.unifi.ing.dfa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class StateTest {

	private State state;
	
	@Before
	public void setUp() {
		state = new State("S1");
	}
	
	@Test
	public void testEquals() {
		assertTrue(state.equals(new State("S1")));
		
		assertFalse(state.equals(new State("S2")));
		assertFalse(state.equals(null));
		assertFalse(state.equals(new Character('X')));
		
	}
	
	@Test
	public void testToString() {
		assertEquals("S1", state.toString());
	}
	
}
