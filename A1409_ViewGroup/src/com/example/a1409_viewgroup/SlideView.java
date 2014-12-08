package com.example.a1409_viewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlideView extends ViewGroup {

	private int mMostRecentX;
	private Scroller mscroller;// 用来模拟数据,不能用来切换界面
	private final int SCREEN_MAIN = 0;// 主界面
	private final int SCREEN_MENU = 1;// 菜单界面
	private int currentScreen = SCREEN_MAIN;// 当前界面默认为主界面

	public SlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mscroller = new Scroller(context);
	}

	// 当自定义的ViewGroup测量宽高时触发此方法
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {// 父控件的宽高
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 测量当前ViewGroup中所有子布局的宽高
		View menu = getChildAt(0);// 在ViewGroup中有两个子布局一个menu，一个main_content
									// 按先后顺序，索引分别为0 1
		// 测量menu的宽高，宽是布局中指定死了的240dip 高度是填充屏幕
		menu.measure(menu.getLayoutParams().width, heightMeasureSpec);// 宽度：取布局文件中的值

		View main = getChildAt(1);// 获得主界面布局
		main.measure(widthMeasureSpec, heightMeasureSpec);// 宽高指定父控件的宽高
		
		Log.i("自定义", "自定义控件的onMeasure（）---------------------------》》》》》》》》》》》》》》》》》");
	}

	// 测量完毕后，开始布局时调用该方法
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// layout()方法限定控件的四边
		View menu = getChildAt(0);
		menu.layout(-menu.getMeasuredWidth(), t, 0, b);

		View main = getChildAt(1);
		main.layout(l, t, r, b);// 指定主界面的上下左右的边界和父控件的边界一样
		
		
		Log.i("自定义", "自定义控件的onLayout（）---------------------------》》》》》》》》》》》》》》》》》");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		 * scrollTo(x,y) 移动到指定的xy scrollBy(x,y) 移动xy
		 */
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			/*
			 * event.getX(): 是手指按点到屏幕（不是控件，是屏幕）的距离 getScrollX():
			 * 是以main界面的左顶点为0,0 屏幕左顶点到main界面左顶点的x方向距离（这里只考虑x方向）
			 * 如果屏幕左顶点在main界面左顶点左边，那么getScrollX()为负值，反之为正值
			 */
			mMostRecentX = (int) event.getX();// 把x轴偏移量记录下来，这里是手指按下的点的初始位置
			System.out.println("mMostRecentX:::" + mMostRecentX);
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();// 移动后，点到左屏幕的距离
			System.out.println("moveX::::" + moveX);
			// 计算增量,这里的增量是屏幕左顶点与main左顶点之间的增量，往右拖动页面，那么屏幕左顶点距离main左顶点越来越远，-1 -2
			// -3 -4.。。。。。。
			int deltaX = mMostRecentX - moveX;// 点移动前，与点移动后之间的偏移量；注意：为什么不是moveX-mMostRecentX?因为moveX-mMostRecentX是main左边缘相对屏幕左边缘的偏移量，而我们的deltaX是屏幕左边缘相对main左边缘的偏移量，值正好与前者相反，也就是-（moveX-mMostRecentX）
			// newScrollX是移动后屏幕左边缘与main左边缘的之间的偏移量，屏幕左边缘与main左边缘重合，则为0 ;
			// 屏幕左边缘在main左边缘的右边，值>0;屏幕左边缘在main左边缘，值<0
			int newScrollX = getScrollX() + deltaX;// getScrollX()：点移动前的屏幕左顶点到main界面左顶点的水平距离，deltaX:点移动后的偏移量，二者之和是点移动后，屏幕左顶点到main界面左顶点的水平距离
			System.out.println("getScrollX():::" + getScrollX());
			// 判断移动后是否已经超过了边界,屏幕左边缘在main左边缘的右边了
			if (newScrollX > 0) {
				// 当前X轴已经超过了0，主界面向左移动了，让其一直停留在0的位置
				scrollTo(0, 0);// 该方法的作用是将屏幕左边缘与main左边缘重合
			} else if (newScrollX < -getChildAt(0).getWidth()) {// 屏幕左边缘在menu左边缘的左边了
				scrollTo(-getChildAt(0).getWidth(), 0);// 该方法作用是将屏幕左边缘移动至menu左边缘
			}
			scrollBy(deltaX, 0);// 该方法作用是，让屏幕能实时移动，移动的距离是偏移量，deltaX为负值，屏幕就左移，deltaX为正值则屏幕右移。
			mMostRecentX = moveX;// 让该次移动后的x距离，作为下次移动前的x距离，这样就能保证在移动过程中时时在偏移
			break;
		case MotionEvent.ACTION_UP:
			// 当拖动时，若屏幕左边缘处于menu宽度的左半块时，一松手，那么屏幕左边缘移动至menu左边缘，也就是完全显示menu
			// 当拖动时，若屏幕左边缘处于menu宽度的右半块时，一松手，那么屏幕左边缘移动至main左边缘，也就是完全显示main
			int scrollX = getScrollX();
			int dx = 0;
			if (scrollX > -getChildAt(0).getWidth() / 2) {
				// 当前屏幕的X轴偏移量的值大于-120，切换到主界面
				// scrollTo(0,0);该方法是瞬间移动，太快
				/*
				 * startX：开始模拟的位置：-80 dx:根据开始位置模拟数据的增量值 0 - （-80）。。目的地x-起点x
				 * duration：持续时间 mscroller.startScroll(startX, startY, dx, dy,
				 * duration)
				 */
				// dx = 0-scrollX;
				currentScreen = SCREEN_MAIN;
			} else {
				// 当前屏幕的X轴偏移量的值小于-120，切换到菜单
				// scrollTo(-getChildAt(0).getWidth(),0);
				// dx = -getChildAt(0).getWidth()-scrollX;
				currentScreen = SCREEN_MENU;
			}
			// mscroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx)*10);
			// //流程：drawChild---child.draw---computeScroll
			// invalidate();//重绘，ViewGroup中没有onDrawn()方法，但是有画子控件方法
			switchScreen();
			break;

		default:
			break;
		}
		return true;
	}

	// 根据currentScreen变量控制当前屏幕的显示
	private void switchScreen() {
		int scrollx = getScrollX();
		int dx = 0;
		if (currentScreen == SCREEN_MAIN) {
			dx = 0 - scrollx;
		} else if (currentScreen == SCREEN_MENU) {
			dx = -getChildAt(0).getWidth() - scrollx;
		}
		/*
		 * 采用Scroller模拟数据是因为，该方法可以让屏幕持续移动，用户体验效果好，而scrollTo方法是瞬间移动，用户体验效果不好
		 * 
		 * 参数一：x方向起始位置 参数二：y方向起始位置 参数三：目标x与起始x之间的偏移量，目标x-起始x 起始就是移动多少偏移量
		 * 参数四：目标y与起始y之间的偏移量，目标y-起始y 起始就是移动多少偏移量 参数五：设定在多长时间内完成移动过程
		 */
		mscroller.startScroll(scrollx, 0, dx, 0, Math.abs(dx) * 5);
		// 流程: drawChild -> child.draw -> computeScroll
		invalidate();// 该方法调用drawChild() drawChild()调用child.draw()
						// child.draw()调用computeScroll()
						// 因此我们重写computeScroll()方法就行
	}

	// 方法请求XY轴的偏移量，做移动的动画
	@Override
	public void computeScroll() {
		// 把Scroller模拟的值取出来，通过scrollTo移动屏幕
		if (mscroller.computeScrollOffset()) {// 判断当前模拟是否正在模拟数据中，true模拟
												// false停止了模拟
			/*
			 * 在mscroller.startScroll(scrollx, 0, dx, 0,Math.abs(dx)*5);模拟数据时，在这里就定义好了一个屏幕左边缘移动至目标的路径。还有走完该路径所需要的时间
			 * 比如从-80移动至0 ，那么移动路径就是：-80 -79 -78 -77.。。。-3 -2 -1 0
			 * mscroller.getCurrX()作用是：按照路径顺序取出当前方法执行时对应的路径所在的坐标。 
			 * 比如：路径 -80 -79  -78 -77.。。。-3 -2 -1 0 为方便理解，这里我们规定用80秒走完，
			 * 那么： 时间：1   2    3   4 。。。    77 78 79 80 
			 * 
			 * 需要注意的是：上面的mscroller.startScroll(scrollx, 0, dx, 0,
			 * Math.abs(dx)*5)只是模拟，相当于做好了计划 方法执行：
			 * 
			 * 假如mscroller.getCurrX()执行时的时间是2，那么取值就是-79 
			 * 下次mscroller.getCurrX()执行时的时间是4 ，那么取值就是-77
			 * 下次mscroller.getCurrX()执行时的时间是78 ，那么取值就是-2
			 * 下次mscroller.getCurrX()执行时的时间是80 ，那么取值就是-0
			 * 
			 * 概括的说就是：在模拟数据的过程中，invalidate()方法会调用computeScroll()，
			 * 在computeScroll()中会调用mscroller.getCurrX()方法，获取mscroller.getCurrX()方法执行时模拟到了哪个路径点，
			 * mscroller.getCurrX()就是得到这个路径点，然后scrollTo(mscroller.getCurrX(),0)方法将屏幕左边缘移动至该路径点
			 * ，后面又invalidate()方法，又调用mscroller.getCurrX()，因为方法执行过程中会消耗时间
			 * ，此时这里的mscroller.getCurrX()取值就变化了
			 * 如此一直循环，直到模拟数据结束，模拟数据结束时，其实也就移动至目标了
			 */
			scrollTo(mscroller.getCurrX(), 0);// 每执行一次都会移动至一个新的位置，直到不再模拟数据
			System.out.println("mscroller.getCurrX()::::"
					+ mscroller.getCurrX());
			invalidate();// 重复移动，没有该行，那么只调用一次scrollTo();该方法作用是重新调用computeScroll()方法
		}
	}

	/*
	 * 事件的传递： dispatchTouchEvent() ：分发事件，传递给OnInterceptTouchEvent()
	 * OnInterceptTouchEvent()方法返回true：表示我要使用事件，然后把事件传递给其所在View的onTouchEvent()方法
	 * OnInterceptTouchEvent()方法返回false：表示我不需要使用事件，然后把事件传递给其所在View的子布局的dispatchTouchEvent()方法
	 * 
	 * 当View有子布局时，OnInterceptTouchEvent()默认是false
	 * 
	 * 在这里我们需要左右拖动滑动，因此需要拦截事件，那么我们在左右滑动达到一定条件，就要将事件拦截，也就是return
	 * true，触发当前的onTouchEvent() 没有达到条件 return
	 * false,就将事件继续向子布局传递，触发事件listview的上下滑动
	 */
	// 用于拦截左右滑动的事件
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mMostRecentX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getX();
			int diff = moveX - mMostRecentX;
			// 当按下和移动的x轴的间距超过了一定的距离，我们认为是左右滑动
			if (Math.abs(diff) > 20) {
				return true;// true表示自己消费，传递给自己的OnTouchEvent（），如果没有触发，那么事件依旧向子布局传递，也就是listview的上下滑动
			}
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	//判断当前menu是否正在显示
	public boolean isShowMenu() {
		// TODO Auto-generated method stub
		return currentScreen == SCREEN_MENU;
	}

	// 隐藏menu
	public void hideMenu() {
		currentScreen = SCREEN_MAIN;
		switchScreen();

	}

	// 显示menu
	public void showMenu() {
		currentScreen = SCREEN_MENU;
		switchScreen();

	}
}
