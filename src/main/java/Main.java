import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.dianping.swallow.Destination;
import com.dianping.swallow.MQService;
import com.dianping.swallow.MessageProducer;
import com.dianping.swallow.impl.MongoMQService;

public class Main {

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

        System.out.println("输入要发送的消息 (type 'quit' to exit): ");
        converter = new InputStreamReader(System.in);
        in = new BufferedReader(converter);

        while (!(curLine.equals("quit"))) {
            curLine = in.readLine().trim();
            if (!(curLine.equals("quit"))) {
                System.out.println("您发送的是: " + curLine);
                p.send(p.createStringMessage(curLine));
            } else {
                System.exit(0);
            }
        }
    }

}
