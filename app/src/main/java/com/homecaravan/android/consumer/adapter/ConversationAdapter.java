package com.homecaravan.android.consumer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ViewUserProfileConsumerActivity;
import com.homecaravan.android.consumer.model.message.MessageItem;
import com.homecaravan.android.consumer.utils.Convert;
import com.homecaravan.android.consumer.utils.TextUtils;
import com.homecaravan.android.consumer.widget.ImagePopup;
import com.homecaravan.android.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.rockerhieu.emojicon.EmojiconTextView;

/**
 * Created by Anh Dao on 9/5/2017.
 */
public class ConversationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_FROM = 2;
    private final int VIEW_TIME = 3;
    private Long timeStampCurrentOfToday = System.currentTimeMillis() / 1000;
    private Long timeStampStartOfToday = (timeStampCurrentOfToday / 86400) * 86400;
    private HashMap<Integer, ProgressBar> hashMapPbProgressBar;
    private Context mContext;
    private ArrayList<MessageItem> mArrConsumerMessages;
    private final OnItemClickListener mListener;
    private int dp8;
    private int dp12;
    private int dp16;
    private int dp22;
    private Picasso mPicasso;


    public ConversationAdapter(Context mContext, ArrayList<MessageItem> mArrConsumerMessages, OnItemClickListener mListener) {
        this.mContext = mContext;
        this.mArrConsumerMessages = mArrConsumerMessages;
        this.hashMapPbProgressBar = new HashMap<>();
        this.mListener = mListener;
        dp8 = Convert.dpToPx(8, mContext);
        dp12 = Convert.dpToPx(12, mContext);
        dp16 = Convert.dpToPx(16, mContext);
        dp22 = Convert.dpToPx(22, mContext);
        mPicasso = HomeCaravanApplication.getInstance().buildPicasso();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
            return new MessageHolder(v);
        } else if (viewType == VIEW_FROM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_from_item, parent, false);
            return new MessageFromHolder(v);
        } else if (viewType == VIEW_TIME) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_time_item, parent, false);
            return new TimeViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        MessageItem message = mArrConsumerMessages.get(position); // tn hiện tại

        if (holder instanceof TimeViewHolder) {
            TimeViewHolder timeLineHolder = (TimeViewHolder) holder;

            if (position != mArrConsumerMessages.size() - 1) {
                timeLineHolder.mTvTime.setText(message.getDate());
                timeLineHolder.mLayoutItem.setVisibility(View.VISIBLE);
            } else {
                timeLineHolder.mLayoutItem.setVisibility(View.GONE);
            }

        } else {
            if (message == null || MessageItem.STATUS_DELETED.equals(message.getStatus())) {
                if (holder instanceof MessageHolder) {
                    final MessageHolder myHolder = (MessageHolder) holder;
                    myHolder.mLayoutItem.setVisibility(View.GONE);
                } else if (holder instanceof MessageFromHolder) {
                    final MessageFromHolder myHolder = (MessageFromHolder) holder;
                    myHolder.mLayoutItem.setVisibility(View.GONE);
                }
                return;
            }
            MessageItem preMessage;
            MessageItem nextMessage = null;

            String preSender, nextSender;
            String currentSender = message.getMessageThreadView().getId();
            if (currentSender == null) {
                currentSender = message.getCreatedBy();
            }

            if (currentSender == null) {
                if (holder instanceof MessageHolder) {
                    final MessageHolder myHolder = (MessageHolder) holder;
                    myHolder.mLayoutItem.setVisibility(View.GONE);
                } else if (holder instanceof MessageFromHolder) {
                    final MessageFromHolder myHolder = (MessageFromHolder) holder;
                    myHolder.mLayoutItem.setVisibility(View.GONE);
                }
                return;
            }
            if (position == 0) {
                preSender = "";
            } else {
                preMessage = mArrConsumerMessages.get(position - 1); //tn trước tn hiện tại
                if (preMessage.getId() == null) {
                    preSender = "";
                } else {
                    preSender = preMessage.getMessageThreadView().getId() != null ?
                            preMessage.getCreatedBy() : preMessage.getMessageThreadView().getId();
                }
            }
            if (position == getItemCount() - 1) {
                nextSender = "";
            } else {
                nextMessage = mArrConsumerMessages.get(position + 1); // tn sau tn hiện tại
                if (nextMessage.getId() == null) {
                    nextSender = "";
                } else {
                    nextSender = nextMessage.getMessageThreadView().getId() != null ?
                            nextMessage.getCreatedBy() : nextMessage.getMessageThreadView().getId();
                }
            }

            if (holder instanceof MessageHolder) {
                final MessageHolder myHolder = (MessageHolder) holder;

                if (MessageItem.TYPE_TEXT.equals(message.getType())) {
                    boolean isJustEmoji = checkJustEmoji(message.getContent());

                    if (isJustEmoji) {
                        myHolder.mTvMessage.setEmojiconSize(dp22);
                        myHolder.mLayoutMessage.setPadding(dp8, dp8, dp8, dp8);
                    } else {
                        myHolder.mTvMessage.setEmojiconSize(dp16);
                        myHolder.mLayoutMessage.setPadding(dp12, dp12, dp12, dp8);
                    }
                    myHolder.mTvMessage.setText(message.getContent());
                    myHolder.mTvMessage.setVisibility(View.VISIBLE);
                    myHolder.mLayoutMessage.setVisibility(View.VISIBLE);
                    myHolder.mImgMessage.setVisibility(View.GONE);

                } else if (MessageItem.TYPE_IMAGE.equals(message.getType())) {
                    myHolder.mLayoutMessage.setVisibility(View.GONE);
                    myHolder.mImgMessage.setVisibility(View.VISIBLE);
                    if (message.getImage() != null) {
                        mPicasso.load(message.getImage()).fit().centerCrop()
                                .placeholder(R.drawable.avatar_default).into(myHolder.mImgMessage);
                    } else if (message.getTempImage() != null) {
                        Glide.with(mContext.getApplicationContext()).load(message.getTempImage()).asBitmap().fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.no_image_b).dontAnimate().into(myHolder.mImgMessage);
                    } else {
                        mPicasso.load(R.drawable.no_image_b).fit().centerCrop()
                                .placeholder(R.drawable.avatar_default).into(myHolder.mImgMessage);
                    }
                } else if (MessageItem.TYPE_FILE.equals(message.getType())) {
                }

                if (!currentSender.equals(preSender) && !currentSender.equals(nextSender)) { //single message
                    mPicasso.load(message.getMessageThreadView().getPhoto()).fit().centerCrop()
                            .placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatar);

                    myHolder.mTvName.setText(message.getMessageThreadView().getName());
                    myHolder.mTvChatTime.setText(TextUtils.getLastTime(message.getCreatedDatetime(), timeStampStartOfToday));
                    myHolder.mTvChatTime.setVisibility(View.VISIBLE);
//                    myHolder.mVStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.dot_green_online));
                    myHolder.mLayoutChatName.setVisibility(View.VISIBLE);
                    myHolder.mLayoutAvatar.setVisibility(View.VISIBLE);
                } else if (!currentSender.equals(preSender) && currentSender.equals(nextSender)) { //top message
                    mPicasso.load(message.getMessageThreadView().getPhoto()).fit().centerCrop()
                            .placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatar);

                    myHolder.mTvName.setText(message.getMessageThreadView().getName());
