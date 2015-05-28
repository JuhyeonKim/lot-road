package com.nagesoft.postcode.common.handler;

import com.nagesoft.postcode.common.model.ref.RefType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * RefTypeEnumHandler
 *
 * @author Juhyeon Kim
 *
 */
public class RefTypeEnumHandler extends BaseTypeHandler<RefType> {

	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, RefType refType, JdbcType jdbcType) throws SQLException {
		if (refType == null) {
			preparedStatement.setString(i, null);
		} else {
			preparedStatement.setString(i, refType.getCode());
		}
	}

	@Override
	public RefType getNullableResult(ResultSet resultSet, String s) throws SQLException {
		return (resultSet.getObject(s) != null)? RefType.getRefType(resultSet.getString(s)): null;
	}

	@Override
	public RefType getNullableResult(ResultSet resultSet, int i) throws SQLException {
		return (resultSet.getObject(i) != null)? RefType.getRefType(resultSet.getString(i)): null;
	}

	@Override
	public RefType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		return (callableStatement.getObject(i) != null)? RefType.getRefType(callableStatement.getString(i)): null;
	}
}

