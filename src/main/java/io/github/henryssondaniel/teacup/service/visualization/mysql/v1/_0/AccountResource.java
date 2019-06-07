package io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0;

import static io.github.henryssondaniel.teacup.service.visualization.mysql.Utils.createMySqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Account resource. Handles account related requests.
 *
 * @since 1.0
 */
@Path("{a:v1/account|v1.0/account|account}")
public class AccountResource {
  private static final String ACCOUNT_WHERE_EMAIL = "`account` WHERE email = ?";
  private static final String DELETE = "DELETE FROM `teacup_visualization`.";
  private static final String EMAIL = "email";
  private static final String ERROR = "An error occurred during %s";
  private static final String ERROR_RETRIEVE = "Could not retrieve the %s";
  private static final String ID = "id";
  private static final String INSERT = "INSERT INTO `teacup_visualization`.";
  private static final Logger LOGGER = Logger.getLogger(AccountResource.class.getName());
  private static final String SECRET = "password";
  private static final String SELECT_ID = "SELECT id FROM `teacup_visualization`.";

  private final DataSource dataSource;

  /**
   * Constructor.
   *
   * @since 1.0
   */
  public AccountResource() {
    this(createMySqlDataSource());
  }

  AccountResource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("changePassword")
  public Response changePassword(String data, @Context HttpServletRequest httpServletRequest) {
    LOGGER.log(Level.FINE, "Change password");

    ResponseBuilder responseBuilder;

    try (var connection = dataSource.getConnection()) {
      responseBuilder = changePassword(connection, data, httpServletRequest);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, String.format(ERROR, "change password"), e);
      responseBuilder = Response.serverError();
    }

    return responseBuilder.build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("logIn")
  @Produces(MediaType.APPLICATION_JSON)
  public Response logIn(String data, @Context HttpServletRequest httpServletRequest) {
    LOGGER.log(Level.FINE, "Log in");

    ResponseBuilder responseBuilder;

    try (var connection = dataSource.getConnection()) {
      responseBuilder = logIn(connection, data, httpServletRequest);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, String.format(ERROR, "log in"), e);
      responseBuilder = Response.serverError();
    }

    return responseBuilder.build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("recover")
  public Response recover(String data, @Context HttpServletRequest httpServletRequest) {
    LOGGER.log(Level.FINE, "Recover");

    ResponseBuilder responseBuilder;

    try (var connection = dataSource.getConnection()) {
      responseBuilder = recover(connection, data, httpServletRequest);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, String.format(ERROR, "recover"), e);
      responseBuilder = Response.serverError();
    }

    return responseBuilder.build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("signUp")
  public Response signUp(String data, @Context HttpServletRequest httpServletRequest) {
    LOGGER.log(Level.FINE, "Sign up");

    ResponseBuilder responseBuilder;

    try (var connection = dataSource.getConnection()) {
      responseBuilder = signUp(connection, data, httpServletRequest);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, String.format(ERROR, "sign up"), e);
      responseBuilder = Response.serverError();
    }

    return responseBuilder.build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("verify")
  @Produces(MediaType.APPLICATION_JSON)
  public Response verify(String data, @Context HttpServletRequest httpServletRequest) {
    LOGGER.log(Level.FINE, "Verify");

    ResponseBuilder responseBuilder;

    try (var connection = dataSource.getConnection()) {
      responseBuilder = verify(connection, data, httpServletRequest);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, String.format(ERROR, "verify"), e);
      responseBuilder = Response.serverError();
    }

