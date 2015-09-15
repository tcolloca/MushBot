package mush.game.action.player;

import mush.game.Game;
import mush.game.GameUser;
import mush.game.action.GameAction;
import mush.game.action.validator.Validator;

/**
 * Represents an action in the game being triggered by a player, or in some
 * occasions by the game.
 * 
 * @author Tomas
 */
public abstract class PlayerAction implements GameAction {

	private GameUser performer;
	private Validator validator;

	/**
	 * @param performer
	 * @param validator
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	PlayerAction(GameUser performer, Validator validator) {
		if (performer == null || validator == null) {
			throw new IllegalArgumentException();
		}
		this.performer = performer;
		this.validator = validator;
	}

	/**
	 * @param validator
	 * @throws IllegalArgumentException
	 *             If {@code validator} is null.
	 */
	public PlayerAction(Validator validator) {
		if (validator == null) {
			throw new IllegalArgumentException();
		}
		this.validator = validator;
	}

	/**
	 * Performs an action over the Mush Game after validation.
	 * 
	 * @param game
	 */
	abstract void actionate(Game game);

	@Override
	public void action(Game mushGame) {
		if (!validator.validate(mushGame, this)) {
			return;
		}
		actionate(mushGame);
	}
	
	public GameUser getPerformer() {
		return performer;
	}
}
