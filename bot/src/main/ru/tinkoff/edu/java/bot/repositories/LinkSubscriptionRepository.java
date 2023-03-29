package bot.repositories;

import org.springframework.stereotype.Repository;

import javax.management.InvalidAttributeValueException;
import javax.management.openmbean.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LinkSubscriptionRepository {
    private final Map<Long, List<String>> subscriptions = new HashMap<>();

    public boolean existsUserWithId(Long id){
        return subscriptions.containsKey(id);
    }

    public void createNewUserWithId(Long id){
        subscriptions.put(id, new ArrayList<>());
    }

    public boolean existsUserWithIdAndSubscribedLink(Long id, String subscribedLink){
        return existsUserWithId(id) && subscriptions.get(id).contains(subscribedLink);
    }

    public void addNewLinkToId(Long id, String link){
        if (!existsUserWithId(id)) throw new InvalidKeyException("This id is not initialized!");
        else {
            subscriptions.get(id).add(link);
        }
    }

    public void removeLinkForId(Long id, String link){
        if (!existsUserWithId(id)) throw new InvalidKeyException("This id is not initialized!");
        else if (!existsUserWithIdAndSubscribedLink(id, link)) throw new InvalidKeyException("There is no " + link + " in repository");
        else {
            subscriptions.get(id).remove(link);
        }
    }

    public boolean isEmptyForId(Long id){
        if (!existsUserWithId(id)) throw new InvalidKeyException("This id is not initialized!");
        else {
            return subscriptions.get(id).isEmpty();
        }
    }

    public List<String> getAllLinksForId(Long id){
        if (!existsUserWithId(id)) throw new InvalidKeyException("This id is not initialized!");
        else {
            return subscriptions.get(id);
        }
    }
}
