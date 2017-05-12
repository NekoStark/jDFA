package it.unifi.ing.dfa.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.exception.ErrorStateReachedException;

public class DFATest {

	private Symbol s1, s2;
	private State st1, st2;
	private Transition t1, t2, t3, t4;
	
	private Set<State> states;
	private Set<Symbol> alphabet;
	private Set<Transition> transitions;
	private Set<State> acceptingStates;
	
	private DFA dfa;
	
	@Before
	public void setUp() {
		//DFA with a binary alphabet, which requires that the input contains an even number of 0s
		st1 = new State("S1");
		st2 = new State("S2");

		s1 = new Symbol('0');
		s2 = new Symbol('1');

		t1 = new Transition(st1, s1, st2);
		t2 = new Transition(st1, s2, st1);
		t3 = new Transition(st2, s1, st1);
		t4 = new Transition(st2, s2, st2);
		
		states = set(st1, st2);
		alphabet = set(s1, s2);
		transitions = set(t1, t2, t3, t4);
		acceptingStates = set(st1);
		dfa = new DFA(states, alphabet, transitions, st1, acceptingStates);
	}
	
	@Test
	public void testEquals() {
		assertTrue(dfa.equals( new DFA(states, alphabet, transitions, st1, acceptingStates) ));
	}
	
	@Test
	public void testHashcode() {
		assertEquals(dfa.hashCode(), 
				new DFA(states, alphabet, transitions, st1, acceptingStates).hashCode());
	}

	@Test
	public void testConstructor() {
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

		assertEquals(st1, dfa.getStartState());

		assertEquals(1, dfa.getAcceptingStates().size());
		assertTrue(dfa.getAcceptingStates().contains(st1));
	}
	
	@Test
	public void testGetNextState() {
		assertEquals(st2, dfa.getNextState(st1, s1));
		assertEquals(st1, dfa.getNextState(st1, s2));
	}
	
	@Test(expected=ErrorStateReachedException.class)
	public void testGetNextStateNotFound() {
		DFA anotherDFA = new DFA(states, alphabet, set(t1, t2, t3), st1, acceptingStates);
		anotherDFA.getNextState(st2, s2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVerifyNullStates() {
		DFA.verifyStates(set(st1, st2, (State) null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVerifyNullSymbol() {
		DFA.verifyAlphabet(set(s1, (Symbol) null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVerifyNullTransition() {
		DFA.verifyTransitions(states, alphabet, set((Transition) null));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransitionWithUndefinedFromState() {
		DFA.verifyTransitions(states, alphabet, set(new Transition(new State("SX"), s1, st2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransitionWithUndefinedToState() {
		DFA.verifyTransitions(states, alphabet, set(new Transition(st1, s1, new State("SX"))));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTransitionWithUndefinedSymbol() {
		DFA.verifyTransitions(states, alphabet, set(new Transition(st1, new Symbol('X'), st2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setNullStartState() {
		DFA.verifyStartState(states, (State)null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setIllegalStartState() {
		DFA.verifyStartState(states, new State("SX"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetIllegalAcceptingStates() {
		DFA.verifyAcceptingStates(states, set(new State("SX")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetNullAcceptingStates() {
		DFA.verifyAcceptingStates(states, set((State)null));
	}

	//
	// UTIL METHODS
	//

	@SafeVarargs
	private final <T> Set<T> set(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}

}
