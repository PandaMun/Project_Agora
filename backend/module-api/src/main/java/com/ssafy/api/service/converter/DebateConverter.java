package com.ssafy.api.service.converter;

//import com.ssafy.api.request.DebateOrderBase;
//import com.ssafy.api.request.DebateTimeSettingBase;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DebateConverter {
//    public String cedaModeOptionToString(ArrayList<DebateOrderBase> debateOrderList, ArrayList<DebateTimeSettingBase> debateTimeSettingList) throws JSONException {
//        Collections.sort(debateOrderList, new Comparator<DebateOrderBase>() {
//            @Override
//            public int compare(DebateOrderBase o1, DebateOrderBase o2) {
//                return Integer.compare(o1.getOrder(), o2.getOrder());
//            }
//        });
//
//        JSONObject result = new JSONObject();
//        JSONArray debateOrderArray = new JSONArray();
//        JSONObject debateTimeSettingJson = new JSONObject();
//
//        for (DebateOrderBase debateOrder : debateOrderList) {
//            JSONObject object = new JSONObject();
//            object.put("userId", debateOrder.getUserId());
//            object.put("order", debateOrder.getOrder());
//            debateOrderArray.put(object);
//        }
//        for (DebateTimeSettingBase debateTimeSetting : debateTimeSettingList) {
//            debateTimeSettingJson.put("argumentTime", debateTimeSetting.getArgumentTime());
//            debateTimeSettingJson.put("crossFireTime", debateTimeSetting.getCrossFireTime());
//            debateTimeSettingJson.put("counterArgument", debateTimeSetting.getCounterargument());
//        }
//
//        result.put("debateOrder", debateOrderArray);
//        result.put("debateTimeSetting", debateTimeSettingList);
//
//        return result.toString();
//    }
}
