# TAMZSpaceInvaders
Aplikace je velice jednoduchá při spuštění se objeví menu se 2 tlačítky. První spustí hru , druhé ukáže nejlepší skore.
Při spuštění hry začnou přilétat nepřátelé. Ti mají 1-4 životy. Pokud se dotknou hráče nebo spodní části obrazovky, zemřou a hráči se ubere život.
Hráč má celkem 3 životy. Pokud hráči dojdou životy nebo hráč sestřelil všechny nepřátele, hra končí, skore se zapíše do databáze.

Hra obsahuje: vykreslování do canvasu, vlastní view, čtení a zápis do databáze, vlákna, zvuky

Bylo v plánu ale nakonec není implemetováno: pohyb pomocí naklánění telefonu (mobil, s kterým jsem to chtěl testovat to vzdal a 
bohužel jsem neměl přístup k jinému telefonu s androidem)

problémy: Ve hře fungujou hitboxy na pixely ne pouze protnutí obdélníků (asi to dělá víc problému než užitku ale zajímalo mě to a chtěl jsem si to zkusit)
někdy nastane situace že hitboxy přestanou fungovat a není pak možné nepřítele trefit, zatím jsem neodhalil chybu. 
