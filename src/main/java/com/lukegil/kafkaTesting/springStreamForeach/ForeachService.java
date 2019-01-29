package com.lukegil.kafkaTesting.springStreamForeach;

import org.springframework.stereotype.Service;

@Service
public class ForeachService {
    public void process(final String value) {
        final String v = value;
        /* do something */
    }
}
