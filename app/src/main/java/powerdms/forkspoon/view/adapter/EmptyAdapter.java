package powerdms.forkspoon.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import powerdms.forkspoon.R;
import powerdms.forkspoon.view.viewholder.EmptyVH;

/**
 * Created by Ambruster on 20/07/2016.
 */


public class EmptyAdapter
        extends RecyclerView.Adapter<EmptyVH> {

    private final String mEmptyText;

    public EmptyAdapter(String emptyText) {
        mEmptyText = emptyText;
    }

    @Override
    public EmptyVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_empty, parent, false);
        return new EmptyVH(view);
    }

    @Override
    public void onBindViewHolder(final EmptyVH holder, int position) {
        holder.text_empty.setText(mEmptyText);
    }

    @Override
    public int getItemCount() {
        return 1;
    }


}
