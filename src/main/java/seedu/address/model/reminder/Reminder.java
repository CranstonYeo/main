package seedu.address.model.reminder;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Reminder {
    private String desc;

    public Reminder(String desc) {
        requireAllNonNull(desc);
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSameReminder(Reminder otherReminder) {
        if (otherReminder == this) {
            return true;
        }
        return otherReminder != null
                && otherReminder.getDesc().equals(getDesc());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Reminder)) {
            return false;
        }
        Reminder otherReminder = (Reminder) other;
        return otherReminder.getDesc().equals(getDesc());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(desc);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("REMINDER: ")
                .append(getDesc() + "\n");
        return builder.toString();
    }
}
