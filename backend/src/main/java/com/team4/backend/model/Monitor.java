package com.team4.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("monitors")
@EqualsAndHashCode(callSuper = true)
public class Monitor extends User implements Serializable {
}
