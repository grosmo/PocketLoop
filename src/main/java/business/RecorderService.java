package business;

import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.concurrent.Task;
import java.io.File;
import ddf.minim.*;

public class RecorderService extends Task<Boolean> {
    private final int RECORDING_DURATION_MS = 4000; 
    
    private Minim minim;
    private AudioInput input;
    private AudioRecorder recorder;
    private ServiceHelper serviceHelper;
    private String currentRecordingFile;

    protected RecorderService(ServiceHelper serviceHelper){
        this.serviceHelper = serviceHelper;
        minim = new SimpleMinim();
        File recordingsDir = new File("recordings");
        if (!recordingsDir.exists()) 
            recordingsDir.mkdirs();
    }

    @Override
    protected Boolean call() {
        while(Thread.currentThread().isInterrupted() == false){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        return true;
    }

    // Initialisiert Audio-Input VOR dem Countdown, damit Hardware bereit ist
    protected void prepareRecording(){
        currentRecordingFile = "recordings/recording_" + System.currentTimeMillis() + ".wav";
        input = minim.getLineIn(Minim.STEREO, 512);
        
        if(input != null){
            recorder = minim.createRecorder(input, currentRecordingFile, true);
        }
    }
    
    // Startet die eigentliche Aufnahme NACH dem Countdown
    protected void startRecording(){
        if(recorder != null){
            recorder.beginRecord();
            new Thread(() -> {
                try {
                    Thread.sleep(RECORDING_DURATION_MS);
                    stopRecording();
                    serviceHelper.getSamplePlayers().add(new AudioSamplePlayer(serviceHelper, new SampleModel(currentRecordingFile)));
                    serviceHelper.setRecording(false);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }

    protected void stopRecording(){
        if(recorder != null){
            recorder.endRecord();
            recorder.save();   
            recorder = null;
        }
        if(input != null){
            input.close();
            input = null;
        }
        if(minim != null)
            minim.stop();
    }
}
