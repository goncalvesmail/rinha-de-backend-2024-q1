package com.goncalvesmail.rinhabackend.queue;

import com.goncalvesmail.rinhabackend.model.Cliente;
import com.goncalvesmail.rinhabackend.model.Transacao;
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
        Thread.ofVirtual().unstarted(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if(!clienteQueue.isEmpty()) {
                        Thread.ofVirtual().unstarted(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Cliente cliente = clienteQueue.take();
                                    logger.info(cliente.getNome());
                                } catch (InterruptedException e) {
                                    logger.error(e.getMessage());
                                    //throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                }
            }
        });

    }
}
