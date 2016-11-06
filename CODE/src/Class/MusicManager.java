package Class;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.example.jeuandroid.Option;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class MusicManager {
	//Instance de MusicManager, permet de n'avoir qu'une seul instance de cette classe
    private static MusicManager manager;
    //Repr�sente l'instance MediaPlayer permettant de lire les sons lourds
    private  MediaPlayer player;
    //Repr�sente l'instance SoundPool permettant de charger tout les petit sons et de les lire � la suite.
    private  SoundPool bruitage= new SoundPool(5, 3, 0);
    //L'index de tout les sons permettant de les trouvers � l'aide d'un string et non d'un int donner par la soundPool
    private  HashMap<String,Integer> index_bruitages= new HashMap<String,Integer>();
    //La musique en train d'�tre jouer.
    private  int music_playing;
    //Le volume actuelle
    private float volume_sound;
    //Fonction utiliser par la SoundPool, permet de charger un son
	public static final String  PREFS_NAME="MyPrefsFile";

    private MusicManager(Context context){
    	SharedPreferences settings=context.getSharedPreferences(PREFS_NAME,0);
    	volume_sound=(float)settings.getInt("volumePref", 100)/100;
    	
    }
    
    public void  loadBruit(Context ctx,int musique, String key){
    	index_bruitages.put(key, bruitage.load(ctx, musique, 1));
    
    }
    //Fonction utilis� par la soundPool, permet de lire un son
    public void playBruit(String key){
    	if(Option.bruit_mute==false){
    	int musique=index_bruitages.get(key);
    	bruitage.play(musique, volume_sound, volume_sound, 1, 0, 1);
    	}
    }
    public void unloadAll(){
    	Set<String> set= index_bruitages.keySet();
    	Iterator<String> ite=set.iterator();
    	while(ite.hasNext()){
    		bruitage.unload(index_bruitages.get(ite.next()));
    	}
    }
    //Fonction utilis� par la SoundPool, permet de d�charg� un son
    public void unLoadBruit(String key){
    	int musique=index_bruitages.get(key);
    	bruitage.unload(musique);
    	index_bruitages.remove(key);
    }
    //Permet de retourner le volume actuelle
    public float getVolume(){
    	return volume_sound;
    }
    //Permet d'attribuer une valeur au volume
    public void setVolume(int volume){
    	
    	volume_sound=(float)volume/100;
    	player.setVolume(volume_sound, volume_sound);
    }
    //Fonction utiliser par la SoundPool, permettant de mute tout les bruit.
    public void muteBruit(){
    	bruitage.autoPause();
    }
    //Fonction utilis� par le MediaPlayer, permettant de cr�er un nouveau lecteur avec une musique.
    public void SoundPlayer(Context ctx,int raw_id){
    		player= new MediaPlayer();
            player = MediaPlayer.create(ctx, raw_id);
            player.setLooping(true); 
            player.setVolume(volume_sound, volume_sound);
             music_playing=raw_id;
        }
    //Fonction utilis� par le MediaPlayer, permettant d'arr�ter un lecteur
    public void StopMusic(){
        player.stop();
    }
public boolean checkPlayer(){
	if(player==null){
		return false;
	}else{
		return true;
	}
}
    //Fonction utilis� par le MediaPlayer, permettant de relancer un lecteur avec une musique
    public void PlayMusic(Context ctx,int raw_id){
        player.reset();
        player = MediaPlayer.create(ctx, raw_id);
        player.setLooping(true); 
        player.setVolume(volume_sound, volume_sound);
        music_playing=raw_id;
}
    public void pauseMusic(){
    	if(Option.bruit_mute==false){
    	if(etatMusic()){
    		player.pause();
    	}else{
    		player.start();
    	}
    	}
    }
    //Retourne la musique jouer actuellement
    public int getMusic_Playing(){
    	return music_playing;
    }
    //Fonction utilis� par le MediaPlayer, permet de savoir si une musique est jou� ou pas
    public boolean etatMusic(){
    	Boolean etat=false;
         etat= player.isPlaying();
        return etat;
    }
    //Fonction permettant de retourner l'instance d�ja existante de MusicManager, ou d'en cr�er une si elle n'existe pas
    public static MusicManager getMusicManager(Context context){
    	if(manager==null){
    		manager=new MusicManager(context);
    	}
    	return manager;
    }
}