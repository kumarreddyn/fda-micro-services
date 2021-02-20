package kumarreddyn.github.fda.user.util;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptDecryptUtil {
	
	private final Logger logger = LoggerFactory.getLogger(EncryptDecryptUtil.class);
	private String unicodeUTF8Format = "UTF8";
	private String desedeEncryptionScheme = "DESede";
	private String myEncryptionScheme;
	private KeySpec keySpec;
	private byte[] arrayBytes;	
	private SecretKeyFactory secretKeyFactory;
	private SecretKey key;
    private Cipher cipher;
    
  
	public EncryptDecryptUtil(@Value("${string.encrypt-decrypt.key}") String encryptDecryptKey) {
		myEncryptionScheme = desedeEncryptionScheme;
		try {
			arrayBytes = encryptDecryptKey.getBytes(unicodeUTF8Format);
			keySpec = new DESedeKeySpec(arrayBytes);
			secretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
			key = secretKeyFactory.generateSecret(keySpec);
			cipher = Cipher.getInstance(myEncryptionScheme);
		} catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException
				| NoSuchPaddingException e) {
			logger.error("Not able to create a cipher instance for password encryption: {}", e.getMessage());
		}
	}

	public String encrypt(String unencryptedString) {
        String encryptedString = StringUtils.EMPTY;
        if(!unencryptedString.isEmpty()){
        	try {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] plainText = unencryptedString.getBytes(unicodeUTF8Format);
                byte[] encryptedText = cipher.doFinal(plainText);
                encryptedString = new String(Base64.encodeBase64(encryptedText));
            } catch (Exception e) {
            	logger.error("Not able to encrypt the string: {} - {} ", unencryptedString, e.getMessage());
            }
        }
        return encryptedString;
    }


    public String decrypt(String encryptedString) {
        String decryptedText = StringUtils.EMPTY;
        if(!encryptedString.isEmpty()){
        	try {
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] encryptedText = Base64.decodeBase64(encryptedString);
                byte[] plainText = cipher.doFinal(encryptedText);
                decryptedText= new String(plainText);
            } catch (Exception e) {
            	logger.error("Not able to decrypt the string: {} - {} ", encryptedString, e.getMessage());
            }
        }
        return decryptedText;
    }   

}