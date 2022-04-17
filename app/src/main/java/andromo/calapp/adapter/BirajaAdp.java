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

import andromo.calapp.CalDisp.BiraCalD;
import andromo.calapp.R;
import andromo.calapp.model.BiModel;

public class BirajaAdp extends RecyclerView.Adapter<BirajaAdp.MyViewHolder> {

    private Context mContext;
    private List<BiModel> bilist;

    public BirajaAdp(Context mContext, List<BiModel> bilist) {
        this.mContext = mContext;
        this.bilist = bilist;
    }

    @Override
    public BirajaAdp.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_albm, viewGroup, false);

        return new BirajaAdp.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BirajaAdp.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(bilist.get(i).getName());
        Picasso.with(mContext)
                .load(bilist.get(i).getPic())
                .placeholder(R.mipmap.ldng)
                .into(viewHolder.cpic);

    }

    @Override
    public int getItemCount() {
        return bilist.size();
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
                        BiModel clickedDataItem = bilist.get(pos);
                        Intent intent = new Intent(mContext, BiraCalD.class);
                        intent.putExtra("url", bilist.get(pos).getUrl());
                        intent.putExtra("pic", bilist.get(pos).getPic());
                        intent.putExtra("name", bilist.get(pos).getName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    }