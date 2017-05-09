package it.unifi.ing.dfa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
		assertEquals(new Symbol('0'), symbol);
		assertNotEquals(new Symbol('1'), symbol);
	}
	
}
