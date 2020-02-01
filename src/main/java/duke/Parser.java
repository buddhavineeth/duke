package duke;

import duke.command.*;
import duke.exception.DukeException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static Command parse(String instruction) throws DukeException {
        if (instruction.equals("")) {
            throw new DukeException("\t" + "Please use me! Do not leave your commands empty.");
        }

        String keyword = instruction.split(" ")[0].toLowerCase();
        // by default, result is a Bye Command
        switch (keyword) {
            case "bye":
                return new ByeCommand();

            case "list":
                return new ListCommand();

            case "done":
                String[] doneTokens = instruction.split(" ");
                if (doneTokens.length != 2) {
                    throw new DukeException("\t" + "Please key in the correct format of your done command.");
                }

                int completedTaskNumber;
                try {
                    completedTaskNumber = Integer.parseInt(doneTokens[1]);
                } catch (NumberFormatException ne) {
                    throw new DukeException("\t" + "Please specify the number of the task that you" +
                            " wish to mark as completed.");
                }
                return new DoneCommand(completedTaskNumber);

            case "delete":
                String[] delTokens = instruction.split(" ");
                if (delTokens.length != 2) {
                    throw new DukeException("\t" + "Please key in the correct format of your delete command.");
                }

                int deletedTaskNumber;
                try {
                    deletedTaskNumber = Integer.parseInt(delTokens[1]);
                } catch (NumberFormatException ne) {
                    throw new DukeException("\t" + "Please specify the number of the task that you" +
                            " wish to delete.");
                }
                return new DeleteCommand(deletedTaskNumber);

            case "whatsup":
                String[] whatsupTokens = instruction.split("/on ");
                LocalDate queryDate;
                try {
                    queryDate = LocalDate.parse(whatsupTokens[1].trim());
                } catch (DateTimeParseException e) {
                    throw new DukeException("\t" + "Please write the date in this format:" +
                            "YYYY-MM-DD");
                }
                return new WhatsupCommand(queryDate);

            case "todo":
                String taskName;
                try {
                    taskName = instruction.split("todo ")[1];
                    if (taskName.equals("")) {
                        throw new DukeException("\t" + "Invalid Todo Format.");
                    }
                } catch (Exception e) {
                    throw new DukeException("\t" + "Please do not leave todo description as empty.");
                }
                return new AddCommand("todo", taskName);

            case "event":
                String[] eventTokens = instruction.split("/at ");
                String timePeriod;
                String eventName;
                try {
                    timePeriod = eventTokens[1];
                    eventName = eventTokens[0].split("event ")[1].trim();
                    if (timePeriod.equals("") || eventName.equals("")) {
                        throw new DukeException("\t" + "Invalid Event Format.");
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    throw new DukeException("\t" + "Please do not leave the event description (partially) empty.");
                }
                return new AddCommand("event", eventName, timePeriod);

            case "deadline":
                String[] deadlineTokens = instruction.split("/by ");
                String dueDate;
                String deadlineName;
                try {
                    dueDate = deadlineTokens[1];
                    deadlineName = deadlineTokens[0].split("deadline ")[1].trim();
                    if (dueDate.equals("") || deadlineName.equals("")) {
                        throw new DukeException("\t" + "Invalid Deadline Format.");
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    throw new DukeException("\t" + "Please do not leave the event description (partially) empty.");
                }
                return new AddCommand("deadline", deadlineName, dueDate);

            case "find":
                String[] findTokens = instruction.split(" ");
                String keyWord;
                try {
                    keyWord = findTokens[1];
                    if (keyWord.equals("")) {
                        throw new DukeException("\t" + "Please enter a keyword when finding.");
                    }
                } catch (ArrayIndexOutOfBoundsException aiobe) {
                    throw new DukeException("\t" + "Please do not leave the find description empty.");
                }
                return new FindCommand(keyWord);

            default:
                throw new DukeException("\t" + "You have used an invalid instruction. " +
                    "I am too dumb to understand you.");
        }
    }
}