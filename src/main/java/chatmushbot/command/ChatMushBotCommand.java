package chatmushbot.command;

import java.util.List;

import security.Permission;
import util.message.Message;
import util.message.Messages;
import chat.ChatChannel;
import chat.ChatUser;
import chat.command.ChatCommand;
import chatmushbot.ChatMushBot;
import chatmushbot.command.util.ChatMushBotCommandManager;
import chatmushbot.command.util.CommandValues;
import chatmushbot.security.ChatAuthUser;

/**
 * Command that can be interpreted by a MushBot. When a new Command is created,
 * it should be added to the command package. The command name must be added to
 * {@link CommandName}. The command's parameters must be added to
 * {@link ParameterName}. A file with all the messages of the command must be
 * added to the i18n/&lt;languages&gt;/command corresponding folder, and an
 * interface with the keys should be added to command.util.message. It must also
 * be added to the CommandPermission enum and to the permissions files.
 * 
 * @author Tomas
 */
public abstract class ChatMushBotCommand extends ChatCommand implements
		CommandValues {

	private ChatMushBot chatMushBot;
	private ChatMushBotCommandFactory commandFactory;

	/**
	 * @param chatMushBot
	 * @param commandFactory
	 * @param commandName
	 * @param parameters
	 * @param executer
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	protected ChatMushBotCommand(ChatMushBot chatMushBot,
			ChatMushBotCommandFactory commandFactory, String commandName,
			List<String> parameters, ChatAuthUser executer) {
		super(commandName, parameters, executer);
		if (chatMushBot == null || commandFactory == null) {
			throw new IllegalArgumentException();
		}
		this.chatMushBot = chatMushBot;
		this.commandFactory = commandFactory;
	}

	/**
	 * Returns the help message associated with the command.
	 * 
	 * @param user
	 *            User that executed the help command for the corresponding
	 *            mushBot's command.
	 * @return the help message associated with the command.
	 */
	protected abstract Message getHelp(ChatUser user);

	/**
	 * Executes the command in the given channel after validating permissions of
	 * the executer.
	 * 
	 * @param channel
	 *            Channel where the command is going to be executed.
	 * @throws IllegalArgumentException
	 *             If {@code channel} is null.
	 */
	public abstract void safeExecute(ChatChannel channel);

	/**
	 * Executes the command for the given user after validating permissions of
	 * the executer.
	 * 
	 * @param user
	 *            User that will receive the execution of the command privately.
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	public abstract void safeExecute(ChatUser user);

	@Override
	public final void execute(ChatChannel channel) {
		if (!checkPermission()) {
			return;
		}
		safeExecute(channel);
	}

	@Override
	public final void execute(ChatUser user) {
		if (!checkPermission()) {
			return;
		}
		safeExecute(user);
	}

	@Override
	public ChatAuthUser getExecuter() {
		return (ChatAuthUser) super.getExecuter();
	}

	public ChatMushBot getChatMushBot() {
		return chatMushBot;
	}

	public ChatMushBotCommandFactory getCommandFactory() {
		return commandFactory;
	}

	protected String getLang() {
		return chatMushBot.getLanguage();
	}

	private boolean checkPermission() {
		if (!getExecuter().hasPermission(getPermission())) {
			getChatMushBot().send(
					getExecuter(),
					Messages.getMessage(getLang(), COMMAND_NOT_ALLOWED,
							toString()));
			return false;
		}
		return true;
	}

	private Permission getPermission() {
		return ChatMushBotCommandManager.getCommandName(getCommandName());
	}
}
