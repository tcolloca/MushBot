package chat.command;

import java.util.ArrayList;
import java.util.List;

import util.StringConverter;
import chat.ChatChannel;
import chat.ChatUser;

/**
 * A Command being sent through a chat. It can be executed at any time.
 * 
 * @author Tomas
 */
public abstract class ChatCommand {

	private String commandName;
	private List<String> parameters;
	private ChatUser executer;

	/**
	 * @param commandName
	 * @param parameters
	 *            It should be null or empty if it has no parameters.
	 * @param executer
	 *            User that executed the command.
	 * @throws IllegalArgumentException
	 *             If any of the received arguments is null.
	 */
	public ChatCommand(String commandName, List<String> parameters,
			ChatUser executer) {
		if (commandName == null || parameters == null || executer == null) {
			throw new IllegalArgumentException();
		}
		this.commandName = commandName;
		this.parameters = parameters != null ? parameters
				: new ArrayList<String>();
		this.executer = executer;
	}

	/**
	 * Executes the command in the given channel.
	 * 
	 * @param channel
	 *            Channel where the command is going to be executed.
	 * @throws IllegalArgumentException
	 *             If {@code channel} is null.
	 */
	public abstract void execute(ChatChannel channel);

	/**
	 * Executes the command for the given user.
	 * 
	 * @param user
	 *            User that will receive the execution of the command privately.
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	public abstract void execute(ChatUser user);

	public String toString() {
		List<String> commandStrings = new ArrayList<String>();
		commandStrings.add(commandName);
		commandStrings.addAll(parameters);
		return StringConverter.stringfyList(commandStrings, " ", "", "");
	}

	public String getCommandName() {
		return commandName;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public ChatUser getExecuter() {
		return executer;
	}
}
