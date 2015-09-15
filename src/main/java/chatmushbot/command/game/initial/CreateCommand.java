package chatmushbot.command.game.initial;

import java.util.List;

import util.message.Message;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommand;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that tries to create a Mush Game in the channel where the command was
 * executed. If the executer has called the command in a private message, then
 * he will he told that it should be called in a channel.
 *
 * @author Tomas
 */
public class CreateCommand extends ChatMushBotCommand {

	private static final String HELP_CREATE = "help_create";

	public CreateCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	public final void safeExecute(ChatChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		getChatMushBot().createGame(channel, getExecuter());
	}

	@Override
	public final void safeExecute(ChatUser user) {
		getChatMushBot().send(getExecuter(),
				Messages.getMessage(getLang(), COMMAND_CHANNEL_COMMAND));
	}

	@Override
	protected Message getHelp(ChatUser user) {
		return new Message(HELP_CREATE);
	}
}
