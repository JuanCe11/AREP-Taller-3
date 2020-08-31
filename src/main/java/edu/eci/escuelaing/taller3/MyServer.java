package edu.eci.escuelaing.taller3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MyServer {
	
	/** Propertie running*/
	private boolean ib_running;
	
	/** Propertie port */
	private int ii_port;

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
	 * @param ab_b the new value for port propertie.
	 */
	public void setPort(int ai_i) {
		ii_port = ai_i;
	}
	
	/**
	 * Default class creator
	 */
	public MyServer() {
		ii_port = 36000;
	}

	public void start() {
		try{
			ServerSocket lss_serverSocket = null;
			try{
				lss_serverSocket = new ServerSocket(getPort());
			}catch (IOException e) {
				System.err.println("Could not listen on port "+getPort());
				System.exit(1);
			}
			ib_running = true;
			while (ib_running) {
				Socket ls_clientSocket = null;
				try {
					System.out.println("Ready to recieve on port "+ getPort());
					ls_clientSocket = lss_serverSocket.accept();
				} catch (IOException e) {
					System.err.println("Accept failed.");
					System.exit(1);
				}
				processReq(ls_clientSocket);
				ls_clientSocket.close();
			}
			lss_serverSocket.close();
		}catch(IOException e){
			Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, e);
		}
		
	}

	private void processReq(Socket clientSocket) throws IOException {
		BufferedReader in;	
		String inputLine;
		String[] lsa_values;
		Map<String,String> lmss_values;
		boolean lb_fistLine;
		
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		lmss_values = new HashMap<String, String>();
		lb_fistLine = true;
				
		inputLine = in.readLine();
		while (inputLine != null && inputLine.length() > 0) {
			System.out.println(inputLine);
			if (lb_fistLine){
				lsa_values = inputLine.split(" ");
				lmss_values.put("verb",lsa_values[0].trim());
				lmss_values.put("path",(lsa_values[1].trim().equals("/"))?"/index.html":lsa_values[1].trim());
				lmss_values.put("protocol",lsa_values[2].trim());
				lb_fistLine = false;
			}else {
				lsa_values = inputLine.split(": ");
				lmss_values.put(lsa_values[0], lsa_values[1]);
			}
			
			if (!in.ready()) {
				break;
			}
			inputLine = in.readLine();
		}
		if (lmss_values.get("path").contains("jpg"))
			ResourceReader.image(clientSocket.getOutputStream(),"/1.jpg");
		else
			createResponse(new PrintWriter(clientSocket.getOutputStream(),true), lmss_values);
		in.close();

	}
	
	private void createResponse(PrintWriter printWriter,Map<String, String> amss_values) {
		String ls_response;
	
		ls_response = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/" + amss_values.get("path").split("\\.")[1].trim() + "\r\n" + "\r\n";
		//ls_response = "HTTP/1.1 200 OK\r\n" + "Content-Type: image/jpeg\r\n" + "\r\n";

		ls_response += ResourceReader.convertHTLM(amss_values.get("path"));
		printWriter.print(ls_response);
		printWriter.close();
	}

	public static void get(String as_resourcePath, BiFunction<String, String, String> abf_bf) {
		// si el servidor web no esta corriendo, correrlo

	}

}
