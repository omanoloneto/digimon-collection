package co.hillstech.digicolle.Classes

import co.hillstech.digicolle.Session
import java.util.*

class Monster {
    var id: Int = 0
    var name: String? = null
    var partner: String? = null
    var evolutions: List<String> = listOf()
    var lvl: Int? = 0
    var image: Int = 0
    var scan: Int = 0
    var percent: Int = 0
    var evoLvl: Int = 0

    constructor() : super(){}

    constructor(id: Int, name: String, partner: String, evos: List<String>){
        this.id = id
        this.name = name
        this.partner = partner
        this.evolutions = evos
        this.lvl = 0
        this.image = 0
        this.scan = 0
    }

    constructor(id: Int, name: String, partner: String, lvl: Int) {
        this.id = id
        this.name = name
        this.partner = partner
        this.evolutions = listOf()
        this.lvl = lvl
        this.image = 0
        this.scan = 0
    }

    constructor(id: Int, name: String, partner: String){
        this.id = id
        this.name = name
        this.partner = partner
        this.evolutions = listOf()
        this.lvl = 0
        this.image = 0
        this.scan = 0
    }

    constructor(id: Int, name: String, evos: List<String>){
        this.id = id
        this.name = name
        this.partner = "false"
        this.evolutions = evos
        this.lvl = 0
        this.image = 0
        this.scan = 0
    }

    constructor(id: Int, name: String, evos: List<String>, image: Int){
        this.id = id
        this.name = name
        this.partner = "false"
        this.evolutions = evos
        this.lvl = 0
        this.image = image
        this.scan = 0
    }

    constructor(id: Int, name: String, evos: List<String>, image: Int, percent: Int){
        this.id = id
        this.name = name
        this.partner = "false"
        this.evolutions = evos
        this.lvl = 0
        this.image = image
        this.scan = 0
        this.percent = percent
    }

    constructor(id: Int, name: String, evos: List<String>, image: Int, percent: Int, evoLvl: Int){
        this.id = id
        this.name = name
        this.partner = "false"
        this.evolutions = evos
        this.lvl = 0
        this.image = image
        this.scan = 0
        this.percent = percent
        this.evoLvl = evoLvl
    }

    constructor(name: String, evos: List<String>, image: Int, percent: Int, evoLvl: Int){
        this.id = 0
        this.name = name
        this.partner = "false"
        this.evolutions = evos
        this.lvl = 0
        this.image = image
        this.scan = 0
        this.percent = percent
        this.evoLvl = evoLvl
    }

    constructor(name: String, evos: List<String>, image: Int, percent: Int){
        this.id = 0
        this.name = name
        this.partner = "false"
        this.evolutions = evos
        this.lvl = 0
        this.image = image
        this.scan = 0
        this.percent = percent
        this.evoLvl = evoLvl
    }

