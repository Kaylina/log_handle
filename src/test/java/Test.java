import com.mr.handle.PageView;
import com.mr.handle.PubMethod;

import java.io.*;

/**
 * Created by Kaylina on 2017/5/28.
 */
public class Test {

    /**
     * Created with Kaylina
     * Time: 2017/5/14 21:34
     * Description: 按行读文件并按类别输出
     */
    public static void readFileByLines(String fileName, String outfile) {

        File file = new File(fileName);
        BufferedReader reader = null;
        BufferedWriter writer = null;
        StringBuffer buffer = null;
        String line = null;
        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            writer = new BufferedWriter(new FileWriter(outfile, true));
            while ((line = reader.readLine()) != null) {
                try {
                    PageView pageView = PageView.analyzeLog(line);
                    if (!PubMethod.isEmpty(pageView)) {
                        System.out.println(pageView.toString());
                        writer.write(pageView.toString());
                        writer.newLine();
                        writer.flush();
                    }
                    //Map<String, Object> etlMap = MapHandle.analyzeLog(line);
                    /*for (Map.Entry<String, Object> entry : etlMap.entrySet()) {
                        writer.write(entry.getKey() + ":" + entry.getValue() + "\t");
                    }
                    writer.newLine();//换行
                    writer.flush();*/


                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {
                    e1.printStackTrace();

                }
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();

                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        readFileByLines("resources/pageview.log", "resources/c.txt");

    }

}
