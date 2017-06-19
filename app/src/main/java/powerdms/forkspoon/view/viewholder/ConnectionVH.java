package powerdms.forkspoon.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import powerdms.forkspoon.R;

/**
 * Created by Ambruster on 6/15/2017.
 */

public class ConnectionVH extends RecyclerView.ViewHolder{

    public TextView text_error;
    public Button reconnect;

    public ConnectionVH(View view) {
        super(view);
        text_error = (TextView) view.findViewById(R.id.text_error);
        reconnect = (Button) view.findViewById(R.id.retry);
    }


}