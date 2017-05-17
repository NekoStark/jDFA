package it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.State;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling.Pair;

public class PairTest {

	private State s1, s2, s3;
	private Pair pair;
	
	@Before
	public void setUp() {
		s1 = new State("s1");
		s2 = new State("s2");
		s3 = new State("s3");
		pair = new Pair(s1, s2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor() {
		new Pair(s1, s1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullConstructorFirst() {
		new Pair(null, s1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullConstructorSecond() {
		new Pair(s1, null);
	}
	
	@Test
	public void testGetOther() {
		assertEquals(s1, new Pair(s1, s2).getOther(s2));
		assertEquals(s2, new Pair(s1, s2).getOther(s1));
	}
	
	@Test
	public void testAsSet() {
		assertEquals(Stream.of(s1, s2).collect(Collectors.toSet()), new Pair(s1, s2).asSet());
		assertEquals(Stream.of(s1, s2).collect(Collectors.toSet()), new Pair(s2, s1).asSet());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGetOtherInvalid() {
		new Pair(s1, s2).getOther(s3);
	}
	
	@Test
	public void testEquals() {
		assertTrue( pair.equals(pair) );
		assertTrue( pair.equals(new Pair(s1, s2)) );
		assertTrue( pair.equals(new Pair(s1, s2)) );
		assertTrue( pair.equals(new Pair(s2, s1)) );
		
		assertFalse( pair.equals(new Pair(s1, s3)) );
		assertFalse( pair.equals((Pair)null) );
		assertFalse( pair.equals(s1) );
	}
	
	@Test
	public void testHashcode() {
		assertEquals(pair.hashCode(), new Pair(s1, s2).hashCode());
		assertEquals(pair.hashCode(), new Pair(s2, s1).hashCode());
		assertNotEquals(pair.hashCode(), new Pair(s3, s1).hashCode());
	}
	
	@Test
	public void testToString() {
		assertEquals("(s1, s2)", new Pair(s1, s2).toString());
		assertEquals("(s1, s2)", new Pair(s2, s1).toString());
	}
	
}
