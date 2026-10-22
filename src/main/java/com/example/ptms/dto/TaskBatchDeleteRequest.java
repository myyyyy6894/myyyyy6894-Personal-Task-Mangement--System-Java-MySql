package com.example.ptms.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class TaskBatchDeleteRequest {
    @NotEmpty
    List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
