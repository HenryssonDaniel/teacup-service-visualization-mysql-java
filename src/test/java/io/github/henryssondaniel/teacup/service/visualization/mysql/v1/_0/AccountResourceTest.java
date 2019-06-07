package io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

class AccountResourceTest {
  private static final String EMAIL = "{\"email\":\"admin@teacup.com\"}";
  private static final String ID = "id";
  private static final String LOG_IN =
      "{\"email\":\"admin@teacup.com\", \"password\":\"password\"}";
  private static final String PASSWORD = "password";
  private static final String PASS_WORD = BCrypt.hashpw("PassWord", BCrypt.gensalt());
  private static final String SIGN_UP =
      "{\"email\":\"admin@teacup.com\", \"firstName\":\"first\", \"lastName\":\"last\", \"password\":\"password\"}";
  private static final String UNSUCCESSFUL = "unsuccessful";

  private final DataSource dataSource = mock(DataSource.class);
  private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
  private final PreparedStatement preparedStatement = mock(PreparedStatement.class);
  private final ResultSet resultSet = mock(ResultSet.class);

  @Test
  void accountResource() {
    assertThat(new AccountResource()).isNotNull();
  }

  @BeforeEach
  void beforeEach() throws SQLException {
    setupResultSet();

    var connection = setupConnection(setupPreparedStatement());

    try (var conn = dataSource.getConnection()) {
      when(conn).thenReturn(connection);
    }

    when(httpServletRequest.getHeader(anyString())).thenReturn(null, "", "unknown");
  }

