package io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0;

import static io.github.henryssondaniel.teacup.service.visualization.mysql.Utils.createMySqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.Notification;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.json.JSONObject;

@Path("{a:v1/account|v1.0/account|account}")
public class AccountResource {
  private static final Logger LOGGER = Logger.getLogger(AccountResource.class.getName());
  private static final int UNAUTHORIZED = 401;

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
  public Response logIn(String data) {
    LOGGER.log(Level.FINE, "Log in");

    ResponseBuilder responseBuilder;

    try (var connection = dataSource.getConnection()) {
      responseBuilder = getResponseBuilder(connection, data);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Could not initialize the database", e);
      responseBuilder = Response.serverError();
    }

    return responseBuilder.build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("recover")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response recover(Notification notification) {
    LOGGER.log(Level.FINE, "Recover");
    return Response.status(201).entity(notification).build();
  }

  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  @Path("signUp")
  @Produces(MediaType.APPLICATION_JSON)
  public static Response signUp(Notification notification) {
    LOGGER.log(Level.FINE, "Sign up");
    return Response.status(201).entity(notification).build();
  }

  private static ResponseBuilder getResponseBuilder(Connection connection, String data)
      throws SQLException {
    ResponseBuilder responseBuilder;

    try (var preparedStatement =
        connection.prepareStatement(
            "SELECT * FROM `teacup_visualization`.`account` WHERE email = ? AND password = ?")) {
      var jsonObject = new JSONObject(data);
      preparedStatement.setString(1, jsonObject.getString("email"));
      preparedStatement.setString(2, jsonObject.getString("password"));

      responseBuilder = getResponseBuilder(preparedStatement);
    }

    return responseBuilder;
  }

  private static ResponseBuilder getResponseBuilder(PreparedStatement preparedStatement)
      throws SQLException {
    ResponseBuilder responseBuilder;

    try (var resultSet = preparedStatement.executeQuery()) {
      responseBuilder = resultSet.next() ? Response.ok() : Response.status(UNAUTHORIZED);
    }

    return responseBuilder;
  }
}
