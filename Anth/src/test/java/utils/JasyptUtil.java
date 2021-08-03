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

}
