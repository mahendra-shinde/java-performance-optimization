package com.mahendra;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

// Intro : Java 16
record Department(String deptNo, String name) {};

public class Main2 {
		
	public static void main(String[] args) {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://98.70.245.250:3306/hr", "mahendra", "pass@1234");
			PreparedStatement ps = con.prepareStatement("INSERT into departments (dept_no, dept_name) values(?,?)");
			List<Department> depts = new LinkedList<Department>();
			depts.add(new Department("m001", "Income Tax"));
			depts.add(new Department("m002", "Overseas Marketing"));
			depts.add(new Department("m003", "Housekeeping"));
			
			for(Department d : depts) {
				ps.setString(1, d.deptNo());
				ps.setString(2, d.name());
				ps.addBatch();
				System.out.println("One record added to batch");
			}
			System.out.println("Send the batch for processing...");
			ps.executeBatch();
			System.out.println("Done !");
			ps.close();
			
		} catch (SQLException ex) {
			System.out.println("Error :" + ex.getMessage());
		} finally {
			try {
				con.close(); // Cascading effect: closes all "statements"
			} catch (SQLException ex) {
				System.out.println("Error while closing connection: " + ex.getMessage());
			}
		}
	}

}
