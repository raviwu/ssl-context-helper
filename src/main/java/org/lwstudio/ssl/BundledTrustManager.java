package org.lwstudio.ssl;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

class BundledTrustManager implements X509TrustManager {
  private X509TrustManager defaultTrustManager = TrustManagerUtils.getDefaultTrustManager();
  private X509TrustManager[] customTrustManagerList;

  public BundledTrustManager(X509TrustManager[] customTrustManagerList) throws Exception {
    this.customTrustManagerList = customTrustManagerList;
  }

  @Override
  public void checkClientTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {
    checkClientTrustedInTrustManagerList(0, chain, authType);
  }

  private void checkClientTrustedInTrustManagerList(
      int index, X509Certificate[] chain, String authType) throws CertificateException {
    if (useFinalFallbackManager(index)) {
      determineFinalFallbackManager(index).checkClientTrusted(chain, authType);
    } else {
      try {
        customTrustManagerList[index].checkClientTrusted(chain, authType);
      } catch (CertificateException e) {
        checkClientTrustedInTrustManagerList(index + 1, chain, authType);
      }
    }
  }

  @Override
  public void checkServerTrusted(X509Certificate[] chain, String authType)
      throws CertificateException {
    checkServerTrustedInTrustManagerList(0, chain, authType);
  }

  private void checkServerTrustedInTrustManagerList(
      int index, X509Certificate[] chain, String authType) throws CertificateException {
    if (useFinalFallbackManager(index)) {
      determineFinalFallbackManager(index).checkServerTrusted(chain, authType);
    } else {
      try {
        customTrustManagerList[index].checkServerTrusted(chain, authType);
      } catch (CertificateException e) {
        checkServerTrustedInTrustManagerList(index + 1, chain, authType);
      }
    }
  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return defaultTrustManager.getAcceptedIssuers();
  }

  private boolean useFinalFallbackManager(int index) {
    return index == customTrustManagerList.length;
  }

  private X509TrustManager determineFinalFallbackManager(int index) {
    if (defaultTrustManager != null) {
      return defaultTrustManager;
    } else {
      return customTrustManagerList[index];
    }
  }
}
