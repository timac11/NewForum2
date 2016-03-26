
package logic;
import org.apache.commons.codec.digest.DigestUtils;
public class hash_password {
   public static String md5Apache(String password,String name,String mail) {
       String md5Hex;
       md5Hex = DigestUtils.md5Hex(password+name+mail);
   return md5Hex;
} 
}
