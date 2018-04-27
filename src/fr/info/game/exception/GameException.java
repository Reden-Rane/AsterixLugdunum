package fr.info.game.exception;

public class GameException extends Exception {

	public GameException(String message) {
		super("[AsterixAndObelix] " + message);
	}
}
