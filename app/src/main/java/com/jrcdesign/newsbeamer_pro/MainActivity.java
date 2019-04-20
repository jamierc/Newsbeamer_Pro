package com.jrcdesign.newsbeamer_pro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyInterstitial;
import com.adcolony.sdk.AdColonyInterstitialListener;
import com.adcolony.sdk.AdColonyZone;
import com.facebook.ads.*;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;


public class MainActivity extends Activity  {
    static final int DATE_DIALOG_ID = 0;
    static final int TIME_DIALOG_ID = 1;
    public static Context appContext;
    Button sendButton;
 //   public static String INTERSTITIAL_UNIT = "cf644df6a32a4a98bba6f506668a4461";
    Button sendButton2;
    EditText msgTextField;
    EditText usernameTextField;
    EditText passwordTextField;
    Spinner spinner1;
    ProgressDialog pd;
    //private AdView adView;
    EditText mDateDisplay;
    EditText mTimeDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int hourOfDay;
    //    String ListPreference1;
    //String localTime;
    private int minute;
    private int mhourOfDay;
    private int mminute;
    private Button b1;

  //  private MoPubInterstitial mInterstitial;

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            //  Toast.makeText(MainActivity.this, "Time is="+hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
            mTimeDisplay.setText(
                    new StringBuilder()

                            .append(hourOfDay).append(":")

                            .append(minute));
            //       .append(hourOfDay).append(":")
            //     .append(minute).append("")


        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the layout
        setContentView(R.layout.main);

     //   initMopub();

