package id.cleva.mistexample.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.mist.android.AppMode;
import com.mist.android.MSTCentralManagerIndoorOnlyListener;
import com.mist.android.MSTCentralManagerStatusCode;
import com.mist.android.MSTMap;
import com.mist.android.MSTPoint;
import com.mist.android.MSTVirtualBeacon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import id.cleva.mistexample.MyApp;
import id.cleva.mistexample.R;
import id.cleva.mistexample.databinding.MapFragmentBinding;
import id.cleva.mistexample.utils.MistManager;
import id.cleva.mistexample.utils.Utils;

/**
 * Created by anubhava on 26/03/18.
 */

public class MapNotifFragment extends Fragment implements MSTCentralManagerIndoorOnlyListener {

    public static final String TAG = MapFragment.class.getSimpleName();
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private static final String SDK_TOKEN = "sdkToken";
    private MyApp mainApplication;
    private String sdkToken;
    private String floorPlanImageUrl = "";
    private MSTPoint mstPoint = null;
    private boolean addedMap = false;
    private double scaleXFactor;
    private double scaleYFactor;
    private boolean scaleFactorCalled;
    private float floorImageLeftMargin;
    private float floorImageTopMargin;
    public MSTMap currentMap;
    public HashMap<String, MSTVirtualBeacon> mstVirtualBeaconMap = new HashMap<>();

    public enum AlertType {
        bluetooth,
        network,
        location
    }

    MapFragmentBinding binding;

//    @BindView(R.id.floorplan_bluedot)
//    FrameLayout floorplanBluedotView;
//    @BindView(R.id.floorplan_image)
//    ImageView floorPlanImage;
//    @BindView(R.id.progress_bar)
//    ProgressBar progressBar;
//    @BindView(R.id.txt_error)
//    TextView txtError;

//    @BindColor(R.color.black)
    int blackColor;
//    @BindColor(R.color.zone_color)
    int zoneColor;
//    @BindColor(R.color.vb_color)
    int vbColor;

    public static MapFragment newInstance(String sdkToken) {
        Bundle bundle = new Bundle();
        bundle.putString(SDK_TOKEN, sdkToken);
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.map_fragment, container, false);
//        unbinder = ButterKnife.bind(this, view);

