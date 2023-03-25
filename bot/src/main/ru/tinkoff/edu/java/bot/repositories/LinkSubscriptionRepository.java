package bot.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LinkSubscriptionRepository {
    public final Map<Long, List<String>> subscriptions = new HashMap<>();
}
