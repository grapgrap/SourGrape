import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;

/**
 * Created by fussa on 2016-08-17.
 */
public class Main {
    public static void main ( String[] args ) throws IOException {

        PrintWriter pw = new PrintWriter("C:/Users/fussa/Documents/Project/SourGrape/!Document/개발자료/Code/txt/title.txt");

        // 베이스 URL 설정
        String url = "http://www.metacritic.com/browse/games/title/pc";

        // # ~ Z 까지 27개
        char alphabet = 96;
        for ( int i = 0 ; i < 27; i++, alphabet++ ) { // 이 포문은 알파벳 페이지를 옮겨다니는 포문이다
            String tempAlpha = url;
            if ( alphabet > 122 ) break;
            // 알파벳 페이지 URL 설정
            if ( alphabet == 96) ;
            else url = url + "/" + alphabet;

            // 해당 페이지의 타겟 설정
            Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10000).get();
            String lastPageNumStr;

            // 숫자 페이지가 1페이지만 있는 경우에는 1페이지만 다닐 수 있도록 함
            if ( doc.select("li").hasClass("page") )
                lastPageNumStr = doc.select(".page.last_page > a").text();
            else
                lastPageNumStr = "1";

            int lastPageNum = Integer.parseInt( lastPageNumStr );

            for ( int j = 0; j < lastPageNum; j++ ) { // 이 포문은 알파벳 페이지의 숫자 페이지를 옮겨다니는 포문이다.
                String tempNum = url;

                // 숫자 페이지 URL 설정
                if ( j == 0 ) System.out.println(url);
                else {
                    url = url + "?page=" + j;
                    System.out.println(url);
                }

                // 해당 페이지의 타겟 설정
                doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10000).get();
                Elements elements = doc.select(".basic_stat.product_title > a");

                for ( int k = 0; k < elements.size(); k++ ) {
                    String title = elements.get(k).text();
                    pw.println(title);
                }// end of k for
                url = tempNum;

                // 한 페이지의 크롤링 완료시 1초 동안 휴식
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getStackTrace());
                }

            }
            url = tempAlpha;
        }

        pw.close();
    }
}
