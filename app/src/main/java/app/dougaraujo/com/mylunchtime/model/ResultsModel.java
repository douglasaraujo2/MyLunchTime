package app.dougaraujo.com.mylunchtime.model;

import java.util.List;

/**
 * Created by Dux-Douglas2 on 01/09/2017.
 */

public class ResultsModel {
    private List<ResultsModel> results;
    private Geometry geometry;

    public List<ResultsModel> getResults() {
        return results;
    }

    public void setResults(List<ResultsModel> results) {
        this.results = results;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public class Geometry {
        private Location location;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    public class Location {
        private double lat, lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }


}
