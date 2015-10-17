package edu.regis.msse655.annotatedbibliography.model;

import java.io.Serializable;

/**
 * A domain object representing an entry in a bibliography - a reference or citation.
 */
public class Reference implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id = -1;
    private String mediaTitle;
    private String referenceTitle;
    private String details;
    private String keywords;
    private String referenceAbstract;
    private String notes;
    private String url;
    private String doi;
    private TypeOfMedia typeOfMedia;
    private String authors; // TOOD: consider using a list of Author objects.
    private boolean favorite;
    private long dateModified = Long.MIN_VALUE;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getReferenceTitle() {
        return referenceTitle;
    }

    public void setReferenceTitle(String referenceTitle) {
        this.referenceTitle = referenceTitle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getReferenceAbstract() {
        return referenceAbstract;
    }

    public void setReferenceAbstract(String referenceAbstract) {
        this.referenceAbstract = referenceAbstract;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public TypeOfMedia getTypeOfMedia() {
        return typeOfMedia;
    }

    public void setTypeOfMedia(TypeOfMedia typeOfMedia) {
        this.typeOfMedia = typeOfMedia;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public String toString() {
        return referenceTitle + "\n" + authors + ", " + mediaTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        if (id != reference.id) return false;
        if (favorite != reference.favorite) return false;
        if (dateModified != reference.dateModified) return false;
        if (mediaTitle != null ? !mediaTitle.equals(reference.mediaTitle) : reference.mediaTitle != null)
            return false;
        if (referenceTitle != null ? !referenceTitle.equals(reference.referenceTitle) : reference.referenceTitle != null)
            return false;
        if (details != null ? !details.equals(reference.details) : reference.details != null)
            return false;
        if (keywords != null ? !keywords.equals(reference.keywords) : reference.keywords != null)
            return false;
        if (referenceAbstract != null ? !referenceAbstract.equals(reference.referenceAbstract) : reference.referenceAbstract != null)
            return false;
        if (notes != null ? !notes.equals(reference.notes) : reference.notes != null) return false;
        if (url != null ? !url.equals(reference.url) : reference.url != null) return false;
        if (doi != null ? !doi.equals(reference.doi) : reference.doi != null) return false;
        if (typeOfMedia != reference.typeOfMedia) return false;
        return !(authors != null ? !authors.equals(reference.authors) : reference.authors != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (mediaTitle != null ? mediaTitle.hashCode() : 0);
        result = 31 * result + (referenceTitle != null ? referenceTitle.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (keywords != null ? keywords.hashCode() : 0);
        result = 31 * result + (referenceAbstract != null ? referenceAbstract.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (doi != null ? doi.hashCode() : 0);
        result = 31 * result + (typeOfMedia != null ? typeOfMedia.hashCode() : 0);
        result = 31 * result + (authors != null ? authors.hashCode() : 0);
        result = 31 * result + (favorite ? 1 : 0);
        result = 31 * result + (int) (dateModified ^ (dateModified >>> 32));
        return result;
    }
}
