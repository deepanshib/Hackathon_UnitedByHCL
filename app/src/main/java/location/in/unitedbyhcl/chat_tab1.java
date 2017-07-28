package location.in.unitedbyhcl;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;




public class chat_tab1 extends Fragment implements LocationListener {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    //Boolean flagDisp=false;
    TextView t1,t2,t3;
    ImageView img;
    Button b2,b3,b4;
    private DatabaseReference mDatabase;
    private ChildEventListener mChildEventListener;
    static String question;
    static int i=1;

    View rootView;
    //location code

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat, geoadd,geodistance;
    String lat;
    String provider;
    Geocoder geocoder;
    List<Address> addresses;
    Double latitude, longitude,lat1 = -82.862751 ,lon1 = 135.0000;
    float distance = 0;
    protected boolean gps_enabled, network_enabled;


    public chat_tab1() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSharedData();


        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.chat_tab1, container, false);
        t1=(TextView)rootView.findViewById(R.id.textView5);
        t2=(TextView)rootView.findViewById(R.id.ques2);
        t3=(TextView)rootView.findViewById(R.id.ans2);
        b2=(Button)rootView.findViewById(R.id.button2);
        b3=(Button)rootView.findViewById(R.id.button3);
        b4=(Button)rootView.findViewById(R.id.button4);
        img=rootView.findViewById(R.id.smiley);

        geocoder = new Geocoder(getContext(), Locale.getDefault());

        //location</code>
        txtLat = (TextView) rootView.findViewById(R.id.latlong);
        geoadd = (TextView) rootView.findViewById(R.id.address);
        geodistance = (TextView) rootView.findViewById(R.id.distance) ;





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getActivity())) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            // initializeView();
//            rootView.findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    getActivity().startService(new Intent(getActivity(),FloatingViewService.class));
//                  //  getActivity().finish();
//                }
//            });
            //if(flagDisp)
            //{
                getActivity().startService(new Intent(getActivity(),FloatingViewService.class));
            //}
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("questions");

        fetch_q();
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                t2.setVisibility(View.VISIBLE);

                t2.setText(question);
                t3.setVisibility(View.VISIBLE);

                t3.setText("Yes");
                i++;
                if(i==4){
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    img.setVisibility(View.VISIBLE);
                }
                if(i==5)
                {  i=1;}
                fetch_q();

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t2.setText(question);
                t3.setText("No");
                i++;
                if(i==4){
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    img.setVisibility(View.VISIBLE);

                }
                if (i == 5) {
                    i = 1;
                }
                fetch_q();

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                t2.setVisibility(View.VISIBLE);
                t3.setVisibility(View.VISIBLE);
                t2.setText(question);
                t3.setText("Dont Know");
                i++;
                if(i==4){
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    t2.setVisibility(View.INVISIBLE);
                    t3.setVisibility(View.INVISIBLE);
                    img.setVisibility(View.VISIBLE);

                }
                if(i==5)
                {  i=1;}
                fetch_q();

            }
        });
        return rootView;
    }
public void fetch_q() {
    String k = Integer.toString(i);
    mDatabase.child("mainapp").child(k).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            question = (String) dataSnapshot.getValue();
            t1.setText(question);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    });
}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                //initializeView();

                  getActivity().startService(new Intent(getContext(),FloatingViewService.class));
                  getActivity().finish();

            } else { //Permission is not available
                Toast.makeText(getActivity(),
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                getActivity().finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //location methods implementation
    @Override
    public void onLocationChanged(Location location) {


        txtLat = (TextView) rootView.findViewById(R.id.latlong);
        geoadd = (TextView) rootView.findViewById(R.id.address);
        geodistance = (TextView) rootView.findViewById(R.id.distance);


        latitude = location.getLatitude();
        longitude = location.getLongitude();
        // mDatabase.child("users").child("usercurrentlocation").setValue(latitude);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());


        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            int i = addresses.get(0).getMaxAddressLineIndex();
            String full_add[] = new String[i];
            for (int j = 0; j < i; j++) {
                full_add[j] = addresses.get(0).getAddressLine(j);
            }
            geoadd.setText(Arrays.toString(full_add).replaceAll("\\[|\\]", ""));
            aboveTenkms();
            String str2 = Float.toString(distance);
            geodistance.setText("Distance :"+str2);



        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");
    }

    //code to send latitude and longitude to firebase only if distance between current latitude and longitde
    //and the old one fetched from database is more than 10kms....Loc1 to be fetched from db
    //code to convert latitude and longitude into the distance..........
    //begins here.


    public void aboveTenkms() {
        distance = distFrom(lat1,lon1,latitude,longitude);

        if (distance >= 5000.00) {
            //flagDisp=true;
            //push loc2 to firebase and update loc1 there
            mDatabase.child("users").child("usercurrentlocation").child("latitude").setValue(latitude);
            mDatabase.child("users").child("usercurrentlocation").child("longitude").setValue(longitude);
              //lat1 = latitude;
            //lon1 = longitude;
            // The above two statements should b comment out for not setting your current location as present location else chathead servive will be disabled.
        }


    }

    public float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }


}
