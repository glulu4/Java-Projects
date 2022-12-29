import java.util.*;
import java.io.*;

public class AirlineSystem implements AirlineInterface {
  private static String[] cityNames;
  private Stack<String> cityStack;
  private Digraph G = null;
  private static Scanner scan = null;


  public boolean loadRoutes(String fileName){
    Scanner fileScan;
    try {
      fileScan = new Scanner(new FileInputStream(fileName));
    } catch(Exception e) {
      fileScan = new Scanner(System.in);
    }
    int v = Integer.parseInt(fileScan.nextLine());
    G = new Digraph(v);

    cityNames = new String[v];
    cityStack = new Stack<String>();
    for(int i=0; i<v; i++){
      String city = fileScan.next();
      cityStack.add(city);
      cityNames[i] = city;
      System.out.println(city);
      fileScan.nextLine();
    }

    while(fileScan.hasNext()){
      int from = fileScan.nextInt();
      int to = fileScan.nextInt();
      int weight = fileScan.nextInt();
      double cost = fileScan.nextDouble();

      G.addEdge(new WeightedDirectedEdge(from-1, to-1, weight, cost));
      G.addEdge(new WeightedDirectedEdge(to-1, from-1, weight, cost));

      //fileScan.nextLine();
    }
    fileScan.close();
    return true;

  }




  public Set<String> retrieveCityNames(){
    Set<String> citiesSet = new HashSet<String>();
    for ( String city: cityNames)
      citiesSet.add(city);
    return citiesSet;
  }

  public Set<Route> retrieveDirectRoutesFrom(String city) throws CityNotFoundException {
    Set<Route> directRoutes = new HashSet<Route>();

    // finding index of
    int indexOfCity = cityStack.indexOf(city); // finding elemnt of
    // city not found!
    if ( indexOfCity == -1 )
      throw new CityNotFoundException("City is not found");


    for (WeightedDirectedEdge edge : G.adj(indexOfCity)) {
      // converting WeightedDirectedEdge to a route!
      String source = cityNames[edge.v];
      String destination = cityNames[edge.w];
      int distance = edge.weight;
      double cost = edge.cost;

      Route route = new Route(source, destination, distance, cost );
      directRoutes.add(route);
    }
    return directRoutes;
  }

  // hops
  public Set<ArrayList<String>> fewestStopsItinerary(String source, String destination) throws CityNotFoundException{
      Set<ArrayList<String>> fewestStops = new HashSet<ArrayList<String>>();
      ArrayList<String> shortestPath = new ArrayList<>();

      int sourceNum = cityStack.indexOf(source);
      int destinationNum = cityStack.indexOf(destination);

      if ( sourceNum == -1 || destinationNum == -1 ) throw new CityNotFoundException("City is not found");

      shortestPath.add(cityNames[sourceNum]);
      G.bfs(sourceNum);
      if(!G.marked[destinationNum]){
        shortestPath.add(cityNames[destinationNum]);
      }
      else {
        Stack<Integer> path = new Stack<>();
        for (int x = destinationNum; x != sourceNum; x = G.edgeTo[x]){
            path.push(x);
        }
        int prevVertex = sourceNum;
        while(!path.empty()){
          int v = path.pop();
          shortestPath.add(cityNames[v]);

          prevVertex = v;
        }

      }
    fewestStops.add(shortestPath);
    return fewestStops;
  }
  // miles
  public Set<ArrayList<Route>> shortestDistanceItinerary(String source, String destination) throws CityNotFoundException{
      Set<ArrayList<Route>> shortestDistance = new HashSet<ArrayList<Route>>();
      ArrayList<Route> shortestRoute = new ArrayList<>();
      int sourceNum = cityStack.indexOf(source);
      int destinationNum = cityStack.indexOf(destination);

      if ( sourceNum == -1 || destinationNum == -1 ) throw new CityNotFoundException("City is not found");



      G.dijkstras(sourceNum, destinationNum);
      if(!G.marked[destinationNum]){
        // add end
      }
      else {
        Stack<Integer> path = new Stack<>();
        for (int x = cityStack.indexOf(destination); x != cityStack.indexOf(source); x = G.edgeTo[x]){
            path.push(x);
        }

        int prevVertex = sourceNum;
        while(!path.empty()){
          int v = path.pop();

          for (WeightedDirectedEdge edge : G.adj(prevVertex)){
            if ( v == edge.to()){
              String sourceCity = cityNames[edge.v];
              String destinationCity = cityNames[edge.w];


              int distance = edge.weight;
              double cost = edge.cost;

              Route route = new Route(sourceCity, destinationCity, distance, cost );
              shortestRoute.add(route);
            }
          }
          prevVertex = v;
        }

      }
    shortestDistance.add(shortestRoute);
    return shortestDistance;

  }

