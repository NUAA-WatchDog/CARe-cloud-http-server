package cn.zjt.iot.oncar.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.zjt.iot.oncar.server.javabean.Weight;
import cn.zjt.iot.oncar.server.util.DatabaseUtil;

public class WeightDao {
	
	private DatabaseUtil databaseUtil;
	private String sql;
	private PreparedStatement ps;
	private Connection conn;
	private ResultSet resultSet;
	
	int id;

	/**
	 * @author Mr Dk.
	 * @param int id
	 */
	public WeightDao(int id) {
		super();
		this.id = id;
	}
	
	/**
	 * @author Mr Dk.
	 * @return List <Weight>
	 */
	public List<Weight> QueryAllWeight() {
		List<Weight> AllWeight = new ArrayList<>();
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "SELECT * FROM " + DatabaseUtil.weightTablePre + id;
		
		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Weight weight = new Weight();
				weight.setWeight_time(resultSet.getLong("time"));
				weight.setWeight_value(resultSet.getInt("weight"));
				AllWeight.add(weight);
			}

		} catch (SQLException e) {
			//TODO: handle exception
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//TODO: handle exception
			}
		}

		return AllWeight;
	}

	/**
	 * @author Mr Dk.
	 * @param Weight weight
	 * @return boolean {Success : true; Failure : false}
	 */
	public boolean InsertWeight (Weight weight) {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "INSERT INTO " + DatabaseUtil.weightTablePre + id + " (weight) VALUES (?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setFloat(1, weight.getWeight_value());
			ps.executeUpdate();
		} catch (SQLException e) {
			//TODO: handle exception
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//TODO: handle exception
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * @author Mr Dk.
	 * @return boolean {Success : true; Failure : false}
	 */
	public boolean ClearAllWeight () {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "DELETE FROM " + DatabaseUtil.weightTablePre + id;

		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			//TODO: handle exception
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				//TODO: handle exception
			}
		}

		return true;
	}
}
