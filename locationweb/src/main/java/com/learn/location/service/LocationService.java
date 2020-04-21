package com.learn.location.service;

import java.util.List;
import java.util.Optional;

import com.learn.location.entities.Location;

public interface LocationService {
	
	Location saveLocation(Location location);
	Location updateLocation(Location location);
	void deleteLocation(Location location);
	Location getLocationById(int id);
	List<Location> getAllLocation();

}