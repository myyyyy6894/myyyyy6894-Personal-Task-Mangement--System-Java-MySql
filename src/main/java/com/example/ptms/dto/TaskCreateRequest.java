package com.example.ptms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskCreateRequest {
    @NotBlank(message = "任务标题不能为空")
    @Size(max = 100,message = "标题最长100个字")
    private String title;

    @Size(max = 500,message = "内容最长500个字")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
