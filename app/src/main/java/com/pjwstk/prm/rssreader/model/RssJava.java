package com.pjwstk.prm.rssreader.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="rss", strict=false)
public class RssJava {
    @Element
    public ChannelJava channel;

    @Override
    public String toString() {
        return "RssJava{" +
                "channel=" + channel +
                '}';
    }
}
