package wmp.uksw.pl.pracalicencjacka_prototyp2.models;

/**
 * Created by MSI on 2016-01-14.
 */
public class EventRow {

    private String imageUrl;
    public String name;
    public String description;

    public EventRow (String imageUrl, String name, String description) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
    }
}
