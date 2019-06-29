package io.github.henryssondaniel.teacup.service.visualization.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0.AccountResource;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class RestApplicationTest {
  @Test
  void getClasses() {
    assertThat(new RestApplication().getClasses())
        .containsOnlyElementsOf(Collections.singletonList(AccountResource.class));
  }
}
