package com.example.myapplication.ui.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.Activity.ChallengeApply;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentChallengeBinding;

public class ChallengeFragment extends Fragment {

    private FragmentChallengeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ChallengeViewModel dashboardViewModel =
                new ViewModelProvider(this).get(ChallengeViewModel.class);

        binding = FragmentChallengeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.firstCh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), ChallengeApply.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}