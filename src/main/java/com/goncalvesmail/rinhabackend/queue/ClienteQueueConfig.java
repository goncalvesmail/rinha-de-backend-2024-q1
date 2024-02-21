package com.goncalvesmail.rinhabackend.queue;

import com.goncalvesmail.rinhabackend.model.Cliente;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class ClienteQueueConfig {
    @Bean
    public BlockingQueue<Cliente> clienteQueue(){
        return new LinkedBlockingQueue<>();
    }

    @Bean
    public ClienteQueueConsume clienteQueueConsume() { return new ClienteQueueConsume(clienteQueue());}
}
