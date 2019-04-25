package Agents;

public class Control {
    /*
        todo

        Kontrola wiatraków w zależności od warunków atmosferycznych
        Kontrola przy awariach
        Wysyłanie komunikatów do Agents.Maintanance
     */

    public static boolean checkWind(Turbine turbine, Weather weather) { // jesli za mala/ duza predkosc wiatru to wylaczenie wiatraka
        if(weather.getWind() > 2 || weather.getWind() < 17) {
            return true; // wiatr w normie
        }
        return false; // za maly lub za duzy
    }

    public boolean checkCondition(Turbine turbine) { // sprawdzenie w jakim stanie jest wiatrak i ewentualne odeslanie do naprawy
        if(turbine.getCondition() < 0.5) {
            return false; // slaby stan, do konserwacji
        }
        return true;
    }

    public void increaseAge(Turbine turbine) { // po roku trzeba zwiekszyc wiek turbiny, wplywa na wydajnosc i stan
        turbine.setAge(turbine.getAge() + 1);
        turbine.setCondition(turbine.getCondition() - 0.2);
    }

    public void setAlert(Turbine turbine) { // ustawienie alarmu o awarii
        turbine.setAlert(true); // jesli alarm to do naprawy
    }

    public static void main(String [] argv) {

    }
}
