package com.homecaravan.android.consumer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.homecaravan.android.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgentReviewActivity extends AppCompatActivity {

    @Bind(R.id.imgAvatar)
    RoundedImageView mImgAvatar;
    @Bind(R.id.imgLogo)
    ImageView mImgLogo;
    @Bind(R.id.imgFriendliness1)
    ImageView mImgFriendliness1;
    @Bind(R.id.tvFriendliness1)
    TextView mTvFriendliness1;
    @Bind(R.id.imgFriendliness2)
    ImageView mImgFriendliness2;
    @Bind(R.id.tvFriendliness2)
    TextView mTvFriendliness2;
    @Bind(R.id.imgHelpfulness1)
    ImageView mImgHelpfulness1;
    @Bind(R.id.tvHelpfulness1)
    TextView mTvHelpfulness1;
    @Bind(R.id.imgHelpfulness2)
    ImageView mImgHelpfulness2;
    @Bind(R.id.tvHelpfulness2)
    TextView mTvHelpfulness2;
    @Bind(R.id.imgHelpfulness3)
    ImageView mImgHelpfulness3;
    @Bind(R.id.tvHelpfulness3)
    TextView mTvHelpfulness3;
    @Bind(R.id.imgHelpfulness4)
    ImageView mImgHelpfulness4;
    @Bind(R.id.tvHelpfulness4)
    TextView mTvHelpfulness4;
    @Bind(R.id.imgHelpfulness5)
    ImageView mImgHelpfulness5;
    @Bind(R.id.tvHelpfulness5)
    TextView mTvHelpfulness5;
    @Bind(R.id.imgKnowledgably1)
    ImageView mImgKnowledgably1;
    @Bind(R.id.tvKnowledgably1)
    TextView mTvKnowledgably1;
    @Bind(R.id.imgKnowledgably2)
    ImageView mImgKnowledgably2;
    @Bind(R.id.tvKnowledgably2)
    TextView mTvKnowledgably2;
    @Bind(R.id.imgKnowledgably3)
    ImageView mImgKnowledgably3;
    @Bind(R.id.tvKnowledgably3)
    TextView mTvKnowledgably3;
    @Bind(R.id.imgKnowledgably4)
    ImageView mImgKnowledgably4;
    @Bind(R.id.tvKnowledgably4)
    TextView mTvKnowledgably4;
    @Bind(R.id.imgKnowledgably5)
    ImageView mImgKnowledgably5;
    @Bind(R.id.tvKnowledgably5)
    TextView mTvKnowledgably5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_review);
        ButterKnife.bind(this);
    }

    private void hideAllFriendliness(){
        Glide.with(this).load(R.drawable.ic_consumer_agent_review_bad).fitCenter().into(mImgFriendliness1);
        mTvFriendliness1.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_bad).fitCenter().into(mImgFriendliness2);
        mTvFriendliness2.setVisibility(View.INVISIBLE);
    }

    private void hideAllHelpfulness(){
        Glide.with(this).load(R.drawable.ic_consumer_agent_review_terrible).fitCenter().into(mImgHelpfulness1);
        mTvHelpfulness1.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_bad).fitCenter().into(mImgHelpfulness2);
        mTvHelpfulness2.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_okay).fitCenter().into(mImgHelpfulness3);
        mTvHelpfulness3.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_good).fitCenter().into(mImgHelpfulness4);
        mTvHelpfulness4.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_excellent).fitCenter().into(mImgHelpfulness5);
        mTvHelpfulness5.setVisibility(View.INVISIBLE);
    }

    private void hideAllKnowledgably(){
        Glide.with(this).load(R.drawable.ic_consumer_agent_review_terrible).fitCenter().into(mImgKnowledgably1);
        mTvKnowledgably1.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_bad).fitCenter().into(mImgKnowledgably2);
        mTvKnowledgably2.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_okay).fitCenter().into(mImgKnowledgably3);
        mTvKnowledgably3.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_good).fitCenter().into(mImgKnowledgably4);
        mTvKnowledgably4.setVisibility(View.INVISIBLE);

        Glide.with(this).load(R.drawable.ic_consumer_agent_review_excellent).fitCenter().into(mImgKnowledgably5);
        mTvKnowledgably5.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.lnFriendliness1, R.id.lnFriendliness2, R.id.lnHelpfulness1, R.id.lnHelpfulness2, R.id.lnHelpfulness3, R.id.lnHelpfulness4, R.id.lnHelpfulness5, R.id.lnKnowledgably1, R.id.lnKnowledgably2, R.id.lnKnowledgably3, R.id.lnKnowledgably4, R.id.lnKnowledgably5, R.id.frmSetExclusive, R.id.frmMoveOn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lnFriendliness1:
                if(mTvFriendliness1.getVisibility() == View.INVISIBLE){
                    hideAllFriendliness();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_bad_color).fitCenter().into(mImgFriendliness1);
                    mTvFriendliness1.setVisibility(View.VISIBLE);
                } else{
                    hideAllFriendliness();
                }
                break;
            case R.id.lnFriendliness2:
                if(mTvFriendliness2.getVisibility() == View.INVISIBLE){
                    hideAllFriendliness();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_good_color).fitCenter().into(mImgFriendliness2);
                    mTvFriendliness2.setVisibility(View.VISIBLE);
                }else{
                    hideAllFriendliness();
                }
                break;
            case R.id.lnHelpfulness1:
                if(mTvHelpfulness1.getVisibility() == View.INVISIBLE){
                    hideAllHelpfulness();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_terrible_color).fitCenter().into(mImgHelpfulness1);
                    mTvHelpfulness1.setVisibility(View.VISIBLE);
                }else{
                    hideAllHelpfulness();
                }
                break;
            case R.id.lnHelpfulness2:
                if(mTvHelpfulness2.getVisibility() == View.INVISIBLE){
                    hideAllHelpfulness();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_bad_color).fitCenter().into(mImgHelpfulness2);
                    mTvHelpfulness2.setVisibility(View.VISIBLE);
                }else{
                    hideAllHelpfulness();
                }
                break;
            case R.id.lnHelpfulness3:
                if(mTvHelpfulness3.getVisibility() == View.INVISIBLE){
                    hideAllHelpfulness();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_okay_color).fitCenter().into(mImgHelpfulness3);
                    mTvHelpfulness3.setVisibility(View.VISIBLE);
                }else{
                    hideAllHelpfulness();
                }
                break;
            case R.id.lnHelpfulness4:
                if(mTvHelpfulness4.getVisibility() == View.INVISIBLE){
                    hideAllHelpfulness();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_good_color).fitCenter().into(mImgHelpfulness4);
                    mTvHelpfulness4.setVisibility(View.VISIBLE);
                }else{
                    hideAllHelpfulness();
                }
                break;
            case R.id.lnHelpfulness5:
                if(mTvHelpfulness5.getVisibility() == View.INVISIBLE){
                    hideAllHelpfulness();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_excellent_color).fitCenter().into(mImgHelpfulness5);
                    mTvHelpfulness5.setVisibility(View.VISIBLE);
                }else{
                    hideAllHelpfulness();
                }
                break;
            case R.id.lnKnowledgably1:
                if(mTvKnowledgably1.getVisibility() == View.INVISIBLE){
                    hideAllKnowledgably();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_terrible_color).fitCenter().into(mImgKnowledgably1);
                    mTvKnowledgably1.setVisibility(View.VISIBLE);
                }else{
                    hideAllKnowledgably();
                }
                break;
            case R.id.lnKnowledgably2:
                if(mTvKnowledgably2.getVisibility() == View.INVISIBLE){
                    hideAllKnowledgably();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_bad_color).fitCenter().into(mImgKnowledgably2);
                    mTvKnowledgably2.setVisibility(View.VISIBLE);
                }else{
                    hideAllKnowledgably();
                }
                break;
            case R.id.lnKnowledgably3:
                if(mTvKnowledgably3.getVisibility() == View.INVISIBLE){
                    hideAllKnowledgably();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_okay_color).fitCenter().into(mImgKnowledgably3);
                    mTvKnowledgably3.setVisibility(View.VISIBLE);
                }else{
                    hideAllKnowledgably();
                }
                break;
            case R.id.lnKnowledgably4:
                if(mTvKnowledgably4.getVisibility() == View.INVISIBLE){
                    hideAllKnowledgably();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_good_color).fitCenter().into(mImgKnowledgably4);
                    mTvKnowledgably4.setVisibility(View.VISIBLE);
                }else{
                    hideAllKnowledgably();
                }
                break;
            case R.id.lnKnowledgably5:
                if(mTvKnowledgably5.getVisibility() == View.INVISIBLE){
                    hideAllKnowledgably();
                    Glide.with(this).load(R.drawable.ic_consumer_agent_review_excellent_color).fitCenter().into(mImgKnowledgably5);
                    mTvKnowledgably5.setVisibility(View.VISIBLE);
                }else{
                    hideAllKnowledgably();
                }
                break;
            case R.id.frmSetExclusive:
                break;
            case R.id.frmMoveOn:
                break;
        }
    }
}
