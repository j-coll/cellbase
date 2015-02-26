package org.opencb.cellbase.core.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

/**
 * Created by parce on 18/02/15.
 */
public abstract class CellBaseLoader implements Callable<Integer> {

    protected final BlockingQueue<List<String>> queue;
    protected final Logger logger;
    public String data;
    private Map<String, String> params;

    public CellBaseLoader (BlockingQueue<List<String>> queue, String data, Map<String, String> params) {
        this.queue = queue;
        this.data = data;
        this.params = params;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    public abstract void init();

    public abstract void disconnect();

    @Override
    public abstract Integer call();
}
