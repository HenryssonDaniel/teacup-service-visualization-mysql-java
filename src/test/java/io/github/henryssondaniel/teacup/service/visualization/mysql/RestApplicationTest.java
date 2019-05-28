package io.github.henryssondaniel.teacup.service.visualization.mysql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0.AccountResource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;

class RestApplicationTest {
  private final DataSource dataSource = mock(DataSource.class);

  @Test
  void getClasses() throws SQLException {
    var statement = mock(Statement.class);

    var connection = mock(Connection.class);
    try (var state = connection.createStatement()) {
      when(state).thenReturn(statement);
    }

    try (var connect = dataSource.getConnection()) {
      when(connect).thenReturn(connection);
    }

    createRestApplication();
  }

  @Test
  void getClassesWhenException() throws SQLException {
    try (var connect = dataSource.getConnection()) {
      when(connect).thenThrow(new SQLException("test"));
    }

    createRestApplication();
  }

  @Test
  void restApplication() {
    assertThat(new RestApplication()).isNotNull();
  }

  private void createRestApplication() {
    assertThat(new RestApplication(dataSource).getClasses())
        .containsOnlyElementsOf(Collections.singletonList(AccountResource.class));
  }
}
