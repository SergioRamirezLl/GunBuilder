package application;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;

public class Main extends Application {
	private BorderPane root = new BorderPane();
	private Scene scene = new Scene(root,800,600);
	private double posX=100, posY=340;
	private boolean nerf=true;
	private Image img = new Image("file:///C:/Users/Usuario/eclipse-workspace/GunBuilder/Img/fondo.png");
	private ImageView fondo = new ImageView(img);
	@Override
	public void start(Stage primaryStage) {
		try {
			root.setPadding(new Insets(10, 10, 10, 10));// top, right, bottom, left
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Tank Cool Game");
			primaryStage.setScene(scene);
			root.getChildren().add(fondo);
			primaryStage.show();
			
			Bounds limite = root.getBoundsInLocal();
			
			Personaje gunMan = new Personaje(this.posX, this.posY);
			gunMan.setImage(root);
			root.setLeft(gunMan.setHBox());
			gunMan.setVelPer(1.5);
			gunMan.setPowJump(100);
			gunMan.setTimeJump(500);
			
			Disparo fire = new Disparo();
			root.setBottom(fire.bot());
			fire.setTiempo(600);
			fire.setNumBal(7);
			fire.setLimite(limite.getMaxX());
			
			Enemigo enemy = new Enemigo(this.posX+300, this.posY);
			enemy.setImage(root);
			enemy.setVelPer(0.8);
			enemy.setVidas(3);
			
			Enemigo enemy2 = new Enemigo(this.posX+350, this.posY);
			enemy2.setImage(root);
			enemy2.setVelPer(1);
			enemy2.setVidas(3);
			
			ArrayList<String> input = new ArrayList<String>();
			scene.setOnKeyPressed(evt->{
				String code = evt.getCode().toString();
                if ( !input.contains(code) )
                    input.add( code );
			});
			scene.setOnKeyReleased(evt->{
				String code = evt.getCode().toString();
                input.remove( code );
			});
			   new AnimationTimer()
		        {
		            public void handle(long currentNanoTime)
		            {
		                if(input.contains("X"))
		                	gunMan.salta();
		                if(input.contains("C"))
		                	fire.dispara(root,gunMan);
		                if(input.contains("J")) {
		                	gunMan.movIzq(limite.getMinX());
							fire.setLimite(limite.getMinX());
		                }
		                if(input.contains("L")) {
		                	gunMan.movDer(limite.getMaxX());
							fire.setLimite(limite.getMaxX());
		                }
		                if(enemy.intersects(fire)) {
		                	enemy.muere(fire,root);
		                }
		                if(enemy2.intersects(fire)) {
		                	enemy2.muere(fire,root);
		                }
		                if(enemy.getDistancia(gunMan)<=200) {
		                	enemy.corre(gunMan);
		                }
		                if(gunMan.intersects(enemy)) {
		                	if(nerf==true) {
		                		fire.setNumBal(fire.getNumBal()-3);
		                		nerf=false;
		                		KeyFrame frame = new KeyFrame(Duration.seconds(3));
			            		Timeline waitTime = new Timeline(frame);
			            		waitTime.setOnFinished(evt->nerf=true);
			            		waitTime.play();
		                	}
		                }
		                if(enemy2.getDistancia(gunMan)<=200) {
		                	enemy2.corre(gunMan);
		                }
		                if(gunMan.intersects(enemy2)) {
		                	if(nerf==true) {
		                		fire.setNumBal(fire.getNumBal()-3);
		                		nerf=false;
		                		KeyFrame frame = new KeyFrame(Duration.seconds(3));
			            		Timeline waitTime = new Timeline(frame);
			            		waitTime.setOnFinished(evt->nerf=true);
			            		waitTime.play();
		                	}
		                }
		                if(fire.getNumBal()<0) {
		                	input.clear();
		                	gunMan.muere(root, 0, this);
		                	this.stop();
		                }
		            }
		        }.start();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
