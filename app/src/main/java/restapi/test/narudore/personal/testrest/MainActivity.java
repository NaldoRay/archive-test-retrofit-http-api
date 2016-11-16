package restapi.test.narudore.personal.testrest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NewsAdapter.OnViewClickListener
{
    private static final int REQUEST_POST_NEWS = 1213;

    private NewsService newsService;
    private RecyclerView.Adapter newsAdapter;
    private FloatingActionButton postButton;

    private static int page;
    private List<News> newsList;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postButton = (FloatingActionButton) findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(MainActivity.this, PostNewsActivity.class);
                startActivityForResult(intent, REQUEST_POST_NEWS);
            }
        });


        RecyclerView listView = (RecyclerView) findViewById(R.id.newsList);
        listView.setLayoutManager(new LinearLayoutManager(this));

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList, this);
        listView.setAdapter(newsAdapter);
        listView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled (RecyclerView recyclerView, int dx, int dy)
            {
                if (!recyclerView.canScrollVertically(1))
                {
                    Toast.makeText(MainActivity.this, "Mentok", Toast.LENGTH_SHORT).show();

                    if (page > 0)
                        loadNews();
                }
            }
        });

        MyApp app = (MyApp) getApplication();
        newsService = app.createService(NewsService.class);

        page = 1;
        loadNews();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_POST_NEWS && resultCode == RESULT_OK)
        {
            page = 1;
            loadNews();
        }
    }

    private void loadNews ()
    {
        newsService.getNewsList(page, "desc").enqueue(new Callback<List<News>>()
        {
            @Override
            public void onResponse (Call<List<News>> call, Response<List<News>> response)
            {
                List<News> list = response.body();

                if (list.isEmpty())
                    page = 0;
                else
                {
                    if (page == 1)
                        newsList.clear();
                    page++;

                    newsList.addAll(list);
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure (Call<List<News>> call, Throwable t)
            {
                Log.e("testrestapi", "failure", t);
            }
        });
    }

    @Override
    public void onViewDetail (int position)
    {
        News news = newsList.get(position);
        Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.ARG_NEWS_ID, news.getId());
        startActivity(intent);
    }

    @Override
    public void onShare (int position)
    {
        Toast.makeText(this, "Share news #" + position, Toast.LENGTH_SHORT).show();
    }
}
