package zioncosta.trabajopractico6;

import android.nfc.tech.NfcA;
import android.provider.Settings;
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
   Sprite NaveJugador;
   Sprite NaveEnemiga;
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
		 super.schedule("PonerNaveEnemiga", 2.0f);
	   
		 PonerEnPosicionInicial();
	   
		 PonerTituloJuego();
	   
	  }
	  
	  private void PonerEnPosicionInicial()
	  {
		 NaveJugador = Sprite.sprite("jugador.png");
		 float PosicionInicialX, PosicionInicialY;
		 PosicionInicialX = PantallaDelDevice.width / 2;
		 PosicionInicialY = NaveJugador.getHeight() / 2;
		 NaveJugador.setPosition(PosicionInicialX, PosicionInicialY);
		 super.addChild(NaveJugador);
	  }
	  
	  public void PonerNaveEnemiga(float DiferenciaDeTiempo)
	  {
		 NaveEnemiga = Sprite.sprite("zurg.png");
		 int PosicionInicialX, PosicionInicialY;
		 Float AlturaEnemigo = NaveEnemiga.getHeight();
		 Float AnchuraEnemigo = NaveEnemiga.getWidth();
		 
		 PosicionInicialY = (int)(PantallaDelDevice.height + AlturaEnemigo / 2);
		 
		 Random GeneradorDePosiciones = new Random();
		 PosicionInicialX = GeneradorDePosiciones.nextInt((int) (PantallaDelDevice.width - AnchuraEnemigo));
		 PosicionInicialX += AnchuraEnemigo / 2;
		 
		 NaveEnemiga.setPosition(PosicionInicialX, PosicionInicialY);
		 NaveEnemiga.runAction(RotateTo.action(0.01f, 180));
		 
		 int PosicionFinalX, PosicionFinalY;
		 PosicionFinalX = PosicionInicialX;
		 PosicionFinalY =(int)  - AlturaEnemigo / 2;
		 
		 NaveEnemiga.runAction(MoveTo.action(3, PosicionFinalX, PosicionFinalY));
		 super.addChild(NaveEnemiga);
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
