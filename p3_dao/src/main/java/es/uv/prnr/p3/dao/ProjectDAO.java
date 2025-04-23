package es.uv.prnr.p3.dao;

import java.util.List;
import es.uv.prnr.p2.Project;

/**
 * Interfaz creada para el ejercicio 2.1
 * 
 * Interfaz DAO para la clase Project
 */
public interface ProjectDAO {
    public Project getProjectById(int id);

    public List<Project> getProjects();

    public void createProject(Project p);

    public void delete(Project p);

    public boolean deleteProjectById(int id);

    public List<Project> getByName(String name);

    void assignTeam(Project p, int startId, int endId);
}
