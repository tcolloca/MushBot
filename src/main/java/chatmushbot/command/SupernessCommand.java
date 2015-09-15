package chatmushbot.command;

import java.util.List;

import util.message.Message;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatMushBot;
import chatmushbot.security.ChatAuthUser;

/**
 * Sends to all channels a message related to Superness.
 * 
 * @author Tomas
 */
public class SupernessCommand extends ChatMushBotCommand {

	private static final String SUPERNESS = "superness";
	private static final String HELP_SUPERNESS = "help_super";

	public SupernessCommand(ChatMushBot chatMushBot,
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
		getChatMushBot().send(Messages.getMessage(getLang(), SUPERNESS));
	}

	@Override
	protected Message getHelp(ChatUser user) {
		return new Message(HELP_SUPERNESS);
	}
}
