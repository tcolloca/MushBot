package command;

import java.util.List;

import mush.MushGame;

import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import bot.IrcBot;
import bot.MushBot;

public class StartCommand extends IrcBotCommand {

	StartCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		MushGame mushGame = ((MushBot) bot).getMushGame();
		User user = event.getUser();
		if (mushGame == null) {
			invalidStart(bot, user);
		} else if (!mushGame.isInJoiningPhase()) {
			alreadyStarted(bot, user);
		} else {
			startGame(bot, user);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(HELP_START);
	}

	private void invalidStart(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_START_INVALID);
	}

	private void alreadyStarted(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_START_ALREADY);
	}

	private void startGame(IrcBot bot, User user) {
		bot.sendResourceMessage(MUSH_START_NEW);
		((MushBot) bot).startGame();
	}
}
