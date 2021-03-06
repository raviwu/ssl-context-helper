package org.lwstudio.ssl;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourcePathUtils {
  private ResourcePathUtils() {}

  public static String getPath(String resourceFileName) throws URISyntaxException {
    URL resource = ResourcePathUtils.class.getResource(resourceFileName);
    return Paths.get(resource.toURI()).toAbsolutePath().toString();
  }
}
