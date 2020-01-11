package ntut.csie.backlogItemAttachFileService.gateways.repository.backlogItemAttachFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import ntut.csie.backlogItemAttachFileService.gateways.database.BacklogItemAttachFileTable;
import ntut.csie.backlogItemAttachFileService.gateways.database.SqlDatabaseHelper;
import ntut.csie.backlogItemAttachFileService.model.backlogItemAttachFile.BacklogItemAttachFile;
import ntut.csie.backlogItemAttachFileService.useCase.backlogItemAttachFile.BacklogItemAttachFileRepository;

public class MySqlBacklogItemAttachFileRepositoryImpl implements BacklogItemAttachFileRepository {
	private SqlDatabaseHelper sqlDatabaseHelper;
	private BacklogItemAttachFileMapper backlogItemAttachFileMapper;
	
	public MySqlBacklogItemAttachFileRepositoryImpl() {
		sqlDatabaseHelper = new SqlDatabaseHelper();
		backlogItemAttachFileMapper = new BacklogItemAttachFileMapper();
	}

	@Override
	public synchronized void save(BacklogItemAttachFile backlogItemAttachFile) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			BacklogItemAttachFileData data = backlogItemAttachFileMapper.transformToBacklogItemAttachFileData(backlogItemAttachFile);
			String sql = String.format("Insert Into %s Values (?, ?, ?, ?, ?)", 
					BacklogItemAttachFileTable.tableName);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, data.getBacklogItemAttachFileId());
			preparedStatement.setInt(2, data.getOrderId());
			preparedStatement.setString(3, data.getName());
			preparedStatement.setString(4, data.getPath());
			preparedStatement.setString(5, data.getBacklogItemId());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		} catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the problem when save the attach file of the backlog item. Please try again!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized void remove(BacklogItemAttachFile backlogItemAttachFile) throws Exception {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		try {
			sqlDatabaseHelper.transactionStart();
			String sql = String.format("Delete From %s Where %s = '%s'",
					BacklogItemAttachFileTable.tableName,
					BacklogItemAttachFileTable.backlogItemAttachFileId,
					backlogItemAttachFile.getBacklogItemAttachFileId());
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		}  catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the problem when remove the attach file of the backlog item. Please try again!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized BacklogItemAttachFile getBacklogItemAttachFileById(String backlogItemAttachFileId) {
		sqlDatabaseHelper.connectToDatabase();
		ResultSet resultSet = null;
		BacklogItemAttachFile backlogItemAttachFile = null;
		try {
			String query = String.format("Select * From %s Where %s = '%s'",
					BacklogItemAttachFileTable.tableName,
					BacklogItemAttachFileTable.backlogItemAttachFileId,
					backlogItemAttachFileId);
			resultSet = sqlDatabaseHelper.getResultSet(query);
			if (resultSet.first()) {
				int orderId = resultSet.getInt(BacklogItemAttachFileTable.orderId);
				String name = resultSet.getString(BacklogItemAttachFileTable.name);
				String path = resultSet.getString(BacklogItemAttachFileTable.path);
				String backlogItemId = resultSet.getString(BacklogItemAttachFileTable.backlogItemId);
				
				BacklogItemAttachFileData data = new BacklogItemAttachFileData();
				data.setBacklogItemAttachFileId(backlogItemAttachFileId);
				data.setOrderId(orderId);
				data.setName(name);
				data.setPath(path);
				data.setBacklogItemId(backlogItemId);

				backlogItemAttachFile = backlogItemAttachFileMapper.transformToBacklogItemAttachFile(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.releaseConnection();
		}
		return backlogItemAttachFile;
	}
	
	@Override
	public synchronized Collection<BacklogItemAttachFile> getBacklogItemAttachFilesByBacklogItemId(String backlogItemId){
		sqlDatabaseHelper.connectToDatabase();
		ResultSet resultSet = null;
		Collection<BacklogItemAttachFile> backlogItemAttachFiles = new ArrayList<>();
		try {
			String query = String.format("Select * From %s Where %s = '%s' Order By %s",
					BacklogItemAttachFileTable.tableName, 
					BacklogItemAttachFileTable.backlogItemId, 
					backlogItemId, 
					BacklogItemAttachFileTable.orderId);
			resultSet = sqlDatabaseHelper.getResultSet(query);
			while (resultSet.next()) {
				String backlogItemAttachFileId = resultSet.getString(BacklogItemAttachFileTable.backlogItemAttachFileId);
				int orderId = resultSet.getInt(BacklogItemAttachFileTable.orderId);
				String name = resultSet.getString(BacklogItemAttachFileTable.name);
				String path = resultSet.getString(BacklogItemAttachFileTable.path);
				
				BacklogItemAttachFileData data = new BacklogItemAttachFileData();
				data.setBacklogItemAttachFileId(backlogItemAttachFileId);
				data.setOrderId(orderId);
				data.setName(name);
				data.setPath(path);
				data.setBacklogItemId(backlogItemId);

				BacklogItemAttachFile backlogItemAttachFile = backlogItemAttachFileMapper.transformToBacklogItemAttachFile(data);
				backlogItemAttachFiles.add(backlogItemAttachFile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.releaseConnection();
		}
		return backlogItemAttachFiles;
	}
}
