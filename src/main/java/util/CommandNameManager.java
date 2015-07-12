package util;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import command.CommandName;

public class CommandNameManager implements MessagesValues {

	private static String COMMAND = "_command";

	private static Map<String, List<String>> commands;

	static {
		commands = new HashMap<String, List<String>>();
		for (CommandName commandName : CommandName.values()) {
			commands.put(commandName.getName(), new ArrayList<String>());
		}

		String[] langs = Locale.getISOLanguages();
		for (String lang : langs) {
			URL rb = ClassLoader.getSystemResource(BUNDLE_PATH + UNDERSCORE
					+ lang + PROPERTIES);
			if (rb != null) {
				ResourceBundle resources = ResourceBundle.getBundle(
						BUNDLE_NAME, new Locale(lang));
				for (CommandName commandName : CommandName.values()) {
					String commandNameValue = resources.getString(commandName
							.getName() + COMMAND);
					commands.get(commandName.getName()).add(commandNameValue.toLowerCase());
				}
			}
		}
	}

	public static void print() {
		for (Entry<String, List<String>> e : commands.entrySet()) {
			System.out.println(e.getKey());
			for (String s : e.getValue()) {
				System.out.print(s);
			}
			System.out.println();
			System.out.println();
		}
	}

	public static boolean isCommand(CommandName commandName, String s) {
		return commands.get(commandName.getName()).contains(s.toLowerCase());
	}

	public static List<String> getAvailableCommands(String lang) {
		ResourceBundle resources = ResourceBundle.getBundle(BUNDLE_NAME,
				new Locale(lang));
		List<String> avlCommands = new ArrayList<String>();
		for (CommandName commandName : CommandName.values()) {
			String commandNameValue = resources.getString(commandName.getName()
					+ COMMAND);
			avlCommands.add(commandNameValue);
		}
		return avlCommands;
	}
}
