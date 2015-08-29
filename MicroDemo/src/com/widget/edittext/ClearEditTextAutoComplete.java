/*
 * Copyright (C) 2013 Chengel_HaltuD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.widget.edittext;



import com.demo.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

/**
 * 
 * @ClassName: ClearEditTextAutoComplete
 * @Description: TODO
 * @Author：Chengel_HaltuD
 * @Date：2015-5-30 下午3:38:20
 * @version V1.0
 *
 */
public class ClearEditTextAutoComplete extends AutoCompleteTextView implements  
        OnFocusChangeListener, TextWatcher { 
	/**
     * 晃动动画
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts){
    	Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
    	translateAnimation.setInterpolator(new CycleInterpolator(counts));
    	translateAnimation.setDuration(1000);
    	return translateAnimation;
    } 
 
    /**
	 * 删除按钮的引用
	 */
    private Drawable mClearDrawable; 
 
    public ClearEditTextAutoComplete(Context context) { 
    	this(context, null); 
    	init(context); 
    } 
    
    public ClearEditTextAutoComplete(Context context, AttributeSet attrs) { 
    	//这里构造方法也很重要，不加这个很多属性不能再XML里面定义
    	this(context, attrs, android.R.attr.editTextStyle); 
    	init(context); 
    }
    
    
    public ClearEditTextAutoComplete(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        init(context); 
    } 
 
 
    @Override 
    public void afterTextChanged(Editable s) { 
         
    } 
 
    @Override 
    public void beforeTextChanged(CharSequence s, int start, int count, 
            int after) { 
         
    } 
 
 
    private void init() { 
    	//获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
    	mClearDrawable = getCompoundDrawables()[2]; 
        if (mClearDrawable == null) { 
        	mClearDrawable = getResources() 
                    .getDrawable(R.drawable.lockopenxha); 
        } 
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight()); 
        setClearIconVisible(false); 
        setOnFocusChangeListener(this); 
        addTextChangedListener(this); 
    } 
     
    
    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override 
    public void onFocusChange(View v, boolean hasFocus) { 
        if (hasFocus) { 
            setClearIconVisible(getText().length() > 0); 
        } else { 
            setClearIconVisible(false); 
        } 
    } 
 
    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override 
    public void onTextChanged(CharSequence s, int start, int count, 
            int after) { 
        setClearIconVisible(s.length() > 0); 
    } 
 
    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override 
    public boolean onTouchEvent(MotionEvent event) { 
        if (getCompoundDrawables()[2] != null) { 
            if (event.getAction() == MotionEvent.ACTION_UP) { 
            	boolean touchable = event.getX() > (getWidth() 
                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) 
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) { 
                    this.setText(""); 
                } 
            } 
        } 
 
        return super.onTouchEvent(event); 
    } 
    
   
    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) { 
        Drawable right = visible ? mClearDrawable : null; 
        setCompoundDrawables(getCompoundDrawables()[0], 
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]); 
    }
    
    
    /**
     * 设置晃动动画
     */
    public void setShakeAnimation(){
    	this.setAnimation(shakeAnimation(5));
    }
 
    
    
    
    
    private static final String TAG = "EmailAutoCompleteTextView";  
    private String[] emailSufixs = new String[]{" ","@qq.com", "@163.com", "@126.com", "@gmail.com", "@sina.com", "@hotmail.com",  
            "@yahoo.cn", "@sohu.com", "@foxmail.com", "@139.com", "@yeah.net", "@vip.qq.com", "@vip.sina.com"};  
    public void setAdapterString(String[] es) {  
        if (es != null && es.length > 0)  
            this.emailSufixs = es;
    }
    
    private void init(final Context context) {  
        //adapter中使用默认的emailSufixs中的数据，可以通过setAdapterString来更改  
        this.setAdapter(new EmailAutoCompleteAdapter(context, R.layout.item_email_auto_complete, emailSufixs));  
        //使得在输入1个字符之后便开启自动完成  
        this.setThreshold(1);  
        this.setOnFocusChangeListener(new OnFocusChangeListener() {  
            @Override  
            public void onFocusChange(View v, boolean hasFocus) {  
                if (hasFocus) {  
                    String text = ClearEditTextAutoComplete.this.getText().toString();  
                    //当该文本域重新获得焦点后，重启自动完成  
                    if (!"".equals(text))  
                        performFiltering(text, 0);  
                } else {  
                    //当文本域丢失焦点后，检查输入email地址的格式  
                    ClearEditTextAutoComplete ev = (ClearEditTextAutoComplete) v;  
                    String text = ev.getText().toString();  
                    //这里正则写的有点粗暴:)  
                    if (text != null && text.matches("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")) {  
  
                    } else {  
                        //Toast toast = Toast.makeText(context, "邮件地址格式不正确", Toast.LENGTH_SHORT);  
                       // toast.show();  
                    }  
                }  
            }  
        });  
    }  
  
    @Override  
    protected void replaceText(CharSequence text) {  
        //当我们在下拉框中选择一项时，android会默认使用AutoCompleteTextView中Adapter里的文本来填充文本域  
        //因为这里Adapter中只是存了常用email的后缀  
        //因此要重新replace逻辑，将用户输入的部分与后缀合并  
        Log.i(TAG + " replaceText", text.toString());  
        String t = this.getText().toString();  
        int index = t.indexOf("@");  
        if (index != -1)  
            t = t.substring(0, index);  
        super.replaceText(t + text);  
    }  
  
  
    @Override
	public void performFiltering(CharSequence text, int keyCode) {  
        //该方法会在用户输入文本之后调用，将已输入的文本与adapter中的数据对比，若它匹配  
        //adapter中数据的前半部分，那么adapter中的这条数据将会在下拉框中出现  
        Log.i(TAG + " performFiltering", text.toString() + "   " + keyCode);  
        String t = text.toString();  
        //因为用户输入邮箱时，都是以字母，数字开始，而我们的adapter中只会提供以类似于"@163.com"  
        //的邮箱后缀，因此在调用super.performFiltering时，传入的一定是以"@"开头的字符串  
        int index = t.indexOf("@");  
        if (index == -1) {  
            if (t.matches("^[a-zA-Z0-9_]+$")) {  
                super.performFiltering("@", keyCode);  
            } else  
                this.dismissDropDown();//当用户中途输入非法字符时，关闭下拉提示框  
        } else {  
            super.performFiltering(t.substring(index), keyCode);  
        }  
    }  
  
  
    private class EmailAutoCompleteAdapter extends ArrayAdapter<String> {  
        public EmailAutoCompleteAdapter(Context context, int textViewResourceId, String[] email_s) {  
            super(context, textViewResourceId, email_s);  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            Log.i(TAG, "in GetView");  
            View v = convertView;  
            if (v == null)  
                v = LayoutInflater.from(getContext()).inflate(  
                        R.layout.item_email_auto_complete, null);  
            TextView tv = (TextView) v.findViewById(R.id.tv); 
            String t = ClearEditTextAutoComplete.this.getText().toString();  
            int index = t.indexOf("@");  
            if (index != -1)  
                t = t.substring(0, index);  
            //将用户输入的文本与adapter中的email后缀拼接后，在下拉框中显示  
            tv.setText(t + getItem(position));  
            Log.i(TAG, tv.getText().toString());  
            return v;  
        }  
  
    }
 
}
