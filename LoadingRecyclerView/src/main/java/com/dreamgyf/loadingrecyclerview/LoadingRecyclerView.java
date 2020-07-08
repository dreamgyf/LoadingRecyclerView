package com.dreamgyf.loadingrecyclerview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author dreamgyf, g2409197994@gmail.com
 */
public class LoadingRecyclerView extends RecyclerView {

	public static class Direction {
		public final static int START = 0x01;
		public final static int END = 0x02;
		final static int BOTH = 0x03;
	}

	private Context mContext;

	private boolean mCanLoadOfStart;

	private boolean mCanLoadOfEnd;

	private int mDirection;

	private boolean isLoadingOfStart = false;

	private boolean isLoadingOfEnd = false;

	private RecyclerView.LayoutManager mLayoutManager;

	private RecyclerView.OnScrollListener mOnLoadMoreListener;

	private LoadingListener mLoadingListener;

	public interface LoadingListener {

		void onLoadMore(int direction, int offset);
	}

	public void setLoadingListener(LoadingListener listener) {
		mLoadingListener = listener;
	}

	public LoadingRecyclerView(@NonNull Context context) {
		super(context);
		init(context, null);
	}

	public LoadingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		mContext = context;
		initAttrs(attrs);
		initLoadListener();
		initLoadingView();
	}

	/**
	 * 初始化参数
	 */
	private void initAttrs(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LoadingRecyclerView, 0, 0);
			mCanLoadOfStart = typedArray.getBoolean(R.styleable.LoadingRecyclerView_enableLoad, true);
			mCanLoadOfEnd = typedArray.getBoolean(R.styleable.LoadingRecyclerView_enableLoad, true);
			mDirection = typedArray.getInt(R.styleable.LoadingRecyclerView_direction, Direction.END);
			mCircleColor = typedArray.getColor(R.styleable.LoadingRecyclerView_loadingBackgroundColor, DEFAULT_CIRCLE_COLOR);
			mCircularProgressColor = typedArray.getColor(R.styleable.LoadingRecyclerView_loadingArcColor, DEFAULT_CIRCULAR_PROGRESS_COLOR);
			typedArray.recycle();
		} else {
			enableLoad();
			mDirection = Direction.END;
		}
	}

	public void enableLoad() {
		mCanLoadOfStart = true;
		mCanLoadOfEnd = true;
	}

	public void enableLoad(int direction) {
		if (direction == Direction.START) {
			mCanLoadOfStart = true;
		} else if (direction == Direction.END) {
			mCanLoadOfEnd = true;
		}
	}

	public void disableLoad() {
		mCanLoadOfStart = false;
		mCanLoadOfEnd = false;
	}

	public void disableLoad(int direction) {
		if (direction == Direction.START) {
			mCanLoadOfStart = false;
		} else if (direction == Direction.END) {
			mCanLoadOfEnd = false;
		}
	}

	private void initLoadListener() {
		mOnLoadMoreListener = new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (mCanLoadOfStart
						&& (mDirection & Direction.START) == Direction.START
						&& !isLoadingOfStart
						&& newState == SCROLL_STATE_IDLE
						&& mLayoutManager != null && isScrollToStart()) {
					loadStart(Direction.START);
				}
				if (mCanLoadOfEnd
						&& (mDirection & Direction.END) == Direction.END
						&& !isLoadingOfEnd
						&& newState == SCROLL_STATE_IDLE
						&& mLayoutManager != null && isScrollToEnd()) {
					loadStart(Direction.END);
				}
			}
		};
		super.addOnScrollListener(mOnLoadMoreListener);
	}

	private boolean isScrollToStart() {
		if (mLayoutManager instanceof GridLayoutManager) {
			GridLayoutManager layoutManager = (GridLayoutManager) mLayoutManager;
			int position = layoutManager.findFirstCompletelyVisibleItemPosition();
			return position == 0;
		} else if (mLayoutManager instanceof LinearLayoutManager) {
			LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutManager;
			int position = layoutManager.findFirstCompletelyVisibleItemPosition();
			return position == 0;
		} else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mLayoutManager;
			int column = layoutManager.getSpanCount();
			int[] positions = new int[column];
			layoutManager.findFirstCompletelyVisibleItemPositions(positions);
			for (int position : positions) {
				if (position >= 0 && position < column) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isScrollToEnd() {
		if (mLayoutManager instanceof GridLayoutManager) {
			GridLayoutManager layoutManager = (GridLayoutManager) mLayoutManager;
			int position = layoutManager.findLastCompletelyVisibleItemPosition();
			return position == layoutManager.getItemCount() - 1;
		} else if (mLayoutManager instanceof LinearLayoutManager) {
			LinearLayoutManager layoutManager = (LinearLayoutManager) mLayoutManager;
			int position = layoutManager.findLastCompletelyVisibleItemPosition();
			return position == layoutManager.getItemCount() - 1;
		} else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
			StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) mLayoutManager;
			int column = layoutManager.getSpanCount();
			int[] positions = new int[column];
			layoutManager.findLastCompletelyVisibleItemPositions(positions);
			for (int position : positions) {
				if (position < layoutManager.getItemCount() && position >= layoutManager.getItemCount() - column) {
					return true;
				}
			}
		}
		return false;
	}

	public void setLayoutManager(@Nullable RecyclerView.LayoutManager layout) {
		mLayoutManager = layout;
		super.setLayoutManager(mLayoutManager);
	}

	public void loadFinish(int direction) {
		loadEnd(direction);
	}

	public void setDirection(int direction) {
		mDirection = direction;
	}

	private void loadStart(int direction) {
		if (direction == Direction.START) {
			mLoadingAnimatorOfStart.start();
			isLoadingOfStart = true;
		} else if (direction == Direction.END) {
			mLoadingAnimatorOfEnd.start();
			isLoadingOfEnd = true;
		}

		if (mLoadingListener != null) {
			mLoadingListener.onLoadMore(direction, mLayoutManager.getItemCount());
		}
	}

	private void loadEnd(int direction) {
		if (direction == Direction.START) {
			mLoadingAnimatorOfStart.cancel();
			isLoadingOfStart = false;
		} else if (direction == Direction.END) {
			mLoadingAnimatorOfEnd.cancel();
			isLoadingOfEnd = false;
		}
	}

	private void initLoadingView() {
		initPaint();
		initMatrix();
		initRect();
		initAnimator();
	}

	/**
	 * Loading背景色
	 */
	private static final int SHADOW_COLOR = 0x1E000000;
	private static final int DEFAULT_CIRCLE_COLOR = 0xFFFAFAFA;
	private int mCircleColor = DEFAULT_CIRCLE_COLOR;
	/**
	 * Loading背景
	 */
	private Matrix mLoadingMatrixOfStart;
	private Matrix mLoadingMatrixOfEnd;

	private Canvas mLoadingCanvas;
	private Bitmap mLoadingBitmap;
	private Paint mLoadingPaint;
	/**
	 * Loading圆弧
	 */
	private static final int DEFAULT_CIRCULAR_PROGRESS_COLOR = Color.BLACK;
	private int mCircularProgressColor = DEFAULT_CIRCULAR_PROGRESS_COLOR;
	private Paint mCircularProgressPaint;
	private static final float CIRCULAR_PROGRESS_SCALE = 0.65f;
	private RectF mCircularProgressRectF;
	private float mCircularProgressTranslateXOfStart;
	private float mCircularProgressTranslateYOfStart;
	private float mCircularProgressTranslateXOfEnd;
	private float mCircularProgressTranslateYOfEnd;
	private int mLoadingCanvasRadius;
	private float mLoadingRadius = 50;

	/**
	 * Loading动画坐标位置
	 */
	static class Position {
		float x;
		float y;

		Position(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}

	private Position mLoadingStartPosition;
	private Position mLoadingEndPosition;

	private void initPaint() {
		mLoadingPaint = new Paint();
		mLoadingPaint.setStyle(Paint.Style.FILL);
		mLoadingPaint.setShadowLayer(mLoadingRadius / 3, 0, 0, SHADOW_COLOR);
		mLoadingPaint.setColor(mCircleColor);
		mCircularProgressPaint = new Paint();
		mCircularProgressPaint.setStyle(Paint.Style.STROKE);
		mCircularProgressPaint.setStrokeWidth(mLoadingRadius / 7);
		mCircularProgressPaint.setAntiAlias(true);
		mCircularProgressPaint.setColor(mCircularProgressColor);
	}

	private void initMatrix() {
		mLoadingMatrixOfStart = new Matrix();
		mLoadingMatrixOfEnd = new Matrix();
	}

	private void initRect() {
		mCircularProgressRectF = new RectF();
	}

	private void measureLoadingView() {
		mLoadingCanvasRadius = (int) (mLoadingRadius * 1.5);

		mCircularProgressRectF.left = mLoadingCanvasRadius * CIRCULAR_PROGRESS_SCALE;
		mCircularProgressRectF.top = mLoadingCanvasRadius * CIRCULAR_PROGRESS_SCALE;
		mCircularProgressRectF.right = mLoadingCanvasRadius * 2 - mLoadingCanvasRadius * CIRCULAR_PROGRESS_SCALE;
		mCircularProgressRectF.bottom = mLoadingCanvasRadius * 2 - mLoadingCanvasRadius * CIRCULAR_PROGRESS_SCALE;

		int orientation = VERTICAL;
		if (mLayoutManager instanceof LinearLayoutManager) {
			orientation = ((LinearLayoutManager) mLayoutManager).getOrientation();
		} else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
			orientation = ((StaggeredGridLayoutManager) mLayoutManager).getOrientation();
		}
		if (orientation == VERTICAL) {
			if (mLoadingStartPosition == null) {
				mCircularProgressTranslateXOfStart = (float) getWidth() / 2 - mLoadingCanvasRadius;
				mCircularProgressTranslateYOfStart = mLoadingCanvasRadius;
			} else {
				mCircularProgressTranslateXOfStart = position2Translate(mLoadingStartPosition.x);
				mCircularProgressTranslateYOfStart = position2Translate(mLoadingStartPosition.y);
			}

			if (mLoadingEndPosition == null) {
				mCircularProgressTranslateXOfEnd = (float) getWidth() / 2 - mLoadingCanvasRadius;
				mCircularProgressTranslateYOfEnd = (float) getHeight() - mLoadingCanvasRadius * 2 - mLoadingCanvasRadius;
			} else {
				mCircularProgressTranslateXOfEnd = position2Translate(mLoadingEndPosition.x);
				mCircularProgressTranslateYOfEnd = position2Translate(mLoadingEndPosition.y);
			}
		} else {
			if(mLoadingStartPosition == null) {
				mCircularProgressTranslateXOfStart = mLoadingCanvasRadius;
				mCircularProgressTranslateYOfStart = (float) getHeight() / 2 - mLoadingCanvasRadius;
			} else {
				mCircularProgressTranslateXOfStart = position2Translate(mLoadingStartPosition.x);
				mCircularProgressTranslateYOfStart = position2Translate(mLoadingStartPosition.y);
			}

			if(mLoadingEndPosition == null) {
				mCircularProgressTranslateXOfEnd = (float) getWidth() - mLoadingCanvasRadius * 2 - mLoadingCanvasRadius;
				mCircularProgressTranslateYOfEnd = (float) getHeight() / 2 - mLoadingCanvasRadius;
			} else {
				mCircularProgressTranslateXOfEnd = position2Translate(mLoadingEndPosition.x);
				mCircularProgressTranslateYOfEnd = position2Translate(mLoadingEndPosition.y);
			}
		}
		mLoadingMatrixOfStart.setTranslate(mCircularProgressTranslateXOfStart, mCircularProgressTranslateYOfStart);
		mLoadingMatrixOfEnd.setTranslate(mCircularProgressTranslateXOfEnd, mCircularProgressTranslateYOfEnd);
	}

	private float position2Translate(float coordinate) {
		return coordinate - mLoadingCanvasRadius;
	}

	/**
	 * 制作圆形Loading背景
	 */
	private void drawBitmap() {
		mLoadingBitmap = Bitmap.createBitmap(mLoadingCanvasRadius * 2, mLoadingCanvasRadius * 2, Bitmap.Config.ARGB_8888);
		mLoadingCanvas = new Canvas(mLoadingBitmap);
		mLoadingCanvas.drawCircle(mLoadingCanvasRadius, mLoadingCanvasRadius, mLoadingRadius, mLoadingPaint);
	}

	public int getLoadingBackgroundColor() {
		return mCircleColor;
	}

	public void setLoadingBackgroundColor(int color) {
		mCircleColor = color;
		mLoadingPaint.setColor(mCircleColor);
		if(mLoadingBitmap != null) {
			drawBitmap();
		}
	}

	public int getLoadingArcColor() {
		return mCircularProgressColor;
	}

	public void setLoadingArcColor(int color) {
		mCircularProgressColor = color;
		mCircularProgressPaint.setColor(mCircularProgressColor);
	}

	public float getLoadingRadius() {
		return mLoadingRadius;
	}

	public void setLoadingRadius(float radius) {
		mLoadingRadius = radius;
		measureLoadingView();
		drawBitmap();
	}

	public void setLoadingPosition(int direction, float x, float y) {
		if (direction == Direction.START) {
			mLoadingStartPosition = new Position(x, y);
		} else if (direction == Direction.END) {
			mLoadingEndPosition = new Position(x, y);
		}
	}

	/**
	 * Loading圆弧动画
	 */
	class CircularProgressAnimator {

		private ValueAnimator mLoadingAnimator;
		private float mProgress;
		private float mStartAngleOfAnimStart;
		private float mStartAngle;
		private float mEndAngle;
		private float mSweepAngle;
		private float mRotateAngle = 0;

		private int mDuration = 1332;

		CircularProgressAnimator() {
			init();
		}

		private void init() {
			mLoadingAnimator = ValueAnimator.ofFloat(0f, 1f);
			mLoadingAnimator.setInterpolator(new LinearInterpolator());
			mLoadingAnimator.setRepeatCount(ValueAnimator.INFINITE);
			mLoadingAnimator.setRepeatMode(ValueAnimator.RESTART);
			mLoadingAnimator.addListener(new Animator.AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
					mStartAngle = mStartAngleOfAnimStart;
				}

				@Override
				public void onAnimationEnd(Animator animation) {

				}

				@Override
				public void onAnimationCancel(Animator animation) {
					mStartAngleOfAnimStart = 0;
					mRotateAngle = 0;
				}

				@Override
				public void onAnimationRepeat(Animator animation) {
					mStartAngleOfAnimStart = mStartAngle % 360;
				}
			});
			mLoadingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator valueAnimator) {
					mProgress = (float) valueAnimator.getAnimatedValue();
					if (mProgress < 0.5) {
						if (mProgress < 0.05) {
							mEndAngle = mStartAngleOfAnimStart + 0.1f * 360;
						} else if (mProgress > 0.45) {
							mEndAngle = mStartAngleOfAnimStart + 0.9f * 360;
						} else {
							mStartAngle = mStartAngleOfAnimStart;
							mEndAngle = mStartAngle + mProgress * 2 * 360;
						}
					} else {
						if (mProgress < 0.9) {
							mStartAngle = mStartAngleOfAnimStart + (mProgress - 0.5f) * 2 * 360;
						}
					}
					mSweepAngle = mEndAngle >= mStartAngle ? mEndAngle - mStartAngle : mEndAngle + 360 - mStartAngle;
					mRotateAngle = mRotateAngle + 5;
					invalidate();
				}
			});
		}

		void start() {
			mLoadingAnimator.setDuration(mDuration);
			mLoadingAnimator.start();
		}

		void cancel() {
			mLoadingAnimator.cancel();
			invalidate();
		}

		float getStartAngle() {
			return mStartAngle;
		}

		float getSweepAngle() {
			return mSweepAngle;
		}

		float getRotateAngle() {
			return mRotateAngle;
		}

		boolean isRunning() {
			return mLoadingAnimator.isRunning();
		}

		void setDuration(int duration) {
			mDuration = duration;
		}

	}

	private CircularProgressAnimator mLoadingAnimatorOfStart;

	private CircularProgressAnimator mLoadingAnimatorOfEnd;

	private void initAnimator() {
		mLoadingAnimatorOfStart = new CircularProgressAnimator();
		mLoadingAnimatorOfEnd = new CircularProgressAnimator();
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		measureLoadingView();
		drawBitmap();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		if (mLoadingAnimatorOfStart.isRunning()) {
			canvas.drawBitmap(mLoadingBitmap, mLoadingMatrixOfStart, null);
			canvas.save();
			canvas.translate(mCircularProgressTranslateXOfStart, mCircularProgressTranslateYOfStart);
			canvas.drawArc(mCircularProgressRectF
					, mLoadingAnimatorOfStart.getRotateAngle() + mLoadingAnimatorOfStart.getStartAngle()
					, mLoadingAnimatorOfStart.getSweepAngle()
					, false, mCircularProgressPaint);
			canvas.restore();
		}
		if (mLoadingAnimatorOfEnd.isRunning()) {
			canvas.drawBitmap(mLoadingBitmap, mLoadingMatrixOfEnd, null);
			canvas.save();
			canvas.translate(mCircularProgressTranslateXOfEnd, mCircularProgressTranslateYOfEnd);
			canvas.drawArc(mCircularProgressRectF
					, mLoadingAnimatorOfEnd.getRotateAngle() + mLoadingAnimatorOfEnd.getStartAngle()
					, mLoadingAnimatorOfEnd.getSweepAngle()
					, false, mCircularProgressPaint);
			canvas.restore();
		}
	}
}
