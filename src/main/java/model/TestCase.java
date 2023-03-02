package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;
import java.util.Objects;


public class TestCase {
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("method")
    private String method;
    @JsonProperty("name")
    private String name;
    @JsonProperty("startTime")
    private String startTime;
    @JsonProperty("endTime")
    private String endTime;
    @JsonProperty("status")
    private String status;

    public TestCase() {
    }

    public TestCase(String duration, String method, String name, String startTime, String endTime, String status) {
        this.duration = duration;
        this.method = method;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestCase)) return false;
        TestCase testCase = (TestCase) o;
        if (getDuration().equals(testCase.getDuration()) && getMethod().equals(testCase.getMethod()) && getName().equals(testCase.getName()) && getStartTime().equals(testCase.getStartTime()) && getStatus().equalsIgnoreCase(testCase.getStatus())) {
            if (getEndTime().equals(testCase.getEndTime()) || (StringUtils.isEmpty(getEndTime()) && StringUtils.isEmpty(testCase.getEndTime()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDuration(), getMethod(), getName(), getStartTime(), getEndTime(), getStatus());
    }

    @Override
    public String toString() {
        return "Test{" +
                "duration='" + duration + '\'' +
                ", method='" + method + '\'' +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
