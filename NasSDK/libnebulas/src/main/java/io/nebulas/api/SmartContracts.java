package io.nebulas.api;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;

import io.nebulas.Constants;
import io.nebulas.action.ContractAction;
import io.nebulas.model.AccountState;
import io.nebulas.model.CallContractModel;
import io.nebulas.model.ContractModel;
import io.nebulas.model.GoodsModel;
import io.nebulas.model.OpenAppMode;
import io.nebulas.model.PageParamsModel;
import io.nebulas.model.PayModel;
import io.nebulas.model.PayloadModel;
import io.nebulas.okhttp.OkHttpManager;
import io.nebulas.schema.OpenAppSchema;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 智能合约调用
 * <p>
 * Created by donald99 on 18/5/23.
 */
public class SmartContracts {

    /**
     * pay接口：       星云地址之间的转账
     *
     * @param mainNet      0 测试网    1 主网
     * @param goods        商品详情
     * @param to           转账目标地址
     * @param value        转账value，单位为wei (1NAS =10^18 wei)
     * @param serialNumber 随机码
     */
    public static void pay(Context context, int mainNet, GoodsModel goods, String to, String value, String serialNumber) {

        OpenAppMode openAppMode = new OpenAppMode();
        openAppMode.category = Constants.CATEGORY;
        openAppMode.des = Constants.DESCRIPTION;

        PageParamsModel pageParamsModel = new PageParamsModel();
        pageParamsModel.serialNumber = serialNumber;
        if (mainNet == 0) {
            pageParamsModel.callback = Constants.TEST_NET_CALL_BACK;
        } else {
            pageParamsModel.callback = Constants.MAIN_NET_CALL_BACK;
        }
        pageParamsModel.goods = goods;

        PayloadModel payloadModel = new PayloadModel();
        payloadModel.type = Constants.PAY_PAYLOAD_TYPE;

        PayModel payModel = new PayModel();
        payModel.currency = Constants.PAY_CURRENCY;
        payModel.payload = payloadModel;
        payModel.value = value;
        payModel.to = to;

        pageParamsModel.pay = payModel;

        openAppMode.pageParams = pageParamsModel;

        String params = OpenAppMode.getOpenAppModel(openAppMode);

        String url = OpenAppSchema.getSchemaUrl(params);

        ContractAction.start(context, url);
    }


    /**
     * call函数：      调用智能合约
     *
     * @param mainNet      0 测试网    1 主网
     * @param goods        商品详情（*）
     * @param functionName 调用合约的函数名
     * @param to           转账目标地址
     * @param value        转账value，单位为wei (1NAS =10^18 wei)
     * @param args         函数参数列表
     * @param serialNumber 随机码
     */
    public static void call(Context context, int mainNet, GoodsModel goods, String functionName, String to, String value, String[] args, String serialNumber) {

        OpenAppMode openAppMode = new OpenAppMode();
        openAppMode.category = Constants.CATEGORY;
        openAppMode.des = Constants.DESCRIPTION;

        PageParamsModel pageParamsModel = new PageParamsModel();
        pageParamsModel.serialNumber = serialNumber;
        if (mainNet == 0) {
            pageParamsModel.callback = Constants.TEST_NET_CALL_BACK;
        } else {
            pageParamsModel.callback = Constants.MAIN_NET_CALL_BACK;
        }
        pageParamsModel.goods = goods;

        PayloadModel payloadModel = new PayloadModel();
        payloadModel.type = Constants.CALL_PAYLOAD_TYPE;
        payloadModel.function = functionName;
        payloadModel.args = args;

        PayModel payModel = new PayModel();
        payModel.currency = Constants.PAY_CURRENCY;
        payModel.payload = payloadModel;
        payModel.value = value;
        payModel.to = to;

        pageParamsModel.pay = payModel;

        openAppMode.pageParams = pageParamsModel;

        String params = OpenAppMode.getOpenAppModel(openAppMode);

        String url = OpenAppSchema.getSchemaUrl(params);

        ContractAction.start(context, url);
    }

    /**
     * 查询地址详细信息
     * @param address
     * @param callback
     */
    public static void queryAccountState(String address, final StatusCallback callback) {
        AccountState accountState = new AccountState();
        accountState.address = address;
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , new Gson().toJson(accountState));
        Request request = new Request.Builder().url(Constants.MAIN_NET_RPC_ACCOUNT_STATE_URL).post(requestBody).build();
        OkHttpManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    if (callback != null) {
                        callback.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null && response != null) {
                    callback.onSuccess(response.body().string());
                }

            }
        });
    }

    /**
     * 查询合约上存储的的数据内容
     * @param contractModel
     * @param from
     * @param to
     * @param nonce
     * @param callback
     */
    public static void call(ContractModel contractModel, String from, String to, int nonce, final StatusCallback callback){
        if (contractModel == null || TextUtils.isEmpty(from)) {
            return;
        }
        CallContractModel callContractModel = new CallContractModel();
        callContractModel.contract = contractModel;
        callContractModel.from = from;
        callContractModel.to = to;
        callContractModel.nonce = nonce;
        callContractModel.gasLimit = "1000000";
        callContractModel.gasPrice = "200000";
        callContractModel.value = "0";
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(callContractModel));
        Request request = new Request.Builder().url(Constants.MAIN_NET_RPC_CALL_URL).post(requestBody).build();
        OkHttpManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    if (callback != null) {
                        callback.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null && response != null) {
                    callback.onSuccess(response.body().string());
                }

            }
        });
    }

    /**
     * 查询交易状态
     *
     * @param mainNet      0 测试网    1 主网
     * @param serialNumber
     */
    public static void queryTransferStatus(int mainNet, String serialNumber, final StatusCallback callback) {

        String ENDPOINT = "";

        if (mainNet == 0) {
            ENDPOINT = Constants.MAIN_NET_PAY_URL + "pay/query?payId=" + serialNumber;
        } else {
            ENDPOINT = Constants.MAIN_NET_PAY_URL + "mainnet/pay/query?payId=" + serialNumber;
        }

        Request request = new Request.Builder().get().url(ENDPOINT).build();
        OkHttpManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callback != null) {
                    callback.onFail(e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (callback != null && response != null) {
                    callback.onSuccess(response.body().string());
                }
            }
        });
    }

    public interface StatusCallback {
        void onSuccess(String response);

        void onFail(String error);
    }

}
