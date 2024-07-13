package br.com.controller.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

public class ImageConverter {
	
	 public static String convertImageToBase64(Blob imgBlob) throws IOException, SQLException {
	        if (imgBlob == null) {
	            return null;
	        }

	        try (InputStream imgStream = imgBlob.getBinaryStream();
	             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = imgStream.read(buffer)) != -1) {
	                byteArrayOutputStream.write(buffer, 0, bytesRead);
	            }
	            byte[] imgBytes = byteArrayOutputStream.toByteArray();
	            return Base64.getEncoder().encodeToString(imgBytes);
	        }
	    }
/*
	 public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT cadusu.imglogo FROM cadusu");
		Connection conexao = ConexaoFactory.conectarMySqlUsuariosDirectus();

		PreparedStatement comando = conexao.prepareStatement(sql.toString());
		
		ResultSet resultado = comando.executeQuery();
		
		ArrayList< BufferedImage > listOfImages = new ArrayList< BufferedImage >();

		while (resultado.next()) {
			Blob image = resultado.getBlob("imgLogo");
			String x = ImageConverter.convertImageToBase64(image);
			System.out.println("bu"+x);
		}
		
	}
	*/
}
