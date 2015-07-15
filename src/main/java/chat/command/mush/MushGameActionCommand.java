package chat.command.mush;

import java.util.List;

import mush.MushValues;
import mush.command.ActionCommandType;
import util.message.BotMessagesManager;
import util.message.MessagePack;
import bot.Bot;
import chat.MushBot;
import chat.User;
import chat.command.BotCommand;

public abstract class MushGameActionCommand extends BotCommand implements
		MushValues {

	private ActionCommandType actionCommandType;

	MushGameActionCommand(List<String> args, ActionCommandType actionCommandType) {
		super(args);
		this.actionCommandType = actionCommandType;
	}

	@Override
	public void execute(Bot bot, User user) {
		MushBot mushBot = (MushBot) bot;
		if (!mushBot.isPlaying(user)) {
			gameActioNotAllowed(mushBot, user);
		} else if (!mushBot.canPerformAction(user, user, actionCommandType)) {
			actionCantBePerformed(mushBot, user, actionCommandType);
		} else if (!mushBot.isCorrectTime(user, actionCommandType)) {
			invalidTime(mushBot, user, actionCommandType);
		} else {
			MessagePack pack;
			if ((pack = mushBot.getActionErrors(user, user, actionCommandType, args)) != null) {
				showActionError(mushBot, user, pack);
			} else {
				mushBot.performAction(user, user, actionCommandType, args);
			}
		}
	}
	
	abstract String getRole();

	private void gameActioNotAllowed(MushBot mushBot, User user) {
		mushBot.send(user,
				BotMessagesManager.get(mushBot, MUSH_GAME_ACTION_NOT_ALLOWED));
	}
	
	private void actionCantBePerformed(MushBot mushBot, User user,
			ActionCommandType actionCommandType) {
		mushBot.send(user,
				BotMessagesManager.get(mushBot, MUSH_GAME_ACTION_ROLE_REQUIRED, BotMessagesManager.get(mushBot,getRole())));
	}

	private void invalidTime(MushBot mushBot, User user,
			ActionCommandType actionCommandType) {
		mushBot.send(user,
				BotMessagesManager.get(mushBot, MUSH_GAME_ACTION_INVALID_TIME));
	}

	private void showActionError(MushBot mushBot, User user, MessagePack pack) {
		mushBot.send(user,
				BotMessagesManager.get(mushBot, pack.getKey(), pack.getArgs()));
	}
}