  @Test
  void logIn() throws SQLException {
    assertThat(callLogIn().getStatus()).isEqualTo(Status.OK.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verify(httpServletRequest, times(5)).getHeader(anyString());
    verify(httpServletRequest).getRemoteAddr();
    verifyNoMoreInteractions(httpServletRequest);

    verify(resultSet, times(2)).close();
    verify(resultSet, times(2)).getInt(ID);
    verify(resultSet).getInt(UNSUCCESSFUL);
    verify(resultSet).getString("email");
    verify(resultSet).getString("first_name");
    verify(resultSet).getString("last_name");
    verify(resultSet).getString(PASSWORD);
    verify(resultSet, times(2)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void logInNoMatch() throws SQLException {
    when(resultSet.getInt(UNSUCCESSFUL)).thenReturn(5);
    when(resultSet.getString(PASSWORD)).thenReturn(PASS_WORD);

    assertThat(callLogIn().getStatus()).isEqualTo(Status.NOT_ACCEPTABLE.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet, times(2)).close();
    verify(resultSet, times(2)).getInt(ID);
    verify(resultSet).getInt(UNSUCCESSFUL);
    verify(resultSet).getString(PASSWORD);
    verify(resultSet, times(2)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void logInTooManyUnsuccessful() throws SQLException {
    when(resultSet.getInt(UNSUCCESSFUL)).thenReturn(5);

    assertThat(callLogIn().getStatus()).isEqualTo(Status.NOT_ACCEPTABLE.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet, times(2)).close();
    verify(resultSet, times(2)).getInt(ID);
    verify(resultSet).getInt(UNSUCCESSFUL);
    verify(resultSet).getString(PASSWORD);
    verify(resultSet, times(2)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void logInWhenAccountDoesNotExist() throws SQLException {
    when(resultSet.next()).thenReturn(false);

    assertThat(callLogIn().getStatus()).isEqualTo(Status.UNAUTHORIZED.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet).close();
    verify(resultSet).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void logInWhenConnectionError() throws SQLException {
    connectionError();

    assertThat(callLogIn().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);
    verifyZeroInteractions(resultSet);
  }

  @Test
  void logInWhenNoLogIns() throws SQLException {
    when(httpServletRequest.getHeader(anyString())).thenReturn("test");
    when(resultSet.getString(PASSWORD)).thenReturn(PASS_WORD);
    when(resultSet.next()).thenReturn(true, false, true);

    assertThat(callLogIn().getStatus()).isEqualTo(Status.UNAUTHORIZED.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verify(httpServletRequest).getHeader(anyString());
    verifyNoMoreInteractions(httpServletRequest);

    verify(resultSet, times(3)).close();
    verify(resultSet).getInt(1);
    verify(resultSet).getInt(ID);
    verify(resultSet).getString(PASSWORD);
    verify(resultSet, times(3)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void logInWhenNoLogInsError() throws SQLException {
    when(resultSet.next()).thenReturn(true, false);

    assertThat(callLogIn().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet, times(3)).close();
    verify(resultSet).getInt(ID);
    verify(resultSet).getString(PASSWORD);
    verify(resultSet, times(3)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void recover() throws SQLException {
    assertThat(callRecover().getStatus()).isEqualTo(Status.OK.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verify(httpServletRequest, times(5)).getHeader(anyString());
    verify(httpServletRequest).getRemoteAddr();
    verifyNoMoreInteractions(httpServletRequest);

    verify(resultSet).close();
    verify(resultSet).getInt(ID);
    verify(resultSet).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void recoverWhenConnectionError() throws SQLException {
    connectionError();

    assertThat(callRecover().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);
    verifyZeroInteractions(resultSet);
  }

  @Test
  void recoverWhenNoAccount() throws SQLException {
    when(resultSet.next()).thenReturn(false);

    assertThat(callRecover().getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet).close();
    verify(resultSet).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void signUp() throws SQLException {
    when(resultSet.next()).thenReturn(false, true);

    assertThat(callSignUp().getStatus()).isEqualTo(Status.OK.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verify(httpServletRequest, times(5)).getHeader(anyString());
    verify(httpServletRequest).getRemoteAddr();
    verifyNoMoreInteractions(httpServletRequest);

    verify(resultSet, times(2)).close();
    verify(resultSet).getInt(1);
    verify(resultSet, times(2)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void signUpWhenAccountExists() throws SQLException {
    assertThat(callSignUp().getStatus()).isEqualTo(Status.CONFLICT.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet).close();
    verify(resultSet).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void signUpWhenAccountHistoryError() throws SQLException {
    when(resultSet.next()).thenReturn(false, true);
    when(preparedStatement.execute())
        .thenReturn(true, true, true)
        .thenThrow(new SQLException("test"))
        .thenReturn(true);

    assertThat(callSignUp().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verify(httpServletRequest, times(5)).getHeader(anyString());
    verify(httpServletRequest).getRemoteAddr();
    verifyNoMoreInteractions(httpServletRequest);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet, times(2)).close();
    verify(resultSet).getInt(1);
    verify(resultSet, times(2)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void signUpWhenAccountIdError() throws SQLException {
    when(resultSet.next()).thenReturn(false);

    assertThat(callSignUp().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet, times(2)).close();
    verify(resultSet, times(2)).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void signUpWhenConnectionError() throws SQLException {
    connectionError();

    assertThat(callSignUp().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);
    verifyZeroInteractions(resultSet);
  }

  @Test
  void verifyNoError() throws SQLException {
    assertThat(callVerify().getStatus()).isEqualTo(Status.OK.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verify(httpServletRequest, times(5)).getHeader(anyString());
    verify(httpServletRequest).getRemoteAddr();
    verifyNoMoreInteractions(httpServletRequest);

    verify(resultSet).close();
    verify(resultSet).getInt(ID);
    verify(resultSet).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void verifyWhenAccountIdError() throws SQLException {
    when(resultSet.next()).thenReturn(false);

    assertThat(callVerify().getStatus()).isEqualTo(Status.NO_CONTENT.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);

    verify(resultSet).close();
    verify(resultSet).next();
    verifyNoMoreInteractions(resultSet);
  }

  @Test
  void verifyWhenConnectionError() throws SQLException {
    connectionError();

    assertThat(callVerify().getStatus()).isEqualTo(Status.INTERNAL_SERVER_ERROR.getStatusCode());

    verify(dataSource).getConnection();
    verifyNoMoreInteractions(dataSource);

    verifyZeroInteractions(httpServletRequest);
    verifyZeroInteractions(resultSet);
  }

  private Response callLogIn() {
    return new AccountResource(dataSource).logIn(LOG_IN, httpServletRequest);
  }

  private Response callRecover() {
    return new AccountResource(dataSource).recover(EMAIL, httpServletRequest);
  }

  private Response callSignUp() {
    return new AccountResource(dataSource).signUp(SIGN_UP, httpServletRequest);
  }

  private Response callVerify() {
    return new AccountResource(dataSource).verify(EMAIL, httpServletRequest);
  }

  private void connectionError() throws SQLException {
    try (var conn = dataSource.getConnection()) {
      when(conn).thenThrow(new SQLException("test"));
    }
  }

  private static Connection setupConnection(PreparedStatement preparedStatement)
      throws SQLException {
    var connection = mock(Connection.class);

    try (var statement = connection.prepareStatement(anyString())) {
      when(statement).thenReturn(preparedStatement);
    }

    try (var statement =
        connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))) {
      when(statement).thenReturn(preparedStatement);
    }
    return connection;
  }

  private PreparedStatement setupPreparedStatement() throws SQLException {
    try (var query = preparedStatement.executeQuery()) {
      when(query).thenReturn(resultSet);
    }

    try (var generatedKeys = preparedStatement.getGeneratedKeys()) {
      when(generatedKeys).thenReturn(resultSet);
    }
    return preparedStatement;
  }

  private void setupResultSet() throws SQLException {
    when(resultSet.getString(PASSWORD)).thenReturn(BCrypt.hashpw(PASSWORD, BCrypt.gensalt()));
    when(resultSet.next()).thenReturn(true);
  }
}
