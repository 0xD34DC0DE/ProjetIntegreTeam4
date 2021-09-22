package com.team4.backend.service;

import com.team4.backend.repository.MonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorService {

    @Autowired
    private MonitorRepository monitorRepository;

}
