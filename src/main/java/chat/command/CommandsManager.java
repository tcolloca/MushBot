package chat.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.message.MessagesManager;
import util.message.ResourceBundlesManager;

public class CommandsManager {

	private static String COMMAND = "_command";

	private static Map<String, List<String>> commands;

	static {
		commands = new HashMap<String, List<String>>();
		for (CommandName commandName : CommandName.values()) {
			commands.put(commandName.getName(), new ArrayList<String>());
		}

		for (String lang : ResourceBundlesManager.getAvailableLanguages()) {
			MessagesManager messagesManager = new MessagesManager(lang);
			for (CommandName commandName : CommandName.values()) {
				String commandNameValue = messagesManager.get(commandName
						.getName() + COMMAND);
				commands.get(commandName.getName()).add(
						commandNameValue.toLowerCase());
			}
		}
	}

	public static boolean isCommand(CommandName commandName, String s) {
		return commands.get(commandName.getName()).contains(s.toLowerCase());
	}

	public static List<String> getAvailableCommands(String lang) {
		MessagesManager messagesManager = new MessagesManager(lang);
		List<String> avlCommands = new ArrayList<String>();
		for (CommandName commandName : CommandName.values()) {
			String commandNameValue = messagesManager.get(commandName.getName()
					+ COMMAND);
			avlCommands.add(commandNameValue);
		}
		return avlCommands;
	}
}
