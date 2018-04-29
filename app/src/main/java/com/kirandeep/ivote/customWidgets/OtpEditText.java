package com.kirandeep.ivote.customWidgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kirandeep.ivote.R;

/**
 * Created by abc on 12-04-2018.
 */

public class OtpEditText extends android.support.v7.widget.AppCompatEditText {

    public static final String XML_NAMESPACE_ANDROID = "http://schemas.android.com/apk/res/android";

    float mSpace = 24; //24 dp by default
    float mCharSize = 0;
    float mNumChars = 4;
    float mLineSpacing = 8; //8dp by default
    float mMaxLength = 0;

    private OnClickListener mClickListener;

    private float mLineStroke = 1; //1dp by default
    private Paint mLinesPaint;
    int[][] mStates = new int[][]{
            new int[]{android.R.attr.state_selected}, // selected
            new int[]{android.R.attr.state_focused}, // focused
            new int[]{-android.R.attr.state_focused}, // unfocused
    };

    int[] mColors = new int[]{
            getResources().getColor(R.color.colorAccent),
            Color.BLACK,
            Color.GRAY
    };

    ColorStateList mColorStates = new ColorStateList(mStates, mColors);

    private int getColorForState(int... states) {
        return mColorStates.getColorForState(states, Color.GRAY);
    }
    /* next = is the current char the next character to be input? */
    private void updateColorForLines(boolean next) {
        if (isFocused()) {
            mLinesPaint.setColor(
                    getColorForState(android.R.attr.state_focused));
            if (next) {
                mLinesPaint.setColor(
                        getColorForState(android.R.attr.state_selected));
            }
        } else {
            mLinesPaint.setColor(
                    getColorForState(-android.R.attr.state_focused));
        }
    }


    public OtpEditText(Context context) {
        super(context);
    }

    public OtpEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public OtpEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setBackgroundResource(0);

        float multi = context.getResources().getDisplayMetrics().density;
        mSpace = multi * mSpace; //convert to pixels for our density
        mLineSpacing = multi * mLineSpacing; //convert to pixels
        mMaxLength = attrs.getAttributeIntValue(
                XML_NAMESPACE_ANDROID, "maxLength", 4);
        mNumChars = mMaxLength;

        //Disable copy paste
        super.setCustomSelectionActionModeCallback(
                new ActionMode.Callback() {
                    public boolean onPrepareActionMode(ActionMode mode,
                                                       Menu menu) {
                        return false;
                    }

                    public void onDestroyActionMode(ActionMode mode) {
                    }

                    public boolean onCreateActionMode(ActionMode mode,
                                                      Menu menu) {
                        return false;
                    }

                    public boolean onActionItemClicked(ActionMode mode,
                                                       MenuItem item) {
                        return false;
                    }
                });
        //When tapped, move cursor to end of the text
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelection(getText().length());
                if (mClickListener != null) {
                    mClickListener.onClick(v);
                }
            }
        });

        mLineStroke = multi * mLineStroke;
        mLinesPaint = new Paint(getPaint());
        mLinesPaint.setStrokeWidth(mLineStroke);

    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mClickListener = l;
    }

    @Override
    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        throw new RuntimeException("setCustomSelectionActionModeCallback() not supported.");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int availableWidth =
                getWidth() - getPaddingRight() - getPaddingLeft();

        if (mSpace < 0) {
            mCharSize = (availableWidth / (mNumChars * 2 - 1));
        } else {
            mCharSize =
                    (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
        }

        int startX = getPaddingLeft();
        int bottom = getHeight() - getPaddingBottom();

        //Text Width
        Editable text = getText();
        int textLength = text.length();
        float[] textWidths = new float[textLength];
        getPaint().getTextWidths(getText(), 0, textLength, textWidths);

        for (int i = 0; i < mNumChars; i++) {
            updateColorForLines(i == textLength);
            canvas.drawLine(
                    startX, bottom, startX + mCharSize, bottom, mLinesPaint);

            if (getText().length() > i) {
                float middle = startX + mCharSize / 2;
                canvas.drawText(text,
                        i,
                        i + 1,
                        middle - textWidths[0] / 2,
                        bottom - mLineSpacing,
                        getPaint());
            }

            if (mSpace < 0) {
                startX += mCharSize * 2;
            } else {
                startX += mCharSize + mSpace;
            }

        }
    }
}
