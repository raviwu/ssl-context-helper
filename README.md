# SSL Context Helper

A simple SSL context helper that can import custom pem keys in addition to default key store. Inspired by [this stackoverflow discussion](https://stackoverflow.com/questions/24555890/using-a-custom-truststore-in-java-as-well-as-the-default-one).

The idea is inserting custom pem key(s) through Java mechanism, so that you don't need to bother the `keytool` operation on the system side.

## Usage

```java
String pemKeyPackedInResources = "/your-custom-key.pem";
String pemKeyAbsolutPath = ResourcePathUtils.getPath(awsRootCa1Pem);

// Can pass absolute path of your key during Runtime.
String generatedTempKeyStorePath = awsRootCa1KeyStore =
        KeyStoreUtils.createTempKeyStoreFile(pemKeyAbsolutPath);

SslContextUtils.appendCustomKeys(new String[] {generatedTempKeyStorePath});

SslContextUtils.appendCustomKey(generatedTempKeyStorePath);

// In case you need to reset your SslContext back to default:
SslContextUtils.reset();

// In case you need to replace default manager with your key:
SslContextUtils.assignCustomKey(generatedTempKeyStorePath);
```

## Develop

Compile, test, build, and code analysis:

```
mvn clean verify
```
