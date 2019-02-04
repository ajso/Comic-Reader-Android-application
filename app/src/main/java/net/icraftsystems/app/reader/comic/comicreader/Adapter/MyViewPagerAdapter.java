package net.icraftsystems.app.reader.comic.comicreader.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import net.icraftsystems.app.reader.comic.comicreader.R;
import net.icraftsystems.app.reader.comic.comicreader.model.Link;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    Context context;
    List<Link> LinkList;
    LayoutInflater inflater;


    public MyViewPagerAdapter(Context context, List<Link> linkList) {
        this.context = context;
        LinkList = linkList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return LinkList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View image_layout = inflater.inflate(R.layout.view_pager_item, container, false);

        PhotoView page_image = (PhotoView) image_layout.findViewById(R.id.pager_image);
        Picasso.get().load(LinkList.get(position).getLink()).into(page_image);


        container.addView(image_layout);

        return image_layout;
    }
}
