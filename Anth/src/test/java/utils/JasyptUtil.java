package utils;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.jupiter.api.Test;

@Slf4j
public class JasyptUtil {

    @Test
    public void contextLoads() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //密钥salt
        textEncryptor.setPassword("1125");
        //要加密的数据（数据库的用户名或密码）
        String passwordCipher = textEncryptor.encrypt("root");
        System.out.println("passwordCipher = " + passwordCipher);
    }
    @Test
    public void hashcodeDemo() {
        String tLength = "7b955246-64aa-4330-a4e1-56c7faea4a7b".hashCode()+"";
        System.out.println("tLength = " + tLength);
    }

}
