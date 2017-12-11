package com.homecaravan.android.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Predictions {
    @SerializedName("predictions")
    @Expose
    private ArrayList<Place> places;

    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public class Place {
        @SerializedName("structured_formatting")
        @Expose
        private StructuredFormatting structuredFormatting;

        public StructuredFormatting getStructuredFormatting() {
            return structuredFormatting;
        }
    }

    public class StructuredFormatting {
        @SerializedName("main_text")
        @Expose
        private String mainText;
        @SerializedName("secondary_text")
        @Expose
        private String secondaryText;

        public String getMainText() {
            return mainText;
        }

        public String getSecondaryText() {
            return secondaryText;
        }
    }
}
