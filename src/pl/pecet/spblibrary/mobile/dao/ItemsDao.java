package pl.pecet.spblibrary.mobile.dao;

import java.util.ArrayList;
import java.util.List;

import pl.pecet.spblibrary.mobile.dbutils.LibrarySQLiteOpenHelper;
import pl.pecet.spblibrary.mobile.domain.Item;
import pl.pecet.spblibrary.mobile.domain.ItemCategory;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItemsDao {

	private static final String TABLE_ITEMS = "Items";

	private static final String COLUMN_ID = "Id";

	private static final String COLUMN_TITLE = "Title";

	private static final String COLUMN_AUTHOR = "Author";

	private static final String COLUMN_YEAR = "Year";

	private static final String COLUMN_CATEGORY = "Category";

	private static final String COLUMN_PHOTO = "Photo";

	private static final String COLUMN_BAR_CODE = "BarCode";

	private static final String[] ALL_COLUMNS = { COLUMN_ID, COLUMN_TITLE,
			COLUMN_AUTHOR, COLUMN_YEAR, COLUMN_PHOTO, COLUMN_CATEGORY,
			COLUMN_BAR_CODE };

	private final LibrarySQLiteOpenHelper dbHelper;

	private SQLiteDatabase database;

	public ItemsDao(final Context context) {
		dbHelper = new LibrarySQLiteOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Long create(final Item item) {
		final ContentValues values = new ContentValues();
		values.put(COLUMN_TITLE, item.getTitle());
		values.put(COLUMN_AUTHOR, item.getAuthor());
		values.put(COLUMN_YEAR, item.getYear());
		values.put(COLUMN_PHOTO, item.getPhoto());
		values.put(COLUMN_CATEGORY, item.getCategory().getCode());
		values.put(COLUMN_BAR_CODE, item.getBarCode());

		final Long id = database.insert(TABLE_ITEMS, null, values);
		return id;
	}

	public void delete(final Item item) {
		final Long id = item.getId();

		database.delete(TABLE_ITEMS, COLUMN_ID + " = ?",
				new String[] { Long.toString(id) });
	}

	public void deleteAll() {
		database.delete(TABLE_ITEMS, null, null);
	}

	public List<Item> getAll() {
		final List<Item> allItems = new ArrayList<Item>();

		final Cursor cursor = database.query(TABLE_ITEMS, ALL_COLUMNS, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Item item = cursorToItem(cursor);
			allItems.add(item);
			cursor.moveToNext();
		}
		cursor.close();

		return allItems;
	}

	public List<Item> findItems(final String barCode) {
		final List<Item> items = new ArrayList<Item>();

		final Cursor cursor = database.query(TABLE_ITEMS, ALL_COLUMNS,
				COLUMN_BAR_CODE + "= ?", new String[] { barCode }, null, null,
				null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Item item = cursorToItem(cursor);
			items.add(item);
			cursor.moveToNext();
		}
		cursor.close();

		return items;
	}

	private Item cursorToItem(final Cursor cursor) {
		final Item item = new Item();

		item.setId(cursor.getLong(0));
		item.setTitle(cursor.getString(1));
		item.setAuthor(cursor.getString(2));
		item.setYear(cursor.getInt(3));
		item.setPhoto(cursor.getBlob(4));
		final int categoryCode = cursor.getInt(5);
		if (categoryCode == ItemCategory.MAP.getCode()) {
			item.setCategory(ItemCategory.MAP);
		} else {
			item.setCategory(ItemCategory.BOOK);
		}
		item.setBarCode(cursor.getString(6));

		return item;
	}
}
