package chatmushbot.command;

import java.lang.reflect.Constructor;
import java.util.List;

import chat.ChatUser;
import chat.command.ChatCommandFactory;
import chatmushbot.ChatMushBot;
import chatmushbot.command.util.ChatMushBotCommandManager;
import chatmushbot.security.ChatAuthUser;

/**
 * Returns the corresponding MushBot's Command associated with the commandName
 * and parameters.
 * 
 * @author Tomas
 */
public class ChatMushBotCommandFactory implements ChatCommandFactory {

	private static final String COMMAND = "Command";
	private static final String DEFAULT_COMMAND_LOCATION = "chatmushbot.command";

	private ChatMushBot chatMushBot;

	public ChatMushBotCommandFactory() {
	}

	public void setChatMushBot(ChatMushBot chatMushBot) {
		this.chatMushBot = chatMushBot;
	}

	@Override
	public ChatMushBotCommand build(String commandName,
			List<String> parameters, ChatUser executer) {
		for (CommandName name : CommandName.values()) {
			if (ChatMushBotCommandManager.isCommand(name, commandName)) {
				return newCommandInstance(getClassName(name), chatMushBot,
						commandName, parameters, executer);
			}
		}
		return new ErrorCommand(chatMushBot, this, commandName, parameters,
				(ChatAuthUser) executer);
	}

	private String getClassName(CommandName commandName) {
		String name = commandName.getName();
		String capName = name.substring(0, 1).toUpperCase() + name.substring(1);
		String aux = capName + COMMAND;
		String location = commandName.getLocation();
		return DEFAULT_COMMAND_LOCATION + location + "." + aux;
	}

	private ChatMushBotCommand newCommandInstance(String className,
			ChatMushBot chatMushBot, String commandName,
			List<String> parameters, ChatUser executer) {
		try {
			Class<? extends ChatMushBotCommand> clazz = getChatCommandClass(className);
			Constructor<? extends ChatMushBotCommand> constructor = clazz
					.getConstructor(ChatMushBot.class,
							ChatMushBotCommandFactory.class, String.class,
							List.class, ChatAuthUser.class);
			return constructor.newInstance(new Object[] { chatMushBot, this,
					commandName, parameters, executer });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends ChatMushBotCommand> getChatCommandClass(
			String className) {
		try {
			return (Class<ChatMushBotCommand>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
}
