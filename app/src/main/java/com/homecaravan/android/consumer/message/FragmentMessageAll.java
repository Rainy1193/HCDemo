package com.homecaravan.android.consumer.message;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.api.Constants;
import com.homecaravan.android.consumer.adapter.MessageAllAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.GetAllThreadPresenter;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.IGetAllThreadView;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.message.ConsumerMessageAll;
import com.homecaravan.android.consumer.model.message.ConsumerMessages;
import com.homecaravan.android.consumer.model.message.MessageAddResponse;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageThreadView;
import com.homecaravan.android.consumer.model.message.MessageUser;
import com.homecaravan.android.consumer.model.message.MessageUserData;
import com.homecaravan.android.consumer.model.message.SkeletonMessageThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.socket.emitter.Emitter;

/**
 * Created by Anh Dao on 10/5/2017.
 * Messages All Tab
 */

public class FragmentMessageAll extends BaseFragment implements IGetAllThreadView,
        IMessageThreadAction {

    private final String TAG = "DaoDiDem";
    private MessageAllAdapter mMessageAllAdapter;
    private GetAllThreadPresenter mGetAllThreadPresenter;
    private JSONArray jArrParticipants;
    private ArrayList<MessageThread> mArrMessageThreadList = new ArrayList<>();

    @Bind(R.id.layoutMessageThread)
    RelativeLayout mLayoutMessageThread;
    @Bind(R.id.rvMessageAll)
    RecyclerView mRvMessageAll;
    @Bind(R.id.fabNewMessage)
    FloatingActionButton mFabNewMessage;
    @Bind(R.id.pbLoadThread)
    ProgressBar mPbLoadThread;
    @Bind(R.id.layoutEmpty)
    LinearLayout mLayoutEmpty;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGetAllThreadPresenter = new GetAllThreadPresenter(this);

        setupAdapter();

        if (HomeCaravanApplication.isNetAvailable(getActivity()) && HomeCaravanApplication.mLoginSocketSuccess) {
            getAllThread();
        } else {
            Realm mRealm = HomeCaravanApplication.getInstance().getRealm();
            loadDataFromRealm(mRealm);
        }

        socketListener();

//        if (SkeletonMessageThread.getInstance().getData().size() != 0) {
//            mMessageAllAdapter.notifyDataSetChanged();
//            mPbLoadThread.setVisibility(View.GONE);
//            mLayoutEmpty.setVisibility(View.GONE);
//        }
    }

    private void socketListener() {
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
                                    mMessageAllAdapter.notifyItemInserted(0);
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
                            mMessageAllAdapter.notifyItemInserted(0);
                            saveNewThread(consumerMessageAll, false);
                        }
                    }
                }

                // TODO: 12/1/2017 chưa làm thread update
            }
        });
    }

    @Override
    public void getAllThreadSuccess(ArrayList<MessageThread> data) {
        mArrMessageThreadList = data;
        // TODO: 12/1/2017 bước này đã được cải tiến 
        getAllParticipants();
        mGetAllThreadPresenter.getAllUserInfo(jArrParticipants);
    }

    @Override
    public void getAllThreadFail() {
        Toast.makeText(getActivity(), "Failed to get data from server", Toast.LENGTH_SHORT).show();
        Realm mRealm = HomeCaravanApplication.getInstance().getRealm();
        loadDataFromRealm(mRealm);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SkeletonMessageThread.getInstance().getData().size() != 0) {
            ArrayList<ConsumerMessageAll> mArrTempMessageAll = SkeletonMessageThread.getInstance().getData();
            SkeletonMessageThread.getInstance().getData().clear();
            SkeletonMessageThread.getInstance().getData().addAll(mArrTempMessageAll);
            mMessageAllAdapter.notifyDataSetChanged();
            mPbLoadThread.setVisibility(View.GONE);
            mLayoutEmpty.setVisibility(View.GONE);
        } else {
//            mPbLoadThread.setVisibility(View.GONE);
//            mLayoutEmpty.setVisibility(View.VISIBLE);
            // TODO: 12/4/2017 check empty layout xuất hiện khi mở màn hình này
        }

    }

    @Override
    public void getAllUserInfoSuccess(ArrayList<MessageUserData> data) {
        setUserDataEachThread(data);

        ConsumerMessageAll consumerMessageAll;
        SkeletonMessageThread.getInstance().getData().clear();

        for (MessageThread mt : mArrMessageThreadList) {
            consumerMessageAll = new ConsumerMessageAll();

            if (mt.getParticipants().size() > 2)
                consumerMessageAll.setType("group");
            else if (mt.getParticipants().size() == 2)
                consumerMessageAll.setType("personal");
            else
                continue;

            consumerMessageAll.setMessageThread(mt);

            SkeletonMessageThread.getInstance().getData().add(consumerMessageAll);
        }

        if (SkeletonMessageThread.getInstance().getData().size() != 0) {
            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageAllAdapter.notifyDataSetChanged();
                    mPbLoadThread.setVisibility(View.GONE);
                    mLayoutEmpty.setVisibility(View.GONE);
                }
            });

            Realm realm = Realm.getDefaultInstance();
            deleteAllMessagesAll(realm);
            saveDataToRealm(realm, SkeletonMessageThread.getInstance().getData());
        } else {
            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLayoutEmpty.setVisibility(View.VISIBLE);
                    mPbLoadThread.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void getAllUserInfoFail() {
        Toast.makeText(getActivity(), "Failed to get data from server", Toast.LENGTH_SHORT).show();
        Realm mRealm = HomeCaravanApplication.getInstance().getRealm();
        loadDataFromRealm(mRealm);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_message_all;
    }

    private void setupAdapter() {
        mMessageAllAdapter = new MessageAllAdapter(getActivity(), SkeletonMessageThread.getInstance().getData(),
                HomeCaravanApplication.getInstance().buildPicasso(), this);
        mRvMessageAll.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvMessageAll.setAdapter(mMessageAllAdapter);
    }

    private void getAllThread() {
        Long timeStampLong = System.currentTimeMillis() / 1000;
        String timeStamp = timeStampLong.toString();
        mGetAllThreadPresenter.getAllThread(timeStamp);
    }

    private void loadDataFromRealm(Realm realm) {
        if (checkRealmIsEmpty(realm)) {
            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLayoutEmpty.setVisibility(View.VISIBLE);
                    mPbLoadThread.setVisibility(View.GONE);
                }
            });
            Log.e(TAG, "loadDataFromRealm: checkRealmIsEmpty = true");
        } else {

            Log.e(TAG, "loadDataFromRealm: checkRealmIsEmpty = false");
            RealmResults<ConsumerMessageAll> mArr = realm.where(ConsumerMessageAll.class).findAll();
            SkeletonMessageThread.getInstance().getData().addAll(realm.where(ConsumerMessageAll.class).findAll());

            //Sắp xếp lại vì có tường hợp có các event add thêm hoặc edit thread
            Collections.sort(mArr, new Comparator<ConsumerMessageAll>() {
                @Override
                public int compare(ConsumerMessageAll cma1, ConsumerMessageAll cma2) {

                    return cma1.getMessageThread().getModifiedDatetime()
                            .compareTo(cma2.getMessageThread().getModifiedDatetime());
                }
            });

            SkeletonMessageThread.getInstance().getData().clear();
            SkeletonMessageThread.getInstance().getData().addAll(mArr);

            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageAllAdapter.notifyDataSetChanged();
                    mPbLoadThread.setVisibility(View.GONE);
                    mLayoutEmpty.setVisibility(View.GONE);
                }
            });
        }
    }

    private boolean checkRealmIsEmpty(Realm realm) {
        return realm.where(ConsumerMessageAll.class).findAll().size() == 0;
    }

    private void getAllParticipants() {
        jArrParticipants = new JSONArray();
        for (MessageThread thread : mArrMessageThreadList) {
            for (String partId : thread.getParticipants()) {
                if (!MessageUser.getInstance().getData().getId().equals(partId)) {
                    jArrParticipants.put(partId);
                }
            }
        }
    }

    private void setUserDataEachThread(ArrayList<MessageUserData> arrMessageUserData) {
        int n = mArrMessageThreadList.size();
        RealmList<MessageUserData> messageUserDatas;
        for (int i = 0; i < n; i++) {
            int m = mArrMessageThreadList.get(i).getParticipants().size();
            messageUserDatas = new RealmList<>();
            for (int j = 0; j < m; j++) {
                String idParticipants = mArrMessageThreadList.get(i).getParticipants().get(j);
                if (!MessageUser.getInstance().getData().getId().equals(idParticipants)) {
                    for (MessageUserData mud : arrMessageUserData) {
                        if (mud.getId().equals(idParticipants)) {
                            messageUserDatas.add(mud);
                            break;
                        }
                    }
                }
            }
            mArrMessageThreadList.get(i).setUserInThread(messageUserDatas);
        }
    }

    private void deleteAllMessagesAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.delete(ConsumerMessageAll.class);
            }
        });
    }

    private void saveDataToRealm(Realm realm, final ArrayList<ConsumerMessageAll> mArrMessage) {
        realm.executeTransaction(new Realm.Transaction() {
            ConsumerMessageAll message;
            MessageThread thread;
            MessageThreadView view;
            MessageUserData user;
            RealmList<MessageUserData> arrUser;
            RealmList<String> participants;

            @Override
            public void execute(@NonNull Realm realm) {
                for (ConsumerMessageAll mess : mArrMessage) {
                    arrUser = new RealmList<>();
                    for (MessageUserData us : mess.getMessageThread().getUserInThread()) {
                        user = realm.createObject(MessageUserData.class);
                        user.setId(us.getId());
                        if (us.getData() != null)
                            user.setData(us.getData());
                        user.setName(us.getName());
                        user.setEmail(us.getEmail());
                        user.setAvatar(us.getAvatar());
                        arrUser.add(user);
                    }

                    if (mess.getMessageThread().getMessageThreadView() != null) {
                        view = realm.createObject(MessageThreadView.class);
                        view.setName(mess.getMessageThread().getMessageThreadView().getName());
                        view.setContent(mess.getMessageThread().getMessageThreadView().getContent());
                        view.setPhoto(mess.getMessageThread().getMessageThreadView().getPhoto());
                        view.setCreatedDatetime(mess.getMessageThread().getMessageThreadView().getCreatedDatetime());
                    }

                    participants = new RealmList<>();
                    for (String part : mess.getMessageThread().getParticipants()) {
                        participants.add(part);
                    }
                    thread = realm.createObject(MessageThread.class);
                    thread.setId(mess.getMessageThread().getId());
                    thread.setCreatedDatetime(mess.getMessageThread().getCreatedDatetime());
                    thread.setModifiedDatetime(mess.getMessageThread().getModifiedDatetime());
                    if (mess.getMessageThread().getCreatedBy() != null)
                        thread.setCreatedBy(mess.getMessageThread().getCreatedBy());
                    if (mess.getMessageThread().getModifiedBy() != null)
                        thread.setCreatedBy(mess.getMessageThread().getModifiedBy());
                    if (mess.getMessageThread().getName() != null)
                        thread.setName(mess.getMessageThread().getName());
                    thread.setParticipants(participants);
                    thread.setMessageThreadView(view);
                    thread.setUserInThread(arrUser);
                    message = realm.createObject(ConsumerMessageAll.class);
                    message.setMessageThread(thread);
                    message.setType(mess.getType());

                    Log.e(TAG, "saveDataToRealm success Id: " + mess.getMessageThread().getId());
                }
            }
        });
    }

    private void saveNewThread(final ConsumerMessageAll consumerMessageAll, boolean isNew) {
        Realm realm = HomeCaravanApplication.getInstance().getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insertOrUpdate(consumerMessageAll);
                realm.commitTransaction();
            }
        });

    }

    @Override
    public void deleteThread(int position, String threadId) {
        showPopupConfirm(position, threadId);
    }

    private void showPopupConfirm(final int position, final String threadId) {
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_confirm, null);
        TextView tvMessage = (TextView) view1.findViewById(R.id.tvMessage);
        tvMessage.setText("Are you sure you want to remove this conversation?");
        FrameLayout frmButtonNo = (FrameLayout) view1.findViewById(R.id.frmButtonNo);
        FrameLayout frmButtonYes = (FrameLayout) view1.findViewById(R.id.frmButtonYes);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(view1).create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.TeamTabSearchDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.CENTER;
        wmlp.y = -200;   //y position

        frmButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkConnected()) {
                    showSnackBar(mLayoutMessageThread, TypeDialog.WARNING, R.string.no_network, "no-internet");
                    return;
                }

                mGetAllThreadPresenter.deleteThread(threadId, MessageUser.getInstance().getData().getId());

                SkeletonMessageThread.getInstance().getData().remove(position);
                mMessageAllAdapter.notifyItemRemoved(position);
                mMessageAllAdapter.notifyItemRangeRemoved(position, SkeletonMessageThread.getInstance().getData().size());

                Realm realm = Realm.getDefaultInstance();
                mGetAllThreadPresenter.deleteThreadFromRealm(realm, threadId);
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

}
