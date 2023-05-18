package org.example.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface BaseRepository<T> {
    int save(T t);

    T getById(UUID id);

    List<T> getAll() throws SQLException;

    T update(T t);

    void remove(T t);

    void remove(UUID id);
}
