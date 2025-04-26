package es.uv.prnr.p3.dao;

public abstract class DAOFactory {

	public enum TYPE {
		JPA, XML
	}

	public abstract ProjectDAO getProjectDAO();

	public static DAOFactory getDAOFactory(TYPE t) {
		switch (t) {
			case JPA:
				return new JPADAOFactory();
			/*
			 * case XML:
			 * return new XMLDAOFactory();
			 */
			default:
				break;
		}
		return null;
	}

}
