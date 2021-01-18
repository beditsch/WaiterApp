# WaiterApp - projekt PROZ

## Cel projektu
Celem projektu jest zbudowanie Backendu aplikacji webowej (RESTful Web Sevice) do obsługi klienta w restauracji.

## Prototyp
Prototyp realizuje między innymi obsługę tworzenia i usuwania zamówienia.

## Uruchamianie prototypu
W repozytorium umieszczone są pliki źródłowe projektu.
W celu uruchomienia należy w folderze projektu skorzystać z komendy:
```
mvn spring-boot:run
```
Serwer będzie działał na porcie 8080.
## Testowanie projektu
Testowania projektu można dokonać za pomocą klienta Postman oraz przykładowych żądań dostępnych pod poniższym linkiem.
> https://www.getpostman.com/collections/72e08873d04433735702

## Funkcjonalności
### Autoryzacja właściciela restauracji
Właściciel restauracji może założyć konto, a następnie stworzyć restaurację i modyfikować ją. Możliwość modyfikacji restauracji wymaga autoryzacji.
#### Przykładowe funckjonalności udostępnione właścicielowi restauracji
- dodawanie stolików w restauracji (do których są przypisywane zamówienia)
- zarządzanie menu restauracji, w tym: kategorie potraw, potrawy
- zarządzanie statusami zamówień: możliwość zmiany statusu zamówienia w swojej restauracji z 0 (w oczekiwaniu) na opłacone oraz zakończone
### Obsługa składania zamówień
Dostęp do składania, edytowania oraz usuwania zamówień jest nieograniczony. Z tego względu zamówienia są reprezentowane kluczem publicznym, który ciężko jest odgadnąć.
Zamówienie można edytować tylko wtedy, gdy jego status jest "w oczekiwaniu". Nie można edytować zamówienia jeśli jest ono opłacone/zakończone.

