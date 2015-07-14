package chat.command.mush;

import java.util.List;

import chat.Channel;
import chat.MushBot;
import chat.command.BotCommand;

public abstract class MushGameStarterCommand extends BotCommand {

	MushGameStarterCommand(List<String> args) {
		super(args);
	}

	// TODO: Get channel according to args
	Channel getChannel(MushBot mushBot) {
		return mushBot.getMainChannel();
	}
}
