package edu.eci.escuelaing.taller3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class URLScanner {
//	public static void main(String[] args) {
//		scanURL("https://ldbn.is.escuelaing.edu.co/");
//		scanURL("https://ldbn.is.escuelaing.edu.co:8080/");
//	}

	public static void main(String[] args) throws Exception {
		readURL("https://ldbn.is.escuelaing.edu.co/images/FotoLuisDanielBenavidesNavarro.jpg");
//		readURL("https://i.pinimg.com/originals/3f/00/cc/3f00cc399f4e22450940055307ed6864.jpg");
		//readURL("https://ldbn.is.escuelaing.edu.co/");
	}

	@SuppressWarnings("unused")
	private static void scanURL(String site) {
		try {
			URL url = new URL(site);
			System.out.println("Protocol: " + url.getProtocol());
			System.out.println("Host: " + url.getHost());
			System.out.println("Port: " + url.getPort());
			System.out.println("Queryt: " + url.getQuery());
		} catch (MalformedURLException e) {
			Logger.getLogger(URLScanner.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	private static void readURL(String site) {
		try {
			URL siteURL = new URL(site);
			// Crea el objeto que representa una URL
			// Crea el objeto que URLConnection
			URLConnection urlConnection = siteURL.openConnection();
			// Obtiene los campos del encabezado y los almacena en un estructura Map
			Map<String, List<String>> headers = urlConnection.getHeaderFields();
			// Obtiene una vista del mapa como conjunto de pares <K,V>
			// para poder navegarlo10
			Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
			// Recorre la lista de campos e imprime los valores
			for (Map.Entry<String, List<String>> entry : entrySet) {
				String headerName = entry.getKey();
				// Si el nombre es nulo, significa que es la linea de estado
				if (headerName != null) {
					System.out.print(headerName + ":");
				}
				List<String> headerValues = entry.getValue();
				for (String value : headerValues) {
					System.out.print(value);
				}
				System.out.println("");
				// System.out.println("");
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
