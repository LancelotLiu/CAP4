package com.iisigroup.cap.auth.support;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.iisigroup.cap.auth.model.DefaultFunction;

/**
 * <pre>
 * CodeItem RowMapper
 * </pre>
 * 
 * @since 2014/4/30
 * @author Lancelot
 * @version
 *          <ul>
 *          <li>2014/4/30,Lancelot,new
 *          </ul>
 */
public class FunctionRowMapper implements RowMapper<DefaultFunction> {

    @Override
    public DefaultFunction mapRow(ResultSet rs, int rowNum) throws SQLException {
        DefaultFunction item = new DefaultFunction();
        item.setCode(rs.getInt("CODE"));
        item.setSysType(rs.getString("SYSTYPE"));
        item.setSequence(rs.getInt("SEQUENCE"));
        item.setLevel(rs.getInt("LEVEL"));
        item.setParent(rs.getInt("PARENT"));
        item.setName(rs.getString("NAME"));
        item.setPath(rs.getString("PATH"));
        item.setDescription(rs.getString("DESCRIPTION"));
        item.setStatus(rs.getString("STATUS"));
        item.setUpdater(rs.getString("UPDATER"));
        item.setUpdateTime(rs.getTimestamp("UPDATETIME"));
        return item;
    }

}
