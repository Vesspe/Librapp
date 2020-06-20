package com.example.librapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RecyclerViewConfig extends FragmentActivity {
    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter bookAdapter;
    private View.OnClickListener onItemClickListener;
    private List<RentBookModel> rentBookModelsList = new ArrayList<>();


    //might remove later
    private List<BookModel> secondlist;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
    //default constructor.
    public void setConfig(RecyclerView recyclerView, Context context,
                          List<BookModel> bookModels, List<String> keys){
        mContext = context;
        bookAdapter = new BookAdapter(bookModels, keys);
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.setAdapter(bookAdapter);
    }


    //constructor to pass borrow date
    public void setConfig(RecyclerView recyclerView, Context context,
                          List<BookModel> bookModels, List<String> keys,
                          List<RentBookModel> rentBookModels ){
        mContext = context;

        bookAdapter = new BookAdapter(bookModels, keys);
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerView.setAdapter(bookAdapter);
        this.rentBookModelsList = rentBookModels;
    }

    class BookItemView extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mIsbn;
        private TextView mCategory;
        private ImageView mImage;
        private String key;

        public BookItemView(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_textView);
            mAuthor = itemView.findViewById(R.id.author_textView);
            mCategory = itemView.findViewById(R.id.category_textView);
            mIsbn = itemView.findViewById(R.id.isbn_textView);
            mImage = itemView.findViewById(R.id.book_imageView);
            itemView.setOnClickListener(onItemClickListener);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("clicked", "position "+getAdapterPosition());
                    BookModel bookModel = secondlist.get(getAdapterPosition());

                    if(rentBookModelsList.isEmpty()){
                        Intent intent = new Intent (v.getContext(), BookDetailsActivity.class);
                        intent.putExtra("Book", bookModel);
                        v.getContext().startActivity(intent);
                    }else
                    {
                        Intent intent = new Intent (v.getContext(), RentedBookDetailsActivity.class);
                        intent.putExtra("Book", bookModel );
                    }

                }
            });
        }



        public void bind (BookModel bookModel, String key, int position){
            mTitle.setText(bookModel.getTitle());
            mAuthor.setText(bookModel.getAuthor());
            mCategory.setText(bookModel.getCategory());
            if(!rentBookModelsList.isEmpty())
            {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = rentBookModelsList.get(position).getDatetime();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 14);
                String formatedDate = formatter.format(c.getTime());

                mIsbn.setText("Due to: " + formatedDate);
            }else {
                mIsbn.setText(bookModel.getIsbn());
            }
            //check for null in image field
            if(mImage != null){
                Glide.with(itemView)
                        .load(bookModel.getImage())
                        .into(mImage);
            }else {
                Glide.with(itemView)
                        .load("https://www.tutorialspoint.com/images/tp-logo-diamond.png")
                        .into(mImage);
            }
            this.key = key;

        }



    }



    class BookAdapter extends RecyclerView.Adapter<BookItemView> {
        private List<BookModel> mBookModelList;
        private List<String> mKeys;

        public BookAdapter(List<BookModel> mBookModelList, List<String> mKeys)
        {
            this.mBookModelList = mBookModelList;
            this.mKeys = mKeys;
            secondlist = mBookModelList;
        }

        @NonNull
        @Override
        public BookItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookItemView(LayoutInflater.from(parent.getContext()).inflate(R.layout.booklist_item, parent, false));

        }

        @Override
        public void onBindViewHolder(@NonNull BookItemView holder, final int position) {
            holder.bind(mBookModelList.get(position), mKeys.get(position), position);

        }


        @Override
        public int getItemCount() {
            return mBookModelList.size();
        }

        public BookModel getBook(int position){
            return mBookModelList.get(position);
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
