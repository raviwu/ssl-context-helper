package org.lwstudio.ssl;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SslPoker {
  private static SslPoker SSL_POKER = null;
  private static final int PORT = 443;

  private SslPoker() {}

  public static SslPoker getInstance() {
    if (SSL_POKER == null) {
      SSL_POKER = new SslPoker();

      return SSL_POKER;
    }

    return SSL_POKER;
  }

  public boolean test(String url) {
    System.out.println("[SslPoker] Poking: https://" + url);

    try {
      SSLSocketFactory localSSLSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

      SSLSocket localSSLSocket = (SSLSocket) localSSLSocketFactory.createSocket(url, PORT);

      java.io.InputStream localInputStream = localSSLSocket.getInputStream();
      java.io.OutputStream localOutputStream = localSSLSocket.getOutputStream();

      localOutputStream.write(1);

      while (localInputStream.available() > 0) {
        System.out.print(localInputStream.read());
      }

      System.out.println("[SslPoker] Success conection");

      return true;
    } catch (Exception exception) {
      System.out.println("[SslPoker] Error:" + exception.getMessage());
      exception.printStackTrace();

      return false;
    }
  }
}
