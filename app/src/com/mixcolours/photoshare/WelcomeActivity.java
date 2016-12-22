package com.mixcolours.photoshare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.mixcolours.photoshare.custom.ui.CameraEntryActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;


public class WelcomeActivity extends Activity {
	// 翻页控件
	private ViewPager mViewPager;
	// 滑动状态
	private boolean misScrolled = false;

    private PageModel[] pages = new PageModel[]{
        new PageModel(true,false,R.drawable.welcome_1),
        new PageModel(true,false,R.drawable.welcome_2),
        new PageModel(false,true,R.drawable.welcome_3)};

	private ImageView[] circleImages;

	private void initCircls(Context context) {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.circle_linear);
		LinearLayout.LayoutParams layoutParamsImageMain = new LinearLayout.LayoutParams(15, 15);
		layoutParamsImageMain.setMargins(15, 0, 0, 0);
		circleImages = new ImageView[pages.length];

		for (int i = 0; i < pages.length; i++) {
			circleImages[i] = new ImageView(context);
			circleImages[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			circleImages[i].setScaleType(ScaleType.FIT_CENTER);

			if (i == 0) {
				circleImages[i].setImageDrawable(getResources().getDrawable(R.drawable.welcome_page_now));
			} else {
				circleImages[i].setImageDrawable(getResources().getDrawable(R.drawable.welcome_page));
			}

			linearLayout.addView(circleImages[i], layoutParamsImageMain);
		}

	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		int lastAppVersion = preferences.getInt("lastAppVersion",-1);
		int nowAppVersion = CommonUtil.getVersionCode(this);

		if(lastAppVersion == nowAppVersion){
			//当前版本,直接跳过
			gotoMainPage();
            return;
		}else{
			setContentView(R.layout.activity_welcome);
		}

		mViewPager = (ViewPager) findViewById(R.id.welcome_viewpager);
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		mViewPager.setAdapter(new WelcomePageAdapter(pages));

		initCircls(this);

	}
	
	public class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			switch (state) {
			case ViewPager.SCROLL_STATE_DRAGGING:
				misScrolled = false;
				break;
			case ViewPager.SCROLL_STATE_SETTLING:
				misScrolled = true;
				break;
			case ViewPager.SCROLL_STATE_IDLE:
				if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !misScrolled) {
					gotoMainPage();
					misScrolled = true;
					break;
				}
			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int page) {

			for (int i = 0; i < pages.length; i++) {
				if (i == page) {
					circleImages[i].setImageDrawable(getResources().getDrawable(R.drawable.welcome_page_now));
				} else {
					circleImages[i].setImageDrawable(getResources().getDrawable(R.drawable.welcome_page));
				}
			}

		}

	}
	
	private class WelcomeActivityClickListener  implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			gotoMainPage();
			misScrolled = true;
		}
		
	}

	private void gotoMainPage(){
//        startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
		WelcomeActivity.this.finish();
//		String nowAppVersion = CommonUtil.getVersion(this)+"."+CommonUtil.getVersionCode(this);
//		CrmAppContext.getInstance().updateLastAppVersion(nowAppVersion);

		Intent i = new Intent(this,CameraEntryActivity.class);
		i.putExtra("wantFullScreen",true);
		startActivity(i);

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt("lastAppVersion",CommonUtil.getVersionCode(this));
		editor.commit();
	}


    class PageModel{
        public boolean showCancel;
        public boolean endPage;
        public int resource;

        public PageModel(boolean showCancel, boolean endPage,  int resource){
            this.showCancel = showCancel;
            this.endPage = endPage;
            this.resource = resource;

        }
    }

    class WelcomePageAdapter extends PagerAdapter {

        private PageModel[] pages;

        ImageLoader imageLoader;
        public WelcomePageAdapter(PageModel[] pages) {
            super();
            this.pages = pages;
            imageLoader = ImageLoader.getInstance();
        }

        @Override
        public int getCount() {
            return this.pages.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        //页面view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.item_welcome_page, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.welcome_img);
//            Button btn_come = (Button) view.findViewById(R.id.welcome_comein_btn);
//            ImageView btn_cancel = (ImageView) view.findViewById(R.id.welcome_close_btn);
//
//            btn_cancel.setOnClickListener(new WelcomeActivityClickListener());
//            btn_come.setOnClickListener(new WelcomeActivityClickListener());

            PageModel model = pages[position];
            imageLoader.displayImage(ImageDownloader.Scheme.DRAWABLE.wrap("" + model.resource), imageView);
//            btn_cancel.setVisibility(model.showCancel ? View.VISIBLE : View.GONE);
//            btn_come.setVisibility(model.endPage?View.VISIBLE:View.GONE);
            container.addView(view, 0);
            return view;
        }

    }


}
