package chat;

import java.util.List;

import mush.command.ActionCommandType;
import util.message.MessagePack;
import bot.Bot;

public interface MushBot extends Bot {

	public void createGame(Object id);

	public void addPlayer(Object id, User user);

	public void startGame(Object id);

	public void endGame(Object id);

	public boolean isGameCreated(Object id);

	public boolean canGameBeJoined(Object id);

	public boolean canGameBeStarted(Object id);

	public boolean isGameStarted(Object id);

	public boolean isPlaying(User user);

	public boolean canPerformAction(Object id, User user,
			ActionCommandType actionCommandType);

	public boolean isCorrectTime(Object id,
			ActionCommandType actionCommandType);

	public MessagePack getActionErrors(Object id, User user, ActionCommandType actionCommandType,
			List<String> args);

	public void performAction(Object id, User user,
			ActionCommandType actionCommandType, List<String> args);

	// TODO : Remove
	public Channel getMainChannel();
}
