package com.mahendra;

import java.sql.*;

public class Main1 {

	public static void main(String[] args) {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://98.70.245.250:3306/hr", "mahendra", "pass@1234");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select emp_no, first_name, last_name from employees limit 100 OFFSET 100");
			while (rs.next()) {
				System.out.println("Employee: " + rs.getInt(1) + ", " + rs.getString(2) + " " + rs.getString(3));
			}
			rs.close();
			st.close(); // Cascading effect: close all the result sets
			Statement st2 = con.createStatement();
			st2.execute("select 1");
			st2.close();

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
