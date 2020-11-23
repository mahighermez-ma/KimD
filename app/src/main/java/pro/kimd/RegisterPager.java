package pro.kimd;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class RegisterPager extends FragmentStatePagerAdapter {

    int tabCount;

    public RegisterPager(FragmentManager fm , int tabCount)
    {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:

                return new RegisFragment();
            case 1:

                return new LoginFragment();
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
                return "Register";
            case 1:
                return "Login";

            default:
                return null;
        }
    }
}
