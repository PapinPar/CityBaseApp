package chi_software.citybase.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    public interface photoListner{
        void getPhotoId (String id,int position);
    }

    photoListner photoListner;
    private List<ModelData> developersInfoList;

    public static class DevViewHolder extends RecyclerView.ViewHolder {
        private TextView adminArea;
        private TextView data;
        private TextView type;
        private TextView price;
        private Context myParent;
        private ImageView image;
        private TextView info;

        DevViewHolder (View itemView) {
            super(itemView);
            adminArea = (TextView) itemView.findViewById(R.id.twArea);
            type = (TextView) itemView.findViewById(R.id.twType);
            data = (TextView) itemView.findViewById(R.id.twData);
            price = (TextView) itemView.findViewById(R.id.twPrice);
            myParent = itemView.getContext();
            image = (ImageView) itemView.findViewById(R.id.modelImage);
            info = (TextView) itemView.findViewById(R.id.twInfo);
        }
    }

    public MyAdapter (List<ModelData> developersInfoList,photoListner photoListner) {
        this.developersInfoList = developersInfoList;
        this.photoListner = photoListner;
    }

    @Override
    public DevViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_object_adappter, parent, false);
        DevViewHolder devViewHolder = new DevViewHolder(v);
        return devViewHolder;
    }

    @Override
    public void onBindViewHolder (DevViewHolder holder, final int i) {
        if ( developersInfoList.get(i).AdminArea.length() > 1 ) {
            holder.adminArea.setText(developersInfoList.get(i).AdminArea);
        } else holder.adminArea.setVisibility(View.INVISIBLE);
        if ( developersInfoList.get(i).data.length() > 0 ) {
            holder.data.setText(developersInfoList.get(i).data);
        } else holder.data.setVisibility(View.INVISIBLE);
        if ( developersInfoList.get(i).price != null )
            holder.price.setText(developersInfoList.get(i).price + "грн");
        else holder.price.setText("?грн");
        if ( developersInfoList.get(i).type.length() > 0 )
            holder.type.setText(developersInfoList.get(i).type);
        else
            holder.type.setVisibility(View.INVISIBLE);
        if ( developersInfoList.get(i).url != null )
            Picasso.with(holder.myParent)
                    .load(developersInfoList.get(i).url).centerInside()
                    .resize(120, 120)
                    .error(R.drawable.no_photo)
                    .into(holder.image);
        else
            holder.image.setImageResource(R.drawable.no_photo);
        String info;
        if ( developersInfoList.get(i).info.length() > 110 ) {
            info = developersInfoList.get(i).info.substring(0, 110);
        } else info = developersInfoList.get(i).info;

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                photoListner.getPhotoId(developersInfoList.get(i).id,i);
            }
        });
        info = info + "...";
        info = info.trim();
        holder.info.setText(info);
    }

    @Override
    public int getItemCount () {
        return developersInfoList.size();
    }

}