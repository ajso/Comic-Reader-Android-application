package net.icraftsystems.app.reader.comic.comicreader;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;

import net.icraftsystems.app.reader.comic.comicreader.Adapter.MyComicAdapter;
import net.icraftsystems.app.reader.comic.comicreader.Adapter.MySliderAdapter;
import net.icraftsystems.app.reader.comic.comicreader.common.Common;
import net.icraftsystems.app.reader.comic.comicreader.model.Banner;
import net.icraftsystems.app.reader.comic.comicreader.model.Comic;
import net.icraftsystems.app.reader.comic.comicreader.retrofit.IComicAPI;
import net.icraftsystems.app.reader.comic.comicreader.service.PicassoImageLoadingService;


import java.util.List;

import dmax.dialog.SpotsDialog;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.bannerslider.Slider;


public class MainActivity extends AppCompatActivity {


    Slider slider;
    IComicAPI iComicAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    RecyclerView recycler_comic;
    TextView txt_comic;

    SwipeRefreshLayout swipeRefreshLayout;

    ImageView btn_search;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init API
        iComicAPI = Common.getAPI();

        //search filter
        btn_search = (ImageView)findViewById(R.id.btn_filter);
        btn_search.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CategoryFilter.class));

            }
        });

        //view
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Common.isConnectToInternet(getBaseContext())){
                    fetchBanner();
                    fetchComic();
                }else{

                    Toast.makeText(MainActivity.this,"Not Connected to internet", Toast.LENGTH_LONG).show();
                }

            }
        });

        //defualt load first time

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (Common.isConnectToInternet(getBaseContext())){
                    fetchBanner();
                    fetchComic();
                }else{

                    Toast.makeText(MainActivity.this,"Not Connected to internet", Toast.LENGTH_LONG).show();
                }
            }
        });

        slider = findViewById(R.id.banner_slider);

        Slider.init(new PicassoImageLoadingService());


        recycler_comic = (RecyclerView)findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);


        recycler_comic.setLayoutManager(new GridLayoutManager(this, 2));

        txt_comic = (TextView) findViewById(R.id.txt_comic);

    }

    private void fetchComic() {

        final AlertDialog dialog = new SpotsDialog.Builder().setContext(this).setMessage("Please Wait...").setCancelable(false).build();
        if (!swipeRefreshLayout.isRefreshing())
        dialog.show();

        compositeDisposable.add(iComicAPI.getComicList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comic>>() {
                               @Override
                               public void accept(List<Comic> comics) throws Exception {

                                   recycler_comic.setAdapter(new MyComicAdapter(getBaseContext(), comics));
                                   txt_comic.setText(new StringBuilder("NEW COMIC (").append(comics.size()).append(")"));
                                   if (!swipeRefreshLayout.isRefreshing())
                                   dialog.dismiss();
                                   swipeRefreshLayout.setRefreshing(false);
                               }
                           }
                        , new Consumer<Throwable>(){

                            @Override
                            public void accept(Throwable throwable) throws Exception {


                                if (!swipeRefreshLayout.isRefreshing())
                                    dialog.dismiss();
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(MainActivity.this, "Error loading comics", Toast.LENGTH_SHORT).show();


                    }
                }));
    }

    private void fetchBanner() {
        compositeDisposable.add(iComicAPI.getBannerList()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<List<Banner>>(){

                                public void accept(List<Banner> banners) throws Exception{

                                    slider.setAdapter(new MySliderAdapter(banners));

                                }
                            }, new Consumer<Throwable>(){


                                @Override
                                public void accept(Throwable throwable) {
                                    Toast.makeText(MainActivity.this, "Error loading banner", Toast.LENGTH_SHORT).show();
                                }
                            }));
    }
}
