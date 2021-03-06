package com.servletStore.settings.standard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbconnect.DBConnection;
import com.servletStore.settings.school.model.SchoolPOJO;
import com.servletStore.settings.section.model.SectionPojo;

public class StandardImpl implements StandardDAO{

	DBConnection dbconnect=new DBConnection();
	Connection connection;
	PreparedStatement ps;
	
	public StandardImpl() 
	{
	
		connection=dbconnect.getConnection();

	}

	@Override
	public int addStandard(StandardPOJO standardPojo) 
	{
		int status=0;
		String query="insert into std_master(`name`) values (?)";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, standardPojo.getName());
			status = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	@Override
	public List<StandardPOJO> getStandardDetails() 
	{
		List<StandardPOJO> list=new ArrayList<StandardPOJO>();
		String query="SELECT `id`, `name` FROM `std_master";
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				//System.out.println("id "+rs.getInt("id") + " "+ rs.getString("name"));
				StandardPOJO stdpojo=new StandardPOJO();
				stdpojo.setId(rs.getInt("id"));
				stdpojo.setName(rs.getString("name"));
				list.add(stdpojo);			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	
	@Override
	public List<SchoolPOJO> getSchoolDetails() 
	{
		List<SchoolPOJO> list=new ArrayList<SchoolPOJO>();
		String query="SELECT `id`, `name` FROM `school_master";
		
		try {
			ps = connection.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next())
			{
				//System.out.println("id "+rs.getInt("id") + " "+ rs.getString("name"));
				SchoolPOJO schoolpojo=new SchoolPOJO();
				schoolpojo.setId(rs.getInt("id"));
				schoolpojo.setName(rs.getString("name"));
				list.add(schoolpojo);			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return list;
		
	}

	@Override
	public int addClass(StandardPOJO standardPojo, String schoolID, String sectionId){
		int r=0;
		String selectQuery = "SELECT `id` FROM `fk_school_section_details` WHERE school_id="+schoolID+" AND section_id="+sectionId;
		try {
			ps = connection.prepareStatement(selectQuery);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()){
				int fkId = rs.getInt(1);
				String insertQuery = "INSERT INTO `fk_class_master`(`std_id`, `fk_school_sec_id`) VALUES (?, ?)";
				ps = connection.prepareStatement(insertQuery);
				ps.setInt(1, standardPojo.getStdId());
				ps.setInt(2, fkId);
				r+=ps.executeUpdate();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	
		
}
