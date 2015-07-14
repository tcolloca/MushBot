package bot;

import java.util.List;

import chat.Channel;
import chat.User;

public interface Bot {

	public void send(String message);
	
	public void send(Channel channel, String message);

	public void send(User user, String message);

	public String getLanguage();
	
	public void setLanguage(String language);

	public List<String> getAvailableCommands();

	public void joinChannel(String channelName);

	public void silenceChannel(Channel channel);

	public void inviteToChannel(User user, Channel channel);
}
