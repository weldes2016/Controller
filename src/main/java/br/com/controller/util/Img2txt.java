package br.com.controller.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.compress.utils.IOUtils;

public class Img2txt {
	
	public String base64="";
	public void converter(){
		
	
	try
	{
		InputStream iSteamReader = new FileInputStream("D:/IMAGENS/TGT.png");
		byte[] imageBytes = IOUtils.toByteArray(iSteamReader);
		base64 = Base64.getEncoder().encodeToString(imageBytes);
		System.out.println(base64);
	}catch(
	Exception e)
	{
		e.printStackTrace();
	}System.out.println("data:image/png;base64,"+base64);
	}
	
	public static void main(String[] args) {
		Img2txt img = new Img2txt();
		img.converter();
		
	}
}
