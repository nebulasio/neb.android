# 官方安卓星云钱包接入文档

安卓跳转简述
```
context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)))；
```

其中url具体定义如下
```
url = “openapp.NASnano://virtual?params=” + paramsJSON.toString();
```

url中的paramsJSON为传输中所带的参数，在不同的接口中参数不同。
在第一版本中，接口包括如下三个：


| Name        | Intro           | 
| ---------- |:-------------:|
| pay     | 转账 |
| call    | 调用合约函数      |
| query | 查询调用结果     |



### (1) pay接口：星云地址之间的转账（标记*的在传入时可更改值）

```
{
   "category" : "jump", // 唤起类型
   "des" : "confirmTransfer", // 确认转账
   "pageParams" : {
      "serialNumber" : "9KqpaKLusAWIiZTff5Fd2HekVpKfEy78",  // uuid 此次交易的唯一id（*）
      "goods" : {       // 商品名称（*）
         "name" : "test",         
         "desc" : "test goods"
      },
      "pay" : {
         "value" : "1000000000000000000",    // 转账value，单位为wei (1NAS =10^18 wei) （*）
         "to" : "n1JmhE82GNjdZPNZr6dgUuSfzy2WRwmD9zy",   // 转账目标地址 （*）
         "payload" : {
            "type" : "binary" // 转账类型：binary
         },
         "currency" : "NAS" // 转账种类：NAS
      },
      "callback" : "https://pay.nebulas.io/api/pay"    // 统计地址 (*)
   },
}
```

说明：
其中(*)项为可配置项。
统计地址（callback）:
分为测试网（https://pay.nebulas.io/api/pay）
和主网（https://pay.nebulas.io/api/mainnet/pay）


### (2) call函数：调用智能合约（标记*的为输入参数）
```
{
   "category" : "jump",
   "des" : "confirmTransfer",
   "pageParams" : {
      "pay" : {
         "to" : "n1JmhE82GNjdZPNZr6dgUuSfzy2WRwmD9zy", //目标地址 (*)
         "payload" : {
            "type" : "call",    // 调用合约
            "function" : "set",	// 合约中的方法名(*)
            "args" : "[\"abc\"]" // 函数参数列表 (*)
         },
         "value" : "0",	// 转账金额**
         "currency" : "NAS" // 转账种类NAS
      },
      "serialNumber" : "ogbxuX5eYPzlO99xWh01l7ZBcYi6suJW",	// uuid (*)
      "goods" : {		// 商品名 (*)
         "desc" : "test goods",
         "name" : "test"
      },
      "callback" : "https://pay.nebulas.io/api/pay",	//统计地址 (*)
   },
}
```

（3）查询交易状态

```
测试网：“https://pay.nebulas.io/api/pay/query?payId=” + serialNumber

主网：“https://pay.nebulas.io/api/mainnet/pay/query?payId=” +  serialNumber
```