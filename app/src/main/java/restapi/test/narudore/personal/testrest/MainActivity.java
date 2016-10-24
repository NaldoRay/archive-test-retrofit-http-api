package restapi.test.narudore.personal.testrest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
{
    private static final int REQUEST_POST_NEWS = 1213;

    private NewsService newsService;
    private ArrayAdapter<News> newsAdapter;
    private FloatingActionButton postButton;

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

        ListView listView = (ListView) findViewById(R.id.newsList);
        newsAdapter = new ArrayAdapter<News>(MainActivity.this, android.R.layout.simple_list_item_1);
        listView.setAdapter(newsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id)
            {
                News news = newsAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.ARG_NEWS_ID, news.getId());
                startActivity(intent);
            }
        });

        MyApp app = (MyApp) getApplication();
        newsService = app.createService(NewsService.class);
    }

    @Override
    protected void onStart ()
    {
        super.onStart();
        refreshNews();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_POST_NEWS && resultCode == RESULT_OK)
        {
            refreshNews();
        }
    }

    private void refreshNews()
    {
        newsService.getNewsList(1, "desc").enqueue(new Callback<List<News>>()
        {
            @Override
            public void onResponse (Call<List<News>> call, Response<List<News>> response)
            {
                List<News> newsList = response.body();

                newsAdapter.clear();
                newsAdapter.addAll(newsList);
                newsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure (Call<List<News>> call, Throwable t)
            {
                Log.e("testrestapi", "failure", t);
            }
        });
    }
}
