package com.example.wagner.cidade;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

import android.location.LocationListener;

import java.util.ArrayList;
import java.util.List;

import model.Localizacao;

public class LocalizacaoActivity extends AppCompatActivity implements LocationListener {

    private MapView osm;
    private MapController mc;
    private LocationManager locationManager;
    private List<Localizacao> locs = new ArrayList<Localizacao>();
    private double latAtual;
    private double lonAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        osm = (MapView) findViewById(R.id.mapView);
        osm.setTileSource(TileSourceFactory.MAPNIK);
        osm.setBuiltInZoomControls(true);
        osm.setMultiTouchControls(true);


        mc = (MapController) osm.getController();
        mc.setZoom(15);

        listaLoc();
        gerarListaNoMapa();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // if (ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_DENIED ) {
        //      try{


        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
         //      } catch (Exception e){
         //         System.out.println("Erro ao abrir localização: " + e);
        //   }


       }



    public void gerarListaNoMapa(){
        osm.getOverlays().clear();

        for (int i =0; i < locs.size(); i++) {
            GeoPoint center = new GeoPoint(locs.get(i).getLat(),locs.get(i).getLon());
            mc.animateTo(center);
            addMarker(center);

        }


    }
    public void listaLoc(){
        //-27.05790,-49.51872 || -27.05674,-49.51749 || -27.0567,-49.5210 || -27.05786,-49.53009 || -27.05591,-49.53529
        Localizacao loc1 = new Localizacao(-27.05790,-49.51872);
        Localizacao loc2 = new Localizacao(-27.05674,-49.51749);
        Localizacao loc3 = new Localizacao(-27.0567,-49.5210);
        Localizacao loc4 = new Localizacao(-27.05786,-49.53009);
        Localizacao loc5 = new Localizacao(-27.05591,-49.53529);
        //adicionar localizações no array
        locs.add(loc1);
        locs.add(loc2);
        locs.add(loc3);
        locs.add(loc4);
        locs.add(loc5);
        //locs.add(loc6);


    }

    public void addMarker(GeoPoint center){
        Marker marker = new Marker(osm);
        marker.setPosition(center);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marker.setIcon(getResources().getDrawable(R.drawable.ic_menu_send));


        osm.getOverlays().add(marker);
        osm.invalidate();

    }

    @Override
    public void onLocationChanged(Location location) {
        latAtual = location.getLatitude();
        lonAtual = location.getLongitude();
    }

    public void addLoc(View v){
        locs.add(new Localizacao(latAtual, lonAtual));
        gerarListaNoMapa();
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(this);
        }
    }

}
