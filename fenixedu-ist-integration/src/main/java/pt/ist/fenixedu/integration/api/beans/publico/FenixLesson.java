package pt.ist.fenixedu.integration.api.beans.publico;

import org.fenixedu.academic.domain.*;
import org.fenixedu.commons.i18n.LocalizedString;
import org.fenixedu.spaces.domain.Space;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FenixLesson extends FenixInterval {
    private String id;
    private String shiftId;
    private FenixSpace.Room room;
    private String teacherId;
    private FenixSummary summary;
    private Integer attendance;
    private String created;
    private Boolean taught;

    public FenixLesson(final LessonInstance instance) {
        super(instance.getBeginDateTime(), instance.getEndDateTime());
        setShiftId(instance.getLesson().getShift().getExternalId());
        setRoom(instance.getRoom());
        if (instance.getSummary() != null) {
            Summary summary = instance.getSummary();
            if (summary.getProfessorship() != null) {
                setTeacherId(summary.getProfessorship().getPerson().getUsername());
            } else if (summary.getTeacher() != null) {
                setTeacherId(summary.getTeacher().getPerson().getUsername());
            }
            setSummary(new FenixSummary(summary.getTitle().toLocalizedString(), summary.getSummaryText().toLocalizedString()));
            setAttendance(summary.getStudentsNumber());
            setCreated(summary.getSummaryDateTime().toString("yyyy-MM-dd HH:mm:ss"));
            setTaught(summary.getTaught());
        }
        this.id = instanceToId(this);
    }

    public FenixLesson(final Interval interval, final Shift shift, final Space room) {
        super(interval);
        setShiftId(shift.getExternalId());
        setRoom(room);
    }

    public static List<FenixLesson> collectAllLessons(ExecutionCourse executionCourse) {
        List<FenixLesson> lessons = new ArrayList<>();

        for (Lesson lesson : executionCourse.getLessons()) {
            lessons.addAll(lesson.getLessonInstancesSet().stream().map(FenixLesson::new).collect(Collectors.toList()));
            lessons.addAll(lesson.getAllLessonIntervalsWithoutInstanceDates().stream()
                    .map(interval -> new FenixLesson(interval, lesson.getSala())).collect(Collectors.toList()));
        }

        return lessons;
    }

    public static FenixLesson readById(ExecutionCourse executionCourse, String lessonId) {
        return null;
    }

    private static String instanceToId(FenixLesson fenixLesson) {
        return null;
    }

    public String getShiftId() {
        return shiftId;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public FenixSpace.Room getRoom() {
        return room;
    }

    public void setRoom(Space room) {
        this.room = room == null ? null : new FenixSpace.Room(room, false, false, null);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public FenixSummary getSummary() {
        return summary;
    }

    public void setSummary(FenixSummary summary) {
        this.summary = summary;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Boolean getTaught() {
        return taught;
    }

    public void setTaught(Boolean taught) {
        this.taught = taught;
    }

    public FenixLesson write() {
        return this;
    }

    public FenixLesson delete() {
        return this;
    }

    public static class FenixSummary {
        private LocalizedString title;

        private LocalizedString text;

        public FenixSummary(LocalizedString title, LocalizedString text) {
            this.title = title;
            this.text = text;
        }

        public LocalizedString getTitle() {
            return title;
        }

        public void setTitle(LocalizedString title) {
            this.title = title;
        }

        public LocalizedString getText() {
            return text;
        }

        public void setText(LocalizedString text) {
            this.text = text;
        }
    }

}
