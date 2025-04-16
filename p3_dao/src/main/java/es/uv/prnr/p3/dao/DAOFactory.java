package es.uv.prnr.p3.dao;

public abstract class DAOFactory {
	
	public enum TYPE {JPA,XML}
	
	//TODO Ejercicio 2: Añadir la recuperación de una clase DAO para la entidad Project
	
	
	public static DAOFactory getDAOFactory(TYPE t){
		switch(t){
			case JPA:
				return new JPADAOFactory();
			/*case XML:
				return new XMLDAOFactory();*/
			default:
				break;
		}
		return null;
	}

}
