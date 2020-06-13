package com.example.librapp;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    private UserModel getuser;

    public interface dataStatus{
        void DataIsLoaded(List<BookModel> bookModels, List<String> keys);
        void DataIsLoaded(List<RentBookModel> rentBookModels);
        void DataIsEmpty();
        void UserFound(UserModel getuser);
    }

    public FirebaseDatabaseHelper() {
        this.mDatabase = FirebaseDatabase.getInstance();
        //this.mReference = mDatabase.getReference("Books");
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


        /*mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookModels.clear();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    RentBookModel rentBookModel = keyNode.getValue(RentBookModel.class);
                    if(rentBookModel.getUid().equals(uid)){

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    //get user data (id, email and name)
    public void getUser(final dataStatus dataStatus, final String id){
        //mReference = mDatabase.getReference("Users");
        FirebaseDatabase.getInstance().getReference("Users")
                .equalTo(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserModel user = dataSnapshot.getValue(UserModel.class);
                        dataStatus.UserFound(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





        /*mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){

                    UserModel user = keyNode.getValue(UserModel.class);
                    if(user.getUid().equals(id)){
                        getuser = new UserModel(user.getUid(), user.getEmail(), user.getName(), user.getUserBorrowedBooks());
                    }
                    //dataStatus.UserFound(getuser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}
