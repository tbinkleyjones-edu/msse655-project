package edu.regis.msse655.annotatedbibliography.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.regis.msse655.annotatedbibliography.model.Reference;
import edu.regis.msse655.annotatedbibliography.model.ReferenceFilter;
import edu.regis.msse655.annotatedbibliography.model.TypeOfMedia;
import edu.regis.msse655.annotatedbibliography.service.ReferenceContract.ReferenceTable;

/**
 *
 */
public class ReferenceServiceSQLiteImpl extends SQLiteOpenHelper implements IReferenceService {

    /*
     * SQL queries and statements
     */
    private static final String SQL_CREATE_REFERENCE_TABLE =
            "CREATE TABLE " + ReferenceTable.TABLE_NAME + " (" +
                    ReferenceTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ReferenceTable.COLUMN_NAME_MEDIA_TITLE + " TEXT," +
                    ReferenceTable.COLUMN_NAME_REFERENCE_TITLE + " TEXT," +
                    ReferenceTable.COLUMN_NAME_DETAILS + " TEXT," +
                    ReferenceTable.COLUMN_NAME_KEYWORDS + " TEXT," +
                    ReferenceTable.COLUMN_NAME_ABSTRACT + " TEXT," +
                    ReferenceTable.COLUMN_NAME_NOTES + " TEXT," +
                    ReferenceTable.COLUMN_NAME_URL + " TEXT," +
                    ReferenceTable.COLUMN_NAME_DOI + " TEXT," +
                    ReferenceTable.COLUMN_NAME_TYPE_OF_MEDIA + " TEXT," +
                    ReferenceTable.COLUMN_NAME_AUTHORS + " TEXT," +
                    ReferenceTable.COLUMN_NAME_FAVORITE + " INTEGER," +
                    ReferenceTable.COLUMN_NAME_DATE_MODIFIED + " INTEGER" +
                    " )";

    public static final String SQL_DROP_REFERENCE_TABLE =
            "DROP TABLE IF EXISTS " + ReferenceTable.TABLE_NAME;

    public static final String[] SQL_COLUMNS = new String[]{
            ReferenceTable._ID,
            ReferenceTable.COLUMN_NAME_MEDIA_TITLE,
            ReferenceTable.COLUMN_NAME_REFERENCE_TITLE,
            ReferenceTable.COLUMN_NAME_DETAILS,
            ReferenceTable.COLUMN_NAME_KEYWORDS,
            ReferenceTable.COLUMN_NAME_ABSTRACT,
            ReferenceTable.COLUMN_NAME_NOTES,
            ReferenceTable.COLUMN_NAME_URL,
            ReferenceTable.COLUMN_NAME_DOI,
            ReferenceTable.COLUMN_NAME_TYPE_OF_MEDIA,
            ReferenceTable.COLUMN_NAME_AUTHORS,
            ReferenceTable.COLUMN_NAME_FAVORITE,
            ReferenceTable.COLUMN_NAME_DATE_MODIFIED
    };

    public static final String SQL_SELECT_BY_ID = ReferenceTable._ID + "=?";

    public ReferenceServiceSQLiteImpl(Context context) {
        this(context, ReferenceContract.DATABASE_NAME);
    }

    /**
     * A protected constructor to enable unit testing
     *
     * @param context
     * @param name
     */
    protected ReferenceServiceSQLiteImpl(Context context, String name) {
        super(context, name, null, ReferenceContract.DATABASE_VERSION);
    }

    /*
     * SQLiteOpenHelper implementation
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_REFERENCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this version of onUpgrade just deletes all existing data and calls onCreate.
        db.execSQL(SQL_CREATE_REFERENCE_TABLE);
        onCreate(db);
    }

    /*
     * IReferenceSvc implementation
     */

    @Override
    public List<Reference> retrieveReferences(ReferenceFilter filter) {
        String selection = null;
        String limit = null;
        String orderBy = null; //ReferenceTable.COLUMN_NAME_AUTHORS + " ASC";
        switch (filter) {
            case RECENT:
                // this version of the service returns the 2 most recently modified References for RECENT
                orderBy = ReferenceTable.COLUMN_NAME_DATE_MODIFIED + " DESC";
                limit = "2";
                break;
            case FAVORITES:
                selection = ReferenceTable.COLUMN_NAME_FAVORITE + "=1";
                break;
            case ALL:
            default:
                // nothing to change
                break;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ReferenceTable.TABLE_NAME,
                SQL_COLUMNS,
                selection,
                null, // no selection args
                null, // no group by
                null, // no having
                orderBy,
                limit
        );

        ArrayList<Reference> referenceList = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Reference reference = getReference(cursor);
            referenceList.add(reference);
            cursor.moveToNext();
        }

