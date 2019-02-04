package net.icraftsystems.app.reader.comic.comicreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.icraftsystems.app.reader.comic.comicreader.Interface.IRecyclerOnClick;
import net.icraftsystems.app.reader.comic.comicreader.R;
import net.icraftsystems.app.reader.comic.comicreader.ViewDetails;
import net.icraftsystems.app.reader.comic.comicreader.common.Common;
import net.icraftsystems.app.reader.comic.comicreader.model.Chapter;

import java.util.List;


public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder>{

    Context context;
    List<Chapter> chapterList;


    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chapter_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.txt_chapter_number.setText(new StringBuilder(chapterList.get(i).getName()));
        Common.selected_chapter=chapterList.get(i);

        Common.chapter_index = i;

        //start new activity
        //context.startActivity(new Intent(context, ViewDetails.class));
        myViewHolder.setiRecyclerOnClick(new IRecyclerOnClick() {
            @Override
            public void onClick(View view, int position) {
                Common.selected_chapter= chapterList.get(position);
                Common.chapter_index = position;
                context.startActivity(new Intent(context,  ViewDetails.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_chapter_number;
        IRecyclerOnClick iRecyclerOnClick;

        public void setiRecyclerOnClick(IRecyclerOnClick iRecyclerOnClick) {
            this.iRecyclerOnClick = iRecyclerOnClick;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_chapter_number = (TextView)itemView.findViewById(R.id.txt_chapter_number);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            iRecyclerOnClick.onClick(v, getAdapterPosition());
        }
    }
}
