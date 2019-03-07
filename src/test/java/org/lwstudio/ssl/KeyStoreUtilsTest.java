package org.lwstudio.ssl;

import org.junit.Assert;
import org.junit.Test;

public class KeyStoreUtilsTest {

  @Test
  public void geerateTempKeyStoreFromPem() throws Exception {
    String key = "/aws-root-ca-1.pem";

    String generatedKeyFilePath =
        KeyStoreUtils.createTempKeyStoreFile(ResourcePathUtils.getPath(key));

    Assert.assertTrue(generatedKeyFilePath.contains(".jks"));
  }
}