    return responseBuilder.build();
  }

  private static ResponseBuilder changePassword(
      Connection connection, String data, HttpServletRequest httpServletRequest)
      throws SQLException {
    try (var preparedStatement = connection.prepareStatement(SELECT_ID + ACCOUNT_WHERE_EMAIL)) {
      preparedStatement.setString(1, new JSONObject(data).getString(EMAIL));

      return insertPasswordHistory(connection, data, httpServletRequest, preparedStatement);
    }
  }

  private static String getIp(HttpServletRequest httpServletRequest) {
    var ip = httpServletRequest.getHeader("X-Forwarded-For");

    if (isNotIp(ip)) ip = httpServletRequest.getHeader("Proxy-Client-IP");
    if (isNotIp(ip)) ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
    if (isNotIp(ip)) ip = httpServletRequest.getHeader("HTTP_CLIENT_IP");
    if (isNotIp(ip)) ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
    if (isNotIp(ip)) ip = httpServletRequest.getRemoteAddr();

    return ip;
  }

  private static int getLogInsId(Statement statement) throws SQLException {
    try (var resultSet = statement.getGeneratedKeys()) {
      if (resultSet.next()) return resultSet.getInt(1);
      throw new SQLException(String.format(ERROR_RETRIEVE, "log ins ID"));
    }
  }

  private static ResponseBuilder insertAccount(
      Connection connection,
      String email,
      HttpServletRequest httpServletRequest,
      JSONObject jsonObject)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            INSERT + "`account`(email, first_name, last_name) VALUES(?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, email);
      preparedStatement.setString(2, jsonObject.getString("firstName"));
      preparedStatement.setString(3, jsonObject.getString("lastName"));

      preparedStatement.execute();

      insertSubAccountRows(connection, httpServletRequest, jsonObject, preparedStatement);
    }

    return Response.ok();
  }

  private static void insertAccountRole(Connection connection, int id) throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(INSERT + "`account_role` SET account = ?")) {
      preparedStatement.setInt(1, id);
      preparedStatement.execute();
    }
  }

  private static void insertLogIn(
      Connection connection, HttpServletRequest httpServletRequest, int logInsId, int successful)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(INSERT + "`log_in`(ip, log_ins, successful) VALUES(?, ?, ?)")) {
      preparedStatement.setString(1, getIp(httpServletRequest));
      preparedStatement.setInt(2, logInsId);
      preparedStatement.setInt(3, successful);

      preparedStatement.execute();
    }
  }

  private static int insertLogIns(Connection connection, int id, boolean match)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            INSERT + "`log_ins`(account, unsuccessful) VALUES(?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, match ? 0 : 1);

      preparedStatement.execute();

      return getLogInsId(preparedStatement);
    }
  }

  private static ResponseBuilder insertLogIns(
      Connection connection,
      HttpServletRequest httpServletRequest,
      String password,
      PreparedStatement preparedStatement,
      ResultSet resultSet)
      throws SQLException {
    var id = resultSet.getInt(ID);
    preparedStatement.setInt(1, id);

    try (var executeQuery = preparedStatement.executeQuery()) {
      int logInsId;
      var unsuccessful = 0;

      var match = BCrypt.checkpw(password, resultSet.getString(SECRET));

      if (executeQuery.next()) {
        logInsId = executeQuery.getInt(ID);

        unsuccessful = executeQuery.getInt("unsuccessful");
        unsuccessful = match && unsuccessful < 5 ? 0 : unsuccessful + 1;

        if (unsuccessful <= 5) updateLogIns(connection, id, unsuccessful);
      } else logInsId = insertLogIns(connection, id, match);

      ResponseBuilder responseBuilder;

      if (unsuccessful <= 5) {
        insertLogIn(connection, httpServletRequest, logInsId, match ? 1 : 0);

        responseBuilder =
            match
                ? Response.ok()
                    .entity(
                        "{\"email\":\""
                            + resultSet.getString(EMAIL)
                            + "\", \"firstName\":\""
                            + resultSet.getString("first_name")
                            + "\", \"id\":\""
                            + id
                            + "\", \"lastName\":\""
                            + resultSet.getString("last_name")
                            + "\"}")
                : Response.status(Status.UNAUTHORIZED);
      } else responseBuilder = Response.status(Status.NOT_ACCEPTABLE);

      return responseBuilder;
    }
  }

  private static ResponseBuilder insertPasswordHistory(
      Connection connection,
      String data,
      HttpServletRequest httpServletRequest,
      PreparedStatement preparedStatement)
      throws SQLException {
    ResponseBuilder responseBuilder;

    try (var resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        var jsonObject = new JSONObject(data);

        insertPasswordHistory(
            jsonObject.getBoolean("authorized"),
            connection,
            httpServletRequest,
            resultSet.getInt(ID),
            jsonObject);

        responseBuilder = Response.ok();
      } else responseBuilder = Response.status(Status.NO_CONTENT);
    }

    return responseBuilder;
  }

  private static void insertPasswordHistory(
      boolean authorized,
      Connection connection,
      HttpServletRequest httpServletRequest,
      int id,
      JSONObject jsonObject)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            INSERT + "`password_history`(account, authorized, ip, password) VALUES(?, ?, ?, ?)")) {
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, authorized ? 1 : 0);
      preparedStatement.setString(3, getIp(httpServletRequest));
      preparedStatement.setString(4, BCrypt.hashpw(jsonObject.getString(SECRET), BCrypt.gensalt()));

      preparedStatement.execute();
    }
  }

  private static ResponseBuilder insertRecover(
      Connection connection,
      HttpServletRequest httpServletRequest,
      PreparedStatement preparedStatement)
      throws SQLException {
    ResponseBuilder responseBuilder;

    try (var resultSet = preparedStatement.executeQuery()) {
      if (resultSet.next()) {
        insertRecover(connection, httpServletRequest, resultSet);

        responseBuilder = Response.ok();
      } else responseBuilder = Response.status(Status.NO_CONTENT);
    }

    return responseBuilder;
  }

  private static void insertRecover(
      Connection connection, HttpServletRequest httpServletRequest, ResultSet resultSet)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(INSERT + "`recover`(account, ip) VALUES(?, ?)")) {
      preparedStatement.setInt(1, resultSet.getInt(ID));
      preparedStatement.setString(2, getIp(httpServletRequest));

      preparedStatement.execute();
    }
  }

  private static void insertStatusHistory(Connection connection, int id) throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(INSERT + "`status_history` SET account = ?")) {
      preparedStatement.setInt(1, id);
      preparedStatement.execute();
    } catch (SQLException e) {
      try (var preparedStatement =
          connection.prepareStatement(DELETE + "`account_role` WHERE account = ?")) {
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
      }

      throw e;
    }
  }

  private static void insertSubAccountRows(
      Connection connection,
      HttpServletRequest httpServletRequest,
      JSONObject jsonObject,
      Statement statement)
      throws SQLException {
    try (var generatedKeys = statement.getGeneratedKeys()) {
      if (generatedKeys.next())
        insertSubAccountRows(connection, httpServletRequest, generatedKeys.getInt(1), jsonObject);
      else throw new SQLException(String.format(ERROR_RETRIEVE, "account ID"));
    }
  }

  private static void insertSubAccountRows(
      Connection connection, HttpServletRequest httpServletRequest, int id, JSONObject jsonObject)
      throws SQLException {
    try {
      insertPasswordHistory(false, connection, httpServletRequest, id, jsonObject);
      insertAccountRole(connection, id);
      insertStatusHistory(connection, id);
    } catch (SQLException e) {
      try (var preparedStatement = connection.prepareStatement(DELETE + "`account` WHERE id = ?")) {
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
      }

      throw e;
    }
  }

  private static boolean isNotIp(String ip) {
    return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
  }

  private static ResponseBuilder logIn(
      Connection connection,
      HttpServletRequest httpServletRequest,
      String password,
      ResultSet resultSet)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            "SELECT id, unsuccessful FROM `teacup_visualization`.`log_ins` WHERE account = ?")) {
      return insertLogIns(connection, httpServletRequest, password, preparedStatement, resultSet);
    }
  }

  private static ResponseBuilder logIn(
      Connection connection, String data, HttpServletRequest httpServletRequest)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            "SELECT email, first_name, `teacup_visualization`.`account`.id, last_name, password "
                + "FROM `teacup_visualization`.`account` INNER JOIN "
                + "`teacup_visualization`.`password_history` ON "
                + "`teacup_visualization`.`account`.id = "
                + "`teacup_visualization`.`password_history`.account WHERE email = ? ORDER BY "
                + "`teacup_visualization`.`password_history`.id DESC LIMIT 1")) {
      var jsonObject = new JSONObject(data);
      preparedStatement.setString(1, jsonObject.getString(EMAIL));

      return logIn(connection, httpServletRequest, jsonObject.getString(SECRET), preparedStatement);
    }
  }

  private static ResponseBuilder logIn(
      Connection connection,
      HttpServletRequest httpServletRequest,
      String password,
      PreparedStatement preparedStatement)
      throws SQLException {
    try (var resultSet = preparedStatement.executeQuery()) {
      return resultSet.next()
          ? logIn(connection, httpServletRequest, password, resultSet)
          : Response.status(Status.UNAUTHORIZED);
    }
  }

  private static ResponseBuilder recover(
      Connection connection, String data, HttpServletRequest httpServletRequest)
      throws SQLException {
    try (var preparedStatement = connection.prepareStatement(SELECT_ID + ACCOUNT_WHERE_EMAIL)) {
      preparedStatement.setString(1, new JSONObject(data).getString(EMAIL));

      return insertRecover(connection, httpServletRequest, preparedStatement);
    }
  }

  private static ResponseBuilder signUp(
      Connection connection,
      HttpServletRequest httpServletRequest,
      JSONObject jsonObject,
      PreparedStatement preparedStatement)
      throws SQLException {
    var email = jsonObject.getString(EMAIL);
    preparedStatement.setString(1, email);

    try (var resultSet = preparedStatement.executeQuery()) {
      return resultSet.next()
          ? Response.status(Status.CONFLICT)
          : insertAccount(connection, email, httpServletRequest, jsonObject);
    }
  }

  private static ResponseBuilder signUp(
      Connection connection, String data, HttpServletRequest httpServletRequest)
      throws SQLException {
    try (var preparedStatement = connection.prepareStatement(SELECT_ID + ACCOUNT_WHERE_EMAIL)) {
      return signUp(connection, httpServletRequest, new JSONObject(data), preparedStatement);
    }
  }

  private static void updateLogIns(Connection connection, int id, int unsuccessful)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            "UPDATE `teacup_visualization`.`log_ins` SET unsuccessful = ? WHERE id = ?")) {
      preparedStatement.setInt(1, unsuccessful);
      preparedStatement.setInt(2, id);

      preparedStatement.execute();
    }
  }

  private static ResponseBuilder verify(
      Connection connection, String data, HttpServletRequest httpServletRequest)
      throws SQLException {
    try (var preparedStatement = connection.prepareStatement(SELECT_ID + ACCOUNT_WHERE_EMAIL)) {
      preparedStatement.setString(1, new JSONObject(data).getString(EMAIL));

      return verify(connection, httpServletRequest, preparedStatement);
    }
  }

  private static ResponseBuilder verify(
      Connection connection,
      HttpServletRequest httpServletRequest,
      PreparedStatement preparedStatement)
      throws SQLException {
    try (var resultSet = preparedStatement.executeQuery()) {
      return resultSet.next()
          ? verify(connection, httpServletRequest, resultSet)
          : Response.status(Status.NO_CONTENT);
    }
  }

  private static ResponseBuilder verify(
      Connection connection, HttpServletRequest httpServletRequest, ResultSet resultSet)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            "INSERT INTO `teacup_visualization`.`verified`(account, ip) VALUES(?, ?)")) {
      preparedStatement.setInt(1, resultSet.getInt(ID));
      preparedStatement.setString(2, getIp(httpServletRequest));

      preparedStatement.execute();
    }

    return Response.ok();
  }
}
