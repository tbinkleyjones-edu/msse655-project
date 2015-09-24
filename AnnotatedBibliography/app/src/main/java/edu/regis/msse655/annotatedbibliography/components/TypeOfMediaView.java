package edu.regis.msse655.annotatedbibliography.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import edu.regis.msse655.annotatedbibliography.R;
import edu.regis.msse655.annotatedbibliography.model.TypeOfMedia;

/**
 * A custom TextView that handles loading the Font Awesome type face and converting from TypeOfMedia
 * to appropriate unicode character.
 *
 * Note: this class ignores any previously textAppearance attributes.
 */
public class TypeOfMediaView extends TextView {

    private Typeface fontAwesome;

    public TypeOfMediaView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if(fontAwesome==null) {
            fontAwesome = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        }
        setTypeOfMedia(TypeOfMedia.UNKNOWN);
    }

    public TypeOfMediaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypeOfMediaView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TypeOfMediaView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * Displays the appropriate icon for the specified typeOfMedia.
     *
     * Note: this method over writes the TextView's text and textAppearance properties.
     *
     * @param typeOfMedia
     */
    public void setTypeOfMedia(TypeOfMedia typeOfMedia) {
        int typeOfMediaId;
        int textAppearanceId;
        switch(typeOfMedia) {
            case BOOK:
                typeOfMediaId = R.string.icon_book;
                textAppearanceId = R.style.TypeOfMediaView_TextAppearance_Book;
            case MAGAZINE:
                typeOfMediaId = R.string.icon_magazine;
                textAppearanceId = R.style.TypeOfMediaView_TextAppearance_Magazine;
                break;
            case JOURNAL:
                typeOfMediaId = R.string.icon_journal;
                textAppearanceId = R.style.TypeOfMediaView_TextAppearance_Journal;
                break;
            case WEBSITE:
                typeOfMediaId = R.string.icon_website;
                textAppearanceId = R.style.TypeOfMediaView_TextAppearance_Website;
                break;
            default:
                typeOfMediaId = R.string.icon_other;
                textAppearanceId = R.style.TypeOfMediaView_TextAppearance_Other;
                break;
        }
        setText(getContext().getString(typeOfMediaId));
        setTextAppearance(textAppearanceId);
        setTypeface(fontAwesome);
    }

}
