package com.example.librapp.ui.books;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librapp.BookModel;
import com.example.librapp.FirebaseDatabaseHelper;
import com.example.librapp.R;
import com.example.librapp.RecyclerViewConfig;
import com.example.librapp.RentBookModel;
import com.example.librapp.UserModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment {

    private BooksViewModel booksViewModel;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private List<BookModel> userBookList = new ArrayList<>();
    private List<String> borrowedKeys = new ArrayList<>();


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
        mAuth = FirebaseAuth.getInstance();
        progressBar = root.findViewById(R.id.loading_books_pb);
        mRecyclerView = root.findViewById(R.id.recyclerViewBooks);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.divider));
        mRecyclerView.addItemDecoration(itemDecorator);


        new FirebaseDatabaseHelper().searchUserBooks(new FirebaseDatabaseHelper.dataStatus() {
            @Override
            public void DataIsLoaded(List<BookModel> bookModels, List<String> keys) {

            }

            @Override
            public void DataIsLoaded(final List<RentBookModel> rentBookModels) {
                //Toast.makeText(getContext(), " " + rentBookModels.size(),Toast.LENGTH_LONG).show();
                new FirebaseDatabaseHelper().loadBooks(new FirebaseDatabaseHelper.dataStatus() {
                    @Override
                    public void DataIsLoaded(List<BookModel> bookModels, List<String> keys) {
                        for(int i = 0; i < rentBookModels.size(); i++){
                            for(int j = 0; j < bookModels.size(); j++){
                                if(rentBookModels.get(i).getBookId().equals(bookModels.get(j).getIsbn())){
                                    userBookList.add(bookModels.get(j));
                                    borrowedKeys.add(keys.get(j));
                                }
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                        new RecyclerViewConfig().setConfig(mRecyclerView, getActivity(), userBookList, borrowedKeys, rentBookModels);
                    }

                    @Override
                    public void DataIsLoaded(List<RentBookModel> rentBookModels) {

                    }

                    @Override
                    public void DataIsEmpty() {

                    }
                });
                //progressBar.setVisibility(View.GONE);
                //new RecyclerViewConfig().setConfig(mRecyclerView, getActivity(), bookModels, keys);
            }

            @Override
            public void DataIsEmpty() {

            }

        }, mAuth.getCurrentUser().getUid());



        return root;
    }
}
