package cn.zjt.iot.oncar.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;

import org.json.JSONObject;

import cn.zjt.iot.oncar.server.dao.HeartRateDao;
import cn.zjt.iot.oncar.server.javabean.HeartRateGroup;
import cn.zjt.iot.oncar.server.util.ConstDefinition;

public class HeartRateService {

    /**
     * @author Mr Dk.
     * @param JSONObject inputPack {id, heartrate}
     * @return JSONObject returnPack {status (boolean) : success | failure}
     */
    public void InsertHeartRateService(JSONObject inputPack, JSONObject returnPack) {

        int id = inputPack.getInt("id");
        HeartRateGroup heartRategGroup = new HeartRateGroup();
        HeartRateDao heartRateDao = new HeartRateDao(id);

        heartRategGroup.setHeartRate_time(Calendar.getInstance().getTimeInMillis());
        heartRategGroup.setHeartRate_record(inputPack.getString("heartrate"));
        boolean success = heartRateDao.InsertHeartRate(heartRategGroup);

        if (success) {
            returnPack.put("status", true);
        } else {
            returnPack.put("status", false);
        }
    }

    /**
     * @author Mr Dk.
     * @param JSONObject inputPack {id}
     * @return JSONObject returnPack {status (boolean) : success | failure}
     */
    public void ClearHeartRateService(JSONObject inputPack, JSONObject returnPack) {

        int id = inputPack.getInt("id");
        HeartRateDao heartRateDao = new HeartRateDao(id);

        boolean success = heartRateDao.ClearAllHeartRate();

        if (success) {
            returnPack.put("status", true);
        } else {
            returnPack.put("status", false);
        }
    }

    /**
     * @author Mr Dk.
     * @param  int id
     * @return JSONObject returnPack {status (boolean)}
     */
    public void TrainHeartRateModelService(int id, JSONObject returnPack) {

    	Socket socket = null;
        OutputStream oStream = null;
        InputStream iStream = null;
        byte []request = (ConstDefinition.REQUEST_HEART_RATE_PRE + id + ConstDefinition.REQUEST_HEART_RATE_TRAIN).getBytes();
        byte []response = new byte[8];

        try {
            socket = new Socket(ConstDefinition.ADDRESS, ConstDefinition.PORT);
            socket.setSoTimeout(ConstDefinition.SOCKET_TIME_OUT);
            oStream = socket.getOutputStream();
            iStream = socket.getInputStream();    

            oStream.write(request);
            iStream.read(response);

            returnPack.put("status", true);

        } catch (IOException e) {
            // TODO Auto-generated catch block
        	//e.printStackTrace();
            returnPack.put("status", false);
        } finally {
            try {
                if (oStream != null) {
                    oStream.close();
                }
                if (iStream != null) {
                    iStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @author Mr Dk.
     * @param int id
     * @return JSONObject returnPack {status (boolean), model (string)}
     */
    public void DownloadHeartRateModelService(int id, JSONObject returnPack) {

        Socket socket = null;
        OutputStream oStream = null;
        InputStream iStream = null;
        byte []request = (ConstDefinition.REQUEST_HEART_RATE_PRE + id + ConstDefinition.REQUEST_HEART_RATE_MODEL).getBytes();
        byte []model = new byte[ConstDefinition.HEART_RATE_MODEL_MAX_SIZE];

        try {
            socket = new Socket(ConstDefinition.ADDRESS, ConstDefinition.PORT);
            socket.setSoTimeout(ConstDefinition.SOCKET_TIME_OUT);
            oStream = socket.getOutputStream();
            iStream = socket.getInputStream();    

            oStream.write(request);
            int bytenum = iStream.read(model);

            returnPack.put("status", true);
            returnPack.put("model", new String(model, 0, bytenum));

        } catch (IOException e) {
            // TODO Auto-generated catch block
        	//e.printStackTrace();
            returnPack.put("status", false);
        } finally {
            try {
                if (oStream != null) {
                    oStream.close();
                }
                if (iStream != null) {
                    iStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
