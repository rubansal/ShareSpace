package com.codingblocks.msitnotes;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewRecentAdapter extends RecyclerView.Adapter<ViewRecentAdapter.ViewRecentViewHolder> {

    ArrayList<Upload> uploads = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;

    DownloadManager downloadManager;
    ArrayList<Long> refids = new ArrayList<>();

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

    private DBHelper dbHelper;

    public ViewRecentAdapter(ArrayList<Upload> uploads, RecyclerView recyclerView, Context context) {
        this.uploads = uploads;
        this.recyclerView = recyclerView;
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @Override
    public ViewRecentAdapter.ViewRecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.item_row, parent, false);
        return new ViewRecentViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewRecentAdapter.ViewRecentViewHolder holder, int position) {
        final Upload upload = uploads.get(position);
        holder.name.setText(upload.getName());
        holder.size.setText(upload.getSize());
        holder.firstLetter.setText(upload.getFirstLetter());
        GradientDrawable nameCircle = (GradientDrawable) holder.firstLetter.getBackground();
        int nameColor = getNameColor(upload.getFirstLetter());
        nameCircle.setColor(nameColor);

        String formattedDate = formatDate(Long.parseLong(upload.getTimeInMillisecond()), "LLL dd/ yyyy hh:mm a");
        holder.date.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ViewRecentViewHolder extends RecyclerView.ViewHolder {
        TextView name, size, firstLetter, date;
        public ViewRecentViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            size = itemView.findViewById(R.id.textViewSize);
            firstLetter = itemView.findViewById(R.id.textViewfirstLetter);
            date = itemView.findViewById(R.id.textViewDate);
        }
    }

    private String formatDate(Long milliseconds, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }

    private int getNameColor(String firstLetter) {
        int nameColorResourceId = R.color.colorAccent;
        String colorfirstLetter = firstLetter;
        switch (colorfirstLetter) {
            case "A":
                nameColorResourceId = R.color.A;
                break;
            case "B":
                nameColorResourceId = R.color.B;
                break;
            case "C":
                nameColorResourceId = R.color.C;
                break;
            case "D":
                nameColorResourceId = R.color.D;
                break;
            case "E":
                nameColorResourceId = R.color.E;
                break;
            case "F":
                nameColorResourceId = R.color.F;
                break;
            case "G":
                nameColorResourceId = R.color.G;
                break;
            case "H":
                nameColorResourceId = R.color.H;
                break;
            case "I":
                nameColorResourceId = R.color.I;
                break;
            case "J":
                nameColorResourceId = R.color.J;
                break;
            case "K":
                nameColorResourceId = R.color.K;
                break;
            case "L":
                nameColorResourceId = R.color.L;
                break;
            case "M":
                nameColorResourceId = R.color.M;
                break;
            case "N":
                nameColorResourceId = R.color.N;
                break;
            case "O":
                nameColorResourceId = R.color.O;
                break;
            case "P":
                nameColorResourceId = R.color.P;
                break;
            case "Q":
                nameColorResourceId = R.color.Q;
                break;
            case "R":
                nameColorResourceId = R.color.R;
                break;
            case "S":
                nameColorResourceId = R.color.S;
                break;
            case "T":
                nameColorResourceId = R.color.T;
                break;
            case "U":
                nameColorResourceId = R.color.U;
                break;
            case "V":
                nameColorResourceId = R.color.V;
                break;
            case "W":
                nameColorResourceId = R.color.W;
                break;
            case "X":
                nameColorResourceId = R.color.X;
                break;
            case "Y":
                nameColorResourceId = R.color.Y;
                break;
            case "Z":
                nameColorResourceId = R.color.Z;
                break;
        }

        return ContextCompat.getColor(context, nameColorResourceId);
    }
}
