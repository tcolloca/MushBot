package chatmushbot.command.game.initial;

import java.util.List;

import mush.game.action.player.StartAction;
import util.message.Message;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatGameUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that tries to start the Mush Game associated to the channel where the
 * command was executed.
 * 
 * @author Tomas
 */
public class StartCommand extends InitialCommand {

	private static final String HELP_START = "help_start";

	public StartCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	protected Message getHelp(ChatUser user) {
		return new Message(HELP_START);
	}

	@Override
	public void executeCommand(ChatChannel channel) {
		getChatMushBot().startGame(channel,
				new StartAction(new ChatGameUser(getExecuter())));
	}
}
