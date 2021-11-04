package com.example.cheography.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cdata {
    private String name;
    private String type;
    private String action;
}
