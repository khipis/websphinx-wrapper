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
    private DownloadParameters downloadParameters = new DownloadParameters();

    private WebsphinxCrawler(String url, int depth) throws MalformedURLException {
        super();

        setRoots(new Link[]{new Link(new URL(url))});

        downloadParameters = downloadParameters.changeObeyRobotExclusion(OBEY_ROBOT_EXCLUSION);
        downloadParameters = downloadParameters.changeUserAgent(USER_AGENT);
        downloadParameters = downloadParameters.changeMaxPageSize(MAX_PAGE_SIZE);
        downloadParameters = downloadParameters.changeDownloadTimeout(DOWNLOAD_TIMEOUT);
        downloadParameters = downloadParameters.changeCrawlTimeout(CRAWL_TIMEOUT);
        downloadParameters = downloadParameters.changeUseCaches(USE_CACHES);
        downloadParameters = downloadParameters.changeMaxThreads(MAX_THREADS);

        setDownloadParameters(downloadParameters);

        setLinkType(LINK_TYPES);
        setMaxDepth(depth);

        run();

        links.addAll(getLinks());

    }

    public static WebsphinxCrawler instance(String url) throws MalformedURLException {
        return new WebsphinxCrawler(url, MAX_DEPTH);
    }

    //TODO refactor to predicate?
    //    @Override
    //    public boolean shouldVisit(Link link) {
    //        String host = link.getHost();
    //        String domain = "";
    //        return host.contains(domain) && (host.contains(domain));
    //    }


    @Override
    public void visit(Page page) {

        links.add(page.getURL().toString());

        // Print out some stats about the crawler every 10 pages visited
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
