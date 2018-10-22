package cn.zjt.iot.oncar.server.util;

/**
 * @author Mr Dk.
 * @since 2018.4.20
 * @version 2018.6.12
 */

public class ConstDefinition {

	public static final String SERVER_ADDRESS = "20.191.140.238";
	public static final String LOCAL_ADDRESS = "localhost";
	public static final String ADDRESS = LOCAL_ADDRESS;
	public static final int PORT = 2018;
	public static final int SOCKET_TIME_OUT = 5000;
	
	public static final String REQUEST_HEART_RATE_PRE = "heart_rate_";
	public static final String REQUEST_TEMPERATURE_PRE = "temperature_";
	public static final String REQUEST_HEART_RATE_TRAIN = "|1";
	public static final String REQUEST_TEMPERATURE_TRAIN = "|2";
	public static final String REQUEST_HEART_RATE_MODEL = "|4";
	public static final String REQUEST_TEMPERATURE_MODEL = "|5";
	public static final int HEART_RATE_MODEL_MAX_SIZE = 4096;
	public static final int TEMPERATURE_MODEL_MAX_SIZE = 4096;

	public static final String PATH_LOCAL = "D://JavaWorkspace//OnCarSERVER//key.dat";
	public static final String PATH_SERVER = "C://Users//zonghua//Desktop//OnCarSERVER//key.dat";
	public static final String PATH = PATH_LOCAL;
}
