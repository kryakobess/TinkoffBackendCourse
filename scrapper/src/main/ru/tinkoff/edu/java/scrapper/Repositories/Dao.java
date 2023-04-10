package scrapper.Repositories;

import java.util.List;

public interface Dao<T> {
    List<T> getAll();
    void add(T t);
    void remove(Long id);
}
