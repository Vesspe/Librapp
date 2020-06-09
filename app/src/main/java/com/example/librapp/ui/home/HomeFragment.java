package com.example.librapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librapp.Book;
import com.example.librapp.FirebaseDatabaseHelper;
import com.example.librapp.R;
import com.example.librapp.RecyclerViewConfig;
import com.example.librapp.ui.settings.SettingsViewModel;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView =root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.divider));

        mRecyclerView.addItemDecoration(itemDecorator);


        new FirebaseDatabaseHelper().loadBooks(new FirebaseDatabaseHelper.dataStatus() {
            @Override
            public void DataIsLoaded(List<Book> books, List<String> keys) {
                root.findViewById(R.id.loading_books_pb).setVisibility(View.GONE);
                new RecyclerViewConfig().setConfig(mRecyclerView, getActivity(), books, keys);
            }
        });

        return root;
    }


}



/*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/