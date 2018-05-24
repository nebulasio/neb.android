package io.nebulas.model;

import com.google.gson.Gson;

/**
 * 唤醒App类
 * Created by donald99 on 18/5/23.
 */

public class OpenAppMode {

    public String category;    // 唤起类型
    public String des;         // 确认转账

    public PageParamsModel pageParams;

    public static String getOpenAppModel(OpenAppMode openAppMode){

       return new Gson().toJson(openAppMode);

    }
}
