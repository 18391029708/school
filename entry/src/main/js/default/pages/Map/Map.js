// xxx.js
import prompt from '@system.prompt';
import router from '@system.router';
import geolocation from '@system.geolocation';
import regeneratorRuntime from '@babel/runtime/regenerator';
//var global = {};
//const globalRef = Object.getPrototypeOf(global) || global;
//globalRef.regeneratorRuntime = require('@babel/runtime/regenerator');

export default {
    data:{
    latitude:"",
    longitude:""
    },
    onInit(){

    },

    showDialog() {
        var that = this;
        console.info("输出位置")

        geolocation.getLocation({


            success: function(data) {
                console.info('success get location data. latitude:' + data.latitude);
                that.$set("latitude",data.latitude)
                that.$set("longitude",data.longitude)
                console.info("经度" + that.latitude);
                console.info("纬度" + that.longitude);

            },
            fail: function(data, code) {
                console.log('fail to get location. code:' + code + ', data:' + data);
            },
        });
        this.$element('simpledialog').show()
    },

    cancelDialog(e) {
        prompt.showToast({
            message: 'Dialog cancelled'
        })
    },
    cancelSchedule(e) {
        this.$element('simpledialog').close()
        prompt.showToast({
            message: 'Successfully cancelled'
        })
    },
    setSchedule(e) {
        this.$element('simpledialog').close()
        prompt.showToast({
            message: 'Successfully confirmed'
        })
    },
    routeTo(){
        console.info(2)
        router.push({
            uri:"pages/orderConfirmation/orderConfirmation",
        });
        console.info(1)
    },
    aa:async function (){
    console.info("我执行了")
        var actionData = {};
        actionData.longitude = this.longitude;
        actionData.latitude = this.latitude;

                var action = {};
    action.data = actionData;
                action.bundleName = 'com.example.shundaschool';
                action.abilityName = 'com.example.shundaschool.LocationAbility';
                action.messageCode = 666;
                action.abilityType = 1;
                action.syncOption = 1;



                var result = await FeatureAbility.callAbility(action);
                var ret = JSON.parse(result);
                console.info("00000" + ret);
                console.info("0022" + JSON.stringify(ret.abilityResult));


    },
    onShow(){

    }

}