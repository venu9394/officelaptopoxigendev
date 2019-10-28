package com.hhcl.job;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class Util_mssql {
public static Properties loadPropertiesFile() throws Exception {
Properties prop = new Properties();
InputStream in = new FileInputStream("C:\\ATT_SCHEDULAR\\BULK\\Shift\\config_att.properties");
prop.load(in);
in.close();
return prop;
}
public static  Connection getConnection() {
System.out.println("create jdbc connection using properties file");
Connection conSql = null;
try {
Properties prop = loadPropertiesFile();
String driverClass = prop.getProperty("DRIVER");
String url = prop.getProperty("DBURL");
String username = prop.getProperty("DBUSER");
String password = prop.getProperty("DBPWD");
Class.forName(driverClass);
conSql = DriverManager.getConnection(url, username, password);
if (conSql != null) {
//System.out.println("connection created successfully using properties file");
}
else {
System.out.println(" unable to create connection");
}
}catch (SQLException e) {
e.printStackTrace();
}
catch (Exception e) {
e.printStackTrace();
} /*finally {
try {
if (con != null) {
con.close();
}
} catch (Exception ex) {
ex.printStackTrace();
}
}*/
return conSql;
}
}
 