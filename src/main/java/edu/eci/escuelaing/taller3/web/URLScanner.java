package edu.eci.escuelaing.taller3.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads an reasponse from the server
 */
public class URLScanner {

	public static void main(String[] args) throws Exception {
		readURL("https://ldbn.is.escuelaing.edu.co/images/FotoLuisDanielBenavidesNavarro.jpg");
		scanURL("https://ldbn.is.escuelaing.edu.co/images/FotoLuisDanielBenavidesNavarro.jpg");
	}

	/**
	 * Scan the response header
	 * 
	 * @param as_site url to read
	 */
	private static void scanURL(String as_site) {
		try {
			URL url = new URL(as_site);
			System.out.println("Protocol: " + url.getProtocol());
			System.out.println("Host: " + url.getHost());
			System.out.println("Port: " + url.getPort());
			System.out.println("Queryt: " + url.getQuery());
		} catch (MalformedURLException e) {
			Logger.getLogger(URLScanner.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	/**
	 * Reads an reasponse from the server
	 * 
	 * @param as_site url to read
	 */
	private static void readURL(String as_site) {
		try {
			URL siteURL = new URL(as_site);
			URLConnection urlConnection = siteURL.openConnection();
			Map<String, List<String>> headers = urlConnection.getHeaderFields();
			Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
			for (Map.Entry<String, List<String>> entry : entrySet) {
				String headerName = entry.getKey();
				if (headerName != null) {
					System.out.print(headerName + ":");
				}
				List<String> headerValues = entry.getValue();
				for (String value : headerValues) {
					System.out.print(value);
				}
				System.out.println("");
			}
			System.out.println("------------SEPARADOR----------");
			BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String inputLine = null;
			while ((inputLine = reader.readLine()) != null) {
				System.out.println(inputLine);
			}
		} catch (IOException x) {
			System.err.println(x);
		}
	}

}
