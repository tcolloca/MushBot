package mush.ai;

import java.util.List;

import mush.MushValues;
import mush.Tripulation;
import mush.player.Player;

import org.pircbotx.Channel;
import org.pircbotx.User;

import util.StringConverter;
import bot.IrcBot;
import bot.MushBot;

import com.google.common.collect.Lists;

public class Narrator implements MushValues {

	private IrcBot bot;
	private Channel mushChannel;

	public Narrator(IrcBot bot) {
		this.bot = bot;
	}

	public void announceRequiredPlayers(int requiredPlayers, int minMush) {
		bot.sendResourceMessage(
				MUSH_REQUIRED_PLAYERS,
				Lists.newArrayList(String.valueOf(requiredPlayers),
						String.valueOf(minMush)));
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
		bot.sendPrivateResourceMessage(user, MUSH_MUSH_ATTACK, Lists
				.newArrayList(StringConverter.stringfyList(tripulation
						.getHumans())));
	}

	public void announceNotAllowedGameAction(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_GAME_ACTION_NOT_ALLOWED);
	}

	public void announceNotAllowedMushAction(User user) {
		bot.sendPrivateResourceMessage(user, MUSH_MUSH_ACTION_NOT_ALLOWED);
	}

	public void announceMushVoteResult(User mostVotedUser) {
		announceVoteResult(mushChannel, mostVotedUser);
	}

	public void announceVoteResult(Channel channel, User mostVotedUser) {
		bot.sendResourceMessage(mushChannel, MUSH_VOTE_RESULT,
				Lists.newArrayList(mostVotedUser.getNick()));
	}

	public void announceAction(String key, List<String> args) {
		bot.sendResourceMessage(key, args);
	}

	public void announceDeath(Player player) {
		bot.sendResourceMessage(MUSH_PLAYER_DEAD,
				Lists.newArrayList(player.getNick()));
		List<String> roles = player.getRoleNames();
		for (String role : roles) {
			bot.sendResourceMessage(
					MUSH_PLAYER_ROLE,
					Lists.newArrayList(player.getNick(),
							((MushBot) bot).getMessage(role)));
		}
	}

	public void setMushChannel(Channel mushChannel) {
		this.mushChannel = mushChannel;
	}
}
