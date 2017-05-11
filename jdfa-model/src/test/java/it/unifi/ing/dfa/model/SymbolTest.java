package it.unifi.ing.dfa.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SymbolTest {

	private Symbol symbol;
	
	@Before
	public void setUp() {
		symbol = new Symbol('0');
	}
	
	@Test
	public void testEquals() {
		assertTrue(symbol.equals(new Symbol('0')));

		assertFalse(symbol.equals(new Symbol('1')));
		assertFalse(symbol.equals(null));
		assertFalse(symbol.equals(new State("S1")));
	}
	
	@Test
	public void testToString() {
		assertEquals("0", symbol.toString());
	}

}
