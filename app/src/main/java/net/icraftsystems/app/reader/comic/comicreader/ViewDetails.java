package net.icraftsystems.app.reader.comic.comicreader;

import android.app.AlertDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import net.icraftsystems.app.reader.comic.comicreader.Adapter.MyViewPagerAdapter;
import net.icraftsystems.app.reader.comic.comicreader.common.Common;
import net.icraftsystems.app.reader.comic.comicreader.model.Link;
import net.icraftsystems.app.reader.comic.comicreader.retrofit.IComicAPI;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ViewDetails extends AppCompatActivity {

    IComicAPI iComicAPI;
    ViewPager myViewPager;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    TextView txt_chapter_name;
    View back,next;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);


        iComicAPI = Common.getAPI();
        myViewPager = (ViewPager)findViewById(R.id.view_pager);
        txt_chapter_name = (TextView)findViewById(R.id.txt_chapter_name);
        back = findViewById(R.id.chapter_back);
        next = findViewById(R.id.chapter_next);

        back.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                if (Common.chapter_index == 0) //if the user is on the first chapter but presses bsck
                {
                    Toast.makeText(ViewDetails.this, "You are reading the first Chapter", Toast.LENGTH_SHORT).show();

                }else {
                    Common.chapter_index--;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());

                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.chapter_index == Common.chapterList.size() - 1) //if the user is on the first chapter but presses bsck
                {
                    Toast.makeText(ViewDetails.this, "You are reading the last Chapter", Toast.LENGTH_SHORT).show();

                }else {
                    Common.chapter_index++;
                    fetchLinks(Common.chapterList.get(Common.chapter_index).getID());

                }
            }
        });

        fetchLinks(Common.selected_chapter.getID());
    }

    private void fetchLinks(int id) {

        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please Wait...").setCancelable(false).build();
        dialog.show();

        compositeDisposable.add(iComicAPI.getImageList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Link>>() {
                    @Override
                    public void accept(List<Link> links) throws Exception {
                        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getBaseContext(), links);
                        myViewPager.setAdapter(adapter);

                        txt_chapter_name.setText(Common.formatString(Common.selected_chapter.getName()));

                        // Create a book flipPager
                        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                        bookFlipPageTransformer.setScaleAmountPercent(10f);
                        myViewPager.setPageTransformer(true, bookFlipPageTransformer);

                        dialog.dismiss();

                    }
                }, new Consumer<Throwable>(){


                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(ViewDetails.this, "This Chapter is being translated", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }));
    }
}
