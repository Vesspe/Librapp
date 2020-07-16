package com.example.librapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth mAuth;
    private NavigationView navigationView;
    private BookModel bookModel;
    private String CHANNEL_ID = "0";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String TAG = "BookDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bookModel = (BookModel) intent.getSerializableExtra("Book");
        TextView bookDesc = findViewById(R.id.textView_book_desc);
        bookDesc.setText(bookModel.getDesc());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ImageView imageView = findViewById(R.id.imageView_book_details);

        createNotificationChannel();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_search, R.id.nav_books, R.id.nav_settings, R.id.nav_scanner)
                .setDrawerLayout(drawer)
                .build();


        Glide.with(this)
                .load(bookModel.getImage())
                .override(512,512)
                .into(imageView);





    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String desc = getString(R.string.channel_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void sendNotification(){


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentTitle("Success")
                .setContentText("Borrowed " + bookModel.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Borrowed " + bookModel.getTitle()))
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int notificationId = 1;
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        Log.i(TAG, "Notification sent");
        finish();
    }

    public void goBack(View v){
        finish();
    }


    public void borrowBook(View v) {
        //getting database references

        mAuth = FirebaseAuth.getInstance();
        final String userId = mAuth.getCurrentUser().getUid();
        final String bookId = bookModel.getIsbn();
        new FirebaseDatabaseHelper().searchUserBooks(new FirebaseDatabaseHelper.dataStatus() {
            @Override
            public void DataIsLoaded(List<BookModel> bookModels, List<String> keys) {

            }

            @Override
            public void DataIsLoaded(List<RentBookModel> rentBookModels) {
                for(int i = 0; i<rentBookModels.size(); i++){
                    if(rentBookModels.get(i).getBookId().equals(bookId))
                    {
                        toast();
                        return;
                    }
                }
                DatabaseReference myRef = database.getReference("ReservedBooks").push();
                Date currentTime = Calendar.getInstance().getTime();
                RentBookModel rentBook = new RentBookModel(bookId, currentTime, userId);
                myRef.setValue(rentBook);
                sendNotification();

                Log.i(TAG, "Data is loaded");

            }

            @Override
            public void DataIsEmpty() {
                Log.w(TAG, "Data is empty");
            }
        }, userId);

    }

    private void toast(){
        Toast.makeText(this, "You already have that book", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Book already borrowed");
    }
}