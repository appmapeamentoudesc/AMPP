package br.exemploopenstreetmap;


import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import br.model.Localizacao;

public class MainActivity extends Activity implements LocationListener {
    private MapView osm;
    private MapController mc;
    private LocationManager locationManager;
    private List<Localizacao> locs = new ArrayList<Localizacao>();
    private double latAtual;
    private double lonAtual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        osm = (MapView) findViewById(R.id.mapView);
        osm.setTileSource(TileSourceFactory.MAPNIK);
        osm.setBuiltInZoomControls(true);
        osm.setMultiTouchControls(true);


        mc = (MapController) osm.getController();
        mc.setZoom(15);

        listaLoc();
        gerarListaNoMapa();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        } catch (Exception e){
            System.out.println(e);

        }
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
        marker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));


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
