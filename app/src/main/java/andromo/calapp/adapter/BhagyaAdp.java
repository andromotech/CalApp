package andromo.calapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import andromo.calapp.CalDisp.BhagCalD;
import andromo.calapp.R;
import andromo.calapp.model.BhaModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BhagyaAdp extends RecyclerView.Adapter<BhagyaAdp.MyViewHolder> {

    private Context mContext;
    private List<BhaModel> bhalist;

    public BhagyaAdp(Context mContext, List<BhaModel> bhalist) {
        this.mContext = mContext;
        this.bhalist = bhalist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_albm, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(bhalist.get(i).getName());
        Picasso.with(mContext)
                .load(bhalist.get(i).getPic())
                .placeholder(R.mipmap.ldng)
                .into(viewHolder.cpic);

    }

    @Override
    public int getItemCount() {
        return bhalist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView cpic;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            // count = (TextView) view.findViewById(R.id.count);
              Typeface ofont = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/odia.ttf");
              title.setTypeface(ofont);
            cpic = (ImageView) view.findViewById(R.id.cpic);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        BhaModel clickedDataItem = bhalist.get(pos);
                        Intent intent = new Intent(mContext, BhagCalD.class);
                        intent.putExtra("url", bhalist.get(pos).getUrl());
                        intent.putExtra("pic", bhalist.get(pos).getPic());
                        intent.putExtra("name", bhalist.get(pos).getName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}