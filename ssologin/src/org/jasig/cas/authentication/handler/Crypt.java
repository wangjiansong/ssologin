package org.jasig.cas.authentication.handler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Crypt
  implements PasswordEncoder
{
  public String encode(String str)
  {
    String skey = "64074f968502295ca41b7db452c7c639";
    String ecd = "";
    try
    {
      ecd = EncryptDecryptData.encrypt(skey, str);
    }
    catch (InvalidKeyException e)
    {
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e)
    {
      e.printStackTrace();
    }
    catch (IllegalBlockSizeException e)
    {
      e.printStackTrace();
    }
    catch (BadPaddingException e)
    {
      e.printStackTrace();
    }
    catch (NoSuchPaddingException e)
    {
      e.printStackTrace();
    }
    catch (InvalidKeySpecException e)
    {
      e.printStackTrace();
    }
    return "^" + ecd + "^";
  }
}