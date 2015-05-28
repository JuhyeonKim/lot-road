package com.nagesoft.postcode.core.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {

	private static final String YES = "y";
	private static final String NO = "n";

	public void setNonNullParameter(PreparedStatement ps, int i,
			Boolean parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			ps.setString(i, null);
		} else {
			ps.setString(i, parameter ? YES : NO);
		}
	}

	public Boolean getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		if (rs.getString(columnName) == null)
			return null;
		return rs.getString(columnName).equals(YES);
	}

	public Boolean getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		if (cs.getString(columnIndex) == null)
			return null;
		return cs.getString(columnIndex).equals(NO);
	}

	public Boolean getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		if (rs.getString(columnIndex) == null)
			return null;
		return rs.getString(columnIndex).equals(YES);
	}

}
