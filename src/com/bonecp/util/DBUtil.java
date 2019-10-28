package com.bonecp.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
 
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
/*
 * @author KK JavaTutorials
 * Utility class which is responsible to get JDBC connection object using 
 * BoneCP DataSource connection pool With MYSQL Database.
 */
public class DBUtil {
	private static final String DB_USERNAME="db.username";
	private static final String DB_PASSWORD="db.password";
	private static final String DB_URL ="db.url";
	private static final String DB_MINPAR ="db.min";
	private static final String DB_MAXPAR ="db.max";
	private static final String DB_PARTITIONCOUNT ="db.partitionCount";
	
	private static Properties properties = null;
	private static BoneCP dataSource;
	static{
		try {
			properties = new Properties();
			properties.load(new FileInputStream("C:\\ATT_SCHEDULAR\\att\\config_att.properties"));
			
			BoneCPConfig boneCPConfig = new BoneCPConfig();
			
		 	Class.forName("com.mysql.jdbc.Driver"); 
			boneCPConfig.setJdbcUrl(properties.getProperty(DB_URL));
			boneCPConfig.setUsername(properties.getProperty(DB_USERNAME));
			boneCPConfig.setPassword(properties.getProperty(DB_PASSWORD));
			
			boneCPConfig.setMinConnectionsPerPartition(Integer.parseInt(properties.getProperty(DB_MINPAR)));
			boneCPConfig.setMaxConnectionsPerPartition(Integer.parseInt(properties.getProperty(DB_MAXPAR)));
			boneCPConfig.setPartitionCount(Integer.parseInt(properties.getProperty(DB_PARTITIONCOUNT)));
			
			/*boneCPConfig.setMinConnectionsPerPartition(5);
			boneCPConfig.setMaxConnectionsPerPartition(100);
			boneCPConfig.setPartitionCount(4);*/
			boneCPConfig.setLazyInit(true);
			
			dataSource = new BoneCP(boneCPConfig);
			 
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}