    companion object {

        fun size() : Int{
            return 9
        }

        fun species(): List<Monster>{
            return Session.monsters
        }

        fun inflateMonsters() : List<Monster>{
            var monsters: MutableList<Monster> = mutableListOf()
            var evos: MutableList<String> = mutableListOf()


            //----------------------------------------------------------------------------------------------------------- Fresh

            //Botamon
            /*evos = mutableListOf("Koromon","Budmon","Pagumon","Pinamon","Tokomon","Wanyamon")
            monsters.add(Monster("Botamon", evos, R.drawable.botamon, 25, 2))

            //Metal Koromon
            evos = mutableListOf("Koromon","Kapurimon","Missimon")
            monsters.add(Monster("Metal Koromon", evos, R.drawable.metalkoromon, 25, 2))

            //Nyokimon
            evos = mutableListOf("Yokomon","Tanemon","Koromon","Tsunomon","DemiVeemon","Minomon","Tokomon","Nyaromon","Gummymon","Pagumon","Bukamon","Viximon","Gigimon")
            monsters.add(Monster("Nyokimon", evos, R.drawable.nyokimon, 25, 2))

            //Conomon
            evos = mutableListOf("Kokomon")
            monsters.add(Monster("Conomon", evos, R.drawable.cocomon, 15, 2))

            //Pabumon
            evos = mutableListOf("Motimon","Nyaromon","Yokomon")
            monsters.add(Monster("Pabumon", evos, R.drawable.pabumon, 25, 2))

            //Poyomon
            evos = mutableListOf("Tokomon","Koromon","Tsunomon","DemiVeemon","Minomon","Nyaromon","Gigimon","Viximon","Gummymon","Pagumon","Bukamon")
            monsters.add(Monster("Poyomon", evos, R.drawable.poyomon, 25, 2))

            //Punimon
            evos = mutableListOf("Tsunomon","Bukamon","DemiMeramon","Nyaromon")
            monsters.add(Monster("Punimon", evos, R.drawable.punimon, 25, 2))

            //Yukimi Botamon
            evos = mutableListOf("Nyaromon","Tsunomon","DemiVeemon","Minomon","Tokomon","Gigimon","Viximon","Gummymon","Pagumon","Bukamon")
            monsters.add(Monster("Yukimi Botamon", evos, R.drawable.yukimibotamon, 20, 2))

            //Zerimon
            evos = mutableListOf("Gummymon")
            monsters.add(Monster("Zerimon", evos, R.drawable.zerimon, 20, 2))

            //----------------------------------------------------------------------------------------------------------- In-Training

            //Gigimon
            evos = mutableListOf("Guilmon","Agumon","Gabumon","Veemon","Wormmon","Patamon","Salamon","Elecmon","Renamon","DemiDevimon")
            monsters.add(Monster("Gigimon", evos, R.drawable.gigimon, 15, 8))

            //Viximon
            evos = mutableListOf("Renamon","Gabumon","Veemon","Wormmon","Patamon","Salamon","Elecmon","Guilmon","DemiDevimon")
            monsters.add(Monster("Viximon", evos, R.drawable.viximon, 15, 9))

            //Yokomon
            evos = mutableListOf("Biyomon","Floramon","DemiDevimon","Falcomon","Kunemon","Mushroomon","Palmon")
            monsters.add(Monster("Yokomon", evos, R.drawable.yokomon, 20, 8))

            //Nyaromon
            evos = mutableListOf("Salamon","Agumon","Gabumon","Bearmon","Elecmon","Renamon","DemiDevimon","Agumon Hakase","Gaomon",
                    "Gizamon","Agumon Savers","Hawkmon","Guilmon","Veemon","Wormmon","Patamon","Terriermon","Gotsumon")
            monsters.add(Monster("Nyaromon", evos, R.drawable.nyaromon, 15, 9))

            //Motimon
            evos = mutableListOf("Tentomon","Otamamon","Gotsumon","Elecmon","Crabmon","Kunemon")
            monsters.add(Monster("Motimon", evos, R.drawable.motimon, 20, 8))

            //Budmon
            evos = mutableListOf("Lalamon","Agumon Savers","DoKunemon","Gaomon","Mushroomon","Toy Agumon","Chuumon")
            monsters.add(Monster("Budmon", evos, R.drawable.budmon, 20, 9))

            //Koromon
            evos = mutableListOf("Agumon","Agumon Savers","Toy Agumon","Betamon","Gabumon","Chuumon","Gaomon",
                    "Falcomon","Kudamon","Kamemon","Neemon","Candlemon")
            monsters.add(Monster("Koromon", evos, R.drawable.koromon, 20, 8))

            //Pagumon
            evos = mutableListOf("Gazimon","Gizamon","Lopmon","Tsukaimon","Black Agumon","Gabumon","Bearmon","Elecmon",
                    "Hawkmon","Guilmon","Veemon","Wormmon","Patamon","Renamon","DemiDevimon","Impmon")
            monsters.add(Monster("Pagumon", evos, R.drawable.pagumon, 15, 9))

            //Pinamon
            evos = mutableListOf("Falcomon","Falcomon Savers","Kamemon", "Goblimon", "Kudamon", "Elecmon", "Gotsumon",
                    "Agumon Savers", "Gaomon", "Candlemon", "Mushroomon", "Lalamon", "Swimmon", "Terriermon")
            monsters.add(Monster("Pinamon", evos, R.drawable.pinamon, 15, 8))

            //Tokomon
            evos = mutableListOf("Patamon", "Kunemon", "Biyomon", "Crabmon", "Falcomon", "Gabumon", "Veemon", "Wormmon",
                    "Salamon", "Elecmon", "Guilmon", "Renamon", "Terriermon", "DemiDevimon", "Bakumon", "Hawkmon")
            monsters.add(Monster("Tokomon", evos, R.drawable.tokomon, 20, 8))

            //Wanyamon
            evos = mutableListOf("Gaomon","Agumon Savers", "Chuumon", "Lalamon", "Toy Agumon", "Kamemon", "Candlemon", "Falcomon", "Kudamon", "Bearmon")
            monsters.add(Monster("Wanyamon", evos, R.drawable.wanyamon, 20, 8))

            //Kapurimon
            evos = mutableListOf("Hagurumon","Armadillomon","Kokuwamon","Kotemon","Black PawnChessmon","Toy Agumon")
            monsters.add(Monster("Kapurimon", evos, R.drawable.kapurimon, 20, 8))

            //Missimon
            evos = mutableListOf("Commandramon","Dracmon", "Gizamon", "Hagurumon", "Penguinmon", "Tentomon")
            monsters.add(Monster("Missimon", evos, R.drawable.missimon, 10, 8))

            //Tanemon
            evos = mutableListOf("Palmon","Aruraumon","Biyomon", "Betamon", "Lalamon", "Renamon", "Goblimon", "Monmon", "Floramon", "Kunemon")
            monsters.add(Monster("Tanemon", evos, R.drawable.tanemon, 20, 8))

            //Bukamon
            evos = mutableListOf("Gomamon","Betamon","Crabmon","Gizamon","Kamemon","Otamamon","Penguinmon","Snow Agumon")
            monsters.add(Monster("Bukamon", evos, R.drawable.bukamon, 20, 8))

            //Kokomon
            evos = mutableListOf("Lopmon")
            monsters.add(Monster("Kokomon", evos, R.drawable.kokomon, 15, 8))

            //Gummymon
            evos = mutableListOf("Terriermon","Gabumon", "Veemon", "Wormmon", "Patamon", "Salamon", "Elecmon", "Renamon", "Guilmon", "DemiDevimon", "Bakumon", "Armadillomon")
            monsters.add(Monster("Gummymon", evos, R.drawable.gummymon, 15, 8))

            //Tsunomon
            evos = mutableListOf("Gabumon","Elecmon","Penguinmon", "Gaomon", "Dracmon", "Goblimon", "Kunemon", "Veemon", "Wormmon", "Patamon", "Salamon", "Guilmon", "Renamon", "DemiDevimon")
            monsters.add(Monster("Tsunomon", evos, R.drawable.tsunomon, 20, 8))

            //DemiMeramon
            evos = mutableListOf("Candlemon","Bakumon", "DemiDevimon", "Impmon", "Gabumon", "Veemon", "Wormmon", "Patamon", "Salamon", "Elecmon", "Guilmon", "Renamon")
            monsters.add(Monster("DemiMeramon", evos, R.drawable.demimeramon, 10, 8))

            //DemiVeemon
            evos = mutableListOf("Veemon","Gabumon", "Wormmon", "Patamon", "Salamon", "Elecmon", "Guilmon", "Renamon", "DemiDevimon", "Snow Agumon")
            monsters.add(Monster("DemiVeemon", evos, R.drawable.demiveemon, 10, 8))

            //Minomon
            evos = mutableListOf("Wormmon","Tentomon", "Kunemon", "Gabumon", "Veemon", "Patamon", "Salamon", "Elecmon", "Guilmon", "Renamon", "DemiDevimon")
            monsters.add(Monster("Minomon", evos, R.drawable.minomon, 10, 8))

            //----------------------------------------------------------------------------------------------------------- Rookie

            //DemiDevimon
            evos = mutableListOf("Devimon","Ice Devimon","Bakemon","Black Growmon","Devidramon","Vilemon","Garurumon","Kyubimon",
                    "Raremon","Soulmon","Wizardmon","Angemon","Dinohumon","Dark Tyrannomon","ExVeemon","Dokugumon","Meramon","Nanimon","Pokyupamon","Stingmon",
                    "Lekismon","Greymon","Ebidramon","Cyclonemon","Dolphmon","Shellmon","Coelamon","Numemon","Musyamon","Dex DORUGamon")
            monsters.add(Monster("DemiDevimon", evos, R.drawable.demidevimon, 10, 18))

            //Palmon
            evos = mutableListOf("Togemon","Angemon","Aquilamon","Dokugumon","Fugamon","Jungle Mojyamon","Kiwimon","Leomon","Mojyamon","Wizardmon",
                    "Chuchidarumon","Fangmon","Witchmon","Growlmon","Woodmon","Coelamon","Frigimon","Sunflowmon","Kuwagamon","Leppamon","Nanimon",
                    "Ninjamon","Numemon","GeoGreymon","Red Vegiemon","Tuskmon","Vegiemon","Whamon","MoriShellmon","Sukamon","Ogremon")
            monsters.add(Monster("Palmon", evos, R.drawable.palmon, 10, 18))

            //Biyomon
            evos = mutableListOf("Birdramon","Airdramon","Akatorimon","Aquilamon","Deputymon","Diatrymon","Dokugumon","Fugamon","Golemon",
                    "Kabuterimon","Kiwimon","Kokatorimon","Kuwagamon","Leomon","Minotarumon","Monochromon","Nanimon","Numemon","Peckmon",
                    "Saberdramon","Sukamon","Togemon","Unimon","Woodmon","Veedramon","Vilemon")
            monsters.add(Monster("Biyomon", evos, R.drawable.biyomon, 10,18))

            //Floramon
            evos = mutableListOf("Kiwimon","Akatorimon","Angemon","Black Gargomon","Black Garurumon","Black Gatomon","Dobermon",
                    "Ebidramon","Flymon","Kabuterimon","Kokatorimon","Kyubimon","Ninjamon","Raremon","Red Vegiemon","Shellmon","Stingmon",
                    "Sunflowmon","Togemon","Vegiemon","Woodmon","Youkomon")
            monsters.add(Monster("Floramon", evos, R.drawable.floramon, 10,19))

            //Falcomon
            evos = mutableListOf("Diatrymon","Airdramon","Aquilamon","ExVeemon","Gaogamon","GeoGreymon","Gwappamon","ShellNumemon","Leppamon",
                    "Ninjamon","Numemon","Ogremon","Peckmon","Saberdramon","Starmon","Stingmon","Turuiemon","Tyrannomon","Unimon")
            monsters.add(Monster("Falcomon", evos, R.drawable.falcomon, 10,19))

            //Kunemon
            evos = mutableListOf("Flymon","Airdramon","Bakemon","Centarumon","Dokugumon","Drimogemon","Kabuterimon","Kuwagamon",
                    "Musyamon","Nanimon","Numemon","Ogremon","Red Vegiemon","Roachmon","Shellmon","Snimon","Sukamon","Thundermon",
                    "Tyrannomon","Vegiemon","Yanmamon")
            monsters.add(Monster("Kunemon", evos, R.drawable.kunemon, 10,16))

            //Mushroomon
            evos = mutableListOf("Woodmon","Birdramon","Boogeymon","Devimon","Dokugumon","ExVeemon","Black ExVeemon","Flymon",
                    "Frigimon","Gatomon","Growlmon","Kougamon","Raiamon","Monochromon","Ninjamon","Numemon","Ogremon","Red Vegiemon",
                    "Stingmon","Sukamon","Sunflowmon","Tyrannomon","Veedramon","Vegiemon")
            monsters.add(Monster("Mushroomon", evos, R.drawable.mushroomon, 10,18))

            //--------------------------------

            //Salamon
            evos = mutableListOf("Gatomon","Black Gatomon","DArcmon","Garurumon","Golemon","Guardromon","Musyamon","Piddomon","Raremon",
                    "Deputymon","Starmon","Stingmon","Veedramon","Greymon","ExVeemon","Chuchidarumon","Airdramon","Witchmon","Angemon","Blimpmon",
                    "Fangmon","FlareRizamon","Icemon","Kenkimon","Leppamon","Gaogamon","Opossumon","Ninjamon","Tortomon","Opossumon","Red Veedramon",
                    "Kyubimon","Dolphmon","Unimon")
            monsters.add(Monster("Salamon", evos, R.drawable.salamon, 10))

            //Gabumon
            evos = mutableListOf("Garurumon","Angemon","Apemon","Aquilamon","Black Garurumon","Black Gatomon","Centarumon","Chuchidarumon","Dark Lizardmon",
                    "Dobermon","Dokugumon","Drimogemon","ExVeemon","Fangmon","Flymon","Frigimon","Fugamon","Gaogamon","Gatomon","Gorillamon","Greymon",
                    "Grizzlymon","Gururumon","Ikkakumon","Jungle Mojyamon","Kabuterimon","Kiwimon","Kyubimon","Leomon","Leppamon","Mikemon","Monochromon",
                    "Nanimon","Ninjamon","Nise Drimogemon","Numemon","Ogremon","Opossumon","Seadramon","Snimon","Stingmon","Strikedramon","Sukamon","Turuiemon",
                    "Tyrannomon","Veedramon","Vegiemon","Wizardmon")
            monsters.add(Monster("Gabumon", evos, R.drawable.gabumon, 10))

            //Elecmon
            evos = mutableListOf()
            monsters.add(Monster("Elecmon", evos, R.drawable.elecmon, 10))

            //Renamon
            evos = mutableListOf()
            monsters.add(Monster("Renamon", evos, R.drawable.renamon, 10))

            //Agumon Hakase
            evos = mutableListOf()
            monsters.add(Monster("Agumon Hakase", evos, R.drawable.agumonhakase, 10))

            //Agumon Savers
            evos = mutableListOf()
            monsters.add(Monster("Agumon Savers", evos, R.drawable.agumonsavers, 10))

            //--------------------------------

            //Tentomon
            evos = mutableListOf()
            monsters.add(Monster("Tentomon", evos, R.drawable.tentomon, 10))

            //Otamamon
            evos = mutableListOf()
            monsters.add(Monster("Otamamon", evos, R.drawable.otamamon, 10))

            //Gotsumon
            evos = mutableListOf()
            monsters.add(Monster("Gotsumon", evos, R.drawable.gotsumon, 10))

            //Crabmon
            evos = mutableListOf()
            monsters.add(Monster("Crabmon", evos, R.drawable.crabmon, 10))

            //--------------------------------

            //DoKunemon
            evos = mutableListOf()
            monsters.add(Monster("DoKunemon", evos, R.drawable.dokunemon, 10))

            //Lalamon
            evos = mutableListOf()
            monsters.add(Monster("Lalamon", evos, R.drawable.lalamon, 10))

            //--------------------------------

            //Agumon
            evos = mutableListOf()
            monsters.add(Monster("Agumon", evos, R.drawable.agumon, 10))

            //Betamon
            evos = mutableListOf()
            monsters.add(Monster("Betamon", evos, R.drawable.betamon, 10))

            //Toy Agumon
            evos = mutableListOf()
            monsters.add(Monster("Toy Agumon", evos, R.drawable.toyagumon, 10))

            //Gaomon
            evos = mutableListOf("Gaogamon","Leomon","Aquilamon","GeoGreymon","Sunflowmon","Numemon","Lekismon","Black Gatomon","ShellNumemon",
                    "Gwappamon","Peckmon","Leppamon","Vegiemon","Garurumon","Gargomon","Black Garurumon","Centarumon")
            monsters.add(Monster("Gaomon", evos, R.drawable.gaomon, 10, 18))

            //--------------------------------

            //Gazimon
            evos = mutableListOf()
            monsters.add(Monster("Gazimon", evos, R.drawable.gazimon, 10))

            //Gizamon
            evos = mutableListOf()
            monsters.add(Monster("Gizamon", evos, R.drawable.gizamon, 10))

            //Lopmon
            evos = mutableListOf("Wendigomon","Turuiemon","BomberNanimon","Centarumon","Coelamon","FlareRizamon",
                    "Gargomon","Seasarmon","Strikedramon","Wizardmon","Youkomon")
            monsters.add(Monster("Lopmon", evos, R.drawable.lopmon, 10, 17))

            //Black Agumon
            evos = mutableListOf()
            monsters.add(Monster("Black Agumon", evos, R.drawable.blackagumon, 10))

            //Bearmon
            evos = mutableListOf()
            monsters.add(Monster("Bearmon", evos, R.drawable.bearmon, 10))

            //-------------------------------

            //Terriermon
            evos = mutableListOf("Gargomon","Black Gargomon","Angemon","Black Gatomon","Black Greymon","Clockmon","DArcmon",
                    "Dark Tyrannomon","Deputymon","Devimon","Dinohumon","Doggymon","Ebidramon","Fangmon","Gatomon",
                    "Gorillamon","Gladimon","Greymon","Growlmon","Ikkakumon","Leomon","Minotarumon","Monochromon","Ninjamon","Seadramon",
                    "Sealsdramon","Seasarmon","Tankmon","Thundermon","Turuiemon","Tyrannomon","Unimon","Wendigomon","Witchmon")
            monsters.add(Monster("Terriermon", evos, R.drawable.terriermon, 10, 17))

            //Patamon
            evos = mutableListOf()
            monsters.add(Monster("Patamon", evos, R.drawable.patamon, 10))

            //Wormmon
            evos = mutableListOf()
            monsters.add(Monster("Wormmon", evos, R.drawable.wormmon, 10))

            //Veemon
            evos = mutableListOf()
            monsters.add(Monster("Veemon", evos, R.drawable.veemon, 10))

            //Guilmon
            evos = mutableListOf()
            monsters.add(Monster("Guilmon", evos, R.drawable.guilmon, 10))

            //Hawkmon
            evos = mutableListOf()
            monsters.add(Monster("Hawkmon", evos, R.drawable.hawkmon, 10))

            //Chuumon
            evos = mutableListOf()
            monsters.add(Monster("Chuumon", evos, R.drawable.chuumon, 10))

            //Kamemon
            evos = mutableListOf()
            monsters.add(Monster("Kamemon", evos, R.drawable.kamemon, 10))

            //Kudamon
            evos = mutableListOf()
            monsters.add(Monster("Kudamon", evos, R.drawable.kudamon, 10))

            //Neemon
            evos = mutableListOf()
            monsters.add(Monster("Neemon", evos, R.drawable.neemon, 10))

            //Candlemon
            evos = mutableListOf()
            monsters.add(Monster("Candlemon", evos, R.drawable.candlemon, 10))

            //Impmon
            evos = mutableListOf()
            monsters.add(Monster("Impmon", evos, R.drawable.impmon, 10))

            //Tsukaimon
            evos = mutableListOf()
            monsters.add(Monster("Tsukaimon", evos, R.drawable.tsukaimon, 10))

            //Falcomon Savers
            evos = mutableListOf()
            monsters.add(Monster("Falcomon Savers", evos, R.drawable.falcomonsavers, 10))

            //Goblimon
            evos = mutableListOf()
            monsters.add(Monster("Goblimon", evos, R.drawable.goblimon, 10))

            //Swimmon
            evos = mutableListOf()
            monsters.add(Monster("Swimmon", evos, R.drawable.swimmon, 10))

            //Bakumon
            evos = mutableListOf()
            monsters.add(Monster("Bakumon", evos, R.drawable.bakumon, 10))

            //Hagurumon
            evos = mutableListOf()
            monsters.add(Monster("Hagurumon", evos, R.drawable.hagurumon, 10))

            //Armadillomon
            evos = mutableListOf()
            monsters.add(Monster("Armadillomon", evos, R.drawable.armadillomon, 10))

            //Kokuwamon
            evos = mutableListOf()
            monsters.add(Monster("Kokuwamon", evos, R.drawable.kokuwamon, 10))

            //Kotemon
            evos = mutableListOf()
            monsters.add(Monster("Kotemon", evos, R.drawable.kotemon, 10))

            //Black PawnChessmon
            evos = mutableListOf()
            monsters.add(Monster("Black PawnChessmon", evos, R.drawable.pawnchessmonblack, 10))

            //Commandramon
            evos = mutableListOf()
            monsters.add(Monster("Commandramon", evos, R.drawable.commandramon, 10))

            //Dracmon
            evos = mutableListOf()
            monsters.add(Monster("Dracmon", evos, R.drawable.dracmon, 10))

            //Penguinmon
            evos = mutableListOf()
            monsters.add(Monster("Penguinmon", evos, R.drawable.penguinmon, 10))

            //Aruraumon
            evos = mutableListOf()
            monsters.add(Monster("Aruraumon", evos, R.drawable.aruraumon, 10))

            //Monmon
            evos = mutableListOf()
            monsters.add(Monster("Monmon", evos, R.drawable.monmon, 10))

            //Gomamon
            evos = mutableListOf()
            monsters.add(Monster("Gomamon", evos, R.drawable.gomamon, 10))

            //Snow Agumon
            evos = mutableListOf()
            monsters.add(Monster("Snow Agumon", evos, R.drawable.snowagumon, 10))

            //----------------------------------------------------------------------------------------------------------- Champion

            //Devimon
            evos = mutableListOf()
            monsters.add(Monster("Devimon", evos, R.drawable.devimon, 5))

            //Ice Devimon
            evos = mutableListOf()
            monsters.add(Monster("Ice Devimon", evos, R.drawable.icedevimon, 5))

            //Bakemon
            evos = mutableListOf()
            monsters.add(Monster("Bakemon", evos, R.drawable.bakemon, 5))

            //Black Growmon
            evos = mutableListOf()
            monsters.add(Monster("Black Growmon", evos, R.drawable.blackgrowmon, 5))

            //Devidramon
            evos = mutableListOf()
            monsters.add(Monster("Devidramon", evos, R.drawable.devidramon, 5))

            //Vilemon
            evos = mutableListOf()
            monsters.add(Monster("Vilemon", evos, R.drawable.vilemon, 5))

            //Garurumon
            evos = mutableListOf()
            monsters.add(Monster("Garurumon", evos, R.drawable.garurumon, 5))

            //Kyubimon
            evos = mutableListOf()
            monsters.add(Monster("Kyubimon", evos, R.drawable.kyubimon, 5))

            //Raremon
            evos = mutableListOf()
            monsters.add(Monster("Raremon", evos, R.drawable.raremon, 5))

            //Soulmon
            evos = mutableListOf()
            monsters.add(Monster("Soulmon", evos, R.drawable.soulmon, 5))

            //Wizardmon
            evos = mutableListOf()
            monsters.add(Monster("Wizardmon", evos, R.drawable.wizardmon, 5))

            //Angemon
            evos = mutableListOf()
            monsters.add(Monster("Angemon", evos, R.drawable.angemon, 5))

            //Dinohumon
            evos = mutableListOf()
            monsters.add(Monster("Dinohumon", evos, R.drawable.dinohumon, 5))

            //Dark Tyrannomon
            evos = mutableListOf()
            monsters.add(Monster("Dark Tyrannomon", evos, R.drawable.darktyrannomon, 5))

            //ExVeemon
            evos = mutableListOf()
            monsters.add(Monster("ExVeemon", evos, R.drawable.exveemon, 5))

            //Dokugumon
            evos = mutableListOf()
            monsters.add(Monster("Dokugumon", evos, R.drawable.dokugumon, 5))

            //Meramon
            evos = mutableListOf()
            monsters.add(Monster("Meramon", evos, R.drawable.meramon, 5))

            //Nanimon
            evos = mutableListOf()
            monsters.add(Monster("Nanimon", evos, R.drawable.nanimon, 5))

            //Pokyupamon
            evos = mutableListOf()
            monsters.add(Monster("Pokyupamon", evos, R.drawable.pokyupamon, 5))

            //Stingmon
            evos = mutableListOf()
            monsters.add(Monster("Stingmon", evos, R.drawable.stingmon, 5))

            //Lekismon
            evos = mutableListOf()
            monsters.add(Monster("Lekismon", evos, R.drawable.lekismon, 5))

            //Greymon
            evos = mutableListOf()
            monsters.add(Monster("Greymon", evos, R.drawable.greymon, 5))

            //Ebidramon
            evos = mutableListOf()
            monsters.add(Monster("Ebidramon", evos, R.drawable.ebidramon, 5))

            //Cyclonemon
            evos = mutableListOf()
            monsters.add(Monster("Cyclonemon", evos, R.drawable.cyclonemon, 5))

            //Dolphmon
            evos = mutableListOf()
            monsters.add(Monster("Dolphmon", evos, R.drawable.dolphmon, 5))

            //Shellmon
            evos = mutableListOf()
            monsters.add(Monster("Shellmon", evos, R.drawable.shellmon, 5))

            //Coelamon
            evos = mutableListOf()
            monsters.add(Monster("Coelamon", evos, R.drawable.coelamon, 5))

            //Numemon
            evos = mutableListOf()
            monsters.add(Monster("Numemon", evos, R.drawable.numemon, 5))

            //Musyamon
            evos = mutableListOf()
            monsters.add(Monster("Musyamon", evos, R.drawable.musyamon, 5))

            //Dex DORUGamon
            evos = mutableListOf()
            monsters.add(Monster("Dex DORUGamon", evos, R.drawable.dexdorugamon, 5))

            //-----------------------------------------------------------------------------------------------------------------

            //Togemon
            evos = mutableListOf()
            monsters.add(Monster("Togemon", evos, R.drawable.togemon, 5))

            //Aquilamon
            evos = mutableListOf()
            monsters.add(Monster("Aquilamon", evos, R.drawable.aquilamon, 5))

            //Fugamon
            evos = mutableListOf()
            monsters.add(Monster("Fugamon", evos, R.drawable.fugamon, 5))

            //Kiwimon
            evos = mutableListOf()
            monsters.add(Monster("Kiwimon", evos, R.drawable.kiwimon, 5))

            //Leomon
            evos = mutableListOf()
            monsters.add(Monster("Leomon", evos, R.drawable.leomon, 5))

            //Mojyamon
            evos = mutableListOf()
            monsters.add(Monster("Mojyamon", evos, R.drawable.mojyamon, 5))

            //Chuchidarumon
            evos = mutableListOf()
            monsters.add(Monster("Chuchidarumon", evos, R.drawable.chuchidarumon, 5))

            //Fangmon
            evos = mutableListOf()
            monsters.add(Monster("Fangmon", evos, R.drawable.fangmon, 5))

            //Witchmon
            evos = mutableListOf()
            monsters.add(Monster("Witchmon", evos, R.drawable.witchmon, 5))

            //Growlmon
            evos = mutableListOf()
            monsters.add(Monster("Growlmon", evos, R.drawable.growlmon, 5))

            //Woodmon
            evos = mutableListOf()
            monsters.add(Monster("Woodmon", evos, R.drawable.woodmon, 5))

            //Frigimon
            evos = mutableListOf()
            monsters.add(Monster("Frigimon", evos, R.drawable.frigimon, 5))

            //Sunflowmon
            evos = mutableListOf()
            monsters.add(Monster("Sunflowmon", evos, R.drawable.sunflowmon, 5))

            //Kuwagamon
            evos = mutableListOf()
            monsters.add(Monster("Kuwagamon", evos, R.drawable.kuwagamon, 5))

            //Leppamon
            evos = mutableListOf()
            monsters.add(Monster("Leppamon", evos, R.drawable.leppamon, 5))

            //Ninjamon
            evos = mutableListOf()
            monsters.add(Monster("Ninjamon", evos, R.drawable.ninjamon, 5))

            //GeoGreymon
            evos = mutableListOf()
            monsters.add(Monster("GeoGreymon", evos, R.drawable.geogreymon, 5))

            //Red Vegiemon
            evos = mutableListOf()
            monsters.add(Monster("Red Vegiemon", evos, R.drawable.redveggiemon, 5))

            //Tuskmon
            evos = mutableListOf()
            monsters.add(Monster("Tuskmon", evos, R.drawable.tuskmon, 5))

            //Vegiemon
            evos = mutableListOf()
            monsters.add(Monster("Vegiemon", evos, R.drawable.vegiemon, 5))

            //Whamon
            evos = mutableListOf()
            monsters.add(Monster("Whamon", evos, R.drawable.whamon, 5))

            //MoriShellmon
            evos = mutableListOf()
            monsters.add(Monster("MoriShellmon", evos, R.drawable.morishellmon, 5))

            //Sukamon
            evos = mutableListOf()
            monsters.add(Monster("Sukamon", evos, R.drawable.sukamon, 5))

            //Ogremon
            evos = mutableListOf()
            monsters.add(Monster("Ogremon", evos, R.drawable.ogremon, 5))

            //Birdramon
            evos = mutableListOf()
            monsters.add(Monster("Birdramon", evos, R.drawable.birdramon, 5))

            //Airdramon
            evos = mutableListOf()
            monsters.add(Monster("Airdramon", evos, R.drawable.airdramon, 5))

            //Akatorimon
            evos = mutableListOf()
            monsters.add(Monster("Akatorimon", evos, R.drawable.akatorimon, 5))

            //Deputymon
            evos = mutableListOf()
            monsters.add(Monster("Deputymon", evos, R.drawable.deputymon, 5))

            //Diatrymon
            evos = mutableListOf()
            monsters.add(Monster("Diatrymon", evos, R.drawable.diatrymon, 5))

            //Golemon
            evos = mutableListOf()
            monsters.add(Monster("Golemon", evos, R.drawable.golemon, 5))

            //Kabuterimon
            evos = mutableListOf()
            monsters.add(Monster("Kabuterimon", evos, R.drawable.kabuterimon, 5))

            //Kokatorimon
            evos = mutableListOf()
            monsters.add(Monster("Kokatorimon", evos, R.drawable.kokatorimon, 5))

            //Minotarumon
            evos = mutableListOf()
            monsters.add(Monster("Minotarumon", evos, R.drawable.minotarumon, 5))

            //Monochromon
            evos = mutableListOf()
            monsters.add(Monster("Monochromon", evos, R.drawable.monochromon, 5))

            //Peckmon
            evos = mutableListOf()
            monsters.add(Monster("Peckmon", evos, R.drawable.peckmon, 5))

            //Saberdramon
            evos = mutableListOf()
            monsters.add(Monster("Saberdramon", evos, R.drawable.saberdramon, 5))

            //Unimon
            evos = mutableListOf()
            monsters.add(Monster("Unimon", evos, R.drawable.unimon, 5))

            //Veedramon
            evos = mutableListOf()
            monsters.add(Monster("Veedramon", evos, R.drawable.veedramon, 5))

            //Black Gargomon
            evos = mutableListOf()
            monsters.add(Monster("Black Gargomon", evos, R.drawable.blackgargomon, 5))

            //Black Garurumon
            evos = mutableListOf()
            monsters.add(Monster("Black Garurumon", evos, R.drawable.blackgarurumon, 5))

            //Black Gatomon
            evos = mutableListOf()
            monsters.add(Monster("Black Gatomon", evos, R.drawable.blackgatomon, 5))

            //Dobermon
            evos = mutableListOf()
            monsters.add(Monster("Dobermon", evos, R.drawable.dobermon, 5))

            //Flymon
            evos = mutableListOf()
            monsters.add(Monster("Flymon", evos, R.drawable.flymon, 5))

            //Youkomon
            evos = mutableListOf()
            monsters.add(Monster("Youkomon", evos, R.drawable.youkomon, 5))

            //Jungle Mojyamon
            evos = mutableListOf()
            monsters.add(Monster("Jungle Mojyamon", evos, R.drawable.junglemojyamon, 5))

            //Gaogamon
            evos = mutableListOf()
            monsters.add(Monster("Gaogamon", evos, R.drawable.gaogamon, 5))

            //Gwappamon
            evos = mutableListOf()
            monsters.add(Monster("Gwappamon", evos, R.drawable.gwappamon, 5))

            //ShellNumemon
            evos = mutableListOf()
            monsters.add(Monster("ShellNumemon", evos, R.drawable.shellnumemon, 5))

            //Starmon
            evos = mutableListOf()
            monsters.add(Monster("Starmon", evos, R.drawable.starmon, 5))

            //Tyrannomon
            evos = mutableListOf()
            monsters.add(Monster("Tyrannomon", evos, R.drawable.tyrannomon, 5))

            //Turuiemon
            evos = mutableListOf()
            monsters.add(Monster("Turuiemon", evos, R.drawable.turuiemon, 5))

            //Centarumon
            evos = mutableListOf()
            monsters.add(Monster("Centarumon", evos, R.drawable.centarumon, 5))

            //Drimogemon
            evos = mutableListOf()
            monsters.add(Monster("Drimogemon", evos, R.drawable.drimogemon, 5))

            //Roachmon
            evos = mutableListOf()
            monsters.add(Monster("Roachmon", evos, R.drawable.roachmon, 5))

            //Snimon
            evos = mutableListOf()
            monsters.add(Monster("Snimon", evos, R.drawable.snimon, 5))

            //Thundermon
            evos = mutableListOf()
            monsters.add(Monster("Thundermon", evos, R.drawable.thundermon, 5))

            //Yanmamon
            evos = mutableListOf()
            monsters.add(Monster("Yanmamon", evos, R.drawable.yanmamon, 5))

            //Boogeymon
            evos = mutableListOf()
            monsters.add(Monster("Boogeymon", evos, R.drawable.boogeymon, 5))

            //Gatomon
            evos = mutableListOf()
            monsters.add(Monster("Gatomon", evos, R.drawable.gatomon, 5))

            //Kougamon
            evos = mutableListOf()
            monsters.add(Monster("Kougamon", evos, R.drawable.kougamon, 5))

            //Raiamon
            evos = mutableListOf()
            monsters.add(Monster("Raiamon", evos, R.drawable.raiamon, 5))

            //Black ExVeemon
            evos = mutableListOf()
            monsters.add(Monster("Black ExVeemon", evos, R.drawable.blackexveemon, 5))

            //Gargomon
            evos = mutableListOf()
            monsters.add(Monster("Gargomon", evos, R.drawable.gargomon, 5))

            //Wendigomon
            evos = mutableListOf()
            monsters.add(Monster("Wendigomon", evos, R.drawable.wendigomon, 5))

            //BomberNanimon
            evos = mutableListOf()
            monsters.add(Monster("BomberNanimon", evos, R.drawable.bombernanimon, 5))

            //FlareRizamon
            evos = mutableListOf()
            monsters.add(Monster("FlareRizamon", evos, R.drawable.flarerizamon, 5))

            //Seasarmon
            evos = mutableListOf()
            monsters.add(Monster("Seasarmon", evos, R.drawable.seasarmon, 5))

            //Strikedramon
            evos = mutableListOf()
            monsters.add(Monster("Strikedramon", evos, R.drawable.strikedramon, 5))

            //Clockmon
            evos = mutableListOf()
            monsters.add(Monster("Clockmon", evos, R.drawable.clockmon, 5))

            //DArcmon
            evos = mutableListOf()
            monsters.add(Monster("DArcmon", evos, R.drawable.darcmon, 5))

            //Doggymon
            evos = mutableListOf()
            monsters.add(Monster("Doggymon", evos, R.drawable.doggymon, 5))

            //Gorillamon
            evos = mutableListOf()
            monsters.add(Monster("Gorillamon", evos, R.drawable.gorillamon, 5))

            //Gladimon
            evos = mutableListOf()
            monsters.add(Monster("Gladimon", evos, R.drawable.gladimon, 5))

            //Ikkakumon
            evos = mutableListOf()
            monsters.add(Monster("Ikkakumon", evos, R.drawable.ikkakumon, 5))

            //Seadramon
            evos = mutableListOf()
            monsters.add(Monster("Seadramon", evos, R.drawable.seadramon, 5))

            //Sealsdramon
            evos = mutableListOf()
            monsters.add(Monster("Sealsdramon", evos, R.drawable.sealsdramon, 5))

            //Tankmon
            evos = mutableListOf()
            monsters.add(Monster("Tankmon", evos, R.drawable.tankmon, 5))

            //Sealsdramon
            evos = mutableListOf()
            monsters.add(Monster("Sealsdramon", evos, R.drawable.sealsdramon, 5))

            //Sealsdramon
            evos = mutableListOf()
            monsters.add(Monster("Sealsdramon", evos, R.drawable.sealsdramon, 5))

            //Sealsdramon
            evos = mutableListOf()
            monsters.add(Monster("Sealsdramon", evos, R.drawable.sealsdramon, 5))

            //Sealsdramon
            evos = mutableListOf()
            monsters.add(Monster("Sealsdramon", evos, R.drawable.sealsdramon, 5))

            //Black Greymon
            evos = mutableListOf()
            monsters.add(Monster("Black Greymon", evos, R.drawable.blackgreymon, 5))

            //Guardromon
            evos = mutableListOf()
            monsters.add(Monster("Guardromon", evos, R.drawable.guardromon, 5))

            //Blimpmon
            evos = mutableListOf()
            monsters.add(Monster("Blimpmon", evos, R.drawable.blimpmon, 5))

            //Icemon
            evos = mutableListOf()
            monsters.add(Monster("Icemon", evos, R.drawable.icemon, 5))

            //Kenkimon
            evos = mutableListOf()
            monsters.add(Monster("Kenkimon", evos, R.drawable.kenkimon, 5))

            //Opossumon
            evos = mutableListOf()
            monsters.add(Monster("Opossumon", evos, R.drawable.opossumon, 5))

            //Tortomon
            evos = mutableListOf()
            monsters.add(Monster("Tortomon", evos, R.drawable.tortomon, 5))

            //Red Veedramon
            evos = mutableListOf()
            monsters.add(Monster("Red Veedramon", evos, R.drawable.redveedramon, 5))

            //Apemon
            evos = mutableListOf()
            monsters.add(Monster("Apemon", evos, R.drawable.apemon, 5))

            //Dark Lizardmon
            evos = mutableListOf()
            monsters.add(Monster("Dark Lizardmon", evos, R.drawable.darklizardmon, 5))

            //Grizzlymon
            evos = mutableListOf()
            monsters.add(Monster("Grizzlymon", evos, R.drawable.grizzmon, 5))

            //Gururumon
            evos = mutableListOf()
            monsters.add(Monster("Gururumon", evos, R.drawable.gururumon, 5))

            //Mikemon
            evos = mutableListOf()
            monsters.add(Monster("Mikemon", evos, R.drawable.mikemon, 5))

            //Nise Drimogemon
            evos = mutableListOf()
            monsters.add(Monster("Nise Drimogemon", evos, R.drawable.nisedrimogemon, 5))

            //Piddomon
            evos = mutableListOf()
            monsters.add(Monster("Piddomon", evos, R.drawable.piddomon, 5))*/

            return monsters
        }

        fun getSpecie(id: Int) : Monster {
            var monster: Monster = Monster()
            for (mon in species()){
                if(mon.id == id){
                    monster = mon
                    break
                }
            }
            return monster
        }

        fun getSpecie(name: String) : Monster {
            var monster: Monster = Monster()
            for (mon in species()){
                if(mon.name == name){
                    monster = mon
                    break
                }
            }
            return monster
        }

        fun evolution(name: String): String? {
            var evoName: String? = null

            var evos = getSpecie(name).evolutions

            if(evos.size > 0){

                var rand: Int = Random().nextInt(evos.size)
                evoName = getSpecie(evos[rand]).name

            }

            return evoName
        }
    }
}