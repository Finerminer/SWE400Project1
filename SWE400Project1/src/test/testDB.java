package test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Test;

import domainModel.DB;

public class testDB {
	
	@Test
	public void testConnection() {
		Connection conn = DB.getConnection();
		assertNotNull(conn);
	}
}
