package cn.zjt.iot.oncar.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.zjt.iot.oncar.server.javabean.Temperature;
import cn.zjt.iot.oncar.server.util.DatabaseUtil;

public class TemperatureDao {

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
	public TemperatureDao(int id) {
		super();
		this.id = id;
	}
	
	/**
	 * @author Mr Dk.
	 * @return List <Temperature>
	 */
	public List<Temperature> QueryAllTemperature () {
		List <Temperature> AllTemperature = new ArrayList<>();
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "SELECT * FROM " + DatabaseUtil.temperatureTablePre + id;

		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Temperature temperature = new Temperature();
				temperature.setTemperature_time(resultSet.getLong("time"));
				temperature.setTemperature_record(resultSet.getString("temperature"));
				AllTemperature.add(temperature);
			}

		} catch (SQLException e) {
			//TODO: handle exception
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
				//TODO: handle exception
			}
		}

		return AllTemperature;
	}
	
	/**
	 * @author Mr Dk.
	 * @param Temperature temperature
	 * @return boolean {success : true, failure : false}
	 */
	public boolean InsertTemperature (Temperature temperature) {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "INSERT INTO " + DatabaseUtil.temperatureTablePre + id + " (temperature) VALUES (?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, temperature.getTemperature_record());
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
	 * @return boolean {success : true, failure : false}
	 */
	public boolean ClearAllTemperature () {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "DELETE FROM" + DatabaseUtil.temperatureTablePre + id;

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
				e.printStackTrace();
			}
		}

		return true;
	}
}