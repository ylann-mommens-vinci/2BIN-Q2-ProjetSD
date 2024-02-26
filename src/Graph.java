import java.io.File;

public class Graph {

  File cities;
  File roads;

  public Graph(File cities, File roads) {
    this.cities=cities;
    this.roads=roads;
  }

  /* Algorithme BFS
  Calcul l'itinéraire entre deux villes avec le moins de routes possibles
  S'il est impossible d'aller d'une ville à l'autre -> Exception
  */

  void calculerItineraireMinimisantNombreRoutes(String v1, String v2){

  }

  /* Algorithme de Dijkstra
  Calcul l'itinéraire entre deux villes avec le moins de kilomètres
  S'il est impossible d'aller d'une ville à l'autre -> Exception
  */
  void calculerItineraireMinimisantKm(String v1, String v2){

  }
}
