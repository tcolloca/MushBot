package chatmushbot.command.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import util.message.Messages;
import chatmushbot.command.CommandName;
import chatmushbot.command.ParameterName;

/**
 * This class reads the possible commands and parameters from
 * {@link CommandName} and {@link ParameterName} and and loads the possible
 * strings for those according to the resources files. It can later tell if a
 * string matches a CommandName or ParameterName and give all the possible
 * commands.
 * 
 * @author Tomas
 */
public class ChatMushBotCommandManager {

	private static String COMMAND = "_command";
	private static String PARAMETER = "_parameter";

	private static Map<String, List<String>> commands;
	private static Map<String, List<String>> parameters;
	private static Map<String, List<CommandName>> locationMap;

	static {
		commands = new HashMap<String, List<String>>();
		parameters = new HashMap<String, List<String>>();
		for (CommandName commandName : CommandName.values()) {
			commands.put(commandName.getName(), new ArrayList<String>());
		}
		for (ParameterName parameterName : ParameterName.values()) {
			parameters.put(parameterName.getName(), new ArrayList<String>());
		}

		for (String lang : Messages.getAvailableLanguages()) {
			for (CommandName commandName : CommandName.values()) {
				String commandNameValue = Messages.getMessage(lang,
						commandName.getName() + COMMAND);
				commands.get(commandName.getName()).add(
						commandNameValue.toLowerCase());
			}
			for (ParameterName parameterName : ParameterName.values()) {
				String parameterNameValue = Messages.getMessage(lang,
						parameterName.getName() + PARAMETER);
				parameters.get(parameterName.getName()).add(
						parameterNameValue.toLowerCase());
			}
		}

		initializeLocationMap();
	}

	/**
	 * Returns the CommandName associated with the string.
	 * 
	 * @param string
	 * @return the CommandName associated with the string.
	 * @throws IllegalArgumentException
	 *             If string is null.
	 * @throws NoSuchElementException
	 *             If there is no command associated with the string.
	 */
	public static CommandName getCommandName(String string) {
		if (string == null) {
			throw new IllegalArgumentException();
		}
		for (CommandName commandName : CommandName.values()) {
			if (commands.get(commandName.getName()).contains(
					string.toLowerCase())) {
				return commandName;
			}
		}
		throw new NoSuchElementException();
	}

	/**
	 * Returns {@code true} if the given string matches with the commandName.
	 * {@code false} if not.
	 * 
	 * @param commandName
	 * @param string
	 * @return {@code true} if the given string matches with the commandName.
	 *         {@code false} if not.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public static boolean isCommand(CommandName commandName, String string) {
		if (commandName == null || string == null) {
			throw new IllegalArgumentException();
		}
		return commands.get(commandName.getName()).contains(
				string.toLowerCase());
	}

	/**
	 * Returns {@code true} if the given string matches with the parameterName.
	 * {@code false} if not.
	 * 
	 * @param parameterName
	 * @param string
	 * @return {@code true} if the given string matches with the parameterName.
	 *         {@code false} if not.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public static boolean isParameter(ParameterName parameterName, String string) {
		if (parameterName == null || string == null) {
			throw new IllegalArgumentException();
		}
		return parameters.get(parameterName.getName()).contains(
				string.toLowerCase());
	}

	/**
	 * Returns all the available commands in the language that was passed as
	 * argument.
	 * 
	 * @param lang
	 * @return all the available commands in the language that was passed as
	 *         argument.
	 * @throws IllegalArgumentException
	 *             If {@code lang} is null.
	 */
	public static Set<String> getAvailableCommands(String lang) {
		if (lang == null) {
			throw new IllegalArgumentException();
		}
		Set<String> avlCommands = new HashSet<String>();
		for (CommandName commandName : CommandName.values()) {
			String commandNameValue = Messages.getMessage(lang,
					commandName.getName() + COMMAND);
			avlCommands.add(commandNameValue);
		}
		return avlCommands;
	}

	/**
	 * Returns all the commands in that location. An empty list if there are no
	 * commandNames in such location.
	 * 
	 * @param location
	 * @return all the commands in that location.
	 * @throws IllegalArgumentException
	 *             If location is null.
	 */
	public static List<CommandName> getInLocation(String location) {
		if (location == null) {
			throw new IllegalArgumentException();
		}
		if (!locationMap.containsKey(location)) {
			return new ArrayList<CommandName>();
		}
		return locationMap.get(location);
	}

	private static void initializeLocationMap() {
		locationMap = new HashMap<String, List<CommandName>>();
		for (CommandName commandName : CommandName.values()) {
			List<String> locations = getLocations(commandName);
			for (String location : locations) {
				if (!locationMap.containsKey(location)) {
					locationMap.put(location, new ArrayList<CommandName>());
				}
				locationMap.get(location).add(commandName);
			}
		}
	}

	private static List<String> getLocations(CommandName commandName) {
		String location = commandName.getLocation();
		String[] subLocations = location.split("\\.");
		List<String> locations = new ArrayList<String>();
		locations.add("");
		String path = "";
		for (int i = 1; i < subLocations.length; i++) {
			String subLocation = subLocations[i];
			locations.add(path = path + "." + subLocation);
		}
		locations.add(path + "." + commandName.getName());
		return locations;
	}
}
