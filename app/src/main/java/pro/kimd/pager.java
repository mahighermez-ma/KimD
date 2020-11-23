package pro.kimd;

import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class pager extends FragmentStatePagerAdapter {

    int tabCount;

    public pager(FragmentManager fm , int tabCount)
    {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:

                return new CallFragment();
            case 1:

                return new ContactFragment();
            case 2:

                return new ExeptionFragment();
//            case 3:
//
//                return new SearchFragment();
//            case 4:
//
//                return new FavoriteFragment();
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return "Calls history";
            case 1:
                return "Contact";
            case 2:
                return "Exeption";
//            case 3:
//                return "Search number";


            default:
                return null;
        }
    }
}
