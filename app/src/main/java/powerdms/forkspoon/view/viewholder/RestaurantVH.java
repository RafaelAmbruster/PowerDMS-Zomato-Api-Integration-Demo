package powerdms.forkspoon.view.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import powerdms.forkspoon.R;
import powerdms.forkspoon.helper.util.FontTypefaceUtils;

import powerdms.forkspoon.helper.util.LocationUtility;
import powerdms.forkspoon.model.common.geocode.NearbyRestaurant;
import powerdms.forkspoon.model.restaurant.search.Restaurant;
import powerdms.forkspoon.model.restaurant.search.ZomatoRestaurant;
import powerdms.forkspoon.view.adapter.OnItemClickListener;

/**
 * Created by Ambruster on 6/14/2017.
 */

public class RestaurantVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView text_name, text_cuisine, text_distance;
    private ImageView img_thumb;
    private OnItemClickListener mListener;
    private RatingBar user_review_rating_bar;
    private CardView cv_parent;
    private Context context;

    public RestaurantVH(View itemView, OnItemClickListener listener, Context context) {
        super(itemView);
        mListener = listener;
        itemView.setOnClickListener(this);

        this.context = context;
        text_name = (TextView) itemView.findViewById(R.id.text_name);
        text_name.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(context));

        text_cuisine = (TextView) itemView.findViewById(R.id.text_cuisine);
        text_cuisine.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(context));

        text_distance = (TextView) itemView.findViewById(R.id.text_distance);
        text_distance.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(context));

        img_thumb = (ImageView) itemView.findViewById(R.id.img_thumb);
        cv_parent = (CardView) itemView.findViewById(R.id.cv_parent);
        user_review_rating_bar = (RatingBar) itemView.findViewById(R.id.user_review_rating_bar);
    }

    @Override
    public void onClick(View v) {
        mListener.onItemClick(getAdapterPosition(), v);
    }

    public void bind(final Object c, final int position) {

        TextDrawable.IBuilder builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .roundRect(4);

        String thumb_url = "";
        String name = "";

        if (c instanceof Restaurant) {

            Restaurant rest = (Restaurant) c;
            name = rest.getRestaurant().getName();
            text_name.setText(name);
            text_cuisine.setText(rest.getRestaurant().getCuisines());
            text_distance.setText(String.valueOf(rest.getRestaurant().getDistance()).concat(" mi"));
            if (!rest.getRestaurant().getUserRating().getAggregateRating().equals(""))
                user_review_rating_bar.setRating(Float.parseFloat(rest.getRestaurant().getUserRating().getAggregateRating()));
            thumb_url = rest.getRestaurant().getThumb();

            if (rest.getRestaurant().getDistance() > 0) {
                String distance = LocationUtility.getDistanceString((rest).getRestaurant().getDistance(), LocationUtility.isMetricSystem(), context);
                text_distance.setText(distance);
                text_distance.setVisibility(View.VISIBLE);
            } else {
                text_distance.setVisibility(View.GONE);
            }

        } else if (c instanceof NearbyRestaurant) {

            NearbyRestaurant nearby = (NearbyRestaurant) c;
            name = nearby.getRestaurant().getName();
            text_name.setText(name);

            text_cuisine.setText(nearby.getRestaurant().getCuisines());
            text_distance.setText(String.valueOf(nearby.getRestaurant().getDistance()).concat(" mi"));

            if (!nearby.getRestaurant().getUserRating().getAggregateRating().equals(""))
                user_review_rating_bar.setRating(Float.parseFloat(nearby.getRestaurant().getUserRating().getAggregateRating()));

            thumb_url = nearby.getRestaurant().getThumb();

            if (nearby.getRestaurant().getDistance() > 0) {
                String distance = LocationUtility.getDistanceString((nearby).getRestaurant().getDistance(), LocationUtility.isMetricSystem(), context);
                text_distance.setText(distance);
                text_distance.setVisibility(View.VISIBLE);
            } else {
                text_distance.setVisibility(View.GONE);
            }
        } else if (c instanceof ZomatoRestaurant) {

            ZomatoRestaurant zomato = (ZomatoRestaurant) c;
            name = zomato.getName();
            text_name.setText(name);

            text_cuisine.setText(zomato.getCuisines());
            text_distance.setText(String.valueOf(zomato.getDistance()).concat(" mi"));

            thumb_url = zomato.getThumb();

            if (zomato.getDistance() > 0) {
                String distance = LocationUtility.getDistanceString((zomato).getDistance(), LocationUtility.isMetricSystem(), context);
                text_distance.setText(distance);
                text_distance.setVisibility(View.VISIBLE);
            } else {
                text_distance.setVisibility(View.GONE);
            }
        }

        if (!thumb_url.equals(""))
            Glide.with(context).load(thumb_url)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img_thumb);
        else {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(name);
            TextDrawable ic1 = builder.build(TextUtils.substring(name, 0, 1), color);
            img_thumb.setImageDrawable(ic1);
        }

        cv_parent.setOnClickListener(v -> mListener.onItemClick(position, v));
    }

}
