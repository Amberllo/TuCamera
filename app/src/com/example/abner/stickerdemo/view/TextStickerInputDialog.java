package com.example.abner.stickerdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.abner.stickerdemo.utils.CommonUtils;
import com.share.photoshare.R;
import com.share.photoshare.custom.KeyBoardUtils;

/**
 * Created by Abner on 15/6/12.
 * QQ 230877476
 * Email nimengbo@gmail.com
 */
public class TextStickerInputDialog extends Dialog {
    private final String defaultStr;
    private EditText et_textSticker_input;
    private TextView tv_show_count;
    private ImageView tv_action_done;
    private static final int MAX_COUNT = 150; //字数最大限制33个
    private Context mContext;
    private TextStickerView textStickerView;
    private View root_bubble;

    public TextStickerInputDialog(Context context) {
        super(context, R.style.textsticker_dialog);
        mContext = context;
        defaultStr = context.getString(R.string.double_click_input_text);
        initView();
    }

    public TextStickerInputDialog(Context context, TextStickerView view) {
        super(context, R.style.textsticker_dialog);
        mContext = context;
        defaultStr = context.getString(R.string.double_click_input_text);
        textStickerView = view;
        initView();
    }

    public void setTextStickerView(TextStickerView textStickerView) {
        this.textStickerView = textStickerView;
        if (defaultStr.equals(textStickerView.getFontText())) {
            et_textSticker_input.setText("");
        } else {
            et_textSticker_input.setText(textStickerView.getFontText());
            et_textSticker_input.setSelection(textStickerView.getFontText().length());
        }
    }


    private void initView() {
        setContentView(R.layout.view_input_dialog);
        tv_action_done = (ImageView) findViewById(R.id.tv_action_done);
        et_textSticker_input = (EditText) findViewById(R.id.et_bubble_input);
        tv_show_count = (TextView) findViewById(R.id.tv_show_count);
        root_bubble = findViewById(R.id.root_bubble);

        et_textSticker_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                long textLength = CommonUtils.calculateLength(s);
                tv_show_count.setText(String.valueOf(MAX_COUNT - textLength));
                if (textLength > MAX_COUNT) {
                    tv_show_count.setTextColor(mContext.getResources().getColor(R.color.red_e73a3d));
                } else {
                    tv_show_count.setTextColor(mContext.getResources().getColor(R.color.grey_8b8b8b));
                }

                if(mOnTextChangeCallback!=null){
                    mOnTextChangeCallback.onText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_textSticker_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    done();
                    return true;
                }
                return false;
            }
        });
        tv_action_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
        root_bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
    }


    @Override
    public void show() {
        super.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               KeyBoardUtils.show(et_textSticker_input);
            }
        }, 100);


    }

    @Override
    public void dismiss() {
        super.dismiss();
        KeyBoardUtils.hide(et_textSticker_input);
    }

    public void setOnTextChangeCallback(OnTextChangeCallback onTextChangeCallback) {
        this.mOnTextChangeCallback = onTextChangeCallback;
    }

    public void removeOnTextChangeCallback() {
        this.mOnTextChangeCallback = null;
    }

    public interface CompleteCallBack {
        void onComplete(View bubbleTextView, String str);
    }

    private CompleteCallBack mCompleteCallBack;

    public void setCompleteCallBack(CompleteCallBack completeCallBack) {
        this.mCompleteCallBack = completeCallBack;
    }

    private OnTextChangeCallback mOnTextChangeCallback;
    public interface OnTextChangeCallback {
        void onText(String text);
    }

    private void done() {

        System.out.println("------------ dialog onDone close Keyboard ------------");

        KeyBoardUtils.hide(root_bubble);

        dismiss();
        if (mCompleteCallBack != null) {
            String str;
            if (TextUtils.isEmpty(et_textSticker_input.getText())) {
                str = "";
            } else {
                str = et_textSticker_input.getText().toString();
            }
            mCompleteCallBack.onComplete(textStickerView, str);
        }
    }
}
