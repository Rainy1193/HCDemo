package com.homecaravan.android.consumer.activity;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.homecaravan.android.R;
import com.homecaravan.android.adapter.FriendListAdapter;
import com.homecaravan.android.adapter.FriendListPickAdapter;
import com.homecaravan.android.consumer.base.BaseActivity;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.contactmvp.CreateContactPresenter;
import com.homecaravan.android.consumer.consumermvp.contactmvp.CreateContactView;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.EventContact;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.listitem.FriendListItem;
import com.homecaravan.android.consumer.model.responseapi.ContactData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class FriendListActivity extends BaseActivity implements FriendListAdapter.IPickFriend, CreateContactView {
    private FriendListAdapter mFriendListAdapter;
    private FriendListPickAdapter mFriendListPickAdapter;
    private ArrayList<FriendListItem> mArrFriend = new ArrayList<>();
    private ArrayList<FriendListItem> mArrFriendPick = new ArrayList<>();
    private CreateContactPresenter mCreateContactPresenter;
    private int arr[] = {R.drawable.bg_avatar1, R.drawable.bg_avatar2, R.drawable.bg_avatar3,
            R.drawable.bg_avatar4, R.drawable.bg_avatar5, R.drawable.bg_avatar6};
    private int mCountRequest;
    @Bind(R.id.rvFriendList)
    RecyclerView mFriendList;
    @Bind(R.id.rvFriendListPicked)
    RecyclerView mFriendListPicked;
    @Bind(R.id.ivSendInvite)
    ImageView mIvSendInvite;
    @Bind(R.id.ivClose)
    ImageView mIvClose;
    @Bind(R.id.etSearch)
    EditText mEtSearch;

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (editable.length() != 0) {
            mIvClose.setVisibility(View.VISIBLE);
            filterListFriend(editable.toString());
        } else {
            filterListFriend("");
            mIvClose.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.ivClose)
    public void cancelSearch() {
        mFriendListAdapter.updateList(mArrFriend);
        mEtSearch.setText("");
    }

    @OnClick(R.id.ivBack)
    public void back() {
        onBackPressed();
    }

    @OnClick(R.id.ivSendInvite)
    public void sendInvite() {
        showLoading();
        mCountRequest = mArrFriendPick.size();
        for (int i = 0; i < mArrFriendPick.size(); i++) {
            //mCreateContactPresenter.createContact(mArrFriendPick.get(i).getName(), mArrFriendPick.get(i).getPhone(), "", "", ConsumerUser.getInstance().getData().getId());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpListContact();
        mCreateContactPresenter = new CreateContactPresenter(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_friend_list;
    }

    public void setUpListContact() {
        mFriendListAdapter = new FriendListAdapter(mArrFriend, this, this);
        mFriendListPickAdapter = new FriendListPickAdapter(mArrFriendPick, this);
        mFriendList.setLayoutManager(new LinearLayoutManager(this));
        mFriendList.setAdapter(mFriendListAdapter);
        mFriendListPicked.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mFriendListPicked.setAdapter(mFriendListPickAdapter);
        mFriendListPicked.setItemAnimator(null);
        mFriendList.setItemAnimator(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                readContacts();
            }
        }, 1000);
        mFriendList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard();
                return false;
            }
        });
    }

    private void readContacts() {

        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor contactsCursor = getContentResolver().query(uri, null, null,
                null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");
        if (contactsCursor == null) {
            return;
        }
        if (contactsCursor.moveToFirst()) {
            do {
                long id = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"));
                Uri dataUri = ContactsContract.Data.CONTENT_URI;
                Cursor dataCursor = getContentResolver().query(dataUri, null,
                        ContactsContract.Data.CONTACT_ID + " = " + id, null, null);
                String displayName = "";
                String mobilePhone = "";
                String photoPath = "";
                String contactNumbers = "";
                String contactEmailAddresses = "";
                String contactOtherDetails = "";
                if (dataCursor == null) {
                    break;
                }
                if (dataCursor.moveToFirst()) {
                    displayName = dataCursor
                            .getString(dataCursor
                                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    do {
                        if (dataCursor.getString(dataCursor.getColumnIndex("mimetype"))
                                .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                            switch (dataCursor.getInt(dataCursor
                                    .getColumnIndex("data2"))) {

                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    mobilePhone = dataCursor.getString(dataCursor
                                            .getColumnIndex("data1"));
                                    contactNumbers = mobilePhone;
                                    break;

                            }
                        }
                    }

                    while (dataCursor.moveToNext());
                    if (contactNumbers.length() != 0) {
                        String name = displayName.trim();
                        if (name.contains(" ")) {
                            name = displayName.substring(0, 1) + displayName.substring(displayName.indexOf(" ") + 1, displayName.indexOf(" ") + 2);
                        } else {
                            name = displayName.substring(0, 1);
                        }
                        mArrFriend.add(new FriendListItem(new Random().nextInt(), false, "", displayName, name.toUpperCase(), contactNumbers, "", getRandom(arr)));
                        mFriendListAdapter.notifyDataSetChanged();
                    }
                }
                dataCursor.close();
            } while (contactsCursor.moveToNext());
        }
        contactsCursor.close();
    }


    @Override
    public void pickFriend(int position, boolean b) {
        if (b) {
            mArrFriendPick.add(mArrFriend.get(position));
        } else {
            mArrFriendPick.remove(mArrFriend.get(position));
        }
        if (mArrFriendPick.size() > 0) {
            mFriendListPicked.setVisibility(View.VISIBLE);
            mIvSendInvite.setColorFilter(ContextCompat.getColor(this, R.color.color_send_invite));
        } else {
            mFriendListPicked.setVisibility(View.GONE);
            mIvSendInvite.setColorFilter(ContextCompat.getColor(this, R.color.colorWhite));
        }
        mFriendListPickAdapter.notifyDataSetChanged();
        mFriendListPicked.post(new Runnable() {
            @Override
            public void run() {
                mFriendListPicked.smoothScrollToPosition(mArrFriendPick.size());
            }
        });
    }

    public int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public void filterListFriend(String s) {
        ArrayList<FriendListItem> friendListItems = new ArrayList<>();
        for (FriendListItem friend : mArrFriend) {
            if (friend.getName() != null) {
                String fullName = friend.getName();
                if (fullName.toLowerCase().contains(s)) {
                    friendListItems.add(friend);
                }
            } else {
                if (friend.getPhone() != null) {
                    String phone = friend.getPhone();
                    if (phone.toLowerCase().contains(s)) {
                        friendListItems.add(friend);
                    }
                }
            }
        }
        mFriendListAdapter.updateList(friendListItems);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_close_activity_left, R.anim.anim_close_activity_right);
        hideKeyboard();
    }

    @Override
    public void createContactSuccess(ContactData data) {
//        mCountRequest--;
//        if (mCountRequest == 0) {
//            hideLoading();
//            showSnackBar(findViewById(R.id.layoutMain), TypeDialog.SUCCESS, "Create contact success", "createContact");
//        }
//        ContactManagerData contactManagerData = new ContactManagerData();
//        contactManagerData.setId(data.getId1());
//        contactManagerData.setAvatar(data.getAvatar());
//        contactManagerData.setEmail(data.getEmail());
//        contactManagerData.setPhone(data.getPhone());
//        contactManagerData.setUid(data.getUser());
//        contactManagerData.setName(data.getName());
//        contactManagerData.setPick(true);
//        ContactSingleton.getInstance().getArrContact().add(contactManagerData);
//        EventBus.getDefault().post(new EventContact("new", contactManagerData));
    }

    @Override
    public void createContactFail(@StringRes int message) {
        hideLoading();
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.SUCCESS, message, "createContact");
    }

    @Override
    public void createContactFail(String message) {
        hideLoading();
        showSnackBar(findViewById(R.id.layoutMain), TypeDialog.SUCCESS, message, "createContact");
    }
}
