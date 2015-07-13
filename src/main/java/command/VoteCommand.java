package command;

import java.util.List;

import mush.MushGame;
import mush.MushValues;

import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import bot.IrcBot;
import bot.MushBot;

import com.google.common.collect.Lists;

public abstract class VoteCommand extends IrcBotCommand implements MushValues {

	VoteCommand(List<String> args) {
		super(args);
	}

	abstract boolean canPerformAction(IrcBot bot, User user);

	abstract boolean isCorrectTime(IrcBot bot, User user);

	abstract void actionCantBePerformed(IrcBot bot, User user);

	abstract void vote(IrcBot bot, User user);

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		if (args.size() <= 1) {
			bot.sendPrivateResourceMessage(event.getUser(), MUSH_VOTE_NO_NICK);
		} else {
			MushGame mushGame = ((MushBot) bot).getMushGame();
			User user = event.getUser();
			if (mushGame == null || !mushGame.isPlaying(user)) {
				bot.sendPrivateResourceMessage(user,
						MUSH_GAME_ACTION_NOT_ALLOWED);
			} else if (!canPerformAction(bot, user)) {
				actionCantBePerformed(bot, user);
			} else if (!isCorrectTime(bot, user)) {
				invalidTime(bot, user);
			} else if (!mushGame.canVote(user)) {
				alreadyVoted(bot, user);
			} else if (!mushGame.isVotable(args.get(1))) {
				unknownVote(bot, user);
			} else {
				vote(bot, user);
			}

		}
	}

	private void invalidTime(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_VOTE_INVALID);
	}

	private void alreadyVoted(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_VOTE_ALREADY);
	}

	private void unknownVote(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_VOTE_UNKNOWN,
				Lists.newArrayList(args.get(1)));
	}
}