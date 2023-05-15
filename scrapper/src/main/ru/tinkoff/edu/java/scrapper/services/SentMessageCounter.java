package scrapper.services;

import io.micrometer.core.instrument.Metrics;

public class SentMessageCounter {
    private static final String COUNTER_NAME = "message.sent.count";

    public static void increment() {
        Metrics.counter(COUNTER_NAME).increment();
    }
}
