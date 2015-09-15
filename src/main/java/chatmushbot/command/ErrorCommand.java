package chatmushbot.command;

import java.util.List;

import util.message.Message;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatMushBot;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that is executed when an user tried to execute an invalid command.
 * 
 * @author Tomas
 */
public class ErrorCommand extends ChatMushBotCommand {

	private static final String COMMAND_INVALID = "command_invalid";

	ErrorCommand(ChatMushBot chatMushBot,
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
		getChatMushBot().send(getExecuter(),
				Messages.getMessage(getLang(), COMMAND_INVALID, toString()));
	}

	@Override
	protected Message getHelp(ChatUser user) {
		return new Message(COMMAND_INVALID, toString());
	}
}
