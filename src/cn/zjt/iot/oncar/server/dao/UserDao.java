package cn.zjt.iot.oncar.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.zjt.iot.oncar.server.javabean.User;
import cn.zjt.iot.oncar.server.util.DatabaseUtil;

public class UserDao {

	private DatabaseUtil databaseUtil;
	private String sql;
	private PreparedStatement ps;
	private Connection conn;
	private ResultSet resultSet;

	public List<User> QueryAllUser() {
		List<User> AllUser = new ArrayList<>();
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "SELECT * FROM oncar_user";

		try {
			ps = conn.prepareStatement(sql);
			resultSet = ps.executeQuery();

			while (resultSet.next()) {
				User user = new User();
				user.setUser_id(resultSet.getInt("id"));
				user.setUser_name(resultSet.getString("username"));
				user.setUser_nickname(resultSet.getString("nickname"));
				user.setUser_password(resultSet.getString("password"));
				user.setUser_EMERcontact_1(resultSet.getString("EMERcontact_1"));
				user.setUser_EMERcontact_2(resultSet.getString("EMERcontact_2"));
				user.setUser_height(resultSet.getInt("height"));
				user.setUser_hr_version(resultSet.getInt("hr_version"));
				user.setUser_tp_version(resultSet.getInt("tp_version"));
				AllUser.add(user);
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

		return AllUser;
	}

	/**
	 * @author Mr Dk.
	 * @param int id
	 * @return User
	 */
	public User QueryUserByID(int id) {
		User user = new User();
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "SELECT * FROM oncar_user WHERE id = ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			resultSet = ps.executeQuery();

			if (resultSet.next()) {
				user.setUser_id(resultSet.getInt("id"));
				user.setUser_name(resultSet.getString("username"));
				user.setUser_nickname(resultSet.getString("nickname"));
				user.setUser_password(resultSet.getString("password"));
				user.setUser_EMERcontact_1(resultSet.getString("EMERcontact_1"));
				user.setUser_EMERcontact_2(resultSet.getString("EMERcontact_2"));
				user.setUser_height(resultSet.getInt("height"));
				user.setUser_hr_version(resultSet.getInt("hr_version"));
				user.setUser_tp_version(resultSet.getInt("tp_version"));
			} else {
				user.setUser_id(DatabaseUtil.USER_NOT_EXIST);
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

		return user;
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user
	 */
	public void QueryUserByUsername(User user) {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "SELECT * FROM oncar_user WHERE username = ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_name());
			resultSet = ps.executeQuery();

			if (resultSet.next()) {
				user.setUser_id(resultSet.getInt("id"));
				user.setUser_nickname(resultSet.getString("nickname"));
				user.setUser_password(resultSet.getString("password"));
				user.setUser_EMERcontact_1(resultSet.getString("EMERcontact_1"));
				user.setUser_EMERcontact_2(resultSet.getString("EMERcontact_2"));
				user.setUser_height(resultSet.getInt("height"));
				user.setUser_hr_version(resultSet.getInt("hr_version"));
				user.setUser_tp_version(resultSet.getInt("tp_version"));
			} else {
				user.setUser_id(DatabaseUtil.USER_NOT_EXIST);
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user [If the username has existed, return id with INSERT_NOT_UNIQUE]
	 */
	public void CreateUser(User user) {

		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "INSERT INTO oncar_user (username, nickname, password, EMERcontact_1, EMERcontact_2, height, hr_version, tp_version) "
				+ "VALUES (?, ?, ?, ?, ?, ?, 0, 0)";

		try {
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUser_name());
			ps.setString(2, user.getUser_nickname());
			ps.setString(3, user.getUser_password());
			ps.setString(4, user.getUser_EMERcontact_1());
			ps.setString(5, user.getUser_EMERcontact_2());
			ps.setInt(6, user.getUser_height());
			ps.executeUpdate();

			resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				user.setUser_id(resultSet.getInt(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			user.setUser_id(DatabaseUtil.INSERT_NOT_UNIQUE);
			return;
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			if (user.getUser_id() != DatabaseUtil.INSERT_NOT_UNIQUE) {
				sql = "CREATE TABLE " + DatabaseUtil.heartRateTablePre + user.getUser_id() +
					  " (id bigint PRIMARY KEY IDENTITY(1,1), heartrate VARCHAR(64) NOT NULL);" + 
					  "CREATE TABLE " + DatabaseUtil.temperatureTablePre + user.getUser_id() + 
					  " (id bigint PRIMARY KEY IDENTITY(1,1), temperature VARCHAR(128) NOT NULL);" +
					  "CREATE TABLE " + DatabaseUtil.weightTablePre + user.getUser_id() + 
					  " (id bigint PRIMARY KEY IDENTITY(1,1), weight float NOT NULL);";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user [If failure happens in updating, the id of returned object will be UPDATE_USER_FAILURE]
	 */
	public void UpdateUserByID(User user) {

		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "UPDATE oncar_user SET " + "nickname = ?, password = ?, EMERcontact_1 = ?, EMERcontact_2 = ?, height = ? "
				+ "WHERE id = ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_nickname());
			ps.setString(2, user.getUser_password());
			ps.setString(3, user.getUser_EMERcontact_1());
			ps.setString(4, user.getUser_EMERcontact_2());
			ps.setInt(5, user.getUser_height());
			ps.setInt(6, user.getUser_id());
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			return;
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user [If failure happens in updating, the id of returned object will be UPDATE_USER_FAILURE]
	 */
	public void UpdateUserNickNameByID (User user) {

		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "UPDATE oncar_user SET nickname=? WHERE id=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_nickname());
			ps.setInt(2, user.getUser_id());
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			}
		} catch (SQLException e) {
			//TODO: handle exception
			user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			return;
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user_old, user_new
	 * @return user [If failure happens in updating, the id of returned objects will be UPDATE_USER_FAILURE]
	 * @see ATTENTION : The old password needs to be checked
	 */
	public void UpdateUserPasswordByID (User user_old, User user_new) {

		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "UPDATE oncar_user SET password=? WHERE id=? AND password=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user_new.getUser_password());
			ps.setInt(2, user_old.getUser_id());
			ps.setString(3, user_old.getUser_password());
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				user_old.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
				user_new.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			}
		} catch (SQLException e) {
			//TODO: handle exception
			user_old.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			user_new.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			return;
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user [If failure happens in updating, the id of returned object will be UPDATE_USER_FAILURE]
	 */
	public void UpdateUserHeightByID (User user) {

		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "UPDATE oncar_user SET height=? WHERE id=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_height());
			ps.setInt(2, user.getUser_id());
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			}
		} catch (SQLException e) {
			//TODO: handle exception
			user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			return;
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user [If failure happens in updating, the id of returned object will be UPDATE_USER_FAILURE]
	 */
	public void UpdateUser_EMERcontact1_ByID (User user) {

		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "UPDATE oncar_user SET EMERcontact_1=? WHERE id=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_EMERcontact_1());
			ps.setInt(2, user.getUser_id());
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			}
		} catch (SQLException e) {
			//TODO: handle exception
			user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					ps.close();
				}
			} catch (SQLException e) {
				//TODO: handle exception
			}
		}
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user [If failure happens in updating, the id of returned object will be UPDATE_USER_FAILURE]
	 */
	public void UpdateUser_EMERcontact2_ByID (User user) {

		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "UPDATE oncar_user SET EMERcontact_2=? WHERE id=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_EMERcontact_2());
			ps.setInt(2, user.getUser_id());
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			}
		} catch (SQLException e) {
			//TODO: handle exception
			user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return user [If failure happens in updating, the id of returned object will be UPDATE_USER_FAILURE]
	 */
	public void UpdateUser_EMERcontact_ByID (User user) {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "UPDATE oncar_user SET EMERcontact_1=?,EMERcontact_2=? WHERE id=?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_EMERcontact_1());
			ps.setString(2, user.getUser_EMERcontact_2());
			ps.setInt(3, user.getUser_id());
			int affectedRow = ps.executeUpdate();

			if (affectedRow == 0) {
				user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
			}
		} catch (SQLException e) {
			//TODO: handle exception
			user.setUser_id(DatabaseUtil.UPDATE_USER_FAILURE);
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
	}

	/**
	 * @author Mr Dk.
	 * @param User user
	 * @return void
	 */
	public void RemoveUser(User user) {
		databaseUtil = DatabaseUtil.getInstance();
		conn = databaseUtil.getConnection();
		sql = "DELETE FROM oncar_user WHERE id = ?;" + 
			  "DROP TABLE " + DatabaseUtil.heartRateTablePre + user.getUser_id() + ";" + 
			  "DROP TABLE " + DatabaseUtil.temperatureTablePre + user.getUser_id() + ";" + 
			  "DROP TABLE " + DatabaseUtil.weightTablePre + user.getUser_id() + ";";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, user.getUser_id());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			user.setUser_id(DatabaseUtil.DELETE_USER_FAILURE);
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
	}
}
