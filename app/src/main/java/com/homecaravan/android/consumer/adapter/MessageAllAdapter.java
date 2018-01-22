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
import com.homecaravan.android.consumer.model.message.Mapping;
import com.homecaravan.android.consumer.model.message.MessageItem;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageUser;
import com.homecaravan.android.consumer.model.message.SkeletonConversation;
import com.homecaravan.android.consumer.model.message.SkeletonMessageThread;
import com.homecaravan.android.consumer.utils.MessageUtils;
import com.homecaravan.android.consumer.utils.TextUtils;
import com.homecaravan.android.ui.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;


/**
 * Created by Anh Dao on 8/31/2017.
 * Thread List Adapter
 */

public class MessageAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_MESSAGE_SINGLE = 1;
    private final int VIEW_MESSAGE_GROUP = 2;
    private Long timeStampCurrentOfToday = System.currentTimeMillis() / 1000;
    private Long timeStampStartOfToday = (timeStampCurrentOfToday / 86400) * 86400;
    private Context mContext;
    private ArrayList<MessageThread> mArrThread;

    private Picasso picasso;
    private IMessageThreadAction mListener;

    public MessageAllAdapter(Context mContext, ArrayList<MessageThread> mArrThread, Picasso picasso,
                             IMessageThreadAction mListener) {
        this.mContext = mContext;
        this.mArrThread = mArrThread;
        this.picasso = picasso;
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_MESSAGE_SINGLE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_personal_item, parent, false);
            return new MessagePersonalHolder(v);
        } else if (viewType == VIEW_MESSAGE_GROUP) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_group_item, parent, false);
            return new MessageGroupHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MessageThread thread = mArrThread.get(position);

        if (holder instanceof MessagePersonalHolder) {
            MessagePersonalHolder myHolder = (MessagePersonalHolder) holder;

            myHolder.mTvMessageLastTime.setText(TextUtils.getLastTime(thread.getModifiedDatetime(), timeStampStartOfToday));

            if (thread.getMessageThreadView() != null) {
                String lastMessage = thread.getMessageThreadView().getContent();
                if (lastMessage != null) {
                    switch (thread.getMessageThreadView().getType()) {
                        case MessageItem.TYPE_IMAGE:
                            myHolder.mTvLastMessage.setText(mContext.getString(R.string.sent_a_photo));
                            break;
                        case MessageItem.TYPE_TEXT:
                        case MessageItem.TYPE_INLINE:
                            myHolder.mTvLastMessage.setText(lastMessage);
                            break;
                        default:
                            myHolder.mTvLastMessage.setText(null);
                            break;
                    }
                } else {
                    myHolder.mTvLastMessage.setText(mContext.getString(R.string.start_the_conversation));
                }
            } else {
                myHolder.mTvLastMessage.setText(mContext.getString(R.string.start_the_conversation));
            }

            int positionI = -1;
            if (thread.getMappings() != null) {
                int n = thread.getMappings().size();
                for (int i = 0; i < n; i++) {
                    if (thread.getMappings().get(i).getId()
                            .equals(MessageUser.getInstance().getData().getId())) {
                        if (thread.getMappings().get(i).ismNew()) {
                            myHolder.mVStatusConversation.setVisibility(View.VISIBLE);
                            myHolder.mLayoutPersonal.setBackground(mContext.getDrawable(R.drawable.bg_message_thread_unread_item));
                        } else {
                            myHolder.mVStatusConversation.setVisibility(View.GONE);
                            myHolder.mLayoutPersonal.setBackground(mContext.getDrawable(R.drawable.bg_message_thread_item));
                        }
                        positionI = i;
                    }
                }
            }

            final int positionUnRead = positionI;

            String nameParticipants;
            String avatarParticipants;

            if (!thread.getUserInThread().get(0).getId()
                    .equals(MessageUser.getInstance().getData().getId())) {
                nameParticipants = thread.getUserInThread().get(0).getName();
                avatarParticipants = thread.getUserInThread().get(0).getAvatar() != null ? thread.getUserInThread().get(1).getName() : null;
            } else {
                nameParticipants = thread.getUserInThread().get(1).getName();
                avatarParticipants = thread.getUserInThread().get(1).getAvatar() != null ? thread.getUserInThread().get(1).getName() : null;
            }

            myHolder.mTvMessageName.setText(nameParticipants);
            MessageUtils.checkAvatar(avatarParticipants, myHolder.mTvAvatarPersonal, nameParticipants, myHolder.mImgAvatarPersonal, picasso);

            final String nameThread = nameParticipants;

            myHolder.mLayoutPersonal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ConversationActivity.class);
                    intent.putExtra("NAME_THREAD", nameThread);
                    String timeStamp = String.valueOf(System.currentTimeMillis());

                    if (mArrThread.get(position).getMappings() != null
                            && mArrThread.get(position).getMappings().size() != 0
                            && positionUnRead != -1) {
                        mArrThread.get(position).getMappings().get(positionUnRead).setmNew(false);
                        mArrThread.get(position).getMappings().get(positionUnRead).setTime(timeStamp);
                    } else {
                        Mapping map = new Mapping();
                        map.setId(MessageUser.getInstance().getData().getId());
                        map.setTime(timeStamp);
                        map.setmNew(false);
                        RealmList<Mapping> mArrMapping = new RealmList<>();
                        mArrMapping.add(map);
                        mArrThread.get(position).setMappings(mArrMapping);
                    }
                    mListener.unRead(position, thread.getId(), timeStamp);
                    SkeletonMessageThread.getInstance().getData().clear();
                    SkeletonMessageThread.getInstance().getData().addAll(mArrThread);
                    SkeletonConversation.getInstance().clear();
                    SkeletonConversation.getInstance().setData(thread);
                    mContext.startActivity(intent);
                }
            });

            myHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.deleteThread(position, thread.getId());
                }
            });

        } else {
            MessageGroupHolder myHolder = (MessageGroupHolder) holder;
            final String nameThread;
            if (thread.getName() != null
                    && !thread.getName().isEmpty()) {
                nameThread = thread.getName();
                myHolder.mTvMessageName.setText(nameThread);
            } else {
                nameThread = TextUtils.getThreadName(thread.getUserInThread());
                myHolder.mTvMessageName.setText(nameThread);
            }
            myHolder.mTvMessageLastTime.setText(TextUtils.getLastTime(thread.getModifiedDatetime(), timeStampStartOfToday));

            if (thread.getMessageThreadView() != null) {
                String lastMessage = thread.getMessageThreadView().getContent();
                if (lastMessage != null) {
                    switch (thread.getMessageThreadView().getType()) {
                        case MessageItem.TYPE_IMAGE:
                            myHolder.mTvLastMessage.setText(mContext.getString(R.string.sent_a_photo));
                            break;
                        case MessageItem.TYPE_TEXT:
                        case MessageItem.TYPE_INLINE:
                            myHolder.mTvLastMessage.setText(lastMessage);
                            break;
                        default:
                            myHolder.mTvLastMessage.setText(null);
                            break;
                    }
                } else {
                    myHolder.mTvLastMessage.setText(mContext.getString(R.string.start_the_conversation));
                }
            } else {
                myHolder.mTvLastMessage.setText(mContext.getString(R.string.start_the_conversation));
            }

            int positionI = -1;
            if (thread.getMappings() != null) {
                int n = thread.getMappings().size();
                for (int i = 0; i < n; i++) {
                    if (thread.getMappings().get(i).getId()
                            .equals(MessageUser.getInstance().getData().getId())) {
                        if (thread.getMappings().get(i).ismNew()) {
                            myHolder.mVStatusConversation.setVisibility(View.VISIBLE);
                            myHolder.mLayoutGroup.setBackground(mContext.getDrawable(R.drawable.bg_message_thread_unread_item));
                        } else {
                            myHolder.mVStatusConversation.setVisibility(View.GONE);
                            myHolder.mLayoutGroup.setBackground(mContext.getDrawable(R.drawable.bg_message_thread_item));
                        }
                        positionI = i;
                    }
                }
            }

            final int positionUnRead = positionI;

            String avatar, name;
            int n = thread.getUserInThread().size();

            switch (n) {
                case 1:
                    avatar = thread.getUserInThread().get(0).getAvatar();
                    name = thread.getUserInThread().get(0).getName();
                    MessageUtils.checkAvatar(avatar, myHolder.mTvAvatar1, name, myHolder.mImgAvatar1, picasso);
                    myHolder.mTvAvatar1.setVisibility(View.GONE);
                    myHolder.mImgAvatar1.setVisibility(View.VISIBLE);
                    myHolder.mTvAvatar2.setVisibility(View.GONE);
                    myHolder.mImgAvatar2.setVisibility(View.INVISIBLE);
                    myHolder.mTvAvatar3.setVisibility(View.GONE);
                    myHolder.mImgAvatar3.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    avatar = thread.getUserInThread().get(0).getAvatar();
                    name = thread.getUserInThread().get(0).getName();
                    MessageUtils.checkAvatar(avatar, myHolder.mTvAvatar1, name, myHolder.mImgAvatar1, picasso);
                    avatar = thread.getUserInThread().get(1).getAvatar();
                    name = thread.getUserInThread().get(1).getName();
                    MessageUtils.checkAvatar(avatar, myHolder.mTvAvatar2, name, myHolder.mImgAvatar2, picasso);
                    myHolder.mTvAvatar1.setVisibility(View.GONE);
                    myHolder.mImgAvatar1.setVisibility(View.VISIBLE);
                    myHolder.mTvAvatar2.setVisibility(View.GONE);
                    myHolder.mImgAvatar2.setVisibility(View.VISIBLE);
                    myHolder.mTvAvatar3.setVisibility(View.GONE);
                    myHolder.mImgAvatar3.setVisibility(View.INVISIBLE);
                    break;
                default:
                    avatar = thread.getUserInThread().get(0).getAvatar();
                    name = thread.getUserInThread().get(0).getName();
                    MessageUtils.checkAvatar(avatar, myHolder.mTvAvatar1, name, myHolder.mImgAvatar1, picasso);
                    avatar = thread.getUserInThread().get(1).getAvatar();
                    name = thread.getUserInThread().get(1).getName();
                    MessageUtils.checkAvatar(avatar, myHolder.mTvAvatar2, name, myHolder.mImgAvatar2, picasso);
                    avatar = thread.getUserInThread().get(2).getAvatar();
                    name = thread.getUserInThread().get(2).getName();
                    MessageUtils.checkAvatar(avatar, myHolder.mTvAvatar3, name, myHolder.mImgAvatar3, picasso);
                    myHolder.mTvAvatar1.setVisibility(View.GONE);
                    myHolder.mImgAvatar1.setVisibility(View.VISIBLE);
                    myHolder.mTvAvatar2.setVisibility(View.GONE);
                    myHolder.mImgAvatar2.setVisibility(View.VISIBLE);
                    myHolder.mTvAvatar3.setVisibility(View.GONE);
                    myHolder.mImgAvatar3.setVisibility(View.VISIBLE);
                    break;
            }

            if (n > 3) {
                myHolder.mTvAvatar4.setText(String.format(mContext.getString(R.string.thread_more_participants), n));
                myHolder.mTvAvatar4.setVisibility(View.VISIBLE);
            } else {
                myHolder.mTvAvatar4.setVisibility(View.GONE);
            }

            myHolder.mLayoutGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ConversationActivity.class);
                    intent.putExtra("NAME_THREAD", nameThread);
                    String timeStamp = String.valueOf(System.currentTimeMillis());

                    if (mArrThread.get(position).getMappings() != null
                            && mArrThread.get(position).getMappings().size() != 0
                            && positionUnRead != -1) {
                        mArrThread.get(position).getMappings().get(positionUnRead).setmNew(false);
                        mArrThread.get(position).getMappings().get(positionUnRead).setTime(timeStamp);
                    } else {
                        Mapping map = new Mapping();
                        map.setId(MessageUser.getInstance().getData().getId());
                        map.setTime(timeStamp);
                        map.setmNew(false);
                        RealmList<Mapping> mArrMapping = new RealmList<>();
                        mArrMapping.add(map);
                        mArrThread.get(position).setMappings(mArrMapping);
                    }
                    mListener.unRead(position, thread.getId(), timeStamp);
                    SkeletonMessageThread.getInstance().getData().clear();
                    SkeletonMessageThread.getInstance().getData().addAll(mArrThread);
                    SkeletonConversation.getInstance().clear();
                    SkeletonConversation.getInstance().setData(thread);
                    mContext.startActivity(intent);
                }
            });

            myHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.deleteThread(position, thread.getId());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mArrThread.get(position).getType().equalsIgnoreCase(MessageThread.TYPE_SINGLE)) {
            return VIEW_MESSAGE_SINGLE;
        } else if (mArrThread.get(position).getType().equalsIgnoreCase(MessageThread.TYPE_GROUP)) {
            return VIEW_MESSAGE_GROUP;
        } else {
            return -1;
        }
    }

    @Override
    public int getItemCount() {
        return mArrThread.size();
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
        @Bind(R.id.vStatusConversation)
        View mVStatusConversation;

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

}

