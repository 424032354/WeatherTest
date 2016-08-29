package com.example.victor.weathertest3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.victor.weathertest3.adapter.CityListAdatper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;


public class CityActivity extends Activity {

	private ListView lv_city;
	private List<String> list;
    private JSONObject resultJson;
	private static final String KEY ="bf15f74d90c54731889f9e06678dc71e";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		initViews();
        request();
	}

	private void initViews() {
		findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		lv_city = (ListView) findViewById(R.id.lv_city);
	}

	private void getCities() {

		try {
			String status = resultJson.getString("status");
			if ("ok".equals(status)) {
				list = new ArrayList<String>();
				JSONArray resultArray = resultJson.getJSONArray("city_info");
				Set<String> citySet = new HashSet<String>();
				for (int i = 0; i < resultArray.length(); i++) {
					String city = resultArray.getJSONObject(i).getString("city");
					citySet.add(city);
				}
				list.addAll(citySet);
				CityListAdatper adatper = new CityListAdatper(CityActivity.this, list);
				lv_city.setAdapter(adatper);
				lv_city.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.putExtra("city", list.get(arg2));
						setResult(1, intent);
						finish();
					}
				});

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

    public void request() {
        final String a = "https://api.heweather.com/x3/citylist?search=allchina&key=bf15f74d90c54731889f9e06678dc71e";
        new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    BufferedReader reader = null;
                    String result = null;
                    StringBuffer sbf = new StringBuffer();
                    URL url = new URL(a);
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    Log.e("err", a);
                    connection.connect();

                    InputStream is = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String strRead = null;
                    while ((strRead = reader.readLine()) != null) {
                        sbf.append(strRead);
                    }
                    reader.close();
                    result = sbf.toString();
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    msg.obj = result;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    Log.e("err", "出错" + e);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                try {
                    resultJson =  new JSONObject((String) msg.obj);
                    getCities();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
