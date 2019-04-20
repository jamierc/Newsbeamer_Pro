package com.jrcdesign.newsbeamer_pro;
 
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;
 
public class Settings extends PreferenceActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.layout.preferences);
                // Get the custom preference
                Preference customPref = (Preference) findPreference("customPref");
                customPref
                                .setOnPreferenceClickListener(new OnPreferenceClickListener() {
 
                                        public boolean onPreferenceClick(Preference preference) {
                                                
                                                Intent i = new Intent(Intent.ACTION_SEND);
                                                i.setType("message/rfc822");
                                                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"newsbeamer.android@gmail.com"});
                                                i.putExtra(Intent.EXTRA_SUBJECT, "Feedback from newsbeamer_pro");
                                                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                                                try {
                                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                                } catch (android.content.ActivityNotFoundException ex) {
                                                    Toast.makeText(getBaseContext(), "There are no email clients installed", Toast.LENGTH_LONG).show();
                                                }
                                                
                                                
                                                
                                                
                                                
                                                
                                                
                                                SharedPreferences customSharedPreference = getSharedPreferences(
                                                                "myCustomSharedPrefs", Activity.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = customSharedPreference
                                                                .edit();
                                            
                                                editor.commit();
                                                return true;
                                        }
 
                                });
        }
}