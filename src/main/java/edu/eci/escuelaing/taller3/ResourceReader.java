package edu.eci.escuelaing.taller3;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ResourceReader {
	public static String convertHTLM(String as_path) {
		File lf_file = null;
		FileReader lfr_fr = null;
		BufferedReader lbr_br = null;
		String lda_arreglo = "";

		try {
			String ls_linea;
			lf_file = new File("resources" + as_path);
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

	public static void image(OutputStream outputStream, String as_path) {
		BufferedImage image;
		try {
			image = ImageIO.read(new File("resources" + as_path));
		
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", byteArrayOutputStream);

			byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
			System.out.println(byteArrayOutputStream.size());
        	outputStream.write(size);
        	outputStream.write(byteArrayOutputStream.toByteArray());
        	outputStream.flush();
        	outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
