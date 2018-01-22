package com.homecaravan.android.consumer.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.ui.IconGenerator;
import com.homecaravan.android.HomeCaravanApplication;
import com.homecaravan.android.R;
import com.homecaravan.android.consumer.consumerdiscover.FragmentDiscover;
import com.homecaravan.android.consumer.model.ConsumerListingSchedule;
import com.homecaravan.android.consumer.model.CurrentCaravan;
import com.homecaravan.android.consumer.model.CurrentListingSchedule;
import com.homecaravan.android.consumer.model.StatusMarker;
import com.homecaravan.android.consumer.model.responseapi.CaravanJson;
import com.homecaravan.android.consumer.model.responseapi.ClustersSearchMap;
import com.homecaravan.android.consumer.model.responseapi.ContactData;
import com.homecaravan.android.consumer.model.responseapi.ImageListingDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Utils {
    public static int heightScreen = 0;
    public static int widthScreen = 0;

    public static int getColorResources(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    public static SpannableStringBuilder getSpanText(String input, String type) {
        int position = input.length() - 1;
        SpannableStringBuilder cs = new SpannableStringBuilder(input);
        cs.setSpan(new SuperscriptSpan(), position, input.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(0.6f), position, input.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return cs;
    }

    public static SpannableStringBuilder getLinkAgentUnlock(String raw, Context context) {
        int position = raw.lastIndexOf("/") + 1;
        SpannableStringBuilder cs = new SpannableStringBuilder(raw);
        cs.setSpan(new AbsoluteSizeSpan(context.getResources().getDimensionPixelSize(R.dimen.text_size_agent_unlock)), position, raw.length(), 0);
        return cs;
    }

    public static SpannableStringBuilder getNameSavedSearch(String input, Context context) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorMenuConsumer));

        SpannableStringBuilder cs = new SpannableStringBuilder("Saved search: " + input);
        cs.setSpan(colorSpan, 0, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return cs;
    }


    public static int getDrawable(Context context, String name) {
        name = name.substring(0, name.length() - 4);
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static CharSequence getPriceMarker(String price) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(price);
        ssb.setSpan(new ForegroundColorSpan(Color.WHITE), 0, price.length(), 0);
        return ssb;
    }

    public static CharSequence getPositionRoute(String position) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(position);
        ssb.setSpan(new ForegroundColorSpan(Color.BLACK), 0, position.length(), 0);
        return ssb;
    }

    public static CharSequence getTextSelectAgent(Context context, String input, int start, int end) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(input);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_vote_down)), start, end, 0);
        return ssb;
    }

    public static IconGenerator getIconGenerator(IconGenerator iconGenerator, StatusMarker statusMarker, Context context, String price) {

        switch (statusMarker) {
            case SELECTED:
                View view = LayoutInflater.from(context).inflate(R.layout.layout_marker_selected, null, false);
                TextView textView = (TextView) view.findViewById(R.id.tvPrice);
                textView.setText(getPriceFilter(Integer.parseInt(price)));
                iconGenerator.setBackground(null);
                iconGenerator.setContentView(view);
                break;
            case HAVE_BEEN_VIEWED:
                View view1 = LayoutInflater.from(context).inflate(R.layout.layout_marker_view, null, false);
                TextView textView1 = (TextView) view1.findViewById(R.id.tvPrice);
                textView1.setText(getPriceFilter(Integer.parseInt(price)));
                iconGenerator.setBackground(null);
                iconGenerator.setContentView(view1);
                break;
            case HAVE_NOT_BEEN_VIEWED:
                View view2 = LayoutInflater.from(context).inflate(R.layout.layout_marker_not_view, null, false);
                TextView textView2 = (TextView) view2.findViewById(R.id.tvPrice);
                textView2.setText(getPriceFilter(Integer.parseInt(price)));
                iconGenerator.setBackground(null);
                iconGenerator.setContentView(view2);
                break;
        }
        return iconGenerator;
    }

    public static IconGenerator getIconGeneratorRound(IconGenerator iconGenerator, StatusMarker statusMarker, Context context, String price) {

        if (FragmentDiscover.sZoom >= 10) {
            switch (statusMarker) {
                case SELECTED:
                    View view = LayoutInflater.from(context).inflate(R.layout.layout_marker_selected, null, false);
                    TextView textView = (TextView) view.findViewById(R.id.tvPrice);
                    textView.setText(getPriceFilter(Integer.parseInt(price)));
                    iconGenerator.setBackground(null);
                    iconGenerator.setContentView(view);
                    break;
                case HAVE_BEEN_VIEWED:
                    View view1 = LayoutInflater.from(context).inflate(R.layout.layout_marker_view, null, false);
                    TextView textView1 = (TextView) view1.findViewById(R.id.tvPrice);
                    textView1.setText(getPriceFilter(Integer.parseInt(price)));
                    iconGenerator.setBackground(null);
                    iconGenerator.setContentView(view1);
                    break;
                case HAVE_NOT_BEEN_VIEWED:
                    View view2 = LayoutInflater.from(context).inflate(R.layout.layout_marker_not_view, null, false);
                    TextView textView2 = (TextView) view2.findViewById(R.id.tvPrice);
                    textView2.setText(getPriceFilter(Integer.parseInt(price)));
                    iconGenerator.setBackground(null);
                    iconGenerator.setContentView(view2);
                    break;
            }
        } else {
            switch (statusMarker) {
                case SELECTED:
                    View view = LayoutInflater.from(context).inflate(R.layout.layout_marker_selected_round, null, false);
                    iconGenerator.setBackground(null);
                    iconGenerator.setContentView(view);
                    break;
                case HAVE_BEEN_VIEWED:
                    View view1 = LayoutInflater.from(context).inflate(R.layout.layout_marker_view_round, null, false);
                    iconGenerator.setBackground(null);
                    iconGenerator.setContentView(view1);
                    break;
                case HAVE_NOT_BEEN_VIEWED:
                    View view2 = LayoutInflater.from(context).inflate(R.layout.layout_marker_not_view_round, null, false);
                    iconGenerator.setBackground(null);
                    iconGenerator.setContentView(view2);
                    break;
            }
        }
        return iconGenerator;
    }

    public static IconGenerator getIconGeneratorCluster(IconGenerator iconGenerator, ClustersSearchMap clustersSearchMap, Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_cluster_search, null, false);
        TextView textView = (TextView) view.findViewById(R.id.tvCount);
        textView.setText(getListingCluster(clustersSearchMap.getDocCount()));
        iconGenerator.setBackground(null);
        iconGenerator.setContentView(view);
        return iconGenerator;
    }

    public static IconGenerator getIconGeneratorClusterTwoListing(IconGenerator iconGenerator, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_marker_two_listing, null, false);
        iconGenerator.setBackground(null);
        iconGenerator.setContentView(view);
        return iconGenerator;
    }

    public static String renderIdSavedSearch() {
        String ts = String.valueOf(System.currentTimeMillis());
        String rand = UUID.randomUUID().toString();
        return (rand + ts);
    }

    public static String renderIdAgent() {
        String ts = String.valueOf(System.currentTimeMillis());
        String rand = UUID.randomUUID().toString();
        return ("agent" + rand + ts);
    }

    public static String getDateCreate(String data) {
        return data.substring(8, 10) + "-" + data.substring(5, 7) + "-" + data.substring(0, 4);
    }

    public static String getPriceFilter(int price) {
        if (price < 1000) {
            return String.valueOf(price);
        } else if (price > 1000 && price < 1000000) {
            String sf = String.valueOf((float) price / 1000);
            if (price % 1000 == 0) {
                return String.valueOf(price / 1000) + "K";
            }
            return String.valueOf(price / 1000) + "," + sf.substring(sf.indexOf(".") + 1) + "K";
        } else {
            int p = price / 1000000;
            int d = price - p * 1000000;
            if (d == 0) {
                return String.valueOf(price / 1000000) + "M";
            }
            String sf = String.valueOf((float) price / 1000000);
            String m = sf.substring(sf.indexOf(".") + 1);
            if (m.length() > 3) {
                m = m.substring(0, 3);
            }
            return String.valueOf(price / 1000000) + "," + m + "M";
        }
    }

    public static String getListingCluster(int listings) {
        Log.e("listings", String.valueOf(listings));
        if (listings < 1000) {
            return String.valueOf(listings);
        } else if (listings > 1000 && listings < 1000000) {
            String sf = String.valueOf((float) listings / 1000);
            if (listings % 1000 == 0) {
                return String.valueOf(listings / 1000) + "K";
            }
            return String.valueOf(listings / 1000) + " " + "K";
        } else {
            int p = listings / 1000000;
            int d = listings - p * 1000000;
            if (d == 0) {
                return String.valueOf(listings / 1000000) + "M";
            }
            String sf = String.valueOf((float) listings / 1000000);
            String m = sf.substring(sf.indexOf(".") + 1);
            if (m.length() > 3) {
                m = m.substring(0, 3);
            }
            return String.valueOf(listings / 1000000) + " " + "M";
        }
    }

    public static String getPrice(String price) {
        String tem = "";
        int currentPrice = Integer.parseInt(price);
        int intPrice = Integer.parseInt(price);
        int dem = 0;
        while (intPrice > 0) {
            tem = intPrice % 10 + tem;
            dem++;
            if (dem % 3 == 0 && dem != 0) {
                tem = "," + tem;
            }
            intPrice = intPrice / 10;
        }
        if (tem.startsWith(",")) {
            return "$" + tem.substring(1);
        }
        if (currentPrice == 0) {
            return "$0";
        }
        return "$" + tem;
    }

    public static String getTimeRoute(int hour, int min, String haft) {

        String sHour;
        String sMin;
        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }
        if (min < 10) {
            sMin = "0" + String.valueOf(min);
        } else {
            sMin = String.valueOf(min);
        }
        return sHour + ":" + sMin + " " + haft;
    }

    public static String getEndTime(int hour, int min, String haft, int duration) {
        String sHour;
        String sMin;
        min = min + duration;
        if (min >= 60) {
            min = min - 60;
            hour = hour + 1;
            if (hour > 12 && haft.equalsIgnoreCase("AM")) {
                hour = hour - 12;
                haft = "PM";
            }
            if (hour > 12 && haft.equalsIgnoreCase("PM")) {
                hour = hour - 12;
                haft = "AM";
            }
        }

        if (hour < 10) {
            sHour = "0" + String.valueOf(hour);
        } else {
            sHour = String.valueOf(hour);
        }
        if (min < 10) {
            sMin = "0" + String.valueOf(min);
        } else {
            sMin = String.valueOf(min);
        }

        return sHour + ":" + sMin + " " + haft;
    }

    public static LatLng fromScreenToLocation(GoogleMap googleMap, Point point) {
        return googleMap.getProjection().fromScreenLocation(point);
    }

    public static RequestBody creteRbSearchMap(String content) {
        Log.e("content", content);
        return RequestBody.create(MediaType.parse("text/plain"), content);
    }

    public static ArrayList<String> objectToArray(Object object) {
        ArrayList<String> arr = new ArrayList<>();
        String raw = object.toString();
        if (raw.length() == 2) {
            return arr;
        }
        String convert = raw.substring(1, raw.length() - 1);
        String[] s = convert.split(",");
        arr = new ArrayList<>(Arrays.asList(s));
        return arr;
    }

    public static String handlerNameSearch(String name) {
        if (name == null) {
            return "";
        }
        return name.replaceAll("\r", "").replaceAll("\n", "");
    }

    public static String pointToLocationString(Point point, GoogleMap googleMap) {
        LatLng latLng = Utils.fromScreenToLocation(googleMap, point);
        return String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
    }

    public static String locationString(LatLng latLng) {
        return String.valueOf(latLng.latitude) + "," + String.valueOf(latLng.longitude);
    }

    public static float getZoomByAddress(String address) {
        if (address.equalsIgnoreCase("street_number")) {
            return 17f;
        }
        if (address.equalsIgnoreCase("route")) {
            return 17f;
        }
        if (address.equalsIgnoreCase("locality")) {
            return 12f;
        }
        if (address.equalsIgnoreCase("administrative_area_level_1")) {
            return 8f;
        }
        if (address.equalsIgnoreCase("administrative_area_level_2")) {
            return 8f;
        }
        if (address.equalsIgnoreCase("country")) {
            return 5f;
        }

        return 15f;
    }

    public static LatLng getPositionFromLocation(String location) {
        return new LatLng(Double.parseDouble(location.substring(0, location.indexOf(","))),
                Double.parseDouble(location.substring(location.indexOf(",") + 1, location.length())));
    }

    public static int getSingleHour(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("hh", Locale.US);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getSingleMin(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("mm", Locale.US);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static String getSingleHalf(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("a", Locale.US);
        return simpleDateFormat.format(date);
    }

    public static int getSingleYear(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.US);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getSingleDay(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("dd", Locale.US);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getSingleMonth(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("MM", Locale.US);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getHourRoute(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("hh", Locale.US);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static int getMinuteRoute(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("mm", Locale.US);
        return Integer.parseInt(simpleDateFormat.format(date));
    }

    public static String getHalfRoute(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("a", Locale.US);
        return simpleDateFormat.format(date);
    }

    public static Date createDateFromString(String raw) {
        int year = Integer.parseInt(raw.substring(0, 4));
        int month = Integer.parseInt(raw.substring(5, 7));
        int day = Integer.parseInt(raw.substring(8, 10));
        int hour = Integer.parseInt(raw.substring(11, 13));
        int min = Integer.parseInt(raw.substring(14, 16));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Calendar createDateFromStringAst(String raw) {
        int year = Integer.parseInt(raw.substring(0, 4));
        int month = Integer.parseInt(raw.substring(5, 7));
        int day = Integer.parseInt(raw.substring(8, 10));
        int hour = Integer.parseInt(raw.substring(11, 13));
        int min = Integer.parseInt(raw.substring(14, 16));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    public static String getTimeAst(String raw) {

        Calendar calendar = createDateFromStringAst(raw);
        DateFormat simpleDateFormat = new SimpleDateFormat("h:mm:a", Locale.US);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Calendar createDateFromString1(String raw) {
        int year = Integer.parseInt(raw.substring(0, 4));
        int month = Integer.parseInt(raw.substring(5, 7));
        int day = Integer.parseInt(raw.substring(8, 10));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }


    public static String getTimeCaravan(String rawTime) {
        if (rawTime == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        return dateFormat.format(createDateFromString(rawTime));
    }

    public static String getDateShowing(String rawDate) {
        DateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd", Locale.US);
        return dateFormat.format(createDateFromString(rawDate));
    }

    public static String getTimeShowing(String firstTime, String lastTime) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        if (firstTime == null) {
            return "";
        }
        if (lastTime == null) {
            return dateFormat.format(createDateFromString(firstTime));
        }
        return dateFormat.format(createDateFromString(firstTime)) + " - " + dateFormat.format(createDateFromString(lastTime));
    }

    public static String getTimeStartCaravan(String firstTime) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        if (firstTime != null) {
            return dateFormat.format(createDateFromString(firstTime));
        }
        return "";
    }

    public static String getTimeEndCaravan(String lastTime) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        if (lastTime != null) {
            return dateFormat.format(createDateFromString(lastTime));
        }
        return "";
    }


    public static String getTimeRoute(int hour, int minute, String half, int year, int month, int day) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        if (half.equalsIgnoreCase("PM")) {
            calendar.add(Calendar.HOUR_OF_DAY, 12);
        }
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        return df.format(calendar.getTime());
    }

    public static Date getDateRoute(int hour, int minute, int duration, String half) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (half.equalsIgnoreCase("PM")) {
            calendar.add(Calendar.HOUR, 12);
            if (hour - 12 > 0) {
                calendar.add(Calendar.HOUR, hour - 12);
            }
        } else {
            calendar.add(Calendar.HOUR, hour);
        }
        //calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.MINUTE, duration);
        return calendar.getTime();
    }


    public static String createDestination(ArrayList<ConsumerListingSchedule> arrListingSchedule) throws Exception {
        int year = CurrentListingSchedule.getInstance().getYear();
        int month = CurrentListingSchedule.getInstance().getMonth();
        int day = CurrentListingSchedule.getInstance().getDay();
        ArrayList<CaravanJson> arrJson = CurrentCaravan.getCaravan().getData().getJson();
        Log.e("arrJson1111", arrJson.toString());
        for (int i = 0; i < arrListingSchedule.size(); i++) {
            Log.e("arrListingSchedule", arrListingSchedule.get(i).toString());
            for (int j = 0; j < arrJson.size(); j++) {
                if (arrJson.get(j).getListing().getId().equalsIgnoreCase(arrListingSchedule.get(i).getListing().getId())) {
                    arrJson.get(j).setFixedTimeFrom(getTimeRoute(arrListingSchedule.get(i).getStartHour(), arrListingSchedule.get(i).getStartMin(),
                            arrListingSchedule.get(i).getStartHaft(), year, month, day));
//                    arrJson.get(j).setTimeTo(getTimeRoute(arrListingSchedule.get(i).getEndHour(), arrListingSchedule.get(i).getEndMin(),
//                            arrListingSchedule.get(i).getEndHaft(), year, month, day));
                    arrJson.get(j).setDuration((arrListingSchedule.get(i).getDuration() * 60));
                    arrJson.get(j).setPosition(i);
                }
            }
        }
        Log.e("arrJson", arrJson.toString());
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < arrJson.size(); i++) {
            Log.e("jsonString1", arrJson.get(i).toString());
            CaravanJson caravanJson = arrJson.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("time_from", caravanJson.getTimeFrom() == null ? JSONObject.NULL : caravanJson.getTimeFrom());
            jsonObject.put("time_to", caravanJson.getTimeTo() == null ? JSONObject.NULL : caravanJson.getTimeTo());
            jsonObject.put("duration", caravanJson.getDuration());
            jsonObject.put("park", caravanJson.getPark() == null ? JSONObject.NULL : caravanJson.getPark());
            jsonObject.put("type", caravanJson.getType() == null ? JSONObject.NULL : caravanJson.getType());
            jsonObject.put("address", caravanJson.getAddress() == null ? JSONObject.NULL : caravanJson.getAddress());
            jsonObject.put("short_address", caravanJson.getShort_address() == null ? JSONObject.NULL : caravanJson.getShort_address());
            jsonObject.put("id", caravanJson.getId());
            jsonObject.put("sid", caravanJson.getSid());
            jsonObject.put("position", caravanJson.getPosition());
            jsonObject.put("lat", caravanJson.getLat() == null ? JSONObject.NULL : caravanJson.getLat());
            jsonObject.put("lng", caravanJson.getLng() == null ? JSONObject.NULL : caravanJson.getLng());
            jsonObject.put("delete", caravanJson.getDelete());
            jsonObject.put("manualAst", caravanJson.getManualAst());
            jsonObject.put("autoAst", caravanJson.getAutoAst());
            jsonObject.put("photo", caravanJson.getPhoto() == null ? JSONObject.NULL : caravanJson.getPhoto());
            jsonObject.put("free_time_before", caravanJson.getFreeTimeBefore());
            jsonObject.put("free_time_after", caravanJson.getFreeTimeAfter());
            jsonObject.put("fixed_time_from", caravanJson.getFixedTimeFrom() == null ? JSONObject.NULL : caravanJson.getFixedTimeFrom());
            jsonObject.put("alternate_time", caravanJson.getAlternateTime() == null ? JSONObject.NULL : caravanJson.getAlternateTime());
            jsonObject.put("lock", caravanJson.getLock());
            jsonObject.put("note", caravanJson.getNote() == null ? JSONObject.NULL : caravanJson.getNote());
            jsonObject.put("description", caravanJson.getDescription() == null ? JSONObject.NULL : caravanJson.getDescription());
            jsonObject.put("appointment", caravanJson.getAppointment() == null ? JSONObject.NULL : caravanJson.getAppointment());
            jsonObject.put("action", caravanJson.getAction() == null ? JSONObject.NULL : caravanJson.getAction());
            jsonObject.put("tpl", caravanJson.getTpl() == null ? JSONObject.NULL : caravanJson.getTpl());
            jsonObject.put("copy", caravanJson.getCopy() == null ? JSONObject.NULL : caravanJson.getCopy());
            jsonObject.put("rsd_realtor_list", caravanJson.getRsdRealtorList() == null ? JSONObject.NULL : caravanJson.getRsdRealtorList());
            JSONObject drive = new JSONObject();
            drive.put("time_from", caravanJson.getDrive().getTimeFrom() == null ? JSONObject.NULL : caravanJson.getDrive().getTimeFrom());
            drive.put("time_to", caravanJson.getDrive().getTimeTo() == null ? JSONObject.NULL : caravanJson.getDrive().getTimeTo());
            drive.put("duration", caravanJson.getDrive().getDuration());
            jsonObject.put("drive", drive);
            JSONObject listing = new JSONObject();
            listing.put("id", caravanJson.getListing().getId());
            listing.put("lkey", caravanJson.getListing().getLkey());
            listing.put("status", caravanJson.getListing().getStatus());
            listing.put("listingType", caravanJson.getListing().getListingType());
            listing.put("saleType", caravanJson.getListing().getSaleType());
            listing.put("propertyType", caravanJson.getListing().getPropertyType());
            listing.put("bedrooms", caravanJson.getListing().getBedrooms());
            listing.put("bathrooms", caravanJson.getListing().getBathrooms());
            listing.put("url", caravanJson.getListing().getUrl());
            listing.put("directionUrl", caravanJson.getListing().getDirectionUrl());
            listing.put("description", caravanJson.getListing().getDescription());
            listing.put("yearBuilt", caravanJson.getListing().getYearBuilt());
            listing.put("pool", caravanJson.getListing().getPool());
            listing.put("garage", caravanJson.getListing().getGarage());
            listing.put("timezone", caravanJson.getListing().getTimeZone());
            listing.put("agent", caravanJson.getListing().getAgent());
            listing.put("agent_avatar", caravanJson.getListing().getAgentAvatar());
            JSONObject price = new JSONObject();
            price.put("value", caravanJson.getListing().getPrice().getValue());
            price.put("text", caravanJson.getListing().getPrice().getText());
            listing.put("price", price);
            JSONObject livingSquare = new JSONObject();
            livingSquare.put("value", caravanJson.getListing().getLivingSquare().getValue());
            livingSquare.put("text", caravanJson.getListing().getLivingSquare().getText());
            listing.put("livingSquare", livingSquare);
            JSONObject lotSize = new JSONObject();
            lotSize.put("value", caravanJson.getListing().getLotSize().getValue());
            lotSize.put("text", caravanJson.getListing().getLotSize().getText());
            listing.put("lotSize", lotSize);
            JSONObject address = new JSONObject();
            address.put("address1", caravanJson.getListing().getAddress().getAddress1());
            address.put("address2", caravanJson.getListing().getAddress().getAddress2());
            address.put("city", caravanJson.getListing().getAddress().getCity());
            address.put("state", caravanJson.getListing().getAddress().getState());
            address.put("zip", caravanJson.getListing().getAddress().getZip());
            address.put("country", caravanJson.getListing().getAddress().getCountry());
            address.put("lng", caravanJson.getListing().getAddress().getLng());
            address.put("lat", caravanJson.getListing().getAddress().getLat());
            JSONObject fullAddress = new JSONObject();
            fullAddress.put("one_line", caravanJson.getListing().getAddress().getFullAddress().getOneLine());
            ArrayList<String> twoLine = caravanJson.getListing().getAddress().getFullAddress().getTwoLine();
            JSONArray line = new JSONArray();
            for (int k = 0; k < twoLine.size(); k++) {
                line.put(twoLine.get(k));
            }
            fullAddress.put("two_line", line);
            address.put("fullAddress", fullAddress);
            listing.put("address", address);
            JSONObject listingImages = new JSONObject();
            if (caravanJson.getListing().getListingImages() != null) {
                listingImages.put("image", caravanJson.getListing().getListingImages().getImage() == null ? JSONObject.NULL : caravanJson.getListing().getListingImages().getImage());
                listingImages.put("image_thumb", caravanJson.getListing().getListingImages().getImageThumb() == null ? JSONObject.NULL : caravanJson.getListing().getListingImages().getImageThumb());
                listingImages.put("image_name", caravanJson.getListing().getListingImages().getImageName() == null ? JSONObject.NULL : caravanJson.getListing().getListingImages().getImageName());
                ArrayList<ImageListingDetail> imageListings = caravanJson.getListing().getListingImages().getArrImg();
                JSONArray arrImage = new JSONArray();
                for (int j = 0; j < imageListings.size(); j++) {
                    JSONObject image = new JSONObject();
                    image.put("image", imageListings.get(j).getImage() == null ? JSONObject.NULL : imageListings.get(j).getImage());
                    image.put("image_thumb", imageListings.get(j).getImageThumb() == null ? JSONObject.NULL : imageListings.get(j).getImageThumb());
                    image.put("image_name", imageListings.get(j).getImageName() == null ? JSONObject.NULL : imageListings.get(j).getImageName());
                    arrImage.put(image);
                }
                listingImages.put("images", arrImage);
                listing.put("listingImages", listingImages);
            } else {
                listing.put("listingImages", JSONObject.NULL);
            }
            jsonObject.put("listing", listing);
            Log.e("jsonObject", jsonObject.toString());
            jsonArray.put(jsonObject);
        }
        Log.e("jsonArray", jsonArray.toString());
        return jsonArray.toString();
    }

    public static boolean checkStatusCaravan(String timeCaravan) {
        if (timeCaravan == null) {
            return false;
        }
        Date dateCaravan = createDateFromString(timeCaravan);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, 0);
        Date currentDate = calendar.getTime();
        return dateCaravan.getTime() > currentDate.getTime();
    }


    public static String getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return df.format(calendar.getTime());
    }

    public static String getCurrentMonthMinusOneDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return df.format(calendar.getTime());
    }

    public static String getPreviousMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return df.format(calendar.getTime());
    }

    public static String getDayAst(String raw) {
        Calendar calendar = createDateFromStringAst(raw);
        DateFormat df = new SimpleDateFormat("MMMM d, yyyy", Locale.US);
        return df.format(calendar.getTime()).toUpperCase();
    }

    public static String getNextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, +1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return df.format(calendar.getTime());
    }

    public static Drawable getPlaceHolderListing(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.ic_placeholder_listing_consumer);
    }

    public static ImageLoader getImageLoader() {
        return HomeCaravanApplication.getInstance().getImageLoader();
    }

    public static String handlerTimeAst(String raw) {
        return raw.substring(0, raw.lastIndexOf(":")) + "\n" + raw.substring(raw.lastIndexOf(":") + 1);
    }

    public static void softContact(ArrayList<ContactData> contactDatas) {
        Collections.sort(contactDatas, new ComparatorContact());
    }

    public static class ComparatorContact implements Comparator<ContactData> {

        @Override
        public int compare(ContactData lhs, ContactData rhs) {
            return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
        }
    }

    public static String getDayShowingDashboard(String raw) {
        DateFormat df = new SimpleDateFormat("d", Locale.US);
        return df.format(createDateFromString(raw));
    }

    public static String getMonthShowingDashboard(String raw) {
        DateFormat df = new SimpleDateFormat("MMM", Locale.US);
        return df.format(createDateFromString(raw));
    }

    public static String getTimeShowingDashboard(String raw) {
        DateFormat df = new SimpleDateFormat("hh:mm a", Locale.US);
        return df.format(createDateFromString(raw));
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
