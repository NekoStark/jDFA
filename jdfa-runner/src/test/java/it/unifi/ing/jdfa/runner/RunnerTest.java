package it.unifi.ing.jdfa.runner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.builder.DFABuilder;
import it.unifi.ing.jdfa.ops.AcceptanceOperation;
import it.unifi.ing.jdfa.ops.MinimizeOperation;
import it.unifi.ing.jdfa.runner.exception.DFARunnerException;

public class RunnerTest {

	@Test
	public void testBuildDFA() {
		String[] params = {"def=\"[a, b][01][a-0-b, a-1-a, b-0-a, b-1-b][a][b]\""};
		
		DFA dfa = new DFABuilder()
						.states("a,b")
						.alphabet('0', '1')
						.transition("a", '0', "b")
						.transition("a", '1', "a")
						.transition("b", '0', "a")
						.transition("b", '1', "b")
						.startState("a")
						.acceptingStates("b")
						.get();
		
		assertEquals(dfa, Runner.buildDFA(params));
	}
	
	@Test(expected=DFARunnerException.class)
	public void testBuildDFANoDef() {
		String[] params = {"def=\"[a, b][a-0-b, a-1-a, b-0-a, b-1-b][a][b]\""};
		Runner.buildDFA(params);
	}
	
	@Test
	public void testExtractParam() {
		String[] params = {"strings=\"abc,   def\"", "file=\"def.json\""};
		
		assertEquals("abc,def", Runner.extractParam("strings", params));
		assertEquals("def.json", Runner.extractParam("file", params));
	}
	
	@Test(expected=DFARunnerException.class)
	public void testExtractParamNotFound() {
		String[] params = {"strings=\"abc,   def\"", "file=\"def.json\""};
		Runner.extractParam("ciao", params);
	}
	
	@Test
	public void testGetOperation() {
		String[] params = {"strings=\"abc, def\""};
		assertTrue(Runner.getOperation("acceptance", params) instanceof AcceptanceOperation);
		assertTrue(Runner.getOperation("minimize", params) instanceof MinimizeOperation);
	}
	
	@Test(expected=DFARunnerException.class)
	public void testGetOperationIllegalString() {
		String[] params = {};
		Runner.getOperation("other", params);
	}
	
	@Test
	public void testConstructor() {
		new Runner();
	}
	
}
