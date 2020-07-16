package com.example.librapp.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private SearchViewModel searchViewModel;
    private EditText editTextSearch;
    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private View root;
    private DrawerLayout drawerLayout;
    private Spinner spinner;
    private List<BookModel> filteredList = new ArrayList<>();
    private List<String> filteredKeys = new ArrayList<>();
    private String TAG = "SearchFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                new ViewModelProvider(this).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        progressBar = root.findViewById(R.id.loading_books_pb);

        //spinner with categories
        spinner = root.findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(root.getContext(), R.array.categories_array, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //hide progressbar
        progressBar.setVisibility(View.GONE);
        final TextView textView = root.findViewById(R.id.textView_search_info);
        editTextSearch = root.findViewById(R.id.editText_search_book);
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.divider));

        mRecyclerView.addItemDecoration(itemDecorator);
        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        Button search = root.findViewById(R.id.button_search_book);
        search.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        //notification attempt
        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/
        switch (v.getId()) {
            case R.id.button_search_book:

                //clearing the lists
                filteredKeys.clear();
                filteredList.clear();
                String query = editTextSearch.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                new FirebaseDatabaseHelper().searchBook(new FirebaseDatabaseHelper.dataStatus() {
                    @Override
                    public void DataIsLoaded(List<BookModel> bookModels, List<String> keys) {
                        //hide progress bar after query finished
                        progressBar.setVisibility(View.GONE);
                        //checking value of selected category in spinner
                        String spinnerValue = spinner.getSelectedItem().toString();
                        //if queried book matches selected category or user choose "all", add it to filtered list
                        for(int i=0; i<bookModels.size();i++){
                            if(bookModels.get(i).getCategory().equals(spinnerValue) || spinnerValue.equals("All")){
                                filteredList.add(bookModels.get(i));
                                filteredKeys.add(keys.get(i));
                            }
                        }
                        // new recyclerview with filtered list of books
                        new RecyclerViewConfig().setConfig(mRecyclerView, getActivity(), filteredList, filteredKeys);
                        Log.i(TAG, "Data is loaded");
                    }

                    @Override
                    public void DataIsLoaded(List<RentBookModel> rentBookModels) {

                    }

                    @Override
                    public void DataIsEmpty() {
                        progressBar.setVisibility(View.GONE);
                        Log.w(TAG, "Data is empty");
                    }
                }, query);
                InputMethodManager inputMethodManager = (InputMethodManager) root.getContext().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(root.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                break;
            case R.id.recyclerView:


                break;


        }
    }



}
