package presentation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import business.IServiceHelper;
import business.RecorderService;
import business.ServiceHelper;
import presentation.views.BaseController;
import presentation.views.SamplesViewController;
import presentation.views.OscilloscopeViewController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application {
	private static Map<String, Pane> views;
	private static BorderPane mainRoot;
	RecorderService recorderService;
	Thread recorderServiceThread;
	IServiceHelper serviceHelper;
	GUIHelper guiHelper;

	private static boolean toggleSwitchView;
    public static void setToggleSwitchView(){
        toggleSwitchView = !toggleSwitchView;
    }
    public static boolean getToggleSwitchView(){
        return toggleSwitchView;
    }
	
	@Override
	public void init() {
		serviceHelper = new ServiceHelper();
		views = new HashMap<>();
		toggleSwitchView = false;
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		guiHelper = new GUIHelper();
		BaseController SamplesViewController = new SamplesViewController(serviceHelper, guiHelper);
		Pane SamplesView = SamplesViewController.root();

		BaseController oscilloscopeViewController = new OscilloscopeViewController(serviceHelper, guiHelper);
		Pane oscilloscopeView = oscilloscopeViewController.root();

		views.put(TextHelper.SWITCH_VIEW_NAME_SAMPLE, SamplesView);
		views.put(TextHelper.SWITCH_VIEW_NAME_OSZILLOSKOPE, oscilloscopeView);
		
		mainRoot = new BorderPane();
		mainRoot.setCenter(views.get(TextHelper.SWITCH_VIEW_NAME_SAMPLE));
		Scene scene = new Scene(mainRoot);

		scene.getStylesheets().add(getClass().getResource(TextHelper.CSS_SHEET).toExternalForm());
		primaryStage.getIcons().add(guiHelper.getIcon());
		
		primaryStage.setTitle(TextHelper.APP_TITLE);
		primaryStage.setScene(scene);
		primaryStage.setMinWidth(950);
		primaryStage.setMinHeight(800);
		primaryStage.requestFocus();
		primaryStage.show();
	}
	
	@Override
	public void stop() {
		System.out.println("Beende Anwendung...");
		
		if (serviceHelper != null) {
			serviceHelper.stopAllPlays();
			serviceHelper.stopRecording();
		}
		
		if (recorderServiceThread != null && recorderServiceThread.isAlive())
			recorderServiceThread.interrupt();
				
		System.gc();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		
		deleteRecordings();
		System.out.println("Anwendung beendet.");
	}
	
	private void deleteRecordings() {
		Path recordingsPath = Paths.get("recordings");
		if (!Files.exists(recordingsPath)) return;
		
		for (int versuch = 1; versuch <= 3; versuch++) {
			boolean erfolg = tryDeleteRecordings(recordingsPath, versuch == 3);
			if (erfolg) return;
			
			if (versuch < 3) {
				try { Thread.sleep(300); } catch (InterruptedException e) {}
				System.gc();
			}
		}
	}
	
	private boolean tryDeleteRecordings(Path recordingsPath, boolean letzterVersuch) {
		try (var stream = Files.walk(recordingsPath)) {
			stream.sorted(Comparator.reverseOrder()).forEach(path -> {
				try {
					Files.delete(path);
				} catch (IOException e) {
					if (letzterVersuch) path.toFile().deleteOnExit();
				}
			});
			return !Files.exists(recordingsPath);
		} catch (IOException e) {
			return false;
		}
	}

	public static void switchView(String viewName) {
		Pane view = views.get(viewName);
		if (view != null) {
			mainRoot.setCenter(view);
		}
	}
}
	