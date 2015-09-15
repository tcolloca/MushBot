package chatmushbot.command.game.initial;

import java.util.List;

import mush.game.action.player.JoinAction;
import util.message.Message;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatGameUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that joins the executer of the command to the Mush Game running in
 * the channel where it was executed.
 * 
 * @author Tomas
 */
public class JoinCommand extends InitialCommand {

	private static final String HELP_JOIN = "help_join";

	public JoinCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	protected Message getHelp(ChatUser user) {
		return new Message(HELP_JOIN);
	}

	@Override
	void executeCommand(ChatChannel channel) {
		getChatMushBot().addPlayer(channel,
				new JoinAction(new ChatGameUser(getExecuter())));
	}
}
