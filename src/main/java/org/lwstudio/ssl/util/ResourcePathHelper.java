package org.lwstudio.ssl.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourcePathHelper {
  private ResourcePathHelper() {}

  public static String getPath(String resourceFileName) throws URISyntaxException {
    URL resource = ResourcePathHelper.class.getResource(resourceFileName);
    return Paths.get(resource.toURI()).toAbsolutePath().toString();
  }
}
