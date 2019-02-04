package net.icraftsystems.app.reader.comic.comicreader;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DividerItemDecoration;
import 	android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import net.icraftsystems.app.reader.comic.comicreader.Adapter.MyChapterAdapter;
import net.icraftsystems.app.reader.comic.comicreader.common.Common;
import net.icraftsystems.app.reader.comic.comicreader.model.Chapter;
import net.icraftsystems.app.reader.comic.comicreader.retrofit.IComicAPI;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChapterActivity extends AppCompatActivity {


    IComicAPI iComicAPI;
    RecyclerView recycler_chapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView txt_chapter;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chapter);

        //init API
        iComicAPI = Common.getAPI();

        //view
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(Common.selected_comic.getName());

        //set icon for the toobar
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //
            }
        });
        recycler_chapter = (RecyclerView) findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(layoutManager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));

        txt_chapter = (TextView)findViewById(R.id.txt_chapter);


        fetchChapter(Common.selected_comic.getID());
    }

    private void fetchChapter(int comicid) {
        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please Wait...").setCancelable(false).build();
        dialog.show();

        compositeDisposable.add(iComicAPI.getChapterList(comicid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Chapter>>() {
                    @Override
                    public void accept(List<Chapter> chapters) throws Exception {
                        Common.chapterList = chapters; // Save the chapter to back, next.
                        recycler_chapter.setAdapter(new MyChapterAdapter(getBaseContext(), chapters));
                        txt_chapter.setText(new StringBuilder("CHAPTER (").append(chapters.size()).append(")"));
                        dialog.dismiss();
                    }
                }, new Consumer<Throwable>(){


                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //
                        Toast.makeText(ChapterActivity.this, "Error loading chapter", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }));
    }
}
