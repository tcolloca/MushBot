package chatmushbot.security;

import security.AuthUser;
import chat.ChatActionNotAllowedException;
import chat.ChatChannel;
import chat.ChatUser;
import chat.SendMessageNotAllowedException;

public class ChatAuthUser extends AuthUser implements ChatUser {

	private ChatUser chatUser;

	/**
	 * @param chatUser
	 * @throws IllegalArgumentException
	 *             If {@code chatUser} is null.
	 */
	public ChatAuthUser(ChatUser chatUser) {
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

	@Override
	public void sendMessage(String message)
			throws SendMessageNotAllowedException {
		chatUser.sendMessage(message);
	}

	@Override
	public void inviteTo(ChatChannel channel)
			throws ChatActionNotAllowedException {
		chatUser.inviteTo(channel);
	}
}
