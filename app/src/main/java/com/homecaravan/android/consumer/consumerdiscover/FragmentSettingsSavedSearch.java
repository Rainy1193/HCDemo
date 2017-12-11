package com.homecaravan.android.consumer.consumerdiscover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.homecaravan.android.R;
import com.homecaravan.android.consumer.base.BaseFragment;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SaveSearchPresenter;
import com.homecaravan.android.consumer.consumermvp.searchmvp.SaveSearchView;
import com.homecaravan.android.consumer.listener.IUpdateSavedSearchListener;
import com.homecaravan.android.consumer.model.EventUpdateSearch;
import com.homecaravan.android.consumer.model.responseapi.ConditionFull;
import com.homecaravan.android.consumer.model.responseapi.SearchDetail;
import com.homecaravan.android.consumer.utils.Utils;
import com.homecaravan.android.models.CurrentSaveSearch;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnTextChanged;
import okhttp3.RequestBody;

public class FragmentSettingsSavedSearch extends BaseFragment implements SaveSearchView {
    private IUpdateSavedSearchListener mListener;
    private String mCurrentNameSearch="";
    private SaveSearchPresenter mSaveSearchPresenter;
    @Bind(R.id.etEditName)
    EditText mEditNameSearch;

    public void setListener(IUpdateSavedSearchListener mListener) {
        this.mListener = mListener;
    }

    @OnTextChanged(value = R.id.etEditName, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterSearch(Editable editable) {
        if (editable.length() != 0) {
            mCurrentNameSearch = editable.toString();
        } else {
            mCurrentNameSearch = "New Search";
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSaveSearchPresenter = new SaveSearchPresenter(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_setting_saved_search;
    }

    public void updatePager() {
        mEditNameSearch.setText(CurrentSaveSearch.getInstance().getSearchDetail().getName());
        mEditNameSearch.setSelection(mEditNameSearch.getText().length());
        mCurrentNameSearch = mEditNameSearch.getText().toString();
    }

    public void checkNameSearch() {
        if (CurrentSaveSearch.getInstance().getSearchDetail() != null) {
            if (!mCurrentNameSearch.equalsIgnoreCase(CurrentSaveSearch.getInstance().getSearchDetail().getName())) {
                mSaveSearchPresenter.saveSearch(setRequestParams(mCurrentNameSearch));
            }
        }
    }

    public Map<String, RequestBody> setRequestParams(String searchName) {
        ConditionFull conditionFull = CurrentSaveSearch.getInstance().getSearchDetail().getConditions().get(0);
        Map<String, RequestBody> map = new HashMap<>();
        if (searchName != null) {
            map.put("NAME", Utils.creteRbSearchMap(searchName));
        }
        map.put("id", Utils.creteRbSearchMap(CurrentSaveSearch.getInstance().getId()));
        map.put("ne", Utils.creteRbSearchMap(conditionFull.getNe()));
        map.put("sw", Utils.creteRbSearchMap(conditionFull.getSw()));
        map.put("min_pr", Utils.creteRbSearchMap(conditionFull.getMinPrice() == null ? "" : conditionFull.getMinPrice()));
        map.put("max_pr", Utils.creteRbSearchMap(conditionFull.getMaxPrice() == null ? "" : conditionFull.getMaxPrice()));
        map.put("br", Utils.creteRbSearchMap(conditionFull.getBr() == null ? "" : conditionFull.getBr()));
        map.put("ar", Utils.creteRbSearchMap(conditionFull.getAr() == null ? "" : conditionFull.getAr()));
        map.put("lsf", Utils.creteRbSearchMap(conditionFull.getLsf() == null ? "" : conditionFull.getLsf()));
        map.put("ls", Utils.creteRbSearchMap(conditionFull.getLs() == null ? "" : conditionFull.getLs()));
        map.put("k", Utils.creteRbSearchMap(conditionFull.getKeyword() == null ? "" : conditionFull.getKeyword()));
        map.put("ft", Utils.creteRbSearchMap(conditionFull.getFt() == null ? "" : conditionFull.getFt().toString()));
        map.put("sb", Utils.creteRbSearchMap(conditionFull.getSb() == null ? "" : conditionFull.getSb()));
        map.put("sm", Utils.creteRbSearchMap(conditionFull.getSm() == null ? "" : conditionFull.getSm()));
        map.put("src", Utils.creteRbSearchMap(conditionFull.getSrc() == null ? "" : conditionFull.getSrc()));
        map.put("st", Utils.creteRbSearchMap(conditionFull.getSt() == null ? "" : conditionFull.getSt()));
        map.put("min_ls", Utils.creteRbSearchMap(conditionFull.getMinLs() == null ? "" : conditionFull.getMinLs()));
        map.put("max_ls", Utils.creteRbSearchMap(conditionFull.getMaxLsf() == null ? "" : conditionFull.getMaxLsf()));
        map.put("min_lsf", Utils.creteRbSearchMap(conditionFull.getMinLsf() == null ? "" : conditionFull.getMinLsf()));
        map.put("max_lsf", Utils.creteRbSearchMap(conditionFull.getMaxLsf() == null ? "" : conditionFull.getMaxLsf()));
        map.put("min_yb", Utils.creteRbSearchMap(conditionFull.getMinYb() == null ? "" : conditionFull.getMinYb()));
        map.put("max_yb", Utils.creteRbSearchMap(conditionFull.getMaxYb() == null ? "" : conditionFull.getMaxYb()));
        map.put("dc", Utils.creteRbSearchMap(conditionFull.getDc() == null ? "" : conditionFull.getDc()));
        map.put("pt", Utils.creteRbSearchMap(conditionFull.getPt() == null ? "" : conditionFull.getPt()));
        return map;
    }

    @Override
    public void saveSearchSuccess(SearchDetail searchDetail) {
        CurrentSaveSearch.getInstance().setSearchDetail(searchDetail);
        CurrentSaveSearch.getInstance().setArrParticipant(searchDetail.getParticipants().getData());
        CurrentSaveSearch.getInstance().setId(searchDetail.getId());
        CurrentSaveSearch.getInstance().setName(searchDetail.getName());
        mCurrentNameSearch = searchDetail.getName();
        EventUpdateSearch updateSearch = new EventUpdateSearch();
        updateSearch.setChangeName(true);
        updateSearch.setName(searchDetail.getName());
        EventBus.getDefault().post(updateSearch);
    }

    @Override
    public void saveSearchFail(String message) {

    }

    @Override
    public void saveSearchFail(@StringRes int message) {

    }
}
