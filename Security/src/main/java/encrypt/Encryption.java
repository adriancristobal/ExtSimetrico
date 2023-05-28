package encrypt;

public interface Encryption {

    String encrypt(String texto, String secret);

    String decrypt(String texto, String secret);

}
