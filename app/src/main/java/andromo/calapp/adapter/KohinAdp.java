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

import andromo.calapp.CalDisp.KohinoorCalD;
import andromo.calapp.R;
import andromo.calapp.model.KohiModel;

public class KohinAdp extends RecyclerView.Adapter<KohinAdp.MyViewHolder> {

private Context mContext;
private List<KohiModel> kohilist;

public KohinAdp(Context mContext, List<KohiModel> kohilist) {
        this.mContext = mContext;
        this.kohilist = kohilist;
        }

@Override
public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.card_albm, viewGroup, false);

        return new MyViewHolder(view);
        }

@Override
public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(kohilist.get(i).getName());
        Picasso.with(mContext)
        .load(kohilist.get(i).getPic())
        .placeholder(R.mipmap.ldng)
        .into(viewHolder.cpic);

        }

@Override
public int getItemCount() {
        return kohilist.size();
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
                    KohiModel clickedDataItem = kohilist.get(pos);
                    Intent intent = new Intent(mContext, KohinoorCalD.class);
                    intent.putExtra("url", kohilist.get(pos).getUrl());
                    intent.putExtra("pic", kohilist.get(pos).getPic());
                    intent.putExtra("name", kohilist.get(pos).getName());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
}
