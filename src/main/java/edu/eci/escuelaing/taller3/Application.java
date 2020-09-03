package edu.eci.escuelaing.taller3;

import edu.eci.escuelaing.taller3.DAO.RowDAO;
import edu.eci.escuelaing.taller3.exception.ApplicationException;
import edu.eci.escuelaing.taller3.web.MyServer;
import edu.eci.escuelaing.taller3.web.ResourceReader;

/**
 * Creates the server and publish the paths
 */
public class Application {

	/**
	 * Main thread of the server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MyServer lms_server = new MyServer();
		lms_server.get("/list", (s) -> {
			try {
				return ResourceReader.createTable(RowDAO.findAll());
			} catch (ApplicationException e) {

				return ResourceReader.messagePage(e.getMessage());
			}

		});
		lms_server.get("/add", (s) -> {
			try {
				if (s.startsWith("name=")) {
					String ls_name;

					ls_name = s.substring(5);
					RowDAO.insertRow(ls_name);

					return ResourceReader.messagePage(ApplicationException.INSERT_SUCCESSFUL);
				} else
					return ResourceReader.messagePage(ApplicationException.ERROR_NAME);

			} catch (ApplicationException e) {

				return ResourceReader.messagePage(e.getMessage());
			}

		});

		lms_server.start();
	}

}
