package chatmushbot.command.game.player;

import java.util.List;

import mush.game.action.GameAction;
import mush.game.action.player.VoteAction;
import util.message.Message;
import chat.ChatUser;
import chatmushbot.ChatGameUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that is used to represent a {@link VoteAction}. It has one parameter
 * that is the user being voted.
 * 
 * @author Tomas
 */
public class VoteCommand extends GameCommand {

	private static final int INDEX_VOTED = 0;

	public VoteCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	public GameAction getAction(ChatUser user) {
		String voted = null;
		if (getParameters().size() > INDEX_VOTED) {
			voted = getParameters().get(INDEX_VOTED);
		}
		return new VoteAction(new ChatGameUser(getExecuter()), voted);
	}

	@Override
	protected Message getHelp(ChatUser user) {
		// TODO Auto-generated method stub
		return null;
	}
}