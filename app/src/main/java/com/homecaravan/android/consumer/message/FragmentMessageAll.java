package com.homecaravan.android.consumer.message;

import android.app.AlertDialog;
import android.content.res.Resources;
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
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.message.leavethreadmvp.ILeaveThreadView;
import com.homecaravan.android.consumer.message.leavethreadmvp.LeaveThreadPresenter;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.GetAllThreadPresenter;
import com.homecaravan.android.consumer.message.messagegetallthreadmvp.IGetAllThreadView;
import com.homecaravan.android.consumer.message.messageloginmvp.ILoginView;
import com.homecaravan.android.consumer.message.messageloginmvp.LoginPresenter;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.message.Mapping;
import com.homecaravan.android.consumer.model.message.MessageItem;
import com.homecaravan.android.consumer.model.message.MessageThread;
import com.homecaravan.android.consumer.model.message.MessageThreadView;
import com.homecaravan.android.consumer.model.message.MessageUser;
import com.homecaravan.android.consumer.model.message.MessageUserData;
import com.homecaravan.android.consumer.model.message.SkeletonMessageThread;
import com.homecaravan.android.consumer.model.message.reponse.MessageAddResponse;
import com.homecaravan.android.consumer.model.message.reponse.ThreadDeleteResponse;
import com.homecaravan.android.consumer.model.message.reponse.ThreadUpdateResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.homecaravan.android.HomeCaravanApplication.TAG;

/**
 * Created by Anh Dao on 10/5/2017.
 * Messages All Tab
 */

