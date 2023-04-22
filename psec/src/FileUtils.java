import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class FileUtils {
    public static byte[] readAllBytes(String plaintextFileName) {
        byte[] bytesRead = {};
        try {
            bytesRead = Files.readAllBytes(Paths.get(plaintextFileName));
        } catch (Exception e) {}
        return bytesRead; // returns {} if file does not exist
    }
    public static void write(String transformation,
                             String plaintextFileName, byte[] output, byte[] salt, byte[] iv) {
        String outFile = "";
        String[] parts = transformation.split("/");
        if (parts.length == 3 && parts[0].equals("AES")) {
            outFile = plaintextFileName + ".aes."; //Base64.getEncoder().encodeToString(iv) + "." + Base64.getEncoder().encodeToString(salt);
        } else {}
        try {
            Files.write(Paths.get(outFile), output);
        } catch (Exception e) { e.printStackTrace(); }
    }


    public static byte[] generateIv (){
        SecureRandom secureRandom = null;
        try {
            secureRandom =
                    SecureRandom.getInstance("DEFAULT", "BC");
        } catch (Exception e){e.printStackTrace();}
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }

    public static SecretKey generateKey(char[] pass, byte[] salt){
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512", "BC");

            KeySpec spec = new PBEKeySpec(pass, salt, 100, 128);
            SecretKey secretkey = factory.generateSecret(spec);
            SecretKey aesKey = new SecretKeySpec(secretkey.getEncoded(), "AES");

            return aesKey;
        } catch (Exception e){e.printStackTrace();}

        return null;
    }
    public static byte[] generateSalt(){
        byte[] keyBytes = new byte[16];
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
            keyBytes = new byte[16];
            secureRandom.nextBytes(keyBytes);
        } catch (Exception e){e.printStackTrace();}

        return keyBytes;
    }

    public static String getIv(String path){
        String[] split = path.split("\\.");
        String iv = split[split.length-1];

        return iv;
    }

    public static String getSalt(String path) {
        String[] split = path.split("\\.");
        String salt = split[split.length-2];

        return salt;
    }
}