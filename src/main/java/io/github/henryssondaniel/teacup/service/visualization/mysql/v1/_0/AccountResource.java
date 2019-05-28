package io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0;

import static io.github.henryssondaniel.teacup.service.visualization.mysql.Utils.createMySqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Notification;
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

@Path("{a:v1/account|v1.0/account|account}")
public class AccountResource {
  private static final Logger LOGGER = Logger.getLogger(AccountResource.class.getName());

  private final DataSource dataSource;

  public AccountResource() {
    this(createMySqlDataSource());
  }

  AccountResource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("logIn")
  public Response logIn(String data, @Context HttpServletRequest httpServletRequest) {
    LOGGER.log(Level.FINE, "Log in");

    ResponseBuilder responseBuilder;

    try (var connection = dataSource.getConnection()) {
      responseBuilder = logIn(connection, data, httpServletRequest);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Could not initialize the database", e);
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
      LOGGER.log(Level.SEVERE, "Could not initialize the database", e);
      responseBuilder = Response.serverError();
    }

    return responseBuilder.build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("signUp")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response signUp(Notification notification) {
    LOGGER.log(Level.FINE, "Sign up");
    return Response.status(201).entity(notification).build();
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
      throw new SQLException("Could not retrieve the log ins ID");
    }
  }

  private static ResponseBuilder getResultSet(
      Connection connection, HttpServletRequest httpServletRequest, int id, boolean match)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            "SELECT * FROM `teacup_visualization`.`log_ins` WHERE account = ?")) {
      preparedStatement.setInt(1, id);

      insertLogIns(connection, httpServletRequest, id, match, preparedStatement);
    }

    return match ? Response.ok() : Response.status(Status.UNAUTHORIZED);
  }

  private static void insertLogIn(
      Connection connection, HttpServletRequest httpServletRequest, int logInsId, int successful)
      throws SQLException {
    try (var aaaa =
        connection.prepareStatement(
            "INSERT INTO `teacup_visualization`.`log_in`(ip, log_ins, successful) VALUES(?, ?, ?)")) {
      aaaa.setString(1, getIp(httpServletRequest));
      aaaa.setInt(2, logInsId);
      aaaa.setInt(3, successful);

      aaaa.execute();
    }
  }

  private static int insertLogIns(Connection connection, int id, boolean match)
      throws SQLException {
    try (var preparedStatement =
        connection.prepareStatement(
            "INSERT INTO `teacup_visualization`.`log_ins`(account, unsuccessful) VALUES(?, ?)",
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setInt(1, id);
      preparedStatement.setInt(2, match ? 0 : 1);

      preparedStatement.execute();

      return getLogInsId(preparedStatement);
    }
  }

  private static void insertLogIns(
      Connection connection,
      HttpServletRequest httpServletRequest,
      int id,
      boolean match,
      PreparedStatement preparedStatement)
      throws SQLException {
    try (var resultSet = preparedStatement.executeQuery()) {
      int logInsId;
      var unsuccessful = 0;

      if (resultSet.next()) {
        logInsId = resultSet.getInt("id");
        unsuccessful = match ? 0 : resultSet.getInt("unsuccessful") + 1;

        updateLogIns(connection, id, unsuccessful);
      } else logInsId = insertLogIns(connection, id, match);

      if (unsuccessful <= 5) insertLogIn(connection, httpServletRequest, logInsId, match ? 1 : 0);
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
        connection.prepareStatement(
            "INSERT INTO `teacup_visualization`.`recover`(account, ip) VALUES(?, ?)")) {
      preparedStatement.setInt(1, resultSet.getInt("id"));
      preparedStatement.setString(2, getIp(httpServletRequest));

      preparedStatement.execute();
    }
  }

  private static boolean isNotIp(String ip) {
    return ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip);
  }

  private static ResponseBuilder logIn(
      Connection connection, String data, HttpServletRequest httpServletRequest)
      throws SQLException {
    ResponseBuilder responseBuilder;

    try (var preparedStatement =
        connection.prepareStatement(
            "SELECT * FROM `teacup_visualization`.`account` WHERE email = ?")) {
      var jsonObject = new JSONObject(data);
      preparedStatement.setString(1, jsonObject.getString("email"));

      responseBuilder =
          logIn(
              connection, httpServletRequest, jsonObject.getString("password"), preparedStatement);
    }

    return responseBuilder;
  }

  private static ResponseBuilder logIn(
      Connection connection,
      HttpServletRequest httpServletRequest,
      String password,
      PreparedStatement preparedStatement)
      throws SQLException {
    ResponseBuilder responseBuilder;

    try (var resultSet = preparedStatement.executeQuery()) {
      responseBuilder =
          resultSet.next()
              ? getResultSet(
                  connection,
                  httpServletRequest,
                  resultSet.getInt("id"),
                  password.equals(resultSet.getString("password")))
              : Response.status(Status.UNAUTHORIZED);
    }

    return responseBuilder;
  }

  private static ResponseBuilder recover(
      Connection connection, String data, HttpServletRequest httpServletRequest)
      throws SQLException {
    ResponseBuilder responseBuilder;

    try (var preparedStatement =
        connection.prepareStatement(
            "SELECT * FROM `teacup_visualization`.`account` WHERE email = ?")) {
      var jsonObject = new JSONObject(data);
      preparedStatement.setString(1, jsonObject.getString("email"));

      responseBuilder = insertRecover(connection, httpServletRequest, preparedStatement);
    }

    return responseBuilder;
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
}
