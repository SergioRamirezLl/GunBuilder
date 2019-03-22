package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Personaje {

	Image imageIzq = new Image("file:///C:/Users/Usuario/eclipse-workspace/GunBuilder/Img/GunMan.png");
	Image imageDer = new Image("file:///C:/Users/Usuario/eclipse-workspace/GunBuilder/Img/GunManDer.png");
	ImageView per = new ImageView(imageDer);
	private double xIni, yIni,xFin,yFin;
	private Button rightMove = new Button("[L]");
	private Button leftMove = new Button("[J]");
	private Button jump = new Button("Jump: [X]");
	private double powJump;
	private int timeJump;
	private double velPer;
	private boolean antiRep=true;
	private double width;
    private double height;
    private Text perdiste = new Text("Perdiste");
	
	public Personaje(double xIni,double yIni) {
		this.xIni=xIni;
		this.yIni=yIni-28;
	}
	public HBox setHBox() {
		HBox bot = new HBox();
		bot.setSpacing(20);
		Image izquierda = new Image("file:///C:/Users/Usuario/eclipse-workspace/GunBuilder/Img/izquierda.jpg");
		ImageView izq = new ImageView(izquierda);
		ImageView der = new ImageView(izquierda);
		izq.setFitWidth(20);
		izq.setPreserveRatio(true);
		izq.setSmooth(true);
		leftMove.setGraphic(izq);
		der.setFitWidth(20);
		der.setPreserveRatio(true);
		der.setSmooth(true);
		der.setRotate(180);
		rightMove.setGraphic(der);
		bot.getChildren().addAll(leftMove,rightMove,jump);
		bot.setAlignment(Pos.BOTTOM_LEFT);
		return bot;
	}
	public void setImage(BorderPane root)
    {
		root.getChildren().add(per);
		per.relocate(this.xIni, this.yIni);
		width=38;
		height=28;
    }
	public void setVelPer(double velPer) {
		this.velPer=velPer;
	}
	public void setPowJump(double powJump) {
		this.powJump=per.getLayoutY()-powJump;
	}
	public void setTimeJump(int timeJump) {
		this.timeJump=timeJump;
	}
	public Rectangle2D getBoundary()
    {
        return new Rectangle2D(xFin,yFin,width,height);
    }
	public boolean intersects(Enemigo e)
    {
        return e.getBoundary().intersects( this.getBoundary() );
    }
	public void salta() {
		if(antiRep==true) {
			antiRep=false;
			KeyValue valor = new KeyValue(per.layoutYProperty(), powJump);
			KeyFrame unFrame = new KeyFrame(Duration.millis(timeJump), valor);
			Timeline timeline = new Timeline(unFrame);
			timeline.setCycleCount(1);
			timeline.setOnFinished(evt2 ->{
				KeyValue val = new KeyValue(per.layoutYProperty(), yIni);
				KeyFrame oneFrame = new KeyFrame(Duration.millis(timeJump), val);
				Timeline time = new Timeline(oneFrame);
				time.setCycleCount(1);
				time.play();
				time.setOnFinished(evt3->antiRep=true);
			});
			timeline.play();
		}
	}
	public void movDer(double lim) {
		if((per.getLayoutX()+velPer)<=lim-width) {
			per.setLayoutX(per.getLayoutX() + velPer);
			per.setImage(imageDer);
			xFin=this.getPosX();
			yFin=this.getPosY();
		}
	}
	public void movIzq(double lim) {
		if((per.getLayoutX()-velPer)>=lim) {
			per.setLayoutX(per.getLayoutX() - velPer);
			per.setImage(imageIzq);
			xFin=this.getPosX();
			yFin=this.getPosY();
		}
	}
	public double getPosX() {
		return per.getLayoutX();
	}
	public double getPosY() {
		return per.getLayoutY();
	}
	public void muere(BorderPane root,double limite,AnimationTimer time) {
		KeyValue valor = new KeyValue(per.layoutYProperty(), limite);
		KeyFrame unFrame = new KeyFrame(Duration.seconds(3), valor);
		Timeline timeline = new Timeline(unFrame);
		timeline.setCycleCount(1);
		timeline.setOnFinished(evt2 ->{
			root.getChildren().remove(per);
			root.getChildren().add(perdiste);
			perdiste.relocate(390, 70);
		});
		timeline.play();
	}
}
