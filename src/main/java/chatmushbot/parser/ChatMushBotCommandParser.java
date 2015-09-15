package chatmushbot.parser;

import java.util.Arrays;
import java.util.List;

import chat.parser.ChatCommandParser;

/**
 * This class parses the MushBot's Commands. A command is given by the following syntax:
 * !&lt;commandName&gt; [parameterName ...]
 * 
 * @author Tomas
 */
public class ChatMushBotCommandParser implements ChatCommandParser {

	private static String COMMAND_STARTER = "!";
	private static String COMMAND_ARGUMENTS_SEPARATOR = " ";

	@Override
	public boolean isCommand(String s) {
		return s.startsWith(COMMAND_STARTER);
	}

	@Override
	public List<String> parse(String s) {
		String commandList = s.substring(1);
		List<String> args = Arrays.asList(commandList
				.split(COMMAND_ARGUMENTS_SEPARATOR));
		return args;
	}
}
