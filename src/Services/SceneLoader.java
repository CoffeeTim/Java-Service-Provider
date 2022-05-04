package Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.io.IOException;

public class SceneLoader {


    public  static  void loadScene(String resource, Node node)
    {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/ServiceProvider/view/"+resource));
        try {
            Parent root = loader.load();
            node.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
