package restapi.test.narudore.personal.testrest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart ()
    {
        super.onStart();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.1.11.87/testci3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        NewsService newsService = retrofit.create(NewsService.class);
        newsService.getNewsList(1, "asc").enqueue(new Callback<List<News>>()
        {
            @Override
            public void onResponse (Call<List<News>> call, Response<List<News>> response)
            {
                Log.d("testrestapi", "got response");
                final List<News> newsList = response.body();

                ListView listView = (ListView) findViewById(R.id.newsList);
                ArrayAdapter<News> adapter = new ArrayAdapter<News>(MainActivity.this, android.R.layout.simple_list_item_1, newsList);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick (AdapterView<?> parent, View view, int position, long id)
                    {
                        News news = newsList.get(position);
                        Intent intent = new Intent(MainActivity.this, NewsDetail.class);
                        intent.putExtra(NewsDetail.ARG_NEWS_ID, news.getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure (Call<List<News>> call, Throwable t)
            {
                Log.e("testrestapi", "failure", t);
            }
        });
    }
}
