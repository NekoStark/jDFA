package it.unifi.ing.dfa.model;

import it.unifi.ing.dfa.model.operation.DFAOperation;

public class MockDFAOperation implements DFAOperation {

	private String result;
	
	@Override
	public void execute(DFA dfa) {
		result = dfa.getAlphabet().toString();
	}

	@Override
	public String asString() {
		return result;
	}

}
