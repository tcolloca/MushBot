package parser;

import java.util.Arrays;
import java.util.List;

import command.CommandFactory;
import command.IrcBotCommand;

public class CommandParser {

	private static String COMMAND_STARTER = "!";
	private static String COMMAND_ARGUMENTS_SEPARATOR = " ";

	public boolean isCommand(String s) {
		return s.startsWith(COMMAND_STARTER);
	}

	public IrcBotCommand parse(String s) {
		String commandList = s.substring(1);
		List<String> args = Arrays.asList(commandList
				.split(COMMAND_ARGUMENTS_SEPARATOR));
		String command;
		if (!args.isEmpty()) {
			command = args.get(0);
		} else {
			command = "";
		}
		return CommandFactory.build(command, args);
	}
}
