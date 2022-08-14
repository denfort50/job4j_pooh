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
        Resp response = new Resp("", "501");
        if (POST.equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topic = topics.get(req.getSourceName());
            if (topic != null) {
                topic.forEach((user, queue) -> queue.add(req.getParam()));
                response = new Resp(req.getParam(), "200");
            }
        } else if (GET.equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String text = topics.get(req.getSourceName()).get(req.getParam()).poll();
            if (text != null) {
                response = new Resp(text, "200");
            } else {
                response = new Resp("", "204");
            }
        }
        return response;
    }
}
