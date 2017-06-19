package powerdms.forkspoon.view.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import powerdms.forkspoon.R;
import powerdms.forkspoon.helper.util.FontTypefaceUtils;
import powerdms.forkspoon.model.common.category.Category;
import powerdms.forkspoon.view.adapter.OnItemClickListener;

/**
 * Created by Ambruster on 6/14/2017.
 */

public class CategoryVH extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView text_name;
    private ImageView photo_content;
    private OnItemClickListener mListener;
    private CardView cv_parent;

    public CategoryVH(View itemView, OnItemClickListener listener, Context context) {
        super(itemView);
        mListener = listener;
        itemView.setOnClickListener(this);
        text_name = (TextView) itemView.findViewById(R.id.text_name);
        text_name.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(context));
        photo_content = (ImageView) itemView.findViewById(R.id.photo_content);
        cv_parent = (CardView) itemView.findViewById(R.id.cv_parent);
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClick(getAdapterPosition(), v);
    }

    public void bind(final Category c, final int position) {
        text_name.setText(c.getCategories().getName());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(c.getCategories().getName());

        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .roundRect(4);

        TextDrawable ic1 = builder.build(TextUtils.substring(c.getCategories().getName(), 0, 1), color);
        photo_content.setImageDrawable(ic1);

        cv_parent.setOnClickListener(v -> mListener.onItemClick(position, v));
    }

}
