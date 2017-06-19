package powerdms.forkspoon.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import powerdms.forkspoon.R;

/**
 * Created by Ambruster on 6/15/2017.
 */

public class EmptyVH extends RecyclerView.ViewHolder {

    public TextView text_empty;

    public EmptyVH(View view) {
        super(view);
        text_empty = (TextView) view.findViewById(R.id.text_empty);
    }

}