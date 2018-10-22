package cn.zjt.iot.oncar.server.javabean;

/**
 * @author Mr Dk.
 * @since 2018.4.20
 * @version 2018.5.24
 * @see cn.zjt.iot.oncar.server.dao
 */

public class HeartRateGroup {
	
	private long HeartRate_time;
	private String HeartRate_record;
	
	public long getHeartRate_time() {
		return HeartRate_time;
	}
	public void setHeartRate_time(long heartRate_time) {
		HeartRate_time = heartRate_time;
	}
	public String getHeartRate_record() {
		return HeartRate_record;
	}
	public void setHeartRate_record(String heartRate_record) {
		HeartRate_record = heartRate_record;
	}
}
