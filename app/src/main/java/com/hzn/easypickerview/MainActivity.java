package com.hzn.easypickerview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final int COUNTRY_COUNT = 168;
    public final int EASYPICKERVIEW_COUNT = 5;
    public static final int WHAT_RATE = 1;
    private QueryTask mQueryTask;
    private ListView mListView;
    private List<RateBean> mRateBeanList;
    private RateAdapter mRateAdapter;
    private List<String> mCounttryList;
    private Double mRate;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_RATE:
                    mRate = (Double) msg.obj;
                    Log.d("easypickerview", "WHAT_RATE :" + mRate);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get default country information from xml
        getCountryList();

        initView();

        // get data from retrofit
//        initDataRetrofit();

        // get data from json
        mQueryTask = new QueryTask(MainActivity.this);
        initDataJson();

        // set ListView
        initListView();
    }

    private void getCountryList() {
        mCounttryList = new ArrayList<>();
        int resId = 0;
        String strName = "";
        String strValue = "";
        for (int i = 0; i < COUNTRY_COUNT; i++) {
            strName = "rate_country_";
            strName += i;
            resId = getResources().getIdentifier(strName, "string", "com.hzn.easypickerview");
            strValue = getResources().getString(resId);
            mCounttryList.add(strValue);
        }
    }

    private void initView() {
        int[] EasyPickerViewArray = new int[] {R.id.epv_h, R.id.epv_m, R.id.epv_s, R.id.epv_s1, R.id.epv_s2};
        EasyPickerView epvArray;

        mListView = findViewById(R.id.lv);

        for (int i = 0; i < EASYPICKERVIEW_COUNT; i++) {
            epvArray = findViewById(EasyPickerViewArray[i]);

            final ArrayList<String> hDataList = new ArrayList<>();
            for (int j = 0; j < COUNTRY_COUNT; j++) {
                hDataList.add("" + mCounttryList.get(j));
            }

            epvArray.setDataList(hDataList);
            final int finalI = i;
            epvArray.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
                @Override
                public void onScrollChanged(int curIndex) {
                    updateListViewItem(curIndex, hDataList, finalI);
                }

                @Override
                public void onScrollFinished(int curIndex) {
                    updateListViewItem(curIndex, hDataList, finalI);
                }
            });
        }
    }

    private void updateListViewItem(int curIndex, ArrayList<String> hDataList, int finalI) {
        RateBean rateBean = new RateBean();
        String key = "" + hDataList.get(curIndex);
        rateBean.rateCountry = key;
        rateBean.rateContent = "" + mQueryTask.getRateMap().get(key);
        mRateBeanList.set(finalI, rateBean);
        mRateAdapter.notifyDataSetChanged();
    }

//    private void initDataRetrofit() {
//        // hours
//        final EditText et_h = (EditText) findViewById(R.id.et_h);
//
//        Button btnTo = (Button) findViewById(R.id.btn_to_h);
//        btnTo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(et_h.getText())) {
//                    int index = Integer.parseInt(et_h.getText().toString());
//                    epvH.moveTo(index);
//                }
//            }
//        });
//
//        Button btnBy = (Button) findViewById(R.id.btn_by_h);
//        btnBy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(et_h.getText())) {
//                    int index = Integer.parseInt(et_h.getText().toString());
//                    epvH.moveBy(index);
//                }
//            }
//        });
//
//        // minutes
//        final EditText et_m = (EditText) findViewById(R.id.et_m);
//
//        Button btnTo_m = (Button) findViewById(R.id.btn_to_m);
//        btnTo_m.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(et_m.getText())) {
//                    int index = Integer.parseInt(et_m.getText().toString());
//                    epvM.moveTo(index);
//                }
//            }
//        });
//
//        Button btnBy_m = (Button) findViewById(R.id.btn_by_m);
//        btnBy_m.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(et_m.getText())) {
//                    int index = Integer.parseInt(et_m.getText().toString());
//                    epvM.moveBy(index);
//                }
//            }
//        });
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://apilayer.net/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        PostRequest_Interface postRequest_interface =retrofit.create(PostRequest_Interface.class);
//
//        Call<Translation> call = postRequest_interface.getCall();
//        call.enqueue(new Callback<Translation>() {
//            @Override
//            public void onResponse(Call<Translation> call, Response<Translation> response) {
//                Log.d("easypickerview", "取得成功");
//                System.out.println(response.body().getQuotes());
//            }
//
//            @Override
//            public void onFailure(Call<Translation> call, Throwable t) {
//                Log.d("easypickerview", "取得失败");
//            }
//        });
//    }

    private void initDataJson() {
        mQueryTask.execute("http://apilayer.net/api/live?access_key=1a7f7e4a6c9fcab8b5640ed0fcf2e384&format=1");
    }

    private void initListView() {
        mRateBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RateBean rateBean = new RateBean();
            rateBean.rateCountry = "USD";
            rateBean.rateContent = "1.000000";
            mRateBeanList.add(rateBean);
        }
        mRateAdapter = new RateAdapter(MainActivity.this, mRateBeanList, mHandler);
        mRateAdapter.setOnTextChangeListener(new RateAdapter.OnTextChangeListener() {
            @Override
            public void onTextsChange(double rate, int position) {
                for (int i = 0; i < EASYPICKERVIEW_COUNT; i++) {
                    if ((rate != 1.0) && (rate != 0.0)) {
                        RateBean rateBean = new RateBean();
                        String key = "" + mRateBeanList.get(i).rateCountry;
                        rateBean.rateCountry = key;
                        // recount the result after changing rate account
                        double rateRelst = rate * Double.parseDouble(mRateBeanList.get(i).rateContent);
                        rateBean.rateContent = "" + rateRelst;

                        mRateBeanList.set(i, rateBean);
                        mRateAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mListView.setAdapter(mRateAdapter);
    }
}
