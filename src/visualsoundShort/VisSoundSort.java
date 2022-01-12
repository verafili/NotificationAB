package visualsoundShort;

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

public class VisSoundSort extends Application {

    private final Random rng = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Scene scene = new Scene(root, 1900, 1000);

        String pathVideo = "videos/15 Seconds How to Make Chocolate Fudge Cake Video.mp4";
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

        StackPane stackNotif = new StackPane();
        stackNotif.setTranslateX(1450.0f);
        stackNotif.setTranslateY(800.0f);

        Rectangle r = new Rectangle(1450.0f, 800.0f, 400.0f, 130.0f);
        Text message = new Text("You have 1 new message");
        message.setStyle("-fx-font-size: 18");

        r.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 3;");
        stackNotif.getChildren().addAll(r, message);

        Timeline timeline = new Timeline();
        Duration timepoint = Duration.ZERO ;
        Duration pause = Duration.seconds(5);
        //Duration delay = Duration.seconds(rng.nextInt(15));

        KeyFrame initial = new KeyFrame(timepoint);
        timeline.getKeyFrames().add(initial);

        timepoint = timepoint.add(pause);

        Duration delay = Duration.seconds(rng.nextInt(8));
        timepoint = timepoint.add(delay);
        System.out.println(delay);
        KeyFrame frameAdd = new KeyFrame(timepoint, e -> {
            playerRingtone.play();
            pane.getChildren().add(stackNotif);
            }
        );
        timeline.getKeyFrames().add(frameAdd);

        timepoint = timepoint.add(Duration.seconds(5));

        KeyFrame frameRemove = new KeyFrame(timepoint, e -> {
            playerRingtone.stop();
            pane.getChildren().remove(stackNotif); }
        );
        timeline.getKeyFrames().add(frameRemove);


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
