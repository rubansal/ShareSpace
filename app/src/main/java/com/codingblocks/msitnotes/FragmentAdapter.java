package com.codingblocks.msitnotes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    private String tabTitles[]=new String[]{"Cse","Ece","It","Eee"};
    private Context context;
    private int s;

    /**
     * This method may be called by the ViewPager to obtain a title string
     * to describe the specified page. This method may return null
     * indicating no title for this page. The default implementation returns
     * null.
     *
     * @param position The position of the title requested
     * @return A title for the requested page
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return tabTitles[0];
        else if(position==1)
            return tabTitles[1];
        else if(position==2)
            return tabTitles[2];
        else
            return tabTitles[3];
    }

    public FragmentAdapter(FragmentManager fm,Context context,int s) {
        super(fm);
        this.context=context;
        this.s=s;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        if(position==0) {
            CseFragment cseFragment= new CseFragment();
            return cseFragment.newInstance(s);
        }
        else if(position==1)
            return new EceFragment();
        else if(position==2)
            return new ItFragment();
        else
            return new EeeFragment();
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 4;
    }
}
