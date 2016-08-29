package com.example.victor.weathertest3.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.example.victor.weathertest3.bean.PMBean;
import com.example.victor.weathertest3.bean.SixDayWeatherBean;
import com.example.victor.weathertest3.bean.WeatherBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherService extends Service {

    private String city;
    private WeatherServiceBinder biner = new WeatherServiceBinder();
    private WeatherBean bean = new WeatherBean();
    private boolean isRunning = false;
    private int count = 0;
    private OnParserCallBack callBack;
    private static final int REPEAT_MSG =0X01;
    private final int CALLBACK_OK = 0x02;
    private final int CALLBACK_ERROR = 0x04;

    public WeatherService() {
    }

    public interface OnParserCallBack{
        public void OnParserComplete(WeatherBean bean);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return biner;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        city = "北京";
        mHandler.sendEmptyMessage(REPEAT_MSG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //半小时刷新
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REPEAT_MSG){
                getCityWeather();
                sendEmptyMessageDelayed(REPEAT_MSG,30*60*1000);
            }
        }
    };

    public void getCityWeather(String city){
        this.city = city;
        getCityWeather();
    }

    public void getCityWeather(){
        if(isRunning){
            return;
        }
        isRunning = true;
        count = 0;

        Parameters parameters =new  Parameters();
        parameters.put("city", city);
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, parameters, new ApiCallBack() {
            public void onSuccess(int var1, String var2) {
                count++;
                bean = parserWeather(var1, var2);
            }

            public void onError(int var1, String var2, Exception var3) {
                Log.e("parserWeather", "onError, status: " + var1);
                Log.e("parserWeather", "errMsg: " + (var3 == null ? "" : var3.getMessage()));
            }

            public void onComplete() {
                Parameters parameters = new Parameters();
                parameters.put("cityname", bean.getCity());
                Log.e("cityid", "" + bean.getCity_id());
                parameters.put("cityid", bean.getCity_id().lastIndexOf(9));
                ApiStoreSDK.execute("http://apis.baidu.com/apistore/weatherservice/recentweathers", ApiStoreSDK.GET, parameters, new ApiCallBack() {
                    public void onSuccess(int var1, String var2) {
                        count++;
                        WeatherBean bean2 = parserWeather(var1, var2);
                        bean.setSunscreen_index(bean2.getSunscreen_index());
                    }

                    public void onError(int var1, String var2, Exception var3) {
                        Log.e("parserWeather--防晒指数", "onError, status: " + var1);
                        Log.e("parserWeather--防晒指数", "errMsg: " + (var3 == null ? "" : var3.getMessage()));
                    }

                    public void onComplete() {
                        Log.v("parserWeather--防晒指数", "成功");
                        Log.e("getCityWeather", "" + count);
                        if (count == 2) {
                            Log.e("getCityWeather", "" + callBack);
                            if (callBack != null) {
                                callBack.OnParserComplete(bean);
                            }
                            isRunning = false;
                        }
                    }
                });
                Log.v("parserWeather", "完成");
            }
        });

    }

    public void setCallBack(OnParserCallBack callBack) {
        this.callBack = callBack;
    }

    public void removeCallBack(){
        callBack = null;
    }

    //解析天气接口
    private WeatherBean parserWeather(int v,String s){

        WeatherBean bean = null;
        SimpleDateFormat sdfReleaseDate =new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdfday =new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("H:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("M/d");

        if(v == 200){
            try {
                JSONObject resultJson = new JSONObject(s);
                bean = new WeatherBean();

                if (!resultJson.has("retData")){
                    JSONObject weatherJson = resultJson.getJSONArray("HeWeather data service 3.0").getJSONObject(0);

                    //基础信息
                    JSONObject basic = weatherJson.getJSONObject("basic");
                    bean.setCity(basic.getString("city"));
                    bean.setCity_id(basic.getString("id"));
                    Date datef = sdfReleaseDate.parse(basic.getJSONObject("update").getString("loc"));
                    bean.setRelease(format.format(datef) + "发布");

                    //现在信息
                    JSONObject now = weatherJson.getJSONObject("now");
                    bean.setWeather_str(now.getJSONObject("cond").getString("txt"));
                    bean.setWeather_id(now.getJSONObject("cond").getString("code"));
                    bean.setSendible_temperature(now.getString("fl"));
                    bean.setHumidity(now.getString("hum"));
                    bean.setNow_temp(now.getString("tmp"));
                    bean.setWind_direction(now.getJSONObject("wind").getString("dir"));
                    bean.setWind_power(now.getJSONObject("wind").getString("sc"));

                    //每日预报
                    JSONArray dailyForecast = weatherJson.getJSONArray("daily_forecast");
                    JSONObject dayData = null;
                    Date date = new Date(System.currentTimeMillis());
                    List<SixDayWeatherBean> sixDaylist = new ArrayList<SixDayWeatherBean>();
                    for (int i = 0; i < dailyForecast.length(); i++) {
                        SixDayWeatherBean sixBean = new SixDayWeatherBean();
                        dayData = dailyForecast.getJSONObject(i);
                        datef = sdfday.parse(dayData.getString("date"));
                        if (!datef.after(date)){
                            bean.setMax_temp(dayData.getJSONObject("tmp").getString("max"));
                            bean.setMin_temp(dayData.getJSONObject("tmp").getString("min"));
                            bean.setSunrise(dayData.getJSONObject("astro").getString("sr"));
                            bean.setSunset(dayData.getJSONObject("astro").getString("ss"));
                        }
                        sixBean.setDate(format2.format(datef));
                        sixBean.setWeather_id(dayData.getJSONObject("cond").getString("code_d"));
                        sixBean.setMaxTemp(dayData.getJSONObject("tmp").getString("max"));
                        sixBean.setMinTemp(dayData.getJSONObject("tmp").getString("min"));
                        sixDaylist.add(sixBean);
                        if (sixDaylist.size() == 6){//需要6天的预报，防止过多
                            break;
                        }
                    }

                    //指数
                    JSONObject index = weatherJson.getJSONObject("suggestion");
                    bean.setComfort_index(index.getJSONObject("comf").getString("brf"));
                    bean.setCarWash_index(index.getJSONObject("cw").getString("brf"));
                    bean.setDressing_index(index.getJSONObject("drsg").getString("brf"));
                    bean.setCold_index(index.getJSONObject("flu").getString("brf"));
                    bean.setExercise_index(index.getJSONObject("sport").getString("brf"));
                    bean.setTravel_index(index.getJSONObject("trav").getString("brf"));
                    bean.setUv_index(index.getJSONObject("uv").getString("brf"));
                    bean.setSixDayList(sixDaylist);

                    //api
                    if(weatherJson.has("aqi")) {
                        JSONObject api = weatherJson.getJSONObject("aqi").getJSONObject("city");
                        Log.e("api","有api");
                        PMBean pmBean = new PMBean();
                        pmBean.setAqi(api.getString("aqi"));
                        pmBean.setQuality(api.getString("qlty"));
                        bean.setPmBean(pmBean);
                    }
                }else {
                    bean.setSunscreen_index(resultJson.getJSONObject("retData").getJSONObject("today").getJSONArray("index").getJSONObject(1).getString("index"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            Log.e("err", "出错，通信码为" + v);
        }
        return bean;
    }

    public class WeatherServiceBinder extends Binder{

        public WeatherService getService(){
            return WeatherService.this;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
