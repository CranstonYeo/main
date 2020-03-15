package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.tutorial.Tutorial;

public class TutorialCard extends UiPart<Region> {

    private static final String FXML = "TutorialListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Tutorial tutorial;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label modCode;
    @FXML
    private Label name;
    @FXML
    private Label day;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label TAlocation;
    //@FXML
    //private FlowPane tags;


    public TutorialCard(Tutorial tutorial, int displayedIndex) {
        super(FXML);
        this.tutorial = tutorial;
        id.setText(displayedIndex + ". ");
        name.setText(tutorial.getTutorialName().getTutorialName());
        modCode.setText(tutorial.getModCode().getModCode());
        TAlocation.setText(tutorial.getLocation().getEventLocation());
        //day.setText(tutorial.getDay());
        //startTime.setText(tutorial.getStartTime());
        //endTime.setText(tutorial.getEndTime());

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutorialCard)) {
            return false;
        }

        // state check
        TutorialCard card = (TutorialCard) other;
        return id.getText().equals(card.id.getText())
                && tutorial.equals(card.tutorial);
    }
}

