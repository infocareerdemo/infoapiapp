package com.info.service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;


@Service
public class CommonService {
	
	 private final JdbcTemplate jdbcTemplate;

	   @Autowired
	    public CommonService(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }
	
	    
	 public List<Object> getDataFromTable() {
	     String sql = "SELECT username,email FROM users";
	        return jdbcTemplate.query(sql, new RowMapper<Object>() {
	            @Override
	            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	               
	                return new Object[]{rs.getString("username"), rs.getString("email")};
	            }
	        });
	    }
	 
	    
	    
	    
	    
	 public List<Object[]> getAllDataFromTable() {
	    String sql = "SELECT * FROM users";

	    List<Object[]> dataList = jdbcTemplate.query(sql, new RowMapper<Object[]>() {
	        @Override
	        public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
	            ResultSetMetaData metaData = rs.getMetaData();
	            int columnCount = metaData.getColumnCount();
	            
	            Object[] row = new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	                row[i - 1] = rs.getObject(i);
	            }
	            
	            return row;
	        }
	    });

	    return dataList;
	}

	

	
}
