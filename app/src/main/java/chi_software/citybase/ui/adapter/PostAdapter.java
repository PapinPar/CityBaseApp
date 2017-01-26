package chi_software.citybase.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.data.ModelData;


/**
 * Created by Papin on 11.11.2016.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.DevViewHolder> {

    public interface PostAdapterCall {
        void getPhotoId (String id, int position);
        void getLastPost (int position, int mListSize);
    }


    private final PostAdapterCall mPostAdapterCall;
    private final List<ModelData> mPostsList;


    class DevViewHolder extends RecyclerView.ViewHolder {
        private final TextView adminArea;
        private final TextView site;
        private final TextView type;
        private final TextView price;
        private final ImageView image;
        private final TextView info;
        private final Context myParent;
        //private ImageView backgroundColor;

        DevViewHolder (View itemView) {
            super(itemView);
            adminArea = (TextView) itemView.findViewById(R.id.cityNew);
            type = (TextView) itemView.findViewById(R.id.roomsTypeTW);
            site = (TextView) itemView.findViewById(R.id.siteNew);
            price = (TextView) itemView.findViewById(R.id.priceNew);
            myParent = itemView.getContext();
            image = (ImageView) itemView.findViewById(R.id.imageInfoNew);
            info = (TextView) itemView.findViewById(R.id.detailInfoNew);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    mPostAdapterCall.getPhotoId(mPostsList.get(getAdapterPosition()).getId(), getAdapterPosition());
                }
            });
        }
    }

    public PostAdapter (List<ModelData> developersInfoList, PostAdapterCall mPostAdapterCall) {
        this.mPostsList = developersInfoList;
        this.mPostAdapterCall = mPostAdapterCall;
    }

    @Override
    public DevViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_object_layout, parent, false);
        DevViewHolder devViewHolder = new DevViewHolder(v);
        return devViewHolder;
    }

    @Override
    public void onBindViewHolder (DevViewHolder holder, final int position) {
        Log.d("MyAdapter", "holder.getAdapterPosition():" + holder.getAdapterPosition());
        Log.d("MyAdapter", "i:" + position);
        String currency;
        if ( mPostsList.get(position).getTable().equals("rent_living") || mPostsList.get(position).getTable().equals("rent_not_living") )
            currency = "грн";
        else
            currency = "$";
        // Местоположение
        if ( mPostsList.get(position).getAdminArea().length() > 1 ) {
            holder.adminArea.setText("Район: " + mPostsList.get(position).getAdminArea());
            Log.d("MyAdapter", mPostsList.get(position).getAdminArea());
        } else
            holder.adminArea.setVisibility(View.INVISIBLE);
        // Сайт
        if ( mPostsList.get(position).getData().length() > 0 ) {
            holder.site.setText(mPostsList.get(position).getData());
        } else
            holder.site.setVisibility(View.INVISIBLE);
        // Цена
        if ( mPostsList.get(position).getPrice() != null )
            holder.price.setText(mPostsList.get(position).getPrice() + " " + currency);
        else
            holder.price.setText("?" + currency);
        // Сроки
        if ( mPostsList.get(position).getType() != null && mPostsList.get(position).getType().length() > 0 )
            holder.type.setText(mPostsList.get(position).getType());
        else
            holder.type.setVisibility(View.INVISIBLE);
        // Картинка
        if ( mPostsList.get(position).getUrl() != null ) {
            holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(holder.myParent).load(mPostsList.get(position).getUrl()).error(R.drawable.icon_logo).into(holder.image);
        } else {
            holder.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.image.setImageResource(R.drawable.icon_logo);
        }
        // Информация
        String info;
        if ( mPostsList.get(position).getInfo().length() > 40 ) {
            info = mPostsList.get(position).getInfo().substring(0, 40);
        } else
            info = mPostsList.get(position).getInfo();

        // Информация
        info = info + "...";
        info = info.trim();
        holder.info.setText(info);

        if ( position == (mPostsList.size()) - 3 ) {
            mPostAdapterCall.getLastPost(position + 2, mPostsList.size());
        }
    }

    @Override
    public int getItemCount () {
        return mPostsList.size();
    }

}
