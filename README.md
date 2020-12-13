# TAMZSpaceInvaders
Aplikace je velice jednoduchá při spuštění se objeví menu se 3 tlačítky. První spustí hru v režimu classic, druhé v režimu endless a třetí ukáže nejlepší skóre.
Při spuštění hry začnou přilétat nepřátelé. Ti mají 1-4 životy. Pokud se dotknou hráče nebo spodní části obrazovky, zemřou a hráči se ubere život.
Hráč má celkem 3 životy. Pokud hráči dojdou životy, hra končí, skóre se zapíše do databáze.
V režimu classic se nepřátelé pohybují v bloku od kraje ke kraji, jejich rychlost se postupně zvyšuje. Všichni nepřátele se spawnou při spuštění hry. Při zabití všech nepřátel se hra ukončí a přehraje se výtězný zvuk.
V endless módu hráč nemůže vyhrát, nepřátele se spawnují postupně každé 3s a pohybují se pouze rovně dolů.

Hra obsahuje: vykreslování do canvasu, vlastní view, čtení a zápis do databáze, vlákna, zvuky

Bylo v plánu ale nakonec není implemetováno: pohyb pomocí naklánění telefonu (mobil, s kterým jsem to chtěl testovat to vzdal a 
bohužel jsem neměl přístup k jinému telefonu s androidem)

problémy: Ve hře fungujou hitboxy na pixely ne pouze protnutí obdélníků (asi to dělá víc problému než užitku ale zajímalo mě to a chtěl jsem si to zkusit)
někdy nastane situace že hitboxy přestanou fungovat a není pak možné nepřítele trefit, zatím jsem neodhalil chybu. 
