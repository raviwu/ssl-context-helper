package org.lwstudio.ssl.util;

import org.junit.Assert;
import org.junit.Test;

public class SslPokerTest {
  private static SslPoker POKER = SslPoker.getInstance();

  @Test
  public void connectToValidHost() throws Exception {
    String url = "stackoverflow.com";

    Assert.assertTrue(POKER.test(url));
  }

  @Test
  public void connectToInvalidHost() throws Exception {
    String url = "localhost";

    Assert.assertFalse(POKER.test(url));
  }
}
