package edu.regis.msse655.annotatedbibliography.model;

/**
 * A domain object representing an entry in a bibliography - a reference or citation.
 */
public class Reference {

    private int id;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return referenceTitle + "\n" + authors + ", " + mediaTitle;
    }

}
