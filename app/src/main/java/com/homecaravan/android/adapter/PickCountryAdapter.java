package com.homecaravan.android.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.models.Country;
import com.homecaravan.android.myinterface.IGetCountry;

import java.io.InputStream;
import java.util.ArrayList;

public class PickCountryAdapter extends RecyclerView.Adapter<PickCountryAdapter.MyHolder> {
    private ArrayList<String> ensignCountry;
    private ArrayList<Country> arrCountry = new ArrayList<>();
    private Context context;
    private IGetCountry iGetCountry;

    public PickCountryAdapter(Context context, ArrayList<String> ensignCountry, ArrayList<Country> arrCountry) {
        this.ensignCountry = ensignCountry;
        this.arrCountry = arrCountry;
        this.context = context;
    }

    public void setListner(IGetCountry iGetCountry) {
        this.iGetCountry = iGetCountry;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View countryItem = layoutInflater.inflate(R.layout.pic_country_item, parent, false);
        return new MyHolder(countryItem);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        try {
            InputStream inputStream = context.getAssets().open(ensignCountry.get(position));
            holder.imageViewCountry.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            holder.textViewName.setText(arrCountry.get(position).getName());
            holder.textViewPhone.setText(arrCountry.get(position).getDialCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.relativeLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iGetCountry.getDataCountry(arrCountry.get(position).getDialCode(), ensignCountry.get(position), arrCountry.get(position).getCode(),arrCountry.get(position).getName());
            }
        });
    }


    public void updateCountry(ArrayList<String> ensignCountry, ArrayList<Country> countries) {
        this.ensignCountry = ensignCountry;
        this.arrCountry = countries;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrCountry.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewCountry;
        private TextView textViewName;
        private TextView textViewPhone;
        private RelativeLayout relativeLayoutItem;

        public MyHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            imageViewCountry = (ImageView) itemView.findViewById(R.id.ivCountry);
            textViewName = (TextView) itemView.findViewById(R.id.tvNameCountry);
            textViewPhone = (TextView) itemView.findViewById(R.id.tvPhoneCountry);
            relativeLayoutItem = (RelativeLayout) itemView.findViewById(R.id.layoutCountryItem);
        }
    }


}
