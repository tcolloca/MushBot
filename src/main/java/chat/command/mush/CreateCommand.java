package chat.command.mush;

import java.util.List;

import util.message.BotMessagesManager;
import util.message.MessagePack;
import bot.Bot;
import chat.Channel;
import chat.MushBot;
import chat.User;

public class CreateCommand extends MushGameStarterCommand {

	public CreateCommand(List<String> args) {
		super(args);
	}

	@Override
	public void execute(Bot bot, User user) {
		MushBot mushBot = (MushBot) bot;
		Channel channel = getChannel(mushBot);
		if (mushBot.isGameCreated(user)) {
			alreadyCreated(mushBot, user);
		} else {
			createGame(mushBot, channel);
		}
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		return new MessagePack(HELP_CREATE);
	}

	private void alreadyCreated(MushBot mushBot, User user) {
		mushBot.send(user, BotMessagesManager.get(mushBot, MUSH_CREATE_ALREADY));
	}

	private void createGame(MushBot mushBot, Channel channel) {
		mushBot.send(channel, BotMessagesManager.get(mushBot, MUSH_CREATE_NEW));
		mushBot.createGame(channel);
	}
}
