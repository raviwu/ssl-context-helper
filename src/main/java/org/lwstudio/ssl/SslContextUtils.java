package org.lwstudio.ssl;

import static org.lwstudio.ssl.TrustManagerUtils.generateCustomTrustManager;
import static org.lwstudio.ssl.TrustManagerUtils.getDefaultTrustManager;

import java.util.Arrays;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SslContextUtils {
  private static final String SSL_CONTEXT = "TLS";

  public static void reset() throws Exception {
    configureSslContext(getDefaultTrustManager());
  }

  public static void assignCustomKey(String keyPath) throws Exception {
    X509TrustManager customTm = generateCustomTrustManager(keyPath);

    configureSslContext(customTm);
  }

  public static void appendCustomKey(String keyPath) throws Exception {
    X509TrustManager[] customManagers =
        new X509TrustManager[] {generateCustomTrustManager(keyPath)};

    X509TrustManager customTm = new BundledTrustManager(customManagers);

    configureSslContext(customTm);
  }

  public static void appendCustomKeys(String[] keyPaths) throws Exception {
    X509TrustManager[] customManagers =
        Arrays.stream(keyPaths)
            .map(TrustManagerUtils::generateCustomTrustManager)
            .toArray(X509TrustManager[]::new);

    X509TrustManager customTm = new BundledTrustManager(customManagers);

    configureSslContext(customTm);
  }

  private static void configureSslContext(X509TrustManager trustManager) throws Exception {
    SSLContext sslContext = SSLContext.getInstance(SSL_CONTEXT);
    sslContext.init(null, new TrustManager[] {trustManager}, null);

    SSLContext.setDefault(sslContext);
  }
}
