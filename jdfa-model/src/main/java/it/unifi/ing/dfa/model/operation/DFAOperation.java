package it.unifi.ing.dfa.model.operation;

import it.unifi.ing.dfa.model.DFA;

public interface DFAOperation {

	void execute(DFA dfa);
	String asString();
}