public class FragmentMessageAll extends BaseFragment implements IGetAllThreadView,
        IMessageThreadAction,
        ILeaveThreadView,
        ILoginView {

    private MessageAllAdapter mMessageAllAdapter;
    private GetAllThreadPresenter mGetAllThreadPresenter;
    private LeaveThreadPresenter mLeaveThreadPresenter;
    private boolean mFirstOpenMessageAll = true;
    private Realm mRealm;
    private ArrayList<MessageThread> mArrThread = new ArrayList<>();
    private Resources mResources;

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
        mLeaveThreadPresenter = new LeaveThreadPresenter(this);

        setupAdapter();

        mResources = getResources();

        mRealm = HomeCaravanApplication.getInstance().getRealm();


        if (HomeCaravanApplication.mSocket.connected() && HomeCaravanApplication.mLoginSocketSuccess) {
            getAllThread();
        } else {
            if (HomeCaravanApplication.isNetAvailable(getActivity())) {
                Log.e(TAG, "FragmentAll: Socket: " + HomeCaravanApplication.mSocket.connected());
                reLogin();
            } else {
                loadDataFromRealm(mRealm);
            }
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_message_all;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e(TAG, "Fragement --- onResume");
//        if (mFirstOpenMessageAll) {
//            mFirstOpenMessageAll = false;
//        } else {
//            if (!HomeCaravanApplication.isNetAvailable(getActivity())) {
//                loadDataFromRealm(mRealm);
//            } else {
//                mArrThread.clear();
//                if (SkeletonMessageThread.getInstance().getData().size() != 0) {
//                    mArrThread.addAll(SkeletonMessageThread.getInstance().getData());
//                    mPbLoadThread.setVisibility(View.GONE);
//                    mLayoutEmpty.setVisibility(View.GONE);
//                } else {
//                    mPbLoadThread.setVisibility(View.GONE);
//                    mLayoutEmpty.setVisibility(View.VISIBLE);
//                }
//                mMessageAllAdapter.notifyDataSetChanged();
//            }
//        }
    }

    @Override
    public void getAllThreadSuccess(ArrayList<MessageThread> data) {

        mArrThread.clear();
        mArrThread.addAll(data);

        SkeletonMessageThread.getInstance().getData().clear();
        SkeletonMessageThread.getInstance().getData().addAll(data);

        if (mArrThread.size() != 0) {
            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageAllAdapter.notifyDataSetChanged();
                    mLayoutEmpty.setVisibility(View.GONE);
                    mPbLoadThread.setVisibility(View.GONE);
                }
            });

            Realm realm = Realm.getDefaultInstance();
            deleteAllMessagesAll(realm);
            saveDataToRealm(realm, mArrThread);
        } else {
            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPbLoadThread.setVisibility(View.GONE);
                    mLayoutEmpty.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void getAllThreadFail() {
        mArrThread.clear();
        if (getActivity() == null)
            return;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), "Failed to get data from server", Toast.LENGTH_SHORT).show();
                mLayoutEmpty.setVisibility(View.VISIBLE);
                mPbLoadThread.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void leaveThreadSuccess(String threadId, final int position) {
        mArrThread.remove(position);
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageAllAdapter.notifyItemRemoved(position);
                    mMessageAllAdapter.notifyItemRangeRemoved(position, mArrThread.size());
                }
            });
        }
        Realm realm = Realm.getDefaultInstance();
        mLeaveThreadPresenter.deleteThreadFromRealm(realm, threadId);
    }

    @Override
    public void leaveThreadFail() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Can't leave this conversation", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void deleteThread(int position, String threadId) {
        showPopupConfirm(position, threadId);
    }

    @Override
    public void unRead(int position, String threadId, String timeStamp) {
        mGetAllThreadPresenter.threadUnRead(threadId);
    }

    @Override
    public void loginSuccess() {
        Log.e(TAG, "FragmentAll: loginSuccess");
        HomeCaravanApplication.mLoginSocketSuccess = true;
        getAllThread();
    }

    @Override
    public void loginFail() {
        Log.e(TAG, "FragmentAll: loginFail");
        HomeCaravanApplication.mLoginSocketSuccess = false;
        Realm mRealm = HomeCaravanApplication.getInstance().getRealm();
        loadDataFromRealm(mRealm);
    }

    private void reLogin() {
        HomeCaravanApplication.mSocket.connect();
        if (ConsumerUser.getInstance().getData().getPnUID() == null || ConsumerUser.getInstance().getData().getPnUID().isEmpty()) {
            HomeCaravanApplication.mLoginSocketSuccess = false;
            loadDataFromRealm(mRealm);
        } else {
            Log.e(TAG, "FragmentAll loginSocket-ID: " + ConsumerUser.getInstance().getData().getPnUID());
            LoginPresenter mLoginPresenter = new LoginPresenter(this);
            mLoginPresenter.login(ConsumerUser.getInstance().getData().getPnUID());
        }
    }

    private void setupAdapter() {
        mMessageAllAdapter = new MessageAllAdapter(getActivity(), mArrThread,
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
        if (!checkRealmIsEmpty(realm)) {
            Log.e(TAG, "loadDataFromRealm: checkRealmIsEmpty = false");
            //Sắp xếp lại vì có tường hợp có các event add thêm hoặc edit thread
            RealmResults<MessageThread> mArr = realm.where(MessageThread.class)
                    .findAllSorted("modifiedDatetime", Sort.DESCENDING);
            mArrThread.clear();
            mArrThread.addAll(mArr);
            SkeletonMessageThread.getInstance().getData().clear();
            SkeletonMessageThread.getInstance().getData().addAll(mArrThread);
        }

        if (getActivity() == null)
            return;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mArrThread.size() != 0) {
                    mMessageAllAdapter.notifyDataSetChanged();
                    mLayoutEmpty.setVisibility(View.GONE);
                    mPbLoadThread.setVisibility(View.GONE);
                } else {
                    mLayoutEmpty.setVisibility(View.VISIBLE);
                    mPbLoadThread.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean checkRealmIsEmpty(Realm realm) {
        return realm.where(MessageThread.class).findAll().size() == 0;
    }

    private void deleteAllMessagesAll(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.delete(MessageThread.class);
            }
        });
        Log.e(TAG, "deleteAllMessagesAll: successful");
    }

    private void saveDataToRealm(Realm realm, final ArrayList<MessageThread> mArrThreadList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                realm.insert(mArrThreadList);
            }
        });
        Log.e(TAG, "FragmentAll: Save Thread list to Realm successful");
    }

    private void showPopupConfirm(final int position, final String threadId) {
        LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
        View view1 = layoutInflater1.inflate(R.layout.dialog_consumer_popup_confirm, null);
        TextView tvMessage = (TextView) view1.findViewById(R.id.tvMessage);
        tvMessage.setText(mResources.getString(R.string.confirm_leave_thread));
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
                    alertDialog.dismiss();
                    return;
                }
                mLeaveThreadPresenter.leaveThread(threadId, position);
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

    private void threadUpdateAddParticipants(String command, String key, Object... args) {
        if (command.equals(Constants.getInstance().MESSAGE_COMMAND_UPDATE)
                && key.equals(Constants.getInstance().MESSAGE_KEY_PARTICIPANTS)) {
            ThreadUpdateResponse threadUpdateReponse = new Gson().fromJson(args[0].toString(), ThreadUpdateResponse.class);
            if (threadUpdateReponse != null) {

                int n = mArrThread.size();
                if (n != 0) {
                    for (int i = 0; i < n; i++) {
                        if (mArrThread.get(i).getId()
                                .equals(threadUpdateReponse.getMessageThread().getId())) {

                        }
                    }

                }
            }
        }
    }

    private void threadUpdateLastMessage(String command, String key, Object... args) {
        if (command.equals(Constants.getInstance().MESSAGE_COMMAND_ADD)
                && key.equals(Constants.getInstance().MESSAGE_MESSAGE)) {
            MessageAddResponse response = new Gson().fromJson(args[0].toString(), MessageAddResponse.class);

        }
    }

    private void insertOrUpdateThreadRealm(final MessageThread thread, boolean isNew) {
        Realm realm = HomeCaravanApplication.getInstance().getRealm();
        Log.e(TAG, "-----------saveNewThread-------- ");
        MessageThread mt = realm.where(MessageThread.class)
                .equalTo("id", thread.getId())
                .findFirst();
        realm.beginTransaction();
        if (mt == null) {
            realm.insert(thread);
        } else {
            mt.getMessageThreadView()
                    .setContent(thread.getMessageThreadView().getContent());
        }
        realm.commitTransaction();
    }

    private void removeThreadAfterLeaveRealm(final MessageThread thread) {
        Realm realm = HomeCaravanApplication.getInstance().getRealm();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                MessageThread result = realm.where(MessageThread.class)
                        .equalTo("messageThread.id", thread.getId()).findFirst();
                if (result != null)
                    result.deleteFromRealm();
            }
        });
    }

    private void updateThreadAfterLeaveRealm(final MessageThread thread) {
        Realm realm = HomeCaravanApplication.getInstance().getRealm();
        Log.e(TAG, "-----------saveNewThread-------- ");
        MessageThread mt = realm.where(MessageThread.class)
                .equalTo("id", thread.getId())
                .findFirst();
        if (mt != null) {
//            realm.beginTransaction();
//            mt.setMessageThread(realm.copyToRealm(thread));
//            mt.setType(consumerMessageAll.getType());
//            realm.commitTransaction();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //Message EventBus
    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveMessageListener(MessageAddResponse response) {
        // TODO: 1/3/2018 nhận last message, update thread vao realm roi khi resume thi lay ra
        if(response == null)
            return;
        response.getMessageItem().setCommand(response.getCommand());
        MessageItem message = response.getMessageItem();

        for (MessageThread childCMA : mArrThread) {
            if (childCMA.getId()
                    .equals(message.getMessageThread().getId())) {
                MessageThread thread = new MessageThread(childCMA);
                if (thread.getMessageThreadView() == null) {
                    MessageThreadView view = new MessageThreadView();
                    view.setId(message.getCreatedBy());
                    view.setContent(message.getContent());
                    view.setType(message.getType());
                    thread.setMessageThreadView(view);
                } else {
                    thread.getMessageThreadView().setId(message.getCreatedBy());
                    thread.getMessageThreadView().setContent(message.getContent());
                    thread.getMessageThreadView().setType(message.getType());
                }
                thread.setModifiedDatetime(message.getMessageThread().getModifiedDatetime());

                if (thread.getMappings() != null) {
                    for (Mapping childMap : thread.getMappings()) {
                        if (childMap.getId().equals(MessageUser.getInstance().getData().getId())) {
                            childMap.setmNew(true);
                            childMap.setTime(String.valueOf(System.currentTimeMillis()));
                        }
                    }
                } else {
                    Mapping map = new Mapping();
                    map.setId(MessageUser.getInstance().getData().getId());
                    map.setTime(String.valueOf(System.currentTimeMillis()));
                    map.setmNew(true);
                    RealmList<Mapping> mArrMapping = new RealmList<>();
                    mArrMapping.add(map);
                    thread.setMappings(mArrMapping);
                }

                mArrThread.remove(childCMA);
                mArrThread.add(0, thread);
                mMessageAllAdapter.notifyDataSetChanged();
//                                    insertOrUpdateThreadRealm(thread, false);
                break;
            }
        }
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onOtherLeaveThreadListener(ThreadDeleteResponse response) {
        if(response == null)
            return;

        ArrayList<String> partList = new ArrayList<>();
        int n = mArrThread.size();
        for (int i = 0; i < n; i++) {
            if (mArrThread.get(i).getId().equals(response.getMessageThread().getId())) {
                partList.addAll(mArrThread.get(i).getParticipants());
                partList.removeAll(response.getMessageThread().getParticipants());
                if (partList.size() == 1) {
                    mArrThread.get(i).getParticipants().remove(partList.get(0));

                    for (Iterator<MessageUserData> it = mArrThread.get(i).getUserInThread().iterator(); it.hasNext(); ) {
                        MessageUserData messageUserData = it.next();
                        if (messageUserData.getId().equals(partList.get(0))) {
                            it.remove();
                        }
                    }

                    //bị kích ra khỏi
                    if(partList.get(0).equals(MessageUser.getInstance().getData().getId())){
                        mArrThread.remove(i);
                        mMessageAllAdapter.notifyItemRemoved(i);
                        mMessageAllAdapter.notifyItemRangeRemoved(i, mArrThread.size());
//                                            removeThreadAfterKicked(mArrThread.get(position));
                        return;
                    }

                    //người khác leave
                    mMessageAllAdapter.notifyItemChanged(i);
//                                        updateThreadAfterLeaveRealm(mArrThread.get(position));
                }
                return;
            }
        }
    }

}
