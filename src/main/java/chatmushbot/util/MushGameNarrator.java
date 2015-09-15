package chatmushbot.util;

import i18n.Internationalizable;
import mush.MushValues;
import mush.game.GameUser;
import mush.game.Phase;
import mush.game.action.GameAction;
import mush.game.action.player.PlayerAction;
import mush.game.error.MushGameError;
import mush.game.event.MushGameEventListener;
import util.message.Message;
import util.message.Messages;
import chat.ChatBot;
import chat.ChatChannel;
import chat.ChatUser;
import chat.NoSuchChannelException;
import chat.NoSuchUserException;

/**
 * This class sends the messages for the different actions and stages of the
 * Mush game.
 * 
 * @author Tomas
 */
public class MushGameNarrator implements MushGameEventListener, MushValues {

	private static final String MUSH_CHANNEL_PREFIX = "#mush_channel_";

	private ChatBot chatBot;
	private ChatChannel mainChannel;
	private ChatChannel mushChannel;
	private ActionManager actionManager;
	private ErrorManager errorManager;

	private volatile int prevTime = Integer.MAX_VALUE;

	/**
	 * Creates the MushGameNarrator and joins the mushChannel for later use.
	 * 
	 * @param chatBot
	 * @param mainChannel
	 * @throws MushChannelNotCreatedException
	 *             If the bot couldn't join the mush channel.
	 * @throws IllegalArgumentException
	 *             If any of there arguments is null.
	 */
	public MushGameNarrator(ChatBot chatBot, ChatChannel mainChannel)
			throws MushChannelNotCreatedException {
		if (chatBot == null || mainChannel == null) {
			throw new IllegalArgumentException();
		}
		this.chatBot = chatBot;
		this.mainChannel = mainChannel;
		this.actionManager = new ActionManager();
		this.errorManager = new ErrorManager();
		try {
			chatBot.joinChannel(MUSH_CHANNEL_PREFIX + randomString());
		} catch (NoSuchChannelException e) {
			throw new MushChannelNotCreatedException();
		}
	}

	@Override
	public void onCreateGame() {
		chatBot.send(mainChannel,
				Messages.getMessage(getLang(), MUSH_CREATE_NEW));

	}

	@Override
	public void onStartGame() {
		chatBot.send(mainChannel,
				Messages.getMessage(getLang(), MUSH_START_NEW));
	}

	@Override
	public void onStopGame() {
		chatBot.send(mainChannel,
				Messages.getMessage(getLang(), MUSH_GAME_STOPPED));
	}

	@Override
	public void onAddPlayer(GameUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		chatBot.send(
				mainChannel,
				Messages.getMessage(getLang(), MUSH_JOIN_NEW,
						user.getUsername()));
	}

	@Override
	public void onPerformAction(GameAction action) {
		if (action == null) {
			throw new IllegalArgumentException();
		}
		String message = Messages.getMessage(getLang(), getMessage(action));
		if (isPrivateAction(action)) {
			chatBot.send(getUser((String) ((PlayerAction) action)
					.getPerformer().getId()), message);
		} else if (isMushAction(action)) {
			chatBot.send(mushChannel, message);
		} else {
			chatBot.send(mainChannel, message);
		}
	}

	@Override
	public void onTimeoutNotification(Phase currentPhase, long time) {
		if (currentPhase == null) {
			throw new IllegalArgumentException();
		}
		int missingTime = (int) (time / 1000);
		boolean flag = false;
		if (missingTime > prevTime) {
			flag = true;
		}
		if (missingTime <= 60 && prevTime > 60) {
			flag = true;
		}
		if (missingTime <= 30 && prevTime > 30) {
			flag = true;
		}
		if (missingTime <= 10 && prevTime > 10) {
			flag = true;
		}
		if (missingTime <= 5 && prevTime > 5) {
			flag = true;
		}
		if (flag) {
			prevTime = missingTime;
			chatBot.send(
					mainChannel,
					Messages.getMessage(
							getLang(),
							MUSH_MISSING_TIME,
							Messages.getMessage(getLang(),
									currentPhase.getName()), missingTime));
		}
	}

	@Override
	public void onError(MushGameError error) {
		if (error == null) {
			throw new IllegalArgumentException();
		}
		if (error.getUser() != null) {
			chatBot.send(getUser((String) error.getUser().getId()),
					Messages.getMessage(getLang(), getMessage(error)));
		} else {
			chatBot.send(mainChannel,
					Messages.getMessage(getLang(), getMessage(error)));
		}
	}

	private ChatUser getUser(String userId) {
		try {
			return chatBot.getUser(userId);
		} catch (NoSuchUserException e) {
			throw new IllegalStateException();
		}
	}

	private Message getMessage(MushGameError error) {
		return errorManager.getMessage(error);
	}

	private Message getMessage(GameAction action) {
		return actionManager.getMessage(action);
	}

	private boolean isPrivateAction(GameAction action) {
		return actionManager.isPrivateAction(action);
	}

	private boolean isMushAction(GameAction action) {
		return actionManager.isMushAction(action);
	}

	private String getLang() {
		return ((Internationalizable) chatBot).getLanguage();
	}

	private String randomString() {
		return String.valueOf((int) (Math.random() * 1000000));
	}
}
