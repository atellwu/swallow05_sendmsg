import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.dianping.swallow.Destination;
import com.dianping.swallow.MQService;
import com.dianping.swallow.MessageProducer;
import com.dianping.swallow.impl.MongoMQService;

public class Main2 {

    public static void main(String[] args) throws Exception {
        MQService mq = new MongoMQService("10.1.6.86:27017");
        //        MQService mq = new MongoMQService("192.168.8.21:27017");
        Destination dest = null;

        String curLine = ""; // Line read from standard in

        System.out.println("输入要发送的Topic (type 'quit' to exit): ");
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        curLine = in.readLine().trim();
        if (!(curLine.equals("quit"))) {
            System.out.println("Topic是: " + curLine);
            dest = Destination.topic(curLine);
        } else {
            System.exit(0);
        }

        MessageProducer p = mq.createProducer(dest);

        converter = new InputStreamReader(System.in);
        in = new BufferedReader(converter);

        while (true) {
            System.out.println("======= 输入要发送的消息所在的文件，一行一条消息 (type 'quit' to exit): ========");
            curLine = in.readLine().trim();
            if (!(curLine.equals("quit"))) {
                System.out.println("您输入的文件是: " + curLine);
                //读取文件内容
                File file = new File(curLine);
                if (!file.exists()) {
                    System.out.println("您输入的文件不存在！");
                } else {
                    List<String> lines = IOUtils.readLines(new FileInputStream(file), "UTF-8");
                    if (lines != null) {
                        for (String line : lines) {
                            System.out.println("正在发送：" + line);
                            try {
                                p.send(p.createStringMessage(line));
                                System.out.println("发送完毕。");
                            } catch (Exception e) {
                                System.out.println("发送失败: " + e.getMessage());
                            }
                        }
                    }
                }

            } else {
                System.exit(0);
            }
        }
    }

}
