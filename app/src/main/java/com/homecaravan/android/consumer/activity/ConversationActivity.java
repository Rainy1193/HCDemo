package com.homecaravan.android.consumer.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.homecaravan.android.BuildConfig;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.adapter.ConversationAdapter;
import com.homecaravan.android.consumer.adapter.ConversationGroupMemberAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.message.IUploadImage;
import com.homecaravan.android.consumer.message.messagegetallmvp.IMessagesActionView;
import com.homecaravan.android.consumer.message.messagegetallmvp.MessagesActionPresenter;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.GetAllThreadPresenter;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.GetThreadPresenter;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.IGetAllThreadView;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.IGetThreadView;
import com.homecaravan.android.consumer.message.messageinviteuser.InviteIntoGroupPresenter;
import com.homecaravan.android.consumer.message.messageinviteuser.InviteIntoGroupView;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.message.ConsumerMessageAll;
import com.homecaravan.android.consumer.model.message.ConsumerMessages;
import com.homecaravan.android.consumer.model.message.MessageAddResponse;
import com.homecaravan.android.consumer.model.message.MessageItem;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageThreadView;
import com.homecaravan.android.consumer.model.message.MessageTyping;
import com.homecaravan.android.consumer.model.message.MessageUser;
import com.homecaravan.android.consumer.model.message.MessageUserData;
import com.homecaravan.android.consumer.model.message.SkeletonMessageThread;
import com.homecaravan.android.consumer.model.message.UploadObject;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.mydialog.MyDialog;
import com.kyleduo.switchbutton.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import io.github.rockerhieu.emojicon.EmojiconEditText;
import io.github.rockerhieu.emojicon.EmojiconGridFragment;
import io.github.rockerhieu.emojicon.EmojiconsFragment;
import io.github.rockerhieu.emojicon.emoji.Emojicon;
import io.realm.Realm;
import io.socket.emitter.Emitter;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConversationActivity extends BaseActivity implements IMessagesActionView,

        EmojiconGridFragment.OnEmojiconClickedListener,
        EmojiconsFragment.OnEmojiconBackspaceClickedListener,
        IGetThreadView,
        IGetAllThreadView,
        InviteIntoGroupView {

    private final String TAG = "DaoDiDem";
    private final int REQUEST_CONTACT_CODE = 79;
    private int mWidthPage;
    //    private ConversationListAgentAdapter mConversationListAgentAdapter;
    private ConversationAdapter mConversationAdapter;
    private ConversationGroupMemberAdapter mConversationGroupMemberAdapter;
    //    private CustomLayoutManager mCustomLayoutManager;
    private String sThreadId;
    private String sName;
    private String sCreateDatetime;
    private String sModifiedDatetime;
    private String sCreateBy;
    private String sModifiedBy;
    private String sDataThread;
    //    private int positionToSmoothScroll = -1;
//    private ArrayList<MessageThread> mArrMessageListThread = new ArrayList<>();
    private ArrayList<ConsumerMessages> mArrConsumerMessages = new ArrayList<>();
    private ArrayList<MessageUserData> mArrGroupUser = new ArrayList<>();

    private boolean mIsEditingGroupName;
    //    private boolean mAttackSmoothScroll;
    private boolean detailClicked;
    private boolean attachClicked;
    private boolean emojiClicked;
    private boolean attachImageClicked;
    private MessagesActionPresenter mMessagesActionPresenter;
    private GetAllThreadPresenter mGetAllThreadPresenter;

    private final int REQUEST_GALLERY = 1;
    private final int REQUEST_CAMERA = 2;
    private String currentPhotoPath;
    private Realm mRealm;

    private ArrayList<MessageTyping> mArrTyping = new ArrayList<>();
    private SharedPreferences mPrefs;

    @Bind(R.id.tvName)
    TextView mTvName;
    @Bind(R.id.rvMessage)
    RecyclerView mRvMessage;
    @Bind(R.id.layoutRv)
    RelativeLayout mLayoutRv;
    @Bind(R.id.imgAttach)
    ImageView mImgAttach;
    @Bind(R.id.imgEmoji)
    ImageView mImgEmoji;
    @Bind(R.id.edtMessage)
    EmojiconEditText mEdtMessage;
    @Bind(R.id.imgSend)
    ImageView mImgSend;
    @Bind(R.id.layoutConversation)
    RelativeLayout mLayoutConversation;
//    @Bind(R.id.viewArrow)
//    ViewArrow mViewArrow;
//    @Bind(R.id.rvConversationListAgent)
//    RecyclerView mRvConversationListAgent;
    @Bind(R.id.layoutAttach)
    LinearLayout mLayoutAttach;
    @Bind(R.id.tvQuickRespone1)
    TextView mTvQuickRespone1;
    @Bind(R.id.tvQuickRespone2)
    TextView mTvQuickRespone2;
    @Bind(R.id.tvQuickRespone3)
    TextView mTvQuickRespone3;
    @Bind(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;
    @Bind(R.id.pbLoadMessages)
    ProgressBar mPbLoadMessages;
    @Bind(R.id.layoutEmoji)
    LinearLayout mLayoutEmoji;

    @Bind(R.id.lnAttachImage)
    LinearLayout mLnAttachImage;
    @Bind(R.id.svQuickResponse)
    ScrollView mSvQuickResponse;
    @Bind(R.id.imgQuickRespone)
    ImageView mImgQuickRespone;
    @Bind(R.id.imgAttachImage)
    ImageView mImgAttachImage;

    @Bind(R.id.layoutDetail)
    ScrollView mLayoutDetail;
    @Bind(R.id.tvGroupName)
    TextView mTvGroupName;
    @Bind(R.id.tvGroupCount)
    TextView mTvGroupCount;
    @Bind(R.id.edtGroupName)
    EditText mEdtGroupName;
    @Bind(R.id.imgEditGroupName)
    ImageView mImgEditGroupName;
    @Bind(R.id.rvGroupMember)
    RecyclerView mRvGroupMember;
    @Bind(R.id.imgConversationDetail)
    ImageView mImgConversationDetail;

    @Bind(R.id.tvTyping)
    TextView mTvTyping;
    @Bind(R.id.sbThreadNotification)
    SwitchButton mSbThreadNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mMessagesActionPresenter = new MessagesActionPresenter(this);
        mGetAllThreadPresenter = new GetAllThreadPresenter(this);
        mImgSend.setEnabled(false);
        mRealm = HomeCaravanApplication.getInstance().getRealm();
        mPrefs = getSharedPreferences(Constants.getInstance().HOME_CARAVAN_CONSUMER, Context.MODE_PRIVATE);

        setupAdapter();

        if (HomeCaravanApplication.isNetAvailable(this) && HomeCaravanApplication.mLoginSocketSuccess) {
            checkIntent();
        } else {
            // TODO: 11/20/2017 save user in thread to realm
            mMessagesActionPresenter.getMessagesFromRealm(sThreadId, mRealm);
        }

        socketListening();

        checkSettings();

//        setupListAgentOfPersonalConversation();

//        setEmojiconFragment(false);

        checkArrMessage();

        addTextChangeListener();

        mLayoutConversation.post(new Runnable() {
            @Override
            public void run() {
                mWidthPage = mLayoutConversation.getWidth();
            }
        });

    }

    private void checkSettings() {
        if (!HomeCaravanApplication.mReceiverMessageNotification) {
            mSbThreadNotification.setChecked(false);
        } else {
            if (mPrefs != null) {
                List<String> mArr;
                String serialized = mPrefs.getString(Constants.getInstance().THREAD_ID_TURN_OFF_NOTIFICATION_LIST, null);
                if (serialized != null && !serialized.isEmpty()) {
                    mArr = Arrays.asList(TextUtils.split(serialized, ","));
                    for (String threadId : mArr) {
                        if (threadId.equals(sThreadId)) {
                            mSbThreadNotification.setChecked(false);
                            return;
                        }
                    }
                }
                mSbThreadNotification.setChecked(true);
            }
        }
    }

    private void addTextChangeListener() {
        mEdtMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mEdtMessage.length() == 1) {
                    String data = ConsumerUser.getInstance().getData().getPnUID() + ","
                            + ConsumerUser.getInstance().getData().getFullName();

                    JSONObject json = new JSONObject();
                    try {
                        json.put(Constants.getInstance().THREAD_ID, sThreadId);
                        json.put(Constants.getInstance().MESSAGE_CONTENT, data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    HomeCaravanApplication.mSocket.emit(Constants.getInstance().MESSAGE_THREAD_TYPING, data);
                }
            }
        });
    }

    @OnClick(R.id.imgSend)
    public void onSendMessageClicked() {
        mImgSend.setEnabled(false);
        String message = mEdtMessage.getText().toString().trim();
        mEdtMessage.setText(null);
        if (message.length() == 0) {
            mImgSend.setEnabled(true);
            return;
        }
        mLayoutEmpty.setVisibility(View.GONE);
        mRvMessage.setVisibility(View.VISIBLE);

        JSONObject json = new JSONObject();
        try {
            json.put(Constants.getInstance().MESSAGE_SEND_TO_THREAD, sThreadId);
            json.put(Constants.getInstance().MESSAGE_MESSAGE, message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("DaoDiDem", "onSendMessageClicked json: " + json.toString());

        //Gửi tn
        HomeCaravanApplication.mSocket.emit(Constants.getInstance().MESSAGE_SEND_MESSAGE, json);

        mImgSend.setEnabled(true);
    }

    private void newMessage(String message) {
        MessageItem messageItem = new MessageItem();
        MessageThreadView messageThreadView = new MessageThreadView();
        MessageThread messageThread = new MessageThread();
        ConsumerMessages consumerMessages = new ConsumerMessages();

        messageItem.setId("");
        String currentTime = String.valueOf(System.currentTimeMillis());
        messageItem.setCreatedBy(currentTime);
        messageItem.setCreatedDatetime(currentTime);
        messageItem.setModifiedDatetime(currentTime);
        messageItem.setModifiedBy(currentTime);
        messageItem.setContent(message);

        messageThreadView.setId(MessageUser.getInstance().getData().getId());
        messageThreadView.setName(MessageUser.getInstance().getData().getName());
        messageThreadView.setPhoto(MessageUser.getInstance().getData().getAvatar());
        messageThreadView.setContent(message);
        messageThreadView.setCreatedDatetime(MessageUser.getInstance().getData().getCreatedDatetime());

        messageItem.setMessageThreadView(messageThreadView);
        messageItem.setType(Constants.getInstance().MESSAGE_TYPE_TEXT);
        messageItem.setStatus(Constants.getInstance().MESSAGE_STATUS_NORMAL);

        messageThread.setId(sThreadId);
        messageThread.setCreatedDatetime(sCreateDatetime);
        messageThread.setModifiedDatetime(sModifiedDatetime);

        messageItem.setMessageThread(messageThread);

        consumerMessages.setMessageItem(messageItem);
        consumerMessages.setType(Constants.getInstance().MESSAGE_ITEM_TYPE_FROM_ITEM);

        mArrConsumerMessages.add(consumerMessages);
        int n = mArrConsumerMessages.size();
        mConversationAdapter.notifyItemInserted(n - 1);
        if (n > 0) {
            mRvMessage.smoothScrollToPosition(n - 1);
        }
    }

    private void newMessage(File imageFile) {
        ConsumerMessages consumerMessages;
        MessageItem messageItem = new MessageItem();
        MessageThreadView messageThreadView = new MessageThreadView();
        MessageThread messageThread = new MessageThread();
        consumerMessages = new ConsumerMessages();

        messageItem.setId("");
        String currentTime = String.valueOf(System.currentTimeMillis());
        messageItem.setCreatedDatetime(currentTime);
        messageItem.setModifiedDatetime(currentTime);

        messageItem.setTempImage(getByteArrayFromImage(imageFile));

        messageThreadView.setId(MessageUser.getInstance().getData().getId());
        messageThreadView.setName(MessageUser.getInstance().getData().getName());
        messageThreadView.setPhoto(MessageUser.getInstance().getData().getAvatar());
        messageThreadView.setCreatedDatetime(MessageUser.getInstance().getData().getCreatedDatetime());

        messageItem.setMessageThreadView(messageThreadView);
        messageItem.setType(Constants.getInstance().MESSAGE_TYPE_IMAGE);
        messageItem.setStatus(Constants.getInstance().MESSAGE_STATUS_NORMAL);

        messageThread.setId(sThreadId);
        messageThread.setCreatedDatetime(sCreateDatetime);
        messageThread.setModifiedDatetime(sModifiedDatetime);

        messageItem.setMessageThread(messageThread);

        consumerMessages.setMessageItem(messageItem);
        consumerMessages.setType(Constants.getInstance().MESSAGE_ITEM_TYPE_FROM_ITEM);

        mArrConsumerMessages.add(consumerMessages);
        int n = mArrConsumerMessages.size();
        mConversationAdapter.notifyItemInserted(n - 1);
        if (n > 0) {
            mRvMessage.smoothScrollToPosition(n - 1);
        }
    }

    @OnClick(R.id.ivBack)
    void onBackClicked() {
        if (detailClicked) {
            AnimUtils.slideRightToLeft(mLayoutDetail, 0, mWidthPage, true);
            mImgConversationDetail.setVisibility(View.VISIBLE);
            detailClicked = false;
            return;
        }
        finish();
    }

    @OnClick(R.id.edtMessage)
    void onEdtMessageClicked() {
        closeAttach();
        mLayoutEmoji.setVisibility(View.GONE);
        emojiClicked = false;
    }

    @OnClick(R.id.imgAttach)
    void onAttachClicked() {
        if (attachClicked) {
            closeAttach();
        } else {
            mLayoutAttach.setVisibility(View.VISIBLE);
            AnimUtils.slideUp(this, mLayoutAttach);
            mImgAttach.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_consumer_message_x_close_attach));
            attachClicked = true;
            hideKeyboard();
        }
    }

    @OnClick(R.id.imgEmoji)
    void onEmojiClicked() {
        if (emojiClicked) {
            mLayoutEmoji.setVisibility(View.GONE);
            emojiClicked = false;
        } else {
            mLayoutEmoji.setVisibility(View.VISIBLE);
            emojiClicked = true;
            hideKeyboard();
        }

    }

    @OnClick(R.id.imgAttachImage)
    void onAttachImageClicked() {
        if (!attachImageClicked) {
            changeImageColor(mImgQuickRespone, false);
            changeImageColor(mImgAttachImage, true);
            mLnAttachImage.setVisibility(View.VISIBLE);
            mSvQuickResponse.setVisibility(View.GONE);
            attachImageClicked = true;
        }
    }

    @OnClick(R.id.lnPhotoLibrary)
    void onPhotoLibraryClicked() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            HomeCaravanApplication.askPermission(this, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_GALLERY);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_GALLERY);
        }
    }

    @OnClick(R.id.lnTakeAPhoto)
    void onTakeAPhotoClicked() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            HomeCaravanApplication.askPermission(this, this, Manifest.permission.CAMERA, REQUEST_CAMERA);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(ConversationActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            photoFile);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
            }
        }
    }

    private void changeImageColor(ImageView img, boolean isClick) {
        int fromColor;
        int toColor;

        if (isClick) {
            fromColor = R.color.colorTextMain;
            toColor = R.color.color_text_light_blue_filter;
        } else {
            fromColor = R.color.color_text_light_blue_filter;
            toColor = R.color.colorTextMain;
        }
        AnimUtils.changeColorFilter(this, fromColor, toColor, img);
    }

    @OnClick(R.id.imgQuickRespone)
    void onQuickRespone() {
        if (attachImageClicked) {
            changeImageColor(mImgQuickRespone, true);
            changeImageColor(mImgAttachImage, false);
            mLnAttachImage.setVisibility(View.GONE);
            mSvQuickResponse.setVisibility(View.VISIBLE);
            attachImageClicked = false;
        }
    }

    @OnClick(R.id.tvQuickRespone1)
    void onQuickRespone1Clicked() {
        closeAttach();
        mEdtMessage.setText(mTvQuickRespone1.getText().toString());
    }

    @OnClick(R.id.tvQuickRespone2)
    void onQuickRespone2Clicked() {
        closeAttach();
        mEdtMessage.setText(mTvQuickRespone2.getText().toString());
    }

    @OnClick(R.id.tvQuickRespone3)
    void onQuickRespone3Clicked() {
        closeAttach();
        mEdtMessage.setText(mTvQuickRespone3.getText().toString());
    }

    @OnClick(R.id.sbThreadNotification)
    void onThreadNotificationClicked() {
        if (!mSbThreadNotification.isChecked()) {
            if (mPrefs != null) {
                Log.e(TAG, "onThreadNotificationClicked: turn on notification: " + sThreadId);
                List<String> mArr;
                String serialized = mPrefs.getString(Constants.getInstance().THREAD_ID_TURN_OFF_NOTIFICATION_LIST, null);
                SharedPreferences.Editor edit = mPrefs.edit();
                if (serialized != null && !serialized.isEmpty()) {
                    mArr = Arrays.asList(TextUtils.split(serialized, ","));
                    for (String threadId : mArr) {
                        if (threadId.equals(sThreadId)) {
                            mArr.remove(threadId);
                            edit.putString(Constants.getInstance().THREAD_ID_TURN_OFF_NOTIFICATION_LIST, TextUtils.join(",", mArr));
                            break;
                        }
                    }

                }
                HomeCaravanApplication.mReceiverMessageNotification = true;
                edit.putBoolean(Constants.getInstance().RECEIVER_NEW_MESSAGE_NOTIFICATION, HomeCaravanApplication.mReceiverMessageNotification);
                edit.apply();
            }
        } else {
            if (mPrefs != null) {
                Log.e(TAG, "onThreadNotificationClicked: turn off notification: " + sThreadId);
                List<String> mArr;
                String serialized = mPrefs.getString(Constants.getInstance().THREAD_ID_TURN_OFF_NOTIFICATION_LIST, null);
                if (serialized != null && !serialized.isEmpty()) {
                    mArr = Arrays.asList(TextUtils.split(serialized, ","));
                } else {
                    mArr = new ArrayList<>();
                    mArr.add(sThreadId);
                }

                SharedPreferences.Editor edit = mPrefs.edit();
                edit.putString(Constants.getInstance().THREAD_ID_TURN_OFF_NOTIFICATION_LIST, TextUtils.join(",", mArr));
                edit.apply();
            }
        }

    }

    @OnClick(R.id.lnInviteMore)
    void onInviteMoreClicked() {
        Intent intent = new Intent(this, ContactsManagerActivity.class);
        intent.putExtra("FROM_MESSAGE", true);
        startActivityForResult(intent, REQUEST_CONTACT_CODE);
    }

    public void closeAttach() {
        AnimUtils.slideDown(this, mLayoutAttach);
        attachClicked = false;
        mImgAttach.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_consumer_message_attach));
    }

    public void hideKeyboard() {
        //hide keyboard
        if (getCurrentFocus() != null) {
            HomeCaravanApplication.getInstance().getInput().hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void socketListening() {
        HomeCaravanApplication.mSocket.on(sThreadId, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e(TAG, "socketListening " + sThreadId + ": " + args[0].toString());
                if (args[0] == null) {
                    return;
                }
                final JSONObject data = (JSONObject) args[0];
                String key, command;
                try {
                    key = data.getString(Constants.getInstance().MESSAGE_KEY);
                    command = data.getString(Constants.getInstance().MESSAGE_COMMAND);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                //Receiver message
                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_ADD)
                        && key.equals(Constants.getInstance().MESSAGE_MESSAGE)) {
                    MessageAddResponse messageAddResponse = new Gson().fromJson(args[0].toString(), MessageAddResponse.class);
                    if (messageAddResponse != null) {
                        messageAddResponse.getMessageItem().setCommand(messageAddResponse.getCommand());
                        ConsumerMessages consumerMessages = new ConsumerMessages();
                        consumerMessages.setMessageItem(messageAddResponse.getMessageItem());
                        final String pnUserId = consumerMessages.getMessageItem().getMessageThreadView().getId();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                checkTyping(pnUserId);
                            }
                        });

                        if (MessageUser.getInstance().getData().getId()
                                .equals(messageAddResponse.getMessageItem().getMessageThreadView().getId())) {
                            consumerMessages.setType(Constants.getInstance().MESSAGE_ITEM_TYPE_FROM_ITEM);

                            if (messageAddResponse.getMessageItem().getType().equals(Constants.getInstance().MESSAGE_TYPE_IMAGE)) {
                                return;
                            }
                        } else {
                            consumerMessages.setType(Constants.getInstance().MESSAGE_ITEM_TYPE_ITEM);
                        }
                        mArrConsumerMessages.add(consumerMessages);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int n = mArrConsumerMessages.size();
                                mConversationAdapter.notifyItemInserted(n - 1);
                                if (n > 0) {
                                    mRvMessage.smoothScrollToPosition(n - 1);
                                }
                            }
                        });
                    }
                }

                //Update message
                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_UPDATE)
                        && key.equals(Constants.getInstance().MESSAGE_MESSAGE)) {

                }

                //Receiver thread
                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_UPDATE)
                        && key.equals(Constants.getInstance().MESSAGE_NAME)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mTvGroupName.setText(data.getString(Constants.getInstance().MESSAGE_VALUE));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_DELETE)
                        && key.equals(Constants.getInstance().THREAD)) {
                    //Bị xóa khỏi group hoặc tự out group
                    //Test lai api nay
                }

                //Message typing
                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_TYPING)
                        && key.equals(Constants.getInstance().MESSAGE_MESSAGE)) {
                    String id, name;
                    try {
                        JSONObject value = data.getJSONObject(Constants.getInstance().MESSAGE_VALUE);
                        id = value.getString("id");
                        name = value.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                    final String typingId = id;
                    final String typingName = name;

                    if (!ConsumerUser.getInstance().getData().getPnUID().equals(typingId)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for(MessageTyping mt : mArrTyping){
                                    if(mt.getIdTyping().equals(typingId)){
                                        return;
                                    }
                                }
                                final MessageTyping messageTyping = new MessageTyping(typingId, typingName);
                                mArrTyping.add(messageTyping);

                                int n = mArrTyping.size();
                                String nameTyping = mArrTyping.get(0).getNameTyping();
                                if (n > 1) {
                                    for (int i = 1; i < n; i++) {
                                        nameTyping += ", " + mArrTyping.get(i).getNameTyping();
                                    }
                                }
                                mTvTyping.setVisibility(View.VISIBLE);
                                mTvTyping.setText(nameTyping + " is typing ..");


                                new CountDownTimer(3000, 3000) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        checkTyping(typingId);
                                    }
                                }.start();
                            }
                        });
                    }
                }
            }
        });

        HomeCaravanApplication.mSocket.on(Constants.getInstance().THREAD, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.e(TAG, "socketListening thread: " + args[0].toString());
                if (args[0] == null) {
                    return;
                }
                final JSONObject data = (JSONObject) args[0];
                String key, command;
                try {
                    key = data.getString(Constants.getInstance().MESSAGE_KEY);
                    command = data.getString(Constants.getInstance().MESSAGE_COMMAND);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }
                if (command.equals(Constants.getInstance().MESSAGE_COMMAND_ADD)
                        && key.equals(Constants.getInstance().MESSAGE_MESSAGE)) {
                    MessageAddResponse messageAddResponse = new Gson().fromJson(args[0].toString(), MessageAddResponse.class);
                    if (messageAddResponse != null) {
                        messageAddResponse.getMessageItem().setCommand(messageAddResponse.getCommand());
                        ConsumerMessages consumerMessages = new ConsumerMessages();
                        consumerMessages.setMessageItem(messageAddResponse.getMessageItem());

                        if (SkeletonMessageThread.getInstance().getData().size() != 0) {
                            for (ConsumerMessageAll cma : SkeletonMessageThread.getInstance().getData()) {
                                if (cma.getMessageThread().getId()
                                        .equals(consumerMessages.getMessageItem().getMessageThread().getId())) {
                                    ConsumerMessageAll consumerMessageAll = new ConsumerMessageAll();
                                    consumerMessageAll.setType(cma.getType());
                                    consumerMessageAll.setMessageThread(cma.getMessageThread());
                                    SkeletonMessageThread.getInstance().getData().remove(cma);
                                    SkeletonMessageThread.getInstance().getData().add(0, consumerMessageAll);
                                    saveNewThread(consumerMessageAll, false);
                                    break;
                                }
                            }
                        } else {
                            ConsumerMessageAll consumerMessageAll = new ConsumerMessageAll();
                            int sizeParticipants = consumerMessages.getMessageItem().getMessageThread().getParticipants().size();
                            if (sizeParticipants > 2)
                                consumerMessageAll.setType("group");
                            else if (sizeParticipants == 2)
                                consumerMessageAll.setType("personal");
                            else
                                return;
                            consumerMessageAll.setMessageThread(consumerMessages.getMessageItem().getMessageThread());
                            SkeletonMessageThread.getInstance().getData().add(consumerMessageAll);
                            saveNewThread(consumerMessageAll, true);
                        }
                    }
                }
            }
        });
    }

    private void checkTyping(String pnUserId){
        for (MessageTyping mt : mArrTyping) {
            if (mt.getIdTyping().equals(pnUserId)) {
                mArrTyping.remove(mt);
                int n = mArrTyping.size();
                if (n == 0) {
                    mTvTyping.setVisibility(View.GONE);
                    mTvTyping.setText(null);
                } else {
                    String nameTyping = mArrTyping.get(0).getNameTyping();
                    if (n > 1) {
                        for (int i = 1; i < n; i++) {
                            nameTyping += ", " + mArrTyping.get(i).getNameTyping();
                        }
                    }
                    mTvTyping.setVisibility(View.VISIBLE);
                    mTvTyping.setText(nameTyping + " is typing ..");
                }
                break;
            }
        }
    }

    private void saveNewThread(final ConsumerMessageAll consumerMessageAll, boolean isNew) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Realm realm = HomeCaravanApplication.getInstance().getRealm();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull Realm realm) {
                        realm.insertOrUpdate(consumerMessageAll);
                        realm.commitTransaction();
                    }
                });
            }
        });
    }

    private void setupAdapter() {
//        mConversationListAgentAdapter = new ConversationListAgentAdapter(this, mArrMessageListThread);
//        mRvConversationListAgent.setLayoutManager(createLayoutManager());
//        mRvConversationListAgent.setAdapter(mConversationListAgentAdapter);

        mConversationAdapter = new ConversationAdapter(this, mArrConsumerMessages, new ConversationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ConsumerMessages item) {
                closeAttach();
                hideKeyboard();
            }

            @Override
            public void onItemLongClick(int position, ConsumerMessages item, Bitmap bitmap) {
                if (MessageUser.getInstance().getData().getId().equals(item.getMessageItem().getMessageThreadView().getId())) {
                    showDialogAction(position, item, bitmap, true);
                } else {
                    showDialogAction(position, item, bitmap, false);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = createLayoutManagerVertical();
        linearLayoutManager.setStackFromEnd(true);
        mRvMessage.setLayoutManager(linearLayoutManager);
        mRvMessage.setAdapter(mConversationAdapter);

        mConversationGroupMemberAdapter = new ConversationGroupMemberAdapter(this, mArrGroupUser);
        mRvGroupMember.setLayoutManager(createLayoutManagerVertical());
        mRvGroupMember.setAdapter(mConversationGroupMemberAdapter);
    }

    private void checkIntent() {
        if (ConsumerUser.getInstance().getData().getPnUID() == null || ConsumerUser.getInstance().getData().getPnUID().isEmpty()) {
            Toast.makeText(this, "Failed to get data from server", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Intent intent = getIntent();
        String threadId = intent.getStringExtra("THREAD_ID");
        
        if (threadId != null) {
            // tu CIA qua
            sThreadId = threadId;
            mMessagesActionPresenter.getMessages(sThreadId);
            GetThreadPresenter mGetThreadPresenter = new GetThreadPresenter(this);
            mGetThreadPresenter.getThread(sThreadId);

            sName = intent.getStringExtra("MESSAGE_THREAD_NAME");
            mTvName.setText(sName);
            String responseMessage1 = intent.getStringExtra("RESPONSE_MESSAGE_1");
            mTvQuickRespone1.setText(responseMessage1);
        } else {
            getData(intent);
        }
    }

    private void getData(Intent intent) {
        sThreadId = intent.getStringExtra("ID_THREAD");
        sName = intent.getStringExtra("NAME_THREAD");
        sCreateDatetime = intent.getStringExtra("CREATE_DATETIME_THREAD");
        sModifiedDatetime = intent.getStringExtra("MODIFIED_DATETIME_THREAD");
        sCreateBy = intent.getStringExtra("CREATE_BY_THREAD");
        sModifiedBy = intent.getStringExtra("MODIFIED_BY_THREAD");
        sDataThread = intent.getStringExtra("DATA_THREAD");
        mTvName.setText(sName);
        mTvGroupName.setText(sName);
        mMessagesActionPresenter.getMessages(sThreadId);
        GetThreadPresenter mGetThreadPresenter = new GetThreadPresenter(this);
        mGetThreadPresenter.getThread(sThreadId);
    }

//    private void setupListAgentOfPersonalConversation() {
//        mCustomLayoutManager = new CustomLayoutManager(CustomLayoutManager.HORIZONTAL);
//        mCustomLayoutManager.attach(mRvConversationListAgent);
//        mCustomLayoutManager.setItemTransformer(new ScaleTransformerRecyclerView());
//        mCustomLayoutManager.setOnItemSelectedListener(new CustomLayoutManager.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(RecyclerView recyclerView, View item, final int position) {
//                if (mAttackSmoothScroll) {
//                    mRvConversationListAgent.smoothScrollToPosition(position);
//                    mMessagesActionPresenter.getMessages(mArrMessageListThread.get(position).getId());
//                    mTvName.setText(mArrMessageListThread.get(position).getUserInThread().get(0).getName());
//                    mPbLoadMessages.setVisibility(View.VISIBLE);
//                    mLayoutEmpty.setVisibility(View.GONE);
//
//                } else {
//                    mAttackSmoothScroll = true;
//                }
//            }
//        });
//
//        if (positionToSmoothScroll != -1) {
//            mRvConversationListAgent.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mRvConversationListAgent.smoothScrollToPosition(positionToSmoothScroll);
//                }
//            }, 200);
//        }
//    }

    private LinearLayoutManager createLayoutManagerVertical() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void showDialogAction(final int position, final ConsumerMessages item, final Bitmap bitmap, boolean isMySelf) {
        LayoutInflater layoutInflater1 = LayoutInflater.from(ConversationActivity.this);
        View view = layoutInflater1.inflate(R.layout.dialog_item_consumer_message_conversation, null);
        TextView tv1 = (TextView) view.findViewById(R.id.tv1);
        TextView tvRemove = (TextView) view.findViewById(R.id.tv2);
        View view1 = view.findViewById(R.id.view1);
        View view2 = view.findViewById(R.id.view2);
        TextView tvCancel = (TextView) view.findViewById(R.id.tvCancel);
        if (Constants.getInstance().MESSAGE_TYPE_IMAGE.equals(item.getMessageItem().getType())) {
//            tv1.setText("Save");
            tv1.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
        } else {
            tv1.setText("Copy");
        }
        if (!isMySelf) {
            view2.setVisibility(View.GONE);
            tvRemove.setVisibility(View.GONE);
        }

        final AlertDialog alertDialog = new AlertDialog.Builder(ConversationActivity.this).setView(view).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        wmlp.y = 200;   //y position

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.getInstance().MESSAGE_TYPE_IMAGE.equals(item.getMessageItem().getType())) {
//                    String imageName = UUID.randomUUID().toString()+".png";
////                    MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, imageName, "");
//                    saveImage(bitmap, imageName);
//                    Toast.makeText(ConversationActivity.this, imageName, Toast.LENGTH_SHORT).show();
                } else {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("message", item.getMessageItem().getContent());
                    clipboard.setPrimaryClip(clip);
                }
                alertDialog.dismiss();
            }
        });

        tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessagesActionPresenter.removeMessage(item.getMessageItem().getId(), position);
                alertDialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPGE" + timeStamp + "_";
        File storageDir = new File(Constants.FOLDER);
//        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_DCIM), "Screenshots");

        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void savePhoto(Uri uri) throws Exception {
        String filename = String.format("%d_%s", Calendar.getInstance().getTimeInMillis(), uri.getLastPathSegment());
        File imageFile = new File(Constants.FOLDER, filename);
        Log.d(TAG, uri.getPath());
        FileInputStream inStream = new FileInputStream(new File(uri.getPath()));
        FileOutputStream outStream = new FileOutputStream(imageFile);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
        newMessage(imageFile);
        uploadImageToService(imageFile);
    }

    private void uploadImageToService(File imageFile) {
        if (imageFile != null) {
            Map<String, RequestBody> bodyMap = new HashMap<>();
            MediaType MEDIA_TYPE = MediaType.parse("image/*");
            RequestBody imageUse = RequestBody.create(MEDIA_TYPE, imageFile);
            bodyMap.put("userfile\"; filename=\"" + imageFile.getName() + "\" ", imageUse);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.getInstance().URL_BASE_CONSUMER_MESSAGE_UPLOAD_IMAGE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            IUploadImage uploadImage = retrofit.create(IUploadImage.class);
            Call<ArrayList<UploadObject>> fileUpload = uploadImage.uploadFile(bodyMap);
            fileUpload.enqueue(new Callback<ArrayList<UploadObject>>() {
                @Override
                public void onResponse(Call<ArrayList<UploadObject>> call, Response<ArrayList<UploadObject>> response) {
                    if (response.isSuccessful()) {
                        String fileName = response.body().get(0).getFileName();
                        String fileUrl = Constants.getInstance().URL_BASE_CONSUMER_MESSAGE_UPLOAD_IMAGE + "/" + response.body().get(0).getFileUrl();

                        JSONObject data = new JSONObject();

                        try {
                            data.put("size", response.body().get(0).getSize());
                            data.put("fileName", response.body().get(0).getFileName());
                            data.put("fileUrl", fileUrl);
                            data.put("type", response.body().get(0).getType());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e(TAG, "JSON data: " + data.toString());

                        JSONObject json = new JSONObject();
                        JSONObject message = new JSONObject();
                        try {
                            message.put("data", data.toString());
                            message.put("content", fileName);
                            message.put("type", Constants.getInstance().MESSAGE_TYPE_IMAGE);

                            json.put(Constants.getInstance().MESSAGE_SEND_TO_THREAD, sThreadId);
                            json.put(Constants.getInstance().MESSAGE_MESSAGE, message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e(TAG, "onSendMessageClicked json: " + json.toString());

                        mConversationAdapter.uploadImageComplete(mArrConsumerMessages.size() - 1);

                        //Gửi tn
                        HomeCaravanApplication.mSocket.emit(Constants.getInstance().MESSAGE_SEND_MESSAGE, json);

                    } else {
                        Log.e(TAG, response.message());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<UploadObject>> call, Throwable t) {
                    Log.e("onFailure", t.getMessage());
                }
            });
        } else {
            MyDialog.getInstance("No Image Upload", 2).show(getSupportFragmentManager(), "DIALOG33");
        }
    }

    private String getRealPathFromURIPath(Uri contentURI) {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(contentURI, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(idx);
            }
        } catch (Exception e) {
            if (cursor != null)
                cursor.close();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return contentURI.getPath();
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.emojicons, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    private ConsumerMessages newTimeLine(String date) {
        ConsumerMessages timeLine = new ConsumerMessages();
        timeLine.setType(Constants.getInstance().MESSAGE_ITEM_TYPE_TIME_LINE);
        timeLine.setDate(date);
        return timeLine;
    }

    @Override
    public void onBackPressed() {
        if (detailClicked) {
            AnimUtils.slideRightToLeft(mLayoutDetail, 0, mWidthPage, true);
            mImgConversationDetail.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(R.drawable.ic_consumer_message_edit).asBitmap().into(mImgEditGroupName);
            mEdtGroupName.setVisibility(View.GONE);
            mTvGroupName.setVisibility(View.VISIBLE);
            detailClicked = false;
            return;
        }
        if (emojiClicked) {
            mLayoutEmoji.setVisibility(View.GONE);
            emojiClicked = false;
            return;
        }
        if (attachClicked) {
            closeAttach();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    try {
                        String filePath = getRealPathFromURIPath(selectedUri);
                        File imageFile = new File(filePath);
                        newMessage(imageFile);
                        uploadImageToService(imageFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (requestCode == REQUEST_CAMERA) {
                if (currentPhotoPath != null) {
                    try {
                        savePhoto(Uri.parse(currentPhotoPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            //Invited people from contact
            if (requestCode == REQUEST_CONTACT_CODE) {
                String userId = data.getStringExtra(Constants.getInstance().CONTACT_LIST_INVITED);
                if (userId != null && !userId.isEmpty()) {
                    InviteIntoGroupPresenter mInviteIntoGroupPresenter = new InviteIntoGroupPresenter(this);
                    mInviteIntoGroupPresenter.inviteIntoGroup(sThreadId, userId);
                    // TODO: 12/4/2017 call api 
                }
            }
        }
    }

    @Override
    public void getMessagesSuccess(ArrayList<MessageItem> mArrMessages) {
        int n = mArrMessages.size();
        if (n == 0) {
            getMessagesFailed();
            return;
        }

        Long timeStampCurrentOfToday = System.currentTimeMillis() / 1000;
        Long timeStampStartOfToday = (timeStampCurrentOfToday / 86400) * 86400;
        Long timeStampStartOfYesterday = timeStampStartOfToday - 86400;
        Long timeLastMessage;

        mArrConsumerMessages.clear();

        boolean toDay = true, yesterday = true, fewDaysAgo = true;
        ConsumerMessages consumerMessages;

        // Because the mArrMessageItem has sorted by CreatedDatetime from newest to oldest
        for (int i = n - 1; i > -1; i--) {
            if (Constants.getInstance().MESSAGE_STATUS_DELETED.equals(mArrMessages.get(i).getStatus())) {
                continue;
            }
            consumerMessages = new ConsumerMessages();
            timeLastMessage = Long.parseLong(mArrMessages.get(i).getCreatedDatetime()) / 1000;

            if (timeLastMessage >= timeStampStartOfToday) {
                if (toDay) {
                    mArrConsumerMessages.add(newTimeLine("Today"));
                    toDay = false;
                }
            } else if (timeLastMessage >= timeStampStartOfYesterday) {
                if (yesterday) {
                    mArrConsumerMessages.add(newTimeLine("Yesterday"));
                    yesterday = false;
                }
            } else {
                if (!toDay || !yesterday) {
                    if (!mArrMessages.get(i + 1).getDateFormat().equals(mArrMessages.get(i).getDateFormat())) {
                        mArrConsumerMessages.add(newTimeLine(mArrMessages.get(i).getDateFormat()));
                    }
                } else {
                    if (fewDaysAgo) {
                        mArrConsumerMessages.add(newTimeLine(mArrMessages.get(i).getDateFormat()));
                        fewDaysAgo = false;
                    } else {
                        if (!mArrMessages.get(i + 1).getDateFormat().equals(mArrMessages.get(i).getDateFormat())) {
                            mArrConsumerMessages.add(newTimeLine(mArrMessages.get(i).getDateFormat()));
                        }
                    }
                }
            }

            String currentIdMessage = mArrMessages.get(i).getCreatedBy() != null ?
                    mArrMessages.get(i).getCreatedBy() : mArrMessages.get(i).getMessageThreadView().getId();
            if (MessageUser.getInstance().getData().getId().equals(currentIdMessage))
                consumerMessages.setType(Constants.getInstance().MESSAGE_ITEM_TYPE_FROM_ITEM);
            else
                consumerMessages.setType(Constants.getInstance().MESSAGE_ITEM_TYPE_ITEM);

            consumerMessages.setMessageItem(mArrMessages.get(i));

            mArrConsumerMessages.add(consumerMessages);
        }

        checkArrMessage();

        if (mArrConsumerMessages.size() != 0) {
            Realm realm = Realm.getDefaultInstance();
            mMessagesActionPresenter.saveMessages(realm, mArrConsumerMessages, sThreadId);
        } else {
            showEmptyConversation();
        }
    }

    @Override
    public void getMessagesFromRealmSuccess(ArrayList<ConsumerMessages> mArrMessages) {
        mArrConsumerMessages.clear();
        mArrConsumerMessages.addAll(mArrMessages);
        mConversationAdapter.notifyDataSetChanged();
    }

    @Override
    public void getMessagesFailed() {
        Log.e(TAG, "getMessagesFailed: No message");
        showEmptyConversation();
    }

    @Override
    public void removeMessageSuccess(final int position) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mArrConsumerMessages.remove(position);
                int n = mArrConsumerMessages.size();
                mConversationAdapter.notifyItemRemoved(position);
                mConversationAdapter.notifyItemRangeRemoved(position, n);
                if (position == n - 1) {
                    mRvMessage.smoothScrollToPosition(position - 1);
                } else if (n != 0) {
                    mRvMessage.smoothScrollToPosition(position);
                }
            }
        });
    }

    @Override
    public void removeMessageFail() {
        Log.e(TAG, "removeMessageFail: Show snackbar voi message: remove fail, try again");
    }

    @Override
    public void invitedSuccess() {
        hideLoading();
        // TODO: 11/29/2017 tiep tuc
    }

    @Override
    public void invitedFail(String message) {
        hideLoading();
        showSnackBar(mLayoutConversation, TypeDialog.WARNING, message, "invitedFail");
    }

    @Override
    public void serverError(@StringRes int message) {
        hideLoading();
        showSnackBar(mLayoutConversation, TypeDialog.ERROR, message, "getMessagesFailed");
    }

    @Override
    protected void onDestroy() {
        HomeCaravanApplication.mSocket.off(sThreadId);
        HomeCaravanApplication.mSocket.off(Constants.getInstance().THREAD);
        super.onDestroy();
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mEdtMessage);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mEdtMessage, emojicon);
    }

    @Override
    public void getThreadSuccess(final MessageThread messageThread) {
        if (messageThread.getParticipants() == null) {
            Log.e(TAG, "getThreadFail: null");
            return;
        }
        JSONArray jArrParticipants = new JSONArray();
        for (String participantId : messageThread.getParticipants()) {
            if (!participantId.equals(MessageUser.getInstance().getData().getId())) {
                jArrParticipants.put(participantId);
            }
            Log.e(TAG, "getThreadSuccess - participantId: " + participantId);
        }
        mGetAllThreadPresenter.getAllUserInfo(jArrParticipants);

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
//                mTvName.setText(messageThread.getName());
                mTvGroupName.setText(messageThread.getName());
            }
        });
    }

    @Override
    public void getThreadFail() {
        Log.e(TAG, "getThreadFail: ");
    }

    @Override
    public void getAllThreadSuccess(ArrayList<MessageThread> data) {

    }

    @Override
    public void getAllThreadFail() {

    }

    @Override
    public void getAllUserInfoSuccess(final ArrayList<MessageUserData> data) {
        if (data.size() == 0) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mArrGroupUser.clear();
                mArrGroupUser.addAll(data);
                mConversationGroupMemberAdapter.notifyDataSetChanged();
                MessageUserData currentUser = new MessageUserData();
                currentUser.setId(MessageUser.getInstance().getData().getId());
                currentUser.setAvatar(MessageUser.getInstance().getData().getAvatar());
                currentUser.setName(MessageUser.getInstance().getData().getName());
                mArrGroupUser.add(currentUser);
                mConversationGroupMemberAdapter.notifyItemInserted(mArrGroupUser.size());
                mTvGroupCount.setText(mArrGroupUser.size() + " participants");
            }
        });
    }

    @Override
    public void getAllUserInfoFail() {
    }

    private byte[] getByteArrayFromImage(File file) {
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[2048];
        try {
            FileInputStream fis = new FileInputStream(file);
            //create FileInputStream which obtains input bytes from a file in a file system
            //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
                //no doubt here is 0
                /*Writes len bytes from the specified byte array starting at offset
                off to this byte array output stream.*/
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bos.toByteArray();
    }

    private void checkArrMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mArrConsumerMessages.size() != 0) {
                    mPbLoadMessages.setVisibility(View.GONE);
                    mRvMessage.setVisibility(View.VISIBLE);
                    mImgSend.setEnabled(true);
                    mConversationAdapter.notifyDataSetChanged();
                    mRvMessage.smoothScrollToPosition(mArrConsumerMessages.size() - 1);
                }
            }
        });
    }

    private void showEmptyConversation() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRvMessage.setVisibility(View.GONE);
                mPbLoadMessages.setVisibility(View.GONE);
                mImgSend.setEnabled(true);
                mLayoutEmpty.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_conversation;
    }

    @OnClick(R.id.imgConversationDetail)
    void showConversationDetail() {
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mLayoutDetail, "translationX", mWidthPage, 0).setDuration(200);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                mLayoutDetail.setVisibility(View.VISIBLE);
                mImgConversationDetail.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                detailClicked = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        objectAnimator.start();
    }

    @OnClick(R.id.lnLeaveConversation)
    void onLeaveConversationClicked() {
        showPopupConfirm();
    }

    private void showPopupConfirm() {
        LayoutInflater layoutInflater1 = LayoutInflater.from(this);
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_confirm, null);
        TextView tvMessage = (TextView) view1.findViewById(R.id.tvMessage);
        tvMessage.setText("Are you sure you want to leave conversation?");
        FrameLayout frmButtonNo = (FrameLayout) view1.findViewById(R.id.frmButtonNo);
        FrameLayout frmButtonYes = (FrameLayout) view1.findViewById(R.id.frmButtonYes);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showSnackBar(mLayoutConversation, TypeDialog.WARNING, R.string.no_network, "no-internet");
                    return;
                }

                mGetAllThreadPresenter.deleteThread(sThreadId, MessageUser.getInstance().getData().getId());

                Realm realm = Realm.getDefaultInstance();
                mGetAllThreadPresenter.deleteThreadFromRealm(realm, sThreadId);

                finish();
                alertDialog.dismiss();
            }
        });

        frmButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @OnClick(R.id.imgEditGroupName)
    void onEditGroupNameClicked() {
        if (mIsEditingGroupName) {
            Glide.with(getApplicationContext()).load(R.drawable.ic_consumer_message_edit).asBitmap().into(mImgEditGroupName);
            mEdtGroupName.setVisibility(View.GONE);
            mTvGroupName.setVisibility(View.VISIBLE);
            String groupName = mEdtGroupName.getText().toString();
            if (groupName.length() != 0) {
                mTvGroupName.setText(groupName);
                //call api

                JSONObject json = new JSONObject();
                try {
                    json.put(Constants.getInstance().THREAD_ID, sThreadId);
                    json.put(Constants.getInstance().MESSAGE_KEY, Constants.getInstance().MESSAGE_NAME);
                    json.put(Constants.getInstance().MESSAGE_VALUE, groupName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HomeCaravanApplication.mSocket.emit(Constants.getInstance().THREAD_UPDATE, json);
            }
            mIsEditingGroupName = !mIsEditingGroupName;

            if (getCurrentFocus() != null) {
                HomeCaravanApplication.getInstance().getInput().hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } else {
            Glide.with(getApplicationContext()).load(R.drawable.ic_consumer_message_edit_submit).asBitmap().into(mImgEditGroupName);
            mEdtGroupName.setVisibility(View.VISIBLE);
            mTvGroupName.setVisibility(View.GONE);
            mEdtGroupName.setText(mTvGroupName.getText().toString());
            mIsEditingGroupName = !mIsEditingGroupName;
        }

    }

    @OnTouch(R.id.rvGroupMember)
    public boolean onGroupMemberTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLayoutDetail.setNestedScrollingEnabled(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLayoutDetail.setNestedScrollingEnabled(true);
                break;
        }
        return false;
    }

    @Override
    protected void onPause() {
        mLayoutDetail.setNestedScrollingEnabled(true);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
