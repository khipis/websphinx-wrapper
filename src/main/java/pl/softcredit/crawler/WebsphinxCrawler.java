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

    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0";

    private static final int CRAWL_TIMEOUT = 240;
    private static final int DOWNLOAD_TIMEOUT = 240;
    private static final int MAX_PAGE_SIZE = 4096;
    private static final boolean OBEY_ROBOT_EXCLUSION = false;
    private static final boolean USE_CACHES = true;
    private static final String[] DOMAIN = Crawler.SERVER;
    private static final String[] LINK_TYPES = Crawler.HYPERLINKS;
    private static final int LOG_MODULO = 50;
    private static int MAX_DEPTH = 1;
    private static int MAX_THREADS = 6;

    private Set<String> links = new HashSet<String>();
    private static DownloadParameters downloadParameters = new DownloadParameters();


    private static WebsphinxCrawler websphinxCrawler = new WebsphinxCrawler();

    private WebsphinxCrawler() {
        super();

        downloadParameters = downloadParameters.changeCrawlTimeout(CRAWL_TIMEOUT);
        downloadParameters = downloadParameters.changeObeyRobotExclusion(OBEY_ROBOT_EXCLUSION);
        downloadParameters = downloadParameters.changeUserAgent(USER_AGENT);
        downloadParameters = downloadParameters.changeMaxPageSize(MAX_PAGE_SIZE);
        downloadParameters = downloadParameters.changeDownloadTimeout(DOWNLOAD_TIMEOUT);
        downloadParameters = downloadParameters.changeUseCaches(USE_CACHES);
        downloadParameters = downloadParameters.changeMaxThreads(MAX_THREADS);

        websphinxCrawler.setMaxDepth(MAX_DEPTH);

        setDownloadParameters(downloadParameters);
        links.addAll(getLinks());
    }

    public static WebsphinxCrawler crawler() {
        return websphinxCrawler;
    }

    public static WebsphinxCrawler withCrawlTimeout(int crawlTimeout) {
        downloadParameters = downloadParameters.changeCrawlTimeout(crawlTimeout);
        return websphinxCrawler;
    }

    public static WebsphinxCrawler withObeyRobotExclusion(boolean obeyRobotExclusion) {
        downloadParameters = downloadParameters.changeObeyRobotExclusion(obeyRobotExclusion);
        return websphinxCrawler;
    }

    public static WebsphinxCrawler withUserAgent(String userAgent) {
        downloadParameters = downloadParameters.changeUserAgent(userAgent);
        return websphinxCrawler;
    }

    public static WebsphinxCrawler withMaxPageSize(int maxPageSize) {
        downloadParameters = downloadParameters.changeMaxPageSize(maxPageSize);
        return websphinxCrawler;
    }

    public static WebsphinxCrawler withDownloadTimeout(int downloadTimeout) {
        downloadParameters = downloadParameters.changeDownloadTimeout(downloadTimeout);
        return websphinxCrawler;
    }


    public static WebsphinxCrawler withUseCaches(boolean useCaches) {
        downloadParameters = downloadParameters.changeUseCaches(useCaches);
        return websphinxCrawler;
    }


    public static WebsphinxCrawler withMaxThreads(int maxThreads) {
        downloadParameters = downloadParameters.changeMaxThreads(maxThreads);
        return websphinxCrawler;
    }


    public static WebsphinxCrawler withLinkTypes(String[] linkTypes) {
        websphinxCrawler.setLinkType(linkTypes);
        return websphinxCrawler;
    }

    public static WebsphinxCrawler withUrl(String url) throws MalformedURLException {
        websphinxCrawler.setRoots(new Link[]{new Link(new URL(url))});
        return websphinxCrawler;
    }

    public static WebsphinxCrawler withDepth(int depth) {
        websphinxCrawler.setMaxDepth(depth);
        return websphinxCrawler;
    }


    public WebsphinxCrawler build() {
        setDownloadParameters(downloadParameters);
        return this;
    }

    @Override
    public void visit(Page page) {

        links.add(page.getURL().toString());
        int n = this.getPagesVisited();
        if (n % LOG_MODULO == 0) {
            System.out.println(String.valueOf(this.getPagesVisited()) + " pages visited.  " + this
                    .getPagesLeft() + " pages left.  " + this.getActiveThreads()
                               + " active threads.");
        }

        page.discardContent();
    }

    public Set<String> getLinks() {
        return links;
    }
}