        return Collections.unmodifiableList(new ArrayList<>(referenceList));
    }

    private Reference getReference(Cursor cursor) {
        Reference reference = new Reference();
        // fill fields in the same order the appear in the SQL_COLUMNS array.
        reference.setId(cursor.getInt(0));
        reference.setMediaTitle(cursor.getString(1));
        reference.setReferenceTitle(cursor.getString(2));
        reference.setDetails(cursor.getString(3));
        reference.setKeywords(cursor.getString(4));
        reference.setReferenceAbstract(cursor.getString(5));
        reference.setNotes(cursor.getString(6));
        reference.setUrl(cursor.getString(7));
        reference.setDoi(cursor.getString(8));
        reference.setTypeOfMedia(TypeOfMedia.valueOf(cursor.getString(9)));
        reference.setAuthors(cursor.getString(10));
        reference.setFavorite((cursor.getInt(11) == 1));
        reference.setDateModified(cursor.getLong(12));

        Log.d("RefSvcSQLiteImpl", "Hydrating reference with id: " + reference.getId());
        return reference;
    }

    @Override
    public List<Reference> retrieveAllReferences() {
        return retrieveReferences(ReferenceFilter.ALL);
    }

    @Override
    public Reference retrieveReference(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ReferenceTable.TABLE_NAME,
                SQL_COLUMNS,
                SQL_SELECT_BY_ID,
                new String[]{String.valueOf(id)}, // no selection args
                null, // no group by
                null, // no having
                null, // no order by
                null // no limit
        );

        Reference reference = null;
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            reference = getReference(cursor);
        }

        return reference;
    }

    @Override
    public Reference create(Reference reference) {
        // update the date modified value here
        reference.setDateModified(System.currentTimeMillis());

        SQLiteDatabase db = getWritableDatabase();

        // copy the reference's values into a ContentValues map.
        ContentValues values = new ContentValues();
        values.put(ReferenceTable.COLUMN_NAME_MEDIA_TITLE, reference.getMediaTitle());
        values.put(ReferenceTable.COLUMN_NAME_REFERENCE_TITLE, reference.getReferenceTitle());
        values.put(ReferenceTable.COLUMN_NAME_DETAILS, reference.getDetails());
        values.put(ReferenceTable.COLUMN_NAME_KEYWORDS, reference.getKeywords());
        values.put(ReferenceTable.COLUMN_NAME_ABSTRACT, reference.getReferenceAbstract());
        values.put(ReferenceTable.COLUMN_NAME_NOTES, reference.getNotes());
        values.put(ReferenceTable.COLUMN_NAME_URL, reference.getUrl());
        values.put(ReferenceTable.COLUMN_NAME_DOI, reference.getDoi());
        values.put(ReferenceTable.COLUMN_NAME_TYPE_OF_MEDIA, reference.getTypeOfMedia().name());
        values.put(ReferenceTable.COLUMN_NAME_AUTHORS, reference.getAuthors());
        values.put(ReferenceTable.COLUMN_NAME_FAVORITE, reference.isFavorite() ? 1 : 0);
        values.put(ReferenceTable.COLUMN_NAME_DATE_MODIFIED, reference.getDateModified());

        long id = db.insert(ReferenceTable.TABLE_NAME, null, values);

        // TODO: check the value of id - bad if it is -1

        // use the newly  generated row id as the reference's id
        reference.setId(id);
        return reference;
    }

    @Override
    public Reference update(Reference reference) {
        // update the date modified value here
        reference.setDateModified(System.currentTimeMillis());

        SQLiteDatabase db = getWritableDatabase();

        // copy the reference's values into a ContentValues map.
        ContentValues values = new ContentValues();
        values.put(ReferenceTable.COLUMN_NAME_MEDIA_TITLE, reference.getMediaTitle());
        values.put(ReferenceTable.COLUMN_NAME_REFERENCE_TITLE, reference.getReferenceTitle());
        values.put(ReferenceTable.COLUMN_NAME_DETAILS, reference.getDetails());
        values.put(ReferenceTable.COLUMN_NAME_KEYWORDS, reference.getKeywords());
        values.put(ReferenceTable.COLUMN_NAME_ABSTRACT, reference.getReferenceAbstract());
        values.put(ReferenceTable.COLUMN_NAME_NOTES, reference.getNotes());
        values.put(ReferenceTable.COLUMN_NAME_URL, reference.getUrl());
        values.put(ReferenceTable.COLUMN_NAME_DOI, reference.getDoi());
        values.put(ReferenceTable.COLUMN_NAME_TYPE_OF_MEDIA, reference.getTypeOfMedia().name());
        values.put(ReferenceTable.COLUMN_NAME_AUTHORS, reference.getAuthors());
        values.put(ReferenceTable.COLUMN_NAME_FAVORITE, reference.isFavorite() ? 1 : 0);
        values.put(ReferenceTable.COLUMN_NAME_DATE_MODIFIED, reference.getDateModified());

        long rowsUpdatedCount = db.update(ReferenceTable.TABLE_NAME, values,
                SQL_SELECT_BY_ID, new String[]{String.valueOf(reference.getId())});

        // TODO: check that the number of rows updated is exactly one.

        return reference;

    }

    @Override
    public Reference delete(Reference reference) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeletedCount = db.delete(ReferenceTable.TABLE_NAME,
                SQL_SELECT_BY_ID, new String[]{String.valueOf(reference.getId())});

        // TODO: check that the number of rows deleted is exactly one.

        return reference;
    }

    @Override
    public void clear() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ReferenceTable.TABLE_NAME, null, null);
    }


}
