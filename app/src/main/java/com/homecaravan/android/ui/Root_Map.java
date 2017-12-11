package com.homecaravan.android.ui;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.homecaravan.android.R;

import java.util.ArrayList;

/**
 * Created by RAINY on 5/8/2016.
 */
public class Root_Map extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Polygon polyline;
    Double latitude, longitude;
    FrameLayout fram_map;
    Button btn_draw_State;
    Boolean Is_MAP_Moveable = false;
    Projection projection;
    PolygonOptions rectOptions;
    ArrayList<LatLng> val = new ArrayList<>();
    LatLng begin;
    ArrayList<LatLng> arrLatlng = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_map);
        fram_map = (FrameLayout) findViewById(R.id.fram_map);
        btn_draw_State = (Button) findViewById(R.id.btn_draw_State);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        arrLatlng.add(new LatLng(33.6860538, -118.0381185));
        arrLatlng.add(new LatLng(16.454927, 107.582544));
        arrLatlng.add(new LatLng(40.71323, -74.0133669));
        arrLatlng.add(new LatLng(33.8153022, -117.9261865));
        arrLatlng.add(new LatLng(37.8199286, -122.4782551));
        arrLatlng.add(new LatLng(36.1173213, -115.1737189));

        btn_draw_State.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Is_MAP_Moveable != true) {
                    Is_MAP_Moveable = true;
                    fram_map.setVisibility(View.VISIBLE);
                } else {
                    Is_MAP_Moveable = false;
                    fram_map.setVisibility(View.GONE);
                }
            }

        });

        fram_map.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                int x_co = Math.round(x);
                int y_co = Math.round(y);
                projection = mMap.getProjection();
                Point x_y_points = new Point(x_co, y_co);
                LatLng latLng = mMap.getProjection().fromScreenLocation(x_y_points);
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                int eventaction = event.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN:
                        mMap.clear();
                        val.clear();
                        val.add(new LatLng(latitude, longitude));
                        begin = new LatLng(latitude, longitude);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        val.add(new LatLng(latitude, longitude));
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.add(begin, new LatLng(latitude, longitude));
                        polylineOptions.width(2);
                        mMap.addPolyline(polylineOptions);
                        begin = new LatLng(latitude, longitude);
                        break;
                    case MotionEvent.ACTION_UP:
                        Draw_Map();
                        break;
                    default:
                }

                if (Is_MAP_Moveable == true) {
                    return true;

                } else {
                    return false;
                }
            }
        });

    }

    public void Draw_Map() {
        rectOptions = new PolygonOptions();
        rectOptions.addAll(val);
        rectOptions.strokeWidth(1);
        rectOptions.fillColor(Color.TRANSPARENT);
        polyline = mMap.addPolygon(rectOptions);
        for (int i = 0; i < arrLatlng.size(); i++) {
            if (PolyUtil.containsLocation(arrLatlng.get(i),val,true)) {
                mMap.addMarker(new MarkerOptions().position(arrLatlng.get(i)));
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

}