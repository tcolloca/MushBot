package chatmushbot.command.game.player;

import java.util.List;

import mush.game.action.GameAction;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommand;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.command.util.GameValues;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that has an effect over a Mush Game. If the executer of the command
 * is not playing any game, it is notified about it.
 * 
 * @author Tomas
 */
public abstract class GameCommand extends ChatMushBotCommand implements
		GameValues {

	protected GameCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	/**
	 * Returns the corresponding {@link GameAction} associated to the command.
	 * 
	 * @param user
	 *            User that performed the action.
	 * @return the corresponding GameAction associated to the command.
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	public abstract GameAction getAction(ChatUser user);

	@Override
	public final void safeExecute(ChatChannel channel) {
		execute(getExecuter());
	}

	@Override
	public final void safeExecute(ChatUser user) {
		user = getExecuter();
		if (!getChatMushBot().hasGame(user)) {
			getChatMushBot().send(user,
					Messages.getMessage(getLang(), MUSH_NOT_PLAYING_GAME));
			return;
		}
		getChatMushBot().performAction(user, getAction(user));
	}

}
