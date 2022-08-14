package pooh;

public class Req {
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String xHttpRequestType = "POST";
        if (content.startsWith("GET")) {
            xHttpRequestType = "GET";
        }
        String xPoohMode = "queue";
        if (content.contains("topic")) {
            xPoohMode = "topic";
        }
        String xSourceName = content.split("/")[2].split(" ")[0];
        String[] parts = content.split(System.lineSeparator());
        String xParam = "";
        if (content.contains("=")) {
            xParam = parts[parts.length - 1];
        } else if (parts[0].split("/")[3].contains(" ")) {
            xParam = parts[0].split("/")[3].split(" ")[0];
        }
        return new Req(xHttpRequestType, xPoohMode, xSourceName, xParam);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
