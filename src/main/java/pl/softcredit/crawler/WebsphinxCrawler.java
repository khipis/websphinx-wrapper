package pl.softcredit.crawler;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import websphinx.Crawler;
import websphinx.DownloadParameters;
import websphinx.Link;
import websphinx.Page;

public class WebsphinxCrawler extends Crawler {

    public static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    private static final int CRAWL_TIMEOUT = 240;
    private static final int DOWNLOAD_TIMEOUT = 240;
    private static final int MAX_PAGE_SIZE = 4096;
    private static final boolean OBEY_ROBOT_EXCLUSION = false;
    private static final boolean USE_CACHES = true;
    private static final String[] DOMAIN = Crawler.SERVER;
    private static final String[] LINK_TYPES = Crawler.HYPERLINKS;
    private static final int LOG_MODULO = 50;
    private static int MAX_DEPTH = 2;
    private static int MAX_THREADS = 6;

    private Set<String> urls = new HashSet<String>();

    WebsphinxCrawler(String url, int depth) throws MalformedURLException {
        super();

        setRoots(new Link[]{new Link(new URL(url))});

        DownloadParameters dp = new DownloadParameters();
        dp = dp.changeObeyRobotExclusion(OBEY_ROBOT_EXCLUSION);
        dp = dp.changeUserAgent(USER_AGENT);
        dp = dp.changeMaxPageSize(MAX_PAGE_SIZE);
        dp = dp.changeDownloadTimeout(DOWNLOAD_TIMEOUT);
        dp = dp.changeCrawlTimeout(CRAWL_TIMEOUT);
        dp = dp.changeUseCaches(USE_CACHES);
        dp = dp.changeMaxThreads(MAX_THREADS);

        setDownloadParameters(dp);
        // setDomain(domain);

        setLinkType(LINK_TYPES);
        setMaxDepth(depth);

        long startTime = System.currentTimeMillis();
        run();
        long stopTime = System.currentTimeMillis();
        System.out.println("Crawling time:" + ((stopTime - startTime) / 1000));

        urls.addAll(getUrls());

    }

    public String domain = "";

    @Override
    public boolean shouldVisit(Link link) {
        String host = link.getHost();

        return host.contains(domain) && (host.contains(domain));

    }


    @Override
    public void visit(Page page) {

        urls.add(page.getURL().toString());

        // Print out some stats about the crawler every 10 pages visited
        int n = this.getPagesVisited();
        if (n % LOG_MODULO == 0) {
            System.out.println(String.valueOf(this.getPagesVisited()) + " pages visited.  " + this
                    .getPagesLeft() + " pages left.  " + this.getActiveThreads()
                               + " active threads.");
        }

        page.discardContent();
    }

    public Set<String> getUrls() {
        return urls;
    }
}
