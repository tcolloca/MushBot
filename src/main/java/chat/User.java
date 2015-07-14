package chat;

public interface User {

	public String getNick();

	public void sendMessage(String message);

	public void inviteTo(Channel channel);
}
