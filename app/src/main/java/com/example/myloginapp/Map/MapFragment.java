package com.example.myloginapp.Map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myloginapp.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
    private static final String TAG = "googlemap";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 15000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 15000;

    //구글맵참조변수
    private GoogleMap googleMap = null;
    private MapView mapView = null;
    private GoogleApiClient googleApiClient = null;
    private Marker currentMarker = null;

    private final static int MAXENTRIES = 5;
    private String[] LikelyPlaceNames = null;
    private String[] LikelyAddresses = null;
    private String[] LikelyAttributions = null;
    private LatLng[] LikelyLatLngs = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MapFragment() {
        // Required empty public constructor
    }

    public void setCurrentLoation(Location location, String markerTitle, String markerSnippet){
        if(currentMarker != null)
            currentMarker.remove();

        if(location != null){
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            currentMarker = this.googleMap.addMarker(markerOptions);
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        currentMarker = this.googleMap.addMarker(markerOptions);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener(){
            @Override
            public void onPlaceSelected(Place place){
                Location location = new Location("");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);

                setCurrentLoation(location, place.getName().toString(), place.getAddress().toString());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        return layout;
    }

    @Override
    public void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
        mapView.onStart();

        if(googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();

        if(googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    public void onPause(){
        super.onPause();
        mapView.onPause();

        if(googleApiClient != null && googleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();

        if(googleApiClient != null){
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);

            if(googleApiClient.isConnected()){
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                googleApiClient.disconnect();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // 액티비티가 처음 생성될 때 실행되는 함수
        MapsInitializer.initialize(getActivity().getApplicationContext());

        if(mapView != null){
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // 런타임 퍼미션 요청 대회상잔나 GPS 활성 요청 대화상자 보이기 전에 지도의 초기위치를 서울로 이동
        setCurrentLoation(null, "위치정보 가져올 수 없음", "위치 퍼미션과 GPS 활성 여부 확인");

        // 나침반이 나타나도록 설정
        googleMap.getUiSettings().setCompassEnabled(true);

        // 매끄럽게 이동함
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(7));

        // API 23 이상이면 런타임 퍼미션 처리 필요
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // 사용 권한 체크
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

            if(hasFineLocationPermission == PackageManager.PERMISSION_DENIED){
                // 사용권한이 없을 경우 권한 재요청
                ActivityCompat.requestPermissions(getActivity(), new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
            else{
                // 사용권한이 있는 경우
                if(googleApiClient == null){
                    buildGoogleApiClient();
                }

                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    googleMap.setMyLocationEnabled(true);
                }
            }
        }
        else{
            if(googleApiClient == null){
                buildGoogleApiClient();
            }
            googleMap.setMyLocationEnabled(true);
        }
    }

    private void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();

        googleApiClient.connect();
    }

    public boolean checkLocationServicesStatus(){
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.e(TAG, "onLocationChanged call..");
        searchCurrentPlaces();
    }

    private void searchCurrentPlaces(){
        @SuppressWarnings("MissingPermission")
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer placeLikelihoods) {
                int i = 0;
                LikelyPlaceNames = new String[MAXENTRIES];
                LikelyAddresses = new String[MAXENTRIES];
                LikelyAttributions = new String[MAXENTRIES];
                LikelyLatLngs = new LatLng[MAXENTRIES];

                for(PlaceLikelihood placeLikelihood: placeLikelihoods){
                    LikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                    LikelyAddresses[i] = (String) placeLikelihood.getPlace().getAddress();
                    LikelyAttributions[i] = (String) placeLikelihood.getPlace().getAttributions();
                    LikelyLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                    i++;
                    if(i > MAXENTRIES - 1){
                        break;
                    }
                }

                placeLikelihoods.release();

                Location location = new Location("");
                if(LikelyLatLngs[0] != null) {
                    location.setLatitude(LikelyLatLngs[0].latitude);
                    location.setLongitude(LikelyLatLngs[0].longitude);
                }

//                setCurrentLoation(location, LikelyPlaceNames[0], LikelyAddresses[0]);
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(!checkLocationServicesStatus()){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("위치 서비스 비활성화");
            builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하십시오.");
            builder.setCancelable(true);
            builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.create().show();
        }

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_MS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
            }
        }
        else{
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
            this.googleMap.getUiSettings().setCompassEnabled(true);
            this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        if(cause == CAUSE_NETWORK_LOST)
            Log.e(TAG, "onConnectionSuspended(): Google Play services" + "connection lost. Cause: network lost.");
        else if(cause == CAUSE_SERVICE_DISCONNECTED)
            Log.e(TAG, "onConnectionSuspended(): Google Play services" + "connection lost. Cause: service disconnected.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Location location = new Location("");
        location.setLatitude(DEFAULT_LOCATION.latitude);
        location.setLongitude(DEFAULT_LOCATION.longitude);
        setCurrentLoation(location, "위치정보 가져올 수 없음", "위치 퍼미션과 GPS 활성 여부 확인");
    }
}
