package ntut.csie.backlogItemAttachFileService.gateways.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDatabaseHelper {
	private String serverUrl;
	private String databaseName;
	private String account;
	private String password;
	private Connection connection;
	private boolean transacting;
	
	public SqlDatabaseHelper() {
		serverUrl = "127.0.0.1";
		databaseName = "backlog_item_attach_file_service";
		account = "root";
		password = "root";
		transacting = false;
	}
	
	public void initialize() {
		createBacklogItemAttachFileServiceDatabase();
		ConnectionPool.getInstance().initialize(serverUrl, databaseName, account, password);
		createBacklogItemAttachFileTable();
	}
	
	public void connectToDatabase() {
		try {
			connection = ConnectionPool.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createBacklogItemAttachFileServiceDatabase() {
		Statement statement = null;
		String sql = "Create Database If Not Exists " + databaseName;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://"+serverUrl+":3306?useSSL=false&autoReconnect=true", account, password);
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			closeStatement(statement);
			closeConnection();
		}
	}
	
	private void createBacklogItemAttachFileTable() {
		connectToDatabase();
		Statement statement = null;
		String sql = "Create Table If Not Exists " + BacklogItemAttachFileTable.tableName + " ("
				+ BacklogItemAttachFileTable.backlogItemAttachFileId + " Varchar(50) Not Null, "
				+ BacklogItemAttachFileTable.orderId + " Integer Not Null, "
				+ BacklogItemAttachFileTable.name + " Varchar(50) Not Null, "
				+ BacklogItemAttachFileTable.path + " Varchar(256) Not Null, "
				+ BacklogItemAttachFileTable.backlogItemId + " Varchar(50) Not Null, "
				+ "Primary Key (" + BacklogItemAttachFileTable.backlogItemAttachFileId + ") "
				+ ")";
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(statement);
			releaseConnection();
		}
	}
	
	public boolean isTransacting() {
		return transacting;
	}
	
	public void transactionStart() throws SQLException {
		connection.setAutoCommit(false);
		transacting = true;
	}
	
	public void transactionError(){
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void transactionEnd() throws SQLException {
		connection.commit();
		connection.setAutoCommit(true);
		transacting = false;
	}
	
	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		return preparedStatement;
	}
	
	public ResultSet getResultSet(String query) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(query);
		return resultSet;
	}
	
	public void closeStatement(Statement statement) {
		if(statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closePreparedStatement(PreparedStatement preparedStatement) {
		if(preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void closeResultSet(ResultSet resultSet) {
		if(resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void closeConnection() {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void releaseConnection() {
		ConnectionPool.releaseConnection(connection);
	}
}
