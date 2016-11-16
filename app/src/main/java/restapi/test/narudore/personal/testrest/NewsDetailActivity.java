package restapi.test.narudore.personal.testrest;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsDetailActivity extends AppCompatActivity
{
    public static final String ARG_NEWS_ID = "news_id";

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart ()
    {
        super.onStart();

        Bundle args = getIntent().getExtras();
        int newsId = args.getInt(ARG_NEWS_ID);

        MyApp app = (MyApp) getApplication();
        NewsService newsService = app.createService(NewsService.class);
        newsService.getNewsDetail(newsId).enqueue(new Callback<News>()
        {
            @Override
            public void onResponse (Call<News> call, Response<News> response)
            {
                News news = response.body();
                TextView textView;

                textView = (TextView) findViewById(R.id.titleText);
                textView.setText(news.getTitle());

                textView = (TextView) findViewById(R.id.contentText);
                textView.setText(news.getContent());
            }

            @Override
            public void onFailure (Call<News> call, Throwable t)
            {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
