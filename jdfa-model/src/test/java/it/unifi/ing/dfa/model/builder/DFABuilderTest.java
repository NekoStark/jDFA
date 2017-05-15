package it.unifi.ing.dfa.model.builder;

import static org.junit.Assert.assertEquals;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.State;
import it.unifi.ing.dfa.model.Symbol;
import it.unifi.ing.dfa.model.Transition;

public class DFABuilderTest {

	private DFA dfa;
	
	@Before
	public void setUp() {
		dfa = new DFA(
				set(state("s1"), state("s2")), 
				set(symbol('0'), symbol('1')), 
				set(
					transition(state("s1"), symbol('0'), state("s2")), 
					transition(state("s1"), symbol('1'), state("s1")), 
					transition(state("s2"), symbol('0'), state("s1")), 
					transition(state("s2"), symbol('1'), state("s2"))
				), 
				state("s1"), 
				set(state("s1"))
		)	;
	}
	
	@Test
	public void testBuilder() {
		assertEquals(
				
			new DFABuilder()
				.states("s1", "s2")
				.alphabet('0', '1')
				.transition("s1", '0', "s2")
				.transition("s1", '1', "s1")
				.transition("s2", '0', "s1")
				.transition("s2", '1', "s2")
				.acceptingStates("s1")
				.startState("s1")
				.get(),
				
			dfa
				
		);
		
		assertEquals(
				
			new DFABuilder()
				.states("s1, s2")
				.alphabet('0', '1')
				.transition("s1", '0', "s2")
				.transition("s1", '1', "s1")
				.transition("s2", '0', "s1")
				.transition("s2", '1', "s2")
				.acceptingStates("s1")
				.startState("s1")
				.get(),
				
			dfa	
				
		);
		
		assertEquals(
				
				new DFABuilder()
					.states("s1   ", "s2")
					.alphabet('0', '1')
					.transition("s1", '0', "s2")
					.transition("   s1", '1', "s1  ")
					.transition("s2", '0', "s1")
					.transition("s2", '1', "s2")
					.acceptingStates("  s1")
					.startState("s1   ")
					.get(),
					
				dfa
					
			);
		
	}
	
	//
	// UTIL METHODS
	//
	
	private State state(String name) {
		return new State(name);
	}
	
	private Symbol symbol(Character character) {
		return new Symbol(character);
	}
	
	private Transition transition(State st1, Symbol s, State st2) {
		return new Transition(st1, s, st2);
	}

	@SafeVarargs
	private final <T> Set<T> set(T... values) {
		return Stream.of(values).collect(Collectors.toSet());
	}
}
