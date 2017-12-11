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
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.activity.ViewUserProfileConsumerActivity;
import com.homecaravan.android.consumer.model.message.ConsumerMessages;
import com.homecaravan.android.consumer.utils.Convert;
import com.homecaravan.android.consumer.widget.ImagePopup;
import com.homecaravan.android.ui.CircleImageView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
    private ArrayList<ConsumerMessages> mArrConsumerMessages;
    private final OnItemClickListener mListener;
    private int dp8;
    private int dp12;
    private int dp16;
    private int dp26;
    private Picasso mPicasso;


    public ConversationAdapter(Context mContext, ArrayList<ConsumerMessages> mArrConsumerMessages, OnItemClickListener mListener) {
        this.mContext = mContext;
        this.mArrConsumerMessages = mArrConsumerMessages;
        this.hashMapPbProgressBar = new HashMap<>();
        this.mListener = mListener;
        dp8 = Convert.dpToPx(8, mContext);
        dp12 = Convert.dpToPx(12, mContext);
        dp16 = Convert.dpToPx(16, mContext);
        dp26 = Convert.dpToPx(26, mContext);
        mPicasso = HomeCaravanApplication.getInstance().buildPicasso();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_item, parent, false);
            vh = new MessageHolder(v);
        } else if (viewType == VIEW_FROM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_from_item, parent, false);
            vh = new MessageFromHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_time_item, parent, false);
            vh = new TimeViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ConsumerMessages message = mArrConsumerMessages.get(position); // tn hiện tại

        if (holder instanceof TimeViewHolder) {
            TimeViewHolder timeLineHolder = (TimeViewHolder) holder;

            if (position != mArrConsumerMessages.size() - 1) {
                timeLineHolder.mTvTime.setText(message.getDate());
                timeLineHolder.mLayoutItem.setVisibility(View.VISIBLE);
            } else {
                timeLineHolder.mLayoutItem.setVisibility(View.GONE);
            }

        } else {
            if (message.getMessageItem() == null || Constants.getInstance().MESSAGE_STATUS_DELETED.equals(message.getMessageItem().getStatus())) {
                if (holder instanceof MessageHolder) {
                    final MessageHolder myHolder = (MessageHolder) holder;
                    myHolder.mLayoutItem.setVisibility(View.GONE);
                } else if (holder instanceof MessageFromHolder) {
                    final MessageFromHolder myHolder = (MessageFromHolder) holder;
                    myHolder.mLayoutItem.setVisibility(View.GONE);
                }
                return;
            }
            ConsumerMessages preMessage;
            ConsumerMessages nextMessage = null;

            String preSender, nextSender;
            String currentSender = message.getMessageItem().getCreatedBy();
            if (currentSender == null) {
                currentSender = message.getMessageItem().getMessageThreadView().getId();
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
                if (preMessage.getMessageItem() == null) {
                    preSender = "";
                } else {
                    preSender = preMessage.getMessageItem().getCreatedBy();
                    if (preSender == null) {
                        preSender = preMessage.getMessageItem().getMessageThreadView().getId();
                    }
                }
            }
            if (position == getItemCount() - 1) {
                nextSender = "";
            } else {
                nextMessage = mArrConsumerMessages.get(position + 1); // tn sau tn hiện tại
                if (nextMessage.getMessageItem() == null) {
                    nextSender = "";
                } else {
                    nextSender = nextMessage.getMessageItem().getCreatedBy();
                    if (nextSender == null) {
                        nextSender = nextMessage.getMessageItem().getMessageThreadView().getId();
                    }
                }
            }

            if (holder instanceof MessageHolder) {
                final MessageHolder myHolder = (MessageHolder) holder;

                if (Constants.getInstance().MESSAGE_TYPE_TEXT.equals(message.getMessageItem().getType())) {
                    boolean isJustEmoji = checkJustEmoji(message.getMessageItem().getContent());

                    if (isJustEmoji) {
                        myHolder.mTvMessage.setEmojiconSize(dp26);
                        myHolder.mLayoutMessage.setPadding(dp8, dp8, dp8, dp8);
                    } else {
                        myHolder.mTvMessage.setEmojiconSize(dp16);
                        myHolder.mLayoutMessage.setPadding(dp12, dp12, dp12, dp8);
                    }
                    myHolder.mTvMessage.setText(message.getMessageItem().getContent());
                    myHolder.mTvMessage.setVisibility(View.VISIBLE);
                    myHolder.mLayoutMessage.setVisibility(View.VISIBLE);
                    myHolder.mImgMessage.setVisibility(View.GONE);

                } else if (Constants.getInstance().MESSAGE_TYPE_IMAGE.equals(message.getMessageItem().getType())) {
                    myHolder.mLayoutMessage.setVisibility(View.GONE);
                    myHolder.mImgMessage.setVisibility(View.VISIBLE);
                    if (message.getMessageItem().getImage() != null) {
                        mPicasso.load(message.getMessageItem().getImage()).fit().centerCrop()
                                .placeholder(R.drawable.avatar_default).into(myHolder.mImgMessage);
                    } else if (message.getMessageItem().getTempImage() != null) {
                        Glide.with(mContext.getApplicationContext()).load(message.getMessageItem().getTempImage()).asBitmap().fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.no_image_b).dontAnimate().into(myHolder.mImgMessage);
                    } else {
                        mPicasso.load(R.drawable.no_image_b).fit().centerCrop()
                                .placeholder(R.drawable.avatar_default).into(myHolder.mImgMessage);
                    }
                } else if (Constants.getInstance().MESSAGE_TYPE_FILE.equals(message.getMessageItem().getType())) {
                }

                if (!currentSender.equals(preSender) && !currentSender.equals(nextSender)) { //single message
                    mPicasso.load(message.getMessageItem().getMessageThreadView().getPhoto()).fit().centerCrop()
                            .placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatar);

                    myHolder.mTvName.setText(message.getMessageItem().getMessageThreadView().getName());
                    myHolder.mTvChatTime.setText(getTime(message.getMessageItem().getCreatedDatetime()));
                    myHolder.mTvChatTime.setVisibility(View.VISIBLE);
//                    myHolder.mVStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.dot_green_online));
                    myHolder.mLayoutChatName.setVisibility(View.VISIBLE);
                    myHolder.mLayoutAvatar.setVisibility(View.VISIBLE);
                } else if (!currentSender.equals(preSender) && currentSender.equals(nextSender)) { //top message
                    mPicasso.load(message.getMessageItem().getMessageThreadView().getPhoto()).fit().centerCrop()
                            .placeholder(R.drawable.avatar_default).into(myHolder.mImgAvatar);

                    myHolder.mTvName.setText(message.getMessageItem().getMessageThreadView().getName());
//                    myHolder.mVStatus.setBackground(ContextCompat.getDrawable(mContext, R.drawable.dot_green_online));
                    myHolder.mLayoutChatName.setVisibility(View.VISIBLE);
                    myHolder.mLayoutAvatar.setVisibility(View.VISIBLE);

                    long currentMessageTime = Long.parseLong(message.getMessageItem().getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getMessageItem().getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(getTime(currentMessageTime));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else if (currentSender.equals(preSender) && currentSender.equals(nextSender)) { //middle message
                    myHolder.mLayoutChatName.setVisibility(View.GONE);
                    myHolder.mLayoutAvatar.setVisibility(View.INVISIBLE);

                    long currentMessageTime = Long.parseLong(message.getMessageItem().getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getMessageItem().getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(getTime(currentMessageTime));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else { //bottom message
                    myHolder.mTvChatTime.setText(getTime(message.getMessageItem().getCreatedDatetime()));
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

                if (Constants.getInstance().MESSAGE_TYPE_TEXT.equals(message.getMessageItem().getType())) {
                    boolean isJustEmoji = checkJustEmoji(message.getMessageItem().getContent());

                    if (isJustEmoji) {
                        myHolder.mTvMessage.setEmojiconSize(dp26);
                        myHolder.mLayoutMessage.setPadding(dp8, dp8, dp8, dp8);
                    } else {
                        myHolder.mTvMessage.setEmojiconSize(dp16);
                        myHolder.mLayoutMessage.setPadding(dp12, dp12, dp12, dp8);
                    }
                    myHolder.mTvMessage.setText(message.getMessageItem().getContent());
                    myHolder.mTvMessage.setVisibility(View.VISIBLE);
                    myHolder.mLayoutMessage.setVisibility(View.VISIBLE);
                    myHolder.mImgMessage.setVisibility(View.GONE);
                } else if (Constants.getInstance().MESSAGE_TYPE_IMAGE.equals(message.getMessageItem().getType())) {
                    myHolder.mTvMessage.setVisibility(View.GONE);
                    myHolder.mLayoutMessage.setVisibility(View.GONE);
                    myHolder.mImgMessage.setVisibility(View.VISIBLE);
                    if (message.getMessageItem().getImage() != null) {
                        mPicasso.load(message.getMessageItem().getImage()).fit().centerCrop()
                                .placeholder(R.drawable.avatar_default).into(myHolder.mImgMessage);
                    } else if (message.getMessageItem().getTempImage() != null) {
                        Glide.with(mContext.getApplicationContext()).load(message.getMessageItem().getTempImage()).asBitmap().fitCenter()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.no_image_b).dontAnimate().into(myHolder.mImgMessage);
                        if (!message.getMessageItem().isHasAppearedOnce()) {
                            myHolder.mPbUploadImage.setVisibility(View.VISIBLE);
                            hashMapPbProgressBar.put(position, myHolder.mPbUploadImage);
                            message.getMessageItem().setHasAppearedOnce(true);
                        }
                    } else {
                        mPicasso.load(R.drawable.no_image_b).fit().centerCrop()
                                .placeholder(R.drawable.avatar_default).into(myHolder.mImgMessage);
                    }
                } else if (Constants.getInstance().MESSAGE_TYPE_FILE.equals(message.getMessageItem().getType())) {
                }

                if (!currentSender.equals(preSender) && !currentSender.equals(nextSender)) { //single message
                    myHolder.mTvChatTime.setText(getTime(message.getMessageItem().getCreatedDatetime()));
                    myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                } else if (!currentSender.equals(preSender) && currentSender.equals(nextSender)) { //top message
                    long currentMessageTime = Long.parseLong(message.getMessageItem().getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getMessageItem().getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(getTime(currentMessageTime));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else if (currentSender.equals(preSender) && currentSender.equals(nextSender)) { //middle message
                    long currentMessageTime = Long.parseLong(message.getMessageItem().getCreatedDatetime());
                    long nextMessageTime = Long.parseLong(nextMessage.getMessageItem().getCreatedDatetime());
                    if (nextMessageTime - currentMessageTime > 300000) {
                        myHolder.mTvChatTime.setText(getTime(currentMessageTime));
                        myHolder.mTvChatTime.setVisibility(View.VISIBLE);
                    } else {
                        myHolder.mTvChatTime.setVisibility(View.GONE);
                    }
                } else { //bottom message
                    myHolder.mTvChatTime.setText(getTime(message.getMessageItem().getCreatedDatetime()));
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
        if (mArrConsumerMessages.get(position).getType().equalsIgnoreCase(Constants.getInstance().MESSAGE_ITEM_TYPE_ITEM)) {
            return VIEW_ITEM;
        } else if (mArrConsumerMessages.get(position).getType().equalsIgnoreCase(Constants.getInstance().MESSAGE_ITEM_TYPE_FROM_ITEM)) {
            return VIEW_FROM;
        } else {
            return VIEW_TIME;
        }
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

        public void bind(final int position, final ConsumerMessages item, final OnItemClickListener listener, final Bitmap bitmap) {
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

        private void bind(final int position, final ConsumerMessages item, final OnItemClickListener listener, final Bitmap bitmap) {
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

    private String getTime(String time) {
        try {
            long timeStamp = Long.parseLong(time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
//            TimeZone tz = TimeZone.getDefault();
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));

            timeStamp /= 1000;
            SimpleDateFormat sdf;
            if (timeStamp >= timeStampStartOfToday) {
                sdf = new SimpleDateFormat("hh:mm a", Locale.US);
            } else {
                sdf = new SimpleDateFormat("dd MMM", Locale.US);
            }

            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getTime(long timeStamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timeStamp);
//            TimeZone tz = TimeZone.getDefault();
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));

            timeStamp /= 1000;
            SimpleDateFormat sdf;
            if (timeStamp >= timeStampStartOfToday) {
                sdf = new SimpleDateFormat("hh:mm a", Locale.US);
            } else {
                sdf = new SimpleDateFormat("dd MMM", Locale.US);
            }

            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
        void onItemClick(int position, ConsumerMessages item);

        void onItemLongClick(int position, ConsumerMessages item, Bitmap bitmap);
    }
}
