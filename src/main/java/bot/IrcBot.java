package bot;

import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.User;

public interface IrcBot {

	public void setChannel(Channel channel);

	public void sendMessage(Channel channel, String message);

	public void sendResourceMessage(String key);

	public void sendResourceMessage(Channel channel, String key);

	public void sendResourceMessage(String key, List<String> args);

	public void sendResourceMessage(Channel channel, String key,
			List<String> args);

	public void sendPrivateMessage(User user, String message);

	public void sendPrivateResourceMessage(User user, String key);

	public void sendPrivateResourceMessage(User user, String key,
			List<String> args);

	public void changeLanguage(String lang);

	public List<String> getAvailableCommands();

	public void joinChannel(String channel);

	public void silenceChannel();

	public void silenceChannel(Channel channel);

	public void inviteToChannel(Channel channel, List<User> users);
}
