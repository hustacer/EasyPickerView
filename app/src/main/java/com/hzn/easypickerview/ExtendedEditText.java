package com.hzn.easypickerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

@SuppressLint("AppCompatCustomView")
public class ExtendedEditText extends EditText {
    private ArrayList<TextWatcher> mListeners = null;

    public ExtendedEditText(Context context) {
        super(context);
    }

    public ExtendedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        Log.d("easypickerview", "addTextChangedListener");
        if (mListeners == null) {
            mListeners = new ArrayList<TextWatcher>();
        }

        mListeners.add(watcher);

        super.addTextChangedListener(watcher);
    }

    @Override
    public void removeTextChangedListener(TextWatcher watcher) {
        Log.d("easypickerview", "removeTextChangedListener");
        if (mListeners != null) {
            int i = mListeners.indexOf(watcher);
            if (i >= 0) {
                mListeners.remove(i);
            }
        }

        super.removeTextChangedListener(watcher);
    }

    public void clearTextChangedListeners() {
        Log.d("easypickerview", "clearTextChangedListeners");
        if (mListeners != null) {
            for (TextWatcher textWatcher : mListeners) {
                super.removeTextChangedListener(textWatcher);
            }

            mListeners.clear();
            mListeners = null;
        }
    }
}
