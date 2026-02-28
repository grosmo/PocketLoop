package presentation.views;

import javafx.scene.layout.Pane;

public abstract class BaseController <T extends Pane>{
    private T root;

    public abstract void initialize();

    public T root(){
        return root;
    }

    public void setRoot(T pane){
        root = pane;
    }
}