        binding = DataBindingUtil.inflate(
                inflater, R.layout.map_fragment, container, false);
        View v = binding.getRoot();
        binding.setFragment(this);
        initView();
        return v;
    }

    void initView() {
        blackColor = R.color.black;
        zoneColor = R.color.zone_color;
        blackColor = R.color.vb_color;
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null)
            mainApplication = (MyApp) getActivity().getApplication();
        if (getArguments() != null)
            sdkToken = getArguments().getString(SDK_TOKEN);
    }

    @Override
    public void onStart() {
        super.onStart();
        initMISTSDK();
    }

    private void initMISTSDK() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity() != null &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            showLocationPermissionDialog();
        } else {
            startMistSdk();
        }
    }

    //permission dialogs
    private void showLocationPermissionDialog() {
        if (getActivity() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect beacons in the background.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_FINE_LOCATION);
                }
            });
            builder.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (getActivity() != null) {
            switch (requestCode) {
                case PERMISSION_REQUEST_FINE_LOCATION:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "fine location permission granted !!");
                        startMistSdk();
                    } else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Functionality limited");
                        builder.setMessage("Since location access has not been granted, " +
                                "this app will not be able to discover beacons when in the background.");
                        builder.setPositiveButton(android.R.string.ok, null);
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                            }
                        });
                        builder.show();
                    }
            }
        }
    }

    /**
     * This method checks for the availability for Internet , Location and Bluetooth and show dialog if anything is not enabled else start the Mist SDK
     */
    @SuppressLint("MissingPermission")
    private void startMistSdk() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled() && getActivity() != null &&
                Utils.isNetworkAvailable(getActivity()) && Utils.isLocationServiceEnabled(getActivity())) {
            runMISTSDK();
        } else {
            if (getActivity() != null && !Utils.isNetworkAvailable(getActivity())) {
                showSettingsAlert(AlertType.network);
            }
            if (getActivity() != null && !Utils.isLocationServiceEnabled(getActivity())) {
                showSettingsAlert(AlertType.location);
            }
            if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
                showSettingsAlert(AlertType.bluetooth);
            }
        }
    }

    //initializing the Mist sdk with sdkToken
    private void runMISTSDK() {
        MistManager mistManager = MistManager.newInstance(mainApplication);
        mistManager.init(sdkToken, this, AppMode.FOREGROUND);
    }

    /**
     * This method show the alert as per AlertType
     *
     * @param alertType Type of Alert
     *                  bluetooth
     *                  network
     *                  location
     */
    private void showSettingsAlert(final AlertType alertType) {
        if (getActivity() != null) {
            final String sTitle, sButton;
            if (alertType == AlertType.bluetooth) {
                sTitle = "Bluetooth is disabled in your device. Would you like to enable it?";
                sButton = "Goto Settings Page To Enable Bluetooth";
            } else if (alertType == AlertType.network) {
                sTitle = "Network Connection is disabled in your device. Would you like to enable it?";
                sButton = "Goto Settings Page To Enable Network Connection";
            } else {
                sTitle = "Location is disabled in your device. Would you like to enable it?";
                sButton = "Goto Settings Page To Enable Location";
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setMessage(sTitle)
                    .setCancelable(false)
                    .setPositiveButton(sButton,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intentOpenBluetoothSettings = new Intent();
                                    if (alertType == AlertType.bluetooth) {
                                        intentOpenBluetoothSettings.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
                                    } else if (alertType == AlertType.network) {
                                        intentOpenBluetoothSettings.setAction(Settings.ACTION_WIFI_SETTINGS);
                                    } else if (alertType == AlertType.location) {
                                        intentOpenBluetoothSettings.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    }

                                    startActivity(intentOpenBluetoothSettings);
                                }
                            });
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            final AlertDialog.Builder builder = new
                                    AlertDialog.Builder(getActivity());
                            builder.setTitle("Functionality won't work");
                            builder.setMessage(sButton);
                            builder.setPositiveButton(android.R.string.ok, null);
                            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                }
                            });
                            builder.show();
                        }
                    });
            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }


    @Override
    public void onBeaconDetected(JSONArray beaconArray, Date dateUpdated) {

    }


    /**
     * This callback provide the location of the device
     *
     * @param relativeLocation provide x,y of the device on particular map
     * @param maps
     * @param dateUpdated      time stamp of the location provided
     */
    @Override
    public void onRelativeLocationUpdated(MSTPoint relativeLocation, MSTMap[] maps, Date dateUpdated) {
        if (relativeLocation != null && maps != null) {
            mstPoint = relativeLocation;
            updateRelativeLocation();
        }
    }

    private void updateRelativeLocation() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (currentMap != null && addedMap) {
                        renderBlueDot(mstPoint);
                    }
                }
            });
        }
    }

    //logic to show the blue dot for the location
    public void renderBlueDot(final MSTPoint point) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (binding.floorPlanImage.getDrawable() != null && currentMap != null && point != null && addedMap) {
                        float xPos = convertCloudPointToFloorplanXScale(point.getX());
                        float yPos = convertCloudPointToFloorplanYScale(point.getY());

                        // If scaleX and scaleY are not defined, check again
                        if (!scaleFactorCalled && (scaleXFactor == 0 || scaleYFactor == 0)) {
                            setupScaleFactorForFloorplan();
                        }
                        float leftMargin = floorImageLeftMargin + (xPos - (binding.floorplanBluedotView.getWidth() / 2));
                        float topMargin = floorImageTopMargin + (yPos - (binding.floorplanBluedotView.getHeight() / 2));

                        binding.floorplanBluedotView.setX(leftMargin);
                        binding.floorplanBluedotView.setY(topMargin);
                        binding.floorplanBluedotView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }


    //calculating the scale factors
    private void setupScaleFactorForFloorplan() {
        ViewTreeObserver vto = binding.floorPlanImage.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                floorImageLeftMargin = binding.floorPlanImage.getLeft();
                floorImageTopMargin = binding.floorPlanImage.getTop();
                if (binding.floorPlanImage.getDrawable() != null) {
                    scaleXFactor = (binding.floorPlanImage.getWidth() / (double) binding.floorPlanImage.getDrawable().getIntrinsicWidth());
                    scaleYFactor = (binding.floorPlanImage.getHeight() / (double) binding.floorPlanImage.getDrawable().getIntrinsicHeight());
                    scaleFactorCalled = true;
                }
            }
        });
    }

    //converting the x point from meter's to pixel with the present scaling factor of the map rendered in the imageview
    private float convertCloudPointToFloorplanXScale(double meter) {
        return (float) (meter * this.scaleXFactor * currentMap.getPpm());
    }

    //converting the y point from meter's to pixel with the present scaling factor of the map rendered in the imageview
    private float convertCloudPointToFloorplanYScale(double meter) {
        return (float) (meter * this.scaleYFactor * currentMap.getPpm());
    }

    @Override
    public void onPressureUpdated(double pressure, Date dateUpdated) {

    }


    /**
     * This callback provide the detail of map user is on
     *
     * @param map         Map object having details about the map
     * @param dateUpdated
     */
    @Override
    public void onMapUpdated(MSTMap map, Date dateUpdated) {
        floorPlanImageUrl = map.getMapImageUrl();
        //Log.d(TAG, floorPlanImageUrl);
        if (getActivity() != null && (binding.floorPlanImage.getDrawable() == null || this.currentMap == null || !this.currentMap.getMapId().equals(map.getMapId()))) {
            // Set the current map
            this.currentMap = map;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    renderImage(floorPlanImageUrl);
                }
            });
        }
    }

    /**
     * This method is used for rendering the map image using the url from the MSTMap object received from OnMapUpdated callback
     *
     * @param floorPlanImageUrl map image url
     */
    private void renderImage(final String floorPlanImageUrl) {
        Log.d(TAG, "in picasso");
        addedMap = false;
        Glide.with(binding.floorPlanImage.getContext())
                .load(floorPlanImageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        renderImage(floorPlanImageUrl);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        addedMap = true;
                        binding.progressBar.setVisibility(View.GONE);

                        if (!scaleFactorCalled) {
                            setupScaleFactorForFloorplan();
                        }
                        return false;
                    }
                })
                .into(binding.floorPlanImage);

    }

    //provides the list of vBeacons atatched to your floor plan
    @Override
    public void onVirtualBeaconListUpdated(MSTVirtualBeacon[] virtualBeacons, Date dateUpdated) {
        mstVirtualBeaconMap.clear();
        for (MSTVirtualBeacon vb : virtualBeacons) {
            mstVirtualBeaconMap.put(vb.getVbid(), vb);
            Log.e(TAG, "onVirtualBeaconListUpdated: " + vb.getName() );
            Log.e(TAG, "onVirtualBeaconListUpdated: " + vb.getMessage() );
            Log.e(TAG, "onVirtualBeaconListUpdated: -------------------------------------------" );
        }
    }

    //called when notifications are received on entering a zone or passing by a vBecaon
    @Override
    public void onNotificationReceived(Date dateReceived, String message) {
        Log.d(TAG, "notification recieved!!");
        if (!Utils.isEmptyString(message)) {
            try {
                JSONObject notificationJSONObject = new JSONObject(message);
                Log.e(TAG, "onNotificationReceived: " + notificationJSONObject.toString(2) );
                String type = notificationJSONObject.getString("type");
                if (type.equalsIgnoreCase("zone-event-vb")) {
                    JSONObject messageObject = notificationJSONObject.optJSONObject("message");
                    if (messageObject != null) {
                        String proximity = messageObject.getString("proximity");
                        if (proximity.equals("near") || proximity.equals("immediate")) {
                            String messageToBeDisplayed = "";
                            String extra = messageObject.getString("Extra");
                            String vbID = messageObject.optString("vbID");
                            if (mstVirtualBeaconMap.containsKey(vbID)) {
                                MSTVirtualBeacon vb = mstVirtualBeaconMap.get(vbID);
                                messageToBeDisplayed = vb.getMessage();
                            }
                            if (TextUtils.isEmpty(messageToBeDisplayed)) {
                                if (TextUtils.isEmpty(extra)) {
                                    messageToBeDisplayed = "You're near the Anonymous VB";
                                } else {
                                    messageToBeDisplayed = String.format("You're %1$s %2$s", proximity, extra);
                                }
                            }
                            showNotification(false, messageToBeDisplayed);
                        } else if (proximity.equals("far")) {
                            String messageToBeDisplayed = "";
                            String extra = messageObject.getString("Extra");
                            String vbID = messageObject.optString("vbID");
                            if (mstVirtualBeaconMap.containsKey(vbID)) {
                                MSTVirtualBeacon vb = mstVirtualBeaconMap.get(vbID);
                                messageToBeDisplayed = vb.getMessage();
                            }
                            if (TextUtils.isEmpty(messageToBeDisplayed)) {
                                if (TextUtils.isEmpty(extra)) {
                                    messageToBeDisplayed = "You're far from the Anonymous VB";
                                } else {
                                    messageToBeDisplayed = String.format("You're %1$s %2$s", proximity, extra);
                                }
                            }
                            //action can be taken according to the need in case of far beacon
                        }
                    }

                } else if (type.equalsIgnoreCase("zones-events")) {
                    JSONObject messageObject = notificationJSONObject.optJSONObject("message");
                    if (messageObject != null) {
                        String trigger = messageObject.getString("Trigger");
                        if (trigger.equalsIgnoreCase("in")) {
                            String messageToBeDisplayed = "";
                            String extra = messageObject.getString("Extra");
                            if (TextUtils.isEmpty(extra)) {
                                messageToBeDisplayed = "You're in the Anonymous Zone";
                            } else {
                                messageToBeDisplayed = String.format("You're %1$s %2$s", trigger, extra);
                            }
                            showNotification(true, messageToBeDisplayed);
                        }
                        if (trigger.equalsIgnoreCase("out")) {
                            String messageToBeDisplayed = "";
                            String extra = messageObject.getString("Extra");
                            if (TextUtils.isEmpty(extra)) {
                                messageToBeDisplayed = "You left the Anonymous Zone";
                            } else {
                                messageToBeDisplayed = String.format("You left the %1$s", extra);
                            }
                            showNotification(true, messageToBeDisplayed);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //showing the notifications
    private void showNotification(boolean isZone, String message) {
        final Snackbar snackBar = Snackbar.make(getActivity().findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        snackBar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        View snackBarView = snackBar.getView();
        TextView textView = snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(blackColor);
        if (isZone) {
            snackBarView.setBackgroundColor(zoneColor);
        } else {
            snackBarView.setBackgroundColor(vbColor);
        }
        snackBar.show();
    }

    @Override
    public void onClientInformationUpdated(String clientName) {

    }

    @Override
    public void receivedLogMessageForCode(String message, MSTCentralManagerStatusCode code) {
    }

    @Override
    public void receivedVerboseLogMessage(String message) {
    }

    //callback for error
    @Override
    public void onMistErrorReceived(String message, Date date) {
        binding.progressBar.setVisibility(View.GONE);
        binding.txtError.setVisibility(View.VISIBLE);
        binding.txtError.setText(message);
    }

    @Override
    public void onMistRecommendedAction(String message) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MistManager.newInstance(mainApplication).destroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        //disconnecting the Mist SDK
        MistManager.newInstance(mainApplication).disconnect();
    }
}
