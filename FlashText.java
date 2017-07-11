import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.awt.*;

/**
 * Created by cdxu0 on 2017/7/9.
 */
public class FlashText extends Application {
    private String text = "";

    @Override
    public void start(Stage primaryStage) {
        StackPane pane = new StackPane();
        Label lblText = new Label("Programming is fun");
        pane.getChildren().add(lblText);
        //创建Runable对象
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    while (true) {
//                        if (lblText.getText().trim().length() == 0)
//                            text = "Welcome";
//                        else
//                            text = "";
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                lblText.setText(text);
//                            }
//                        });
//                        Thread.sleep(200);
//                    }
//                }
//                catch (InterruptedException ex){
//                }
//            }
//        }).start();

        new Thread(() -> {  //lambda expression
            try {
                while(true) {
                    if (lblText.getText().trim().length() == 0)
                        text = "Welcome";
                    else
                        text = "";
                    Platform.runLater(()-> lblText.setText(text));
                    Thread.sleep(200);
                }
            }
            catch (InterruptedException ex) {
            }
        }).start();

        Scene scene = new Scene(pane, 200, 50);
        primaryStage.setTitle("FlashText");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
