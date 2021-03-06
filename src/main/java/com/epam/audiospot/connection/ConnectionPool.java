package com.epam.audiospot.connection;

import com.epam.audiospot.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final int INITIAL_POOL_SIZE = 10;
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    private static final Lock instanceLock = new ReentrantLock();
    private static final Lock getConnectionLock = new ReentrantLock();
    private static final Lock releaseConnectionLock = new ReentrantLock();
    private static ConnectionPool instance = null;

    private final Semaphore semaphore = new Semaphore(INITIAL_POOL_SIZE);
    private Queue <ConnectionWrapper> pool;

    public static ConnectionPool getInstance() {
        if (!initialized.get()) {
            try {
                instanceLock.lock();
                if (!initialized.get()) {
                    instance = new ConnectionPool();
                    initConnectionPool(instance);
                    initialized.set(true);
                }
            } finally {
                instanceLock.unlock();
            }
        }
        return instance;
    }

    private static void initConnectionPool(ConnectionPool connectionPool) {
        Queue <ConnectionWrapper> connections = new LinkedList <>();
        ConnectionCreator creator = new ConnectionCreator();
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            ConnectionWrapper connection = creator.createConnection();
            connections.add(connection);
        }
        connectionPool.setPool(connections);
    }

    public ConnectionWrapper getConnection() {
        try {
            getConnectionLock.lock();
            semaphore.acquire();
            return pool.poll();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionPoolException(e.getMessage(), e);
        } finally {
            getConnectionLock.unlock();
        }
    }

    public void releaseConnection(ConnectionWrapper connection) {
        try {
            releaseConnectionLock.lock();
            pool.add(connection);
            semaphore.release();
        } finally {
            releaseConnectionLock.unlock();
        }
    }

    public void closeConnections() {
        for (ConnectionWrapper connection : pool) {
            connection.close();
        }
    }

    private void setPool(Queue <ConnectionWrapper> pool) {
        this.pool = pool;
    }
}
