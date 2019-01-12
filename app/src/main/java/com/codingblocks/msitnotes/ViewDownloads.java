package com.codingblocks.msitnotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import co.dift.ui.SwipeToAction;

public class ViewDownloads extends AppCompatActivity {

    RecyclerView rvDownloads;
    Download download = new Download();
    ArrayList<Download> downloads = download.getArrayListItems();

    SwipeToAction swipeToAction;

    private DBHelper dbHelper;

    ViewDownloadAdapter viewDownloadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_downloads);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Share Space");

        rvDownloads = findViewById(R.id.rvDownloads);
        rvDownloads.setLayoutManager(new LinearLayoutManager(this));
        viewDownloadAdapter = new ViewDownloadAdapter(this);
        rvDownloads.setAdapter(viewDownloadAdapter);

//        swipeToAction=new SwipeToAction(rvDownloads, new SwipeToAction.SwipeListener<Download>() {
//            @Override
//            public boolean swipeLeft(Download itemData) {
//                return false;
//            }
//
//            @Override
//            public boolean swipeRight(Download itemData) {
//                int position=removeDownload(itemData);
//                Toast.makeText(ViewDownloads.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//
//            @Override
//            public void onClick(Download itemData) {
//
//            }
//
//            @Override
//            public void onLongClick(Download itemData) {
//
//            }
//        });
//    }
//
//    private int removeDownload(Download download){
//        int position=downloads.indexOf(download);
//        File file=new File(download.getPath());
//        file.delete();
//        dbHelper.removeItem(download.getId());
//        downloads.remove(download);
//        viewDownloadAdapter.notifyItemRemoved(position);
//        return position;
//    }
    }
}
