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
 * Note: this class ignores any previously set fontName attribute.
 */
public class TypeOfMediaView extends TextView {

    public TypeOfMediaView(Context context) {
        super(context);
        init();
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

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fontawesome-webfont.ttf");
        setTypeface(font);
    }

    /**
     * Displays the appropriate icon for the specified typeOfMedia.
     *
     * Note: this method over writes the TextView's text property.
     *
     * @param typeOfMedia
     */
    public void setTypeOfMedia(TypeOfMedia typeOfMedia) {
        int typeOfMediaId;
        switch(typeOfMedia) {
            case BOOK:
                typeOfMediaId = R.string.icon_book;
            case MAGAZINE:
                typeOfMediaId = R.string.icon_magazine;
                break;
            case JOURNAL:
                typeOfMediaId = R.string.icon_journal;
                break;
            case WEBSITE:
                typeOfMediaId = R.string.icon_website;
                break;
            default:
                typeOfMediaId = R.string.icon_other;
                break;
        }
        setText(getContext().getString(typeOfMediaId));
    }

}
