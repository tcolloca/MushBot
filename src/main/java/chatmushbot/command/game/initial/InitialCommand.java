package chatmushbot.command.game.initial;

import java.util.List;

import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommand;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.command.util.GameValues;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that has an effect over a Mush Game that hasn't started yet. If the
 * executer of the command is not playing any game, it is notified about it. If
 * the executer has called the command in a private message, then he will he
 * told that it should be called in a channel.
 * 
 * @author Tomas
 */
public abstract class InitialCommand extends ChatMushBotCommand implements
		GameValues {

	InitialCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	/**
	 * Executes the command after validating there is a game associated to the
	 * channel.
	 * 
	 * @param channel
	 *            Channel where the command was executed.
	 */
	abstract void executeCommand(ChatChannel channel);

	@Override
	public final void safeExecute(ChatChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		if (!getChatMushBot().hasGame(channel)) {
			getChatMushBot().send(getExecuter(),
					Messages.getMessage(getLang(), MUSH_NO_GAME));
			return;
		}
		executeCommand(channel);
	}

	@Override
	public final void safeExecute(ChatUser user) {
		getChatMushBot().send(getExecuter(),
				Messages.getMessage(getLang(), COMMAND_CHANNEL_COMMAND));
	}
}
