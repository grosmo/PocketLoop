package presentation.uicomponents;

import business.IAudioSamplePlayer;
import ddf.minim.AudioSample;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import presentation.TextHelper;

public class WaveformCanvas extends StackPane {
    private static final int WAVEFORM_HEIGHT = 60;
    private static final int WAVEFORM_WIDTH = 400;
    private static final int HANDLE_WIDTH = 2;
    private static final double HANDLE_SNAP_DISTANCE = 5.0;   
    
    private static final String SAMPLE_LADE_FEHLER = "FEHLER: Sample konnte nicht geladen werden!";
    private static final String WAVEFORM_LADE_FEHLER = "FEHLER beim Laden der Waveform: ";
    
    private Canvas canvas;
    public IAudioSamplePlayer sample;
    private float[] waveformData; 
    private int totalSamples;
    private float maxAmplitude = 1.0f;
    private double startHandleX;
    private double endHandleX;
    private boolean draggingStart = false;
    private boolean draggingEnd = false;
        
    public WaveformCanvas(IAudioSamplePlayer sample) {
        this.sample = sample;
        this.canvas = new Canvas(WAVEFORM_WIDTH, WAVEFORM_HEIGHT);
            
        getChildren().add(canvas);
        
        this.getStyleClass().add(TextHelper.STYLECLASS_WAVEFORM_CANVAS);
        
        this.setMinWidth(0);
        this.setMinHeight(WAVEFORM_HEIGHT);
        
        canvas.widthProperty().bind(this.widthProperty());
        
        canvas.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            if (newWidth.doubleValue() > 0) {
                updateHandlePositions();
                drawWaveform();
            }
        });
        
        loadWaveformData();
        updateHandlePositions();
        drawWaveform();
        setupMouseHandlers();
    }
        
    private void loadWaveformData() {
        SimpleMinim tempMinim = null;
        AudioSample sampleIntern = null;
        
        try {
            tempMinim = new SimpleMinim();
            sampleIntern = tempMinim.loadSample(sample.getFilePath());
            
            if (sampleIntern != null) {
                int channels = sampleIntern.getFormat().getChannels();
                float[] leftChannel = sampleIntern.getChannel(AudioSample.LEFT);
                float[] rightChannel = channels > 1 ? sampleIntern.getChannel(AudioSample.RIGHT) : leftChannel;
                
                totalSamples = leftChannel.length;
                
                waveformData = new float[totalSamples];
                
                maxAmplitude = 0;
                for (int i = 0; i < totalSamples; i++) {
                    waveformData[i] = (leftChannel[i] + rightChannel[i]) / 2.0f;
                    maxAmplitude = Math.max(maxAmplitude, Math.abs(waveformData[i]));
                }
                
                if (maxAmplitude < 0.00001f) maxAmplitude = 0.00001f;
            } else {
                System.err.println(SAMPLE_LADE_FEHLER);
            }
        } catch (Exception e) {
            System.err.println(WAVEFORM_LADE_FEHLER + e.getMessage());
        } finally {
            if (sampleIntern != null) {
                try {
                    sampleIntern.close();
                } catch (Exception e) {
                    // Ignoriere Fehler beim Schließen
                }
            }
            if (tempMinim != null) {
                try {
                    tempMinim.stop();
                } catch (Exception e) {
                    // Ignoriere Fehler beim Stoppen
                }
            }
        }
    }
        
    private void updateHandlePositions() {
        double totalDuration = sample.getTotalDurationMilliseconds();
        double startMs = sample.getStartMilliseconds();
        double endMs = sample.getEndMilliseconds();
        double width = canvas.getWidth();
        
        startHandleX = (startMs / totalDuration) * width;
        endHandleX = (endMs / totalDuration) * width;
    }
        
    private void drawWaveform() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        
        if (waveformData == null || totalSamples == 0) 
            return;
        
        double centerY = height / 2;
        double samplesPerPixel = (double) totalSamples / width;
        
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        
        for (int pixel = 0; pixel < width; pixel++) {
            int startIdx = (int)(pixel * samplesPerPixel);
            int endIdx = (int)((pixel + 1) * samplesPerPixel);
            endIdx = Math.min(endIdx, totalSamples);
            
            if (startIdx >= endIdx) 
                continue;
            
            float min = Float.MAX_VALUE;
            float max = Float.MIN_VALUE;
            for (int i = startIdx; i < endIdx; i++) {
                min = Math.min(min, waveformData[i]);
                max = Math.max(max, waveformData[i]);
            }
            
            if (min == Float.MAX_VALUE || max == Float.MIN_VALUE) 
                continue;
            
            double normalizedMin = min / maxAmplitude;
            double normalizedMax = max / maxAmplitude;
            
            double yMin = centerY - (normalizedMin * height * 0.45);
            double yMax = centerY - (normalizedMax * height * 0.45);
            
            gc.strokeLine(pixel, yMax, pixel, yMin);
        }
        
        gc.setStroke(Color.rgb(80, 80, 80));
        gc.setLineWidth(1.5);
        gc.strokeLine(0, centerY, width, centerY);
        gc.setFill(Color.rgb(100, 100, 100, 0.6));
        
        if (startHandleX > 0)
            gc.fillRect(0, 0, startHandleX, height);
        
        if (endHandleX < width)
            gc.fillRect(endHandleX, 0, width - endHandleX, height);
        
        gc.setFill(Color.BLACK);
        gc.fillRect(startHandleX - HANDLE_WIDTH / 2, 0, HANDLE_WIDTH, height);
        
        gc.setFill(Color.BLACK);
        gc.fillRect(endHandleX - HANDLE_WIDTH / 2, 0, HANDLE_WIDTH, height);
    }
        
    private void setupMouseHandlers() {
        canvas.setOnMousePressed(e -> {
            double x = e.getX();            
            if (Math.abs(x - startHandleX) < HANDLE_SNAP_DISTANCE) {
                draggingStart = true;
                canvas.setCursor(Cursor.H_RESIZE);
            } else if (Math.abs(x - endHandleX) < HANDLE_SNAP_DISTANCE) {
                draggingEnd = true;
                canvas.setCursor(Cursor.H_RESIZE);
            }
        });
        
        canvas.setOnMouseDragged(e -> {
            double x = Math.max(0, Math.min(canvas.getWidth(), e.getX()));
            if (draggingStart) {
                if (x < endHandleX - HANDLE_WIDTH) {
                    startHandleX = x;
                    updateRecordingStart();
                    drawWaveform();
                }
            } else if (draggingEnd) {
                if (x > startHandleX + HANDLE_WIDTH) {
                    endHandleX = x;
                    updateRecordingEnd();
                    drawWaveform();
                }
            }
        });
        
        canvas.setOnMouseReleased(e -> {
            draggingStart = false;
            draggingEnd = false;
            canvas.setCursor(Cursor.DEFAULT);            
        });
        
        canvas.setOnMouseMoved(e -> {
            double x = e.getX();            
            if (Math.abs(x - startHandleX) < HANDLE_SNAP_DISTANCE ||
                Math.abs(x - endHandleX) < HANDLE_SNAP_DISTANCE) {
                canvas.setCursor(Cursor.H_RESIZE);
            } else {
                canvas.setCursor(Cursor.DEFAULT);
            }
        });
    }
    
    private void updateRecordingStart() {
        double totalDuration = sample.getTotalDurationMilliseconds();
        double width = canvas.getWidth();
        double startMs = (startHandleX / width) * totalDuration;
        sample.setStartMilliseconds(startMs);
    }
    
    private void updateRecordingEnd() {
        double totalDuration = sample.getTotalDurationMilliseconds();
        double width = canvas.getWidth();
        double endMs = (endHandleX / width) * totalDuration;
        sample.setEndMilliseconds(endMs);
    }
}