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
  private static final Logger LOGGER = Logger.getLogger(RestApplication.class.getName());
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
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`account` ("
              + "  `email` VARCHAR(45) NOT NULL,"
              + "  `first_name` VARCHAR(45) NOT NULL,"
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `last_name` VARCHAR(45) NOT NULL,"
              + "  `password` VARCHAR(45) NOT NULL,"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);");
    }
  }

  private static void createAccountRole(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`account_role` ("
              + "  `account` INT UNSIGNED NOT NULL,"
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `role` INT UNSIGNED NOT NULL DEFAULT 3,"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  INDEX `account_role.account_idx` (`account` ASC) VISIBLE,"
              + "  INDEX `account_role.role_idx` (`role` ASC) VISIBLE,"
              + "  CONSTRAINT `account_role.account`"
              + "    FOREIGN KEY (`account`)"
              + "    REFERENCES `teacup_visualization`.`account` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION,"
              + "  CONSTRAINT `account_role.role`"
              + "    FOREIGN KEY (`role`)"
              + "    REFERENCES `teacup_visualization`.`role` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION);");
    }
  }

  private static void createLogIn(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`log_in` ("
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `ip` VARCHAR(39) NOT NULL,"
              + "  `log_ins` INT UNSIGNED NOT NULL,"
              + "  `successful` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1,"
              + "  `time` DATETIME(3) NOT NULL DEFAULT now(3),"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  INDEX `log_in.log_ins_idx` (`log_ins` ASC) VISIBLE,"
              + "  CONSTRAINT `log_in.log_ins`"
              + "    FOREIGN KEY (`log_ins`)"
              + "    REFERENCES `teacup_visualization`.`log_ins` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION);");
    }
  }

  private static void createLogIns(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`log_ins` ("
              + "  `account` INT UNSIGNED NOT NULL,"
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `unsuccessful` INT UNSIGNED NOT NULL DEFAULT 0,"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  UNIQUE INDEX `account_UNIQUE` (`account` ASC) VISIBLE,"
              + "  CONSTRAINT `log_ins.account`"
              + "    FOREIGN KEY (`account`)"
              + "    REFERENCES `teacup_visualization`.`account` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION);");
    }
  }

  private static void createRecover(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`recover` ("
              + "  `account` INT UNSIGNED NOT NULL,"
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `ip` VARCHAR(39) NOT NULL,"
              + "  `time` TIMESTAMP(3) NOT NULL DEFAULT now(3),"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  INDEX `recover.account_idx` (`account` ASC) VISIBLE,"
              + "  CONSTRAINT `recover.account`"
              + "    FOREIGN KEY (`account`)"
              + "    REFERENCES `teacup_visualization`.`account` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION);");
    }
  }

  private static void createRole(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`role` ("
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `name` VARCHAR(45) NOT NULL,"
              + "  PRIMARY KEY (`id`),"
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
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`status` ("
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `name` VARCHAR(45) NOT NULL,"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE);");
    }
  }

  private static void createStatusHistory(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`status_history` ("
              + "  `account` INT UNSIGNED NOT NULL,"
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `status` ENUM('active', 'banned', 'inactive') NOT NULL DEFAULT 'active',"
              + "  `time` DATETIME(3) NOT NULL DEFAULT now(3),"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  INDEX `status_history.account_idx` (`account` ASC) VISIBLE,"
              + "  CONSTRAINT `status_history.account`"
              + "    FOREIGN KEY (`account`)"
              + "    REFERENCES `teacup_visualization`.`account` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION);");
    }
  }

  private static void createVerified(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`verified` ("
              + "  `account` INT UNSIGNED NOT NULL,"
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `time` TIMESTAMP(3) NOT NULL DEFAULT now(3),"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `account_UNIQUE` (`account` ASC) VISIBLE,"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  CONSTRAINT `verified.account`"
              + "    FOREIGN KEY (`account`)"
              + "    REFERENCES `teacup_visualization`.`account` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION);");
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
