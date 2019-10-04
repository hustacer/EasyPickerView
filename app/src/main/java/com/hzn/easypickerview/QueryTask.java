package com.hzn.easypickerview;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class QueryTask extends AsyncTask<String, Void, Void> {
    private Context mContext;
    private HashMap<String, Double> mRateMap;
    private List<String> mCountryList;

    public QueryTask(Context mContext) {
        this.mContext = mContext;
        mRateMap = new HashMap<String, Double>();
        mCountryList = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(String... strings) {
        return getJsonData(strings[0]);
    }

    private Void getJsonData(String url) {
//        try {
//            String jsonString = null;
//            try {
//                jsonString = readStream(new URL(url).openStream());
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//
//            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject(jsonString);
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//
//            // 遍历获取json中的key值
//            Iterator<String> sIterator = jsonObject.keys();
//            while (sIterator.hasNext()) {
//                // 获得key
//                String key = sIterator.next();
//                // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
//                Long value = jsonObject.getLong(key);
//                System.out.println("key: " + key + ",value" + value);
//                if ( key != "") {
//                    mRateMap.put(key, value);
//                    mCountryList.add(key);
//                }
//            }
//        } catch (JSONException e1) {
//            e1.printStackTrace();
//        }
        mRateMap.put("USD", 1.0001);
        mRateMap.put("AED", 2.0002);
        mRateMap.put("AMD", 3.0003);
        mRateMap.put("BYN", 4.0004);
        mRateMap.put("CNY", 5.0005);
        mCountryList.add("USD");
        mCountryList.add("AED");
        mCountryList.add("AMD");
        mCountryList.add("BYN");
        mCountryList.add("CNY");

        return null;
    }

    private String readStream(InputStream is) {
        InputStreamReader inputStreamReader;
        String result ="";

        try {
            String line = "";
            inputStreamReader = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(inputStreamReader);

            while ((line = br.readLine()) != null) {
                result += line;
            }

            inputStreamReader.close();
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public HashMap<String, Double> getRateMap() {
        return mRateMap;
    }

    public List<String> getCountryList() {
        return mCountryList;
    }
}
