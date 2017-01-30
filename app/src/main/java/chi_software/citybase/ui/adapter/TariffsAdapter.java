package chi_software.citybase.ui.adapter;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import chi_software.citybase.R;
import chi_software.citybase.data.tarif.TariffModel;


public class TariffsAdapter extends RecyclerView.Adapter<TariffsAdapter.TariffRecyclerAdapter> {

    private ArrayList<TariffModel> mTariffList;
    private static final String mAmount = "К оплате ";
    private int mHours;
    private TariffClick mTarrifClcikI;


    public interface TariffClick {
        void activateOrder (String id);
        void buyTariff (String id,float amount);
    }

    public TariffsAdapter (ArrayList<TariffModel> mTariffList, TariffClick mTarrifClcikI) {
        this.mTariffList = mTariffList;
        this.mTarrifClcikI = mTarrifClcikI;
    }

    @Override
    public TariffRecyclerAdapter onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_tariff_list, parent, false);
        return new TariffRecyclerAdapter(view);
    }

    @Override
    public void onBindViewHolder (TariffsAdapter.TariffRecyclerAdapter holder, int position) {
        mHours = Integer.parseInt(mTariffList.get(position).getDuration());
        mHours = mHours / 24;
        String plain = Html.fromHtml(mTariffList.get(position).getDescription()).toString();
        String[] s = plain.split("MSR");
        plain = s[0].replace("\n\n", "\n");
        plain = plain + " MSR";
        holder.mTariffName.setText(mTariffList.get(position).getRusName());
        holder.mButt.setText(mAmount + mTariffList.get(position).getUserCost() + " грн");
        holder.mDate.setText(mTariffList.get(position).getCost() + " грн" + "/" + mHours + " дней");
        holder.mInfo.setText(plain);
    }

    @Override
    public int getItemCount () {
        return mTariffList.size();
    }

    public class TariffRecyclerAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTariffName;
        private final TextView mDate;
        private final Button mButt;
        private final TextView mInfo;

        public TariffRecyclerAdapter (View itemView) {
            super(itemView);
            mTariffName = (TextView) itemView.findViewById(R.id.tariff_name_tv);
            mDate = (TextView) itemView.findViewById(R.id.date_tv);
            mButt = (Button) itemView.findViewById(R.id.tariff_btn);
            mInfo = (TextView) itemView.findViewById(R.id.tariff_info_tv);

            mButt.setOnClickListener(this);
        }

        @Override
        public void onClick (View v) {
            int mAmount = mTariffList.get(getAdapterPosition()).getUserCost();
            if ( mAmount<=0) {
                mTarrifClcikI.activateOrder(mTariffList.get(getAdapterPosition()).getId());
            } else {
                mTarrifClcikI.buyTariff(mTariffList.get(getAdapterPosition()).getId(),mTariffList.get(getAdapterPosition()).getUserCost());
            }
        }
    }
}