  public Set<ArrayList<Route>> cheapestItinerary(String source, String destination) throws CityNotFoundException{
    Set<ArrayList<Route>> shortestDistance = new HashSet<ArrayList<Route>>();
    ArrayList<Route> shortestRoute = new ArrayList<>();
    int sourceNum = cityStack.indexOf(source);
    int destinationNum = cityStack.indexOf(destination);

    if ( sourceNum == -1 || destinationNum == -1 ) throw new CityNotFoundException("City is not found");



    G.dijkstrasForCost(sourceNum, destinationNum);
    if(!G.marked[destinationNum]){
      // add end
    }
    else {
      Stack<Integer> path = new Stack<>();
      for (int x = cityStack.indexOf(destination); x != cityStack.indexOf(source); x = G.edgeTo[x]){
          path.push(x);
      }

      int prevVertex = sourceNum;
      while(!path.empty()){
        int v = path.pop();

        for (WeightedDirectedEdge edge : G.adj(prevVertex)){
          if ( v == edge.to()){
            String sourceCity = cityNames[edge.v];
            String destinationCity = cityNames[edge.w];


            int distance = edge.weight;
            double cost = edge.cost;

            Route route = new Route(sourceCity, destinationCity, distance, cost );
            shortestRoute.add(route);
          }
        }
        prevVertex = v;
      }

    }
  shortestDistance.add(shortestRoute);
  return shortestDistance;

  }

  @SuppressWarnings("unchecked")
  public Set<ArrayList<Route>> cheapestItinerary(String source, String transit, String destination) throws CityNotFoundException{
    Set<ArrayList<Route>> cheapestItinerary = new HashSet<ArrayList<Route>>();
    ArrayList<Route> cheapest = new ArrayList<>();
    int sourceNum = cityStack.indexOf(source);
    int transitNum = cityStack.indexOf(transit);
    int destinationNum = cityStack.indexOf(destination);

    if ( sourceNum == -1 || transitNum == -1 || destinationNum == -1) throw new CityNotFoundException("City not found");

    Set<ArrayList<Route>> src2Transit = cheapestItinerary(source,transit);
    Set<ArrayList<Route>> transit2Dst = cheapestItinerary(transit,destination);

    ArrayList<Route>[] src2TransitItems = src2Transit.toArray( new ArrayList[src2Transit.size()]);
    ArrayList<Route>[] transit2DstItems = transit2Dst.toArray( new ArrayList[src2Transit.size()]);

    ArrayList<Route> sumOfRoutes = new ArrayList<>();

    for ( int i = 0; i < src2TransitItems.length; i++){
      for ( int j = 0; j < transit2DstItems.length; j++){
        for ( Route r :src2TransitItems[i]){
          sumOfRoutes.add(r);
        }
        for ( Route r :transit2DstItems[j]){
          sumOfRoutes.add(r);
        }
        cheapestItinerary.add(sumOfRoutes);
        sumOfRoutes = new ArrayList<>();
      }

    }
    return cheapestItinerary;

  }

