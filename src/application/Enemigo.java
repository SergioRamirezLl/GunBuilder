package application;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Enemigo {
	private Image imageIzq = new Image("file:///C:/Users/Usuario/eclipse-workspace/GunBuilder/Img/enemyIzq.png");
	private Image imageDer = new Image("file:///C:/Users/Usuario/eclipse-workspace/GunBuilder/Img/enemyDer.png");
	private ImageView per = new ImageView(imageDer);
	private double xIni, yIni;
	private double powJump;
	private int timeJump;
	private int vidas;
	private double velPer;
	private double width;
    private double height;
	
	public Enemigo(double xIni,double yIni) {
		this.xIni=xIni;
		this.yIni=yIni-73;
	}
	public void setImage(BorderPane root)
    {
		root.getChildren().add(per);
		per.setFitWidth(30);
		per.setPreserveRatio(true);
		per.setSmooth(true);
		width = 30;
        height = 73;
		per.relocate(this.xIni, this.yIni);
    }
	public void setVelPer(double velPer) {
		this.velPer=velPer;
	}
	public void setVidas(int vidas) {
		this.vidas=vidas;
	}
	public void corre(Personaje gun) {
		if(gun.getPosX()<this.getPosX()) {
			per.setLayoutX(per.getLayoutX() - velPer);
			per.setImage(imageIzq);
			xIni=this.getPosX();
		}
		else {
			per.setLayoutX(per.getLayoutX() + velPer);
			per.setImage(imageDer);
			xIni=this.getPosX();
		}
	}
	public double getPosX() {
		return per.getLayoutX();
	}
	public double getPosY() {
		return per.getLayoutY();
	}
	public double getDistancia(Personaje gun) {
		double distancia = Math.sqrt(Math.pow(gun.getPosX() - per.getLayoutX(), 2) + Math.pow(gun.getPosY() - per.getLayoutY(), 2));
		return distancia;
	}
	public Rectangle2D getBoundary()
    {
        return new Rectangle2D(xIni,yIni,width,height);
    }
	public boolean intersects(Disparo d)
    {
        return d.getBoundary().intersects( this.getBoundary() );
    }
	public void muere(Disparo shoot, BorderPane root) {
			if(vidas>1) {
				vidas--;
				shoot.removeBullet(root);
				shoot.relocate(0, 0);
			}
			else {
				shoot.removeBullet(root);
				root.getChildren().remove(per);
				shoot.relocate(0, 0);
				xIni=0; yIni=0;
			}
	}
}
