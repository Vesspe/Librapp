package com.example.librapp.ui.scanner;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librapp.Book;
import com.example.librapp.BookDetails;
import com.example.librapp.FirebaseDatabaseHelper;
import com.example.librapp.MainActivity;
import com.example.librapp.R;
import com.example.librapp.RecyclerViewConfig;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler{

    private ScannerViewModel scannerViewModel;
    private ZXingScannerView scannerView;
    private TextView result;

    public static ScannerFragment newInstance() {
        return new ScannerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        scannerViewModel =
                new ViewModelProvider(this).get(ScannerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scanner, container, false);
        result = root.findViewById(R.id.txt_result);
        scannerView = root.findViewById(R.id.zxscan);


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA},
                    50); }

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(ScannerFragment.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();


        return root;

    }


    @Override
    public void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    public void handleResult(final Result rawResult){
        result.setText(rawResult.getText());
        String query = rawResult.getText();
        new FirebaseDatabaseHelper().searchBook(new FirebaseDatabaseHelper.dataStatus() {
            @Override
            public void DataIsLoaded(List<Book> books, List<String> keys) {
                //progressBar.setVisibility(View.GONE);
                //new RecyclerViewConfig().setConfig(mRecyclerView, getActivity(), books, keys);
                Book book = books.get(0);
                Intent intent = new Intent (getContext(), BookDetails.class);
                intent.putExtra("Book", book);
                getContext().startActivity(intent);

            }
        }, query);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        scannerViewModel = new ViewModelProvider(this).get(ScannerViewModel.class);
        // TODO: Use the ViewModel
    }

}



    /*private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }*/