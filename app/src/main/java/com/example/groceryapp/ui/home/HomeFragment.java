package com.example.groceryapp.ui.home;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.groceryapp.Model;
import com.example.groceryapp.R;
import com.example.groceryapp.ViewPagerAdapterCard;
import com.example.groceryapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ViewPager viewpager;

    // Creating Object of ViewPagerAdapter

    ViewPagerAdapterCard adapter;
    List<Model> models;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        models = new ArrayList<>();
        models.add(new Model(R.drawable.ca1, "YOUR KITCHEN NEEDS", "Brochure is an informative paper document "));
        models.add(new Model(R.drawable.ca3, "YOUR KITCHEN NEEDS", "Sticker is a type of label: a piece of printed paper"));
        models.add(new Model(R.drawable.ca2, "YOUR KITCHEN NEEDS", "Poster is any piece of printed paper"));

        adapter = new ViewPagerAdapterCard(models, getContext());

        viewpager = root.findViewById(R.id.viewpager);
        viewpager.setAdapter(adapter);
        viewpager.setPadding(70, 0, 120, 0);



        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
return root;
    }
}