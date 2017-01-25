package chi_software.citybase.ui.adapter;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.data.history_amount.HistoryModel;


/**
 * Created by user on 25.01.2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryRecyclerAdapter> {

    ArrayList<HistoryModel> mHistoryList;

    public HistoryAdapter (ArrayList<HistoryModel> mHistoryList) {
        this.mHistoryList = mHistoryList;
    }

    @Override
    public HistoryRecyclerAdapter onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_payments_history_layout, parent, false);
        return new HistoryRecyclerAdapter(view);
    }

    @Override
    public void onBindViewHolder (HistoryRecyclerAdapter holder, int position) {
        String date = mHistoryList.get(position).getDate();
        String status = mHistoryList.get(position).getStatus();
        if ( status.equals("failure") ) {
            status = "Недостаточно средств";
            holder.mStatus.setTextColor(Color.RED);
        }
        if ( status.equals("success") ) {
            status = "Успешный";
            holder.mStatus.setTextColor(Color.GREEN);
        }
        if ( status.equals("wait") ) {
            status = "Ожидаеться";
            holder.mStatus.setTextColor(Color.RED);
        }
        String[] dateAr = date.split(" ");
        holder.mTarifName.setText(mHistoryList.get(position).getOperation());
        holder.mAmount.setText(mHistoryList.get(0).getAmount());
        holder.mDate.setText(dateAr[0]);
        holder.mTime.setText(dateAr[1]);
        holder.mStatus.setText(status);
    }

    @Override
    public int getItemCount () {
        return mHistoryList.size();
    }

    class HistoryRecyclerAdapter extends RecyclerView.ViewHolder {

        private final TextView mTarifName;
        private final TextView mDate;
        private final TextView mTime;
        private final TextView mAmount;
        private final TextView mStatus;

        public HistoryRecyclerAdapter (View itemView) {
            super(itemView);
            mTarifName = (TextView) itemView.findViewById(R.id.tarif_name_tv);
            mDate = (TextView) itemView.findViewById(R.id.date_history_tv);
            mTime = (TextView) itemView.findViewById(R.id.time_history_tv);
            mAmount = (TextView) itemView.findViewById(R.id.amount_history_tv);
            mStatus = (TextView) itemView.findViewById(R.id.status_history_tv);
        }
    }
}
