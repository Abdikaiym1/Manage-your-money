package com.example.asusx555l.projecttoolbar.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.asusx555l.projecttoolbar.R;

public class CurrencyTextView extends TextView {

    private String name = "";

    public CurrencyTextView(Context context) {
        super(context);
        init(context, null);
    }

    public CurrencyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CurrencyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CurrencyTextView);
            name = a.getString(R.styleable.CurrencyTextView_currencyName);
            /*for (int i = 0; i < a.getIndexCount(); i++) {
                if (a.getIndex(i) == R.styleable.CurrencyTextView_currencyName) {
                    name = a.getString(i);
                }
            }*/

            a.recycle();
        }
    }

    private String handleText(CharSequence text) {
        return text.toString() + " " + name;
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(handleText(text), type);
    }

    @Override
    public CharSequence getText() {
        return super.getText() + name;
    }

    public String getName() {
        return name;
    }

    public void setCurrencyText(String name) {
        this.name = name;
    }
}
