package it.unifi.ing.dfa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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
		assertTrue( transition.equals(transition) );
		assertTrue( transition.equals(new Transition(st1, s, st2)) );
		assertFalse( transition.equals(new Transition(new State("S3"), s, st2)) );
		assertFalse( transition.equals(new Transition(st1, s, new State("S3"))) );
		assertFalse( transition.equals(new Transition(st1, new Symbol('1'), st2)) );
		assertFalse( transition.equals(null) );
		assertFalse( transition.equals(new Symbol('X')) );
	}
	
	@Test
	public void testHashcode() {
		assertEquals(new Transition(st1, s, st2), transition);
		
		State st3 = new State("S3");
		assertNotEquals(new Transition(st1, s, st3), transition);
	}
	
	@Test
	public void testToString() {
		assertEquals("Transition: S1 -[0]-> S2", transition.toString());
	}
	
}
