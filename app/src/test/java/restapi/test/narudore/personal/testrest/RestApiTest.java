package restapi.test.narudore.personal.testrest;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RestApiTest
{
    private static NewsService newsService;

    @BeforeClass
    public static void setUp()
    {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost/testci3/")
            .addConverterFactory(GsonConverterFactory.create())
            //.client(httpClient.build())
            .build();

        newsService = retrofit.create(NewsService.class);
    }

    @Test
    public void testGetNewsListShouldNotEmpty()
    {
        try
        {
            Call<List<News>> call = newsService.getNewsList(1, "asc");
            Response<List<News>> response = call.execute();
            assertTrue(response.body().size() > 0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNewsDetailShouldNotEmpty()
    {
        try
        {
            Call<News> call = newsService.getNews(1);
            Response<News> response = call.execute();
            assertTrue(response.body().getTitle().length() > 0);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetWrongNewsDetailShouldEmpty()
    {
        try
        {
            Call<News> call = newsService.getNews(999);
            Response<News> response = call.execute();
            assertNull(response.body());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}