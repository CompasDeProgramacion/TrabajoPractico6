package zioncosta.trabajopractico6;

import android.nfc.tech.NfcA;
import android.provider.Settings;
import android.support.annotation.FloatRange;
import android.util.Log;

import org.cocos2d.actions.interval.MoveTo;
import org.cocos2d.actions.interval.RotateBy;
import org.cocos2d.actions.interval.RotateTo;
import org.cocos2d.actions.interval.ScaleBy;
import org.cocos2d.layers.Layer;
import org.cocos2d.nodes.Director;
import org.cocos2d.nodes.Label;
import org.cocos2d.nodes.Scene;
import org.cocos2d.nodes.Sprite;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.types.CCColor3B;
import org.cocos2d.types.CCPoint;
import org.cocos2d.types.CCSize;

import java.util.Random;

/**
 * Created by 42567058 on 1/8/2017.
 */

public class clsJuego

{
   CCGLSurfaceView _VistaDelJuego;
   CCSize PantallaDelDevice;
   Sprite Androidcito;
   Float AlturaAndroidcito;
   Float AnchuraAndroidcito;
   Sprite ImagenFondo;
   Label lblTituloJuego;
   
   public clsJuego(CCGLSurfaceView VistaDelJuego)
   {
	  _VistaDelJuego = VistaDelJuego;
   }
   
   public void ComenzarJuego()
   {
	  Director.sharedDirector().attachInView(_VistaDelJuego);
	  PantallaDelDevice = Director.sharedDirector().displaySize();
	  Director.sharedDirector().runWithScene(EscenaDelJuego());
	  Log.d("aaaa", "Ancho: " + PantallaDelDevice.width + ". Alto: " + PantallaDelDevice.height);
   }
   
   private Scene EscenaDelJuego()
   {
	  
	  Scene EscenaADevolver = Scene.node();
	  
	  CapaDeFondo MiCapaDeFondo = new CapaDeFondo();
	  
	  CapaDeFrente MiCapaDeFrente = new CapaDeFrente();
	  
	  EscenaADevolver.addChild(MiCapaDeFondo, -10);
	  
	  EscenaADevolver.addChild(MiCapaDeFrente, 10);
	  
	  return EscenaADevolver;
   }
   
   class CapaDeFondo extends Layer
   {
	  public CapaDeFondo()
	  {
		 PonerImagenFondo();
	  }
	  
	  private void PonerImagenFondo()
	  {
		 ImagenFondo = Sprite.sprite("fondo.jpg");
		 ImagenFondo.setPosition(PantallaDelDevice.getWidth() / 2, PantallaDelDevice.getHeight() / 2);
		 
		 ImagenFondo.runAction(ScaleBy.action(0.01f, 2.0f, 2.0f));
		 //Se tiene que hacer de la siguiente forma (lo hice como estaba antes porque es más cheto):
		 /*FactorAncho = PantallaDelDevice.width / ImagenFondo.getWidth();
		 FactorAlto = PantallaDelDevice.height / ImagenFondo.getHeight();
		 ImagenFondo.runAction(ScaleBy.action(0.01f, FactorAncho, FactorAlto));*/
		 super.addChild(ImagenFondo);
	  }
   }
   
   class CapaDeFrente extends Layer
   {
	  public CapaDeFrente()
	  {
		 super.schedule("PonerAndroid", 3.0f);
		 PonerTituloJuego();
	  }
	  
	  public void PonerAndroid(float DiferenciaDeTiempo)
	  {
		 Androidcito = Sprite.sprite("android.png");
		 int PosicionX, PosicionY;
		 AlturaAndroidcito = Androidcito.getHeight();
		 AnchuraAndroidcito = Androidcito.getWidth();
		 
		 Random GeneradorDePosiciones = new Random();
		 
		 PosicionX = GeneradorDePosiciones.nextInt((int) (PantallaDelDevice.width - AnchuraAndroidcito));
		 PosicionX += AnchuraAndroidcito / 2;
		 PosicionY = GeneradorDePosiciones.nextInt((int) (PantallaDelDevice.height - AlturaAndroidcito));
		 PosicionY += AlturaAndroidcito / 2;
		 
		 Androidcito.setPosition(PosicionX, PosicionY);
		 Desplazamiento(PosicionX, PosicionY);
		 super.addChild(Androidcito);
	  }
	  
	  private void Desplazamiento(int PosicionX, int PosicionY)
	  {
		 Float PosicionFinalX, PosicionFinalY;
		 
		 if (PosicionX < PantallaDelDevice.width / 2)
		 {
			//Está en la parte izquierda
			if (PosicionY < PantallaDelDevice.height / 2)
			{
			   //Está en la parte izquierda de abajo
			   PosicionFinalX = PantallaDelDevice.width + AnchuraAndroidcito;
			   PosicionFinalY = PantallaDelDevice.height + AlturaAndroidcito;
			}
			else
			{
			   //Está en la parte izquierda de arriba
			   PosicionFinalX = PantallaDelDevice.width + AnchuraAndroidcito;
			   PosicionFinalY = (float) 0;
			}
		 }
		 else
		 {
			//Está en la parte derecha
			if (PosicionY < PantallaDelDevice.height / 2)
			{
			   //Está en la parte derecha de abajo
			   PosicionFinalX = (float) 0;
			   PosicionFinalY = PantallaDelDevice.height + AlturaAndroidcito;
			}
			else
			{
			   //Está en la parte derecha de arriba
			   PosicionFinalX = (float) 0 - AnchuraAndroidcito;
			   PosicionFinalY = (float) 0 - AlturaAndroidcito;
			}
		 }
		 Androidcito.runAction(MoveTo.action(1, PosicionFinalX, PosicionFinalY));
	  }
	  
	  private void PonerTituloJuego()
	  {
		 lblTituloJuego = Label.label("DIS GAME BRUH LIKE FORREAL THO", "Verdana", 30);
		 float AltoTitulo = lblTituloJuego.getHeight();
		 lblTituloJuego.setPosition(PantallaDelDevice.width / 2, PantallaDelDevice.height - AltoTitulo / 2);
		 //Cosas para usar en el futuro:
		 /*int PuntajeActual = 1;
		 CCColor3B ColorPuntaje =  new CCColor3B(128, 100, 200);
		 lblTituloJuego.setString("Puntaje: " + PuntajeActual);*/
		 super.addChild(lblTituloJuego);
	  }
   }
   
}
