package edu.eci.escuelaing.taller3.model;

/**
 * Class to map DB row
 */
public class Row {

	/** Property name. */
	private String is_name;

	/**
	 * Method that returns the value of property name.
	 * 
	 * @return the value of property name.
	 */
	public String getName() {
		return is_name;
	}

	/**
	 * Method that modifies the value of property name.
	 * 
	 * @param as_s the new value of property name.
	 */
	public void setName(String as_s) {
		is_name = as_s;
	}

}
