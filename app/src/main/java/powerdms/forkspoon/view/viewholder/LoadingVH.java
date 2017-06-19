package powerdms.forkspoon.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import powerdms.forkspoon.R;

/**
 * Created by Ambruster on 13/06/2017.
 */

public final class LoadingVH extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingVH(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
    }
}