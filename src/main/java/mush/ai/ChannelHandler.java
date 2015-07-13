package mush.ai;

import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.User;

public class ChannelHandler {

	private static final String VOICE_ONLY = "+m";
	
	public static void prepareChannel(Channel channel) {
		channel.send().setMode(VOICE_ONLY);
	}
	
	public static void prepareMushChannel(Channel channel) {
		channel.send().setInviteOnly(channel);
		channel.send().setSecret(channel);
	}
	
	public static void silenceAll(Channel channel) {
		channel.send().setMode(VOICE_ONLY);
	}
	
	public static void inviteToChannel(Channel channel, List<User> users) {
		for (User user: users) {
			inviteToChannel(channel, user);
		}
	}
	
	public static void inviteToChannel(Channel channel, User user) {
		user.send().invite(channel);
	}
}
