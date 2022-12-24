import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        final int TOKEN_LENGTH = 32;
        final String CHAR_LIST = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(2);
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; ++i) {
            int randomNumber = secureRandom.nextInt(CHAR_LIST.length());
            char ch = CHAR_LIST.charAt(randomNumber);
            randomString.append(ch);
        }

        System.out.println(randomString.toString());
    }
}
