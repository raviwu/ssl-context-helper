package org.lwstudio.ssl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SslContextUtilsTest {
  private static SslPoker POKER = SslPoker.getInstance();

  private String awsRootCa1Pem = "/aws-root-ca-1.pem";
  private String awsRootCa2Pem = "/aws-root-ca-2.pem";
  private String awsRootCa3Pem = "/aws-root-ca-3.pem";
  private String validAwsRootCa1Url = "good.sca1a.amazontrust.com";
  private String validAwsRootCa2Url = "good.sca2a.amazontrust.com";
  private String validAwsRootCa3Url = "good.sca3a.amazontrust.com";
  private String awsRootCa1KeyStore;
  private String awsRootCa2KeyStore;
  private String awsRootCa3KeyStore;

  @Before
  public void setup() throws Exception {

    awsRootCa1KeyStore =
        KeyStoreUtils.createTempKeyStoreFile(ResourcePathUtils.getPath(awsRootCa1Pem));
    awsRootCa2KeyStore =
        KeyStoreUtils.createTempKeyStoreFile(ResourcePathUtils.getPath(awsRootCa2Pem));
    awsRootCa3KeyStore =
        KeyStoreUtils.createTempKeyStoreFile(ResourcePathUtils.getPath(awsRootCa3Pem));

    SslContextUtils.assignCustomKey(awsRootCa3KeyStore);
  }

  @Test
  public void defaultSslContextRoutine() throws Exception {
    Assert.assertTrue(POKER.test(validAwsRootCa3Url));
    Assert.assertFalse(POKER.test(validAwsRootCa1Url));
    Assert.assertFalse(POKER.test(validAwsRootCa2Url));
  }

  @Test
  public void appendCustomKeyStoreCa1() throws Exception {
    Assert.assertTrue(POKER.test(validAwsRootCa3Url));

    SslContextUtils.appendCustomKey(awsRootCa1KeyStore);

    Assert.assertTrue(POKER.test(validAwsRootCa1Url));

    Assert.assertFalse(POKER.test(validAwsRootCa2Url));
  }

  @Test
  public void appendCustomKeyStoreCa2() throws Exception {
    Assert.assertTrue(POKER.test(validAwsRootCa3Url));

    SslContextUtils.appendCustomKeys(new String[] {awsRootCa1KeyStore, awsRootCa2KeyStore});

    Assert.assertTrue(POKER.test(validAwsRootCa1Url));
    Assert.assertTrue(POKER.test(validAwsRootCa2Url));
  }

  @Test
  public void assignCustomKeyStoreCa1() throws Exception {
    Assert.assertTrue(POKER.test(validAwsRootCa3Url));

    SslContextUtils.appendCustomKey(awsRootCa2KeyStore);

    Assert.assertFalse(POKER.test(validAwsRootCa1Url));
    Assert.assertTrue(POKER.test(validAwsRootCa2Url));

    SslContextUtils.assignCustomKey(awsRootCa1KeyStore);

    Assert.assertTrue(POKER.test(validAwsRootCa1Url));
    Assert.assertFalse(POKER.test(validAwsRootCa2Url));
    Assert.assertFalse(POKER.test(validAwsRootCa3Url));
  }

  @After
  public void cleanup() throws Exception {
    SslContextUtils.assignCustomKey(awsRootCa3KeyStore);
  }
}
