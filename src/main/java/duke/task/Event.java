package duke.task;

import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime timePeriod;

    public Event(String identifier, String timePeriod) throws DukeException {
        super(identifier);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:m");
        try {
            this.timePeriod = LocalDateTime.parse(timePeriod, formatter);
        } catch (DateTimeParseException e) {
                throw new DukeException("\t" + "Please write the date and time in this format:" +
                        "dd-MM-yyyy h:m. For example, 05-27-1997 21:02 is the format" +
                        " to represent 9:02pm on 27 May 1997");
        }
    }

    public LocalDateTime getTimePeriod() {
        return this.timePeriod;
    }

    @Override
    public String toString() {
        return "Event: " + super.toString() + " (at: " + timePeriod + ")";
    }
}