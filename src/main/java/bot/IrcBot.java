package bot;

import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.User;

public interface IrcBot {
	
	public void setChannel(Channel channel);
	
	public void sendMessage(String message);

	public void sendResourceMessage(String key);

	public void sendResourceMessage(String key,
			List<String> args);

	public void sendPrivateMessage(User user, String message);

	public void sendPrivateResourceMessage(User user, String key);

	public void sendPrivateResourceMessage(User user, String key,
			List<String> args);

	public void changeLanguage(String lang);

	public boolean hasLanguage(String lang);

	public void showAllLanguages(User user);
	
	public String getAvailableCommandsString();
}
