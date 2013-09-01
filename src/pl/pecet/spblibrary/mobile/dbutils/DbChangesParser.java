package pl.pecet.spblibrary.mobile.dbutils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pl.pecet.spblibrary.mobile.LibraryApplication;
import pl.pecet.spblibrary.mobile.xmlparser.XMLParser;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class DbChangesParser {

	private static final String CHANGE_NODE = "change";

	private static final String APPLY_NODE = "apply";

	private static final String REVERT_NODE = "revert";

	private DbChangesParser() {
	}

	public static List<Change> getDbChanges() {
		try {
			final InputStream dbChangesXmlFile = LibraryApplication
					.getLibraryApplicationContext().getAssets()
					.open("db-changes.xml");

			final List<Change> dbChanges = new ArrayList<Change>();

			final XMLParser xmlParser = new XMLParser();
			final Document dbChangesDocument = xmlParser
					.getDocument(dbChangesXmlFile);

			final NodeList allDbChanges = dbChangesDocument
					.getElementsByTagName(CHANGE_NODE);

			for (int i = 0; i < allDbChanges.getLength(); i++) {
				final Element element = (Element) allDbChanges.item(i);
				final String applyQuery = xmlParser.getValue(element,
						APPLY_NODE);
				final String revertQuery = xmlParser.getValue(element,
						REVERT_NODE);
				dbChanges.add(new Change() {
					@Override
					public void apply(final SQLiteDatabase database) {
						database.execSQL(applyQuery);
					}

					@Override
					public void revert(final SQLiteDatabase database) {
						database.execSQL(revertQuery);
					}
				});
			}

			return dbChanges;
		} catch (final IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}
	}
}
