package mush.game.ai;

import mush.MushValues;

public class Narrator implements MushValues {

	/*
	 * private ChatBot bot; private ChatChannel mainChannel; private ChatChannel
	 * mushChannel;
	 * 
	 * public Narrator(ChatBot bot, ChatChannel mainChannel) { this.bot = bot;
	 * this.mainChannel = mainChannel; }
	 * 
	 * public void setMushChannel(ChatChannel mushChannel) { this.mushChannel =
	 * mushChannel; }
	 * 
	 * public void announceTripulation(Tripulation tripulation) {
	 * bot.send(mainChannel, get(MUSH_PLAYERS_AMOUNT,
	 * tripulation.getPlayersAmount())); String mushAmountMessage =
	 * MUSH_MUSH_AMOUNT; if (tripulation.getMushAmount() == 1) {
	 * mushAmountMessage = MUSH_ONLY_ONE_MUSH; } bot.send(mainChannel,
	 * get(mushAmountMessage, tripulation.getMushAmount())); for (ChatUser user
	 * : tripulation.getMushUsers()) { announceUserHeIsMush(user); } }
	 * 
	 * public void announceMushAttack(Tripulation tripulation) {
	 * bot.send(mainChannel, get(MUSH_MUSH_ATTACK_TIME)); for (ChatUser user :
	 * tripulation.getMushUsers()) { announceMushAttack(user, tripulation); } }
	 * 
	 * public void announceMushVoteResult(ChatUser mostVotedUser) {
	 * announceVoteResult(mushChannel, mostVotedUser); }
	 * 
	 * public void announceVoteResult(ChatChannel channel, ChatUser
	 * mostVotedUser) { bot.send(mushChannel, get(MUSH_VOTE_RESULT,
	 * mostVotedUser.getUsername())); }
	 * 
	 * public void announceAction(String key, List<String> args) {
	 * bot.send(mainChannel, get(key, args)); }
	 * 
	 * public void announceDeath(Player player) { bot.send(mainChannel,
	 * get(MUSH_PLAYER_DEAD, player.getNick())); List<String> roles =
	 * player.getRoleNames(); for (String role : roles) { bot.send(mainChannel,
	 * get(MUSH_PLAYER_ROLE, player.getNick(), get(role))); } }
	 * 
	 * private void announceUserHeIsMush(ChatUser user) { bot.send(user,
	 * get(MUSH_USER_IS_MUSH)); }
	 * 
	 * private void announceMushAttack(ChatUser user, Tripulation tripulation) {
	 * bot.send(user, get(MUSH_MUSH_ATTACK, tripulation.getHumans())); }
	 * 
	 * private String get(String key) { return BotMessagesManager.get(bot, key);
	 * }
	 * 
	 * private String get(String key, Object... args) { List<String> stringArgs
	 * = new ArrayList<String>(); for (Object arg : args) {
	 * stringArgs.add(String.valueOf(arg)); } return get(key, stringArgs); }
	 * 
	 * private String get(String key, String... args) { return
	 * BotMessagesManager.get(bot, key, args); }
	 * 
	 * private String get(String key, List<String> args) { return
	 * BotMessagesManager.get(bot, key, args); }
	 */
}
