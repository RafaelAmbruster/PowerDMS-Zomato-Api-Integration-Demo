package powerdms.forkspoon.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import powerdms.forkspoon.R;
import powerdms.forkspoon.view.viewholder.ConnectionVH;

/**
 * Created by Ambruster on 20/07/2016.
 */


public class ConnectionAdapter
        extends RecyclerView.Adapter<ConnectionVH> {

    private final String mEmptyText;
    private OnItemClickListener mListener;

    public ConnectionAdapter(String emptyText, OnItemClickListener listener) {
        mEmptyText = emptyText;
        mListener = listener;
    }

    @Override
    public ConnectionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_no_connection, parent, false);
        return new ConnectionVH(view);
    }

    @Override
    public void onBindViewHolder(final ConnectionVH holder, int position) {
        holder.text_error.setText(mEmptyText);
        holder.reconnect.setOnClickListener(v -> mListener.onItemClick(position, v));
    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
