import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by fussa on 2016-08-18.
 */
public class Main {
    public static void main ( String[] args ) throws IOException {
        PrintWriter pw = new PrintWriter("C:/Users/fussa/Documents/Project/SourGrape/!Document/개발자료/Code/txt/steam_img_url.txt");
        BufferedReader br = new BufferedReader(new FileReader("C:/Users/fussa/Documents/Project/SourGrape/!Document/개발자료/Code/txt/title.txt"));

        while ( true ) {
            String title = br.readLine();
            if ( title == null ) break;


            String url = "http://store.steampowered.com/search/?snr=1_7_7_151_12&term=";
            url = url + title;
            System.out.print ( title + " " );

            Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(10000).get();

            // 해당 타이틀의 검색 결과가 없으면 다음으로 진행한다.
            Elements elements = doc.select("div#search_result_container > p");
            String emptyMsg = "No results were returned for that query.";
            boolean isEmpty = false;
            for ( int i = 0; i < elements.size(); i++ ) {
                if ( elements.get(i).text().equals(emptyMsg) ) {
                    isEmpty = true;
                    System.out.println ( "검색 결과 없음" );
                    pw.println( title + ", " );
                    break;
                }
            }
            if ( isEmpty ) continue;

            else {
                String selectedTitle = doc.select("span.title").first().text();

                //엉뚱한 게임의 썸네일을 가져오는 것을 방지
                if ( !title.equals(selectedTitle) ) {
                    System.out.println ( "검색 결과 없음" );
                    pw.println( title + ", " );
                    continue;
                }

                else {
                    String selectedThumbUrl = doc.select("div.col.search_capsule > img").attr("src");

                    String[] array = selectedThumbUrl.split("_sm_120.jpg");
                    selectedThumbUrl = array[0] + "_616x353.jpg";

                    System.out.println( selectedThumbUrl );
                    pw.println( title + ", " + selectedThumbUrl );
                }
            }
            // 타이틀 하나가 끝나면 1초 휴식
            try{
                Thread.sleep(1000);
            } catch ( InterruptedException e ) {
                System.out.print( e.getStackTrace() );
            }
        }

        pw.close();
        br.close();
    }
}
