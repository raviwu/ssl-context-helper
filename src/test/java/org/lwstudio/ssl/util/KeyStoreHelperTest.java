package org.lwstudio.ssl.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import org.junit.Assert;
import org.junit.Test;

public class KeyStoreHelperTest {
  @Test
  public void connectToValidHost() throws Exception {
    String key = "/test-fake-pem";

    String generatedKeyFilePath = KeyStoreHelper.createTempKeyStoreFile(getPath(key));

    Assert.assertTrue(generatedKeyFilePath.contains(".jks"));
  }

  private String getPath(String resourceFileName) throws URISyntaxException {
    URL resource = KeyStoreHelperTest.class.getResource(resourceFileName);
    return Paths.get(resource.toURI()).toAbsolutePath().toString();
  }
}
