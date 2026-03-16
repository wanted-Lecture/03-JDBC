package com.wanted.crud.course.model.dto;

public class SectionDTO {

    private Long sectionId;
    private Long courseId;
    private String title;
    private int sectionOrder;

    @Override
    public String toString() {
        return "SectionDTO{" +
                "sectionId=" + sectionId +
                ", courseId=" + courseId +
                ", title='" + title + '\'' +
                ", sectionOrder=" + sectionOrder +
                '}';
    }

    public int getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(int sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public SectionDTO(Long sectionId, Long courseId, String title, int sectionOrder) {
        this.sectionId = sectionId;
        this.courseId = courseId;
        this.title = title;
        this.sectionOrder = sectionOrder;
    }

    public SectionDTO() {
    }
}
