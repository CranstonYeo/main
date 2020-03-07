package seedu.address.logic.commands.consults;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_BEGIN_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONSULT_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONSULTS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Location;
import seedu.address.model.event.consult.Consult;

/**
 * Edits the details of an existing consult in TAble.
 */
public class EditConsultCommand extends Command {

    public static final String COMMAND_WORD = "editConsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the consult identified "
            + "by the index number used in the displayed consult list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_CONSULT_BEGIN_DATE_TIME + "BEGINDATETIME] "
            + "[" + PREFIX_CONSULT_END_DATE_TIME + "ENDDATETIME] "
            + "[" + PREFIX_PLACE + "PLACE] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CONSULT_BEGIN_DATE_TIME + "2020-03-03 15:00"
            + PREFIX_PLACE + "The Deck";

    public static final String MESSAGE_EDIT_CONSULT_SUCCESS = "Edited Consult: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONSULT = "This consult already exists in TAble.";

    private final Index index;
    private final EditConsultDescriptor editConsultDescriptor;

    /**
     * @param index of the consult in the filtered consult list to edit
     * @param editConsultDescriptor details to edit the consult with
     */
    public EditConsultCommand(Index index, EditConsultDescriptor editConsultDescriptor) {
        requireNonNull(index);
        requireNonNull(editConsultDescriptor);

        this.index = index;
        this.editConsultDescriptor = new EditConsultDescriptor(editConsultDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Consult> lastShownList = model.getFilteredConsultList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONSULT_DISPLAYED_INDEX);
        }

        Consult consultToEdit = lastShownList.get(index.getZeroBased());
        Consult editedConsult = createEditedConsult(consultToEdit, editConsultDescriptor);

        if (!consultToEdit.equals(editedConsult) && model.hasConsult(editedConsult)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONSULT);
        }

        model.setConsult(consultToEdit, editedConsult);
        model.updateFilteredConsultList(PREDICATE_SHOW_ALL_CONSULTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CONSULT_SUCCESS, editedConsult));
    }

    /**
     * Creates and returns a {@code Consult} with the details of {@code consultToEdit}
     * edited with {@code editConsultDescriptor}.
     */
    private static Consult createEditedConsult(Consult consultToEdit, EditConsultDescriptor editConsultDescriptor) {
        assert consultToEdit != null;

        LocalDateTime updatedBeginStartTime = editConsultDescriptor.getConsultBeginDateTime()
                .orElse(consultToEdit.getConsultBeginDateTime());
        LocalDateTime updatedEndStartTime = editConsultDescriptor.getConsultEndDateTime()
                .orElse(consultToEdit.getConsultEndDateTime());
        Location updatedLocation = editConsultDescriptor.getLocation().orElse(consultToEdit.getPlace());

        return new Consult(updatedBeginStartTime, updatedEndStartTime, updatedLocation);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditConsultCommand)) {
            return false;
        }

        // state check
        EditConsultCommand e = (EditConsultCommand) other;
        return index.equals(e.index)
                && editConsultDescriptor.equals(e.editConsultDescriptor);
    }

    /**
     * Stores the details to edit the consult with. Each non-empty field value will replace the
     * corresponding field value of the consult.
     */
    public static class EditConsultDescriptor {
        private LocalDateTime consultBeginDateTime;
        private LocalDateTime consultEndDateTime;
        private Location location;


        public EditConsultDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditConsultDescriptor(EditConsultDescriptor toCopy) {
            setConsultBeginDateTime(toCopy.consultBeginDateTime);
            setConsultEndDateTime(toCopy.consultEndDateTime);
            setLocation(toCopy.location);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(consultBeginDateTime,
                    consultEndDateTime, location);
        }

        public void setConsultBeginDateTime(LocalDateTime consultBeginDateTime) {
            this.consultBeginDateTime = consultBeginDateTime;
        }

        public Optional<LocalDateTime> getConsultBeginDateTime() {
            return Optional.ofNullable(consultBeginDateTime);
        }

        public void setConsultEndDateTime(LocalDateTime consultEndDateTime) {
            this.consultEndDateTime = consultEndDateTime;
        }

        public Optional<LocalDateTime> getConsultEndDateTime() {
            return Optional.ofNullable(consultEndDateTime);
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditConsultDescriptor)) {
                return false;
            }

            // state check
           EditConsultDescriptor e = (EditConsultDescriptor) other;

            return getConsultBeginDateTime().equals(e.getConsultBeginDateTime())
                    && getConsultEndDateTime().equals(e.getConsultEndDateTime())
                    && getLocation().equals(e.getLocation());
        }


    }
}
