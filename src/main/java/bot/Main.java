package bot;

/**
 * Copyright (C) 2010-2011 Leon Blakey <lord.quackstar at gmail.com>
 *
 * This file is part of PircBotX.
 *
 * PircBotX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PircBotX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with PircBotX.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

/**
 * Basic example class for various features of PircBotX. Heavily documented to
 * explain what's going on
 * 
 * @author Leon Blakey <lord.quackstar at gmail.com>
 */
public class Main implements ConnectionValues {
	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {

		Properties props = new Properties();
		MushBot mushBot;
		InputStream input;
		try {
			input = ClassLoader
					.getSystemResourceAsStream(CONNECTION_PROPERTIES);
			props.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		@SuppressWarnings({ "unchecked" })
		Configuration configuration = new Configuration.Builder()
				.setName(props.getProperty(IRC_NICKNAME))
				// Set the nick of the bot.
				.setServerHostname(props.getProperty(IRC_SERVER))
				// (78.129.202.9)
				.addAutoJoinChannel(props.getProperty(IRC_CHANNEL))
				.addListener(mushBot = new MushBot())
				.setEncoding(Charset.forName(props.getProperty(IRC_ENCODING)))
				.buildConfiguration();

		// Create our bot with the configuration

		@SuppressWarnings("unchecked")
		PircBotX bot = new PircBotX(configuration);
		
		mushBot.setBot(bot);
		
		// Connect to the server 
		bot.startBot();
	}
}