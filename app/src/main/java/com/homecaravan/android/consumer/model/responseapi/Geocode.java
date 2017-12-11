package com.homecaravan.android.consumer.model.responseapi;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * The type Geocode.
 * @author Dau Hung
 */
public class Geocode {
    /**
     * The Address.
     */
    @SerializedName("results")
    ArrayList<ResultGeoCode> address;
    /**
     * The Status.
     */
    @SerializedName("status")
    String status;

    /**
     * Gets address.
     *
     * @return the address
     */
    public ArrayList<ResultGeoCode> getAddress() {
        return address;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Geocode{" +
                "address=" + address +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * The type Result geo code.
     */
    public class ResultGeoCode {

        /**
         * The Add components.
         */
        @SerializedName("address_components")
        ArrayList<Components> addComponents;
        /**
         * The Geometry.
         */
        @SerializedName("geometry")
        Geometry geometry;
        /**
         * The Formatted address.
         */
        @SerializedName("formatted_address")
        String formattedAddress;

        /**
         * Gets formatted address.
         *
         * @return the formatted address
         */
        public String getFormattedAddress() {
            return formattedAddress;
        }

        /**
         * Gets geometry.
         *
         * @return the geometry
         */
        public Geometry getGeometry() {
            return geometry;
        }

        @Override
        public String toString() {
            return "ResultGeoCode{" +
                    "geometry=" + geometry +
                    '}';
        }

        /**
         * Gets add components.
         *
         * @return the add components
         */
        public ArrayList<Components> getAddComponents() {
            return addComponents;
        }
    }

    /**
     * The type Components.
     */
    public class Components {
        /**
         * The Long name.
         */
        @SerializedName("long_name")
        String longName;
        /**
         * The Short name.
         */
        @SerializedName("short_name")
        String shortName;
        /**
         * The Types.
         */
        @SerializedName("types")
        ArrayList<String> types;

        /**
         * Gets long name.
         *
         * @return the long name
         */
        public String getLongName() {
            return longName;
        }

        /**
         * Gets short name.
         *
         * @return the short name
         */
        public String getShortName() {
            return shortName;
        }

        /**
         * Gets types.
         *
         * @return the types
         */
        public ArrayList<String> getTypes() {
            return types;
        }



    }

    /**
     * The type Geometry.
     */
    public class Geometry {
        /**
         * The Location.
         */
        @SerializedName("location")
        GeometryLocation location;

        /**
         * Gets location.
         *
         * @return the location
         */
        public GeometryLocation getLocation() {
            return location;
        }

        @Override
        public String toString() {
            return "Geometry{" +
                    "location=" + location +
                    '}';
        }
    }

    /**
     * The type Geometry location.
     */
    public class GeometryLocation {
        /**
         * The Lat.
         */
        @SerializedName("lat")
        String lat;
        /**
         * The Lng.
         */
        @SerializedName("lng")
        String lng;

        /**
         * Gets lat.
         *
         * @return the lat
         */
        public String getLat() {
            return lat;
        }

        /**
         * Gets lng.
         *
         * @return the lng
         */
        public String getLng() {
            return lng;
        }

        @Override
        public String toString() {
            return "GeometryLocation{" +
                    "lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }
    }
}
