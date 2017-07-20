package com.huihui.crash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Ucache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;

    private List<String> datas;

    private String TAG = this.getClass().getSimpleName();

    Myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        datas = new ArrayList<>();


        ((Button) findViewById(R.id.btn_crash)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                throw new RuntimeException("自定义异常  测试异常");
            }
        });

        mListView = ((ListView) findViewById(R.id.listView));

         adapter=new Myadapter(datas);

        mListView.setAdapter(adapter);


        Ucache ucache = (Ucache) MobAPI.getAPI(Ucache.NAME);

        ucache.queryUcacheGetAll("crash", 1, 20, new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {


                Log.e(TAG, "onSuccess:");

                if (map != null) {


                    Object result = map.get("result");


                    String s = result.toString();

                    try {
                        JSONObject jsonObject=new JSONObject(s);


                        JSONArray array = jsonObject.getJSONArray("data");


                        for (int i1 = 0; i1 < array.length(); i1++) {


                            JSONObject jsonObject1 = array.getJSONObject(i1);


                            datas.add(jsonObject1.getString("v"));
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {


                Log.e(TAG, "onError:" + throwable.getMessage());
            }
        });


    }

    class Myadapter extends BaseAdapter {

        private List<String> datas;

        public Myadapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyViewHoder viewHoder = null;

            if (convertView == null) {

                convertView = LayoutInflater.from(parent.getContext().getApplicationContext()).inflate(R.layout.item, null);

                viewHoder = new MyViewHoder();

                viewHoder.tv = (TextView) convertView.findViewById(R.id.name);

                convertView.setTag(viewHoder);

            } else {

                viewHoder = (MyViewHoder) convertView.getTag();
            }


            String s = datas.get(position);


            viewHoder.tv.setText(s);

            return convertView;
        }


        class MyViewHoder {


            public TextView tv;
        }
    }
}
