package net.icraftsystems.app.reader.comic.comicreader.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.gson.Gson;

import net.icraftsystems.app.reader.comic.comicreader.R;
import net.icraftsystems.app.reader.comic.comicreader.model.Category;

import java.util.ArrayList;
import java.util.List;

public class MultipleChooseAdapter extends RecyclerView.Adapter<MultipleChooseAdapter.MyViewHolder>{



    Context context;
    List<Category> categoryList;
    SparseBooleanArray itemStateArray = new SparseBooleanArray();
    List<Category> selected_category = new ArrayList<>();

    public MultipleChooseAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.check_item,viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.ckb_options.setText(categoryList.get(i).getName());
        myViewHolder.ckb_options.setChecked(itemStateArray.get(i));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public String getFilterArray() {

        List<Integer> id_selected = new ArrayList<>();
        for (Category category:selected_category)
            id_selected.add(category.getID());
        return new Gson().toJson(id_selected);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox ckb_options;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ckb_options = (CheckBox) itemView.findViewById(R.id.check_options);
            ckb_options.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int adapterPosition = getAdapterPosition();

                    itemStateArray.put(adapterPosition,isChecked);
                    if(isChecked)
                        selected_category.add(categoryList.get(adapterPosition));
                    else
                        selected_category.remove(categoryList.get(adapterPosition));
                }
            });
        }
    }
}
