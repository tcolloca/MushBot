package chat.command;

import java.util.List;

import chat.command.mush.CreateCommand;
import chat.command.mush.JoinCommand;
import chat.command.mush.MushVoteCommand;
import chat.command.mush.StartCommand;

public class CommandFactory {

	public static BotCommand build(String command, List<String> args) {
		if (CommandsManager.isCommand(CommandName.HELP, command)) {
			return new HelpCommand(args);
		} else if (CommandsManager.isCommand(CommandName.SUPERNESS, command)) {
			return new SupernessCommand(args);
		} else if (CommandsManager.isCommand(CommandName.LANG, command)) {
			return new LanguageCommand(args);
		} else if (CommandsManager.isCommand(CommandName.CREATE, command)) {
			return new CreateCommand(args);
		} else if (CommandsManager.isCommand(CommandName.JOIN, command)) {
			return new JoinCommand(args);
		} else if (CommandsManager.isCommand(CommandName.START, command)) {
			return new StartCommand(args);
		} else if (CommandsManager.isCommand(CommandName.MUSH_VOTE, command)) {
			return new MushVoteCommand(args);
		}
		return new ErrorCommand(args, command);
	}
}
