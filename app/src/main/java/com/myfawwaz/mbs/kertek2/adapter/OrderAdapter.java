
package com.myfawwaz.mbs.kertek.adapter;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;

        import com.myfawwaz.mbs.kertek.model.ModelOrder;

        import com.myfawwaz.mbs.kertek.R;

        import java.util.List;

/**
 * Created by Azhar Rivaldi on 22-12-2019.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private  List<ModelOrder> items;
    private OrderAdapter.onSelectData onSelectData;

    public interface onSelectData {
        void onSelected(ModelOrder modelOrder);
    }

    public OrderAdapter(Context context, List<ModelOrder> items, OrderAdapter.onSelectData xSelectData) {
        this.items = items;
        this.onSelectData = xSelectData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_surah, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelOrder data = items.get(position);

        holder.txtNumber.setText(data.getOrder_id());
        holder.txtName.setText("User : " + data.getFirstname() +" "+ data.getLastname());
        holder.txtHp.setText("Hp."+ data.getTelephone() +" Tgl :"+ data.getDate_added());
        holder.txtAlmt.setText(data.getPayment_address_1() + " ");
        holder.txtTotal.setText(data.getTotal());
        holder.cvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Class Holder
    class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvOrder;
        public TextView txtNumber;
        public TextView txtName;
        public TextView txtHp;
        public TextView txtAlmt;
        public TextView txtTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            cvOrder = itemView.findViewById(R.id.cvOrder);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtName = itemView.findViewById(R.id.txtName);
            txtHp = itemView.findViewById(R.id.txtHp);

            txtAlmt = itemView.findViewById(R.id.txtAlmt);
            txtTotal = itemView.findViewById(R.id.txtTotal);
        }
    }

}
