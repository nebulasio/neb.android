package io.nebulas.model;

/**
 * payload 详情类
 * Created by donald99 on 18/5/23.
 */

public class PayloadModel {

    public String type;      // 调用合约
    public String function;  // 合约中的方法名(*)
    public String[] args;    // 函数参数列表 (*)

}
