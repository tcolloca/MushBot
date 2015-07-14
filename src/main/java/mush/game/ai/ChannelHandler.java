package mush.game.ai;

import java.util.List;

import chat.Channel;
import chat.User;

public class ChannelHandler {
	
	public static void prepareChannel(Channel channel) {
		channel.silence();
	}
	
	public static void prepareMushChannel(Channel channel) {
		channel.setInviteOnly();
		channel.setSecret();
	}
	
	public static void silence(Channel channel) {
		channel.silence();
	}
	
	public static void inviteToChannel(Channel channel, List<User> users) {
		for (User user: users) {
			inviteToChannel(channel, user);
		}
	}
	
	public static void inviteToChannel(Channel channel, User user) {
		user.inviteTo(channel);
	}
}
