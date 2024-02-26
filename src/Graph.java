import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  private Map<String, City> idToCity = new HashMap<>();
  private  Map<City, Set<Road>> outputRoad = new HashMap<>();

  public Graph(File cities, File roads) {
    try(Scanner sc = new Scanner(cities)) {
      while(sc.hasNextLine()) {
        String[] line = sc.nextLine().split(",");
        idToCity.put(line[0], new City(line[0], line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3])));
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    try(Scanner scanner=new Scanner(roads)){
      // On parcourt le fichier roads
      while (scanner.hasNextLine()){
        String[] line = scanner.nextLine().split(",");
        // On récupère les villes de départ
        City source = idToCity.get(line[0]);
        City destination = idToCity.get(line[1]);

        //On crée une nouvelle route
        Road road = new Road(source, destination);

        //Si ville est déja dans la map
        if (outputRoad.containsKey(source)) {
          //Si la route n'est pas déja dans la map
          Set<Road> roadSet = outputRoad.get(source);
          if(!roadSet.contains(road)) {
            //On ajoute la route à la ville
            roadSet.add(road);
            outputRoad.put(source, roadSet);
          }
        }else {
          //On ajoute la ville dans la map & On ajoute la route à la ville
          Set<Road> roadSet = new HashSet<>();
          roadSet.add(road);
          outputRoad.put(source, roadSet);
        }
      }
    }catch (FileNotFoundException e){
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
