package com.example.test;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private String[] navItems = { "Brown", "Cadet Blue", "Dark Olive Green", "Dark Orange", "Golden Rod" };
	private ListView lvNavList;
	private FrameLayout flContainer;
	private DrawerLayout dlDrawer;
	private ActionBarDrawerToggle dtToggle;

	private ViewPager mPager;
	// mPager.setCurrentItem(0);
	// 요런 메소드가 있다. 호출하면 해당 위치로 이동

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvNavList = (ListView) findViewById(R.id.lv_activity_main_nav_list);
		flContainer = (FrameLayout) findViewById(R.id.fl_activity_main_container);
		dlDrawer = (DrawerLayout) findViewById(R.id.dl_activity_main_drawer);

		// 사용자가 현재 drawer list 중에 어디를 보고 있는지 알려주는 액션바 토글
		dtToggle = new ActionBarDrawerToggle(this, dlDrawer, R.drawable.ic_drawer, R.string.open_drawer_message,
				R.string.close_drawer_message) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		// public ActionBarDrawerToggle(Activity activity, DrawerLayout layout, int drawerImageRes, int
		// openDrawerContentDescRes, int closeDrawerContentDescRes)에 대한 설명:
		// activity : 드로어를 포함하는 액티비티
		// drawerLayout : 액티비티의 액션바와 연동할 드로어
		// drawerImageRes : 드로어의 상태 표시에 사용할 이미지 (리소스)
		// openDrawerContentDescRes : '드로어 열기' 에 해당하는 문자열 리소스 (접근성 지원용)
		// closeDrawerContentDescRes : '드로어 닫기'에 해당하는 문자열 리소스 (접근성 지원용)

		dlDrawer.setDrawerListener(dtToggle); // 드로어와 액션바 토글 연결
		getActionBar().setDisplayHomeAsUpEnabled(true);

		lvNavList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
		lvNavList.setOnItemClickListener(new DrawerItemClickListener());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
	}

	// 액션바 옵션 드랍다운 관련
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	// //////////////////////////////////////
	// /// 드로어 리스트 내 아이템 클릭 리스너
	// //////////////////////////////////////

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
			switch (position) {
			case 0:
				flContainer.setBackgroundColor(Color.parseColor("#A52A2A"));
				break;
			case 1:
				flContainer.setBackgroundColor(Color.parseColor("#5F9EA0"));
				break;
			case 2:
				flContainer.setBackgroundColor(Color.parseColor("#556B2F"));
				break;
			case 3:
				flContainer.setBackgroundColor(Color.parseColor("#FF8C00"));
				break;
			case 4:
				flContainer.setBackgroundColor(Color.parseColor("#DAA520"));
				break;
			}
			dlDrawer.closeDrawer(lvNavList);
		}
	}

	// //////////////////////////////////////
	// /// 액션바 토글과 드로어 상태 동기화
	// //////////////////////////////////////

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		dtToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		dtToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (dtToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// //////////////////////////////////////
	// /// 페이지 어댑터 내부 클래스 구현
	// //////////////////////////////////////

	private class PagerAdapterClass extends PagerAdapter {

		private LayoutInflater mInflater;

		public PagerAdapterClass(Context c) {
			super();
			mInflater = LayoutInflater.from(c);
		}

		// 페이지 inflater에 의한 페이지 어댑터 초기화
		@Override
		public Object instantiateItem(View pager, int position) {
			View v = null;
			if (position == 0) {
				v = mInflater.inflate(R.layout.inflate_one, null);
				// 여기서 각각의 페이지 뷰에 있는 뷰 객체들 초기화
			} else if (position == 1) {
				v = mInflater.inflate(R.layout.inflate_two, null);
			} else {
				v = mInflater.inflate(R.layout.inflate_three, null);
			}

			((ViewPager) pager).addView(v, 0);
			return v;
		}

		@Override
		public void destroyItem(View pager, int position, Object view) {
			((ViewPager) pager).removeView((View) view);
		}

		@Override
		public boolean isViewFromObject(View pager, Object obj) {
			return pager == obj;
		}

		// 페이지 뷰 개수 알려주기
		@Override
		public int getCount() {
			return 3;
		}
	}
}