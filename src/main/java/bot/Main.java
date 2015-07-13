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

import java.nio.charset.Charset;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import properties.ConnectionProperties;

/**
 * Basic example class for various features of PircBotX. Heavily documented to
 * explain what's going on
 * 
 * @author Leon Blakey <lord.quackstar at gmail.com>
 */
public class Main implements ConnectionValues {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {

		MushBot mushBot;

		@SuppressWarnings({ "unchecked" })
		Configuration configuration = new Configuration.Builder()
				.setName(ConnectionProperties.nickname())
				.setAutoReconnect(true)
				.setNickservPassword(ConnectionProperties.password())
				// Set the nick of the bot.
				.setServerHostname(ConnectionProperties.server())
				// (78.129.202.9)
				.addAutoJoinChannel(ConnectionProperties.channel())
				.addListener(mushBot = new MushBot())
				.setEncoding(Charset.forName(ConnectionProperties.encoding()))
				.buildConfiguration();

		// Create our bot with the configuration

		@SuppressWarnings("unchecked")
		PircBotX bot = new PircBotX(configuration);

		mushBot.setBot(bot);
		// Connect to the server
		bot.startBot();
	}
}