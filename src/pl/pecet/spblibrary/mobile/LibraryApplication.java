package pl.pecet.spblibrary.mobile;

import android.app.Application;
import android.content.Context;

public class LibraryApplication extends Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		LibraryApplication.context = getApplicationContext();
	}

	public static Context getLibraryApplicationContext() {
		return LibraryApplication.context;
	}
}
