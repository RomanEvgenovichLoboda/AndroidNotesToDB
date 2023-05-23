package com.example.androidnotestodb;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.androidnotestodb.databinding.FragmentFirstBinding;

import java.time.LocalDateTime;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
        binding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addNote();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNote() {
        //if(binding.editHeader.getText().toString().equals("")||binding.editNote.getText().toString().equals("")){
        if(binding.editTextHeader.getText().toString().equals("")||binding.editTextNote.getText().toString().equals("")){
            Toast.makeText(getActivity().getApplicationContext(),"Fill in all fields!",Toast.LENGTH_SHORT).show();
        }
        else {
            SQLiteDatabase db = getActivity().getApplicationContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS notes (header TEXT,note TEXT,date TEXT)");
            LocalDateTime dateTime = LocalDateTime.now();
            ContentValues values=new ContentValues();
            values.put("header",binding.editTextHeader.getText().toString());
            values.put("note",binding.editTextNote.getText().toString());
            values.put("date",dateTime.toString());
            db.insert("notes",null,values);
            Toast.makeText(getActivity().getApplicationContext(),"OK!",Toast.LENGTH_SHORT).show();
            db.close();
            binding.editTextHeader.setText("");
            binding.editTextNote.setText("");
        }
    }
}