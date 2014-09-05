package br.com.procergs.rss;

import java.util.List;

public interface FeedParser {
    List<Message> parse();
}