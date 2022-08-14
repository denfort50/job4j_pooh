package pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    public static final String GET = "GET";
    public static final String POST = "POST";
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp response = new Resp("", "404");
        if ("queue".equals(req.getPoohMode())) {
            if (POST.equals(req.httpRequestType())) {
                queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
                queue.get(req.getSourceName()).add(req.getParam());
                response = new Resp("Information posted", "200");
            }
            if (GET.equals(req.httpRequestType())) {
                String text = queue.get(req.getSourceName()).poll();
                if (text != null) {
                    response = new Resp(text, "200");
                }
            }
        }
        return response;
    }
}
