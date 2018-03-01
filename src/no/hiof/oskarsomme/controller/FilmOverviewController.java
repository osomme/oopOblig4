package no.hiof.oskarsomme.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import no.hiof.oskarsomme.MainJavaFX;
import no.hiof.oskarsomme.model.media.film.Film;

public class FilmOverviewController {
    @FXML
    private RadioMenuItem contextSortTitleAsc, contextSortTitleDesc, contextSortReleaseAsc,
            contextSortReleaseDesc, contextSortRuntimeAsc, contextSortRuntimeDesc;
    @FXML
    private Button btnNew, btnEdit, btnDelete;
    @FXML
    private Label filmTitle, filmDate, filmRuntime, filmDescription;
    @FXML
    private ListView<Film> filmList;


    private MainJavaFX main;
    private Film currentTarget;

    @FXML
    private void initialize() {
        // Edit film attributes (oppgave 9)
        btnEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                main.goToEditDialog(currentTarget);
                setInfoListing(currentTarget); // Updates info with new values
            }
        });

        // Add new film to list (oopgave 10).
        btnNew.setOnAction(event -> main.addFilm());

        // Delete from list (oppgave 11).
        btnDelete.setOnAction(event -> deleteFilm(currentTarget));

        // Handles actions involving context menu over listView.
        contextSortTitleAsc.setOnAction(event -> sortByTitleAscending());
        contextSortTitleDesc.setOnAction(event -> sortByTitleDescending());
        contextSortReleaseAsc.setOnAction(event -> sortByReleaseDateAscending());
        contextSortReleaseDesc.setOnAction(event -> sortByReleaseDateDescending());
        contextSortRuntimeAsc.setOnAction(event -> sortByRuntimeAscending());
        contextSortRuntimeDesc.setOnAction(event -> sortByRuntimeDescending());
    }


    public void setMain(MainJavaFX main) {
        this.main = main;

        filmList.setItems(main.getFilms());
        filmList.setFixedCellSize(40);

        // Sets info with values of first film in list of films.
        setInfoListing(Film.LIST_OF_FILMS.get(0));
        // At launch the list is sorted by title (a-z)
        sortByTitleAscending();
    }

    public void getInfo(InputEvent mouseEvent) {
        currentTarget = filmList.getFocusModel().getFocusedItem();

        if(main.getFilms().size() > 0) {
            setInfoListing(currentTarget);
        }
    }

    public void setInfoListing(Film film) {
        final String[] MONTH_NAMES = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        if(!film.isEmpty()) {
            filmTitle.setText(film.getTitle());
            filmDate.setText(String.valueOf(film.getReleaseDate().getDayOfMonth() + " " +
                    MONTH_NAMES[film.getReleaseDate().getMonthValue() - 1] + " " +
                    film.getReleaseDate().getYear()));
            filmRuntime.setText(film.getRunningTime() + " minutes");
            filmDescription.setText(film.getDescription());
        }
    }

    private void setSelectedContextMenu(RadioMenuItem item) {
        final RadioMenuItem[] RADIO_MENU_ITEMS = {contextSortTitleAsc, contextSortTitleDesc, contextSortReleaseAsc,
                contextSortReleaseDesc, contextSortRuntimeAsc, contextSortRuntimeDesc};

        for(RadioMenuItem menuItem : RADIO_MENU_ITEMS) {
            if(menuItem.getId().equals(item.getId())) {
                menuItem.setSelected(true);
            } else {
                menuItem.setSelected(false);
            }
        }
    }

    private void deleteFilm(Film film) {
        if(main.getFilms().contains(film)) {
            main.getFilms().remove(film);
        }
    }

    @FXML
    private void sortByTitleAscending() {
        main.sortByTitleAscending();
        setSelectedContextMenu(contextSortTitleAsc);
    }

    @FXML
    private void sortByTitleDescending() {
        main.sortByTitleDescending();
        setSelectedContextMenu(contextSortTitleDesc);
    }

    @FXML
    private void sortByReleaseDateAscending() {
        main.sortByReleaseDateAscending();
        setSelectedContextMenu(contextSortReleaseAsc);
    }

    @FXML
    private void sortByReleaseDateDescending() {
        main.sortByReleaseDateDescending();
        setSelectedContextMenu(contextSortReleaseDesc);
    }

    @FXML
    private void sortByRuntimeAscending() {
        main.sortByRuntimeAscending();
        setSelectedContextMenu(contextSortRuntimeAsc);
    }

    @FXML
    private void sortByRuntimeDescending() {
        main.sortByRuntimeDescending();
        setSelectedContextMenu(contextSortRuntimeDesc);
    }



    public ListView<Film> getFilmList() {
        return filmList;
    }
}
