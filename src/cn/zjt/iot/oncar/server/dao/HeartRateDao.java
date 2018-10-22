package cn.zjt.iot.oncar.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.zjt.iot.oncar.server.javabean.HeartRateGroup;
import cn.zjt.iot.oncar.server.util.DatabaseUtil;

public class HeartRateDao {

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
	public HeartRateDao(int id) {
		this.id = id;
	}

	/**
	 * @author Mr Dk.
	 * @return List<HeartRateGroup>
	 */
	public List<HeartRateGroup> QueryAllHeartRate() {
		List<HeartRateGroup> AllHeartRateRecords = new ArrayList<>();
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "SELECT * FROM " + DatabaseUtil.heartRateTablePre + id;

		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				HeartRateGroup heartRateGroup = new HeartRateGroup();
				heartRateGroup.setHeartRate_time(resultSet.getLong("time"));
				heartRateGroup.setHeartRate_record(resultSet.getString("heartrate"));
				AllHeartRateRecords.add(heartRateGroup);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return AllHeartRateRecords;
	}

	/**
	 * @author Mr Dk.
	 * @param HeartRateGroup heartRateGroup
	 * @return boolean {Success : true; Failure : false}
	 */
	public boolean InsertHeartRate(HeartRateGroup heartRateGroup) {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "INSERT INTO " + DatabaseUtil.heartRateTablePre + id + " (heartrate) VALUES (?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, heartRateGroup.getHeartRate_record());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * @author Mr Dk.
	 * @return boolean {Success : true; Failure : false}
	 */
	public boolean ClearAllHeartRate () {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "DELETE FROM " + DatabaseUtil.heartRateTablePre + id;

		try {
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}
}
