package ui.main;

import common.utils.FxmlPaths;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import ui.screens.principal.PrincipalController;

import java.io.IOException;

@Log4j2

public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;


    public void start(@Observes @StartUpScene Stage stage) throws IOException {
        try {
            Parent fxmlParent = fxmlLoader.load(
                    getClass().getResourceAsStream(FxmlPaths.MAIN_BORDERPAIN_SCREEN));
            PrincipalController controller = fxmlLoader.getController();
            controller.setStage(stage);

            stage.setScene(new Scene(fxmlParent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
