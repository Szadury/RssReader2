package com.pjwstk.prm.rssreader.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name="item", strict = false)
public class FeedJava {
    @Element
    public String title;

    @Element
    public String link;

    @Element
    public String description;

    @Path("enclosure")
    @Attribute(name = "url", required = false)
    public String enclosureUrl = "";

    @Path("enclosure")
    @Attribute(name = "type", required = false)
    public String enclosureType = "";

    @Override
    public String toString() {
        return "FeedJava{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", enclosureUrl='" + enclosureUrl + '\'' +
                ", enclosureType='" + enclosureType + '\'' +
                '}';
    }
}
