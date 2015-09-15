package util.message;

/**
 * Names of the different ResourceBundles.
 * 
 * @author Tomas
 */
public enum ResourceBundles {

	COMMANDS("command.Commands"), MESSAGES("Messages"), SECURITY("Security"), ERROR("command.Error"), LANGUAGE(
			"command.Language"), HELP("command.Help"), SUPERNESS(
			"command.Superness"), CREATE("command.game.Create"), JOIN(
			"command.game.Join"), START("command.game.Start"), STOP(
			"command.game.Stop"), MUSHVOTE("command.game.Mushvote"), VOTE(
			"command.game.Vote"), GAME("game.Game"), ROLES("game.Roles"), PHASES(
			"game.Phases");

	private String bundleName;

	private ResourceBundles(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getBundleName() {
		return bundleName;
	}

	@Override
	public String toString() {
		return bundleName;
	}
}
