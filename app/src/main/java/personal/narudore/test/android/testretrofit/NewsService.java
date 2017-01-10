package personal.narudore.test.android.testretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Ray on 10/17/2016.
 */

interface NewsService
{
    @GET("api/news/list/{page}")
    Call<List<News>> getNewsList (@Path("page") int page, @Query("sort") String sort);

    @GET("api/news/{newsId}")
    Call<News> getNewsDetail (@Path("newsId") int newsId);

    @FormUrlEncoded
    @POST("api/news/post")
    Call<ServiceResponse<Void>> postNews (@Field("title") String title, @Field("content") String content);
}
