package com.example.myapplication.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private  Dialog dialog;
    private EditText editText;
    private Button saveButton, closeButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CalendarView calendarView = root.findViewById(R.id.calendar); //캘린더뷰 
        
        //45line~77line 캘린더뷰 날짜 클릭(선택) 이벤트
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                dialog = new Dialog(requireContext());
                dialog.setContentView(R.layout.popup_layout);
                dialog.setCancelable(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.CENTER);

                editText = dialog.findViewById(R.id.editText);
                saveButton = dialog.findViewById(R.id.saveButton);
                closeButton = dialog.findViewById(R.id.closeButton);

                //59line~74line 모달창내 저장버튼 닫기 버튼 눌렀을 시 이벤트
                saveButton.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) { 
                              String text = editText.getText().toString();
                              saveTextToFile(text);
                              dialog.dismiss();
                          }
                      });
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return root;
    }


    //81line~95line 모달창 내 저장버튼 눌렀을 시 텍스트 파일 저장하는 이벤트
    // 현재 텍스트 파일이 기기 내부 스토리지 (프로젝트 파일 경로x)에 저장되어 파일 저장 경로 확인 필요함.
            private void saveTextToFile(String text) {
                try {
                    File file = new File(requireContext().getExternalFilesDir(null), "saved_text.txt");
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(text);
                    writer.close();

                    String filePath = file.getAbsolutePath();
                    Toast.makeText(requireContext(), filePath +"에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

// 텍스트 파일 저장된 경로 확인하면 날짜 클릭 시 경로 확인 후 저장된 텍스트 파일이 있을 시 해당 텍스트 파일 출력, 저장버튼 -> 수정으로 텍스트 변경 구현 예정
// 경로 확인을 위해 저장버튼 클릭 시 현재 기기 내부 스토리지 경로를 토스트메세지로 출력하게 함.