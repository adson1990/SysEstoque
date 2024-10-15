package com.adsonlucas.SysEstoque;

import java.util.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageToBase64 {

	 public static String convertImageToBase64(String caminhoImagem) throws IOException {
	        File file = new File(caminhoImagem);
	        FileInputStream fis = new FileInputStream(file);
	        byte[] bytes = new byte[(int) file.length()];
	        fis.read(bytes);
	        fis.close();

	        return Base64.getEncoder().encodeToString(bytes);
	    }

	    public static void main(String[] args) {
	        try {
	            String caminho = "/home/adson/Imagens/homem_feio_meme.png";
	            String imagemBase64 = convertImageToBase64(caminho);
	            System.out.println(imagemBase64);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

}
