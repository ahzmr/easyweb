package com.wenin819.easyweb.core.db;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by wenin819@gmail.com on 2014/4/5.
 */
public class DatabaseMetaDataTest {

  private Properties prop;

  @Before
  public void before() throws IOException {
    prop = PropertiesLoaderUtils.loadAllProperties("easyweb.properties");
  }

  @Test
  public void test() {
    try {
      Class.forName(prop.getProperty("jdbc.driver"));
      Properties tmpProp = System.getProperties();
      tmpProp.setProperty("user", prop.getProperty("jdbc.username"));
      tmpProp.setProperty("password", prop.getProperty("jdbc.password"));
      tmpProp.setProperty("remarksReporting", "true");
      tmpProp.setProperty("useInformationSchema", "true");
      Connection conn = DriverManager.getConnection(prop.getProperty("jdbc.url"), tmpProp);
      DatabaseMetaData metaData = conn.getMetaData();
      ResultSet rs = null;
//      ResultSet rs = metaData.getTables(null, null, "sys_user", new String[]{"TABLE"});
//      DbUtils.printResultSet(rs);
      System.out.printf("\n");
//      rs = metaData.getColumns(null, null, "sys_user", null);
      rs = metaData.getColumns(null, "MMC", "P_MNG_USER", null);
      DbUtils.printResultSet(rs);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
