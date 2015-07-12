package parser;

import java.util.Arrays;
import java.util.List;

import command.IrcBotCommand;
import command.CommandFactory;

public class CommandParser {

	public boolean isCommand(String s) {
		return s.startsWith("!");
	}

	public IrcBotCommand parse(String s) {
		String commandList = s.substring(1);
		List<String> args = Arrays.asList(commandList.split(" "));
		String command;
		if (!args.isEmpty()) {
			command = args.get(0);
		} else {
			command = "";
		}
		return CommandFactory.build(command, args);
	}
}
