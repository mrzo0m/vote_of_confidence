package io.voteofconf.tracker.repository;

import io.voteofconf.tracker.model.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
@Slf4j
public class QueryCachingSupport {

    private SupportAGRepository supportAGRepository;

    private Map<String, String> queryMap = new ConcurrentHashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public QueryCachingSupport(SupportAGRepository supportAGRepository) {
        this.supportAGRepository = supportAGRepository;
    }

    public String getQuerySource(String name) {
        try {
            if (queryMap.isEmpty()) {
                initQueryCache();
            }

            readWriteLock.readLock().lockInterruptibly();

            String querySource = queryMap.get(name);

            if (querySource == null)
                throw new RuntimeException(String.format("Query \"%s\" not found!", name));

            return querySource;
        } catch (InterruptedException ex) {
            throw new RuntimeException("Interrupted exception: ", ex);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private void initQueryCache() throws InterruptedException {
        readWriteLock.writeLock().lockInterruptibly();
        Objects.requireNonNull(loadQueries())
                .forEach(query -> queryMap.put(query.getName(), query.getSource()));
        readWriteLock.writeLock().unlock();
    }

    public Query loadQuery(String name) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Query> future = executorService.submit(() -> supportAGRepository
                .getQuery(name)
                .block());
        try {
            return future.get();
        } catch (Exception ex) {
            return null;
        }
    }

    private List<Query> loadQueries() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Query>> future = executorService.submit(
                () -> supportAGRepository
                        .getQueries()
                        .collectList()
                        .block());
        try {
            return future.get();
        } catch (Exception ex) {
            return null;
        }
    }
}
