package presentation.views;

import business.IServiceHelper;
import ddf.minim.AudioOutput;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import presentation.GUI;
import presentation.GUIHelper;
import presentation.TextHelper;

public class OscilloscopeViewController extends BaseController<OscilloscopeView> {
    
    private AnimationTimer oscilloscopeTimer;
    private AudioOutput masterOutput;
    private boolean timerRunning = false;
    private float scaleFactor;

    public OscilloscopeViewController(IServiceHelper serviceHelper, GUIHelper guiHelper) {
        setRoot(new OscilloscopeView());
        this.serviceHelper = serviceHelper;
        this.guiHelper = guiHelper;
        this.masterOutput = serviceHelper.getMasterOutput();
        scaleFactor = (float) root().scaleSlider.getValue();
        initialize();
    }

    @Override
    public void initialize() {
        
        root().switchView.setOnAction(e -> {
            GUI.setToggleSwitchView();
            root().switchView.setSelected(GUI.getToggleSwitchView());
            switchToSamplesView();
        });
        
        startOscilloscope();

        root().scaleLabel.setText(TextHelper.SCALE + (int) root().scaleSlider.getValue());
        root().scaleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            scaleFactor = newVal.floatValue();
            root().scaleLabel.setText(TextHelper.SCALE + newVal.intValue());
        });
        
        root().sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                if (oscilloscopeTimer != null && !isTimerRunning())
                    oscilloscopeTimer.start();
            }
        });

        Tooltip switchTooltip = new Tooltip(TextHelper.TOOLTIP_SWITCH_VIEW_TEXT);
        switchTooltip.setFont(TextHelper.FONT_TOOLTIP);
        root().switchView.setTooltip(switchTooltip);
        
        initializeControlls();
    }
    
    private void startOscilloscope() {
        Canvas canvas = root().getOscilloscope();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        oscilloscopeTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawOscilloscope(gc, canvas.getWidth(), canvas.getHeight());
            }
        };
        
        oscilloscopeTimer.start();
        
        canvas.widthProperty().bind(root().widthProperty());
        canvas.heightProperty().bind(root().heightProperty().subtract(120));
    }
    
    private boolean isTimerRunning() {
        return timerRunning;
    }
    
    private void drawOscilloscope(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.rgb(20, 20, 30));
        gc.fillRect(0, 0, width, height);        
        gc.setStroke(Color.rgb(60, 60, 80));
        gc.setLineWidth(1);
        gc.strokeLine(0, height / 2, width, height / 2);
        
        if (masterOutput == null)
            return;
            
        gc.setStroke(Color.rgb(0, 180, 160));
        gc.setLineWidth(3);
        
        int bufferSize = masterOutput.bufferSize();
        float[] leftSamples = masterOutput.left.toArray();
        float[] rightSamples = masterOutput.right.toArray();
        
        if (leftSamples.length == 0) {
            return;
        }

        int samplesToDisplay = Math.min(bufferSize, (int) width * 2);
        double xStep = width / (double) samplesToDisplay;
        
        gc.beginPath();
        
        for (int i = 0; i < samplesToDisplay; i++) {
            int sampleIndex = i * bufferSize / samplesToDisplay;
            
            if (sampleIndex >= leftSamples.length)
                break;
            
            float sample = (leftSamples[sampleIndex] + rightSamples[sampleIndex]) / 2.0f;            
            sample = Math.max(-1.0f, Math.min(1.0f, sample));
            sample *= scaleFactor;
            
            double x = i * xStep;
            double y = height / 2 - (sample * height / 2.5);
            
            if (i == 0)
                gc.moveTo(x, y);
            else
                gc.lineTo(x, y);
        }
        
        gc.stroke();        
        gc.setFill(Color.rgb(100, 100, 120));
    }
    
    private void switchToSamplesView() {
        if (oscilloscopeTimer != null) {
            oscilloscopeTimer.stop();
        }
        GUI.switchView(TextHelper.SWITCH_VIEW_NAME_SAMPLE);
    }
    
}