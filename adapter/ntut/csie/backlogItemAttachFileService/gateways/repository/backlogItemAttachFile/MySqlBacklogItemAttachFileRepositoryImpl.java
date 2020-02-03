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
			preparedStatement.setString(2, data.getName());
			preparedStatement.setString(3, data.getPath());
			preparedStatement.setString(4, data.getBacklogItemId());
			preparedStatement.setString(5, data.getCreateTime());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		} catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the database problem when save the attach file of the backlog item. Please contact to the system administrator!");
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
			BacklogItemAttachFileData data = backlogItemAttachFileMapper.transformToBacklogItemAttachFileData(backlogItemAttachFile);
			String sql = String.format("Delete From %s Where %s = ?",
					BacklogItemAttachFileTable.tableName,
					BacklogItemAttachFileTable.backlogItemAttachFileId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, data.getBacklogItemAttachFileId());
			preparedStatement.executeUpdate();
			sqlDatabaseHelper.transactionEnd();
		}  catch(SQLException e) {
			sqlDatabaseHelper.transactionError();
			e.printStackTrace();
			throw new Exception("Sorry, there is the database problem when remove the attach file of the backlog item. Please contact to the system administrator!");
		} finally {
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
	}

	@Override
	public synchronized BacklogItemAttachFile getBacklogItemAttachFileById(String backlogItemAttachFileId) {
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		BacklogItemAttachFile backlogItemAttachFile = null;
		try {
			String sql = String.format("Select * From %s Where %s = ?",
					BacklogItemAttachFileTable.tableName,
					BacklogItemAttachFileTable.backlogItemAttachFileId);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, backlogItemAttachFileId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				String name = resultSet.getString(BacklogItemAttachFileTable.name);
				String path = resultSet.getString(BacklogItemAttachFileTable.path);
				String backlogItemId = resultSet.getString(BacklogItemAttachFileTable.backlogItemId);
				String createTime = resultSet.getString(BacklogItemAttachFileTable.createTime);
				
				BacklogItemAttachFileData data = new BacklogItemAttachFileData();
				data.setBacklogItemAttachFileId(backlogItemAttachFileId);
				data.setName(name);
				data.setPath(path);
				data.setBacklogItemId(backlogItemId);
				data.setCreateTime(createTime);

				backlogItemAttachFile = backlogItemAttachFileMapper.transformToBacklogItemAttachFile(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
		return backlogItemAttachFile;
	}
	
	@Override
	public synchronized Collection<BacklogItemAttachFile> getBacklogItemAttachFilesByBacklogItemId(String backlogItemId){
		sqlDatabaseHelper.connectToDatabase();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Collection<BacklogItemAttachFile> backlogItemAttachFiles = new ArrayList<>();
		try {
			String sql = String.format("Select * From %s Where %s = ? Order By %s",
					BacklogItemAttachFileTable.tableName, 
					BacklogItemAttachFileTable.backlogItemId, 
					BacklogItemAttachFileTable.createTime);
			preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
			preparedStatement.setString(1, backlogItemId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String backlogItemAttachFileId = resultSet.getString(BacklogItemAttachFileTable.backlogItemAttachFileId);
				String name = resultSet.getString(BacklogItemAttachFileTable.name);
				String path = resultSet.getString(BacklogItemAttachFileTable.path);
				String createTime = resultSet.getString(BacklogItemAttachFileTable.createTime);
				
				BacklogItemAttachFileData data = new BacklogItemAttachFileData();
				data.setBacklogItemAttachFileId(backlogItemAttachFileId);
				data.setName(name);
				data.setPath(path);
				data.setBacklogItemId(backlogItemId);
				data.setCreateTime(createTime);

				BacklogItemAttachFile backlogItemAttachFile = backlogItemAttachFileMapper.transformToBacklogItemAttachFile(data);
				backlogItemAttachFiles.add(backlogItemAttachFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlDatabaseHelper.closeResultSet(resultSet);
			sqlDatabaseHelper.closePreparedStatement(preparedStatement);
			sqlDatabaseHelper.releaseConnection();
		}
		return backlogItemAttachFiles;
	}
}
