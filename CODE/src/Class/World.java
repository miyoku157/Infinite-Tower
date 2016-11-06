package Class;

import java.util.List;

public class World {
	private String type;
	private List<Niveau> collection_niveau;
	public World(String _type,List<Niveau> _collection){
		type=_type;
		collection_niveau=_collection;
	}
	public void addNiveau(Niveau lvl){
		collection_niveau.add(lvl);
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
	 * @return the collection_niveau
	 */
	public List<Niveau> getCollection_niveau() {
		return collection_niveau;
	}

	/**
	 * @param collection_niveau the collection_niveau to set
	 */
	public void setCollection_niveau(List<Niveau> collection_niveau) {
		this.collection_niveau = collection_niveau;
	}
}
