package com.team4.backend.service;

import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Log
@Order(3)
@Service
@EnableScheduling
public class TaskSchedulerService {
}
