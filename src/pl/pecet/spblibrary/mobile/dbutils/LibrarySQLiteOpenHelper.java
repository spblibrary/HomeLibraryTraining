package pl.pecet.spblibrary.mobile.dbutils;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LibrarySQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "db.spblibrary";

	private static final Change[] DATABASE_SCHEMA_CHANGES = getDatabaseSchemaChanges();

	public LibrarySQLiteOpenHelper(final Context context) {
		super(context, DATABASE_NAME, null, DATABASE_SCHEMA_CHANGES.length);
	}

	@Override
	public void onCreate(final SQLiteDatabase db) {
		for (int i = 0; i < DATABASE_SCHEMA_CHANGES.length; i++) {
			DATABASE_SCHEMA_CHANGES[i].apply(db);
		}
	}

	@Override
	public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		for (int i = oldVersion; i < newVersion; i++) {
			DATABASE_SCHEMA_CHANGES[i].apply(db);
		}
	}

	@Override
	public void onDowngrade(final SQLiteDatabase db, final int oldVersion,
			final int newVersion) {
		for (int i = oldVersion; i > newVersion; i--) {
			DATABASE_SCHEMA_CHANGES[i - 1].revert(db);
		}
	}

	private static Change[] getDatabaseSchemaChanges() {
		final List<Change> changesList = DbChangesParser.getDbChanges();
		return changesList.toArray(new Change[changesList.size()]);
	}
}
