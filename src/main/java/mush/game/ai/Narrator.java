package mush.game.ai;

import java.util.ArrayList;
import java.util.List;

import mush.MushValues;
import mush.game.Tripulation;
import mush.game.player.Player;
import util.message.BotMessagesManager;
import bot.Bot;
import chat.Channel;
import chat.User;

public class Narrator implements MushValues {

	private Bot bot;
	private Channel mainChannel;
	private Channel mushChannel;

	public Narrator(Bot bot, Channel mainChannel) {
		this.bot = bot;
		this.mainChannel = mainChannel;
	}

	public void setMushChannel(Channel mushChannel) {
		this.mushChannel = mushChannel;
	}

	public void announceRequiredPlayers(int requiredPlayers, int minMush) {
		bot.send(mainChannel,
				get(MUSH_REQUIRED_PLAYERS, requiredPlayers, minMush));
	}

	public void announceTripulation(Tripulation tripulation) {
		bot.send(mainChannel,
				get(MUSH_PLAYERS_AMOUNT, tripulation.getPlayersAmount()));
		String mushAmountMessage = MUSH_MUSH_AMOUNT;
		if (tripulation.getMushAmount() == 1) {
			mushAmountMessage = MUSH_ONLY_ONE_MUSH;
		}
		bot.send(mainChannel,
				get(mushAmountMessage, tripulation.getMushAmount()));
		for (User user : tripulation.getMushUsers()) {
			announceUserHeIsMush(user);
		}
	}

	public void announceMushAttack(Tripulation tripulation) {
		bot.send(mainChannel, get(MUSH_MUSH_ATTACK_TIME));
		for (User user : tripulation.getMushUsers()) {
			announceMushAttack(user, tripulation);
		}
	}

	public void announceMushVote(User user, User voted) {
		bot.send(mushChannel,
				get(MUSH_VOTE_VOTE, user.getNick(), voted.getNick()));
	}

	public void announceVote(User user, User voted) {
		bot.send(mainChannel,
				get(MUSH_VOTE_VOTE, user.getNick(), voted.getNick()));
	}

	public void announceMushVoteResult(User mostVotedUser) {
		announceVoteResult(mushChannel, mostVotedUser);
	}

	public void announceVoteResult(Channel channel, User mostVotedUser) {
		bot.send(mushChannel, get(MUSH_VOTE_RESULT, mostVotedUser.getNick()));
	}

	public void announceAction(String key, List<String> args) {
		bot.send(mainChannel, get(key, args));
	}

	public void announceDeath(Player player) {
		bot.send(mainChannel, get(MUSH_PLAYER_DEAD, player.getNick()));
		List<String> roles = player.getRoleNames();
		for (String role : roles) {
			bot.send(mainChannel,
					get(MUSH_PLAYER_ROLE, player.getNick(), get(role)));
		}
	}

	public void announceUserCheck(User user, String nick, String role) {
		bot.send(user, get(MUSH_CHECK_RESULT, nick, role));
	}
	
	private void announceUserHeIsMush(User user) {
		bot.send(user, get(MUSH_USER_IS_MUSH));
	}

	private void announceMushAttack(User user, Tripulation tripulation) {
		bot.send(user, get(MUSH_MUSH_ATTACK, tripulation.getHumans()));
	}

	private String get(String key) {
		return BotMessagesManager.get(bot, key);
	}

	private String get(String key, Object... args) {
		List<String> stringArgs = new ArrayList<String>();
		for (Object arg : args) {
			stringArgs.add(String.valueOf(arg));
		}
		return get(key, stringArgs);
	}

	private String get(String key, String... args) {
		return BotMessagesManager.get(bot, key, args);
	}

	private String get(String key, List<String> args) {
		return BotMessagesManager.get(bot, key, args);
	}

}
