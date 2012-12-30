package com.aiti.kabarui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.IconPagerAdapter;

class TestFragmentAdapter extends FragmentStatePagerAdapter implements
		IconPagerAdapter {
	protected static final String[] CONTENT = new String[] { "Home",
			"Acara Kampus", "Beasiswa", "Lomba", "Santai", "Umum",
			"Komunitas Mahasiswa", "Dari Kamu", "Dari Admin", "Opini",
			"Organisasi Mahasiswa", "Pengumuman Kampus", "Snapshot" };
	protected static final int[] ICONS = new int[] {
			R.drawable.perm_group_calendar, R.drawable.perm_group_camera,
			R.drawable.perm_group_device_alarms, R.drawable.perm_group_location };

	private int mCount = CONTENT.length;
	private ViewPager mPager;
	private FragmentManager mFragmentManager;

	public TestFragmentAdapter(FragmentManager fm, ViewPager mPager) {
		super(fm);
		this.mPager = mPager;
		this.mFragmentManager = fm;
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0)
			return new HomeFragment(mPager);
		else
			return new CategoryFragment(position - 1);
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TestFragmentAdapter.CONTENT[position % CONTENT.length];
	}

	@Override
	public int getIconResId(int index) {
		return ICONS[index % ICONS.length];
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}

	public Fragment getActiveFragment(ViewPager container, int position) {
		String name = makeFragmentName(container.getId(), position);
		return mFragmentManager.findFragmentByTag(name);
	}

	private static String makeFragmentName(int viewId, int index) {
		return "android:switcher:" + viewId + ":" + index;
	}
}