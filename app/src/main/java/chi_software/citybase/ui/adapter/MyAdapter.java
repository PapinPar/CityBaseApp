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
import chi_software.citybase.data.ObjectModel;

/**
 * Created by Papin on 11.11.2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DevViewHolder> {

    private List<ObjectModel> developersInfoList;

    public static class DevViewHolder extends RecyclerView.ViewHolder {
        private TextView adminArea;
        private TextView data;
        private TextView type;
        private TextView price;
        private Context myParent;
        private ImageView image;

        DevViewHolder (View itemView) {
            super(itemView);
            adminArea = (TextView) itemView.findViewById(R.id.twArea);
            type = (TextView) itemView.findViewById(R.id.twType);
            data = (TextView) itemView.findViewById(R.id.twData);
            price = (TextView) itemView.findViewById(R.id.twPrice);
            myParent = itemView.getContext();
            image = (ImageView) itemView.findViewById(R.id.objectImage);
        }
    }

    public MyAdapter (List<ObjectModel> developersInfoList) {
        this.developersInfoList = developersInfoList;
    }

    @Override
    public DevViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_object_adappter, parent, false);
        DevViewHolder devViewHolder = new DevViewHolder(v);
        return devViewHolder;
    }

    @Override
    public void onBindViewHolder (DevViewHolder holder, int i) {
        if ( developersInfoList.get(i).AdminArea.length() > 1 ) {
            holder.adminArea.setText(developersInfoList.get(i).AdminArea);
        } else holder.adminArea.setVisibility(View.INVISIBLE);
        if ( developersInfoList.get(i).data.length() > 0 ) {
            holder.data.setText(developersInfoList.get(i).data);
        } else holder.data.setVisibility(View.INVISIBLE);
        if ( developersInfoList.get(i).price != null )
            holder.price.setText(developersInfoList.get(i).price + "грн");
        else holder.price.setVisibility(View.INVISIBLE);
        if ( developersInfoList.get(i).type.length() > 0 )
            holder.type.setText(developersInfoList.get(i).type);
        else
            holder.type.setVisibility(View.INVISIBLE);
        if ( developersInfoList.get(i).url != null )
            Picasso.with(holder.myParent)
                    .load(developersInfoList.get(i).url).centerInside()
                    .resize(120, 120)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_photo).into(holder.image);
        else
            holder.image.setImageResource(R.drawable.no_photo);

    }

    @Override
    public int getItemCount () {
        return developersInfoList.size();
    }

}
