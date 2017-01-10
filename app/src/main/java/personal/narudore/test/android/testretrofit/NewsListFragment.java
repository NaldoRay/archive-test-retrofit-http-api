package personal.narudore.test.android.testretrofit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ray on 1/10/2017.
 */

public class NewsListFragment extends Fragment implements NewsAdapter.OnViewClickListener
{
    private static final int REQUEST_POST_NEWS = 1213;

    private NewsService newsService;
    private NewsAdapter newsAdapter;
    private FloatingActionButton postButton;

    private static int page;

    private boolean loading;

    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_news_list, container, false);

        postButton = (FloatingActionButton) root.findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(getContext(), PostNewsActivity.class);
                startActivityForResult(intent, REQUEST_POST_NEWS);
            }
        });

        newsAdapter = new NewsAdapter(getContext(), this);

        RecyclerView listView = (RecyclerView) root.findViewById(R.id.newsList);
        listView.setAdapter(newsAdapter);
        listView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled (RecyclerView recyclerView, int dx, int dy)
            {
                if (!recyclerView.canScrollVertically(1))
                {
                    if (page > 0)
                        loadNews();
                }
            }
        });

        MyApp app = (MyApp) getActivity().getApplication();
        newsService = app.createService(NewsService.class);

        page = 1;
        loadNews();

        return root;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_POST_NEWS && resultCode == Activity.RESULT_OK)
        {
            page = 1;
            loadNews();
        }
    }

    private void loadNews ()
    {
        if (loading)
            return;

        loading = true;
        newsAdapter.showLoadingSpinner();

        newsService.getNewsList(page, "desc").enqueue(new Callback<List<News>>()
        {
            @Override
            public void onResponse (Call<List<News>> call, Response<List<News>> response)
            {
                List<News> list = response.body();

                if (list.isEmpty())
                {
                    page = 0;
                }
                else
                {
                    if (page == 1)
                        newsAdapter.clear();

                    page++;
                    newsAdapter.add(list);
                }

                loading = false;
                newsAdapter.hideLoadingSpinner();
            }

            @Override
            public void onFailure (Call<List<News>> call, Throwable t)
            {
                Log.e("testrestapi", "failure", t);
                loading = false;
                newsAdapter.hideLoadingSpinner();
            }
        });
    }

    @Override
    public void onViewDetail (int position)
    {
        News news = newsAdapter.get(position);
        Intent intent = new Intent(getContext(), NewsDetailActivity.class);
        intent.putExtra(NewsDetailActivity.ARG_NEWS_ID, news.getId());
        startActivity(intent);
    }

    @Override
    public void onShare (int position)
    {
        Toast.makeText(getContext(), "Share news #" + position, Toast.LENGTH_SHORT).show();
    }
}
