package business;

public class SampleModel {

    String filePath;
    protected String getFilePath(){
        return filePath;
    }
    protected void setFilePath(String path){
        this.filePath = path;
    }

    boolean resampled;
    protected boolean isResampled(){
        return resampled;
    }
    protected void setResampled(boolean resampled){
        this.resampled = resampled;
    }

    boolean loopSelected;
    protected boolean isLoopSelected() {
        return loopSelected;
    }
    protected void setLoopSelected(boolean loopSelected) {
        this.loopSelected = loopSelected;
    }

    String displayName;
    protected String getDisplayName(){
        if(displayName == null || displayName.isEmpty()){
            int lastSlash = filePath.lastIndexOf('/');
            if(lastSlash >= 0)
                return filePath.substring(lastSlash + 1);
            return filePath;
        }
        return displayName;
    }

    protected void setDisplayName(String name){
        this.displayName = name;
    }

    protected SampleModel(String filePath){
        this.filePath = filePath;
    }
}
