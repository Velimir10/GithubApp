package adapters;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import fragments.UserFollowersFragment;
import fragments.UserFollowingFragment;
import fragments.UserProfileFragment;
import fragments.UserRepositoryFragment;

public class DetailPageAdapter extends FragmentPagerAdapter {


    public static final String[] fragments = {"Profile", "Repository", "Followers", "Following"};


    public DetailPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {



        switch (position){

            case 0:
                return new UserProfileFragment();
            case 1:
                return new UserRepositoryFragment();
            case 2:
                return new UserFollowersFragment();
            case 3:
                return new UserFollowingFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }


}
