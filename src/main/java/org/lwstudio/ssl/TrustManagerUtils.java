package org.lwstudio.ssl;

import java.io.FileInputStream;
import java.security.KeyStore;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class TrustManagerUtils {
  private static X509TrustManager cachedDefaultTrustManager;

  public static X509TrustManager getDefaultTrustManager() {
    if (cachedDefaultTrustManager == null) {
      try {
        // Using null here initialises the TMF with the default trust store.
        TrustManagerFactory tmf = initializeTrustManagerFactory((KeyStore) null);
        cachedDefaultTrustManager = fetchTrustManager(tmf);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }

    return cachedDefaultTrustManager;
  }

  public static X509TrustManager generateCustomTrustManager(String keyStorePath) {
    try (FileInputStream keys = new FileInputStream(keyStorePath)) {
      KeyStore trustStore = null;
      trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
      trustStore.load(keys, KeyStoreUtils.KEY_STORE_PASSWORD.toCharArray());
      TrustManagerFactory tmf = initializeTrustManagerFactory(trustStore);
      return fetchTrustManager(tmf);
    } catch (Exception exception) {
      exception.printStackTrace();
      return getDefaultTrustManager();
    }
  }

  private static TrustManagerFactory initializeTrustManagerFactory(KeyStore keystore)
      throws Exception {
    TrustManagerFactory tmf =
        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(keystore);
    return tmf;
  }

  private static X509TrustManager fetchTrustManager(TrustManagerFactory trustManagerFactory) {
    X509TrustManager targetTrustManager = null;
    for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
      if (trustManager instanceof X509TrustManager) {
        targetTrustManager = (X509TrustManager) trustManager;
        break;
      }
    }
    return targetTrustManager;
  }
}
