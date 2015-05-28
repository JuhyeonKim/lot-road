package com.nagesoft.postcode.member.model.handler;

import com.nagesoft.postcode.member.model.RoleType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 관리자 권한 Enum Handler
 *
 * @author Juhyeon Kim
 *
 */
public class RoleTypeEnumHandler extends BaseTypeHandler<RoleType> {

	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, RoleType roleType, JdbcType jdbcType)
		throws SQLException {
		if (roleType == null) {
			preparedStatement.setString(i, null);
		} else {
			preparedStatement.setString(i, roleType.getCode());
		}
	}

	@Override
	public RoleType getNullableResult(ResultSet resultSet, String s) throws SQLException {
		return (resultSet.getObject(s) != null)? RoleType.getRoleType(resultSet.getString(s)): null;
	}

	@Override
	public RoleType getNullableResult(ResultSet resultSet, int i) throws SQLException {
		return (resultSet.getObject(i) != null)? RoleType.getRoleType(resultSet.getString(i)): null;
	}

	@Override
	public RoleType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
		return (callableStatement.getObject(i) != null)? RoleType.getRoleType(callableStatement.getString(i)): null;
	}
}
