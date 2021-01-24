package Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.AirplaneListenerModel;
import models.ConnectModel;
import models.MatrixModel;

public class MainApp extends Application {


	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Flight Master");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("file:resources/app_icon.png"));

			AirplaneListenerModel airplaneModel = new AirplaneListenerModel(5500);
			MatrixModel matrixModel = new MatrixModel();
			ConnectModel connectionModel = new ConnectModel();
			ViewModel vm = new ViewModel(matrixModel, airplaneModel, connectionModel);

			airplaneModel.addObserver(vm);
			matrixModel.addObserver(vm);
			connectionModel.addObserver(vm);

			FXMLLoader fxl = new FXMLLoader();
			MainWindowController mainWindowController;
			BorderPane root = fxl.load(getClass().getResource("MainWindow.fxml").openStream());
			Scene scene = new Scene(root);
			mainWindowController = fxl.getController();
			mainWindowController.setViewModel(vm);
			vm.addObserver(mainWindowController);
			airplaneModel.start();
			primaryStage.setScene(scene);
			primaryStage.show();

			primaryStage.setOnCloseRequest(e -> {
				airplaneModel.stop();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
		Platform.exit();
		System.exit(0);
	}

}
