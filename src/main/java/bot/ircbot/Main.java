package bot.ircbot;

import java.nio.charset.Charset;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;

import properties.ConnectionProperties;

public class Main {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {

		ListenerAdapter mushBot;

		@SuppressWarnings({ "unchecked" })
		Configuration configuration = new Configuration.Builder()
				.setName(ConnectionProperties.nickname())
				.setAutoReconnect(true)
				.setNickservPassword(ConnectionProperties.password())
				.setServerHostname(ConnectionProperties.server())
				.addAutoJoinChannel(ConnectionProperties.channel())
				.addListener(mushBot = new MushBotImpl())
				.setEncoding(Charset.forName(ConnectionProperties.encoding()))
				.buildConfiguration();

		@SuppressWarnings("unchecked")
		PircBotX bot = new PircBotX(configuration);
		((MushBotImpl) mushBot).setBot(bot);
		bot.startBot();
	}
}