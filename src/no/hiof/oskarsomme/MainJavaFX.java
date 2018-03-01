package no.hiof.oskarsomme;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import no.hiof.oskarsomme.controller.FilmEditController;
import no.hiof.oskarsomme.controller.FilmOverviewController;
import no.hiof.oskarsomme.model.media.film.Film;
import java.time.LocalDate;
import java.util.Collections;

public class MainJavaFX extends Application {
    private ObservableList<Film> films = FXCollections.observableArrayList();
    private Stage primaryStage;
    private FilmOverviewController filmOverviewController;

    public MainJavaFX() {
        new Film("The Godfather", "The aging patriarch of an" +
                " organized crime dynasty transfers control of his clandestine" +
                " empire to his reluctant son.", 177, LocalDate.of(1972, 3, 24));
        new Film("Fight Club", "An insomniac office worker, " +
                "looking for a way to change his life, crosses paths with a devil-may-care " +
                "soapmaker, forming an underground fight club that evolves " +
                "into something much, much more.", 139, LocalDate.of(1999, 10, 15));
        new Film("The Goodfellas", "The story of Henry Hill and his life in the mob, " +
                "covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway " +
                "and Tommy DeVito in the Italian-American crime syndicate.", 146, LocalDate.of(1990, 9, 21));
        new Film("Black Panther", "T'Challa, the King of Wakanda, rises to the throne in the isolated, " +
                "technologically advanced African nation, but his claim is challenged by a vengeful " +
                "outsider who was a childhood victim of T'Challa's father's mistake.", 134, LocalDate.of(2018, 2, 16));
        new Film("Finding Nemo", "After his son is captured in the Great Barrier Reef and" +
                " taken to Sydney, a timid clownfish sets out on a journey to bring him home.", 100, LocalDate.of(2003, 5, 30));
        new Film("Platoon", "A young soldier in Vietnam faces a moral crisis when confronted with the horrors of war and " +
                "the duality of man.", 120, LocalDate.of(1986, 12, 19));
        new Film("Inside Man", "A police detective, a bank robber, and a high-power broker enter " +
                "high-stakes negotiations after the criminal's brilliant heist spirals into " +
                "a hostage situation.", 129, LocalDate.of(2006, 3, 24));
        new Film("25th Hour", "Cornered by the DEA, convicted New York drug dealer Montgomery Brogan " +
                "reevaluates his life in the 24 remaining hours before facing a seven-year jail " +
                "term.", 135, LocalDate.of(2003, 1, 10));
        new Film("American History X", "A former neo-nazi skinhead tries to prevent his younger brother " +
                "from going down the same wrong path that he did.", 119, LocalDate.of(1998, 10, 30));
        new Film("Birdman", "A washed-up actor, who once played an iconic superhero, attempts " +
                "to revive his career by writing and starring in his very own Broadway play.", 119, LocalDate.of(2014, 10, 17));

        films.addAll(Film.LIST_OF_FILMS);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        goToFilmListing();
    }

    public void goToFilmListing() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/FilmOversikt.fxml"));
            Parent root = loader.load();

            filmOverviewController = loader.getController();
            filmOverviewController.setMain(this);

            Scene primaryScene = new Scene(root);

            primaryStage.setTitle("Filmer");

            primaryStage.setMinHeight(500);
            primaryStage.setMinWidth(500);
            primaryStage.setScene(primaryScene);
            primaryStage.show();
        } catch (Exception e) {
            fxmlLoadingErrorBox(e);
        }
    }

    public void goToEditDialog(Film film) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/PopupMenu.fxml"));

            Parent root = loader.load();

            Stage editMenu = new Stage();
            editMenu.setTitle("Edit film");
            editMenu.initModality(Modality.WINDOW_MODAL);
            editMenu.initOwner(primaryStage);

            Scene editScene = new Scene(root);
            editMenu.setScene(editScene);

            FilmEditController fec = loader.getController();
            fec.setStage(editMenu);
            fec.setFilm(film);

            editMenu.showAndWait();

            // Updates info in FilmOverview.
            filmOverviewController.setInfoListing(film);
            // Forces a refresh of the list in FilmOverview.
            filmOverviewController.getFilmList().refresh();
        } catch (Exception e) {
            fxmlLoadingErrorBox(e);
        }
    }

    public ObservableList<Film> getFilms() {
        return films;
    }

    public void addFilm() {
        Film newFilm = new Film();
        goToEditDialog(newFilm);
        if(!newFilm.isEmpty()) {
            films.add(newFilm);
        }
    }

    private void fxmlLoadingErrorBox(Exception e) {
        Alert errorBox = new Alert(Alert.AlertType.ERROR);
        errorBox.setTitle("Something went wrong...");
        errorBox.setHeaderText(null);
        errorBox.setContentText("FXML " + e.getMessage());
        errorBox.showAndWait();
    }

    public void sortByTitleAscending() {
        Collections.sort(films);
    }

    public void sortByTitleDescending() {
        Collections.sort(films);
        Collections.reverse(films);
    }

    public void sortByReleaseDateAscending() {
        Collections.sort(films, Film.sortByReleaseDate);
    }

    public void sortByReleaseDateDescending() {
        Collections.sort(films, Film.sortByReleaseDate);
        Collections.reverse(films);
    }

    public void sortByRuntimeAscending() {
        Collections.sort(films, Film.sortByRuntime);
    }

    public void sortByRuntimeDescending() {
        Collections.sort(films, Film.sortByRuntime);
        Collections.reverse(films);
    }
}
