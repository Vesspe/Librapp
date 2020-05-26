package com.example.librapp;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<Book> books = new ArrayList<>();

    public interface dataStatus{
        void DataIsLoaded(List<Book> books, List<String> keys);
    }

    public FirebaseDatabaseHelper() {
        this.mDatabase = FirebaseDatabase.getInstance();
        this.mReference = mDatabase.getReference("Books");
    }

    public void loadBooks(final dataStatus dataStatus){
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                books.clear();
                List<String> keys = new ArrayList<>();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
