package com.homecaravan.android.consumer.consumerdata;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.model.ResponseConsumerAgent;
import com.homecaravan.android.consumer.model.ResponseConsumerHome;
import com.homecaravan.android.consumer.model.ResponseConsumerListing;
import com.homecaravan.android.consumer.model.ResponseConsumerListingFull;
import com.homecaravan.android.consumer.model.ResponseConsumerMapSearch;
import com.homecaravan.android.consumer.model.ResponseConsumerMessage;
import com.homecaravan.android.consumer.model.ResponseConsumerTeam;
import com.homecaravan.android.consumer.model.ResponseConsumerUpcoming;
import com.homecaravan.android.consumer.utils.JSONHandler;

import java.io.IOException;

public class ConsumerDummyData {
    private Context mContext;

    public ConsumerDummyData(Context context) {
        this.mContext = context;
    }

    public void createDummyData() {
        try {
            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();
            ConsumerListingFullData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.cons_listings), jsonParser), ResponseConsumerListingFull.class));
            ConsumerHomeData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.consumer_home), jsonParser), ResponseConsumerHome.class));
            ConsumerMapSearchData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.consumer_map_search), jsonParser), ResponseConsumerMapSearch.class));
            ConsumerMessageData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.consumer_message), jsonParser), ResponseConsumerMessage.class));
            ConsumerTeamData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.cons_teams), jsonParser), ResponseConsumerTeam.class));
            ConsumerListingData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.consumer_trending), jsonParser), ResponseConsumerListing.class));
            ConsumerUpcomingData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.consumer_upcoming), jsonParser), ResponseConsumerUpcoming.class));
            ConsumerAgentData.getInstance().
                    setData(gson.fromJson(createJsonElement(getDataFromJsonFile(R.raw.consumer_agents), jsonParser), ResponseConsumerAgent.class));
        } catch (Exception e) {
            Log.e("Error Create Dummy Data", e.toString());
        }
    }

    private String getDataFromJsonFile(int res) throws IOException {
        return JSONHandler.parseResource(mContext, res);
    }

    private JsonElement createJsonElement(String data, JsonParser jsonParser) {
        return jsonParser.parse(data);
    }
}
