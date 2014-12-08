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
	private Scroller mscroller;// ����ģ������,���������л�����
	private final int SCREEN_MAIN = 0;// ������
	private final int SCREEN_MENU = 1;// �˵�����
	private int currentScreen = SCREEN_MAIN;// ��ǰ����Ĭ��Ϊ������

	public SlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mscroller = new Scroller(context);
	}

	// ���Զ����ViewGroup�������ʱ�����˷���
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {// ���ؼ��Ŀ��
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// ������ǰViewGroup�������Ӳ��ֵĿ��
		View menu = getChildAt(0);// ��ViewGroup���������Ӳ���һ��menu��һ��main_content
									// ���Ⱥ�˳�������ֱ�Ϊ0 1
		// ����menu�Ŀ�ߣ����ǲ�����ָ�����˵�240dip �߶��������Ļ
		menu.measure(menu.getLayoutParams().width, heightMeasureSpec);// ��ȣ�ȡ�����ļ��е�ֵ

		View main = getChildAt(1);// ��������沼��
		main.measure(widthMeasureSpec, heightMeasureSpec);// ���ָ�����ؼ��Ŀ��
		
		Log.i("�Զ���", "�Զ���ؼ���onMeasure����---------------------------����������������������������������");
	}

	// ������Ϻ󣬿�ʼ����ʱ���ø÷���
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// layout()�����޶��ؼ����ı�
		View menu = getChildAt(0);
		menu.layout(-menu.getMeasuredWidth(), t, 0, b);

		View main = getChildAt(1);
		main.layout(l, t, r, b);// ָ����������������ҵı߽�͸��ؼ��ı߽�һ��
		
		
		Log.i("�Զ���", "�Զ���ؼ���onLayout����---------------------------����������������������������������");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*
		 * scrollTo(x,y) �ƶ���ָ����xy scrollBy(x,y) �ƶ�xy
		 */
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			/*
			 * event.getX(): ����ָ���㵽��Ļ�����ǿؼ�������Ļ���ľ��� getScrollX():
			 * ����main������󶥵�Ϊ0,0 ��Ļ�󶥵㵽main�����󶥵��x������루����ֻ����x����
			 * �����Ļ�󶥵���main�����󶥵���ߣ���ôgetScrollX()Ϊ��ֵ����֮Ϊ��ֵ
			 */
			mMostRecentX = (int) event.getX();// ��x��ƫ������¼��������������ָ���µĵ�ĳ�ʼλ��
			System.out.println("mMostRecentX:::" + mMostRecentX);
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();// �ƶ��󣬵㵽����Ļ�ľ���
			System.out.println("moveX::::" + moveX);
			// ��������,�������������Ļ�󶥵���main�󶥵�֮��������������϶�ҳ�棬��ô��Ļ�󶥵����main�󶥵�Խ��ԽԶ��-1 -2
			// -3 -4.������������
			int deltaX = mMostRecentX - moveX;// ���ƶ�ǰ������ƶ���֮���ƫ������ע�⣺Ϊʲô����moveX-mMostRecentX?��ΪmoveX-mMostRecentX��main���Ե�����Ļ���Ե��ƫ�����������ǵ�deltaX����Ļ���Ե���main���Ե��ƫ������ֵ������ǰ���෴��Ҳ����-��moveX-mMostRecentX��
			// newScrollX���ƶ�����Ļ���Ե��main���Ե��֮���ƫ��������Ļ���Ե��main���Ե�غϣ���Ϊ0 ;
			// ��Ļ���Ե��main���Ե���ұߣ�ֵ>0;��Ļ���Ե��main���Ե��ֵ<0
			int newScrollX = getScrollX() + deltaX;// getScrollX()�����ƶ�ǰ����Ļ�󶥵㵽main�����󶥵��ˮƽ���룬deltaX:���ƶ����ƫ����������֮���ǵ��ƶ�����Ļ�󶥵㵽main�����󶥵��ˮƽ����
			System.out.println("getScrollX():::" + getScrollX());
			// �ж��ƶ����Ƿ��Ѿ������˱߽�,��Ļ���Ե��main���Ե���ұ���
			if (newScrollX > 0) {
				// ��ǰX���Ѿ�������0�������������ƶ��ˣ�����һֱͣ����0��λ��
				scrollTo(0, 0);// �÷����������ǽ���Ļ���Ե��main���Ե�غ�
			} else if (newScrollX < -getChildAt(0).getWidth()) {// ��Ļ���Ե��menu���Ե�������
				scrollTo(-getChildAt(0).getWidth(), 0);// �÷��������ǽ���Ļ���Ե�ƶ���menu���Ե
			}
			scrollBy(deltaX, 0);// �÷��������ǣ�����Ļ��ʵʱ�ƶ����ƶ��ľ�����ƫ������deltaXΪ��ֵ����Ļ�����ƣ�deltaXΪ��ֵ����Ļ���ơ�
			mMostRecentX = moveX;// �øô��ƶ����x���룬��Ϊ�´��ƶ�ǰ��x���룬�������ܱ�֤���ƶ�������ʱʱ��ƫ��
			break;
		case MotionEvent.ACTION_UP:
			// ���϶�ʱ������Ļ���Ե����menu��ȵ�����ʱ��һ���֣���ô��Ļ���Ե�ƶ���menu���Ե��Ҳ������ȫ��ʾmenu
			// ���϶�ʱ������Ļ���Ե����menu��ȵ��Ұ��ʱ��һ���֣���ô��Ļ���Ե�ƶ���main���Ե��Ҳ������ȫ��ʾmain
			int scrollX = getScrollX();
			int dx = 0;
			if (scrollX > -getChildAt(0).getWidth() / 2) {
				// ��ǰ��Ļ��X��ƫ������ֵ����-120���л���������
				// scrollTo(0,0);�÷�����˲���ƶ���̫��
				/*
				 * startX����ʼģ���λ�ã�-80 dx:���ݿ�ʼλ��ģ�����ݵ�����ֵ 0 - ��-80������Ŀ�ĵ�x-���x
				 * duration������ʱ�� mscroller.startScroll(startX, startY, dx, dy,
				 * duration)
				 */
				// dx = 0-scrollX;
				currentScreen = SCREEN_MAIN;
			} else {
				// ��ǰ��Ļ��X��ƫ������ֵС��-120���л����˵�
				// scrollTo(-getChildAt(0).getWidth(),0);
				// dx = -getChildAt(0).getWidth()-scrollX;
				currentScreen = SCREEN_MENU;
			}
			// mscroller.startScroll(scrollX, 0, dx, 0, Math.abs(dx)*10);
			// //���̣�drawChild---child.draw---computeScroll
			// invalidate();//�ػ棬ViewGroup��û��onDrawn()�����������л��ӿؼ�����
			switchScreen();
			break;

		default:
			break;
		}
		return true;
	}

	// ����currentScreen�������Ƶ�ǰ��Ļ����ʾ
	private void switchScreen() {
		int scrollx = getScrollX();
		int dx = 0;
		if (currentScreen == SCREEN_MAIN) {
			dx = 0 - scrollx;
		} else if (currentScreen == SCREEN_MENU) {
			dx = -getChildAt(0).getWidth() - scrollx;
		}
		/*
		 * ����Scrollerģ����������Ϊ���÷�����������Ļ�����ƶ����û�����Ч���ã���scrollTo������˲���ƶ����û�����Ч������
		 * 
		 * ����һ��x������ʼλ�� ��������y������ʼλ�� ��������Ŀ��x����ʼx֮���ƫ������Ŀ��x-��ʼx ��ʼ�����ƶ�����ƫ����
		 * �����ģ�Ŀ��y����ʼy֮���ƫ������Ŀ��y-��ʼy ��ʼ�����ƶ�����ƫ���� �����壺�趨�ڶ೤ʱ��������ƶ�����
		 */
		mscroller.startScroll(scrollx, 0, dx, 0, Math.abs(dx) * 5);
		// ����: drawChild -> child.draw -> computeScroll
		invalidate();// �÷�������drawChild() drawChild()����child.draw()
						// child.draw()����computeScroll()
						// ���������дcomputeScroll()��������
	}

	// ��������XY���ƫ���������ƶ��Ķ���
	@Override
	public void computeScroll() {
		// ��Scrollerģ���ֵȡ������ͨ��scrollTo�ƶ���Ļ
		if (mscroller.computeScrollOffset()) {// �жϵ�ǰģ���Ƿ�����ģ�������У�trueģ��
												// falseֹͣ��ģ��
			/*
			 * ��mscroller.startScroll(scrollx, 0, dx, 0,Math.abs(dx)*5);ģ������ʱ��������Ͷ������һ����Ļ���Ե�ƶ���Ŀ���·�������������·������Ҫ��ʱ��
			 * �����-80�ƶ���0 ����ô�ƶ�·�����ǣ�-80 -79 -78 -77.������-3 -2 -1 0
			 * mscroller.getCurrX()�����ǣ�����·��˳��ȡ����ǰ����ִ��ʱ��Ӧ��·�����ڵ����ꡣ 
			 * ���磺·�� -80 -79  -78 -77.������-3 -2 -1 0 Ϊ������⣬�������ǹ涨��80�����꣬
			 * ��ô�� ʱ�䣺1   2    3   4 ������    77 78 79 80 
			 * 
			 * ��Ҫע����ǣ������mscroller.startScroll(scrollx, 0, dx, 0,
			 * Math.abs(dx)*5)ֻ��ģ�⣬�൱�������˼ƻ� ����ִ�У�
			 * 
			 * ����mscroller.getCurrX()ִ��ʱ��ʱ����2����ôȡֵ����-79 
			 * �´�mscroller.getCurrX()ִ��ʱ��ʱ����4 ����ôȡֵ����-77
			 * �´�mscroller.getCurrX()ִ��ʱ��ʱ����78 ����ôȡֵ����-2
			 * �´�mscroller.getCurrX()ִ��ʱ��ʱ����80 ����ôȡֵ����-0
			 * 
			 * ������˵���ǣ���ģ�����ݵĹ����У�invalidate()���������computeScroll()��
			 * ��computeScroll()�л����mscroller.getCurrX()��������ȡmscroller.getCurrX()����ִ��ʱģ�⵽���ĸ�·���㣬
			 * mscroller.getCurrX()���ǵõ����·���㣬Ȼ��scrollTo(mscroller.getCurrX(),0)��������Ļ���Ե�ƶ�����·����
			 * ��������invalidate()�������ֵ���mscroller.getCurrX()����Ϊ����ִ�й����л�����ʱ��
			 * ����ʱ�����mscroller.getCurrX()ȡֵ�ͱ仯��
			 * ���һֱѭ����ֱ��ģ�����ݽ�����ģ�����ݽ���ʱ����ʵҲ���ƶ���Ŀ����
			 */
			scrollTo(mscroller.getCurrX(), 0);// ÿִ��һ�ζ����ƶ���һ���µ�λ�ã�ֱ������ģ������
			System.out.println("mscroller.getCurrX()::::"
					+ mscroller.getCurrX());
			invalidate();// �ظ��ƶ���û�и��У���ôֻ����һ��scrollTo();�÷������������µ���computeScroll()����
		}
	}

	/*
	 * �¼��Ĵ��ݣ� dispatchTouchEvent() ���ַ��¼������ݸ�OnInterceptTouchEvent()
	 * OnInterceptTouchEvent()��������true����ʾ��Ҫʹ���¼���Ȼ����¼����ݸ�������View��onTouchEvent()����
	 * OnInterceptTouchEvent()��������false����ʾ�Ҳ���Ҫʹ���¼���Ȼ����¼����ݸ�������View���Ӳ��ֵ�dispatchTouchEvent()����
	 * 
	 * ��View���Ӳ���ʱ��OnInterceptTouchEvent()Ĭ����false
	 * 
	 * ������������Ҫ�����϶������������Ҫ�����¼�����ô���������һ����ﵽһ����������Ҫ���¼����أ�Ҳ����return
	 * true��������ǰ��onTouchEvent() û�дﵽ���� return
	 * false,�ͽ��¼��������Ӳ��ִ��ݣ������¼�listview�����»���
	 */
	// �����������һ������¼�
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mMostRecentX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) ev.getX();
			int diff = moveX - mMostRecentX;
			// �����º��ƶ���x��ļ�೬����һ���ľ��룬������Ϊ�����һ���
			if (Math.abs(diff) > 20) {
				return true;// true��ʾ�Լ����ѣ����ݸ��Լ���OnTouchEvent���������û�д�������ô�¼��������Ӳ��ִ��ݣ�Ҳ����listview�����»���
			}
			break;

		default:
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	//�жϵ�ǰmenu�Ƿ�������ʾ
	public boolean isShowMenu() {
		// TODO Auto-generated method stub
		return currentScreen == SCREEN_MENU;
	}

	// ����menu
	public void hideMenu() {
		currentScreen = SCREEN_MAIN;
		switchScreen();

	}

	// ��ʾmenu
	public void showMenu() {
		currentScreen = SCREEN_MENU;
		switchScreen();

	}
}
