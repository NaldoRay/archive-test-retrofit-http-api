package restapi.test.narudore.personal.testrest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ray on 11/16/2016.
 */

class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>
{
    private Context context;
    private List<News> newsList;
    private OnViewClickListener listener;

    public NewsAdapter (Context context, List<News> newsList, OnViewClickListener listener)
    {
        this.context = context;
        this.newsList = newsList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_news_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, final int position)
    {
        News news = newsList.get(position);
        holder.titleText.setText(news.getTitle());
        holder.contentText.setText(news.getContent());
        holder.shareButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                listener.onShare(position);
            }
        });
        holder.moreButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                listener.onViewDetail(position);
            }
        });
    }

    @Override
    public int getItemCount ()
    {
        return newsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView titleText;
        private TextView contentText;
        private Button shareButton;
        private Button moreButton;

        ViewHolder (View view)
        {
            super(view);
            titleText = (TextView) view.findViewById(R.id.titleText);
            contentText = (TextView) view.findViewById(R.id.contentText);
            shareButton = (Button) view.findViewById(R.id.shareButton);
            moreButton = (Button) view.findViewById(R.id.moreButton);
        }
    }

    interface OnViewClickListener
    {
        void onViewDetail (int position);
        void onShare (int position);
    }
}
