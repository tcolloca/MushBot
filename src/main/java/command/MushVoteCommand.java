package command;

import java.util.List;

import mush.MushValues;

import org.pircbotx.hooks.types.GenericMessageEvent;

import util.MessagePack;
import util.MessagesValues;
import bot.IrcBot;
import bot.MushBot;

import com.google.common.collect.Lists;
import command.help.HelpValues;

public class MushVoteCommand extends IrcBotCommand implements MessagesValues,
		MushValues {

	MushVoteCommand(List<String> args) {
		super(args);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(IrcBot bot, GenericMessageEvent event) {
		if (args.size() <= 1) {
			bot.sendPrivateResourceMessage(event.getUser(), MUSH_VOTE_NO_NICK);
		} else {
			((MushBot) bot).mushVote(event.getUser(), args.get(1));
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	MessagePack getHelp(IrcBot bot, GenericMessageEvent event) {
		if (args.size() == 1) {
			return new MessagePack(HelpValues.MUSH_VOTE);
		} else {
			return new MessagePack(HelpValues.MUSH_VOTE_NICK,
					Lists.newArrayList(args.get(1)));
		}
	}
}
