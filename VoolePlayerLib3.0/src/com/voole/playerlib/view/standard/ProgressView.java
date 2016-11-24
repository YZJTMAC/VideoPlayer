package com.voole.playerlib.view.standard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.voole.playerlib.R;
import com.voole.playerlib.util.LogVoolePlayer;

class ProgressView extends ImageView {
	private String TAG = ProgressView.class.getName();
	private Context context;
	private int background = -1;
	private int indexBackground = -1;
	private long progress;
	private long max;
	private Paint paint;
	private Rect clipBounds;
	long progressX = 0;

	public ProgressView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ProgressView(Context context) {
		super(context);
		initView(context);
		
	}

	@SuppressLint("NewApi")
	private void initView(Context context) {
		this.context = context;
		if (background == -1) {
			LogVoolePlayer.i(TAG+"initView-------------------------------> X "+ getX() + " Y "+getY());
			background = R.drawable.progress_back_bg;
		}
		if (indexBackground == -1) {
			indexBackground = R.drawable.progress_front_bg;
		}
		this.setBackgroundResource(background);
	}

	public void setProgressBackground(int background) {
		this.background = background;
	}

	public void setProgressIndexBackground(int indexBackground) {
		this.indexBackground = indexBackground;
	}

	public void setMax(long max) {
		this.max = max;
	}
	
	public void setProgress(long progress,boolean isMouseSeek) {
		LogVoolePlayer.i(TAG+"---------------setProgress---------------> "+progress +" max "+max +" isMouseSeek " +isMouseSeek);
		if (progress>=0&&progress <= max) {
			if(!isMouseSeek) {
				progressX = progress;
			}
			this.progress = progress;
		}
		this.invalidate();
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		if (paint == null) {
			paint = new Paint();
			paint.setAntiAlias(true);
		}
			clipBounds = canvas.getClipBounds();
			System.out.println("progress");
		
		if (max >= progress && progress > 0) {
			Bitmap indexbackgroundBitmap = BitmapFactory.decodeResource(context.getResources(), indexBackground);
			NinePatch indexbackground = new NinePatch(indexbackgroundBitmap,indexbackgroundBitmap.getNinePatchChunk(), null);
			int width = clipBounds.right - clipBounds.left;
			double section = (double)width / max;
			
			Rect indexRect = new Rect();
			indexRect.left = clipBounds.left;
			indexRect.top = clipBounds.top;
			indexRect.right = clipBounds.left + (int)(section * progress);
			indexRect.bottom = clipBounds.bottom;
			indexbackground.draw(canvas, indexRect);
		}
		
	}
	float startX = 0;
	float endX = 0;
	double lengthX = 0;
	long progressBar = 0;
	boolean actionMove = false;
	@SuppressLint("UseValueOf")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
            	startX = event.getX();
            	LogVoolePlayer.i(TAG+"onTouchEvent------------------------------->MotionEvent.ACTION_DOWN getX "+ event.getX() + " getY "+event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
            	actionMove = true;
            	endX = event.getX();
            	if(endX>startX){
            		if(endX > this.getWidth()) {
            			endX = this.getWidth();
            		}
            		lengthX = endX - startX;
            	} else {
            		if(endX < 0) {
            			endX = 0;
            		}
            		lengthX = startX - endX;
            	}
            	long currentProgress = new Double(lengthX/this.getWidth()* getMax()).longValue();
            	LogVoolePlayer.i(TAG+"onTouchEvent------------------------------->MotionEvent.ACTION_DOWN startX "+ startX + " endX "+endX);
            	if(endX>startX){
            		progressBar = progressX+currentProgress;
            		if(progressBar > max) {
            			progressBar = max - 5000;
            		}
            	} else{
            		progressBar = progressX -currentProgress;
            		if(progressBar < 0) {
            			progressBar = 0;
            		}
            	}
            	if(onSeekProgressBar != null) {
            		onSeekProgressBar.setSeekProgress(progressBar,true,actionMove);
            	}
            	LogVoolePlayer.i(TAG+"onTouchEvent------------------------------->lengthX "+ lengthX +" startX "+startX +" endX "+endX+" progressX "+progressX  +" currentProgress "+currentProgress +" progressBar "+progressBar);
                break;
            case MotionEvent.ACTION_UP:
            	if(onSeekProgressBar != null) {
            		if(!actionMove) {
            			progressBar = progressX;
            		}
            		onSeekProgressBar.setSeekProgress(progressBar,false,actionMove);
            	}
            	progressX = progress;
            	actionMove = false;
            	LogVoolePlayer.i(TAG+"onTouchEvent------------------------------->MotionEvent.ACTION_UP");
            	break;
        }
		return true;
	}

	public long getMax() {
		return max;
	}

	public double getProgress() {
		return progress;
	}
	
	
	public int getProgressWidth(){
		if (clipBounds!=null) {
			return clipBounds.width();
		}
		return getMeasuredWidth();
	}
	
	private OnSeekProgressBar onSeekProgressBar = null;
	
	public OnSeekProgressBar getOnSeekProgressBar() {
		return onSeekProgressBar;
	}

	public void setOnSeekProgressBar(OnSeekProgressBar onSeekProgressBar) {
		this.onSeekProgressBar = onSeekProgressBar;
	}

	public interface OnSeekProgressBar{
		public void setSeekProgress(long progress,boolean seekEnd,boolean ismove);
	} 

}