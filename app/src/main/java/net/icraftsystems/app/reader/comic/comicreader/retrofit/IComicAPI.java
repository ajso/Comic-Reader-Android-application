package net.icraftsystems.app.reader.comic.comicreader.retrofit;


import io.reactivex.Observable;

import net.icraftsystems.app.reader.comic.comicreader.model.Banner;
import net.icraftsystems.app.reader.comic.comicreader.model.Category;
import net.icraftsystems.app.reader.comic.comicreader.model.Chapter;
import net.icraftsystems.app.reader.comic.comicreader.model.Comic;
import net.icraftsystems.app.reader.comic.comicreader.model.Link;


import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IComicAPI {

    @GET("banner")
    Observable<List<Banner>> getBannerList();

    @GET("comic")
    Observable<List<Comic>> getComicList();

    @GET("chapter/(comicid)")
    Observable<List<Chapter>> getChapterList(@Path("comicid")int comicid);

    @GET("links/(chapterid)")
    Observable<List<Link>> getImageList(@Path("chapterid")int chapterid);

    @GET("categories")
    Observable<List<Category>> getCategoryList();

    @POST("filter")
    @FormUrlEncoded
    Observable<List<Comic>> getFilteredcomic(@Field("data") String data);

    @POST("search")
    @FormUrlEncoded
    Observable<List<Comic>> getSearchedComic(@Field("search") String search);

}