//                    myHolder.mVStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.dot_green_online));
                    myHolder.mLayoutChatName.setVisibility(View.VISIBLE);
                    myHolder.mLayoutAvatar.setVisibility(View.VISIBLE);

                    long currentMessageTime = Long.parseLong(message.getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(TextUtils.getLastTime(currentMessageTime, timeStampStartOfToday));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else if (currentSender.equals(preSender) && currentSender.equals(nextSender)) { //middle message
                    myHolder.mLayoutChatName.setVisibility(View.GONE);
                    myHolder.mLayoutAvatar.setVisibility(View.INVISIBLE);

                    long currentMessageTime = Long.parseLong(message.getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(TextUtils.getLastTime(currentMessageTime, timeStampStartOfToday));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else { //bottom message
                    myHolder.mTvChatTime.setText(TextUtils.getLastTime(message.getCreatedDatetime(), timeStampStartOfToday));
                    myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    myHolder.mLayoutChatName.setVisibility(View.GONE);
                    myHolder.mLayoutAvatar.setVisibility(View.INVISIBLE);
                }

                myHolder.mImgMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ImagePopup imagePopup = new ImagePopup(mContext);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setImageOnClickClose(true);
                        imagePopup.initiatePopup(myHolder.mImgMessage.getDrawable());
                    }
                });

                myHolder.mImgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.startActivity(new Intent(mContext, ViewUserProfileConsumerActivity.class));
                    }
                });

                myHolder.bind(position, message, mListener, myHolder.mTvMessage.getDrawingCache());

            } else if (holder instanceof MessageFromHolder) {
                final MessageFromHolder myHolder = (MessageFromHolder) holder;

                if (MessageItem.TYPE_TEXT.equals(message.getType())) {
                    boolean isJustEmoji = checkJustEmoji(message.getContent());

                    if (isJustEmoji) {
                        myHolder.mTvMessage.setEmojiconSize(dp22);
                        myHolder.mLayoutMessage.setPadding(dp8, dp8, dp8, dp8);
                    } else {
                        myHolder.mTvMessage.setEmojiconSize(dp16);
                        myHolder.mLayoutMessage.setPadding(dp12, dp12, dp12, dp8);
                    }
                    myHolder.mTvMessage.setText(message.getContent());
                    myHolder.mTvMessage.setVisibility(View.VISIBLE);
                    myHolder.mLayoutMessage.setVisibility(View.VISIBLE);
                    myHolder.mImgMessage.setVisibility(View.GONE);
                } else if (MessageItem.TYPE_IMAGE.equals(message.getType())) {
                    myHolder.mTvMessage.setVisibility(View.GONE);
                    myHolder.mLayoutMessage.setVisibility(View.GONE);
                    myHolder.mImgMessage.setVisibility(View.VISIBLE);
                    if (message.getImage() != null) {
                        mPicasso.load(message.getImage()).fit().centerCrop()
                                .placeholder(R.drawable.no_image_b).into(myHolder.mImgMessage);
                    } else if (message.getTempImage() != null) {
                        Glide.with(mContext.getApplicationContext()).load(message.getTempImage()).asBitmap().fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.no_image_b).dontAnimate().into(myHolder.mImgMessage);
                        if (!message.isHasAppearedOnce()) {
                            myHolder.mPbUploadImage.setVisibility(View.VISIBLE);
                            hashMapPbProgressBar.put(position, myHolder.mPbUploadImage);
                            message.setHasAppearedOnce(true);
                        }
                    } else {
                        mPicasso.load(R.drawable.no_image_b).fit().centerCrop()
                                .placeholder(R.drawable.avatar_default).into(myHolder.mImgMessage);
                    }
                } else if (MessageItem.TYPE_FILE.equals(message.getType())) {
                }

                if (!currentSender.equals(preSender) && !currentSender.equals(nextSender)) { //single message
                    myHolder.mTvChatTime.setText(TextUtils.getLastTime(message.getCreatedDatetime(), timeStampStartOfToday));
                    myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                } else if (!currentSender.equals(preSender) && currentSender.equals(nextSender)) { //top message
                    long currentMessageTime = Long.parseLong(message.getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(TextUtils.getLastTime(currentMessageTime, timeStampStartOfToday));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else if (currentSender.equals(preSender) && currentSender.equals(nextSender)) { //middle message
                    long currentMessageTime = Long.parseLong(message.getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(TextUtils.getLastTime(currentMessageTime, timeStampStartOfToday));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else { //bottom message
                    myHolder.mTvChatTime.setText(TextUtils.getLastTime(message.getCreatedDatetime(), timeStampStartOfToday));
                    myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                }

                myHolder.mImgMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ImagePopup imagePopup = new ImagePopup(mContext);
                        imagePopup.setHideCloseIcon(true);
                        imagePopup.setImageOnClickClose(true);
                        imagePopup.initiatePopup(myHolder.mImgMessage.getDrawable());
                    }
                });

                myHolder.bind(position, message, mListener, myHolder.mTvMessage.getDrawingCache());
            }
        }
    }

    public void uploadImageComplete(int position) {
        for (Map.Entry<Integer, ProgressBar> entry : hashMapPbProgressBar.entrySet()) {
            int key = entry.getKey();
            if (key == position) {
                ProgressBar progressBar = entry.getValue();
                progressBar.setVisibility(View.GONE);
                hashMapPbProgressBar.remove(entry);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mArrConsumerMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mArrConsumerMessages.get(position).getTypeMessage().equals(MessageItem.TYPE_ITEM)) {
            return VIEW_ITEM;
        } else if (mArrConsumerMessages.get(position).getTypeMessage().equals(MessageItem.TYPE_FROM_ITEM)) {
            return VIEW_FROM;
        } else if (mArrConsumerMessages.get(position).getTypeMessage().equals(MessageItem.TYPE_TIME_LINE)) {
            return VIEW_TIME;
        }
        return -1;
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.layoutAvatar)
        RelativeLayout mLayoutAvatar;
        @Bind(R.id.layoutMessage)
        LinearLayout mLayoutMessage;
        @Bind(R.id.imgAvatar)
        CircleImageView mImgAvatar;
        @Bind(R.id.vStatus)
        View mVStatus;
        @Bind(R.id.layoutChatName)
        RelativeLayout mLayoutChatName;
        @Bind(R.id.tvName)
        TextView mTvName;
        @Bind(R.id.tvChatTime)
        TextView mTvChatTime;
        @Bind(R.id.tvMessage)
        EmojiconTextView mTvMessage;
        @Bind(R.id.imgMessage)
        ImageView mImgMessage;
        @Bind(R.id.vSpace)
        View mVSpace;


        private MessageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final int position, final MessageItem item, final OnItemClickListener listener, final Bitmap bitmap) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position, item);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(position, item, bitmap);
                    return true;
                }
            });

            mImgMessage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(position, item, bitmap);
                    return true;
                }
            });
        }
    }

    public class MessageFromHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.layoutMessage)
        LinearLayout mLayoutMessage;
        //        @Bind(R.id.tvName)
