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

    public interface photoListener {
        void getPhotoId (String id, int position);
    }


    final photoListener photoListner;
    private final List<ModelData> mDevelopersInfoList;


    public static class DevViewHolder extends RecyclerView.ViewHolder {
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
            // backgroundColor = (ImageView) itemView.findViewById(backgroundColor);
        }
    }

    public PostAdapter (List<ModelData> developersInfoList, photoListener photoListener) {
        this.mDevelopersInfoList = developersInfoList;
        this.photoListner = photoListener;
    }

    @Override
    public DevViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_object_layout, parent, false);
        DevViewHolder devViewHolder = new DevViewHolder(v);
        return devViewHolder;
    }

    @Override
    public void onBindViewHolder (DevViewHolder holder, final int i) {
        Log.d("MyAdapter", "holder.getAdapterPosition():" + holder.getAdapterPosition());
        Log.d("MyAdapter", "i:" + i);
        String currency;
        if ( mDevelopersInfoList.get(i).getTable().equals("rent_living") || mDevelopersInfoList.get(i).getTable().equals("rent_not_living") )
            currency = "грн";
        else
            currency = "$";
        // Местоположение
        if ( mDevelopersInfoList.get(i).getAdminArea().length() > 1 ) {
            holder.adminArea.setText("Район: " + mDevelopersInfoList.get(i).getAdminArea());
            Log.d("MyAdapter", mDevelopersInfoList.get(i).getAdminArea());
        } else
            holder.adminArea.setVisibility(View.INVISIBLE);
        // Сайт
        if ( mDevelopersInfoList.get(i).getData().length() > 0 ) {
            holder.site.setText(mDevelopersInfoList.get(i).getData());
        } else
            holder.site.setVisibility(View.INVISIBLE);
        // Цена
        if ( mDevelopersInfoList.get(i).getPrice() != null )
            holder.price.setText(mDevelopersInfoList.get(i).getPrice() + " " + currency);
        else
            holder.price.setText("?" + currency);
        // Сроки
        if ( mDevelopersInfoList.get(i).getType() != null && mDevelopersInfoList.get(i).getType().length() > 0 )
            holder.type.setText(mDevelopersInfoList.get(i).getType());
        else
            holder.type.setVisibility(View.INVISIBLE);
        // Картинка
        if ( mDevelopersInfoList.get(i).getUrl() != null ) {
            holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(holder.myParent).load(mDevelopersInfoList.get(i).getUrl()).error(R.drawable.icon_logo).into(holder.image);
        } else {
            holder.image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.image.setImageResource(R.drawable.icon_logo);
        }

        // Информация
        String info;
        if ( mDevelopersInfoList.get(i).getInfo().length() > 40 ) {
            info = mDevelopersInfoList.get(i).getInfo().substring(0, 40);
        } else
            info = mDevelopersInfoList.get(i).getInfo();
        // Слушатель
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                photoListner.getPhotoId(mDevelopersInfoList.get(i).getId(), i);
            }
        });
        // Информация
        info = info + "...";
        info = info.trim();
        holder.info.setText(info);
        // Окрашивание
        // if ( developersInfoList.get(i).color != null ) {
        //     if ( developersInfoList.get(i).color.equals("1") )
        //         holder.backgroundColor.setImageResource(R.color.backGreen);
        //     if ( developersInfoList.get(i).color.equals("2") )
        //         holder.backgroundColor.setImageResource(R.color.backYellow);
        //     if ( developersInfoList.get(i).color.equals("3") )
        //         holder.backgroundColor.setImageResource(R.color.backRed);
        //     if ( developersInfoList.get(i).color.equals("0") )
        //         holder.backgroundColor.setImageResource(R.color.backWhite);
        // } else
        //     holder.backgroundColor.setImageResource(R.color.backWhite);
    }

    @Override
    public int getItemCount () {
        return mDevelopersInfoList.size();
    }

}
