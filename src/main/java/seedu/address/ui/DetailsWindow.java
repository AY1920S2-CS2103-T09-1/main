package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Displayable;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of selected {@code Event} and its deadline list update of children node of
 * slideWindowEvent happened here.
 */
public class DetailsWindow extends UiPart<Region> {

    private static final String FXML = "DetailsWindow.fxml";
    private ObservableList<Event> eventList;
    private MainWindow mainWindow;

    private Displayable displayable;

    @FXML
    private ListView<Displayable> deadlineListView;
    @FXML
    private VBox slideEventCard;

    private SlideWindowEvent slideWindowEvent;

    public DetailsWindow(Displayable displayable, MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.displayable = displayable;
        if (displayable instanceof Event) {
            deadlineListView.setItems(FXCollections.observableArrayList(((Event) displayable).getDeadlines()));
        } else if (displayable instanceof Module) {
            deadlineListView.setItems(FXCollections.observableArrayList(((Module) displayable).getEvents()));
        } else {
            deadlineListView.setItems(FXCollections.emptyObservableList());
        }
        slideWindowEvent = new SlideWindowEvent(displayable);
        slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
        deadlineListView.setCellFactory(listView -> new ListViewCell(mainWindow));
    }

    /**
     * Updates this component.
     */
    public void updateStatus() {
        if (displayable instanceof Event) {
            deadlineListView.setItems(FXCollections.observableArrayList(((Event) displayable).getDeadlines()));
        } else {
            assert displayable instanceof Module;
            deadlineListView.setItems(FXCollections.observableArrayList(((Module) displayable).getEvents()));
        }
        slideWindowEvent = new SlideWindowEvent(displayable);
        slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
        deadlineListView.setCellFactory(listView -> new ListViewCell(mainWindow));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code event} using a {@code eventCard}.
     */
    static class ListViewCell extends ListCell<Displayable> {
        private MainWindow mainWindow;

        public ListViewCell(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }

        @Override
        protected void updateItem(Displayable listItem, boolean empty) {
            super.updateItem(listItem, empty);
            if (empty || listItem == null) {
                setGraphic(null);
                setText(null);
            } else if (listItem instanceof Deadline) {
                setGraphic(new DeadlineCard((Deadline) listItem, getIndex() + 1, mainWindow).getRoot());
            } else if (listItem instanceof Event) {
                setGraphic(new EventSidePanelCard((Event) listItem, getIndex() + 1).getRoot());
                setOnMouseClicked(event -> mainWindow.handleSidePanelListClick((Event) listItem));
            }
        }
    }
}