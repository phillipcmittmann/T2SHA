import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class T2SHA {
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException {
//		FileInputStream video = new FileInputStream("FuncoesResumo - SHA1.mp4"); 
		FileInputStream video = new FileInputStream("FuncoesResumo - Hash Functions.mp4"); 
		
		int tamBloco = 1024;
        
        Stack<byte[]> bytesStk = new Stack<>();

        while (true) {
            byte[] data = new byte[tamBloco];
            int bytesRead = video.read(data);
            
            if (bytesRead == -1) {
                break;
            }

            if (bytesRead < tamBloco) {
                tamBloco = bytesRead;
                byte[] aux = new byte[bytesRead];
                aux = Arrays.copyOf(data, aux.length);
                
                bytesStk.add(aux);
            } else {
                bytesStk.push(data);	
            }
        }
        
        video.close();

        // Instancia a classe de hashing
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        
        // Define os valores hexadecimais a serem resumidos
        sha256.reset();
        sha256.update(bytesStk.peek());
        
        // Armazena o valor do ultimo bloco em um array de bytes
        byte[] digest = sha256.digest();
        
        // Armazena esse array de bytes em uma string
        String ult = String.format("%064x", new java.math.BigInteger(1, digest));

        for (int i = bytesStk.size() - 2; i >= 0; i--) {
        	sha256.reset();

        	sha256.update(bytesStk.get(i));
        	sha256.update(digest, 0, 32);
        	
        	digest = sha256.digest();
        }
        
        System.out.println(String.format("%064x", new java.math.BigInteger(1, digest)));
        System.out.println(ult);
	}
}
