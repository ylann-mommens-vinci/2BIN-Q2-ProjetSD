public class Road {

  private final City source;
  private final City destination;

  public Road(City source, City destination) {
    this.source = source;
    this.destination = destination;
  }

  public City getSource() {
    return source;
  }

  public City getDestination() {
    return destination;
  }

}
