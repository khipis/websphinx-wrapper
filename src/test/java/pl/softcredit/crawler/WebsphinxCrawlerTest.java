package pl.softcredit.crawler;

import org.junit.Test;


public class WebsphinxCrawlerTest {

    @Test
    public void runCrawling() {
        try {

            WebsphinxCrawler crawler =
                    new WebsphinxCrawler("http://www.national-geographic.pl/", 1);
            for (String url : crawler.getUrls()) {
                System.out.println(url);
            }
            System.out.println("Links:" + crawler.getUrls().size());
        } catch (Exception e) {
            System.out.println("Cannot crawl site: " + e.getMessage());
        }
    }


}
