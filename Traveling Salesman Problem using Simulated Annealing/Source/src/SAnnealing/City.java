package SAnnealing;

public class City {
String name;
double lat;
double lon;

City(String name, double lat, double lon)
{
	this.name=name;
	this.lat=lat;
	this.lon=lon;
}


public String getCityName()
{
	return name;
}
	
public double getCityLat()
{
	return lat;
}

public double getCityLon()
{
	return lon;
}
@Override
public boolean equals(Object other) {
    if (!(other instanceof City)) {
        return false;
    }

    City that = (City) other;

    // Overridden equality check by comparing id's of object
    return this.name.equals(that.name);

}
}

