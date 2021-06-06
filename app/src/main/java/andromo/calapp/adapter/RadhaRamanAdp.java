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

import andromo.calapp.CalDisp.RadhaCalD;
import andromo.calapp.R;
import andromo.calapp.model.RaModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RadhaRamanAdp extends RecyclerView.Adapter<RadhaRamanAdp.MyViewHolder> {

    private Context mContext;
    private List<RaModel> radhalist;

    public RadhaRamanAdp(Context mContext, List<RaModel> radhalist) {
        this.mContext = mContext;
        this.radhalist = radhalist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_albm, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(radhalist.get(i).getName());
        Picasso.with(mContext)
                .load(radhalist.get(i).getPic())
                .placeholder(R.mipmap.ldng)
                .into(viewHolder.cpic);

    }

    @Override
    public int getItemCount() {
        return radhalist.size();
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
                        RaModel clickedDataItem = radhalist.get(pos);
                        Intent intent = new Intent(mContext, RadhaCalD.class);
                        //intent.putExtra("name", albumList.get(pos).getSteps());
                        intent.putExtra("url", radhalist.get(pos).getUrl());
                        intent.putExtra("pic", radhalist.get(pos).getPic());
                        intent.putExtra("name", radhalist.get(pos).getName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //   intent.putExtra(YogaDetailView.EXTRA_POSITION, getAdapterPosition());
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}