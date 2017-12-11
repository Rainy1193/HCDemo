package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Anh Dao on 8/31/2017.
 */

public class MessageContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SectionIndexer {
    private final int VIEW_FIRST_CHAR = 1;
    private final int VIEW_MESSAGE_CONTACT = 2;
    private Context mContext;
    private ArrayList<ContactData> mArrMessageContact;
    private ArrayList<ContactData> mArrMessageContactSections;
    private ArrayList<Integer> mSectionPositions;
    private Picasso picasso;
    private OnItemClickListener mListener;

    public MessageContactAdapter(Context mContext, ArrayList<ContactData> mArrMessageContact, Picasso picasso) {
        this.mContext = mContext;
        this.mArrMessageContact = mArrMessageContact;
        this.mArrMessageContactSections = new ArrayList<>();
        this.picasso = picasso;
    }

    public void setOnClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_MESSAGE_CONTACT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_contact_item, parent, false);
            return new MessageContactHolder(v);
        } else if (viewType == VIEW_FIRST_CHAR) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_first_char_item, parent, false);
            return new FirstCharHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ContactData data = mArrMessageContactSections.get(position);

        if (holder instanceof FirstCharHolder) {
            FirstCharHolder timeLineHolder = (FirstCharHolder) holder;
            timeLineHolder.mTvFirstChar.setText(data.getFirstChar());
        } else if (holder instanceof MessageContactHolder) {
            MessageContactHolder myHolder = (MessageContactHolder) holder;
            myHolder.mTvName.setText(data.getName());
            if(data.getEmail().isEmpty()){
                myHolder.mTvCompany.setText(data.getPhone());
            }else{
                myHolder.mTvCompany.setText(data.getEmail());
            }

            String avatarUrl = data.getAvatar();
            if (avatarUrl != null) {
                if(avatarUrl.length() != 0){
                    String[] s = avatarUrl.split("/");
                    if (s[s.length - 1].equals("avatar_default.jpg")) {
                        myHolder.mTvAvatarPersonal.setText(getFirstCharacter(data.getName()));
                        myHolder.mTvAvatarPersonal.setVisibility(View.VISIBLE);
                    } else {
                        picasso.load(data.getAvatar()).fit().centerCrop().placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatarPersonal);
                        myHolder.mTvAvatarPersonal.setVisibility(View.GONE);
                    }
                }else{
                    myHolder.mTvAvatarPersonal.setText(getFirstCharacter(data.getName()));
                    myHolder.mTvAvatarPersonal.setVisibility(View.VISIBLE);
                }
            }


            myHolder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(data);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mArrMessageContactSections.get(position).getType().equalsIgnoreCase("contact")) {
            return VIEW_MESSAGE_CONTACT;
        } else if (mArrMessageContactSections.get(position).getType().equalsIgnoreCase("first")) {
            return VIEW_FIRST_CHAR;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return mArrMessageContactSections.size();
    }

    @Override
    public Object[] getSections() {
        int n = mArrMessageContact.size();
        if (n == 0) {
            return null;
        }
        ArrayList<String> sections = new ArrayList<>(26); //Array chứa 26 ký tự chữ cái
        mSectionPositions = new ArrayList<>(26); //Int arr

        String section = "";
        for (int i = 0; i < n; i++) {
            String first = String.valueOf(mArrMessageContact.get(i).getName().charAt(0)).toUpperCase();
            if (first.equalsIgnoreCase(section)) {
                mArrMessageContact.get(i).setType("contact");
                mArrMessageContactSections.add(mArrMessageContact.get(i));
            } else {
                ContactData contactData = new ContactData();
                contactData.setType("first");
                contactData.setFirstChar(first);
                mArrMessageContactSections.add(contactData);

                mArrMessageContact.get(i).setType("contact");
                mArrMessageContactSections.add(mArrMessageContact.get(i));

                section = first;

                sections.add(section); //thêm D vào
                mSectionPositions.add(i); // Add vi trí thứ 0
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    public class MessageContactHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.imgAvatarPersonal)
        RoundedImageView mImgAvatarPersonal;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvCompany)
        TextView mTvCompany;
        @Bind(R.id.tvAvatarPersonal)
        TextView mTvAvatarPersonal;

        private MessageContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class FirstCharHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvFirstChar)
        TextView mTvFirstChar;

        private FirstCharHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ContactData item);
    }

    private String getFirstCharacter(String name) {
        if (name == null || name.isEmpty())
            return name;
        String[] s = name.split(" ");
        String charName = "";
        for (String value : s) {
            charName += value.charAt(0);
        }
        return charName.toUpperCase();
    }

}

