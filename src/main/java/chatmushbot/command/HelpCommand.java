package chatmushbot.command;

import java.util.List;
import java.util.Set;

import util.StringConverter;
import util.message.Message;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatMushBot;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that shows a help message for a given command, or how it works if
 * there are no parameters.
 * 
 * @author Tomas
 */
public class HelpCommand extends ChatMushBotCommand {

	private static final String HELP = "help";

	private static final String HELP_HELP = "help_help";
	private static final String HELP_HELP_COMMAND = "help_help_command";

	private static final int HELP_COMMAND_INDEX = 0;

	public HelpCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	public void safeExecute(ChatChannel channel) {
		execute(getExecuter());
	}

	@Override
	public void safeExecute(ChatUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		if (getParameters().isEmpty()) {
			helpCommand(user);
		} else {
			helpCommandWithArguments(user);
		}
	}

	@Override
	protected Message getHelp(ChatUser user) {
		if (getParameters().isEmpty()) {
			return new Message(HELP_HELP);
		} else {
			String helpCommand = StringConverter.stringfyList(getParameters(),
					" ", "", "");
			return new Message(HELP_HELP_COMMAND, helpCommand);
		}
	}

	private void helpCommand(ChatUser user) {
		Set<String> availableCommands = getChatMushBot().getAvailableCommands();
		getChatMushBot().send(
				user,
				Messages.getMessage(getLang(), HELP,
						StringConverter.stringfySet(availableCommands, "\"")));

	}

	private void helpCommandWithArguments(ChatUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		ChatMushBotCommand helpCommand = getCommandFactory().build(
				getParameters().get(HELP_COMMAND_INDEX),
				getParameters().subList(HELP_COMMAND_INDEX + 1,
						getParameters().size()), getExecuter());
		Message message = helpCommand.getHelp(user);
		getChatMushBot().send(user,
				Messages.getMessage(getChatMushBot().getLanguage(), message));
	}
}
