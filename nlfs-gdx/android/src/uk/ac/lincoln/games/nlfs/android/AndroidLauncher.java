package uk.ac.lincoln.games.nlfs.android;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import uk.ac.lincoln.games.nlfs.NonLeague;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new NonLeague(tm.getDeviceId()), config);
	}
}
