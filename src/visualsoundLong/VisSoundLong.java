package visualsoundLong;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.text.Text;

public class VisSoundLong extends Application {

    private final Random rng = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 1900, 1000);

        String pathVideo = "videos/1 Minute Recipe _ Eggplant Rollatini.mp4";
        String pathRingtone = "videos/notification.mp3";

        Media mediaVideo = new Media(new File(pathVideo).toURI().toString());
        Media mediaRingtone = new Media(new File(pathRingtone).toURI().toString());

        MediaPlayer mediaPlayer = new MediaPlayer(mediaVideo);
        MediaPlayer playerRingtone = new MediaPlayer(mediaRingtone);

        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.fitWidthProperty().bind(scene.widthProperty());
        mediaView.fitHeightProperty().bind(scene.heightProperty());

        mediaPlayer.setAutoPlay(true);

        //Looping video
        /*mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });*/

        Pane pane = new Pane();
        pane.setMinSize(600, 600);

        List<StackPane> notifs = new ArrayList<>();
        for (int i = 0 ; i < 3 ; i++) {
            StackPane stack = new StackPane();
            stack.setTranslateX(1450.0f);
            stack.setTranslateY(800.0f);

            Rectangle r = new Rectangle(1450.0f, 800.0f, 400.0f, 130.0f);
            Text message = new Text("You have " + (i + 2) + " new messages");

            r.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 3;");
            message.setStyle("-fx-font-size: 18");
            stack.getChildren().addAll(r, message);
            notifs.add(stack);
        }

        Timeline timeline = new Timeline();
        Duration timepoint = Duration.ZERO ;
        Duration pause = Duration.seconds(5);
        //Duration delay = Duration.seconds(rng.nextInt(15));

        KeyFrame initial = new KeyFrame(timepoint);
        timeline.getKeyFrames().add(initial);

        int index = 0;
        for (StackPane notif : notifs) {
            timepoint = timepoint.add(pause);
            int finalIndex = index;
            KeyFrame frameRemove = new KeyFrame(timepoint, e -> {
                    playerRingtone.stop();
                    if (finalIndex != 0)
                        pane.getChildren().remove(notifs.get(finalIndex - 1)); }
                    );
            timeline.getKeyFrames().add(frameRemove);

            Duration delay = Duration.seconds(rng.nextInt(15));
            timepoint = timepoint.add(delay);
            System.out.println(delay);
            KeyFrame frameAdd = new KeyFrame(timepoint, e -> {
                playerRingtone.play();
                pane.getChildren().add(notif);
            }
            );
            timeline.getKeyFrames().add(frameAdd);
            index ++;
        }

        root.getChildren().add(mediaView);
        root.getChildren().add(pane);

        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
