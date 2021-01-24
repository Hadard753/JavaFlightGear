package interpreter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Interpreter {

	public static Map<String, Command> cmdsMap = new HashMap<>();
	static Map<String, Double> symTable = new HashMap<>();
	static Map<String, String> varToPath = new HashMap<>();
	static Command cp = new ConditionParser();
	
	public Interpreter() {
		Interpreter.cmdsMap.put("print", new PrintCommand());
		Interpreter.cmdsMap.put("var", new DeclareVarCommand());
		Interpreter.cmdsMap.put("openDataServer", new OpenServerCommand());
		Interpreter.cmdsMap.put("connect", new ConnectCommand());
		Interpreter.cmdsMap.put("if", new ConditionParser());
		Interpreter.cmdsMap.put("while", new ConditionParser());
		Interpreter.cmdsMap.put("sleep", new SleepCommand());
		Interpreter.cmdsMap.put("return", new ReturnCommand());
		Interpreter.cmdsMap.put("disconnect", new DisconnectCommand());
	}
	
	public int interpret(String[] lines) {
		return new Parser().parse(lines);
	}
	
	public static String[] lexer(String fileName) {
		try {
			return Files.lines(Paths.get("./resources/"+fileName)).toArray(String[]::new);
		} catch (IOException e) {}
		return null;
	}


	
	public static void main(String[] args) {
		Interpreter i = new Interpreter();
		i.interpret(lexer("inputFromSimulator.txt"));
	}

}
