package it.unifi.ing.dfa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
		assertEquals(new State("S1"), state);
		assertNotEquals(new State("S2"), state);
	}
	
}
