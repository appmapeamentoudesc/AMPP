package cidademais.udesc.br.model;

/**
 * Created by Wagner on 9/26/2017.
 */

    public class Localizacao {

        private double lat;
        private double lon;

        public Localizacao(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public Localizacao() {
            this.lat = 0;
            this.lon  = 0;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

}
