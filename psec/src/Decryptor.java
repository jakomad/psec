import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

public class Decryptor {
    public static void decryptFile (String filePath, char[] pass){
        try {
            //char[] pass = "pizza".toCharArray();

            byte[] input = FileUtils.readAllBytes(filePath);
            //System.out.println(pass.toString());
            byte[] iv = Arrays.copyOfRange(input, input.length-16, input.length); //iv length is hardcoded to 16
            byte[] salt = Arrays.copyOfRange(input, input.length-16-16, input.length-16);
            byte[] trimmedInput = Arrays.copyOfRange(input, 0, input.length-16-16);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

            cipher.init(Cipher.DECRYPT_MODE, FileUtils.generateKey(pass, salt), new IvParameterSpec(iv));

            System.out.println("iv" + Base64.getEncoder().encodeToString(iv));
            //System.out.println("kode" + Base64.getEncoder().encodeToString(pass));
            System.out.println("salt" + Base64.getEncoder().encodeToString(salt));
            System.out.println("længde" + trimmedInput.length);

            byte[] output = cipher.doFinal(trimmedInput);

            FileUtils.write("AES/CBC/PKCS5Padding", filePath+"test", output,null,null);
        } catch (Exception e) { e.printStackTrace();}
    }
}
