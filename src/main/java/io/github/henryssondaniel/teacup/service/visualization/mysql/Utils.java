package io.github.henryssondaniel.teacup.service.visualization.mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import io.github.henryssondaniel.teacup.core.configuration.Factory;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Utils class for MySQL.
 *
 * @since 1.0
 */
public enum Utils {
  ;

  private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
  private static final String MYSQL_PROPERTY = "visualization.mysql.";
  private static final Properties PROPERTIES = Factory.getProperties();

  /**
   * Creates new MySQL data source.
   *
   * @return the data source
   * @since 1.0
   */
  public static DataSource createMySqlDataSource() {
    LOGGER.log(Level.FINER, "Create MySQL data source");

    var mysqlDataSource = new MysqlDataSource();
    mysqlDataSource.setPassword(PROPERTIES.getProperty(MYSQL_PROPERTY + "password"));
    mysqlDataSource.setServerName(PROPERTIES.getProperty(MYSQL_PROPERTY + "server.name"));
    mysqlDataSource.setUser(PROPERTIES.getProperty(MYSQL_PROPERTY + "user"));

    return mysqlDataSource;
  }
}
