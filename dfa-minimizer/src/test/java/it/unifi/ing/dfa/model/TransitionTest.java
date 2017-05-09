package it.unifi.ing.dfa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class TransitionTest {

	private State st1, st2;
	private Symbol s;
	private Transition transition;
	
	@Before
	public void setUp() {
		st1 = new State("S1");
		st2 = new State("S2");
		s = new Symbol('0');
		transition = new Transition(st1, s, st2);
	}
	
	@Test
	public void testEquals() {
		assertEquals(new Transition(st1, s, st2), transition);
		
		State st3 = new State("S3");
		assertNotEquals(new Transition(st1, s, st3), transition);
		
		Symbol s2 = new Symbol('1');
		assertNotEquals(new Transition(st1, s2, st2), transition);
	}
	
	@Test
	public void testHashcode() {
		assertEquals(new Transition(st1, s, st2), transition);
		
		State st3 = new State("S3");
		assertNotEquals(new Transition(st1, s, st3), transition);
	}
	
}
