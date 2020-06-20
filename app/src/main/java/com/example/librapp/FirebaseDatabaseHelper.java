package com.example.librapp;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private List<BookModel> bookModels = new ArrayList<>();
    private List<RentBookModel> rentBookModelList = new ArrayList<>();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public interface dataStatus{
        void DataIsLoaded(List<BookModel> bookModels, List<String> keys);
        void DataIsLoaded(List<RentBookModel> rentBookModels);
        void DataIsEmpty();
    }

    public interface userStatus{
        void UserIsFound(UserModel user);

    }

    public FirebaseDatabaseHelper() {
        this.mDatabase = FirebaseDatabase.getInstance();
    }

    public void FindUser(final userStatus userStatus){
        //getting reference to database table, in this case users
        mReference = mDatabase.getReference("Users");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    UserModel userModel = keyNode.getValue(UserModel.class);
                    if(userModel.getUid().equals(mAuth.getCurrentUser().getUid())){
                        userStatus.UserIsFound(userModel);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //loading all books in database
    public void loadBooks(final dataStatus dataStatus){
        mReference = mDatabase.getReference("Books");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookModels.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    BookModel bookModel = keyNode.getValue(BookModel.class);
                    bookModels.add(bookModel);
                }
                dataStatus.DataIsLoaded(bookModels, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //search book by author, category or title provided by param query
    public void searchBook(final dataStatus dataStatus, final String query){
        mReference = mDatabase.getReference("Books");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookModels.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    BookModel bookModel = keyNode.getValue(BookModel.class);
                    if(bookModel.getAuthor().equalsIgnoreCase(query)|| bookModel.getCategory().equalsIgnoreCase(query) || bookModel.getIsbn().equals(query) || bookModel.getTitle().equalsIgnoreCase(query)) {
                        bookModels.add(bookModel);
                    }
                }
                if(bookModels.isEmpty()){
                    dataStatus.DataIsEmpty();
                }else
                dataStatus.DataIsLoaded(bookModels, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //looking for user borrowed books
    public void searchUserBooks(final dataStatus dataStatus, final String uid){

        mReference = mDatabase.getReference("BorrowedBooks");
        //Query userBookList = mReference.child("BorrowedBooks").child(uid);
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookModels.clear();
                rentBookModelList.clear();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    RentBookModel rentBookModel = keyNode.getValue(RentBookModel.class);
                    if(rentBookModel.getUid().equals(uid)){
                        rentBookModelList.add(rentBookModel);
                    }

                }
                dataStatus.DataIsLoaded(rentBookModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}




