package com.example.groceryapp;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class LoginSignupViewPagerAdapter  extends FragmentStateAdapter {
    public LoginSignupViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1){
            return new SignupTabFragment();
        }
        return new FragmentLoginTab();
    }
    @Override
    public int getItemCount() {
        return 2;
    }
}
