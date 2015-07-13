package command;

import java.util.List;

import mush.MushGame;

import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import bot.IrcBot;
import bot.MushBot;

import com.google.common.collect.Lists;

public class JoinCommand extends IrcBotCommand {

	JoinCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		MushGame mushGame = ((MushBot) bot).getMushGame();
		User user = event.getUser();
		if (mushGame == null || !mushGame.isInJoiningPhase()) {
			invalidJoin(bot, user);
		} else if (mushGame.isPlaying(user)) {
			alreadyJoined(bot, user);
		} else {
			join(bot, user);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(HELP_JOIN);
	}

	private void invalidJoin(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_JOIN_INVALID);
	}

	private void alreadyJoined(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_JOIN_ALREADY);
	}

	private void join(IrcBot bot, User user) {
		bot.sendResourceMessage(MUSH_JOIN_NEW,
				Lists.newArrayList(user.getNick()));
		((MushBot) bot).addPlayer(user);
	}
}
