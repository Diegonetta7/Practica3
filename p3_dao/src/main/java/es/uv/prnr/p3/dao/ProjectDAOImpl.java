package es.uv.prnr.p3.dao;

import java.util.List;
import javax.persistence.EntityManager;
import es.uv.prnr.p2.*;

/**
 * Clase creada para el ejercicio 2.1
 * 
 * Clase que implementa el DAO de Project
 */
public class ProjectDAOImpl extends DAOImpl<Integer, Project>
        implements ProjectDAO {

    public ProjectDAOImpl(EntityManager em) {
        super(em, Project.class);
    }

    public Project getProjectById(int id) {
        return this.getById(id);
    }

    public List<Project> getProjects() {
        return this.findAll();
    }

    public void createProject(Project p) {
        this.create(p);
    }

    public void delete(Project p) {
        this.delete(p);
    }

    public boolean deleteProjectById(int id) {
        return this.deleteById(id);
    }

    public List<Project> getByName(String name) {
        return this.findByCriteria("e.name = '" + name + "'");
    }

    public void assignTeam(Project p, int startId, int endId){
        ProjectService service = new ProjectService();
        service.assignTeam(p, startId, endId);
    }
}
