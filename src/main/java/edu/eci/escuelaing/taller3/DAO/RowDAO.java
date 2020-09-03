package edu.eci.escuelaing.taller3.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.escuelaing.taller3.exception.ApplicationException;
import edu.eci.escuelaing.taller3.model.Row;

public class RowDAO {

	/** Constante cs_SELECT_ALL. */
	private static final String cs_SELECT_ALL = "SELECT * FROM jugador";

	/** Constante cs_INSERT. */
	private static final String cs_INSERT = "INSERT INTO jugador (nombre) VALUES(?)";

	/**
	 * Method that find all rows on table jugador
	 * 
	 * @return Collection with all rows
	 * @throws ApplicationException when the connection to DB fails.
	 */
	public static Collection<Row> findAll() throws ApplicationException {
		PreparedStatement lps_ps;
		ResultSet lrs_rs;
		Collection<Row> lcr_data;
		Connection lc_connection;

		lps_ps = null;
		lrs_rs = null;
		lcr_data = null;
		lc_connection = null;

		try {
			lc_connection = DBConnection.getConnection();
			lcr_data = new ArrayList<Row>();

			lps_ps = lc_connection.prepareStatement(cs_SELECT_ALL);
			lrs_rs = lps_ps.executeQuery();

			while (lrs_rs.next()) {
				lcr_data.add(getRow(lrs_rs));
			}

			lc_connection.close();

		} catch (Exception e) {

			Logger.getLogger(RowDAO.class.getName()).log(Level.SEVERE, null, e);

			throw new ApplicationException(ApplicationException.ERROR_DB);
		} finally {

		}

		return lcr_data;
	}

	/**
	 * Method that insert one row on table jugador
	 * 
	 * @param ls_name the name of the new jugador
	 * @throws ApplicationException when the connection to DB fails.
	 */
	public static void insertRow(String ls_name) throws ApplicationException {
		PreparedStatement lps_ps;
		Connection lc_connection;

		lps_ps = null;
		lc_connection = null;

		try {

			lc_connection = DBConnection.getConnection();

			lps_ps = lc_connection.prepareStatement(cs_INSERT);
			lps_ps.setString(1, ls_name);

			lps_ps.executeUpdate();

			lc_connection.close();

		} catch (Exception e) {

			Logger.getLogger(RowDAO.class.getName()).log(Level.SEVERE, null, e);

			throw new ApplicationException(ApplicationException.ERROR_DB);
		}

	}

	/**
	 * Create new row with the information on the result set
	 * 
	 * @param ars_resultSet the result set with the row information
	 * @return new row eith the information
	 * @throws SQLException
	 */
	private static Row getRow(ResultSet ars_resultSet) throws SQLException {

		Row lr_row;
		lr_row = new Row();

		lr_row.setName(ars_resultSet.getString("nombre"));

		return lr_row;

	}

}
