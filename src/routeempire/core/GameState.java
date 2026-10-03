package routeempire.core;

import routeempire.model.City;
import routeempire.model.Economy;
import routeempire.model.Garage;
import routeempire.model.Stop;
import routeempire.model.bridge.Bridge;
import routeempire.model.facility.Facility;
import routeempire.model.map.GameMap;
import routeempire.model.map.Position;
import routeempire.model.vehicle.Vehicle;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds all game data in one place.
 * No game logic, no rendering — just data and simple queries.
 */
public class GameState {

    private final GameMap map;
    private final List<City> cities;
    private final List<Facility> facilities;
    private final List<Vehicle> vehicles;
    private final List<Stop> stops;
    private final List<Garage> garages;
    private final List<Bridge> bridges;
    private final Economy economy;
    private final GameCalendar calendar;

    public GameState(GameMap map) {
        this.map = map;
        this.cities = new ArrayList<>();
        this.facilities = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.stops = new ArrayList<>();
        this.garages = new ArrayList<>();
        this.bridges = new ArrayList<>();
        this.economy = new Economy();
        this.calendar = new GameCalendar();
    }

    // --- Getters ---

    public GameMap getMap() { return map; }
    public Economy getEconomy() { return economy; }
    public GameCalendar getCalendar() { return calendar; }
    public List<City> getCities() { return cities; }
    public List<Facility> getFacilities() { return facilities; }
    public List<Vehicle> getVehicles() { return vehicles; }
    public List<Stop> getStops() { return stops; }
    public List<Garage> getGarages() { return garages; }
    public List<Bridge> getBridges() { return bridges; }

    // --- Add / Remove ---

    public void addCity(City city) { cities.add(city); }
    public void addFacility(Facility facility) { facilities.add(facility); }
    public void addVehicle(Vehicle vehicle) { vehicles.add(vehicle); }
    public void removeVehicle(Vehicle vehicle) { vehicles.remove(vehicle); }
    public void addStop(Stop stop) { stops.add(stop); }
    public void removeStop(Stop stop) { stops.remove(stop); }
    public void addGarage(Garage garage) { garages.add(garage); }
    public void removeGarage(Garage garage) { garages.remove(garage); }
    public void addBridge(Bridge bridge) { bridges.add(bridge); }
    public void removeBridge(Bridge bridge) { bridges.remove(bridge); }

    // --- Queries ---

    /**
     * Find all stops adjacent to a facility (manhattan distance == 1 from any facility tile).
     */
    public List<Stop> getLinkedStops(Facility facility) {
        List<Position> facilityTiles = facility.getOccupiedTiles();
        List<Stop> linked = new ArrayList<>();

        for (Stop stop : stops) {
            Position stopPos = stop.getPosition();
            for (Position ft : facilityTiles) {
                if (ft.manhattanDistance(stopPos) == 1) {
                    linked.add(stop);
                    break; // don't add same stop twice
                }
            }
        }
        return linked;
    }

    /**
     * Find all stops inside or adjacent to a city.
     */
    public List<Stop> getLinkedStops(City city) {
        List<Stop> linked = new ArrayList<>();

        for (Stop stop : stops) {
            // Stop is on a city road tile
            if (city.containsTile(stop.getPosition())) {
                linked.add(stop);
                continue;
            }
            // Stop is adjacent to any city tile
            List<Position> cityTiles = city.getAllTiles();
            for (Position ct : cityTiles) {
                if (ct.manhattanDistance(stop.getPosition()) == 1) {
                    linked.add(stop);
                    break;
                }
            }
        }
        return linked;
    }

    /**
     * Find all vehicles assigned to a garage.
     */
    public List<Vehicle> getVehiclesForGarage(Garage garage) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getAssignedGarage() == garage) {
                result.add(v);
            }
        }
        return result;
    }

    /**
     * Find the facility at a given position (if any).
     */
    public Facility getFacilityAt(Position pos) {
        for (Facility f : facilities) {
            if (f.getOccupiedTiles().contains(pos)) {
                return f;
            }
        }
        return null;
    }

    /**
     * Find the city that contains a given position (if any).
     */
    public City getCityAt(Position pos) {
        for (City c : cities) {
            if (c.containsTile(pos)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Find the stop at a given position (if any).
     */
    public Stop getStopAt(Position pos) {
        for (Stop s : stops) {
            if (s.getPosition().equals(pos)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Find the garage at a given position (if any).
     */
    public Garage getGarageAt(Position pos) {
        for (Garage g : garages) {
            if (g.getPosition().equals(pos)) {
                return g;
            }
        }
        return null;
    }
}