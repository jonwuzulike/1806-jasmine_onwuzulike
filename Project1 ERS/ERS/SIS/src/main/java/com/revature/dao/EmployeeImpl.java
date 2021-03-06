package com.revature.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.revature.pojos.Reimbursement;
import com.revature.pojos.Users;
import com.revature.util.ConnectionUtil;

public class EmployeeImpl implements EmpDao{

	
	// Get the information for an Employee.
	@Override
	public Users login(String email, String password) {
		Users usr = null;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				usr = new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(6));
				
			}
			else {
				System.out.println("Need some validation here");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usr;
	}

	// Get all reimbursements for each Employee.
	@Override
	public List<Reimbursement> getEmpReimb(int empId) {
		Reimbursement rb = null;
		List<Reimbursement> users = new ArrayList<Reimbursement>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM reimbursement LEFT JOIN rq_status ON reimbursement.rq_status_id = rq_status.rq_status_id LEFT JOIN users ON reimbursement.emp_u_id = users.u_id LEFT JOIN rq_type ON reimbursement.rq_type_id = rq_type.rq_type_id WHERE emp_u_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, empId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rb = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(18), rs.getString(4),rs.getString(10), rs.getString(12), rs.getString(13));
				users.add(rb);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return users;
	}

	//Create a new Reimbursement.
	@Override
	public int createNewReimbursement(double amount, String rDescription, int uId, int type) {
		
		int row = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
		String sql = "INSERT INTO reimbursement (amount, r_description, emp_u_id, rq_type_id) VALUES(?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDouble(1, amount);
		ps.setString(2, rDescription);
		ps.setInt(3, uId);
		ps.setInt(4, type);
		row = ps.executeUpdate();
		System.out.println(row + " reimbursements added");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return row;
		
	}

	
	//
	@Override
	public int updateFirstname(int uId, String firstname) {
		int first = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE users SET f_name = ? WHERE u_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uId);
			ps.setString(2, firstname);
			first = ps.executeUpdate();
			if (first !=0) {
				System.out.println("Success");
			}
			else {
				System.out.println("Failed.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return first;
	}

	
	//Allows a user to update last name.
	@Override
	public int updateLastName(int uId,String lastname) {
		int last = 0;
		try(Connection conn = ConnectionUtil.getConnection()) {
			String sql ="UPDATE users SET l_name = ? WHERE u_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uId);
			ps.setString(2, lastname);
			last = ps.executeUpdate();
			if (last !=0) {
				System.out.println("Success");
			}
			else {
				System.out.println("Failed.");
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return last;
	}

	
	//Allows a user to update password.
	@Override
	public int updatePassword(int uId, String password) {
			int pass = 0;
			try(Connection conn = ConnectionUtil.getConnection()) {
				String sql ="UPDATE users SET password = ? WHERE u_id = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, password);
				ps.setInt(2, uId);
				pass = ps.executeUpdate();
				if (pass !=0) {
					System.out.println("Success");
				}
				else {
					System.out.println("Failed.");
				}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pass;
	}

	@Override
	public String uploadReciept(int rId, BufferedImage image) {
		String img = "/SIS/src/main/resources/Img/invoice.png";
		String a = null;
		try {
			BufferedImage im = ImageIO.read(new File(img));
			if (im != null) {
				 a = "Yes";
				System.out.println(a);
			}
			else {
				a = "No";
				System.out.println(a);
			}
		} catch (IOException e) {
			System.out.println("File not found.");
		}
		return a;
	}
	
	
	

	}

	
