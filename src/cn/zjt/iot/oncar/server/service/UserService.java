package cn.zjt.iot.oncar.server.service;

import org.json.JSONObject;

import cn.zjt.iot.oncar.server.dao.UserDao;
import cn.zjt.iot.oncar.server.javabean.User;
import cn.zjt.iot.oncar.server.util.DatabaseUtil;

public class UserService {

	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {username, nickname, password, EMERcontact_1, EMERcontact_2, height}
	 * @return JSONObject returnPack status (boolean) {success : true; failure : false}
	 */
	public void CreateUserService (JSONObject inputPack, JSONObject returnPack) {
		
		User user = new User();
		UserDao userDao = new UserDao();

		user.setUser_name(inputPack.getString("username"));
		user.setUser_nickname(inputPack.getString("nickname"));
		user.setUser_password(inputPack.getString("password"));

		if (inputPack.has("EMERcontact_1")) {
			user.setUser_EMERcontact_1(inputPack.getString("EMERcontact_1"));
		}
		if (inputPack.has("EMERcontact_2")) {
			user.setUser_EMERcontact_2(inputPack.getString("EMERcontact_2"));
		}
		if (inputPack.has("height")) {
			user.setUser_height(inputPack.getInt("height"));
		}
		
		userDao.CreateUser(user);

		if (user.getUser_id() == DatabaseUtil.INSERT_NOT_UNIQUE) {
			// Insert failure
			returnPack.put("status", false);
		} else {
			// Insert Success
			returnPack.put("status", true);
		}
	}

	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {username, password}
	 * @return JSONObject returnPack
	 * 		@return {status : USER_NOT_EXIST}
	 * 		@return {status : PASSWORD_INCORRECT}
	 * 		@return {status : LOGIN_SUCCESS, id : id, username : username, nickname : nickname, 
	 * 					EMERcontact_1 : EMERcontact_1, EMERcontact_2 : EMERcontact_2, height : height}
	 */
	public void LogInService (JSONObject inputPack, JSONObject returnPack) {

		User user = new User();
		UserDao userDao = new UserDao();
		String password = inputPack.getString("password");

		user.setUser_name(inputPack.getString("username"));
		user.setUser_password(inputPack.getString("password"));

		userDao.QueryUserByUsername(user);

		if (user.getUser_id() == DatabaseUtil.USER_NOT_EXIST) {
			// User doesn't exist
			returnPack.put("status", DatabaseUtil.USER_NOT_EXIST);
		} else if (!user.getUser_password().equals(password)) {
			// Password incorrect
			returnPack.put("status", DatabaseUtil.PASSWORD_INCORRECT);
		} else {
			// Log in success
			returnPack.put("status", DatabaseUtil.LOGIN_SUCCESS);
			returnPack.put("id", user.getUser_id());
			//returnPack.put("username", user.getUser_name());
			returnPack.put("nickname", user.getUser_nickname());
			returnPack.put("EMERcontact_1", user.getUser_EMERcontact_1());
			returnPack.put("EMERcontact_2", user.getUser_EMERcontact_2());
			returnPack.put("height", user.getUser_height());
			returnPack.put("hr_version", user.getUser_hr_version());
			returnPack.put("tp_version", user.getUser_tp_version());
		}
	}
	
	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {id, height}
	 * @return JSONObject returnPack status (boolean) {success : true; failure : false}
	 */
	public void UpdateUserHeightService (JSONObject inputPack, JSONObject returnPack) {

		User user = new User();
		UserDao userDao = new UserDao();

		user.setUser_id(inputPack.getInt("id"));
		user.setUser_height(inputPack.getInt("height"));

		userDao.UpdateUserHeightByID(user);

		if (user.getUser_id() == DatabaseUtil.UPDATE_USER_FAILURE) {
			returnPack.put("status", false);
		} else {
			returnPack.put("status", true);
		}
	}

	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {id, nickname}
	 * @return JSONObject returnPack status (boolean) {success : true; failure : false}
	 */
	public void UpdateUserNickNameService (JSONObject inputPack, JSONObject returnPack) {

		User user = new User();
		UserDao userDao = new UserDao();

		user.setUser_id(inputPack.getInt("id"));
		user.setUser_nickname(inputPack.getString("nickname"));

		userDao.UpdateUserNickNameByID(user);

		if (user.getUser_id() == DatabaseUtil.UPDATE_USER_FAILURE) {
			returnPack.put("status", false);
		} else {
			returnPack.put("status", true);
		}
	}

	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {id, nickname}
	 * @return JSONObject returnPack status (boolean) {success : true; failure : false}
	 */
	public void UpdateUserPasswordService (JSONObject inputPack, JSONObject returnPack) {

		User oldUser = new User();
		User newUser = new User();
		UserDao userDao = new UserDao();

		oldUser.setUser_id(inputPack.getInt("id"));
		oldUser.setUser_password(inputPack.getString("oldPassword"));
		newUser.setUser_password(inputPack.getString("newPassword"));
		
		userDao.UpdateUserPasswordByID(oldUser, newUser);

		if (oldUser.getUser_id() == DatabaseUtil.UPDATE_USER_FAILURE) {
			returnPack.put("status", false);
		} else {
			returnPack.put("status", true);
		}
	}

	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {id, EMERcontact_1}
	 * @return JSONObject returnPack status (boolean) {success : true; failure : false}
	 */
	public void UpdateUser_EMERcontact_1_Service (JSONObject inputPack, JSONObject returnPack) {

		User user = new User();
		UserDao userDao = new UserDao();

		user.setUser_id(inputPack.getInt("id"));
		user.setUser_EMERcontact_1(inputPack.getString("EMERcontact_1"));

		userDao.UpdateUser_EMERcontact1_ByID(user);

		if (user.getUser_id() == DatabaseUtil.UPDATE_USER_FAILURE) {
			returnPack.put("status", false);
		} else {
			returnPack.put("status", true);
		}
	}

	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {id, EMERcontact_2}
	 * @return JSONObject returnPack status (boolean) {success : true; failure : false}
	 */
	public void UpdateUser_EMERcontact_2_Service (JSONObject inputPack, JSONObject returnPack) {
		
		User user = new User();
		UserDao userDao = new UserDao();

		user.setUser_id(inputPack.getInt("id"));
		user.setUser_EMERcontact_2(inputPack.getString("EMERcontact_2"));

		userDao.UpdateUser_EMERcontact2_ByID(user);

		if (user.getUser_id() == DatabaseUtil.UPDATE_USER_FAILURE) {
			returnPack.put("status", false);
		} else {
			returnPack.put("status", true);
		}
	}

	/**
	 * @author Mr Dk.
	 * @param JSONObject inputPack {id, EMERcontact_2}
	 * @return JSONObject returnPack status (boolean) {success : true; failure : false}
	 */
	public void UpdateUser_EMERcontact_Service (JSONObject inputPack, JSONObject returnPack) {
		
		User user = new User();
		UserDao userDao = new UserDao();

		user.setUser_id(inputPack.getInt("id"));
		user.setUser_EMERcontact_1(inputPack.getString("EMERcontact_1"));
		user.setUser_EMERcontact_2(inputPack.getString("EMERcontact_2"));

		userDao.UpdateUser_EMERcontact_ByID(user);

		if (user.getUser_id() == DatabaseUtil.UPDATE_USER_FAILURE) {
			returnPack.put("status", false);
		} else {
			returnPack.put("status", true);
		}
	}
}
