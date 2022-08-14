package pooh;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TopicServiceTest {

    @Test
    public void whenTopicWith2Subscribers() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text()).isEqualTo("temperature=18");
        assertThat(result2.text()).isEqualTo("");
    }

    @Test
    public void whenTopicWith6Subscribers() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=25";
        String paramForSubscriber1 = "client1";
        String paramForSubscriber2 = "client2";
        String paramForSubscriber3 = "client3";
        String paramForSubscriber4 = "client4";
        String paramForSubscriber5 = "client5";
        String paramForSubscriber6 = "client6";
        topicService.process(new Req("GET", "topic", "weather", paramForSubscriber1));
        topicService.process(new Req("GET", "topic", "weather", paramForSubscriber2));
        topicService.process(new Req("GET", "topic", "weather", paramForSubscriber3));
        topicService.process(new Req("GET", "topic", "weather", paramForSubscriber4));
        topicService.process(new Req("GET", "topic", "weather", paramForSubscriber5));
        topicService.process(new Req("POST", "topic", "weather", paramForPublisher));
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Resp result3 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber3)
        );
        Resp result4 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber4)
        );
        Resp result5 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber5)
        );
        Resp result6 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber6)
        );
        assertThat(result1.text()).isEqualTo("temperature=25");
        assertThat(result2.text()).isEqualTo("temperature=25");
        assertThat(result3.text()).isEqualTo("temperature=25");
        assertThat(result4.text()).isEqualTo("temperature=25");
        assertThat(result5.text()).isEqualTo("temperature=25");
        assertThat(result6.text()).isEqualTo("");
    }
}