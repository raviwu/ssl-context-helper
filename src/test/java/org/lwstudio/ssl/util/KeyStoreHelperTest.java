package org.lwstudio.ssl.util;

import org.junit.Assert;
import org.junit.Test;

public class KeyStoreHelperTest {
  @Test
  public void geerateTempKeyStoreFromPem() throws Exception {
    String key = "/test-fake-pem";

    String generatedKeyFilePath =
        KeyStoreHelper.createTempKeyStoreFile(ResourcePathHelper.getPath(key));

    Assert.assertTrue(generatedKeyFilePath.contains(".jks"));
  }
}
