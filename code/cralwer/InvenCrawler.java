import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by fussa on 2016-08-18.
 */
public class InvenCrawler {
    public static void main (String[] args) throws IOException {
        PrintWriter pw = new PrintWriter("C:/Users/fussa/Documents/Project/SourGrape/!Document/개발자료/Code/txt/inven_title.txt");

        String url = "http://www.inven.co.kr/webzine/game/";

        // 1은 온라인게임, 2는 패키지게임
        for ( int i = 1; i < 3; i++ ) {
            String platformTemp = url;
            url = url + "?platform=" + i;

            Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10000).get();
            int lastPageNum = Integer.parseInt( doc.select("a.pg").last().text() );

            // 페이지를 옮겨다니는 포문
            for ( int j = 1; j <= lastPageNum; j++ ) {
                String pageTemp = url;
                url = url + "&page=" + j;

                doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10000).get();
                Elements elements = doc.select(".left.iconimg.name");

                for ( int k = 0; k < elements.size(); k++ ) {
                    pw.println( elements.get(k).text() );
                }

                System.out.println("Success : " + url );
                url = pageTemp;

                // 한 페이지 종료 후 1초 휴식
                try{
                    Thread.sleep(1000);
                } catch ( InterruptedException e ) {
                    System.out.print( e.getStackTrace() );
                }
            }

            url = platformTemp;
        }// end of i for

        pw.close();
    }
}
