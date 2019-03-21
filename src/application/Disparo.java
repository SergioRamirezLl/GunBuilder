package application;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;

public class Disparo {
	private TextField txt= new TextField("Balas restantes: "+15);
	private Button btn = new Button("Fire: [C]");
	private Image img = new Image("file:///C:/Users/Usuario/eclipse-workspace/GunBuilder/Img/bullet.png");
	private ImageView bullet = new ImageView(img);
	private int numBal;
	private double limite;
	private int seg;
	private boolean antiRep=true;
	private double width;
    private double height;
	
	public Disparo() {
		this.width=6;
		this.height=2;
	}
	
	public void setLimite(double limite) {
		this.limite=limite;
	}
	public void setTiempo(int seg) {
		this.seg=seg;
	}
	public HBox bot() {
		HBox bot = new HBox();
		bot.setSpacing(20);;
		bot.setAlignment(Pos.BOTTOM_RIGHT);
		bot.getChildren().addAll(btn,txt);
		return bot;
	}
	public void dispara(BorderPane root, Personaje per) {
		if((this.antiRep==true)&&(numBal>0)) {
			this.antiRep=false;
			--numBal;
			txt.setText("Balas restantes: "+ this.getNumBal());
			root.getChildren().add(bullet);
			bullet.relocate(per.getPosX()+19, per.getPosY()+16);//top-left sqrt form
			KeyValue valor = new KeyValue(bullet.layoutXProperty(), limite);
			KeyFrame unFrame = new KeyFrame(Duration.millis(seg), valor);
			Timeline timeline = new Timeline(unFrame);
			timeline.setCycleCount(1);
			timeline.setOnFinished(evt2 -> {root.getChildren().remove(bullet); this.antiRep=true;});
			timeline.play();
		}
	}
	public void setNumBal(int numBal) {
		txt.setEditable(false);
		this.numBal=numBal;
		txt.setText("Balas restantes: "+ this.getNumBal());
	}
	public int getNumBal() {
		return this.numBal;
	}
	public double getPosX() {
		return bullet.getLayoutX();
	}
	public double getPosY() {
		return bullet.getLayoutY();
	}
	public Rectangle2D getBoundary()
    {
        return new Rectangle2D(this.getPosX(),this.getPosY(),width,height);
    }
	public void removeBullet(BorderPane root) {
		root.getChildren().remove(bullet);
	}
	public void relocate(double x, double y) {
		bullet.relocate(x, y);
	}
}
