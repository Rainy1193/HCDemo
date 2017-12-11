package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ConversationActivity;
import com.homecaravan.android.consumer.message.IMessageThreadAction;
import com.homecaravan.android.consumer.model.message.ConsumerMessageAll;
import com.homecaravan.android.consumer.model.message.MessageUser;
import com.homecaravan.android.consumer.model.message.MessageUserData;
import com.homecaravan.android.ui.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;


/**
 * Created by Anh Dao on 8/31/2017.
 */

public class MessageAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_MESSAGE_PERSONAL = 1;
    private final int VIEW_MESSAGE_GROUP = 2;
    private Long timeStampCurrentOfToday = System.currentTimeMillis() / 1000;
    private Long timeStampStartOfToday = (timeStampCurrentOfToday / 86400) * 86400;
    private Context mContext;
    private ArrayList<ConsumerMessageAll> mArrMessage;
    private SimpleDateFormat sdfHHMMA = new SimpleDateFormat("hh:mm a", Locale.US);
    private SimpleDateFormat sdfDDMMM = new SimpleDateFormat("dd MMM", Locale.US);
    private Picasso picasso;
    private IMessageThreadAction mListener;

    public MessageAllAdapter(Context mContext, ArrayList<ConsumerMessageAll> mArrMessage, Picasso picasso,
                             IMessageThreadAction mListener) {
        this.mContext = mContext;
        this.mArrMessage = mArrMessage;
        this.picasso = picasso;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_MESSAGE_PERSONAL) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_personal_item, parent, false);
            vh = new MessagePersonalHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_group_item, parent, false);
            vh = new MessageGroupHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ConsumerMessageAll consumerMessage = mArrMessage.get(position);

        if (holder instanceof MessagePersonalHolder) {
            MessagePersonalHolder myHolder = (MessagePersonalHolder) holder;

            myHolder.mTvMessageLastTime.setText(getTime(consumerMessage.getMessageThread().getModifiedDatetime()));

            if (consumerMessage.getMessageThread().getMessageThreadView() != null) {
                String lastMessage = consumerMessage.getMessageThread().getMessageThreadView().getContent();
                if (lastMessage != null) {
                    myHolder.mTvLastMessage.setText(lastMessage);
                } else {
                    myHolder.mTvLastMessage.setText("Start the conversation !!");
                }
            } else {
                myHolder.mTvLastMessage.setText("Start the conversation !!");
            }

            if(consumerMessage.getMessageThread().getUserInThread() != null
                    && consumerMessage.getMessageThread().getUserInThread().size() != 0){
                myHolder.mTvMessageName.setText(consumerMessage.getMessageThread().getUserInThread().get(0).getName());
                String avatarUrl = consumerMessage.getMessageThread().getUserInThread().get(0).getAvatar();
                checkAvatar(avatarUrl, myHolder.mTvAvatarPersonal, getThreadName(consumerMessage.getMessageThread().getUserInThread()), myHolder.mImgAvatarPersonal);

            }else{
                myHolder.mTvMessageName.setText(consumerMessage.getMessageThread().getName());
                myHolder.mTvAvatarPersonal.setText(getFirstCharacter(consumerMessage.getMessageThread().getName()));
                myHolder.mTvAvatarPersonal.setVisibility(View.VISIBLE);
            }

            myHolder.mLayoutPersonal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ConversationActivity.class);
                    intent.putExtra("ID_THREAD", consumerMessage.getMessageThread().getId());
                    intent.putExtra("CREATE_DATETIME_THREAD", consumerMessage.getMessageThread().getCreatedDatetime());
                    intent.putExtra("MODIFIED_DATETIME_THREAD", consumerMessage.getMessageThread().getModifiedDatetime());
                    intent.putExtra("CREATE_BY_THREAD", consumerMessage.getMessageThread().getCreatedBy());
                    intent.putExtra("MODIFIED_BY_THREAD", consumerMessage.getMessageThread().getModifiedBy());
                    if(consumerMessage.getMessageThread().getUserInThread() != null
                            && consumerMessage.getMessageThread().getUserInThread().size() != 0){
                        intent.putExtra("NAME_THREAD", consumerMessage.getMessageThread().getUserInThread().get(0).getName());
                    }else{
                        intent.putExtra("NAME_THREAD", consumerMessage.getMessageThread().getName());
                    }
                    intent.putExtra("DATA_THREAD", consumerMessage.getMessageThread().getData());
                    mContext.startActivity(intent);
                }
            });

            myHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.deleteThread(position, consumerMessage.getMessageThread().getId());
                }
            });

        } else {
            if (consumerMessage.getMessageThread().getUserInThread() == null
                    || consumerMessage.getMessageThread().getUserInThread().isEmpty()) {
                return;
            }
            MessageGroupHolder myHolder = (MessageGroupHolder) holder;
            if (consumerMessage.getMessageThread().getName() != null) {
                myHolder.mTvMessageName.setText(consumerMessage.getMessageThread().getName());
            } else {
                myHolder.mTvMessageName.setText(getThreadName(consumerMessage.getMessageThread().getUserInThread()));
            }
            myHolder.mTvMessageLastTime.setText(getTime(consumerMessage.getMessageThread().getModifiedDatetime()));

            if (consumerMessage.getMessageThread().getMessageThreadView() != null) {
                String lastMessage = consumerMessage.getMessageThread().getMessageThreadView().getContent();
                if (lastMessage != null) {
                    myHolder.mTvLastMessage.setText(lastMessage);
                } else {
                    myHolder.mTvLastMessage.setText("Start the conversation !!");
                }
            } else {
                myHolder.mTvLastMessage.setText("Start the conversation !!");
            }

            String avatarUrl = MessageUser.getInstance().getData().getAvatar();
            checkAvatar(avatarUrl, myHolder.mTvAvatar1, consumerMessage.getMessageThread().getUserInThread().get(0).getName(), myHolder.mImgAvatar1);

            String avatarUrl2, avatarUrl3;
            int n = consumerMessage.getMessageThread().getUserInThread().size();
            if(n == 0){
                myHolder.mTvAvatar2.setVisibility(View.GONE);
                myHolder.mTvAvatar3.setVisibility(View.GONE);
                myHolder.mTvAvatar4.setVisibility(View.GONE);
            }else if(n == 1){
                avatarUrl2 = consumerMessage.getMessageThread().getUserInThread().get(0).getAvatar();
                checkAvatar(avatarUrl2, myHolder.mTvAvatar2, consumerMessage.getMessageThread().getUserInThread().get(0).getName(), myHolder.mImgAvatar2);

                myHolder.mTvAvatar3.setVisibility(View.GONE);
                myHolder.mTvAvatar4.setVisibility(View.GONE);
            } else if(n == 2){
                avatarUrl2 = consumerMessage.getMessageThread().getUserInThread().get(0).getAvatar();
                if (avatarUrl2 != null) {
                    String[] s = avatarUrl2.split("/");
                    if (s[s.length - 1].equals("avatar_default.jpg")) {
                        myHolder.mTvAvatar2.setText(getFirstCharacter(consumerMessage.getMessageThread().getUserInThread().get(0).getName()));
                        myHolder.mTvAvatar2.setVisibility(View.VISIBLE);
                    } else {
                        picasso.load(avatarUrl2).fit().centerCrop().placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatar2);
                        myHolder.mTvAvatar2.setVisibility(View.GONE);
                    }
                }
                checkAvatar(avatarUrl2, myHolder.mTvAvatar2, consumerMessage.getMessageThread().getUserInThread().get(0).getName(), myHolder.mImgAvatar2);

                avatarUrl3 = consumerMessage.getMessageThread().getUserInThread().get(1).getAvatar();
                checkAvatar(avatarUrl3, myHolder.mTvAvatar3, consumerMessage.getMessageThread().getUserInThread().get(1).getName(), myHolder.mImgAvatar3);

                myHolder.mTvAvatar4.setVisibility(View.GONE);
            } else if(n > 2){
                avatarUrl2 = consumerMessage.getMessageThread().getUserInThread().get(0).getAvatar();
                checkAvatar(avatarUrl2, myHolder.mTvAvatar2, consumerMessage.getMessageThread().getUserInThread().get(0).getName(), myHolder.mImgAvatar2);

                avatarUrl3 = consumerMessage.getMessageThread().getUserInThread().get(1).getAvatar();
                checkAvatar(avatarUrl3, myHolder.mTvAvatar3, consumerMessage.getMessageThread().getUserInThread().get(1).getName(), myHolder.mImgAvatar3);

                myHolder.mTvAvatar4.setText("+" + (n - 3));
                myHolder.mTvAvatar4.setVisibility(View.VISIBLE);
            }

            myHolder.mLayoutGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ConversationActivity.class);
                    intent.putExtra("ID_THREAD", consumerMessage.getMessageThread().getId());
                    intent.putExtra("CREATE_DATETIME_THREAD", consumerMessage.getMessageThread().getCreatedDatetime());
                    intent.putExtra("MODIFIED_DATETIME_THREAD", consumerMessage.getMessageThread().getModifiedDatetime());
                    intent.putExtra("CREATE_BY_THREAD", consumerMessage.getMessageThread().getModifiedDatetime());
                    intent.putExtra("MODIFIED_BY_THREAD", consumerMessage.getMessageThread().getModifiedBy());
                    if(consumerMessage.getMessageThread().getName() != null){
                        intent.putExtra("NAME_THREAD", consumerMessage.getMessageThread().getName());
                    }else{
                        intent.putExtra("NAME_THREAD", getThreadName(consumerMessage.getMessageThread().getUserInThread()));
                    }
                    intent.putExtra("DATA_THREAD", consumerMessage.getMessageThread().getData());
                    mContext.startActivity(intent);
                }
            });

            myHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.deleteThread(position, consumerMessage.getMessageThread().getId());
                }
            });
        }
    }

    private void checkAvatar(String avatarUrl, TextView mTvAvatar1, String name, ImageView avatar){
        if (avatarUrl != null) {
            String[] s = avatarUrl.split("/");
            //http://app.homecaravan.net/themes/theme20/img/avatar_default.jpg
            if (s[s.length - 1].equals("avatar_default.jpg")) {
                mTvAvatar1.setText(getFirstCharacter(name));
                mTvAvatar1.setVisibility(View.VISIBLE);
            } else {
                picasso.load(avatarUrl).fit().centerCrop().placeholder(R.drawable.avatar_default).into(avatar);
                mTvAvatar1.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mArrMessage.get(position).getType().equalsIgnoreCase("personal")) {
            return VIEW_MESSAGE_PERSONAL;
        } else {
            return VIEW_MESSAGE_GROUP;
        }
    }

    @Override
    public int getItemCount() {
        return mArrMessage.size();
    }

    public class MessagePersonalHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.slayoutItem)
        SwipeLayout mSlayoutItem;
        @Bind(R.id.imgFavorite)
        ImageView mImgFavorite;
        @Bind(R.id.imgDelete)
        ImageView mImgDelete;
        @Bind(R.id.layoutPersonal)
        LinearLayout mLayoutPersonal;
        @Bind(R.id.imgAvatarPersonal)
        RoundedImageView mImgAvatarPersonal;
        @Bind(R.id.vStatus)
        View mVStatus;
        @Bind(R.id.tvMessageName)
        TextView mTvMessageName;
        @Bind(R.id.tvLastMessage)
        TextView mTvLastMessage;
        @Bind(R.id.tvMessageLastTime)
        TextView mTvMessageLastTime;
        @Bind(R.id.vStatusConversation)
        View mVStatusConversation;
        @Bind(R.id.tvAvatarPersonal)
        TextView mTvAvatarPersonal;

        private MessagePersonalHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSlayoutItem.setShowMode(SwipeLayout.ShowMode.LayDown);
            mSlayoutItem.addDrag(SwipeLayout.DragEdge.Right, mSlayoutItem.findViewWithTag("SwipeLayoutAction"));
        }
    }

    public class MessageGroupHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.slayoutItem)
        SwipeLayout mSlayoutItem;
        @Bind(R.id.imgFavorite)
        ImageView mImgFavorite;
        @Bind(R.id.imgDelete)
        ImageView mImgDelete;
        @Bind(R.id.layoutGroup)
        LinearLayout mLayoutGroup;
        @Bind(R.id.imgAvatar1)
        CircleImageView mImgAvatar1;
        @Bind(R.id.imgAvatar2)
        CircleImageView mImgAvatar2;
        @Bind(R.id.imgAvatar3)
        CircleImageView mImgAvatar3;
        @Bind(R.id.tvAvatar1)
        TextView mTvAvatar1;
        @Bind(R.id.tvAvatar2)
        TextView mTvAvatar2;
        @Bind(R.id.tvAvatar3)
        TextView mTvAvatar3;
        @Bind(R.id.tvAvatar4)
        TextView mTvAvatar4;
        @Bind(R.id.tvMessageName)
        TextView mTvMessageName;
        @Bind(R.id.tvLastMessage)
        TextView mTvLastMessage;
        @Bind(R.id.tvMessageLastTime)
        TextView mTvMessageLastTime;

        private MessageGroupHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSlayoutItem.setShowMode(SwipeLayout.ShowMode.LayDown);
            mSlayoutItem.addDrag(SwipeLayout.DragEdge.Right, mSlayoutItem.findViewWithTag("SwipeLayoutAction"));
        }
    }


    public class TimeLineHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tvTimeLine)
        TextView mTvTimeLine;

        private TimeLineHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String getTime(String time) {
        try {
            long timeStamp = Long.parseLong(time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
//            TimeZone tz = TimeZone.getDefault();
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            timeStamp /= 1000;
            SimpleDateFormat sdf;
            Date currenTimeZone = calendar.getTime();
            if (timeStamp >= timeStampStartOfToday) {
                return sdfHHMMA.format(currenTimeZone);
            } else {
                return sdfDDMMM.format(currenTimeZone);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getThreadName(RealmList<MessageUserData> arrUserInThread) {

        int n = arrUserInThread.size();

        if (n == 0) {
            return "Message title";
        }

        if (n == 1) {
            return arrUserInThread.get(0).getName();
        }

        String groupName = arrUserInThread.get(0).getName();
        for (int i = 1; i < n; i++) {
            groupName += ", " + arrUserInThread.get(i).getName();
        }

        return groupName;
    }

    private String getFirstCharacter(String name) {
        if (name == null || name.isEmpty())
            return name;
        String[] s = name.split(" ");
        String charName = "";
        int i = 0;
        for (String value : s) {
            charName += value.charAt(0);
            if(i++ == 1){
                break;
            }
        }
        return charName.toUpperCase();
    }
}

