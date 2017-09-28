package controller;


import android.app.Activity;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import cidademais.udesc.br.model.Localizacao;

import cidademais.udesc.br.cidade.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalizacaoFragment extends Fragment implements LocationListener{
    private MapView osm;
    private MapController mc;
    private LocationManager locationManager;
    private List<Localizacao> locs = new ArrayList<Localizacao>();
    private double latAtual;
    private double lonAtual;

    public LocalizacaoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localizacao, container, false);
        osm = (MapView) view.findViewById(R.id.mapView);
        osm.setTileSource(TileSourceFactory.MAPNIK);
        osm.setBuiltInZoomControls(true);
        osm.setMultiTouchControls(true);


        mc = (MapController) osm.getController();
        mc.setZoom(15);

        listaLoc();
        gerarListaNoMapa();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

            } else {
                // permission has been granted, continue as usual
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }

        }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Erro ao Abrir, sem internet", Toast.LENGTH_LONG).show();
        }

            // Inflate the layout for this fragment
            return (view);
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
        marker.setIcon(getResources().getDrawable(R.drawable.ic_location_on_black_24dp));


        osm.getOverlays().add(marker);
        osm.invalidate();

        marker.setTitle("Desabamento");
        marker.setSnippet("Snippet Marker");
        marker.setSubDescription("Subdescription");
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker m, MapView mapView) {
                m.showInfoWindow();
                return true;
            }
        });
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
