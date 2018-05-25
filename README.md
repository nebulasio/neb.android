# 官方安卓星云钱包（nasnano）接入文档
## (测试中，如有问题或建议请及时反馈)

nasnano下载地址：https://nano.nebulas.io/index_cn.html

#### 安卓版本接入简述
在你的项目工程中引入该项目，下面的方式仅为示例（其他包依赖管理方式均可）：

下载https://github.com/nebulasio/androidSDK/blob/master/libnebulas-release.aar  (或自行build该工程下的libnebulas)

（1）将aar包复制到你项目的libs中

（2）在build.gradle中引入相关依赖描述

```
repositories {
    flatDir {
        dir 'libs'
    }
}
```

```
dependencies {
...
+    compile(name: 'libnebulas-release', ext: 'aar')
+    implementation 'com.squareup.okhttp3:okhttp:3.10.0'
+    implementation 'com.google.code.gson:gson:2.8.4'
...
}
```

## 调用接口
#### 调用接口 pay

>    public static void pay(Context context, GoodsModel goods, String to, String value) 



```
        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                GoodsModel gm = new GoodsModel();
                gm.name = "mytestGood";       // 商品名称
                gm.desc = "mytestGoodDesp";   //商品描述
                String toAddr = "n1lxxx…………………………";  // 目标地址
                String valueInWei = "your value";    // your value
                
                SmartContracts.pay(MainActivity.this , gm, toAddr, valueInWei);

            }
        });
```

#### 调用接口 call() 传入参数参考上面:
    
   
>    public static void call(Context context, GoodsModel goods, String to, String value, String[] args) 


#### 调用接口 queryTransferStatus() :    

>    queryTransferStatus(int mainNet, String serialNumber, final SmartContracts.TransferStatusCallback callback) 




## 感谢社区小伙伴 大道 提供技术支持

#### 本工程处于测试中，如有问题或建议请及时反馈
#### 更多希望提供技术支持的小伙伴可以在 https://nebulas.io/developers.html 或官方微信群申请，期待来自你们的元气 :)



