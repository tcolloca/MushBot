package chat.parser;

import java.util.List;

/**
 * Parser that determines if a String is a command or not. It also parses the
 * command and returns a list of string where the first one is the command name
 * and if there are more then those are the command's parameters.
 * 
 * @author Tomas
 */
public interface ChatCommandParser {

	/**
	 * Checks if the string received has a command syntax. It does not check if
	 * it's a valid command.
	 * 
	 * @param string
	 * @return {@code true} if it's a valid command. {@code false} if it's not.
	 */
	public boolean isCommand(String string);

	/**
	 * Returns a list of string where the first one is the command name, and the
	 * rest the command's parameters.
	 * 
	 * @param string
	 * @return a list of string where the first one is the command name, and the
	 *         rest the command's parameters.
	 * @throws IllegalStateException
	 *             If the string is not a valid command.
	 */
	public List<String> parse(String string);
}
