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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DevViewHolder> {

    public interface photoListner {
        void getPhotoId (String id, int position);
    }


    photoListner photoListner;
    private List<ModelData> developersInfoList;
    private String currency;


    public static class DevViewHolder extends RecyclerView.ViewHolder {
        private TextView adminArea;
        private TextView site;
        private TextView type;
        private TextView price;
        private ImageView image;
        private TextView info;
        private Context myParent;
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

    public MyAdapter (List<ModelData> developersInfoList, photoListner photoListner) {
        this.developersInfoList = developersInfoList;
        this.photoListner = photoListner;
    }

    @Override
    public DevViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview_object_layout, parent, false);
        DevViewHolder devViewHolder = new DevViewHolder(v);
        return devViewHolder;
    }

    @Override
    public void onBindViewHolder (DevViewHolder holder, final int i) {
        if ( developersInfoList.get(i).table.equals("rent_living") || developersInfoList.get(i).table.equals("rent_not_living") )
            currency = "грн";
        else
            currency = "$";
        // Местоположение
        if ( developersInfoList.get(i).AdminArea.length() > 1 ) {
            holder.adminArea.setText("Район: "+developersInfoList.get(i).AdminArea);
            Log.d("MyAdapter", developersInfoList.get(i).AdminArea);
        } else
            holder.adminArea.setVisibility(View.INVISIBLE);
        // Сайт
        if ( developersInfoList.get(i).data.length() > 0 ) {
            holder.site.setText(developersInfoList.get(i).data);
        } else
            holder.site.setVisibility(View.INVISIBLE);
        // Цена
        if ( developersInfoList.get(i).price != null )
            holder.price.setText(developersInfoList.get(i).price + " " + currency);
        else
            holder.price.setText("?" + currency);
        // Сроки
        if ( developersInfoList.get(i).type != null && developersInfoList.get(i).type.length() > 0 )
            holder.type.setText(developersInfoList.get(i).type);
        else
            holder.type.setVisibility(View.INVISIBLE);
        // Картинка
        if ( developersInfoList.get(i).url != null )
            Picasso.with(holder.myParent).load(developersInfoList.get(i).url).error(R.drawable.icon_logo).into(holder.image);
        else
            holder.image.setImageResource(R.drawable.icon_logo);

        // Информация
        String info;
        if ( developersInfoList.get(i).info.length() > 100 ) {
            info = developersInfoList.get(i).info.substring(0, 100);
        } else
            info = developersInfoList.get(i).info;
        // Слушатель
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                photoListner.getPhotoId(developersInfoList.get(i).id, i);
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
        return developersInfoList.size();
    }

}
