package chat;

public interface Channel {

	public void sendMessage(String message);

	public void silence();
	
	public void setInviteOnly();
	
	public void setSecret();

	public String getName();
	
}
