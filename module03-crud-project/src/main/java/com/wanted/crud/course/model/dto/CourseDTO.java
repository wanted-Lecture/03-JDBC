package com.wanted.crud.course.model.dto;

public class CourseDTO {

    private Long curse_id;
    private Long author_id;
    private String title;
    private String description;
    private String status;

    public CourseDTO() {
    }

    public CourseDTO(Long curse_id, Long author_id, String title, String description, String status) {
        this.curse_id = curse_id;
        this.author_id = author_id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "curse_id=" + curse_id +
                ", author_id=" + author_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Long getCurse_id() {
        return curse_id;
    }

    public void setCurse_id(Long curse_id) {
        this.curse_id = curse_id;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
