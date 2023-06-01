package bot.services;

import io.micrometer.core.instrument.Metrics;

public class ReceivedMessageCounter {

    private final static String COUNTER_NAME = "message.received.count";

    public static void increment() {
        Metrics.counter(COUNTER_NAME).increment();
    }
}
