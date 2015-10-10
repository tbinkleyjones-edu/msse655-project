package edu.regis.msse655.annotatedbibliography.service;

import android.provider.BaseColumns;

/**
 * A container for constants that define names for URs, tables, and columns.
 */
public final class ReferenceContract {

    /**
     * A private constructor to prevent instantiation.
     */
    private ReferenceContract() {
    }

    public static final String DATABASE_NAME = "References.db";
    public static final int DATABASE_VERSION = 1;

    public static abstract class ReferenceTable implements BaseColumns {
        public static final String TABLE_NAME = "reference";
        public static final String COLUMN_NAME_MEDIA_TITLE = "media_title";
        public static final String COLUMN_NAME_REFERENCE_TITLE = "reference_title";
        public static final String COLUMN_NAME_DETAILS = "details";
        public static final String COLUMN_NAME_KEYWORDS = "keywords";
        public static final String COLUMN_NAME_ABSTRACT = "abstract";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_DOI = "doi";
        public static final String COLUMN_NAME_TYPE_OF_MEDIA = "type_of_media";
        public static final String COLUMN_NAME_AUTHORS = "authors";
        public static final String COLUMN_NAME_FAVORITE = "favorite";
        public static final String COLUMN_NAME_DATE_MODIFIED = "date_modified";
    }
}
