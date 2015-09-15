package chat.command;

import java.util.List;

import chat.ChatUser;

/**
 * Factory that builds the proper {@link ChatCommand} according to it's name and
 * parameters. In case of error an error command should be returned.
 * 
 * @author Tomas
 */
public interface ChatCommandFactory {

	/**
	 * Returns the {@link ChatCommand} associated with the commmandName and
	 * parameters. If there is any error, an error command should be returned.
	 * 
	 * @param commandName
	 * @param parameters
	 *            It should be null or empty if it has no parameters.
	 * @param executer
	 *            User that executed the command.
	 * @return {@link ChatCommand} associated with the commandName. If there is
	 *         any error, an error command should be returned.
	 * @throws IllegalArgumentException
	 *             If {@code commandName} is null.
	 */
	public ChatCommand build(String commandName, List<String> parameters,
			ChatUser executer);
}
