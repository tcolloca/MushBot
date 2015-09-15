package chatmushbot;

import properties.ConnectionProperties;

public class Main {

	public static void main(String[] args) throws Exception {
		ChatMushBot bot = new ChatMushBot();
		while (!bot.hasLoggedIn()) {
			Thread.sleep(10);
		}
		bot.joinChannel(ConnectionProperties.channel());
	}
}
