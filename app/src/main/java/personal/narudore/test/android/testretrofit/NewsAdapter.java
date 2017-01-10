package personal.narudore.test.android.testretrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ray on 11/16/2016.
 */

class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int VIEW_ITEM = 0;
    private static final int VIEW_LOADING = 1;

    private Context context;
    private LayoutInflater inflater;
    private List<News> newsList;
    private OnViewClickListener listener;

    public NewsAdapter (Context context, OnViewClickListener listener)
    {
        this.context = context;
        this.newsList = new ArrayList<>();
        this.listener = listener;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType (int position)
    {
        return (newsList.get(position) == null) ? VIEW_LOADING : VIEW_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_ITEM)
        {
            View view = inflater.inflate(R.layout.item_news_card, parent, false);
            return new ViewHolder(view);
        }
        else
        {
            View view = inflater.inflate(R.layout.item_loading_spinner, parent, false);
            return new LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder viewHolder, int position)
    {
        if (viewHolder instanceof ViewHolder)
        {
            final ViewHolder holder = (ViewHolder) viewHolder;

            News news = newsList.get(position);
            holder.titleText.setText(news.getTitle());
            holder.contentText.setText(news.getContent());
            holder.shareButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v)
                {
                    listener.onShare(holder.getAdapterPosition());
                }
            });
            holder.moreButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v)
                {
                    listener.onViewDetail(holder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount ()
    {
        return newsList.size();
    }

    /**
     * Automatically hides loading spinner.
     * @param list
     */
    public void add (Collection<? extends News> list)
    {
        hideLoadingSpinner();

        int oldSize = newsList.size();
        if (newsList.addAll(list))
            notifyItemRangeInserted(oldSize, list.size());
    }

    public void clear ()
    {
        int size = newsList.size();
        newsList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public News get (int position)
    {
        return newsList.get(position);
    }

    public void showLoadingSpinner ()
    {
        int oldSize = newsList.size();
        if (oldSize == 0 || newsList.get(oldSize-1) != null)
        {
            newsList.add(null);
            notifyItemInserted(oldSize);
        }
    }

    public void hideLoadingSpinner ()
    {
        int oldEndIdx = newsList.size() - 1;
        if (oldEndIdx >= 0 && newsList.get(oldEndIdx) == null)
        {
            newsList.remove(oldEndIdx);
            notifyItemRemoved(oldEndIdx);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder
    {
        LoadingViewHolder (View view)
        {
            super(view);
        }
    }

    interface OnViewClickListener
    {
        void onViewDetail (int position);
        void onShare (int position);
    }
}