        b1 = findViewById(R.id.Button01);
        mTimeDisplay = findViewById(R.id.timeDisplay);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });


        // make message text field object
        usernameTextField = findViewById(R.id.usernameTextField);
        passwordTextField = findViewById(R.id.passwordTextField);


        // make send button object
        sendButton = findViewById(R.id.sendButton);
        sendButton2 = findViewById(R.id.sendButton2);

        pd = new ProgressDialog(MainActivity.this);
        //  	addListenerOnButton();
        addListenerOnSpinnerItemSelection();


        // capture our View elements
        mDateDisplay = findViewById(R.id.dateDisplay);
        mPickDate = findViewById(R.id.pickDate);

        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mhourOfDay = c.get(Calendar.HOUR_OF_DAY);
        mminute = c.get(Calendar.MINUTE);


        // display the current date (this method is below)
        updateDisplay();
    }



    // updates the date in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        .append(mMonth + 1).append("/")
                        .append(mDay).append("/")
                        .append(mYear)
        );

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(this, mTimeSetListener, mhourOfDay, mminute, true);

        }
        return null;
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //this method defines what happens when menus are selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            //test dialogue

            final AlertDialog d = new AlertDialog.Builder(this)
                    .setPositiveButton(android.R.string.ok, null)
                    .setMessage(Html.fromHtml("\u00A9 Privacy Policy can be found here: https://sites.google.com/view/newsbeamer/privacy-policy. <br><br> JRC Design 2019<br><br>Version 503(ads)<br><br> This programe relies on scripts and tools from the excellent ebook management program, Calibre (https://calibre-ebook.com) <br><br> This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program. If not,see http://www.gnu.org/licenses."))
                    .create();
            d.show();
            // Make the textview clickable. Must be called after show()
            ((TextView) d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
            ((TextView) d.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);


        }
        if (item.getItemId() == R.id.help) {


            //help dialogue
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Instructions");
            builder.setMessage("NB. This pro subscription entitles you to a daily, weekly, and monthly scheduled subscription for one year. Please email me to set this up. <br><br>1. Log on to your Amazon account and add ebookbeamer@gmail.com to the list of allowed senders in your Kindle settings. \n\n2. Enter your Kindle email address in the Newsbeamer settings menu.\n\n3. Choose your formatting options from the Newsbeamer settings menu.\n\n4. Choose a publication, and whether to send it now or later. \n\n5. Please just press the send button once.");
            builder.setPositiveButton("OK", null);
            AlertDialog dialog = builder.show();

            // Must call show() prior to fetching text view
            TextView messageView = dialog.findViewById(android.R.id.message);
            messageView.setGravity(Gravity.LEFT);


            //startActivity(new Intent(this, Help.class));
        }

        if (item.getItemId() == R.id.preferences) {
            startActivity(new Intent(this, Settings.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //trying to get input from menus
    public void addListenerOnSpinnerItemSelection() {
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener1());
        spinner1.setSelection(1);
        //	String recipe_title=String.valueOf(spinner1.getSelectedItem());
        // 	Toast.makeText(MainActivity.this, recipe_title, Toast.LENGTH_SHORT).show();

    }

    public void onItemSelected(AdapterView<?> spinner1, View view, int pos, long id) {
//	  String recipe_title=String.valueOf(spinner1.getSelectedItem());

        String recipe_title = getResources().getStringArray(R.array.recipe_array)[spinner1.getSelectedItemPosition()];

        Toast.makeText(MainActivity.this, "testing", Toast.LENGTH_SHORT).show();


        // String recipe = getResources().getStringArray(R.array.recipe_values)[pos];

        if (recipe_title.contains("sub")) {

            usernameTextField.setVisibility(View.VISIBLE);
            passwordTextField.setVisibility(View.VISIBLE);


        } else {

            usernameTextField.setVisibility(View.INVISIBLE);
            passwordTextField.setVisibility(View.INVISIBLE);


        }

    }

    // this is the function that gets called when you click the button
    public void send(View v) {

        boolean CheckboxPreference;
        String ListPreference;
        String ListPreference1;
        String editTextPreference;

        String CachedPreference;


        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        CheckboxPreference = prefs.getBoolean("checkboxPref", true);
        String CheckboxPreference1 = String.valueOf(CheckboxPreference);
        ListPreference = prefs.getString("listPref", "nr1");


        if (ListPreference.contains("kindle")) {
            ListPreference1 = "mobi";
        } else if (ListPreference.contains("nook")) {
            ListPreference1 = "epub";
        } else if (ListPreference.contains("ipad")) {
            ListPreference1 = "epub";
        } else if (ListPreference.contains("pdf")) {
            ListPreference1 = "pdf";
            ListPreference = "kindle";

        } else if (ListPreference.contains("kobo")) {
            ListPreference1 = "epub";
        } else {
            ListPreference1 = "mobi";
        }


        CachedPreference = prefs.getString("cachePref", "nr1");
        editTextPreference = prefs.getString("editTextPref",
                "");
// Get the custom preference
        SharedPreferences mySharedPreferences = getSharedPreferences(
                "myCustomSharedPrefs", Activity.MODE_PRIVATE);


        // get the message from the message text box

        if (usernameTextField.getText().toString().length() <= 0) {
            usernameTextField.setText("user");

        } else {
            String username = usernameTextField.getText().toString();
        }


        if (passwordTextField.getText().toString().length() <= 0) {
            passwordTextField.setText("pass");

        } else {
            String password = passwordTextField.getText().toString();
        }


        int pos = spinner1.getSelectedItemPosition();
        String recipe_title = getResources().getStringArray(R.array.recipe_array)[pos];
        String recipe = getResources().getStringArray(R.array.recipe_values)[pos];
        String strTime = mTimeDisplay.getText().toString();
        String strDate = mDateDisplay.getText().toString();

        String username = usernameTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();

        DateFormat date = new SimpleDateFormat("z", Locale.getDefault());
        //   String localTime = date.format(currentLocalTime);
        String tz = TimeZone.getDefault().getID();


        // make sure the fields are not empty
        if (editTextPreference.length() <= 0) {
            Toast.makeText(getBaseContext(), "Please enter your email address in the settings menu", Toast.LENGTH_LONG).show();
        } else if (recipe_title.contains("subscription") && (usernameTextField.length() <= 0)) {
            Toast.makeText(getBaseContext(), "This publication requires a username and password", Toast.LENGTH_LONG).show();
        } else {
            new sendmsg().execute(recipe, editTextPreference, ListPreference, ListPreference1, CheckboxPreference1, CachedPreference, username, password, strDate, strTime, tz);
        }

    }

    // this is the function that gets called when you click the button
    public void send_scheduled(View v) {

        boolean CheckboxPreference;
        String ListPreference;
        String ListPreference1;
        String editTextPreference;
        String ringtonePreference;
        String secondEditTextPreference;
        String customPref;
        boolean Scheduled;


        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());

        CheckboxPreference = prefs.getBoolean("checkboxPref", true);
        String CheckboxPreference1 = String.valueOf(CheckboxPreference);
        ListPreference = prefs.getString("listPref", "nr1");
//ListPreference1 = prefs.getString("listPref1", "nr1");
        String CachedPreference = "scheduled";
//CachedPreference = prefs.getString("cachePref", "nr1");

        editTextPreference = prefs.getString("editTextPref",
                "");
        ringtonePreference = prefs.getString("ringtonePref",
                "DEFAULT_RINGTONE_URI");
        secondEditTextPreference = prefs.getString("SecondEditTextPref",
                "");
// Get the custom preference
        SharedPreferences mySharedPreferences = getSharedPreferences(
                "myCustomSharedPrefs", Activity.MODE_PRIVATE);
        customPref = mySharedPreferences.getString("myCustomPref", "");


        if (ListPreference.contains("kindle")) {
            ListPreference1 = "mobi";
        } else if (ListPreference.contains("nook")) {
            ListPreference1 = "epub";
        } else if (ListPreference.contains("ipad")) {
            ListPreference1 = "epub";
        } else if (ListPreference.contains("pdf")) {
            ListPreference1 = "pdf";
            ListPreference = "kindle";

        } else if (ListPreference.contains("kobo")) {
            ListPreference1 = "epub";
        } else {
            ListPreference1 = "mobi";
        }


        // get the message from the message text box

        if (usernameTextField.getText().toString().length() <= 0) {
            usernameTextField.setText("user");

        } else {
            String username = usernameTextField.getText().toString();
        }


        if (passwordTextField.getText().toString().length() <= 0) {
            passwordTextField.setText("pass");

        } else {
            String password = passwordTextField.getText().toString();
        }


        int pos = spinner1.getSelectedItemPosition();
        String recipe_title = getResources().getStringArray(R.array.recipe_array)[pos];
        String recipe = getResources().getStringArray(R.array.recipe_values)[pos];
        String strTime = mTimeDisplay.getText().toString();
        String strDate = mDateDisplay.getText().toString();

        String username = usernameTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();

        DateFormat date = new SimpleDateFormat("z", Locale.getDefault());
        //  String localTime = date.format(currentLocalTime);
        String tz = TimeZone.getDefault().getID();

        // make sure the fields are not empty
        if (editTextPreference.length() <= 0) {
            Toast.makeText(getBaseContext(), "Please enter your email address in the settings menu", Toast.LENGTH_LONG).show();
        } else if (recipe_title.contains("subscription") && (usernameTextField.length() <= 0)) {
            Toast.makeText(getBaseContext(), "This publication requires a username and password", Toast.LENGTH_LONG).show();
        } else
            new sendmsg2().execute(recipe, editTextPreference, ListPreference, ListPreference1, CheckboxPreference1, CachedPreference, username, password, strDate, strTime, tz);

    }

    public boolean sendOverHttp(String recipe, String editTextPreference, String ListPreference, String ListPreference1, String CheckboxPreference1, String CachedPreference, String username, String password, String strDate, String strTime, String tz) {
        Log.d("NetworkLogs", "SendOverHttp started");
        String urlStr = "http://108.61.197.169/newsbeamer.php";
        Log.d("NetworkLogs", "Sending request to " + urlStr);
        URL url;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

        try {
            String postParams = "message=" + recipe + "&email=" + editTextPreference + "&format=" + ListPreference + "&extension=" + ListPreference1 + "&cache=" + CheckboxPreference1 + "&username=" + username + "&password=" + password + "&cacheage=" + CachedPreference + "&date=" + strDate + "&time=" + strTime + "&tz=" + tz;
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.connect();
            Log.d("NetworkLogs", "Connection successful");
            OutputStream os = null;
            os = new BufferedOutputStream(
                    httpUrlConnection.getOutputStream());
            os.write(postParams.getBytes());
            os.flush();
            Log.d("NetworkLogs", "Response code: " + httpUrlConnection.getResponseCode());

            InputStream is = httpUrlConnection.getInputStream();

            String resp = readFullyAsString(is, "UTF-8");
            Log.d("NetworkLogs", "Response: " + resp);
            is.close();
            httpUrlConnection.disconnect();// close your connection
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }


    public String readFullyAsString(InputStream inputStream, String encoding) throws IOException {
        return readFully(inputStream).toString(encoding);
    }

    private ByteArrayOutputStream readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }




    public class CustomOnItemSelectedListener1 implements OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


            String recipe_title = parent.getItemAtPosition(pos).toString();

            //Toast.makeText(parent.getContext(),
//        			"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
            //	Toast.LENGTH_SHORT).show();

            //		"Selected : " + recipe_title,Toast.LENGTH_SHORT).show();

            if (recipe_title.contains("subscription")) {
                usernameTextField.setVisibility(View.VISIBLE);
                passwordTextField.setVisibility(View.VISIBLE);
            } else {
                usernameTextField.setVisibility(View.GONE);
                passwordTextField.setVisibility(View.GONE);
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }


    public class sendmsg2 extends AsyncTask<String, Integer, Boolean> {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        String editTextPreference = prefs.getString("editTextPref", "Nothing has been entered");

        String strTime = mTimeDisplay.getText().toString();
        String strDate = mDateDisplay.getText().toString();
        int pos = spinner1.getSelectedItemPosition();
        String recipe_title = getResources().getStringArray(R.array.recipe_array)[pos];
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();

        DateFormat date = new SimpleDateFormat("z", Locale.getDefault());
        //  String localTime = date.format(currentLocalTime);
        //TimeZone tz = TimeZone.getDefault();
        String tz = TimeZone.getDefault().getID();


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pd.setTitle("Please wait...");
            pd.setMessage("" + recipe_title + " is being scheduled.");
            pd.setCancelable(true);
            pd.show();
            startHandler();
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return sendOverHttp(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10]);


        }

        @Override
        protected void onPostExecute(Boolean result) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getBaseContext());
            Boolean cached = prefs.getBoolean("checkboxPref", true);

            if (result) {

                usernameTextField.setText(""); // clear text box
                passwordTextField.setText(""); // clear text box


                Toast.makeText(MainActivity.this, "Your publication," + recipe_title + ", has been scheduled for " + strTime + " on " + strDate, Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(MainActivity.this, "Some problem occured", Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(result);
        }


    }


    public class sendmsg extends AsyncTask<String, Integer, Boolean> {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        String editTextPreference = prefs.getString("editTextPref", "Nothing has been entered");

        int pos = spinner1.getSelectedItemPosition();
        String recipe_title = getResources().getStringArray(R.array.recipe_array)[pos];

        @Override


        protected void onPreExecute() {
            // TODO Auto-generated method stub
            pd.setTitle("Please wait...");
            pd.setMessage("" + recipe_title + " will be sent to " + editTextPreference + ".");
            pd.setCancelable(false);
            pd.show();
            startHandler();
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            return sendOverHttp(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10]);


        }

        @Override
        protected void onPostExecute(Boolean result) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getBaseContext());
            Boolean cached = prefs.getBoolean("checkboxPref", true);



            if (result) {

                usernameTextField.setText(""); // clear text box
                passwordTextField.setText(""); // clear text box

                Toast.makeText(MainActivity.this, "" + recipe_title + " will shortly be sent to " + editTextPreference + ".", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(MainActivity.this, "Some problem occured", Toast.LENGTH_LONG).show();
            }


            super.onPostExecute(result);
        }


    }

    private void startHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()){
                    pd.dismiss();
                }

            }
        }, 3000);
    }


    }

