package cn.zjt.iot.oncar.server.service;

import java.util.Calendar;

import org.json.JSONObject;

import cn.zjt.iot.oncar.server.dao.WeightDao;
import cn.zjt.iot.oncar.server.javabean.Weight;

public class WeightService {

    /**
     * @author Mr Dk.
     * @param JSONObject inputPack {id, weight}
     * @return JSONObject returnPack {status (boolean) : success | failure}
     */
    public void InsertWeightService(JSONObject inputPack, JSONObject returnPack) {

        int id = inputPack.getInt("id");
        Weight weight = new Weight();
        WeightDao weightDao = new WeightDao(id);

        weight.setWeight_time(Calendar.getInstance().getTimeInMillis());
        weight.setWeight_value(inputPack.getFloat("weight"));
        boolean success = weightDao.InsertWeight(weight);

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
    public void ClearWeightService(JSONObject inputPack, JSONObject returnPack) {
        int id = inputPack.getInt("id");
        WeightDao weightDao = new WeightDao(id);

        boolean success = weightDao.ClearAllWeight();

        if (success) {
            returnPack.put("status", true);
        } else {
            returnPack.put("status", false);
        }
    }
}
