package chi_software.citybase.ui.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chi_software.citybase.R;
import chi_software.citybase.data.activ_service.ServiceData;


/**
 * Created by Papin on 19.01.2017.
 */

public class ActiveServAdapter extends RecyclerView.Adapter<ActiveServAdapter.DevViewHolder> {

    private List<ServiceData> modelData;

    public ActiveServAdapter (List<ServiceData> modelData) {
        this.modelData = modelData;
    }

    public static class DevViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeFrom;
        private final TextView timeTo;
        private final TextView rusName;

        DevViewHolder (View itemView) {
            super(itemView);
            timeFrom = (TextView) itemView.findViewById(R.id.time_from_tv);
            timeTo = (TextView) itemView.findViewById(R.id.time_to_tv);
            rusName = (TextView) itemView.findViewById(R.id.serv_name_tv);
        }
    }

    @Override
    public DevViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_activ_serv_layout, parent, false);
        DevViewHolder devViewHolder = new DevViewHolder(v);
        return devViewHolder;
    }

    @Override
    public void onBindViewHolder (DevViewHolder holder, int position) {
        holder.timeFrom.setText(modelData.get(position).getDateFrom());
        holder.timeTo.setText(modelData.get(position).getDateTO());
        holder.rusName.setText(modelData.get(position).getRusName());
    }

    @Override
    public int getItemCount () {
        return modelData.size();
    }
}
