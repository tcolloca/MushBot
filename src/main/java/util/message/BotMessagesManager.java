package util.message;

import java.util.List;

import bot.Bot;

import com.google.common.collect.Lists;

public class BotMessagesManager {

	public static String get(Bot bot, String key) {
		return getMessagesManager(bot).get(key);
	}

	public static String get(Bot bot, String key, String... args) {
		return getMessagesManager(bot).get(key, Lists.newArrayList(args));
	}

	public static String get(Bot bot, String key, List<String> args) {
		return getMessagesManager(bot).get(key, args);
	}

	private static MessagesManager getMessagesManager(Bot bot) {
		String lang = bot.getLanguage();
		return new MessagesManager(lang);
	}
}
