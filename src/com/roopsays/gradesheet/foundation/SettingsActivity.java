package com.roopsays.gradesheet.foundation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.roopsays.gradesheet.foundation.util.Toggles;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends PreferenceActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		if ( !Toggles.ENABLE_GRADE_SCALE.on()) {
			//This can enable or disable, but it was not successful in hiding it, as I misunderstood the setView setting.
			//getPreferenceManager().findPreference("displayGradeScale").setEnabled(true);
			
			
			//This is not working yet. 
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
			sharedPref.edit().remove("displayGradeScale");
			sharedPref.edit().commit();
		}
	}

}
