package powerdms.forkspoon.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import powerdms.forkspoon.R;
import powerdms.forkspoon.model.restaurant.search.ZomatoRestaurant;
import powerdms.forkspoon.view.viewholder.LoadingVH;
import powerdms.forkspoon.view.viewholder.RestaurantVH;

/**
 * Created by Ambruster on 20/07/2016.
 */

public class FavoriteRestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading;
    private ItemFilter mFilter = new ItemFilter();
    private Context context;
    private List<ZomatoRestaurant> original_items = new ArrayList<>();
    public List<ZomatoRestaurant> filtered_items = new ArrayList<>();
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    public OnItemClickListener mListener;
    public OnLoadMoreListener mOnLoadMoreListener;

    public FavoriteRestaurantAdapter(Context context, RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.context = context;
        original_items = new ArrayList<>();
        filtered_items = new ArrayList<>();
        mListener = onItemClickListener;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void AddItems(List<ZomatoRestaurant> items) {
        original_items.clear();
        filtered_items.clear();
        original_items.addAll(items);
        filtered_items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearList() {
        original_items.clear();
        filtered_items.clear();
        notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return filtered_items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_restaurant, parent, false);
            return new RestaurantVH(view, mListener, context);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_loading, parent, false);
            return new LoadingVH(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RestaurantVH) {
            RestaurantVH viewHolder = (RestaurantVH) holder;
            viewHolder.bind(filtered_items.get(position), position);
        } else {
            LoadingVH loadingVH = (LoadingVH) holder;
            loadingVH.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return (null != filtered_items ? filtered_items.size() : 0);
    }

    public int getPosition(int recyclerPosition) {
        return recyclerPosition;
    }

    public void setLoaded() {
        isLoading = false;
    }

    public Filter getFilter() {
        return mFilter;
    }

    public class ItemFilter extends Filter {

        FilterResults results;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            results = new FilterResults();
            final List<ZomatoRestaurant> list = original_items;
            final List<ZomatoRestaurant> result_list = new ArrayList<>(list.size());

            for (int i = 0; i < list.size(); i++) {
                String str_name = list.get(i).getName();
                if (str_name.toLowerCase().contains(query)) {
                    result_list.add(list.get(i));
                }
            }
            results.values = result_list;
            results.count = result_list.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filtered_items = (List<ZomatoRestaurant>) results.values;
            notifyDataSetChanged();
        }
    }
}
