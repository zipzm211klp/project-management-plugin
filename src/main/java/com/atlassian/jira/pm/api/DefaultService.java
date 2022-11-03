package com.atlassian.jira.pm.api;

import java.util.List;

public interface DefaultService<T, K> {
    T create(T dto);

    T update(K id, T dto);

    void delete(K id);

    List<T> getAll();

    T get(K id);
}
