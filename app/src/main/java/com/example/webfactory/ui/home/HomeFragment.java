package com.example.webfactory.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.webfactory.API.ApiUtilities;
import com.example.webfactory.R;
import com.example.webfactory.adapter.NewsAdapter;
import com.example.webfactory.databinding.FragmentHomeBinding;
import com.example.webfactory.model.News;
import com.example.webfactory.model.mainNews;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    String api = "43e5c4127f0f4a93b87bf1d6cebfc6e7";
    ArrayList<News> newsArrayList;
    NewsAdapter adapter;
    String country = "ru";

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        newsArrayList = new ArrayList<>();
        
        binding.newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), newsArrayList);
        binding.newsRecycler.setAdapter(adapter);

        findNews();

        return root;
    }

    private void findNews() {
        ApiUtilities.getApiInterface().getNews(country, 20, api).enqueue(new Callback<mainNews>() {
            @Override
            public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                if(response.isSuccessful()){
                    newsArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<mainNews> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}