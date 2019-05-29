# Wind-Farm-Simulation


### Wersja Alfa projektu:
 * Miesięczne wykresy zysków i strat dla trzech miejscowości (Kilce, Gdańsk, Linowo)
 * Pobieranie aktualnych danch pogodowych i na ich podstawie obliczanie wyprodukowanej energii + zysku z niej (brak przechowywania jakich kolwiek danych historycznych, chyba że się uda cos takiego wyciganać przez api pogodowe)
 * Przy pobieraniu pogody(dla przykładowych, wpisanych w kodzie miejscowości) z danago dnia predykcja czasu po jakim inwestycja się zwróci przy (warunkach jakie pobraliśmy)
 * Procent zwrócony po roku pracy farmy
 * Jakieś proste GUI (proponuje to na wersję Beta ? )
 
 
### Wersja Beta projektu: 
 * Awarie, ich obsługa i wpływ na koszty przedsięwzięcia
 * Możliwość oglądania części turbiny
 * Mozliwość symulacji pracy jedenej turbiny (możliwość zmiany warunków real-time)
 * Wyliczenie kiedy najlepiej byłoby zrobić okno serwisowe
 
 #### Done
 - apka czyta juz wszystkie dane od użytkowanika i działa na nich(co ma konsekwencje czyt. to be done)
 - zmiana polskich znaków na ogólne w wyborze nazwy miejscowości z danymi historycznymi
 - wstawienie jakolwiek(za co soreczki :( ) potrzebych w gui elementów 
 - czarne napisy w menu (wybór miast) 
 - daty do pobierania danych muszą byc w innym polu niż textField -> dodałam propozycję DataPicker 
 - wypisywanie info o awariach 
 
 #### To be done
 - napewno spreparować plik dla Linowa o takiej samej nazwie i formacie jak pozostałe
 - jeśli chcemy dowoloność w datach z api pogodowego to trzeba zrobić opcje zapisu do pliku i pobierania danych na "raty"
 - napewno zautomatyzowac wykresy (np. jeśli mamy dane z wieciej niz 60 dni to podsumowanie miesięczne w przeciwnym razie dzienne)
 - w Main.startSimulation nie potrzebujemy już pola years (daty można już pobierać )=> zmiany w kodzie 
 - !!! w OneTurbineSimulation(String city) musiałam zakomentować pobieranie pogody, nie zauważyłam żebyśmy gdzieś tego narazie używali, ale piszę żeby pozniej nie było jakiejś gafy z tego powodu
 - jakiś modern look na info jakie są wyświetlane po symulacji + koniecznie dopisanie jednosek i jakieś sensowne zaokrąglanie liczb
 - poprawić odpowiednią kolejność doblokowywania się przycików w GUI i dopracowanie wygladu.
 - pewnie o czymś jeszcze zapomniałam, ale chyba wiekszość już tutaj jest :) 
 - wykresy statystku i awarie w osobnych zakładkach w menu simulation 
 - zmiana przechowywania info o awariach, nie należą do pojednczej Turbiny tylko do symulacji, bo tylko wtedy będę je umiała sensownie przedstawić w tabeli + wetdy nie ma problemu z nr. turbiny 

 
 - w oknie symulacji klikamy start. Przenosi to nas na podstronę podsumowanie, gdzie będzie można wybrać jaki wykres chcemy zobaczyć.
    - wykres słupkowy z zarobionymi pieniedzmi w miesiacu
    - wykres kolowy pokazyjacymi procent wydatkow na poszczegolne elementy (naprawa, konserwacja)
    - wykres slupkowy ilosci awarii w okresie
    - wykres liniowy zwrotu inwestycji ( mozemy zaczynac od -cena turbin i postawienia elektorwnii i isc w gore, lub zaczynac od + cena turbin i w dol)
    
 