package andromo.calapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andromo.calapp.CalDisp.SplCalD;
import andromo.calapp.R;
import andromo.calapp.model.SplModel;

public class SplAdp extends RecyclerView.Adapter<SplAdp.MyViewHolder> {

    private Context mContext;
    private List<SplModel> spllist;

    public SplAdp(Context mContext, List<SplModel> spllist) {
        this.mContext = mContext;
        this.spllist = spllist;
    }

    @Override
    public SplAdp.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_albm, viewGroup, false);

        return new SplAdp.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SplAdp.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(spllist.get(i).getName());
        Picasso.with(mContext)
                .load(spllist.get(i).getPic())
                .placeholder(R.mipmap.ldng)
                .into(viewHolder.cpic);

    }

    @Override
    public int getItemCount() {
        return spllist.size();
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
                        SplModel clickedDataItem = spllist.get(pos);
                        Intent intent = new Intent(mContext, SplCalD.class);
                        intent.putExtra("url", spllist.get(pos).getUrl());
                        intent.putExtra("pic", spllist.get(pos).getPic());
                        intent.putExtra("name", spllist.get(pos).getName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "କ୍ୟାଲେଣ୍ଡର ୨୦୨୨" + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}