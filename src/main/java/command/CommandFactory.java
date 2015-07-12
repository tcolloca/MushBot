package command;

import java.util.List;

import util.CommandNameManager;

public class CommandFactory {

	public static IrcBotCommand build(String command, List<String> args) {
		if (CommandNameManager.isCommand(CommandName.HELP, command)) {
			return new HelpCommand(args);
		} else if (CommandNameManager.isCommand(CommandName.HELLO, command)) {
			return new HelloCommand(args);
		} else if (CommandNameManager.isCommand(CommandName.SUPERNESS, command)) {
			return new SupernessCommand(args);
		} else if (CommandNameManager.isCommand(CommandName.LANG, command)) {
			return new LanguageCommand(args);
		} else if (CommandNameManager.isCommand(CommandName.CREATE, command)) {
			return new CreateCommand(args);
		} else if (CommandNameManager.isCommand(CommandName.JOIN, command)) {
			return new JoinCommand(args);
		} else if (CommandNameManager.isCommand(CommandName.START, command)) {
			return new StartCommand(args);
		} else if (CommandNameManager.isCommand(CommandName.MUSH_VOTE, command)) {
			return new MushVoteCommand(args);
		}
		return new ErrorCommand(args, command);
	}
}
