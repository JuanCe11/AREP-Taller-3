package edu.eci.escuelaing.taller3.exception;

public class ApplicationException extends Exception {

	/** Property seria. */
	private static final long serialVersionUID = 1L;

	/** Property ERROR_DB */
	public static final String ERROR_DB = "A problem occurred on the DB connection.";

	/** Property NOT_FOUND */
	public static final String NOT_FOUND = "NOT FOUND";

	/** Property ERROR_NAME */
	public static final String ERROR_NAME = "Please insert the parameter name on url /add?name={name}";

	/** Property INSERT_SUCCESSFUL */
	public static final String INSERT_SUCCESSFUL = "Your insert was completed";

	public ApplicationException(String message) {
		super(message);
	}

}
