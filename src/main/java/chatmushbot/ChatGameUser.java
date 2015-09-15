package chatmushbot;

import mush.game.GameUser;
import chat.ChatUser;

/**
 * This class is a Proxy for a ChatUser that can be used within the game.
 * 
 * @author Tomas
 */
public class ChatGameUser implements GameUser {

	private ChatUser chatUser;

	public ChatGameUser(ChatUser chatUser) {
		super();
		if (chatUser == null) {
			throw new IllegalArgumentException();
		}
		this.chatUser = chatUser;
	}

	@Override
	public Object getId() {
		return chatUser.getId();
	}

	@Override
	public String getUsername() {
		return chatUser.getUsername();
	}

}
