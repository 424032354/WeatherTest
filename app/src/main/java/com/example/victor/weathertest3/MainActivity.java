package com.example.victor.weathertest3;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.victor.weathertest3.bean.SixDayWeatherBean;
import com.example.victor.weathertest3.bean.WeatherBean;
import com.example.victor.weathertest3.service.WeatherService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.List;


public class MainActivity extends Activity {

    private Context mContext;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private ScrollView scrollView;
    private WeatherService mService;

    private RelativeLayout rl_city;

    private TextView tv_city,// 城市
            tv_release,// 发布时间
            tv_now_weather,// 天气
            tv_today_maxTemp,// 最高温度
            tv_today_minTemp,// 最低温度
            tv_now_temp,// 当前温度
            tv_aqi,// 空气质量指数
            tv_quality,// 空气质量
            tv_today,// 预报今天
            tv_tomorrow,// 明天
            tv_thirdDay,// 第三天
            tv_fourthDay,// 第四天
            tv_fifthDay,// 第五天
            tv_sixthDay,// 第六天
            tv_today_temp,// 今天温度
            tv_tomorrow_temp,// 明天温度
            tv_thirdDay_temp,// 第三天温度
            tv_fourthDay_temp,// 第四天温度
            tv_fifthDay_temp,// 第五天温度
            tv_sixthDay_temp,// 第六天温度
            tv_wind_direction,// 风向
            tv_wind_power,// 风力
            tv_sunrise,// 日出
            tv_sunset,// 日落
            tv_humidity,// 湿度
            tv_sendible_temperature,// 体感温度
            tv_exercise_index,// 运动指数
            tv_carWash_index,// 洗车指数
            tv_uv_index,// 紫外线指数
            tv_dressing_index,// 穿衣指数
            tv_travel_index,// 旅行指数
            tv_sunscreen_index,// 防晒指数
            tv_cold_index,// 感冒指数
            tv_comfort_index;//舒适指数

