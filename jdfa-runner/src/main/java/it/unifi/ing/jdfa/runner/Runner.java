package it.unifi.ing.jdfa.runner;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.builder.DFABuilder;
import it.unifi.ing.dfa.model.operation.DFAOperation;
import it.unifi.ing.jdfa.ops.AcceptanceOperation;
import it.unifi.ing.jdfa.ops.MinimizeOperation;
import it.unifi.ing.jdfa.ops.minimizer.equivalence.tablefilling.TableFillingStrategy;
import it.unifi.ing.jdfa.runner.exception.DFARunnerException;

public class Runner {

	Runner() {
	}
	
	static DFA buildDFA(String[] params) {
		String[] def = extractDefinition(extractParam("def", params));
		if(def.length < 5) {
			throw new DFARunnerException("wrong textual definition for dfa in input");
		}
		
		DFABuilder builder = new DFABuilder()
								.states(def[0])
								.alphabet(def[1])
								.startState(def[3])
								.acceptingStates(def[4]);
		
		Stream.of(def[2].trim().split(","))
				.map(s -> s.split("-"))
				.forEach(t -> builder.transition(t[0], t[1].charAt(0), t[2]));
		return builder.get();
	}
	
	private static String[] extractDefinition(String definition) {
		List<String> result = new LinkedList<>();
		Pattern p = Pattern.compile("[\\w*\\s*,?\\-?]+");
		Matcher matcher = p.matcher(definition);
		
		while (matcher.find()) {
			result.add(matcher.group(0).trim());
		}

		return result.toArray(new String[result.size()]);
	}

	static DFAOperation getOperation(String opName, String[] params) {
		if("acceptance".equals(opName)) {
			return new AcceptanceOperation( extractParam("strings", params).split(",") );
		}
		if("minimize".equals(opName)) {
			//TODO extend the constructor with strategy selection based on params
			return new MinimizeOperation( new TableFillingStrategy() );
		}
		
		throw new DFARunnerException("operation with name " + opName + " not found");
	}
	
	static String extractParam(String key, String[] args) {
		return Stream.of(args)
				.filter(s -> s.startsWith(key))
				.findFirst()
				.orElseThrow(() -> new DFARunnerException("strings not found"))
				.replaceAll("\"", "")
				.replaceAll(key, "")
				.replaceAll("=", "")
				.replaceAll("\\s+", "");
	}
	
}
