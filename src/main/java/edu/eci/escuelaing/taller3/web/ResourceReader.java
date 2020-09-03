package edu.eci.escuelaing.taller3.web;

import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Collection;

import javax.imageio.ImageIO;

import edu.eci.escuelaing.taller3.exception.ApplicationException;
import edu.eci.escuelaing.taller3.model.Row;

/**
 * Reads and creates response files.
 */
public class ResourceReader {

	/**
	 * Convert an HTLM file to string
	 * 
	 * @param as_path the path of th HTLM file.
	 * @return A string with the file information.
	 */
	public static String convertHTLM(String as_path) {
		File lf_file = null;
		FileReader lfr_fr = null;
		BufferedReader lbr_br = null;
		String lda_arreglo = "";

		if (as_path.contains("\\.") && as_path.split("\\.").length > 0)
			lda_arreglo = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/" + as_path.split("\\.")[1].trim() + "\r\n"
					+ "\r\n";
		else
			lda_arreglo = "HTTP/1.1 200 OK\r\n Content-Type: text/html \r\n\r\n";

		try {
			String ls_linea;
			lf_file = new File("resources" + as_path);
			lfr_fr = new FileReader(lf_file);
			lbr_br = new BufferedReader(lfr_fr);

			while ((ls_linea = lbr_br.readLine()) != null)
				lda_arreglo += ls_linea;

		} catch (IOException e) {
			lda_arreglo = messagePage(ApplicationException.NOT_FOUND);

		} finally {

			try {
				if (null != lfr_fr)
					lfr_fr.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}

		return lda_arreglo;

	}

	/**
	 * Reads and return an image
	 * 
	 * @param path         path of the image
	 * @param outputStream connection stream
	 */
	public static void readImage(OutputStream outputStream, String as_path) throws ApplicationException {
		try {
			BufferedImage lbf_bf = ImageIO.read(new File("resources" + as_path));
			ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(outputStream);

			ImageIO.write(lbf_bf, "PNG", ArrBytes);

			out.writeBytes("HTTP/1.1 200 OK \r\n" + "Content-Type: image/png \r\n" + "\r\n");
			out.write(ArrBytes.toByteArray());
		} catch (IOException e) {
			throw new ApplicationException(ApplicationException.NOT_FOUND);
		}
	}

	/**
	 * Creates an table on HTLM syntax to send
	 * 
	 * @param ac_data Information of the table
	 * @return String with the HTLM response
	 */
	public static String createTable(Collection<Row> ac_data) {
		String ls_response;

		ls_response = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html \r\n\r\n";
		ls_response += "<!DOCTYPE html>\n" + "<head>\n" + "<title>List</title>\n" + "<meta charset=\"UTF-8\">\n"
				+ "</head>\n" + "<body>\n";
		ls_response += "\t<table class=\"egt\">\n" + "\t\t<tr>\n" + "\t\t\t<th>#</th>\n" + "\t\t\t<th>Nombre</th>\n"
				+ "\t\t</tr>\n";

		if (ac_data != null) {
			int li_cont;

			li_cont = 1;

			for (Row lr_iterator : ac_data) {
				ls_response += "\t\t<tr>\n" + "\t\t\t<td>" + (li_cont++) + "</td>\n" + "\t\t\t<td>"
						+ lr_iterator.getName() + "</td>\n" + "\t\t</tr>\n";
			}
		}

		ls_response += "\t</table>" + "</body>\n" + "</html>";

		return ls_response;

	}

	/**
	 * Creates on HTLM syntax a message
	 * 
	 * @param ls_message The message
	 * @return String with the HTLM response
	 */
	public static String messagePage(String ls_message) {
		String ls_response;

		ls_response = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html \r\n\r\n";
		ls_response += "<!DOCTYPE html>\n" + "<head>\n" + "<title>error</title>\n" + "<meta charset=\"UTF-8\">\n"
				+ "</head>\n" + "<body>\n" + "<h1>" + ls_message + "</h1>" + "</body>\n" + "</html>";

		return ls_response;

	}
}
