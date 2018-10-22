package cn.zjt.iot.oncar.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;

import org.json.JSONObject;

import cn.zjt.iot.oncar.server.dao.TemperatureDao;
import cn.zjt.iot.oncar.server.javabean.Temperature;
import cn.zjt.iot.oncar.server.util.ConstDefinition;

public class TemperatureService {

    /**
     * @author Mr Dk.
     * @param JSONObject inputPack {id, temperature}
     * @return JSONObject returnPack {status (boolean) : success | failure}
     */
    public void InsertTemperatureService(JSONObject inputPack, JSONObject returnPack) {
        int id = inputPack.getInt("id");
        Temperature temperature = new Temperature();
        TemperatureDao temperatureDao = new TemperatureDao(id);

        temperature.setTemperature_time(Calendar.getInstance().getTimeInMillis());;
        temperature.setTemperature_record(inputPack.getString("temperature"));
        boolean success = temperatureDao.InsertTemperature(temperature);

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
    public void ClearTemperatureService(JSONObject inputPack, JSONObject returnPack) {
        int id = inputPack.getInt("id");
        TemperatureDao temperatureDao = new TemperatureDao(id);

        boolean success = temperatureDao.ClearAllTemperature();

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
    public void TrainTemperatureModelService(int id, JSONObject returnPack) {
        Socket socket = null;
        OutputStream oStream = null;
        InputStream iStream = null;
        byte []request = (ConstDefinition.REQUEST_TEMPERATURE_PRE + id + ConstDefinition.REQUEST_TEMPERATURE_TRAIN).getBytes();
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
    public void DownloadTemperatureModelService(int id, JSONObject returnPack) {
        Socket socket = null;
        OutputStream oStream = null;
        InputStream iStream = null;
        byte []request = (ConstDefinition.REQUEST_TEMPERATURE_PRE + id + ConstDefinition.REQUEST_TEMPERATURE_MODEL).getBytes();
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
