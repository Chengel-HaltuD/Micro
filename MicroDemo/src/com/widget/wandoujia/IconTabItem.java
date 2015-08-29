package com.widget.wandoujia;

import com.demo.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

/**
 * 图片居中，并可以设置图片大小的TabItem
 * 
 * @author Darcy
 * @date 2014-12-17 下午4:57:07
 * @version V1.0
 */
public class IconTabItem extends RadioButton {

	private Drawable buttonDrawable;

	private int mDrawableWidth;
	private int mDrawableHeight;

	public IconTabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		/**
		 * 百度查的注释
		 * 其中，TypedArray实例是个属性的容器，context.obtainStyledAttributes()方法返回得到。
		 * AttributeSet是节点的属性集合，在本例中是<com.easymorse.textbutton.(一个节点)TextButton节点中的属性集合。
		 */
		TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CompoundButton, 0, 0);
		buttonDrawable = a.getDrawable(R.styleable.CompoundButton_android_button);
		mDrawableWidth = a.getDimensionPixelSize(R.styleable.CompoundButton_drawableWidth, 0);
		mDrawableHeight = a.getDimensionPixelSize(R.styleable.CompoundButton_drawableHeight, 0);
		setButtonDrawable(R.drawable.empty_drawable);
		a.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (buttonDrawable != null) {
			buttonDrawable.setState(getDrawableState());
			final int verticalGravity = getGravity()& Gravity.VERTICAL_GRAVITY_MASK;
			final int buttonHeight = mDrawableWidth == 0? buttonDrawable.getIntrinsicHeight() : mDrawableWidth;

			int y = 0;

			switch (verticalGravity) {
			case Gravity.BOTTOM:
				y = getHeight() - buttonHeight;
				break;
			case Gravity.CENTER_VERTICAL:
				y = (getHeight() - buttonHeight) / 2;
				break;
			}

			int buttonWidth = mDrawableHeight == 0 ? buttonDrawable.getIntrinsicWidth() : mDrawableHeight;
			int buttonLeft = (getWidth() - buttonWidth) / 2;
			buttonDrawable.setBounds(buttonLeft, y, buttonLeft + buttonWidth, y+ buttonHeight);
			buttonDrawable.draw(canvas);
		}
	}
}
