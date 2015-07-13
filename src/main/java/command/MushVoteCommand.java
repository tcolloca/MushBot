package command;

import java.util.List;

import mush.MushGame;
import mush.MushValues;

import org.pircbotx.User;
import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import bot.IrcBot;
import bot.MushBot;

import com.google.common.collect.Lists;

public class MushVoteCommand extends VoteCommand implements MushValues {

	MushVoteCommand(List<String> args) {
		super(args);
	}

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
			} else if (!mushGame.isMush(user)) {
				bot.sendPrivateResourceMessage(user,
						MUSH_MUSH_ACTION_NOT_ALLOWED);
			} else if (!mushGame.isInMushAttackPhase()) {
				bot.sendPrivateResourceMessage(user, MUSH_VOTE_INVALID);
			} else if (!mushGame.canVote(user)) {
				bot.sendPrivateResourceMessage(user, MUSH_VOTE_ALREADY);
			} else if (!mushGame.isVotable(args.get(1))) {
				bot.sendPrivateResourceMessage(user, MUSH_VOTE_UNKNOWN,
						Lists.newArrayList(args.get(1)));
			} else {
				vote(bot, user);
			}

		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		if (args.size() == 1) {
			return new MessagePack(MUSH_VOTE);
		} else {
			return new MessagePack(MUSH_VOTE_NICK, Lists.newArrayList(args
					.get(1)));
		}
	}

	boolean canPerformAction(IrcBot bot, User user) {
		return ((MushBot) bot).getMushGame().isMush(user);
	}

	boolean isCorrectTime(IrcBot bot, User user) {
		return !((MushBot) bot).getMushGame().isInMushAttackPhase();
	}

	void actionCantBePerformed(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(user, MUSH_MUSH_ACTION_NOT_ALLOWED);
	}

	void vote(IrcBot bot, User user) {
		bot.sendPrivateResourceMessage(
				user,
				MUSH_VOTE_VOTE,
				Lists.newArrayList(((MushBot) bot).getMushGame()
						.getVoted(args.get(1)).getNick()));
		((MushBot) bot).mushVote(user, args.get(1));
	}
}
