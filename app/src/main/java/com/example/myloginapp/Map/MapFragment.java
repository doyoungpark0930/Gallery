package com.example.myloginapp.Map;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import androidx.annotation.RequiresApi;
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
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

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

        Log.i(TAG, "location: " + location);
        if(location != null){
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            currentMarker = this.googleMap.addMarker(markerOptions);
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            Log.i(TAG, "current location: " + currentLocation);

            return;
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        currentMarker = this.googleMap.addMarker(markerOptions);
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15));
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // 런타임 퍼미션 요청 대회상잔나 GPS 활성 요청 대화상자 보이기 전에 지도의 초기위치를 서울로 이동
        setCurrentLoation(null, "위치정보 가져올 수 없음", "위치 퍼미션과 GPS 활성 여부 확인");

        // 나침반이 나타나도록 설정
        googleMap.getUiSettings().setCompassEnabled(true);

        // 매끄럽게 이동함
      //  googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

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


        // 전라남도 polygon
        Polygon polygon1 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(35.56589026638052, 126.70891959973633),
                        new LatLng(35.42778593108981, 126.94057957747616),
                        new LatLng(35.39504787712914, 127.32947549794187),
                        new LatLng(35.46157432182293, 127.87122118357604),
                        new LatLng(35.17356552229895, 128.10418684917926),
                        new LatLng(35.46157432182293, 127.87122118357604),
                        new LatLng(34.87730236422249, 128.20100760380865),
                        new LatLng(34.28303551106315, 126.78014133250936),
                        new LatLng(34.30648520382429, 125.95691138480376),
                        new LatLng(35.32481453797089, 126.40571784671474))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));

        // 경상남도 polygon
        Polygon polygon2 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(35.64912105954252, 129.4497208003335),
                        new LatLng(35.6511871814545, 129.2554886038317),
                        new LatLng(35.72221888841761, 129.15565609726568),
                        new LatLng(35.647908824466086, 129.05191557580156),
                        new LatLng(35.56949292006062, 128.75909270457618),
                        new LatLng(35.578728039072494, 128.5926821010706),
                        new LatLng(35.67651251414598, 128.52393716985875),
                        new LatLng(35.61152001901417, 128.36449225709595),
                        new LatLng(35.6597610498084, 128.16459819436028),
                        new LatLng(35.79650152984172, 128.0986262953342),
                        new LatLng(35.91366407161159, 127.8790315059559),
                        new LatLng(35.75430914973239, 127.64924764620903),
                        new LatLng(35.542285820931696, 127.60236630660141),
                        new LatLng(35.447238415123714, 127.68624751408747),
                        new LatLng(35.459879829579414, 127.87248075538008),
                        new LatLng(35.176457093596575, 128.1083094882337),
                        new LatLng(34.87557685915901, 128.2050510494823),
                        new LatLng(34.798905471965035, 128.2570819627925),
                        new LatLng(34.70475603176031, 128.6104933224245),
                        new LatLng(34.88496534102118, 128.7596004357234),
                        new LatLng(35.03834652544177, 128.68153307400868),
                        new LatLng(34.90745458226964, 128.45479612658994),
                        new LatLng(35.05956745448312, 128.648296950287),
                        new LatLng(35.04076675592524, 129.11638356141745),
                        new LatLng(35.33834071603831, 129.3662266573699))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));

        // 경상북도 polygon
        Polygon polygon3 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(35.65042838499153, 129.4474645944842),
                        new LatLng(35.72911447882782, 129.49088839666575),
                        new LatLng(35.840407320660326, 129.518443367538),
                        new LatLng(36.47156826675103, 129.44591489773924),
                        new LatLng(36.64939229975995, 129.43435690659413),
                        new LatLng(36.76604794416803, 129.48103909978988),
                        new LatLng(36.863618087096285, 129.44687986969112),
                        new LatLng(37.06453929374731, 129.4331779422518),
                        new LatLng(37.14884955941045, 129.36961587392307),
                        new LatLng(37.0411637212252, 129.2137850689947),
                        new LatLng(37.061386525743856, 128.84025308755884),
                        new LatLng(37.0310447865901, 128.71006386487437),
                        new LatLng(36.93084439051541, 128.43918534010004),
                        new LatLng(36.83227459485072, 128.17334015548803),
                        new LatLng(36.69135758551455, 127.93631849621633),
                        new LatLng(36.3642869319531, 127.86963278139385),
                        new LatLng(36.29289514686242, 127.85245363666758),
                        new LatLng(36.26377576394953, 128.03445259862812),
                        new LatLng(36.0364413777282, 127.90365039716951),
                        new LatLng(35.91891718931861, 127.88529397990129),
                        new LatLng(35.80065466413276, 128.09779848054532),
                        new LatLng(35.66274756735775, 128.16517164822497),
                        new LatLng(35.61565878161284, 128.36509231712196),
                        new LatLng(35.67832585722772, 128.52591557237403),
                        new LatLng(35.58403726892527, 128.59190469796542),
                        new LatLng(35.57247608872348, 128.75968737740237),
                        new LatLng(35.652067120467606, 129.05113873594797),
                        new LatLng(35.72527545434019, 129.15202560285155),
                        new LatLng(35.65419366361914, 129.25468995668683))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));

        // 전라북도 polygon
        Polygon polygon4 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(35.99915771196859, 126.72565776663431), //이쯤부터 서천군(시계방향)
                        new LatLng(36.05938403714671, 126.86751918930501),
                        new LatLng(36.13554693391416, 126.89583282009762),
                        new LatLng(36.13789868856049, 127.01244355187744),
                        new LatLng(36.06630929481275, 127.12894034716084),
                        new LatLng(36.10761277520423, 127.26547867139566),
                        new LatLng(36.12124414841157, 127.34798973839415),
                        new LatLng(36.0057020239491, 127.4071100817314),
                        new LatLng(35.980139710339074, 127.45807253733675),
                        new LatLng(35.99362675147636, 127.54332104967169),
                        new LatLng(36.03052663896697, 127.54641467406273),
                        new LatLng(36.01404753747989, 127.61445464034935),
                        new LatLng(36.070730339204836, 127.63922784880562), //경남전북경북경계
                        new LatLng(36.040560889633056, 127.67165171242955),
                        new LatLng(36.06116939003701, 127.6988202489132),
                        new LatLng(36.01115331061085, 127.76652910478397),
                        new LatLng(36.039414821248975, 127.85202111902308),
                        new LatLng(36.02191847509255, 127.87881238637057),
                        new LatLng(36.0364413777282, 127.90365039716951),
                        new LatLng(35.91891718931861, 127.88529397990129),
                        new LatLng(35.91366407161159, 127.8790315059559),
                        new LatLng(35.75430914973239, 127.64924764620903),
                        new LatLng(35.542285820931696, 127.60236630660141),
                        new LatLng(35.447238415123714, 127.68624751408747),
                        new LatLng(35.39504787712914, 127.32947549794187),
                        new LatLng(35.42778593108981, 126.94057957747616),
                        new LatLng(35.56589026638052, 126.70891959973633),
                        new LatLng(35.59636085057343, 126.65359055276922),
                        new LatLng(35.58886878567767, 126.49261685557896),
                        new LatLng(35.64181228904139, 126.46401721737577),
                        new LatLng(35.695116281368726, 126.52022873675863),
                        new LatLng(35.79642999852756, 126.47147665431923),
                        new LatLng(35.791525763518415, 126.40920946699957),
                        new LatLng(35.82356374540961, 126.35797937442284),
                        new LatLng(35.844504344211344, 126.39181474191234),
                        new LatLng(35.85172501841209, 126.45410627007301),
                        new LatLng(35.97884838224812, 126.50716339636958),
                        new LatLng(35.99771791508185, 126.62061293930239))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));

        // 강원도 polygon
        Polygon polygon5 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.06453929374731, 129.4331779422518),
                        new LatLng(37.14884955941045, 129.36961587392307),
                        new LatLng(37.0411637212252, 129.2137850689947),
                        new LatLng(37.061386525743856, 128.84025308755884),
                        new LatLng(37.09698683706489 , 128.52582559774714),
                        new LatLng(37.10973615671139 , 128.42816449654512),
                        new LatLng(37.15296086839615 , 128.38143123277237),
                        new LatLng(37.13326025569953 , 128.28744535420824),
                        new LatLng(37.16696284645567 , 128.26351866766177),
                        new LatLng(37.20886749657458 , 128.33342672739573),
                        new LatLng(37.24946208052302 , 128.20430380254734),
                        new LatLng(37.21641190384279 , 128.16484341865086),
                        new LatLng(37.231792638916716 , 128.12469489331102),
                        new LatLng(37.19110709417599 , 128.03327281561576),
                        new LatLng(37.257338754957125 , 127.97502078956161),
                        new LatLng(37.22668611244064 , 127.91405132832283),
                        new LatLng(37.15406765428889 , 127.90885374632515),
                        new LatLng(37.148014512803435 , 127.78775678485921),
                        new LatLng(37.20942536683524 , 127.74369451112725),
                        new LatLng(37.29701669152884 , 127.75321771542914),
                        new LatLng(37.36619148173275 , 127.75679856371386),
                        new LatLng(37.443160348578964 , 127.80241203513265),
                        new LatLng(37.498828024192655 , 127.75524020440142),
                        new LatLng(37.54889657776402 , 127.85424005809406),
                        new LatLng(37.63215345078289 , 127.63623093290678),
                        new LatLng(37.627977690448674 , 127.54919500308125),
                        new LatLng(37.72596560213946 , 127.55717735862483),
                        new LatLng(37.71984598522549 , 127.50777037379221),
                        new LatLng(37.76118878441886 , 127.54654693375086),
                        new LatLng(37.79186656762048 , 127.52061511925919),
                        new LatLng(37.81543830037229 , 127.53604413616043),
                        new LatLng(37.8267292564439 , 127.52558509899286),
                        new LatLng(37.84313751015611 , 127.53115482157185),
                        new LatLng(37.84622193858937 , 127.55008248438453),
                        new LatLng(37.857969538976306 , 127.56544204832556),
                        new LatLng(37.87403042051132 , 127.58265888645764),
                        new LatLng(37.873927863941 , 127.60302546803698),
                        new LatLng(37.911329621539984 , 127.61788612583794),
                        new LatLng(37.93527628954036 , 127.61444648134282),
                        new LatLng(37.948313057537746 , 127.6021766764847),
                        new LatLng(37.955525739663805 , 127.601143242034),
                        new LatLng(37.96675206804079 , 127.54551810738282),
                        new LatLng(37.96675206804079 , 127.54551810738282),
                        new LatLng(38.00167648275772 , 127.53666838049251),
                        new LatLng(38.01237623943274 , 127.4609537071205),
                        new LatLng(38.11396360497237 , 127.43240006754469),
                        new LatLng(38.09352194668387 , 127.33597458061347),
                        new LatLng(38.12827922720938 , 127.2733597766),
                        new LatLng(38.1824313089004 , 127.2925542449001),
                        new LatLng(38.13992130900193 , 127.22083977507495),
                        new LatLng(38.18495487867324 , 127.18591178459664),
                        new LatLng(38.242650729908654 , 127.15973993724508),
                        new LatLng(38.234633100805254 , 127.11293873705988),
                        new LatLng(38.27731476808848 , 127.09545067783631),
                        new LatLng(38.31417440273917 , 127.13647936609871),
                        new LatLng(38.30607094539859 , 127.16134235429091),
                        new LatLng(38.331307200100674 , 127.23898649992547),
                        new LatLng(38.30927519901786 , 127.29306308369654),
                        new LatLng(38.33094135529332 , 127.37805752281642),
                        new LatLng(38.30277504254387 , 127.50814914069466),
                        new LatLng(38.33224325863636 , 127.6152219689796),
                        new LatLng(38.34739131370097 , 127.78227217888947),
                        new LatLng(38.30212283675969 , 127.82568507266575),
                        new LatLng(38.32934590344749 , 127.88747569530445),
                        new LatLng(38.30613734077744 , 128.04523738045427),
                        new LatLng(38.34653979319166 , 128.16295174292864),
                        new LatLng(38.37255133093485 , 128.21316740019813),
                        new LatLng(38.476530896400014 , 128.301448385911),
                        new LatLng(38.5825872791051 , 128.3033604967938),
                        new LatLng(38.6119579495462 , 128.35679480881473),
                        new LatLng(38.55247282268112 , 128.41440818922445),
                        new LatLng(38.458323838333065 , 128.47562218146865),
                        new LatLng(38.43552601492917 , 128.45463500362553),
                        new LatLng(38.37128992069401 , 128.51925584660933),
                        new LatLng(38.2214827382914 , 128.59944571895048),
                        new LatLng(38.017776119225935 , 128.73492934563896),
                        new LatLng(37.90094353083103 , 128.83550073628493),
                        new LatLng(37.68272186189873 , 129.06224874071958),
                        new LatLng(37.38623565360066 , 129.25929938964754))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));

        // 경기도 polygon
        Polygon polygon6 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(38.27731476808848 , 127.09545067783631),
                        new LatLng(38.19807206692176, 126.97369733796948),
                        new LatLng(38.19807206692176, 126.97369733796948),
                        new LatLng(38.13435972387536, 126.9718952362475),
                        new LatLng(38.09278322140833, 126.87560660630179),
                        new LatLng(38.03509005444541, 126.84654396738715),
                        new LatLng(37.96325101802907, 126.67772912656314),
                        new LatLng(37.84797085198979, 126.69277468291685),
                        new LatLng(37.794835624252876, 126.66102280982385),
                        new LatLng(37.77612215545053, 126.57974553355996),
                        new LatLng(37.78971511915858, 126.51572913087628),
                        new LatLng(37.70669674079924, 126.51917291645225),
                        new LatLng(37.603032654690814, 126.55173056102876),
                        new LatLng(37.51669626607474, 126.59568329093568),
                        new LatLng(37.348278533347084, 126.58503120718139),
                        new LatLng(37.332338259741455, 126.6486687776696),
                        new LatLng(37.29333819130672, 126.72969027432138),
                        new LatLng(37.276962391211875, 126.64315327428547),
                        new LatLng(37.17778561535045, 126.64650303169181),
                        new LatLng(37.11095856091853, 126.66985552182419),
                        new LatLng(37.09724456967749, 126.71886972094828),
                        new LatLng(37.0350216790861, 126.74499531021469),
                        new LatLng(36.91527699550581, 126.87467225076442),
                        new LatLng(36.929183453572676, 126.98958405265988),
                        new LatLng(36.96373183830195, 127.11318981769719),
                        new LatLng(36.96367908646372, 127.1591842721539),
                        new LatLng(36.89423905956964, 127.28541007291611),
                        new LatLng(36.95625583921334, 127.38049547418669),
                        new LatLng(37.00676231941407, 127.4497773221625),
                        new LatLng(37.05232001070106, 127.5824325642608),
                        new LatLng(37.209428760382885, 127.74315381498967),
                        new LatLng(37.148014512803435 , 127.78775678485921),
                        new LatLng(37.20942536683524 , 127.74369451112725),
                        new LatLng(37.29701669152884 , 127.75321771542914),
                        new LatLng(37.36619148173275 , 127.75679856371386),
                        new LatLng(37.443160348578964 , 127.80241203513265),
                        new LatLng(37.498828024192655 , 127.75524020440142),
                        new LatLng(37.54889657776402 , 127.85424005809406),
                        new LatLng(37.63215345078289 , 127.63623093290678),
                        new LatLng(37.627977690448674 , 127.54919500308125),
                        new LatLng(37.72596560213946 , 127.55717735862483),
                        new LatLng(37.71984598522549 , 127.50777037379221),
                        new LatLng(37.76118878441886 , 127.54654693375086),
                        new LatLng(37.79186656762048 , 127.52061511925919),
                        new LatLng(37.81543830037229 , 127.53604413616043),
                        new LatLng(37.8267292564439 , 127.52558509899286),
                        new LatLng(37.84313751015611 , 127.53115482157185),
                        new LatLng(37.84622193858937 , 127.55008248438453),
                        new LatLng(37.857969538976306 , 127.56544204832556),
                        new LatLng(37.87403042051132 , 127.58265888645764),
                        new LatLng(37.873927863941 , 127.60302546803698),
                        new LatLng(37.911329621539984 , 127.61788612583794),
                        new LatLng(37.93527628954036 , 127.61444648134282),
                        new LatLng(37.948313057537746 , 127.6021766764847),
                        new LatLng(37.955525739663805 , 127.601143242034),
                        new LatLng(37.96675206804079 , 127.54551810738282),
                        new LatLng(37.96675206804079 , 127.54551810738282),
                        new LatLng(38.00167648275772 , 127.53666838049251),
                        new LatLng(38.01237623943274 , 127.4609537071205),
                        new LatLng(38.11396360497237 , 127.43240006754469),
                        new LatLng(38.09352194668387 , 127.33597458061347),
                        new LatLng(38.12827922720938 , 127.2733597766),
                        new LatLng(38.1824313089004 , 127.2925542449001),
                        new LatLng(38.13992130900193 , 127.22083977507495),
                        new LatLng(38.18495487867324 , 127.18591178459664),
                        new LatLng(38.242650729908654 , 127.15973993724508))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));

        // 충청북도 polygon
        Polygon polygon7 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(37.061386525743856, 128.84025308755884),
                        new LatLng(37.09698683706489 , 128.52582559774714),
                        new LatLng(37.10973615671139 , 128.42816449654512),
                        new LatLng(37.15296086839615 , 128.38143123277237),
                        new LatLng(37.13326025569953 , 128.28744535420824),
                        new LatLng(37.16696284645567 , 128.26351866766177),
                        new LatLng(37.20886749657458 , 128.33342672739573),
                        new LatLng(37.24946208052302 , 128.20430380254734),
                        new LatLng(37.21641190384279 , 128.16484341865086),
                        new LatLng(37.231792638916716 , 128.12469489331102),
                        new LatLng(37.19110709417599 , 128.03327281561576),
                        new LatLng(37.257338754957125 , 127.97502078956161),
                        new LatLng(37.22668611244064 , 127.91405132832283),
                        new LatLng(37.15406765428889 , 127.90885374632515),
                        new LatLng(37.148014512803435 , 127.78775678485921),
                        new LatLng(37.20942536683524 , 127.74369451112725),
                        new LatLng(36.89423905956964, 127.28541007291611),
                        new LatLng(36.819070180285856, 127.35829609116321),
                        new LatLng(36.755421394400884, 127.42250404000187),
                        new LatLng(36.72916713437407, 127.33638385446127),
                        new LatLng(36.692388636984234, 127.28609452540752), // <세종시
                        new LatLng(36.68080078043996, 127.3075322705229),
                        new LatLng(36.63934418096084, 127.28017315417064),
                        new LatLng(36.581590017640245, 127.31285914075741),
                        new LatLng(36.568702210121565, 127.38144571111897),
                        new LatLng(36.52592493609923, 127.41124836013933),
                        new LatLng(36.49565299365512, 127.40930246459698),
                        new LatLng(36.49194648917321, 127.39678310541638),
                        new LatLng(36.48067026553564, 127.40565277983177),
                        new LatLng(36.456746848929185, 127.40231535535656),
                        new LatLng(36.45489100490405, 127.43800262360791),//충남경계
                        new LatLng(36.44822821815222, 127.44617473219559),
                        new LatLng(36.45594460485094, 127.46442454485283),
                        new LatLng(36.476643094877275, 127.48061605993837),
                        new LatLng(36.45500440511741, 127.48333825804993),
                        new LatLng(36.455495496671766, 127.50404557828091),
                        new LatLng(36.43305384083969, 127.4917669483504),
                        new LatLng(36.41225310202745, 127.50091187837823),
                        new LatLng(36.421987473142764, 127.51738795415042),
                        new LatLng(36.4184065545417, 127.54448098286629),
                        new LatLng(36.40045219568207, 127.56076470069263),
                        new LatLng(36.34129793432031, 127.50455625616499),
                        new LatLng(36.2807703486885, 127.49704295066101), //충남 경계
                        new LatLng(36.238091379024524, 127.49677263605584),  //충북 전북 경계
                        new LatLng(36.24944119623287, 127.53956809386727),
                        new LatLng(36.22517926032347, 127.54794346936706),
                        new LatLng(36.23074343133485, 127.59069563018753),
                        new LatLng(36.17996219293476, 127.5960055973838),
                        new LatLng(36.131536890510056, 127.59137328145212),
                        new LatLng(36.070730339204836, 127.63922784880562),  //경남전북경북 경계
                        new LatLng(36.040560889633056, 127.67165171242955),
                        new LatLng(36.06116939003701, 127.6988202489132),
                        new LatLng(36.01115331061085, 127.76652910478397),
                        new LatLng(36.039414821248975, 127.85202111902308),
                        new LatLng(36.02191847509255, 127.87881238637057),
                        new LatLng(36.0364413777282, 127.90365039716951),
                        new LatLng(36.26377576394953, 128.03445259862812),
                        new LatLng(36.29289514686242, 127.85245363666758),
                        new LatLng(36.3642869319531, 127.86963278139385),
                        new LatLng(36.69135758551455, 127.93631849621633),
                        new LatLng(36.83227459485072, 128.17334015548803),
                        new LatLng(36.93084439051541, 128.43918534010004),
                        new LatLng(37.0310447865901, 128.71006386487437),
                        new LatLng(37.061386525743856, 128.84025308755884))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));

        // 충청남도 polygon
        Polygon polygon8 = googleMap.addPolygon(new PolygonOptions()
                .add(new LatLng(36.96373183830195, 127.11318981769719), //경기도 경계
                        new LatLng(36.929183453572676, 126.98958405265988),
                        new LatLng(36.91527699550581, 126.87467225076442),
                        new LatLng(37.0350216790861, 126.74499531021469),
                        new LatLng(36.9967438325505, 126.65884636700807),
                        new LatLng(37.063015830884574, 126.48584691413691),
                        new LatLng(36.99752359753608, 126.30798305192847),
                        new LatLng(36.92119955731265, 126.27419735947129),
                        new LatLng(36.8883102610467, 126.18261255482757),
                        new LatLng(36.693781479852746, 126.07869059093447),
                        new LatLng(36.64243779876058, 126.00487583070186),
                        new LatLng(36.63624789167251, 126.09655018279415),
                        new LatLng(36.63189011655454, 126.13094732785206),
                        new LatLng(36.57956098553646, 126.2373642386995),
                        new LatLng(36.38170935752049, 126.32774457265461),
                        new LatLng(36.34429879505961, 126.456391852846),
                        new LatLng(36.25931037344779, 126.54528801235234),
                        new LatLng(36.231526377333054, 126.51982054501119),
                        new LatLng(36.18312452360561, 126.53149950716869),
                        new LatLng(36.15527794955691, 126.49183750817217),
                        new LatLng(36.12994851470794, 126.50337652138211),
                        new LatLng(36.08654093633192, 126.62018659658558),
                        new LatLng(36.01512418026474, 126.65460719969474),
                        new LatLng(35.99915771196859, 126.72565776663431), //이쯤부터 서천군
                        new LatLng(36.05938403714671, 126.86751918930501),
                        new LatLng(36.13554693391416, 126.89583282009762),
                        new LatLng(36.13789868856049, 127.01244355187744),
                        new LatLng(36.06630929481275, 127.12894034716084),
                        new LatLng(36.10761277520423, 127.26547867139566),
                        new LatLng(36.12124414841157, 127.34798973839415),
                        new LatLng(36.0057020239491, 127.4071100817314),
                        new LatLng(35.980139710339074, 127.45807253733675),
                        new LatLng(35.99362675147636, 127.54332104967169),
                        new LatLng(36.03052663896697, 127.54641467406273),
                        new LatLng(36.01404753747989, 127.61445464034935),
                        new LatLng(36.070730339204836, 127.63922784880562), //경남전북경북경계
                        new LatLng(36.131536890510056, 127.59137328145212),
                        new LatLng(36.17996219293476, 127.5960055973838),
                        new LatLng(36.23074343133485, 127.59069563018753),
                        new LatLng(36.22517926032347, 127.54794346936706),  //충북 전북 경계
                        new LatLng(36.24944119623287, 127.53956809386727),
                        new LatLng(36.238091379024524, 127.49677263605584),
                        new LatLng(36.2807703486885, 127.49704295066101),
                        new LatLng(36.34129793432031, 127.50455625616499),
                        new LatLng(36.40045219568207, 127.56076470069263),
                        new LatLng(36.4184065545417, 127.54448098286629),
                        new LatLng(36.421987473142764, 127.51738795415042),
                        new LatLng(36.41225310202745, 127.50091187837823),
                        new LatLng(36.43305384083969, 127.4917669483504),
                        new LatLng(36.455495496671766, 127.50404557828091),
                        new LatLng(36.45500440511741, 127.48333825804993),
                        new LatLng(36.476643094877275, 127.48061605993837),
                        new LatLng(36.45594460485094, 127.46442454485283),
                        new LatLng(36.44822821815222, 127.44617473219559),
                        new LatLng(36.456746848929185, 127.40231535535656),
                        new LatLng(36.48067026553564, 127.40565277983177),
                        new LatLng(36.49194648917321, 127.39678310541638),
                        new LatLng(36.49565299365512, 127.40930246459698),
                        new LatLng(36.52592493609923, 127.41124836013933),
                        new LatLng(36.568702210121565, 127.38144571111897),
                        new LatLng(36.581590017640245, 127.31285914075741),
                        new LatLng(36.63934418096084, 127.28017315417064),
                        new LatLng(36.68080078043996, 127.3075322705229),
                        new LatLng(36.692388636984234, 127.28609452540752),
                        new LatLng(36.72916713437407, 127.33638385446127),
                        new LatLng(36.755421394400884, 127.42250404000187),
                        new LatLng(36.819070180285856, 127.35829609116321),
                        new LatLng(36.89423905956964, 127.28541007291611),
                        new LatLng(36.96367908646372, 127.1591842721539),
                        new LatLng(36.96373183830195, 127.11318981769719),
                        new LatLng(36.929183453572676, 126.98958405265988),
                        new LatLng(36.91527699550581, 126.87467225076442))
                .strokeColor(Color.argb((float)0.8, (float)0.0, (float)0.3, (float)0.5))
                .fillColor(Color.argb((float)0.7, (float)1.0, (float)1.0, (float)1.0)));
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
                    setCurrentLoation(location, LikelyPlaceNames[0], LikelyAddresses[0]);
                }
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
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            }
        }
        else{
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            this.googleMap.getUiSettings().setCompassEnabled(true);
            //this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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