package edu.uci.ics.crawler4j.examples.mycrawler;

/**
 * Created by warren on 2/16/17.
 */
import com.opencsv.*;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.FileWriter;
import java.util.regex.Pattern;


public class MyCrawler extends WebCrawler {

    private static final Pattern DISALLOW_EXTENSIONS = Pattern.compile(".*\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v" +
            "|rm|smil|wmv|swf|wma|zip|rar|gz)$");

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {

        System.out.println("attemptToFetch: url--> " + webUrl.getURL()+ " , attemptToFetch: code--> " + statusCode);
        try{
            CSVWriter writer = new CSVWriter(new FileWriter("fetch_NBCNews.csv", true));
            String[] data = {webUrl.getURL(), ""+statusCode};
            writer.writeNext(data);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        boolean flag = true;
        // Ignore the url if it has an extension that matches our defined set of image extensions.
        if (DISALLOW_EXTENSIONS.matcher(href).matches()) {
            flag = false;
        }
        flag = (flag && href.startsWith("http://www.nbcnews.com/"));

//        System.out.println("allLinks: url--> " + url.getURL() + " , allLinks: code--> " + (href.startsWith("http://www.nbcnews.com/")?"OK":"NO_OK"));
        // Only accept the url if it is in the "www.nbcnews.com" domain and protocol is "http".
        try{
            CSVWriter writer = new CSVWriter(new FileWriter("urls_NBCNews.csv", true));
            String[] data = {url.getURL(), (href.startsWith("http://www.nbcnews.com/")?"OK":"N_OK")};
            writer.writeNext(data);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
////        System.out.println("visitUrl: url--> " + page.getWebURL().getURL() +
//                            " , size--> " + page.getContentData().length + " bytes" +
//                            " , outLinks--> " + page.getParseData().getOutgoingUrls().size() +
//                            " , content-type--> " + page.getContentType());
        try{
            CSVWriter writer = new CSVWriter(new FileWriter("visit_NBCNews.csv", true));
            String[] data = {page.getWebURL().getURL(), ""+page.getContentData().length,
                            ""+page.getParseData().getOutgoingUrls().size(), page.getContentType()};
            writer.writeNext(data);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

