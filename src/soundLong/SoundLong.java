package soundLong;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SoundLong extends Application {

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

        Timeline timeline = new Timeline();
        Duration timepoint = Duration.ZERO ;
        Duration pause = Duration.seconds(5);

        KeyFrame initial = new KeyFrame(timepoint);
        timeline.getKeyFrames().add(initial);

        for (int i = 0; i < 4; i++) {
            timepoint = timepoint.add(pause);
            KeyFrame frameRemove = new KeyFrame(timepoint, e -> {
                playerRingtone.stop(); }
            );
            timeline.getKeyFrames().add(frameRemove);

            Duration delay = Duration.seconds(rng.nextInt(15));
            timepoint = timepoint.add(delay);
            System.out.println(delay);
            KeyFrame frameAdd = new KeyFrame(timepoint, e -> {
                playerRingtone.play();

            }
            );
            timeline.getKeyFrames().add(frameAdd);
        }

        root.getChildren().add(mediaView);

        primaryStage.setScene(scene);
        primaryStage.show();
        timeline.play();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
