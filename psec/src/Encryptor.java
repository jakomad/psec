import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Provider;
import java.security.Security;
import java.util.Base64;

import org.bouncycastle.util.encoders.Encoder;
import org.bouncycastle.util.encoders.Hex;

public class Encryptor {

    public static void encryptFile(String filePath, char[] pass){
        try {
            byte[] iv = FileUtils.generateIv();
            System.out.println(iv);
            byte[] input = FileUtils.readAllBytes(filePath);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");

            //char[] pass = "pizza".toCharArray();
            System.out.println(pass);


            byte[] salt = FileUtils.generateSalt();

            cipher.init(Cipher.ENCRYPT_MODE, FileUtils.generateKey(pass, salt), new IvParameterSpec(iv));

            byte[] output = cipher.doFinal(input);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(output);
            stream.write(salt);
            stream.write(iv);
            byte[] out = stream.toByteArray();

            System.out.println("iv" + Base64.getEncoder().encodeToString(iv));
            System.out.println("salt" + Base64.getEncoder().encodeToString(salt));
            System.out.println("længde" + output.length);


            FileUtils.write("AES/CBC/PKCS5Padding", filePath, out, null, null);
        } catch (Exception e){e.printStackTrace();}
    }
}
