import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {

  private final Map<String, City> citiesByName;
  private final Map<Integer, City> citiesById;
  private final Map<City, Set<Road>> adjacencyList;

  public Graph(File citiesFile, File roadsFile) {
    citiesByName = new HashMap<>();
    citiesById = new HashMap<>();
    adjacencyList = new HashMap<>();
    readCities(citiesFile);
    readRoads(roadsFile);
  }

  private void readCities(File file) {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1].trim();
        double longitude = Double.parseDouble(parts[2]);
        double latitude = Double.parseDouble(parts[3]);
        City city = new City(id, name, longitude, latitude);
        citiesByName.put(name, city);
        citiesById.put(id, city);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readRoads(File file) {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split(",");
        int cityId1 = Integer.parseInt(parts[0]);
        int cityId2 = Integer.parseInt(parts[1]);
        City city1 = citiesById.get(cityId1);
        City city2 = citiesById.get(cityId2);

        if (city1 != null && city2 != null) {
          Road road = new Road(city1, city2);
          adjacencyList.computeIfAbsent(city1, k -> new HashSet<>()).add(road);
          adjacencyList.computeIfAbsent(city2, k -> new HashSet<>()).add(new Road(city2, city1));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void calculerItineraireMinimisantNombreRoutes(String start, String end) {
    if (!citiesByName.containsKey(start) || !citiesByName.containsKey(end)) {
      throw new IllegalArgumentException("L'une des villes spécifiées n'existe pas.");
    }

    City startCity = citiesByName.get(start);
    City endCity = citiesByName.get(end);
    Queue<City> queue = new LinkedList<>();
    Map<City, City> predecessors = new HashMap<>();
    Set<City> visited = new HashSet<>();
    queue.add(startCity);
    visited.add(startCity);
    predecessors.put(startCity, null);

    while (!queue.isEmpty()) {
      City current = queue.poll();
      if (current.equals(endCity)) {
        printPath(predecessors, endCity);
        return;
      }
      for (Road road : adjacencyList.getOrDefault(current, Collections.emptySet())) {
        City neighbor = road.getDestination();
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          predecessors.put(neighbor, current);
          queue.add(neighbor);
        }
      }

    }
    System.out.println("Il est impossible de trouver un itinéraire entre " + start + " et " + end);
  }

  public void calculerItineraireMinimisantKm(String start, String end) {
    if (!citiesByName.containsKey(start) || !citiesByName.containsKey(end)) {
      throw new IllegalArgumentException("L'une des villes spécifiées n'existe pas.");
    }

    City startCity = citiesByName.get(start);
    City endCity = citiesByName.get(end);
    Map<City, Double> distances = new HashMap<>();
    Map<City, City> predecessors = new HashMap<>();
    TreeMap<City, Double> treeMap = new TreeMap<>(Comparator.comparing(distances::get));
    citiesByName.values().forEach(city -> distances.put(city, Double.MAX_VALUE));
    distances.put(startCity, 0.0);
    treeMap.put(startCity, 0.0);

    while (!treeMap.isEmpty()) {
      City current = treeMap.pollFirstEntry().getKey();
      if (current.equals(endCity)) {
        printPath(predecessors, endCity);
        return;
      }
      double currentDistance = distances.get(current);
      for (Road road : adjacencyList.getOrDefault(current, Collections.emptySet())) {
        City neighbor = road.getDestination();
        double distance = calculateDistance(current, neighbor) + currentDistance;
        if (distance < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
          distances.put(neighbor, distance);
          predecessors.put(neighbor, current);
          treeMap.put(neighbor, distance);
        }
      }
    }
    System.out.println("Il est impossible de trouver un itinéraire entre " + start + " et " + end);
  }

  private void printPath(Map<City, City> predecessors, City endCity) {
    LinkedList<Road> path = new LinkedList<>();
    double totalDistance = 0.0;

    for (City at = endCity; predecessors.get(at) != null; at = predecessors.get(at)) {
      City prevCity = predecessors.get(at);
      Set<Road> roads = adjacencyList.get(prevCity);
      for (Road road : roads) {
        if (road.getDestination().equals(at)) {
          path.addFirst(road);
          totalDistance += calculateDistance(road.getSource(), road.getDestination());
          break;
        }
      }
    }

    System.out.println("Itinéraire de " + path.getFirst().getSource().getName() +
        " à " + endCity.getName() + ": " + path.size() + " routes et " +
        totalDistance + " km");

    for (Road road : path) {
      System.out.println(road.getSource().getName() + " -> " +
          road.getDestination().getName() + " (" + calculateDistance(road.getSource(), road.getDestination()) + " km)");
    }
  }

  private double calculateDistance(City from, City to) {
    return Util.distance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
  }

}