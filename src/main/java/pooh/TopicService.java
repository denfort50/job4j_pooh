package pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    public static final String GET = "GET";
    public static final String POST = "POST";
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp response = new Resp("", "404");
        if ("topic".equals(req.getPoohMode())) {
            if (POST.equals(req.httpRequestType())) {
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topic = topics.get(req.getSourceName());
                if (topic != null) {
                    topic.forEach((user, queue) -> queue.add(req.getParam()));
                }
                response = new Resp("Information posted", "200");
            }
            if (GET.equals(req.httpRequestType())) {
                topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
                topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                String text = topics.get(req.getSourceName()).get(req.getParam()).poll();
                if (text != null) {
                    response = new Resp(text, "200");
                }
            }
        }
        return response;
    }
}
