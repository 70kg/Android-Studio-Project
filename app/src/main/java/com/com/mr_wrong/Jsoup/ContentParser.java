package com.com.mr_wrong.Jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Mr_Wrong on 15/9/14.
 * 解析具体大图页面
 */
public class ContentParser {

    public static Content Parser(String html) {
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
        Content content = new Content();
        Element element = links.get(1).getElementsByTag("img").first();
        content.setUrl(element.attr("src"));
        content.setTitle(element.attr("alt"));
        return content;
    }
}
