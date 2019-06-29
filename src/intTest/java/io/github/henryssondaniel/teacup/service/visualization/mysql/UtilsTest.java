package io.github.henryssondaniel.teacup.service.visualization.mysql;

import static org.assertj.core.api.Assertions.assertThat;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.Test;

class UtilsTest {
  @Test
  void createMySqlDataSource() {
    assertThat(Utils.createMySqlDataSource()).isExactlyInstanceOf(MysqlDataSource.class);
  }
}
