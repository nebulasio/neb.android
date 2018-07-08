package io.nebulas.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by donald99 on 18/6/1.
 */

public class ContractModel<T> {
    private String args;
    public String function;

    private transient List<T> list = new ArrayList();

    /**
     * 按顺序添加，智能合约需要的参数
     * @param arg
     */
    public void addArg(T arg){
        list.add(arg);

        args = new Gson().toJson(list,new TypeToken<ArrayList<T>>(){}.getType());
    }
}