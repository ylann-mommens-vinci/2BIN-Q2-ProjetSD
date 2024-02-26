import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Graph {

  private Map<String, City> idToCity = new HashMap<>();

  public Graph(File cities, File roads) {
    try(Scanner sc = new Scanner(cities)) {
      while(sc.hasNextLine()) {
        String[] line = sc.nextLine().split(",");
        idToCity.put(line[0], new City(line[0], line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3])));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }



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
