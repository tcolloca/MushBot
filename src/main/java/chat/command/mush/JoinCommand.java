package chat.command.mush;

import java.util.List;

import util.message.BotMessagesManager;
import util.message.MessagePack;
import bot.Bot;
import chat.Channel;
import chat.MushBot;
import chat.User;

public class JoinCommand extends MushGameStarterCommand {

	public JoinCommand(List<String> args) {
		super(args);
	}

	@Override
	public void execute(Bot bot, User user) {
		MushBot mushBot = (MushBot) bot;
		Channel channel = getChannel(mushBot);
		if (mushBot.canGameBeJoined(channel)) {
			invalidJoin(mushBot, user);
		} else if (mushBot.isPlaying(user)) {
			alreadyJoined(mushBot, user);
		} else {
			join(mushBot, channel, user);
		}
	}

	@Override
	protected MessagePack getHelp(Bot bot, User user) {
		return new MessagePack(HELP_JOIN);
	}

	private void invalidJoin(MushBot mushBot, User user) {
		mushBot.send(user, BotMessagesManager.get(mushBot, MUSH_JOIN_INVALID));
	}

	private void alreadyJoined(MushBot mushBot, User user) {
		mushBot.send(user, BotMessagesManager.get(mushBot, MUSH_JOIN_ALREADY));
	}

	private void join(MushBot mushBot, Channel channel, User user) {
		mushBot.send(channel,
				BotMessagesManager.get(mushBot, MUSH_JOIN_NEW, user.getNick()));
		mushBot.addPlayer(channel, user);
	}
}
