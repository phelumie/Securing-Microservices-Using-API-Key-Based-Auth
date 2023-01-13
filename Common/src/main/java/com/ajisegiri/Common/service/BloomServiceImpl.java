package com.ajisegiri.Common.service;

import com.redis.om.spring.ops.pds.BloomOperations;
import org.springframework.stereotype.Service;

@Service
public class BloomServiceImpl implements BloomService{
    private final BloomOperations bloomOperations;
    private final static String BLOOM_KEY="API_KEY";

    public BloomServiceImpl(BloomOperations bloomOperations) {
        this.bloomOperations = bloomOperations;
    }
    @Override
    public void add(String apikey){
        bloomOperations.add(BLOOM_KEY,apikey);
    }

    @Override
    public boolean checkIfExistsApiKey(String apikey){
        return bloomOperations.exists(BLOOM_KEY, apikey);
    }
}
