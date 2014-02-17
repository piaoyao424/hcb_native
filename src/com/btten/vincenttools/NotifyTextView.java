package com.btten.vincenttools;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class NotifyTextView extends TextView {

public NotifyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 在XML文件中使用改控件必须定义这个构造函数
	}

	public NotifyTextView(Context content) {
		super(content);
	}
	
	public NotifyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// 获取焦点才能让滚动效果运行
	@Override
	public boolean isFocused() {
		return true;
	}

}
