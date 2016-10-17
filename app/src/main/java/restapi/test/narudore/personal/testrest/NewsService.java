package restapi.test.narudore.personal.testrest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<News> getNews (@Path("newsId") int newsId);
}
