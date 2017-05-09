package it.unifi.ing.dfa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class DFATest {

	private Symbol s1, s2;
	private State st1, st2;
	private Transition t1, t2, t3, t4;
	private DFA dfa;
	
	@Before
	public void setUp() {
		st1 = new State("S1");
		st2 = new State("S2");

		s1 = new Symbol('0');
		s2 = new Symbol('1');
		
		t1 = new Transition(st1, s1, st2);
		t2 = new Transition(st1, s2, st1);
		t3 = new Transition(st2, s1, st1);
		t4 = new Transition(st2, s2, st2);
		
		dfa = new DFA();
	}
	
	@Test
	public void testSetStates() {
		dfa.setStates(st1, st2);
		
		assertEquals(2, dfa.getStates().size());
		assertTrue(dfa.getStates().contains(st1));
		assertTrue(dfa.getStates().contains(st2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetDuplicateStates() {
		dfa.setStates(st1, st1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNullStates() {
		dfa.setStates((State)null);
	}
	
	public void testSetSymbols() {
		dfa.setAlphabet(s1, s2);
		
		assertEquals(2, dfa.getAlphabet().size());
		assertTrue(dfa.getAlphabet().contains(s1));
		assertTrue(dfa.getAlphabet().contains(s2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetDuplicateSymbols() {
		dfa.setAlphabet(s1, s1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNullSymbol() {
		dfa.setAlphabet((Symbol)null);
	}
	
	@Test
	public void testSetTransitions() {
		dfa.setStates(st1, st2);
		dfa.setAlphabet(s1, s2);
		dfa.setTransitions(t1, t2, t3, t4);
		
		assertEquals(4, dfa.getTransitions().size());
		assertTrue(dfa.getTransitions().contains(t1));
		assertTrue(dfa.getTransitions().contains(t2));
		assertTrue(dfa.getTransitions().contains(t3));
		assertTrue(dfa.getTransitions().contains(t4));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetDuplicateTransitions() {
		dfa.setStates(st1, st2);
		dfa.setAlphabet(s1, s2);
		dfa.setTransitions(t1, t1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNullTransition() {
		dfa.setTransitions((Transition)null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetTransitionWithUndefinedFromState() {
		dfa.setStates(st1, st2);
		dfa.setAlphabet(s1, s2);
		dfa.setTransitions(new Transition(new State("SX"), s1, st2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetTransitionWithUndefinedToState() {
		dfa.setStates(st1, st2);
		dfa.setAlphabet(s1, s2);
		dfa.setTransitions(new Transition(st1, s1, new State("SX")));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetTransitionWithUndefinedSymbol() {
		dfa.setStates(st1, st2);
		dfa.setAlphabet(s1, s2);
		dfa.setTransitions(new Transition(st1, new Symbol('x'), st2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setIllegalStartState() {
		dfa.setStates(st1, st2);
		dfa.setStartState(new State("SX"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setNullStartState() {
		dfa.setStartState((State)null);
	}
	
	@Test
	public void testSetAcceptingStates() {
		dfa.setAcceptingStates(st2);
		
		assertEquals(1, dfa.getAcceptingStates().size());
		assertTrue(dfa.getAcceptingStates().contains(st2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetIllegalAcceptingStates() {
		dfa.setAcceptingStates(st2, st2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSetNullAcceptingStates() {
		dfa.setAcceptingStates((State)null);
	}
	
	@Test
	public void testConstructor() {
		State[] states = {st1, st2};
		Symbol[] alphabet = {s1, s2};
		Transition[] transitions = {t1, t2, t3, t4};
		State startState = st1;
		State[] acceptingStates = {st2};
		DFA dfa = new DFA(states, alphabet, transitions, startState, acceptingStates);
		
		assertEquals(2, dfa.getStates().size());
		assertTrue(dfa.getStates().contains(st1));
		assertTrue(dfa.getStates().contains(st2));
		
		assertEquals(2, dfa.getAlphabet().size());
		assertTrue(dfa.getAlphabet().contains(s1));
		assertTrue(dfa.getAlphabet().contains(s2));
		
		assertEquals(4, dfa.getTransitions().size());
		assertTrue(dfa.getTransitions().contains(t1));
		assertTrue(dfa.getTransitions().contains(t2));
		assertTrue(dfa.getTransitions().contains(t3));
		assertTrue(dfa.getTransitions().contains(t4));
		
		assertEquals(startState, dfa.getStartState());
		
		assertEquals(1, dfa.getAcceptingStates().size());
		assertTrue(dfa.getAcceptingStates().contains(st2));
	}
	
}
