package com.example.shundaschool;
import ohos.ace.ability.AceInternalAbility;
import ohos.app.AbilityContext;
import ohos.location.RequestParam;
import ohos.rpc.IRemoteObject;
import ohos.rpc.MessageOption;
import ohos.rpc.MessageParcel;
import ohos.rpc.RemoteException;
import ohos.utils.zson.ZSONObject;
import java.util.HashMap;
import java.util.Map;
public class LocationAbility extends AceInternalAbility {
    private static final String TAG = LocationAbility.class.getSimpleName();
    private static final String BUNDLE_NAME = "com.example.shundaschool";
    private static final String ABILITY_NAME = "com.example.shundaschool.LocationAbility";
    private static final int ERROR = -1;
    private static final int SUCCESS = 0;
    private static final int location = 666;
    private static LocationAbility instance;
    private AbilityContext abilityContext;
    // 如果多个Ability实例都需要注册当前InternalAbility实例，需要更改构造函数，设定自己的bundleName和abilityName
    public LocationAbility() {
        super(BUNDLE_NAME, ABILITY_NAME);
    }
    public boolean onRemoteRequest(int code, MessageParcel data, MessageParcel reply, MessageOption option) {
        switch (code) {
            case location: {
                System.out.println("我是code" + code);
                String zsonStr = data.readString();
                System.out.println("我是zsonStr:" + zsonStr);
                System.out.println("我是转换后字符:" + ZSONObject.stringToClass(zsonStr, RequestParam.class));

                RequestParam param = ZSONObject.stringToClass(zsonStr, RequestParam.class);
                System.out.println("我是转换后字符:" + ZSONObject.stringToClass(zsonStr, RequestParam.class));
                System.out.println("我是param");
                // 返回结果当前仅支持String，对于复杂结构可以序列化为ZSON字符串上报
                Map<String, Object> zsonResult = new HashMap<String, Object>();
                zsonResult.put("code", SUCCESS);
                System.out.println("我是param:" +  param);
                zsonResult.put("abilityResult",  "我是param");

                if (option.getFlags() == MessageOption.TF_SYNC) {
                    reply.writeString(ZSONObject.toZSONString(zsonResult));
                } else {
                    // ASYNC
                    MessageParcel reponseData = MessageParcel.obtain();
                    reponseData.writeString(ZSONObject.toZSONString(zsonResult));
                    IRemoteObject remoteReply = reply.readRemoteObject();
                    try {
                        remoteReply.sendRequest(0, reponseData, MessageParcel.obtain(), new MessageOption());
                        reponseData.reclaim();
                    } catch (RemoteException exception) {
                        return false;
                    }
                }
                break;
            }
            default:{
                reply.writeString("service not defined");
                return false;
            }
        }
        return true;
    }
    public static void register(AbilityContext abilityContext) {
        instance = new LocationAbility();
        instance.onRegister(abilityContext);
    }

    private void onRegister(AbilityContext abilityContext) {
        this.abilityContext = abilityContext;
        this.setInternalAbilityHandler((code, data, reply, option) -> {
            return this.onRemoteRequest(code, data, reply, option);
        });
    }

    // Internal ability deregistration.
    public static void deregister() {
        instance.onDeregister();
    }

    private void onDeregister() {
        abilityContext = null;
        this.setInternalAbilityHandler(null);
    }
}