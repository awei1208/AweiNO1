package com.addtw.aweino1;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by awei on 2016/2/11.
 */
public class GoogleMapPage extends FragmentActivity implements OnMapReadyCallback   {


    private GoogleMap map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlemap);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    @Override
    public void onMapReady(final GoogleMap map) {
        this.map = map;

        //Marker1
        MarkerOptions markerOpt1 = new MarkerOptions();
        markerOpt1.position(new LatLng(25.033611, 121.565000));
        markerOpt1.title("台北101");
        markerOpt1.snippet("於1999年動工，2004年12月31日完工啟用，樓高509.2公尺。");
        markerOpt1.draggable(false);
        markerOpt1.visible(true);
        markerOpt1.anchor(0.5f, 0.5f);//設為圖片中心

        //Marker2
        MarkerOptions markerOpt2 = new MarkerOptions();
        markerOpt2.position(new LatLng(25.047924, 121.517081));
        markerOpt2.title("台北火車站");


        ArrayList<MarkerOptions> markerArray = new ArrayList<MarkerOptions>();
       markerArray.add(markerOpt1);
        markerArray.add(markerOpt2);





        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.mapinfowindow, null);
                LatLng latLng = marker.getPosition();
                String title = marker.getTitle();
                TextView tvTitle = (TextView) v.findViewById(R.id.mapTitle);
                TextView tvLat = (TextView) v.findViewById(R.id.tvLat);
                TextView tvLng = (TextView) v.findViewById(R.id.tvLng);
                tvLat.setText(String.valueOf(latLng.latitude));
                tvLng.setText(String.valueOf(latLng.longitude));
                tvTitle.setText(title);
                return v;
            }
        });

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                Marker marker = map.addMarker(markerOptions);
                marker.showInfoWindow();
            }
        });



        for (int i =0 ; i<markerArray.size();i++){
            map.addMarker(new MarkerOptions().position(markerArray.get(i).getPosition())
                            .title(markerArray.get(i).getTitle())
                            .snippet(markerArray.get(i).getSnippet())
            );
        }



            }

}
