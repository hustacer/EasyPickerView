package com.hzn.easypickerview;

import android.content.Context;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import android.os.Handler;

public class RateAdapter extends BaseAdapter {
    private List<RateBean> mList;
    private LayoutInflater mLayoutInflater;
    private int touchItemPosition = -1;
    private double mCurrtrate = 0.000000;
    private Handler mHandler;

    public RateAdapter(Context context, List<RateBean> mList, Handler handler) {
        this.mList = mList;
        mHandler = handler;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public interface OnTextChangeListener {
        void onTextsChange(double rate, int position);
    }

    private OnTextChangeListener mOnTextChangeListener;

    public void setOnTextChangeListener(OnTextChangeListener listener) {
        this.mOnTextChangeListener = listener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.ratedetail_layout, null);
            viewHolder.rateIconUrlIv = convertView.findViewById(R.id.rate_iv);
            viewHolder.rateCountryTv = convertView.findViewById(R.id.rate_tv);
            viewHolder.rateContentEv = convertView.findViewById(R.id.rate_ev);

            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.rateContentEv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (finalViewHolder.rateContentEv.hasFocus()) {
                        finalViewHolder.rateContentEv.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                mCurrtrate = Double.parseDouble(s.toString());
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                Message message = mHandler.obtainMessage();
                                message.what = MainActivity.WHAT_RATE;
                                message.obj = Double.parseDouble(s.toString()) / mCurrtrate;
                                mHandler.sendMessage(message);

                                if(mOnTextChangeListener!=null) {
                                    mOnTextChangeListener.onTextsChange(Double.parseDouble(s.toString()) / mCurrtrate, position);
                                }
                            }
                        });
                    } else {
                        finalViewHolder.rateContentEv.clearTextChangedListeners();
                    }
                }
            });

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.rateCountryTv.setText(mList.get(position).rateCountry);
        viewHolder.rateContentEv.setText(mList.get(position).rateContent);

        return convertView;
    }

    class ViewHolder {
        public TextView rateCountryTv;
        public ExtendedEditText rateContentEv;
        public ImageView rateIconUrlIv;
//        public RateTextWatcher myTextWatcher;
//
//        public void updatePosition(int myPosition) {
//            myTextWatcher.updateWatcherPosition(myPosition);
//        }
    }
}
