package com.example.librapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librapp.ui.scanner.ScannerFragment;

import java.util.List;


public class RecyclerViewConfig extends FragmentActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter bookAdapter;
    private View.OnClickListener onItemClickListener;
    private List<Book> secondlist;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void setConfig(RecyclerView recyclerView, Context context, List<Book> books, List<String> keys){
        mContext = context;
        bookAdapter = new BookAdapter(books, keys);
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.setAdapter(bookAdapter);
    }

    class BookItemView extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mIsbn;
        private TextView mCategory;
        private String key;

        public BookItemView(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_textView);
            mAuthor = itemView.findViewById(R.id.author_textView);
            mCategory = itemView.findViewById(R.id.category_textView);
            mIsbn = itemView.findViewById(R.id.isbn_textView);
            itemView.setOnClickListener(onItemClickListener);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("clicked", "position"+getAdapterPosition());
                    Toast.makeText(mContext, "clicked " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                    Book book = secondlist.get(getAdapterPosition());
                    Intent intent = new Intent (v.getContext(), BookDetails.class);
                    intent.putExtra("Book", book);
                    v.getContext().startActivity(intent);


                }
            });
        }



        public void bind (Book book, String key){
            mTitle.setText(book.getTitle());
            mAuthor.setText(book.getAuthor());
            mCategory.setText(book.getCategory());
            mIsbn.setText(book.getIsbn());
            this.key = key;
        }



    }



    class BookAdapter extends RecyclerView.Adapter<BookItemView> {
        private List<Book> mBookList;
        private List<String> mKeys;
        //private final OnClickListener mOnClickListener = new MyOnClickListener();

        public BookAdapter(List<Book> mBookList , List<String> mKeys)
        {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
            secondlist = mBookList;
        }

        @NonNull
        @Override
        public BookItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BookItemView view = new BookItemView(LayoutInflater.from(parent.getContext()).inflate(R.layout.booklist_item, parent, false));
                    return view;
        }

        @Override
        public void onBindViewHolder(@NonNull BookItemView holder, final int position) {
            holder.bind(mBookList.get(position), mKeys.get(position));
            //TODO clicklistener
            /*holder.setItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Book book = mBookList.get(position);
                    Intent intent = new Intent (v.getContext(), BookDetails.class);
                    intent.putExtra("Book", book);
                    v.getContext().startActivity(intent);
                }
            });*/

        /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("clicked", "position"+getAdapterPosition());
                    Toast.makeText(mContext, "clicked " + getAdapterPosition(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent (v.getContext(), BookDetails.class);
                    v.getContext().startActivity(intent);


                }
            });*/

        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }

        public Book getBook(int position){
            return mBookList.get(position);
        }


    }




}



/*public class RecyclerViewConfig extends FragmentActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private View.OnClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        recyclerView = findViewById(R.id.recyclerView);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

    }
    class BookItemView extends RecyclerView.ViewHolder{

        private TextView mTitle;
        private TextView mAuthor;
        private TextView mIsbn;
        private TextView mCategory;
        private String key;


        public BookItemView(@NonNull View inflatedView) {
            super(inflatedView);
            //inflatedView.setTag(this);??
            inflatedView.setOnClickListener(onItemClickListener);

            mTitle = inflatedView.findViewById(R.id.title_textView);
            mAuthor = inflatedView.findViewById(R.id.author_textView);
            mCategory = inflatedView.findViewById(R.id.category_textView);
            mIsbn = inflatedView.findViewById(R.id.isbn_textView);
        }
        public void bind (Book book, String key){
            mTitle.setText(book.getTitle());
            mAuthor.setText(book.getAuthor());
            mCategory.setText(book.getCategory());
            mIsbn.setText(book.getIsbn());
            this.key = key;
        }

        public void setItemClickListener(View.OnClickListener clickListener)
        {
            onItemClickListener = clickListener;
        }


    }

    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{

        private String[] mDataset;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView;
            final BooksAdapter booksAdapter;
            public MyViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public BooksAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_text_view, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.setText(mDataset[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}*/
