import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class T2SHA {
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException {
//		FileInputStream video = new FileInputStream("FuncoesResumo - SHA1.mp4"); 
		FileInputStream video = new FileInputStream("FuncoesResumo - Hash Functions.mp4"); 
		
		int tamBloco = 1024;
        
        List<String> hexList = new ArrayList<>();

        while (true) {
            byte[] data = new byte[tamBloco];
            int bytesRead = video.read(data);
            
            if (bytesRead == -1) {
                break;
            }

            if (bytesRead < tamBloco) {
                tamBloco = bytesRead;
            }

            StringBuilder blockHex = new StringBuilder();
            for (byte b : data) {
                blockHex.append(String.format("%02x", b));
            }
            
            hexList.add(blockHex.toString());
        }
        
        video.close();

        // Instancia a classe de hashing
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

        // Define os valores hexadecimais a serem resumidos
        sha256.update(hexList.get(hexList.size() - 1).getBytes());
        
        // Armazena o valor do ultimo bloco em um array de bytes
        byte[] digest = sha256.digest();
        
        // Armazena esse array de bytes em uma string
        String sha256Ult = String.format("%064x", new java.math.BigInteger(1, digest));
     	
        for (int i = hexList.size() - 2; i > 0; i--) {
        	// Define o valor do bloco anterior + o bloco atual a ser resumido
        	sha256.update((hexList.get(i) + digest).getBytes());
        	
        	// Armazena o hash num array de bytes
        	digest = sha256.digest();
        }
        
        // Transforma o hash do primeiro bloco em uma string hexadecimal
        System.out.println(String.format("%064x", new java.math.BigInteger(1, digest)));
        System.out.println(sha256Ult);
	}
}
