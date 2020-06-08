package com.example.librapp.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librapp.Book;
import com.example.librapp.FirebaseDatabaseHelper;
import com.example.librapp.R;
import com.example.librapp.RecyclerViewConfig;

import java.util.List;

public class BooksFragment extends Fragment {

    private BooksViewModel booksViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booksViewModel =
                new ViewModelProvider(this).get(BooksViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_books, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        booksViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });




        return root;
    }
}
