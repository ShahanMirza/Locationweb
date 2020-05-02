package com.learn.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

import com.learn.location.entities.Location;
import com.learn.location.service.LocationService;
import com.learn.location.util.EmailUtilImpl;

@Controller
public class LocationController {

	@Autowired
	LocationService locationService;
//	@Autowired(required=true)
//	EmailUtilImpl emiailUtil;
	
	@RequestMapping("/showCreate")
	public String showCreate()
	{
		return "createLocation";
	}
	
	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location") Location location,ModelMap modelMap)
	{
		Location locationSaved 	=locationService.saveLocation(location);
		String msg="Location saved with id:"+locationSaved.getId();
		modelMap.addAttribute("msg", msg);
//		emiailUtil.sendEmail("ramzan.ali408055@gmail.com", "test", "testing testing");
		return "createLocation";
	}
	
	@RequestMapping(value="/displayLocations", method = RequestMethod.GET)
	
	public String displayLocations(ModelMap modelMap)
	{
		List<Location> locations=locationService.getAllLocation();
		System.out.println(locations.size()); 
		System.out.println(modelMap);
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	@RequestMapping("deleteLocation")
	public String deleteLocation(@RequestParam("id") int id,ModelMap modelMap)
	{
//		Location location= locationService.getLocationById(id);
		Location location=new Location();
		location.setId(id);
		locationService.deleteLocation(location);
		List<Location> locations=locationService.getAllLocation();
		modelMap.addAttribute("locations", locations);
		return "displayLocations";
	}
	
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("id") int id,ModelMap modelMap)
	{
		Location location = locationService.getLocationById(id);
		System.out.println(location);
		modelMap.addAttribute("location", location);
		return "updateLocation";
	}
	
	@RequestMapping("/updateLoc")
	public String updateLocation(@ModelAttribute("location") Location location,ModelMap modelMap)
	{
		locationService.updateLocation(location);
		List<Location> locations = locationService.getAllLocation();
		modelMap.addAttribute("location", locations);
		return "displayLocations";
	}
}
