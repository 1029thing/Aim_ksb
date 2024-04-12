package com.example.myapplication.ui.club;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentClubsBinding;

public class ClubsFragment extends Fragment {

    private FragmentClubsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ClubsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ClubsViewModel.class);

        binding = FragmentClubsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textClubs;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}