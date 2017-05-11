package it.unifi.ing.dfa.model;

public class AcceptVerifier {

	private DFA dfa;

	public AcceptVerifier(DFA dfa) {
		this.dfa = dfa;
	}

	boolean verify(char[] string) {
		State current = dfa.getStartState();

		for (Character c : string) {
			current = dfa.getNextState(current, new Symbol(c));
			//TODO: E' possibile che non esista una transizione?
		}

		return dfa.getAcceptingStates().contains(current);
	}

}
