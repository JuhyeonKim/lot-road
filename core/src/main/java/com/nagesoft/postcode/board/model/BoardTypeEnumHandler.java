package com.nagesoft.postcode.board.model;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardTypeEnumHandler extends BaseTypeHandler<BoardType>{

	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, BoardType refType, JdbcType jdbcType) throws SQLException {
		if (refType == null) {
			preparedStatement.setString(i, null);
		} else {
			preparedStatement.setString(i, refType.getCode());
		}
	}

	@Override
	public BoardType getNullableResult(ResultSet resultSet, String s) throws SQLException {
		return (resultSet.getObject(s) != null)? BoardType.getBoardType(resultSet.getString(s)): null;
	}

	@Override
	public BoardType getNullableResult(ResultSet resultSet, int i) throws SQLException {
		return (resultSet.getObject(i) != null)? BoardType.getBoardType(resultSet.getString(i)): null;
	}

	@Override
	public BoardType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		return (callableStatement.getObject(i) != null)? BoardType.getBoardType(callableStatement.getString(i)): null;
	}

}
