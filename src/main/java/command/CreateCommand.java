package command;

import java.util.List;

import mush.MushGame;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import bot.IrcBot;
import bot.MushBot;

public class CreateCommand extends IrcBotCommand {

	CreateCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		MushGame mushGame = ((MushBot) bot).getMushGame();
		if (mushGame != null && mushGame.hasStarted()) {
			alreadyCreated(bot, event);
		} else {
			createGame(bot);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		return new MessagePack(HELP_CREATE);
	}

	@SuppressWarnings("rawtypes")
	private void alreadyCreated(IrcBot bot, GenericMessageEvent event) {
		bot.sendPrivateResourceMessage(event.getUser(), MUSH_CREATE_ALREADY);
	}

	private void createGame(IrcBot bot) {
		bot.sendResourceMessage(MUSH_CREATE_NEW);
		((MushBot) bot).createGame();
	}
}
