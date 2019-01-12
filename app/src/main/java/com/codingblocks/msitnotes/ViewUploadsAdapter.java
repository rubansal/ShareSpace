package com.codingblocks.msitnotes;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.security.AccessController.getContext;

//and this is my recycler view adapter so sir now see how can i apply web view

public class ViewUploadsAdapter extends RecyclerView.Adapter<ViewUploadsAdapter.ViewUploadViewHolder> {

    ArrayList<Upload> uploads = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;
    String brse;

    DownloadManager downloadManager;
    ArrayList<Long> refids = new ArrayList<>();

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

    private DBHelper dbHelper;

    public ViewUploadsAdapter(ArrayList<Upload> uploads, RecyclerView recyclerView, Context context, String brse) {
        this.uploads = uploads;
        this.recyclerView = recyclerView;
        this.context = context;
        this.brse = brse;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewUploadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.item_row, parent, false);
        return new ViewUploadViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewUploadViewHolder holder, int position) {
        final Upload upload = uploads.get(position);
        holder.name.setText(upload.getName());
        holder.size.setText(upload.getSize());
        holder.firstLetter.setText(upload.getFirstLetter());
        GradientDrawable nameCircle = (GradientDrawable) holder.firstLetter.getBackground();
        int nameColor = getNameColor(upload.getFirstLetter());
        nameCircle.setColor(nameColor);

        String formattedDate = formatDate(Long.parseLong(upload.getTimeInMillisecond()), "LLL dd/ yyyy hh:mm a");
        holder.date.setText(formattedDate);

//        holder.cvRow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,WebViewActivity.class);
//                intent.putExtra("url",upload.getUrl());
//                context.startActivity(intent);
//            }
//        });

        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        holder.cvRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File folder = new File(Environment.getExternalStorageDirectory() + "/MSITNotes");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MSITNotes/" + upload.getName() + ".pdf";
                Log.d("Check", "onClick: ok");
                Uri Download_Uri = Uri.parse(upload.getUrl());
                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setTitle("Downloading " + upload.getName() + ".pdf");
                request.setDescription("Downloading " + "Sample" + ".pdf");
                request.setVisibleInDownloadsUi(true);
                request.setDestinationInExternalPublicDir("/MSITNotes", upload.getName() + ".pdf");
                File file=new File(Environment.getExternalStorageDirectory()+"/MSITNotes",upload.getName()+".pdf");
                Log.d("Pathe", "onClick: "+file.toString());

                final Long refid = downloadManager.enqueue(request);

                refids.add(refid);


               BroadcastReceiver onComplete = new BroadcastReceiver() {
                    @Override
                     public void onReceive(Context context, Intent intent) {
                        long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                        refids.remove(referenceId);
                        if (refids.isEmpty()) {
                            Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();
                            dbHelper.addDownloads(upload.getName(), filePath, upload.getSize());
                        }
                    }
                };

                context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
        });

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Comment.class);
                i.putExtra("comments", upload.getId());
                context.startActivity(i);
            }
        });

//         holder.cvRow.setOnLongClickListener(new View.OnLongClickListener() {
//             @Override
//             public boolean onLongClick(View v) {
//                 Vibrator vibrator= (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                 if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//                     vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
//                 }
//                 else {
//                     vibrator.vibrate(100);
//                 }
//
//                 StorageReference sRef=storageReference.child(brse+"/"+upload.getName()+".pdf");
//                 sRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                     @Override
//                     public void onSuccess(Void aVoid) {
//                         Toast.makeText(context, "file deleted", Toast.LENGTH_SHORT).show();
//                     }
//                 });
//                 return false;
//             }
//        holder.cvRow.setOnClickListener(new View.OnClickListener(
////         });) {
//            @Override
//            public void onClick(View v) {
//                CustomTabsIntent customTabsIntent=new CustomTabsIntent.Builder().build();
//                customTabsIntent.launchUrl(context,Uri.parse(upload.getUrl()));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public class ViewUploadViewHolder extends RecyclerView.ViewHolder {

        TextView name, size, firstLetter, date, comment;
        CardView cvRow;

        public ViewUploadViewHolder(final View itemView) {
            super(itemView);
            cvRow = itemView.findViewById(R.id.cvRow);
            name = itemView.findViewById(R.id.textViewName);
            size = itemView.findViewById(R.id.textViewSize);
            firstLetter = itemView.findViewById(R.id.textViewfirstLetter);
            date = itemView.findViewById(R.id.textViewDate);
            comment = itemView.findViewById(R.id.textViewComment);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position=recyclerView.getChildLayoutPosition(view);
//                    Upload upload=uploads.get(position);
//                    Intent i=new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(upload.getUrl()));
//                    context.startActivity(i);
//                }
//            });


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
