package com.pjwstk.prm.rssreader.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class ChannelJava {
    @Element
    private String title;

    @ElementList(inline=true, required=false)
    public List<FeedJava> items;

    @Override
    public String toString() {
        return "ChannelJava{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
