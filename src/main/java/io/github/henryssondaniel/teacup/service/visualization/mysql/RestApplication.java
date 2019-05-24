package io.github.henryssondaniel.teacup.service.visualization.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import io.github.henryssondaniel.teacup.core.configuration.Factory;
import io.github.henryssondaniel.teacup.service.visualization.mysql.v1._0.AccountResource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api")
public class RestApplication extends Application {
  private static final Logger LOGGER = Logger.getLogger(RestApplication.class.getName());
  private static final String MYSQL_PROPERTY = "visualization.mysql.";
  private static final Properties PROPERTIES = Factory.getProperties();

  private final DataSource dataSource;

  public RestApplication() {
    this(createMysqlDataSource());
  }

  RestApplication(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Set<Class<?>> getClasses() {
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
              + "  `role` INT UNSIGNED NOT NULL,"
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

  private static void createAccountStatus(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`account_status` ("
              + "  `account` INT UNSIGNED NOT NULL,"
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `status` INT UNSIGNED NOT NULL,"
              + "  `time` DATETIME(3) NOT NULL DEFAULT now(3),"
              + "  PRIMARY KEY (`id`),"
              + "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,"
              + "  INDEX `account_status.account_idx` (`account` ASC) VISIBLE,"
              + "  INDEX `account_status.status_idx` (`status` ASC) VISIBLE,"
              + "  CONSTRAINT `account_status.account`"
              + "    FOREIGN KEY (`account`)"
              + "    REFERENCES `teacup_visualization`.`account` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION,"
              + "  CONSTRAINT `account_status.status`"
              + "    FOREIGN KEY (`status`)"
              + "    REFERENCES `teacup_visualization`.`status` (`id`)"
              + "    ON DELETE NO ACTION"
              + "    ON UPDATE NO ACTION);");
    }
  }

  private static void createLogIn(Connection connection) throws SQLException {
    try (var statement = connection.createStatement()) {
      statement.execute(
          "CREATE TABLE IF NOT EXISTS `teacup_visualization`.`log_in` ("
              + "  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,"
              + "  `ip` VARCHAR(15) NOT NULL,"
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
              + "  `unsuccesful` INT UNSIGNED NOT NULL DEFAULT 0,"
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

  private static DataSource createMysqlDataSource() {
    var mysqlDataSource = new MysqlDataSource();
    mysqlDataSource.setPassword(PROPERTIES.getProperty(MYSQL_PROPERTY + "password"));
    mysqlDataSource.setServerName(PROPERTIES.getProperty(MYSQL_PROPERTY + "server.name"));
    mysqlDataSource.setUser(PROPERTIES.getProperty(MYSQL_PROPERTY + "user"));

    return mysqlDataSource;
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

  private void initialize() {
    try (var connection = dataSource.getConnection()) {
      createSchema(connection);
      createAccount(connection);
      createLogIns(connection);
      createLogIn(connection);
      createRole(connection);
      createAccountRole(connection);
      createStatus(connection);
      createAccountStatus(connection);
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Could not initialize the database", e);
    }
  }
}