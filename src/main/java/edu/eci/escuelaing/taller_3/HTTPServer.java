package edu.eci.escuelaing.taller_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class HTTPServer {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(36000);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 36000.");
			System.exit(1);
		}
		boolean running = true;
		while (running) {
			Socket clientSocket = null;
			try {
				System.out.println("Listo para recibir en puerto 36000...");
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String path = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String inputLine, outputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println("Recibí: " + inputLine);
				if (inputLine.contains("GET")) {
					path = inputLine.split("GET")[1];
					path = path.split("HTTP")[0];
					path = path.trim();
					if(path.equals("/"))
						path = "/index.html";
				}
				if (!in.ready()) {
					break;
				}
			}
			System.out.println(path);
			System.out.println(path.split("\\.")+" LENGHT "+path.split("\\.").length);
			outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/"+ path.split("\\.")[1]+"\r\n" + "\r\n"  ;
			//outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: image/jpeg\r\n" + "\r\n"  ;
//					+"<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title>Title of the document</title>\n"
//					+ "</head>\n" + "<body>\n" + "<h1>Mi propio mensaje</h1>\n" + "</body>\n" + "</html>\n" + inputLine;
			outputLine += convertirHtml(path);
			System.out.println(outputLine);
			out.println(outputLine);
			out.close();
			in.close();
			clientSocket.close();
		}
		serverSocket.close();
	}
	private static String convertirHtml(String as_path) {
		File lf_file = null;
		FileReader lfr_fr = null;
		BufferedReader lbr_br = null;
		String lda_arreglo = "";

		try {
			String ls_linea;
			lf_file = new File("resources"+as_path);
			lfr_fr = new FileReader(lf_file);
			lbr_br = new BufferedReader(lfr_fr);

			while ((ls_linea = lbr_br.readLine()) != null)
				lda_arreglo += ls_linea;

		} catch (IOException e) {

			System.out.println("Ocurrio un error al abrir el archivo ");

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
}
