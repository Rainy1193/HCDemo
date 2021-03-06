package com.homecaravan.android.consumer.consumerdiscover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.activity.ContactsManagerActivity;
import com.homecaravan.android.consumer.adapter.ContactManagerAdapter;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumerbase.ConsumerUser;
import com.homecaravan.android.consumer.consumermvp.searchmvp.AddParticipantSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.AddParticipantSearchView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.RemoveParticipantSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.RemoveParticipantSearchView;
import com.homecaravan.android.consumer.consumermvp.searchmvp.UpdateParticipantPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.UpdateParticipantView;
import com.homecaravan.android.consumer.listener.IContactManager;
import com.homecaravan.android.consumer.listener.IUpdateSavedSearchListener;
import com.homecaravan.android.consumer.model.ContactManagerData;
import com.homecaravan.android.consumer.model.ContactSingleton;
import com.homecaravan.android.consumer.model.EventContactManager;
import com.homecaravan.android.consumer.model.TypeDialog;
import com.homecaravan.android.consumer.model.responseapi.Participant;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.AnimUtils;
import com.homecaravan.android.models.CurrentSaveSearch;
import com.homecaravan.android.ui.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;


public class FragmentTeamSavedSearch extends BaseFragment implements IContactManager, UpdateParticipantView, AddParticipantSearchView,
        RemoveParticipantSearchView {
    private IUpdateSavedSearchListener mListener;
    private ArrayList<Participant> mParticipants = new ArrayList<>();
    private ArrayList<ContactManagerData> mArrContact = new ArrayList<>();
    private UpdateParticipantPresenter mUpdateParticipantPresenter;
    private AddParticipantSearchPresenter mAddParticipantSearchPresenter;
    private RemoveParticipantSearchPresenter mRemoveParticipantSearchPresenter;
    private ContactManagerAdapter mAdapter;
    private int REQUEST_ADD_CONTACT = 1;
    private String mRole = "readonly";
    private String mWeight = "1";
    private int mPositionEdit;
    private boolean mIsAdmin;
    @Bind(R.id.rvCollaborator)
    RecyclerView mRvCollaborator;
    @Bind(R.id.layoutEditContact)
    RelativeLayout mLayoutEditContact;
    @Bind(R.id.layoutEditContactContent)
    LinearLayout mLayoutEditContactContent;
    @Bind(R.id.ivContact)
    CircleImageView mIvContact;
    @Bind(R.id.tvName)
    TextView mNameContact;
    @Bind(R.id.layoutAdmin)
    RelativeLayout mLayoutAdmin;
    @Bind(R.id.tvAdmin)
    TextView mTvAdmin;
    @Bind(R.id.layoutMember)
    RelativeLayout mLayoutMember;
    @Bind(R.id.tvMember)
    TextView mTvMember;
    @Bind(R.id.layoutNormal)
    RelativeLayout mLayoutNormal;
    @Bind(R.id.tvNormal)
    TextView mTvNormal;

    @Bind(R.id.layoutVeryImportant)
    RelativeLayout mLayoutVeryImportant;
    @Bind(R.id.tvVeryImportant)
    TextView mTvVeryImportant;
    @Bind(R.id.layoutImportant)
    RelativeLayout mLayoutImportant;
    @Bind(R.id.tvImportant)
    TextView mTvImportant;
    @Bind(R.id.layoutReadonly)
    RelativeLayout mLayoutReadOnly;
    @Bind(R.id.tvReadonly)
    TextView mTvReadOnly;
    @Bind(R.id.layoutAddTeam)
    LinearLayout mLayoutAddTeam;

    @OnClick(R.id.layoutRemove)
    public void onLayoutRemove() {

    }

    @OnClick(R.id.layoutUpdate)
    public void onLayoutUpdate() {
        mArrContact.get(mPositionEdit).setRole(mRole);
        mArrContact.get(mPositionEdit).setWeight(mWeight);
        AnimUtils.hideViewFromBottom(mLayoutEditContactContent);
        mLayoutEditContact.setVisibility(View.GONE);
        mUpdateParticipantPresenter.updateParticipant(CurrentSaveSearch.getInstance().getId(), mArrContact.get(mPositionEdit).getId(), mWeight, mRole);

    }

    @OnClick(R.id.layoutAdmin)
    public void onLayoutAdmin() {
        mRole = "admin";
        pickAdmin();
    }

    @OnClick(R.id.layoutMember)
    public void onLayoutMember() {
        mRole = "vote";
        pickMember();
    }

    @OnClick(R.id.layoutReadonly)
    public void onLayoutReadonly() {
        mRole = "readonly";
        pickReadOnly();
    }

    @OnClick(R.id.layoutVeryImportant)
    public void onLayoutVeryImportant() {
        mWeight = "1";
        pickVeryImportant();
    }

    @OnClick(R.id.layoutImportant)
    public void onLayoutImportant() {
        mWeight = "2";
        pickImportant();
    }

    @OnClick(R.id.layoutNormal)
    public void onLayoutNormal() {
        mWeight = "3";
        pickNormal();
    }

    @OnClick(R.id.layoutEditContact)
    public void onLayoutEditContact() {
        AnimUtils.hideViewFromBottom(mLayoutEditContactContent);
        mLayoutEditContact.setVisibility(View.GONE);
    }

    @OnClick(R.id.layoutEditContactContent)
    public void onLayoutEditContactContent() {

    }


    @OnClick(R.id.layoutAddTeam)
    public void addTeam() {

        ContactSingleton.getInstance().getArrContact().clear();
        ContactSingleton.getInstance().getArrContact().addAll(mArrContact);
        Intent intent = new Intent(getContext(), ContactsManagerActivity.class);
        startActivityForResult(intent, REQUEST_ADD_CONTACT);
        getActivity().overridePendingTransition(R.anim.anim_open_activity_left, R.anim.anim_open_activity_right);
    }

    public void setListener(IUpdateSavedSearchListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        mAdapter = new ContactManagerAdapter(mArrContact, getActivity(), this);
        mRvCollaborator.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvCollaborator.setAdapter(mAdapter);
        mUpdateParticipantPresenter = new UpdateParticipantPresenter(this);
        mAddParticipantSearchPresenter = new AddParticipantSearchPresenter(this);
        mRemoveParticipantSearchPresenter = new RemoveParticipantSearchPresenter(this);
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventContact(EventContactManager contactManager) {
        if (contactManager.status.equalsIgnoreCase("update")) {
            for (int i = 0; i < mArrContact.size(); i++) {
                mUpdateParticipantPresenter.updateParticipant(CurrentSaveSearch.getInstance().getId(), contactManager.contactManagerData.getId(),
                        contactManager.contactManagerData.getWeight(), contactManager.contactManagerData.getRole());
            }
        } else {
            for (int i = 0; i < mArrContact.size(); i++) {
                mRemoveParticipantSearchPresenter.removeParticipant(CurrentSaveSearch.getInstance().getId(), contactManager.contactManagerData.getId());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_team_saved_search;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<ContactManagerData> arr = ContactSingleton.getInstance().getArrContact();
                int count = 0;
                for (int i = 0; i < arr.size(); i++) {
                    for (int j = 0; j < mArrContact.size(); j++) {
                        if (!arr.get(i).getId().equalsIgnoreCase(mArrContact.get(j).getId())) {
                            count++;
                        }
                    }
                    if (count == mArrContact.size()) {
                        Log.e("arr", arr.get(i).toString());
                        addCreateCollaborator(arr.get(i));
                    }
                }
                mArrContact.clear();
                mArrContact.addAll(ContactSingleton.getInstance().getArrContact());
                mAdapter.notifyDataSetChanged();
//                mArrContact.clear();
//                mArrContact.addAll(arr);
//                mAdapter.notifyDataSetChanged();
                //addCreateCollaborator();
            }
        }
    }

    public void addCreateCollaborator(ContactManagerData managerData) {

        String fName, lName = "", role = "admin", weight = "1", email = "", phone = "";
        String fullName = managerData.getName();
        if (fullName.contains(" ")) {
            fName = fullName.substring(0, fullName.indexOf(" "));
            lName = fullName.substring(fullName.indexOf(" ") + 1, fullName.length());
        } else {
            fName = fullName;
        }
        if (managerData.getRole() != null) {
            role = managerData.getRole();
        }
        if (managerData.getWeight() != null) {
            weight = managerData.getWeight();
        }
        if (managerData.getEmail() != null) {
            email = managerData.getEmail();
        }
        if (managerData.getPhone() != null) {
            phone = managerData.getPhone();
        }
        mAddParticipantSearchPresenter.addParticipant(fName, lName, email,
                phone, role, weight, CurrentSaveSearch.getInstance().getId(), "");
    }

    public void updatePager() {
        ContactSingleton.getInstance().getArrContact().clear();
        mParticipants = CurrentSaveSearch.getInstance().getSearchDetail().getParticipants().getData();
        for (Participant participant : mParticipants) {
            ContactManagerData contactManagerData = new ContactManagerData();
            contactManagerData.setId(participant.getId());
            contactManagerData.setName(participant.getFullName());
            contactManagerData.setAvatar(participant.getAvatar());
            contactManagerData.setEmail(participant.getEmail());
            contactManagerData.setPhone(participant.getPhone());
            contactManagerData.setWeight(participant.getWeight());
            contactManagerData.setRole(participant.getRole());

            mArrContact.add(contactManagerData);
        }
        mAdapter.notifyDataSetChanged();
        for (int i = 0; i < mArrContact.size(); i++) {
            if (mArrContact.get(i).getId().equalsIgnoreCase(ConsumerUser.getInstance().getData().getId())) {
                if (mArrContact.get(i).getRole().equalsIgnoreCase("admin")) {
                    mIsAdmin = true;
                }
            }
        }
        if (!mIsAdmin) {
            mLayoutAddTeam.setVisibility(View.GONE);
        }
    }

    public void clearPager() {
        mIsAdmin = false;
        mParticipants.clear();
        mArrContact.clear();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void editContact(ContactManagerData managerData, int position) {
        if (mIsAdmin) {
            mPositionEdit = position;
            Glide.with(this).load(managerData.getAvatar())
                    .asBitmap().fitCenter().placeholder(R.drawable.avatar_default)
                    .dontAnimate().into(mIvContact);
            if (!managerData.getName().isEmpty()) {
                mNameContact.setText(managerData.getName());
            } else {
                if (managerData.getPhone() != null) {
                    mNameContact.setText(managerData.getPhone());
                } else {
                    mNameContact.setText(managerData.getEmail());
                }
            }
            if (managerData.getRole() != null) {
                if (managerData.getRole().equalsIgnoreCase("admin")) {
                    pickAdmin();
                }
                if (managerData.getRole().equalsIgnoreCase("readonly")) {
                    pickReadOnly();
                }
                if (managerData.getRole().equalsIgnoreCase("vote")) {
                    pickMember();
                }
            }
            if (managerData.getWeight() != null) {
                if (managerData.getWeight().equalsIgnoreCase("1")) {
                    pickVeryImportant();
                }
                if (managerData.getWeight().equalsIgnoreCase("2")) {
                    pickImportant();
                }
                if (managerData.getWeight().equalsIgnoreCase("3")) {
                    pickNormal();
                }
            }
            mLayoutEditContact.setVisibility(View.VISIBLE);
            AnimUtils.showViewFromBottom(mLayoutEditContactContent);
        } else {
            showSnackBar(getView(), TypeDialog.WARNING, "You not have permission to use this action!", "action contact");
        }
    }

    @Override
    public void pickContact(ContactManagerData managerData, String id, int position, boolean b) {

    }

    @Override
    public void removeContact(String id) {

    }

    @Override
    public void updateContact(String id) {

    }

    public void pickAdmin() {
        mTvAdmin.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mLayoutAdmin.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter));
        mTvMember.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutMember.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvReadOnly.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutReadOnly.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
    }

    public void pickMember() {
        mTvAdmin.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutAdmin.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvMember.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mLayoutMember.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter));
        mTvReadOnly.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutReadOnly.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
    }

    public void pickReadOnly() {
        mTvAdmin.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutAdmin.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvMember.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutMember.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvReadOnly.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mLayoutReadOnly.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter));
    }

    public void pickVeryImportant() {
        mTvVeryImportant.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mLayoutVeryImportant.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter));
        mTvImportant.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutImportant.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvNormal.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutNormal.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
    }

    public void pickImportant() {
        mTvVeryImportant.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutVeryImportant.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvImportant.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mLayoutImportant.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter));
        mTvNormal.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutNormal.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
    }

    public void pickNormal() {
        mTvVeryImportant.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutVeryImportant.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvImportant.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextMain));
        mLayoutImportant.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter1));
        mTvNormal.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        mLayoutNormal.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_button_filter));
    }

    @Override
    public void updateParticipantSuccess(SearchDetail searchDetail) {
        showSnackBar(getView(), TypeDialog.SUCCESS, "Update Participant Success", "updateParticipant");
    }

    @Override
    public void updateParticipantFail(String message) {
        showSnackBar(getView(), TypeDialog.WARNING, "Update Participant Fail", "updateParticipant");
    }

    @Override
    public void updateParticipantFail(@StringRes int message) {
        showSnackBar(getView(), TypeDialog.SUCCESS, message, "updateParticipant");
    }

    @Override
    public void addParticipantSuccess(SearchDetail searchDetail) {

    }

    @Override
    public void addParticipantFail(String message) {

    }

    @Override
    public void addParticipantFail(@StringRes int message) {

    }

    @Override
    public void removeParticipantSuccess(SearchDetail searchDetail) {

    }

    @Override
    public void removeParticipantFail(String message) {

    }

    @Override
    public void removeParticipantFail(@StringRes int message) {

    }
}
