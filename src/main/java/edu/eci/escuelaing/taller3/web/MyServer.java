package edu.eci.escuelaing.taller3.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.escuelaing.taller3.exception.ApplicationException;

public class MyServer {

	/** Property running */
	private boolean ib_running;

	/** Property port */
	private int ii_port;

	/** Property services */
	private HashMap<String, Function> ihm_services;

	/**
	 * Returns the value of running propertie.
	 * 
	 * @return the value of running propertie.
	 */
	public boolean isRunning() {
		return ib_running;
	}

	/**
	 * Modifies the value of running propertie.
	 * 
	 * @param ab_b the new value for running propertie.
	 */
	public void setRunning(boolean ab_b) {
		ib_running = ab_b;
	}

	/**
	 * Returns the value of port propertie.
	 * 
	 * @return the value of port propertie.
	 */
	public int getPort() {
		return ii_port;
	}

	/**
	 * Modifies the value of port propertie.
	 * 
	 * @param ab_b the new value for port propertie.
	 */
	public void setPort(int ai_i) {
		ii_port = ai_i;
	}

	/**
	 * Default class creator
	 */
	public MyServer() {
		ii_port = genPort();
		ihm_services = new HashMap<String, Function>();
		ib_running = false;
	}
	
	/**
	 * Get default port to run 
	 * 
	 * @return the default port 
	 */
	public int genPort() {
		if (System.getenv("PORT") != null)
		{            
			return Integer.parseInt(System.getenv("PORT"));      
		} 
		return 1234; 
	}

	/**
	 * Methot that starts the server.
	 */
	public void start() {
		try {
			ServerSocket lss_serverSocket = null;
			try {
				lss_serverSocket = new ServerSocket(getPort());
			} catch (IOException e) {
				System.err.println("Could not listen on port " + getPort());
				System.exit(1);
			}
			ib_running = true;
			while (ib_running) {
				Socket ls_clientSocket = null;
				try {
					System.out.println("Ready to recieve on port " + getPort());
					ls_clientSocket = lss_serverSocket.accept();
				} catch (IOException e) {
					System.err.println("Accept failed.");
					System.exit(1);
				}
				processReq(ls_clientSocket);
				ls_clientSocket.close();
			}
			lss_serverSocket.close();
		} catch (IOException e) {
			Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	/**
	 * Method that process a request.
	 * 
	 * @param clientSocket Connection socket
	 * @throws IOException Input exception
	 */
	private void processReq(Socket clientSocket) throws IOException {
		BufferedReader in;
		String inputLine;
		String[] lsa_values;
		Map<String, String> lmss_values;
		boolean lb_fistLine;

		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		lmss_values = new HashMap<String, String>();
		lb_fistLine = true;

		inputLine = in.readLine();
		while (inputLine != null && inputLine.length() > 0) {
			if (lb_fistLine) {
				lsa_values = inputLine.split(" ");
				lmss_values.put("verb", lsa_values[0].trim());
				lmss_values.put("path", (lsa_values[1].trim().equals("/")) ? "/index.html" : lsa_values[1].trim());
				if (lmss_values.get("path").contains("?")) {
					String[] lsa_variable;

					lsa_variable = lmss_values.get("path").split("\\?");
					lmss_values.put("path", lsa_variable[0]);
					lmss_values.put("var", lsa_variable[1]);
				}
				lmss_values.put("protocol", lsa_values[2].trim());
				lb_fistLine = false;
			} else {
				lsa_values = inputLine.split(": ");
				lmss_values.put(lsa_values[0], lsa_values[1]);
			}

			if (!in.ready()) {
				break;
			}

			inputLine = in.readLine();
		}

		if (ihm_services != null && lmss_values != null) {
			if (ihm_services.containsKey(lmss_values.get("path"))) {
				Function<String, String> f = ihm_services.get(lmss_values.get("path"));
				if (lmss_values.containsKey("var"))
					sendResponse(new PrintWriter(clientSocket.getOutputStream(), true),
							f.apply(lmss_values.get("var")));
				else
					sendResponse(new PrintWriter(clientSocket.getOutputStream(), true),
							f.apply(lmss_values.get("path")));
			} else {
				if (lmss_values.containsKey("path")) {
					if (lmss_values.get("path").contains("png")) {
						try {
							ResourceReader.readImage(clientSocket.getOutputStream(), lmss_values.get("path"));
						} catch (ApplicationException e) {
							sendResponse(new PrintWriter(clientSocket.getOutputStream(), true),
									ResourceReader.messagePage(e.getMessage()));
						}
					} else
						createResponse(new PrintWriter(clientSocket.getOutputStream(), true), lmss_values);
				}
			}
		}
		in.close();
	}

	/**
	 * Send a response to the client
	 * 
	 * @param apw_printWriter The connection stream
	 * @param as_response     The response to the client
	 */
	private void sendResponse(PrintWriter apw_printWriter, String as_response) {
		apw_printWriter.print(as_response);
		apw_printWriter.close();
	}

	/**
	 * Creates a response when the type is not an image
	 * 
	 * @param apw_printWriter The connection stream
	 * @param amss_values     the information from the request
	 */
	private void createResponse(PrintWriter apw_printWriter, Map<String, String> amss_values) {
		String ls_response;

		ls_response = ResourceReader.convertHTLM(amss_values.get("path"));

		sendResponse(apw_printWriter, ls_response);
	}

	/**
	 * Publish a get service to the specified path
	 * 
	 * @param as_resourcePath The path to publishing the service
	 * @param abf_bf          The function to do on the path
	 */
	public void get(String as_resourcePath, Function<String, String> abf_bf) {
		if (ihm_services == null)
			ihm_services = new HashMap<String, Function>();
		ihm_services.put(as_resourcePath, abf_bf);
		System.out.println("Contiene el path " + as_resourcePath + " " + ihm_services.containsKey(as_resourcePath));
	}

}
