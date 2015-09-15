package chatmushbot.command.game.player;

import java.util.List;

import mush.game.action.GameAction;
import mush.game.action.player.MushvoteAction;
import util.message.Message;
import chat.ChatUser;
import chatmushbot.ChatGameUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that is used to represent a {@link MushvoteAction}. It has one
 * parameter that is the user being voted.
 * 
 * @author Tomas
 */
public class MushvoteCommand extends GameCommand {

	private static final String MUSH_VOTE = "help_mush_vote";
	private static final String MUSH_VOTE_NICK = "help_mush_vote_nick";

	private static final int INDEX_VOTED = 0;

	public MushvoteCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	protected Message getHelp(ChatUser user) {
		if (getParameters().size() == 0) {
			return new Message(MUSH_VOTE);
		} else {
			return new Message(MUSH_VOTE_NICK, getParameters().get(INDEX_VOTED));
		}
	}

	@Override
	public GameAction getAction(ChatUser user) {
		String voted = null;
		if (getParameters().size() > INDEX_VOTED) {
			voted = getParameters().get(INDEX_VOTED);
		}
		return new MushvoteAction(new ChatGameUser(getExecuter()), voted);
	}
}
