package pl.softcredit.crawler;

import org.junit.Test;


public class WebsphinxCrawlerTest {

    @Test
    public void runCrawling() {
        try {

            long startTime = System.currentTimeMillis();

            WebsphinxCrawler crawler =
                    WebsphinxCrawler.crawler().withUrl("http://www.national-geographic.pl/");
            crawler.run();

            long stopTime = System.currentTimeMillis();
            System.out.println("Crawling time:" + ((stopTime - startTime) / 1000));

            crawler.getLinks().forEach(System.out::println);
            System.out.println("Links:" + crawler.getLinks().size());

        } catch (Exception e) {
            System.out.println("Cannot crawl site: " + e.getMessage());
        }
    }


}