  public Set<Set<Route>> getMSTs(){ // in textbook
    Set<Set<Route>> mst = new HashSet<Set<Route>>();
    Set<Route> route = new HashSet<>();

    G.getMSTs();

    for (int i = 1; i < G.edgeTo.length; i++)
      route.add(new Route(cityNames[i], cityNames[G.edgeTo[i]], G.distTo[i], G.costOf[i])); // just realized i can use the arrays in teh digrapg class :(

    mst.add(route);
    return mst;
  }

  public Set<ArrayList<Route>> tripsWithin(String city, double budget) throws CityNotFoundException{

    Set<ArrayList<Route>> trips = new HashSet<>();

    int cityNum = cityStack.indexOf(city);
    if (cityNum == - 1) throw new CityNotFoundException("City is not found");

    // city num is passed for source, 0 for destination
    G.dfsPaths(cityNum, 0, trips, budget);
    return trips;

  }

  @SuppressWarnings("unchecked")
  public Set<ArrayList<Route>> tripsWithin(double budget){

    Set<ArrayList<Route>> result = new HashSet<>();
    Set<ArrayList<Route>> trip =  new HashSet<ArrayList<Route>>();
    ArrayList<Route> res = new ArrayList<>();

    for ( String city : cityNames ){
      try {
        trip = tripsWithin(city, budget);
      } catch(CityNotFoundException e) {}

        for ( ArrayList<Route> r : trip )
          result.add(r);

      // ArrayList<Route>[] tripArray = trip.toArray( new ArrayList[trip.size()]);
      // for ( int i = 0; i < tripArray.length; i++){
      //   for ( Route r: tripArray[i]){
      //     res.add(r);
      //   }
      //   result.add(res);
      //   res = new ArrayList<>();
      // }
    }

    return result;
  }





  private class Digraph {
    static final int INFINITY = Integer.MAX_VALUE;
    final int v;
    int e;
    LinkedList<WeightedDirectedEdge>[] adj;
    boolean[] marked;  // marked[v] = is there an s-v path
    int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    int[] distTo;      // distTo[v] = number of edges shortest s-v path
    double[] costOf;


    /**
    * Create an empty digraph with v vertices.
    */
    public Digraph(int v) {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      @SuppressWarnings("unchecked")
      LinkedList<WeightedDirectedEdge>[] temp =
      (LinkedList<WeightedDirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        adj[i] = new LinkedList<WeightedDirectedEdge>();
    }

    /**
    * Add the edge e to this digraph.
    */
    public void addEdge(WeightedDirectedEdge edge) {
      int from = edge.from();
      adj[from].add(edge);
      e++;
    }


    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (WeightedDirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<WeightedDirectedEdge> adj(int v) {
      return adj[v];
    }

    public void bfs(int source) {
      marked = new boolean[this.v];
      distTo = new int[this.e];
      edgeTo = new int[this.v];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) {
        int v = q.remove();
        for (WeightedDirectedEdge w : adj(v)) {
          if (!marked[w.to()]) {
            edgeTo[w.to()] = v;
            distTo[w.to()] = distTo[v] + 1;
            marked[w.to()] = true;
            q.add(w.to());
          }
        }
      }
    }
    public void dijkstrasForCost( int source, int destination){
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      costOf = new double[this.v];


      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
        costOf[i] = 100000000.00;
      }
      costOf[source] = 0.00;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) {
          if (costOf[current]+w.cost() < costOf[w.to()]) { // checking neghbors with minimum num

            edgeTo[ w.to() ] = current; // updating parent

            costOf[w.to()] = costOf[current]+ w.cost(); // setting it equal to the lesser distance!
             //TODO:update edgeTo and distTo

          }
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        double min = 10000000.00; //
        current = -1;

        // finds next node to visit by finding minimum path distnace; min distTo value
        for(int i=0; i<costOf.length; i++){
          if(marked[i])
            continue;
          if(costOf[i] < min){
            min = costOf[i];
            current = i;
          }
        }
        if ( current == -1 )
          break;
        //TODO: Update marked[] and nMarked. Check for disconnected graph.
        marked[current] = true; // marking it as visited
        nMarked++; // incrementing the number of visited nodes by 1
        // if current is -1, that means graph has a disconnected part and we already searched it
      }
    }

