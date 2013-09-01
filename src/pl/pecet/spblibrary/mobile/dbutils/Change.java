package pl.pecet.spblibrary.mobile.dbutils;

import android.database.sqlite.SQLiteDatabase;

public interface Change {
	void apply(final SQLiteDatabase database);

	void revert(final SQLiteDatabase database);
}
