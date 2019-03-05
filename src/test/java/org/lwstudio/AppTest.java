package org.lwstudio;

import org.junit.Assert;
import org.junit.Test;

public class AppTest {
  @Test
  public void shouldAnswerWithTrue() {
    App placeholder = new App();
    App.main(null);

    Assert.assertTrue(placeholder instanceof App);
  }
}
