package org.lwstudio.ssl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class KeyStoreHelper {
  private static final String KEY_STORE_TYPE = "JKS";
  private static final String KEY_STORE_PROVIDER = "SUN";
  private static final String KEY_STORE_FILE_PREFIX = "tmp-cacerts";
  private static final String KEY_STORE_FILE_SUFFIX = ".jks";
  public static final String KEY_STORE_PASSWORD = "changeit";

  private KeyStoreHelper() {}

  public static String createTempKeyStoreFile(String pemCertificatePath) throws Exception {
    return createKeyStoreFile(createCertificate(pemCertificatePath)).getPath();
  }

  private static X509Certificate createCertificate(String pemCertificatePath) throws Exception {
    CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

    try (InputStream certInputStream = new FileInputStream(pemCertificatePath)) {
      return (X509Certificate) certFactory.generateCertificate(certInputStream);
    }
  }

  private static File createKeyStoreFile(X509Certificate rootX509Certificate) throws Exception {
    File keyStoreFile = getTempKeyStoreFileName();

    try (FileOutputStream fos = new FileOutputStream(keyStoreFile.getPath())) {
      KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE, KEY_STORE_PROVIDER);
      ks.load(null);
      ks.setCertificateEntry("rootCaCertificate", rootX509Certificate);
      ks.store(fos, KEY_STORE_PASSWORD.toCharArray());
    }

    return keyStoreFile;
  }

  private static File getTempKeyStoreFileName() throws Exception {
    String fileName = KEY_STORE_FILE_PREFIX + "-" + System.currentTimeMillis();
    return File.createTempFile(fileName, KEY_STORE_FILE_SUFFIX);
  }
}
