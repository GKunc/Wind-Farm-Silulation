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
 
 #### To be done
 - napewno spreparować plik dla Linowa o takiej samej nazwie i formacie jak pozostałe
 - w Main.startSimulation **(potrzebujemy?)** pola years (daty można już pobierać )=> zmiany w kodzie 
 - !!! w OneTurbineSimulation(String city) musiałam zakomentować pobieranie pogody, nie zauważyłam żebyśmy gdzieś tego narazie używali, ale piszę żeby pozniej nie było jakiejś gafy z tego powodu
 - jakiś modern look na info jakie są wyświetlane po symulacji + koniecznie dopisanie jednosek i jakieś sensowne zaokrąglanie liczb
 - w oknie symulacji klikamy start. Przenosi to nas na podstronę podsumowanie, gdzie podsumowanie liczbowe i mozliwosc wyboru jaki wykres chcemy zobaczyć.
     - wykres słupkowy z zarobionymi pieniedzmi w miesiacu
     - wykres kolowy pokazyjacymi procent wydatkow na poszczegolne elementy (naprawa, konserwacja)
     - wykres slupkowy ilosci awarii w okresie
     - wykres liniowy zwrotu inwestycji ( mozemy zaczynac od -cena turbin i postawienia elektorwnii i isc w gore, lub zaczynac od + cena turbin i w dol)
     

###Bugs ? :
 - nie wyświetla się srednia prędkośc wiatru dla symulaci z API :( 
 