//        TextView mTvName;
        @Bind(R.id.tvChatTime)
        TextView mTvChatTime;
        @Bind(R.id.tvMessage)
        EmojiconTextView mTvMessage;
        @Bind(R.id.imgMessage)
        ImageView mImgMessage;
        @Bind(R.id.vSpace)
        View mVSpace;
        @Bind(R.id.pbUploadImage)
        ProgressBar mPbUploadImage;

        private MessageFromHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final int position, final MessageItem item, final OnItemClickListener listener, final Bitmap bitmap) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position, item);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(position, item, bitmap);
                    return true;
                }
            });

            mImgMessage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemLongClick(position, item, bitmap);
                    return true;
                }
            });
        }
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.layoutItem)
        LinearLayout mLayoutItem;
        @Bind(R.id.tvTime)
        TextView mTvTime;

        private TimeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private boolean checkJustEmoji(String messageContent) {
        int n = messageContent.length();
        boolean isJustEmoji = false;
        for (int i = 0; i < n; i++) {
            int type = Character.getType(messageContent.charAt(i));
            if (type == Character.LOWERCASE_LETTER || type == Character.UPPERCASE_LETTER
                    || type == Character.DECIMAL_DIGIT_NUMBER || type == Character.MATH_SYMBOL
                    || type == Character.OTHER_PUNCTUATION || type == Character.DASH_PUNCTUATION
                    || type == Character.END_PUNCTUATION || type == Character.START_PUNCTUATION
                    || type == Character.MODIFIER_SYMBOL || type == Character.CURRENCY_SYMBOL
                    || type == Character.CONNECTOR_PUNCTUATION || type == Character.OTHER_SYMBOL) {
                isJustEmoji = false;
                break;
            } else if (type == Character.SURROGATE) {
                isJustEmoji = true;
            }
        }
        return isJustEmoji;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, MessageItem item);

        void onItemLongClick(int position, MessageItem item, Bitmap bitmap);
    }
}