    private ImageView iv_now_weather,// 现在
            iv_today,// 预报今天
            iv_tomorrow,// 明天
            iv_thirdDay,// 第三天
            iv_fourthDay,// 第四天
            iv_fifthDay,// 第五天
            iv_sixthDay;// 第六天


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        init();
        initService();
    }

    private void initService(){
        Intent inter = new Intent(mContext, WeatherService.class);
        startService(inter);
        bindService(inter,conn,Context.BIND_AUTO_CREATE);
    }

    ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((WeatherService.WeatherServiceBinder)service).getService();
            mService.setCallBack(new WeatherService.OnParserCallBack() {
                @Override
                public void OnParserComplete(WeatherBean bean) {
                    pullToRefreshScrollView.onRefreshComplete();
                    if(bean != null){
                        setWeatherViews(bean);
                    }
                }
            });
            mService.getCityWeather();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService.removeCallBack();
        }
    };

    private void init(){
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.my_list);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mService.getCityWeather();
            }
        });
        scrollView = pullToRefreshScrollView.getRefreshableView();

        rl_city = (RelativeLayout) findViewById(R.id.rl_city);
        rl_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext,CityActivity.class),1);
            }
        });


        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_release = (TextView) findViewById(R.id.tv_release);
        tv_now_weather = (TextView) findViewById(R.id.tv_now_weather);
        tv_today_maxTemp = (TextView) findViewById(R.id.tv_today_maxTemp);
        tv_today_minTemp = (TextView) findViewById(R.id.tv_today_minTemp);
        tv_now_temp = (TextView) findViewById(R.id.tv_now_temp);
        tv_aqi = (TextView) findViewById(R.id.tv_aqi);
        tv_quality = (TextView) findViewById(R.id.tv_quality);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_tomorrow = (TextView) findViewById(R.id.tv_tomorrow);
        tv_thirdDay = (TextView) findViewById(R.id.tv_thirdDay);
        tv_fourthDay = (TextView) findViewById(R.id.tv_fourthDay);
        tv_fifthDay = (TextView) findViewById(R.id.tv_fifthDay);
        tv_sixthDay = (TextView) findViewById(R.id.tv_sixthDay);
        tv_today_temp = (TextView) findViewById(R.id.tv_today_temp);
        tv_tomorrow_temp = (TextView) findViewById(R.id.tv_tomorrow_temp);
        tv_thirdDay_temp = (TextView) findViewById(R.id.tv_thirdDay_temp);
        tv_fourthDay_temp = (TextView) findViewById(R.id.tv_fourthDay_temp);
        tv_fifthDay_temp = (TextView) findViewById(R.id.tv_fifthDay_temp);
        tv_sixthDay_temp = (TextView) findViewById(R.id.tv_sixthDay_temp);
        tv_wind_direction = (TextView) findViewById(R.id.tv_windDirection);
        tv_wind_power = (TextView) findViewById(R.id.tv_windPower);
        tv_sunrise = (TextView) findViewById(R.id.tv_sunrise);
        tv_sunset = (TextView) findViewById(R.id.tv_sunset);
        tv_humidity = (TextView) findViewById(R.id.tv_humidity);
        tv_sendible_temperature = (TextView) findViewById(R.id.tv_sendibleTemperature);
        tv_exercise_index = (TextView) findViewById(R.id.tv_exercise_index);
        tv_carWash_index = (TextView) findViewById(R.id.tv_carWash_index);
        tv_dressing_index = (TextView) findViewById(R.id.tv_dressing_index);
        tv_travel_index = (TextView) findViewById(R.id.tv_travel_index);
        tv_sunscreen_index = (TextView) findViewById(R.id.tv_sunscreen_index);
        tv_cold_index = (TextView) findViewById(R.id.tv_cold_index);
        tv_comfort_index = (TextView) findViewById(R.id.tv_comfort_index);
        tv_uv_index = (TextView) findViewById(R.id.tv_uv_index);

        iv_now_weather = (ImageView) findViewById(R.id.iv_now_weather);
        iv_today = (ImageView) findViewById(R.id.iv_today);
        iv_tomorrow = (ImageView) findViewById(R.id.iv_tomorrow);
        iv_thirdDay = (ImageView) findViewById(R.id.iv_thirdDay);
        iv_fourthDay = (ImageView) findViewById(R.id.iv_fourthDay);
        iv_fifthDay = (ImageView) findViewById(R.id.iv_fifthDay);
        iv_sixthDay = (ImageView) findViewById(R.id.iv_sixthDay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == 1){
            String city = data.getStringExtra("city");
            mService.getCityWeather(city);
        }
    }

    private void setWeatherViews(WeatherBean bean) {
        tv_city.setText(bean.getCity());
        tv_release.setText(bean.getRelease());
        tv_now_weather.setText(bean.getWeather_str());
        tv_today_maxTemp.setText(bean.getMax_temp()+"°");
        tv_today_minTemp.setText(bean.getMin_temp()+"°");
        tv_now_temp.setText(bean.getNow_temp() + "°");
        if(bean.getPmBean() != null){
            tv_aqi.setText(bean.getPmBean().getAqi());
            tv_quality.setText(bean.getPmBean().getQuality());
        }else{
            tv_aqi.setText(R.string.default_text);
            tv_quality.setText(R.string.default_text);
        }
        tv_wind_direction.setText(bean.getWind_direction());
        tv_wind_power.setText(bean.getWind_power()+"级");
        tv_sunrise.setText(bean.getSunrise());
        tv_sunset.setText(bean.getSunset());
        tv_humidity.setText(bean.getHumidity()+"%");
        tv_sendible_temperature.setText(bean.getSendible_temperature()+"°");
        tv_exercise_index.setText(bean.getExercise_index());
        tv_carWash_index.setText(bean.getCarWash_index());
        tv_dressing_index.setText(bean.getDressing_index());
        tv_uv_index.setText(bean.getUv_index());
        tv_travel_index.setText(bean.getTravel_index());
        tv_sunscreen_index.setText(bean.getSunscreen_index());
        tv_cold_index.setText(bean.getCold_index());
        tv_comfort_index.setText(bean.getComfort_index());
        iv_now_weather.setImageResource(getResources().getIdentifier("d" + bean.getWeather_id(), "drawable", mContext.getPackageName()));
        Log.e("err",bean.getWeather_id());
        setSixDayWeatherViews(bean.getSixDayList());
    }

    private void setSixDayWeatherViews(List<SixDayWeatherBean> list) {
        setSixDayWeather(tv_today, iv_today, tv_today_temp, list.get(0));
        setSixDayWeather(tv_tomorrow, iv_tomorrow, tv_tomorrow_temp, list.get(1));
        setSixDayWeather(tv_thirdDay, iv_thirdDay, tv_thirdDay_temp, list.get(2));
        setSixDayWeather(tv_fourthDay, iv_fourthDay, tv_fourthDay_temp, list.get(3));
        setSixDayWeather(tv_fifthDay, iv_fifthDay, tv_fifthDay_temp, list.get(4));
        setSixDayWeather(tv_sixthDay, iv_sixthDay, tv_sixthDay_temp, list.get(5));
    }

    private void setSixDayWeather(TextView tv_day, ImageView iv_weather, TextView tv_temp, SixDayWeatherBean bean) {
        tv_day.setText(bean.getDate());
        iv_weather.setImageResource(getResources().getIdentifier("d" + bean.getWeather_id(), "drawable", mContext.getPackageName()));
        tv_temp.setText(bean.getMaxTemp() + "°/"+bean.getMinTemp() + "°");
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
