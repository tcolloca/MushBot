package chat.command.mush;

import java.util.List;

import util.message.BotMessagesManager;
import util.message.MessagePack;
import bot.Bot;
import chat.Channel;
import chat.MushBot;
import chat.User;

public class StartCommand extends MushGameStarterCommand {

	public StartCommand(List<String> args) {
		super(args);
	}

	@Override
	public void execute(Bot bot, User user) {
		MushBot mushBot = (MushBot) bot;
		Channel channel = getChannel(mushBot);
		if (mushBot.canGameBeStarted(channel)) {
			invalidStart(mushBot, user);
		} else if (mushBot.isGameStarted(channel)) {
			alreadyStarted(mushBot, user);
		} else {
			startGame(mushBot, channel);
		}
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		return new MessagePack(HELP_START);
	}

	private void invalidStart(MushBot mushBot, User user) {
		mushBot.send(user, BotMessagesManager.get(mushBot, MUSH_START_INVALID));
	}

	private void alreadyStarted(MushBot mushBot, User user) {
		mushBot.send(user, BotMessagesManager.get(mushBot, MUSH_START_ALREADY));
	}

	private void startGame(MushBot mushBot, Channel channel) {
		mushBot.send(channel, BotMessagesManager.get(mushBot, MUSH_START_NEW));
		mushBot.startGame(channel);
	}
}
