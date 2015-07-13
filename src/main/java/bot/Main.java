package bot;

import java.nio.charset.Charset;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import properties.ConnectionProperties;

public class Main {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {

		MushBot mushBot;

		@SuppressWarnings({ "unchecked" })
		Configuration configuration = new Configuration.Builder()
				.setName(ConnectionProperties.nickname())
				.setAutoReconnect(true)
				.setNickservPassword(ConnectionProperties.password())
				.setServerHostname(ConnectionProperties.server())
				.addAutoJoinChannel(ConnectionProperties.channel())
				.addListener(mushBot = new MushBot())
				.setEncoding(Charset.forName(ConnectionProperties.encoding()))
				.buildConfiguration();

		@SuppressWarnings("unchecked")
		PircBotX bot = new PircBotX(configuration);
		mushBot.setBot(bot);
		bot.startBot();
	}
}