    public void dijkstras(int source, int destination) {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      costOf = new double[this.v];


      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
        costOf[i] = 0.00;
      }
      distTo[source] = 0;
      marked[source] = true;

      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) {
          if (distTo[current]+w.weight() < distTo[w.to()]) { // checking neghbors with minimum num
            edgeTo[ w.to() ] = current; // updating parent
            distTo[w.to()] = distTo[current]+ w.weight(); // setting it equal to the lesser distance!
            //TODO:update edgeTo and distTo

          }

        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        int min = INFINITY;
        current = -1;
        // finds next node to visit by finding minimum path distnace; min distTo value
        for(int i=0; i<distTo.length; i++){
          if(marked[i])
            continue;
          if(distTo[i] < min){
            min = distTo[i];
            current = i;
          }
        }
        if ( current == -1 )
          break;
        //TODO: Update marked[] and nMarked. Check for disconnected graph.
        marked[current] = true; // marking it as visited
        nMarked++; // incrementing the number of visited nodes by 1
        // if current is -1, that means graph has a disconnected part and we already searched it

      }
    }

    public void getMSTs(){

      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      costOf = new double[this.v];

      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
        costOf[i] = INFINITY;
      }
      distTo[0] = 0;
      marked[0] = true;
      costOf[0] = 0.0;
      int nMarked = 1;

      int current = 0; // instead of source, just start from first city

      while (nMarked < this.v) {
        for (WeightedDirectedEdge edge : adj(current)) {
          if (marked[edge.to()] == false ){
            if (edge.weight() < distTo[edge.to()]){
              distTo[edge.to()] = edge.weight();
              costOf[edge.to()] = edge.cost();
              edgeTo[edge.to()] = current;
            }
          }
        }
            //Find the vertex with minimim path distance
      int min = INFINITY;
      current = -1;

      for(int i=0; i<distTo.length; i++){
        if(marked[i]) continue;

        if(distTo[i] < min){
          min = distTo[i];
          current = i;
        }
      }
      if (current == -1) break;
        marked[current] = true;
        nMarked++;
      }
    }


    public void dfsPaths(int source, int destination, Set<ArrayList<Route>> trips, double budget){

      ArrayList<Route> currTrip = new ArrayList<Route>();
      double currentPrice = 0;
      marked = new boolean[this.v];
      marked[source] = true;

      dfsPathsHelper(source, trips, budget, currTrip, marked, currentPrice  );

    }



    public void dfsPathsHelper( int currentCity, Set<ArrayList<Route>> trips, double budget,
      ArrayList<Route> currentRoute, boolean[] marked, double currentPrice){

      if (currentPrice > budget ) return;

      else
        if( currentRoute.size() != 0 )
          trips.add(new ArrayList<Route>(currentRoute));

      marked[currentCity] = true; 
      for (WeightedDirectedEdge w : adj(currentCity)) {
        if (!marked[w.to()]) {
          int nextCity = w.to(); // gets next city
          currentRoute.add(new Route(cityNames[currentCity], cityNames[nextCity], w.weight, w.cost));

          if(currentPrice + w.cost <= budget)
            trips.add(new ArrayList<Route>(currentRoute));

          dfsPathsHelper(nextCity, trips, budget, currentRoute, marked, currentPrice + w.cost);
          currentRoute.remove(currentRoute.size()-1);


        }
      }

      marked[currentCity] = false;
    }

  }

  /**
  *  The <tt>WeightedDirectedEdge</tt> class represents a weighted edge in an directed graph.
  */

  private class WeightedDirectedEdge {
    final int v;
    final int w;
    int weight;
    double cost;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedDirectedEdge(int v, int w, int weight, double cost) {
      this.v = v;
      this.w = w;
      this.weight = weight;
      this.cost = cost;
    }
    public double cost(){
      return cost;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }

    public int weight(){
      return weight;
    }
  }
} // end of AirlineSystem class
