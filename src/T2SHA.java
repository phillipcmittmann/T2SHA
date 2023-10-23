import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class T2SHA {
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException {
		FileInputStream video = new FileInputStream("FuncoesResumo - SHA1.mp4"); 
        
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

        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

        sha256.update(hexList.get(hexList.size() - 1).getBytes());
        
        byte[] digest = sha256.digest();
        
        String sha256Ult = String.format("%064x", new java.math.BigInteger(1, digest));
     	
        for (int i = hexList.size() - 2; i > 0; i--) {
        	sha256.update((hexList.get(i) + digest).getBytes());
        	
        	digest = sha256.digest();
        }
        
        System.out.println(String.format("%064x", new java.math.BigInteger(1, digest)));
        System.out.println(sha256Ult);
	}
}
