package com.wanted.crud.course.model.dto;

import java.util.ArrayList;
import java.util.List;

public class CourseSectionDTO {

    private Long course_id;
    private Long author_id;
    private String title;
    private String description;

    public CourseSectionDTO(Long course_id, Long author_id, String title, String description, String status) {
        this.course_id = course_id;
        this.author_id = author_id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    private String status;
    private List<SectionDTO> sections = new ArrayList<>();

    public CourseSectionDTO() {
    }

    public CourseSectionDTO(Long course_id, Long author_id, String title, String description, String status, List<SectionDTO> sections) {
        this.course_id = course_id;
        this.author_id = author_id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.sections = sections;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
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

    public List<SectionDTO> getSections() {
        return sections;
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        return "CourseSectionDTO{" +
                "course_id=" + course_id +
                ", author_id=" + author_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", sections=" + sections +
                '}';
    }
}
