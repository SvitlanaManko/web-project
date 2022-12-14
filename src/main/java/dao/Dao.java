package dao;

import java.util.List;

public interface Dao<E, I> {
    void create(E entity);

    E read(I id);

    List<E> readAll();

    void update(E entity);

    void delete(I id);

}
