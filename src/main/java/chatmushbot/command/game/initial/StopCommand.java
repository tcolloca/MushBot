package chatmushbot.command.game.initial;

import java.util.List;

import mush.game.action.player.StopAction;
import util.message.Message;
import chat.ChatChannel;
import chat.ChatUser;
import chatmushbot.ChatGameUser;
import chatmushbot.ChatMushBot;
import chatmushbot.command.ChatMushBotCommandFactory;
import chatmushbot.security.ChatAuthUser;

public class StopCommand extends InitialCommand {

	private static final String HELP_STOP = "help_stop";

	public StopCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(chatMushBot, commandFactory, commandName, parameters, executer);
	}

	@Override
	protected Message getHelp(ChatUser user) {
		return new Message(HELP_STOP);
	}

	@Override
	public void executeCommand(ChatChannel channel) {
		getChatMushBot().stopGame(channel,
				new StopAction(new ChatGameUser(getExecuter())));
	}
}
