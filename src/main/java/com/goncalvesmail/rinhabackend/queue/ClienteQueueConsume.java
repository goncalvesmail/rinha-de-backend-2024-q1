package com.goncalvesmail.rinhabackend.queue;

import com.goncalvesmail.rinhabackend.model.Cliente;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
public class ClienteQueueConsume {
    private final BlockingQueue<Cliente> clienteQueue;
    private static final Logger logger = LoggerFactory.getLogger(ClienteQueueConsume.class);

    public ClienteQueueConsume(BlockingQueue<Cliente> clienteQueue) {
        this.clienteQueue = clienteQueue;
    }

    @Async
    @PostConstruct
    public void execute() {
        logger.info("Consumidor ligado....");
        Thread.ofVirtual().unstarted(() -> {
            while (true) {
                if(!clienteQueue.isEmpty()) {
                    Thread.ofVirtual().unstarted(() -> {
                        try {
                            Cliente cliente = clienteQueue.take();
                            logger.info(cliente.getNome());
                        } catch (InterruptedException e) {
                            logger.error(e.getMessage());
                        }
                    });
                }
            }
        });

    }
}
