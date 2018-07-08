package io.nebulas.nas;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import io.nebulas.Constants;
import io.nebulas.api.SmartContracts;
import io.nebulas.model.ContractModel;
import io.nebulas.model.GoodsModel;
import io.nebulas.utils.Util;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String value = "100";

    private String serialNumber = "";

    private TextView tvAddress,tvShowAccountState;

    private EditText goodsName,goodsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAddress = findViewById(R.id.address);
        tvShowAccountState = findViewById(R.id.showAccountState);
        goodsName = findViewById(R.id.goods_name);
        goodsDescription = findViewById(R.id.goods_desc);
    }

    public void nasPay(View view){

        if (view == null) {
            return;
        }
        serialNumber = Util.getRandomCode(Constants.RANDOM_LENGTH);

        String to = "n1ULQeCi1FEbDn4tktufhzZXCTnv4eJQb4C";//入账钱包地址，钱包地址，钱包地址

        GoodsModel goods = new GoodsModel();
        goods.name = goodsName.getText().toString();
        goods.desc = goodsDescription.getText().toString();

        SmartContracts.pay(this, Constants.MAIN_NET, goods,  to, value , serialNumber);

    }

    public void nasCall(View view){

        serialNumber = Util.getRandomCode(Constants.RANDOM_LENGTH);

        GoodsModel goods = new GoodsModel();
        goods.name = goodsName.getText().toString();
        goods.desc = goodsDescription.getText().toString();


        String to = "n1zVUmH3BBebksT4LD5gMiWgNU9q3AMj3se";//部署上链的，合约地址，合约地址，合约地址
        String functionName = "set";

        String[] args = new String[]{"one","two","three"};

        SmartContracts.call(this, Constants.MAIN_NET ,  goods, functionName, to, value, args, serialNumber);
    }

    public void nasQueryTransferStatus(View view){

        if (TextUtils.isEmpty(serialNumber)) {
            Toast.makeText(this,"serialNumber is empty !",Toast.LENGTH_LONG).show();
            return;
        }

        SmartContracts.queryTransferStatus(Constants.MAIN_NET, serialNumber,new SmartContracts.StatusCallback(){
            @Override
            public void onSuccess(String response) {

                Log.i(TAG,"response:" + response);

                Looper.prepare();
                Toast.makeText(MainActivity.this,"response:"+response,Toast.LENGTH_LONG).show();
                Looper.loop();

            }

            @Override
            public void onFail(String error) {

                Log.i(TAG,"error:" + error);

                Looper.prepare();
                Toast.makeText(MainActivity.this,"error:" + error,Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
    }

    public void nasQueryAccountState(View view){
        String address = tvAddress.getText().toString();
        SmartContracts.queryAccountState(address, new SmartContracts.StatusCallback() {
            @Override
            public void onSuccess(final String response) {

                Log.i(TAG,"response:" + response);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"response:"+response,Toast.LENGTH_LONG).show();
                        if (tvShowAccountState != null) {
                            tvShowAccountState.setText(response);
                        }
                    }
                });
            }

            @Override
            public void onFail(String error) {

                Log.i(TAG,"error:" + error);
            }
        });
    }


    public void nasCallContract(View view) {
        ContractModel contractModel = new ContractModel();

        //contractModel.addArg("美扑伪麻片");
        //contractModel.function = "getDrugItem";
        //String from = "n1haLy4Ka989bB7NujA9LxRMhU4FP6PFoK4";

        contractModel.addArg(1);
        contractModel.addArg(0);
        contractModel.addArg(1);
        contractModel.function = "history";
        String from = "n22Djb3G8dzLyeRMWAxov7j3ExLdhnLtwgw";

        SmartContracts.simulateCall(contractModel,from,from,1, new SmartContracts.StatusCallback() {
            @Override
            public void onSuccess(final String response) {

                Log.i(TAG,"response:" + response);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFail(String error) {

                Log.i(TAG,"error:" + error);
            }
        });
    }
}

