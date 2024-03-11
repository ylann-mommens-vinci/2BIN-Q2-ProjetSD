import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  // On utilise une map pour associer l'id de la ville à la ville
  private final Map<String, City> idToCity = new HashMap<>();

  // On utilise une map pour associer le nom de la ville à la ville
  private final Map<String, City> nameToCity = new HashMap<>();

  // On utilise une map pour associer une ville à un ensemble de routes (liste d'adjacence)
  private final Map<City, Set<Road>> outputRoad = new HashMap<>();

  public Graph(File cities, File roads) {
    try(Scanner sc = new Scanner(cities)) {
      while(sc.hasNextLine()) {
        String[] line = sc.nextLine().split(",");
        City newCity = new City(line[0], line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3]));

        idToCity.put(newCity.getId(), newCity);
        nameToCity.put(newCity.getName(), newCity);
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

  void calculerItineraireMinimisantNombreRoutes(String depart, String arrivee){
    // Itinéraire avec les villes dans le bon ordre
    Deque<City> itineraire = new ArrayDeque<>();

    // File pour le parcours en largeur (file BFS)
    Deque<City> file = new ArrayDeque<>();
    // Map qui stocke la relation entre une ville et sa ville parente
    Map<City, City> parentMap = new HashMap<>();


    // On récupère les villes de départ et d'arrivée
    City departCity = nameToCity.get(depart);
    City arriveeCity = nameToCity.get(arrivee);

    file.add(departCity);

    // Parcours en largeur (BFS)
    while (!file.isEmpty()) {
      //TODO: On récupère la première ville de la file
      City currentCity = file.pollFirst();

      // TODO: Si la ville est déjà visitée, on passe à la suivante

      // TODO: Ajout de la ville à la liste des villes visitées (pour éviter les boucles infinies)

      // Si la ville est la ville d'arrivée, on a trouvé l'itinéraire
      if(currentCity.getId().equals(arriveeCity.getId())) {

        // Construction de l'itinéraire en remontant le chemin
        while (currentCity != null) {
          itineraire.addFirst(currentCity);
          currentCity = parentMap.get(currentCity);
        }
        break;
      }
    }

    // Si on n'a pas trouvé d'itinéraire, on lève une exception
    if(itineraire.isEmpty())
      throw new RuntimeException("Impossible d'aller de " + depart + " à " + arrivee);

    // on affiche l'itinéraire
    itineraire.forEach(c -> System.out.println(c.getName()));
  }

  /* Algorithme de Dijkstra
  Calcul l'itinéraire entre deux villes avec le moins de kilomètres
  S'il est impossible d'aller d'une ville à l'autre -> Exception
  */
  void calculerItineraireMinimisantKm(String v1, String v2){

  }
}
