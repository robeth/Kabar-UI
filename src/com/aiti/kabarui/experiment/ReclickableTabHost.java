package com.aiti.kabarui.experiment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TabHost;

public class ReclickableTabHost extends TabHost {
	private OnCurrentItemListenerClick onCurrentItemListenerClick;
	
    public ReclickableTabHost(Context context) {
        super(context);
    }

    public ReclickableTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentTab(int index) {
        if (index == getCurrentTab()) {
            // FIRE OFF NEW LISTENER
        	if(onCurrentItemListenerClick != null)
        		onCurrentItemListenerClick.onCurrentClick(index);
        } else {
            super.setCurrentTab(index);
        }
    }
    
    public OnCurrentItemListenerClick getOnCurrentItemListenerClick() {
		return onCurrentItemListenerClick;
	}

	public void setOnCurrentItemListenerClick(
			OnCurrentItemListenerClick onCurrentItemListenerClick) {
		this.onCurrentItemListenerClick = onCurrentItemListenerClick;
	}

	public static abstract class OnCurrentItemListenerClick {
    	public abstract void onCurrentClick(int index);
    }
}