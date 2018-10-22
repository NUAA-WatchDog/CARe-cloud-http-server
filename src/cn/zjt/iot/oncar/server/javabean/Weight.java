package cn.zjt.iot.oncar.server.javabean;

/**
 * @author Mr Dk.
 * @since 2018.4.20
 * @version 2018.5.24
 * @see cn.zjt.iot.oncar.server.dao
 */

public class Weight {

    private long Weight_time;
    private float Weight_value;
    
	public long getWeight_time() {
		return Weight_time;
	}
	public void setWeight_time(long weight_time) {
		Weight_time = weight_time;
	}
	public float getWeight_value() {
		return Weight_value;
	}
	public void setWeight_value(float weight_value) {
		Weight_value = weight_value;
	}
}
