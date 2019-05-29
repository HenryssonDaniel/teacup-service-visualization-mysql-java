package io.github.henryssondaniel.teacup.service.visualization.mysql;

import static io.github.henryssondaniel.teacup.service.visualization.mysql.Utils.createMySqlDataSource;

import io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0.AccountResource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * REST application. This is the starting point for the REST server. All the resources will have the
 * /api/ in front of the path.
 *
 * @since 1.0
 */
@ApplicationPath("api")
public class RestApplication extends Application {
  private static final String ACCOUNT = "`account` INT UNSIGNED NOT NULL,";
  private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `teacup_visualization`";
  private static final String FOREIGN_KEY_ACCOUNT =
      " FOREIGN KEY (`account`) REFERENCES `teacup_visualization`.`account` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION";
  private static final String ID = "`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,";
  private static final Logger LOGGER = Logger.getLogger(RestApplication.class.getName());
  private static final String NO_ACTION = " ON DELETE NO ACTION ON UPDATE NO ACTION);";
  private static final String PRIMARY_KEY = " PRIMARY KEY (`id`),";
  private static final String TIME = "`time` TIMESTAMP(3) NOT NULL DEFAULT now(3),";
  private static final String UNIQUE_INDEX = " UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,";

  private final DataSource dataSource;

  /**
   * Constructor.
   *
   * @since 1.0
   */
  public RestApplication() {
    this(createMySqlDataSource());
  }

  RestApplication(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Set<Class<?>> getClasses() {
    LOGGER.log(Level.FINE, "Get classes");

    initialize();

    return new HashSet<>(Collections.singletonList(AccountResource.class));
  }

  private static void createAccount(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`account` ("
              + "  `email` VARCHAR(45) NOT NULL,"
              + "  `first_name` VARCHAR(45) NOT NULL,"
              + ID
              + "  `last_name` VARCHAR(45) NOT NULL,"
              + "  `password` VARCHAR(45) NOT NULL,"
              + PRIMARY_KEY
              + UNIQUE_INDEX
              + "  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);");
    }
  }

  private static void createAccountRole(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`account_role` ("
              + ACCOUNT
              + ID
              + "  `role` INT UNSIGNED NOT NULL DEFAULT 3,"
              + PRIMARY_KEY
              + UNIQUE_INDEX
              + "  INDEX `account_role.account_idx` (`account` ASC) VISIBLE,"
              + "  INDEX `account_role.role_idx` (`role` ASC) VISIBLE,"
              + "  CONSTRAINT `account_role.account`"
              + FOREIGN_KEY_ACCOUNT
              + ','
              + "  CONSTRAINT `account_role.role`"
              + "    FOREIGN KEY (`role`)"
              + "    REFERENCES `teacup_visualization`.`role` (`id`)"
              + NO_ACTION);
    }
  }

  private static void createLogIn(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`log_in` ("
              + ID
              + "  `ip` VARCHAR(39) NOT NULL,"
              + "  `log_ins` INT UNSIGNED NOT NULL,"
              + "  `successful` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,"
              + TIME
              + PRIMARY_KEY
              + UNIQUE_INDEX
              + "  INDEX `log_in.log_ins_idx` (`log_ins` ASC) VISIBLE,"
              + "  CONSTRAINT `log_in.log_ins`"
              + "    FOREIGN KEY (`log_ins`)"
              + "    REFERENCES `teacup_visualization`.`log_ins` (`id`)"
              + NO_ACTION);
    }
  }

  private static void createLogIns(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`log_ins` ("
              + ACCOUNT
              + ID
              + "  `unsuccessful` INT UNSIGNED NOT NULL DEFAULT 0,"
              + PRIMARY_KEY
              + UNIQUE_INDEX
              + "  UNIQUE INDEX `account_UNIQUE` (`account` ASC) VISIBLE,"
              + "  CONSTRAINT `log_ins.account`"
              + FOREIGN_KEY_ACCOUNT
              + ");");
    }
  }

  private static void createRecover(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`recover` ("
              + ACCOUNT
              + ID
              + "  `ip` VARCHAR(39) NOT NULL,"
              + TIME
              + PRIMARY_KEY
              + UNIQUE_INDEX
              + "  INDEX `recover.account_idx` (`account` ASC) VISIBLE,"
              + "  CONSTRAINT `recover.account`"
              + FOREIGN_KEY_ACCOUNT
              + ");");
    }
  }

  private static void createRole(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`role` ("
              + ID
              + "  `name` VARCHAR(45) NOT NULL,"
              + PRIMARY_KEY
              + "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);");
    }
  }

  private static void createSchema(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute("CREATE SCHEMA IF NOT EXISTS teacup_visualization");
    }
  }

  private static void createStatus(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`status` ("
              + ID
              + "  `name` VARCHAR(45) NOT NULL,"
              + PRIMARY_KEY
              + UNIQUE_INDEX
              + "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);");
    }
  }

  private static void createStatusHistory(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`status_history` ("
              + ACCOUNT
              + ID
              + "  `status` ENUM('active', 'banned', 'inactive') NOT NULL DEFAULT 'active',"
              + TIME
              + PRIMARY_KEY
              + UNIQUE_INDEX
              + "  INDEX `status_history.account_idx` (`account` ASC) VISIBLE,"
              + "  CONSTRAINT `status_history.account`"
              + FOREIGN_KEY_ACCOUNT
              + ");");
    }
  }

  private static void createVerified(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          CREATE_TABLE
              + ".`verified` ("
              + ACCOUNT
              + ID
              + TIME
              + PRIMARY_KEY
              + "  UNIQUE INDEX `account_UNIQUE` (`account` ASC) VISIBLE,"
              + UNIQUE_INDEX
              + "  CONSTRAINT `verified.account`"
              + FOREIGN_KEY_ACCOUNT
              + ");");
    }
  }

  private void initialize() {
    try (var connection = dataSource.getConnection()) {
      createSchema(connection);
      createAccount(connection);
      createLogIns(connection);
      createLogIn(connection);
      createRole(connection);

      insertRoles(connection);

      createAccountRole(connection);
      createStatus(connection);
      createStatusHistory(connection);
      createVerified(connection);
      createRecover(connection);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Could not initialize the database", e);
    }
  }

  private static void insertRoles(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "INSERT INTO `teacup_visualization`.`role`(name) VALUES('admin'), ('super'), ('user') ON DUPLICATE KEY UPDATE id=id");
    }
  }
}
