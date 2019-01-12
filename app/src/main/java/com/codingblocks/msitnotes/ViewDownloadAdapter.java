package com.codingblocks.msitnotes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.dift.ui.SwipeToAction;

public class ViewDownloadAdapter extends RecyclerView.Adapter<ViewDownloadAdapter.ViewDownloadViewHolder> {
    private Context context;
    private DBHelper dbHelper;
    public ViewDownloadAdapter(Context context) {
        this.context=context;
        dbHelper=new DBHelper(context);
    }

    @Override
    public ViewDownloadAdapter.ViewDownloadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=li.inflate(R.layout.item_row_downloads,parent,false);
        return new ViewDownloadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewDownloadAdapter.ViewDownloadViewHolder holder, final int position) {
        final Download download=getItem(position);
        holder.name.setText(download.getName());
        holder.size.setText(download.getLength());
        String formattedDate=formatDate(download.getDate(),"LLL dd/ yyyy hh:mm a");
        holder.date.setText(formattedDate);

        holder.cvRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Path", "onClick: "+download.getPath());
                //Log.d("uri", "onClick: "+path);
                File pdfPath=new File(Environment.getExternalStorageDirectory(),"MSITNotes");
                File file=new File(pdfPath,download.getName()+".pdf");
                Uri pdfUri= FileProvider.getUriForFile(context,
                        "com.codingblocks.fileprovider",
                        file);
                Log.d("pdf", "onClick: "+FileProvider.getUriForFile(context,
                        "com.codingblocks.fileprovider",
                        file));
               // Log.d("fileprovider", "onClick: "+pdfUri);
                context.grantUriPermission("com.codingblocks.msitnotes",pdfUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(pdfUri,"application/pdf");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Intent intent1=Intent.createChooser(intent,"Open File");
                context.startActivity(intent1);
            }
        });

        holder.cvRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                File file=new File(download.getPath());
                file.delete();
                Toast.makeText(context, getItem(position).getName(), Toast.LENGTH_SHORT).show();
                dbHelper.removeItem(getItem(position).getId());
                notifyItemRemoved(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbHelper.getCount();
    }

    public class ViewDownloadViewHolder extends RecyclerView.ViewHolder {
        TextView name,size,date;
        CardView cvRow;
        public ViewDownloadViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textViewName);
            size=itemView.findViewById(R.id.textViewSize);
            date=itemView.findViewById(R.id.textViewDate);
            cvRow=itemView.findViewById(R.id.cvRow);
        }
    }

    public Download getItem(int position){
        return dbHelper.getItemAt(position);
    }

    private String formatDate(Long milliseconds,String dateFormat){
        SimpleDateFormat formatter=new SimpleDateFormat(dateFormat);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return formatter.format(calendar.getTime());
    }
}
