package it.unifi.ing.jdfa.runner;

import static it.unifi.ing.jdfa.runner.Runner.buildDFA;
import static it.unifi.ing.jdfa.runner.Runner.extractParam;
import static it.unifi.ing.jdfa.runner.Runner.getOperation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import it.unifi.ing.dfa.model.DFA;
import it.unifi.ing.dfa.model.operation.DFAOperation;
import it.unifi.ing.jdfa.ops.FormatAsDigraph;
import it.unifi.ing.jdfa.runner.exception.DFARunnerException;

public class Main {

	private Main() {
	}

	public static void main(String[] args) {
		if(args.length <= 0) {
			throw new DFARunnerException("parameters needed for execution");
		}
		
		// create dfa from definition and execute operation
		DFAOperation op = getOperation(args[0], args);
		DFA dfa = buildDFA(loadTextualDefinition(args));
		String result = dfa.execute(op).asString();
		
		// writes the output
		writeOutput(args, Stream.of(
								"original", dfa.execute(new FormatAsDigraph()).asString(), 
								"result", result
							).collect(Collectors.toList()));
		
		System.exit(0);
	}
	
	private static final String loadTextualDefinition(String[] args) {
		try {
			return extractParam("def", args);
			
		} catch(DFARunnerException e) {
			try {
				return Files
						.readAllLines(Paths.get( extractParam("file", args) ))
						.stream()
						.map(String::trim)
						.collect(Collectors.joining(""));
				
			} catch (IOException ioe) {
				throw new DFARunnerException(ioe.getMessage());
			}
		}
	}
	
	private static final void writeOutput(String[] args, List<String> output) {
		try {
			String outputType = extractParam("output", args);
			if("file".equals(outputType)) {
				Files.write(Paths.get(output.get(0)), output.get(1).getBytes());
				Files.write(Paths.get(output.get(2)), output.get(3).getBytes());
				
			} else if("stdout".equals(outputType)) {
				output.forEach(System.out::println);
				
			} else {
				//TODO change this condition if add new type of output
				throw new DFARunnerException("output of type " + outputType + " not supported");
			}
			
		} catch (IOException ioe) {
			throw new DFARunnerException(ioe.getMessage());
		}
	}
}
