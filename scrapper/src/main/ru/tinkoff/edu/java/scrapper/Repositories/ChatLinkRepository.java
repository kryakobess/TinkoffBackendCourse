package scrapper.Repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class ChatLinkRepository {
    public final Map<Integer, Set<String>> linksMap = new HashMap<>();;
}
