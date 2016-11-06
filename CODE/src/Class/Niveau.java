package Class;

import java.util.List;

import Decor.Decor;

public class Niveau {
	private int highscore;
	private boolean finished;
	private String type;
	private int num_lvl;
	private List<Decor> collection_decor;
	public Niveau(int _highscore,boolean _finished,String _type,int _num_lvl){
		highscore=_highscore;
		finished=_finished;
		type=_type;
		num_lvl=_num_lvl;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the highscore
	 */
	public int getHighscore() {
		return highscore;
	}
	/**
	 * @param highscore the highscore to set
	 */
	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}
	/**
	 * @return the finished
	 */
	public boolean isFinished() {
		return finished;
	}
	/**
	 * @param finished the finished to set
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	/**
	 * @return the collection_decor
	 */
	public List<Decor> getCollection_decor() {
		return collection_decor;
	}
	/**
	 * @param collection_decor the collection_decor to set
	 */
	public void setCollection_decor(List<Decor> collection_decor) {
		this.collection_decor = collection_decor;
	}
	/**
	 * @return the num_lvl
	 */
	public int getNum_lvl() {
		return num_lvl;
	}
	/**
	 * @param num_lvl the num_lvl to set
	 */
	public void setNum_lvl(int num_lvl) {
		this.num_lvl = num_lvl;
	}

}
