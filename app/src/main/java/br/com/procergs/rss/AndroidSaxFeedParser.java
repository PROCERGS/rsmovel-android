package br.com.procergs.rss;

import java.util.List;

import android.util.Xml;

public class AndroidSaxFeedParser extends BaseFeedParser {

	public AndroidSaxFeedParser(String feedUrl) {
		super(feedUrl);
	}

	public List<Message> parse() {
		RssHandler handler = new RssHandler();
		try {
			Xml.parse(this.getInputStream(), Xml.Encoding.ISO_8859_1, handler);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return handler.getMessages();
	}

}