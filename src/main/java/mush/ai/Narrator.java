package mush.ai;

import mush.MushValues;
import mush.Tripulation;

import org.pircbotx.User;

import util.StringConverter;
import bot.IrcBot;

import com.google.common.collect.Lists;

public class Narrator implements MushValues {

	private IrcBot bot;

	public Narrator(IrcBot bot) {
		this.bot = bot;
	}

	public void announceGameAlreadyCreated(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_CREATE_ALREADY);
	}

	public void announceNewGameCreated() {
		bot.sendResourceMessage(MUSH_CREATE_NEW);
	}

	public void announceInvalidJoining(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_JOIN_INVALID);
	}

	public void annouceAlreadyJoined(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_JOIN_ALREADY);
	}

	public void announcePlayerJoins(User user) {
		bot.sendResourceMessage(MUSH_JOIN_NEW,
				Lists.newArrayList(user.getNick()));
	}

	public void announceInvalidStart(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_START_INVALID);
	}

	public void announceGameAlreadyStarted(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_START_ALREADY);
	}

	public void announceRequiredPlayers(int requiredPlayers, int minMush) {
		bot.sendResourceMessage(
				MUSH_REQUIRED_PLAYERS,
				Lists.newArrayList(String.valueOf(requiredPlayers),
						String.valueOf(minMush)));
	}

	public void announceGameStarts() {
		bot.sendResourceMessage(MUSH_START_NEW);
	}

	public void announceTripulation(Tripulation tripulation) {
		bot.sendResourceMessage(MUSH_PLAYERS_AMOUNT, Lists.newArrayList(String
				.valueOf(tripulation.getPlayersAmount())));
		String mushAmountMessage = MUSH_MUSH_AMOUNT;
		if (tripulation.getMushAmount() == 1) {
			mushAmountMessage = MUSH_ONLY_ONE_MUSH;
		}
		bot.sendResourceMessage(mushAmountMessage,
				Lists.newArrayList(String.valueOf(tripulation.getMushAmount())));
		for (User user : tripulation.getMushUsers()) {
			announceUserHeIsMush(user);
		}
	}
	
	private void announceUserHeIsMush(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_USER_IS_MUSH);
	}

	public void announceMushAttack(Tripulation tripulation) {
		bot.sendResourceMessage(MUSH_MUSH_ATTACK_TIME);
		for (User user : tripulation.getMushUsers()) {
			announceMushAttack(user, tripulation);
		}
	}
	
	private void announceMushAttack(User user, Tripulation tripulation) {
		bot.sendPrivateResourceMessage(user, MUSH_MUSH_ATTACK, Lists.newArrayList(StringConverter.stringfyList(tripulation.getHumans())));
	}

	public void announceNotAllowedGameAction(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_GAME_ACTION_NOT_ALLOWED);
	}

	public void announceNotAllowedMushAction(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_MUSH_ACTION_NOT_ALLOWED);
	}

	public void announceNotMushAttackTime(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_VOTE_INVALID);
	}

	public void announceAlreadyVoted(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_VOTE_ALREADY);
	}

	public void announceUknownVote(User user, String string) {
		bot.sendPrivateResourceMessage(user, MUSH_VOTE_UNKNOWN, Lists.newArrayList(string));
	}

	public void announceVote(User user, User voted) {
		bot.sendPrivateResourceMessage(user, MUSH_VOTE_VOTE, Lists.newArrayList(voted.getNick()));
	}

	public void announceVoteResult(User mostVotedUser) {
		bot.sendResourceMessage(MUSH_VOTE_RESULT, Lists.newArrayList(mostVotedUser.getNick()));
	}
}
