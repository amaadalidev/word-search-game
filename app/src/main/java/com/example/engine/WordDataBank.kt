package com.example.engine

import com.example.model.ThemeCategory
import com.example.model.ThemeIcon
import androidx.compose.ui.graphics.Color

object WordDataBank {

    val vocabularyCategory: ThemeCategory
        get() = ThemeCategory(
            id = "vocabulary",
            title = "Oxford English Dictionary",
            iconType = ThemeIcon.VOCABULARY,
            unlockCost = 0,
            totalLevels = OxfordDictionaryEngine.TOTAL_LEVELS,
            accentColor = Color(0xFFFF8F00)
        )

    val categories: List<ThemeCategory>
        get() = listOf(
            vocabularyCategory,
            ThemeCategory("animals", "Animals", ThemeIcon.ANIMALS, unlockCost = 0, totalLevels = 30, accentColor = Color(0xFFC2185B)),
            ThemeCategory("colors", "Colors", ThemeIcon.COLORS, unlockCost = 0, totalLevels = 30, accentColor = Color(0xFF9C27B0)),
            ThemeCategory("cities", "Cities", ThemeIcon.CITIES, unlockCost = 0, totalLevels = 30, accentColor = Color(0xFFFF9800)),
            ThemeCategory("nature", "Nature", ThemeIcon.NATURE, unlockCost = 0, totalLevels = 30, accentColor = Color(0xFFFFA000)),
            ThemeCategory("house", "House", ThemeIcon.HOUSE, unlockCost = 0, totalLevels = 30, accentColor = Color(0xFF1976D2)),
            ThemeCategory("adjectives", "Adjectives", ThemeIcon.ADJECTIVES, unlockCost = 0, totalLevels = 30, accentColor = Color(0xFF512DA8)),
            ThemeCategory("tv_shows", "TV Shows", ThemeIcon.TV_SHOWS, unlockCost = 50, totalLevels = 30, accentColor = Color(0xFFFF5722)),
            ThemeCategory("countries", "Countries", ThemeIcon.COUNTRIES, unlockCost = 100, totalLevels = 30, accentColor = Color(0xFFD81B60)),
            ThemeCategory("monuments", "Monuments", ThemeIcon.MONUMENTS, unlockCost = 100, totalLevels = 30, accentColor = Color(0xFFE64A19)),
            ThemeCategory("actors", "Actors & Directors", ThemeIcon.ACTORS, unlockCost = 125, totalLevels = 30, accentColor = Color(0xFFFB8C00)),
            ThemeCategory("writers", "Writers", ThemeIcon.WRITERS, unlockCost = 125, totalLevels = 30, accentColor = Color(0xFFC2185B)),
            ThemeCategory("history", "History", ThemeIcon.HISTORY, unlockCost = 150, totalLevels = 30, accentColor = Color(0xFF6A1B9A)),
            ThemeCategory("space", "Space", ThemeIcon.SPACE, unlockCost = 0, totalLevels = 30, accentColor = Color(0xFF00ACC1)),
            ThemeCategory("food", "Food & Fruits", ThemeIcon.FOOD, unlockCost = 50, totalLevels = 30, accentColor = Color(0xFF43A047)),
            ThemeCategory("sports", "Sports", ThemeIcon.SPORTS, unlockCost = 100, totalLevels = 30, accentColor = Color(0xFF0288D1)),
            ThemeCategory("science", "Science", ThemeIcon.SCIENCE, unlockCost = 150, totalLevels = 30, accentColor = Color(0xFF7B1FA2))
        )

    private val wordLists = mapOf(
        "animals" to listOf(
            "LION", "TIGER", "BEAR", "WOLF", "ZEBRA", "GIRAFFE", "MONKEY", "PANDA", "KOALA", "EAGLE",
            "SHARK", "WHALE", "DOLPHIN", "OTTER", "RABBIT", "FOX", "DEER", "HORSE", "CAMEL", "FROG",
            "SNAKE", "HAWK", "OWL", "FALCON", "BEAVER", "JAGUAR", "CHEETAH", "RHINO", "HIPPO", "LEMUR",
            "BADGER", "BISON", "COYOTE", "HYENA", "DINGO", "MOOSE", "WALRUS", "SEAL", "PENGUIN", "LLAMA",
            "GOAT", "SHEEP", "DONKEY", "BADGER", "SWAN", "PARROT", "CRANE", "HERON", "PEACOCK", "TURKEY"
        ),
        "colors" to listOf(
            "RED", "BLUE", "GREEN", "YELLOW", "ORANGE", "PURPLE", "VIOLET", "INDIGO", "CYAN", "MAGENTA",
            "BROWN", "BLACK", "WHITE", "GRAY", "PINK", "TEAL", "AMBER", "GOLD", "SILVER", "BRONZE",
            "MAROON", "OLIVE", "LIME", "NAVY", "AQUA", "CORAL", "SALMON", "PEACH", "BEIGE", "IVORY",
            "TURQUOISE", "LAVENDER", "CRIMSON", "SCARLET", "EMERALD", "SAPPHIRE", "RUBY", "OCHRE", "PLUM", "CHARCOAL"
        ),
        "cities" to listOf(
            "PARIS", "LONDON", "TOKYO", "BERLIN", "ROME", "MADRID", "SYDNEY", "CAIRO", "DUBAI", "SEOUL",
            "BEIJING", "MOSCOW", "ATHENS", "VIENNA", "PRAGUE", "DUBLIN", "OSLO", "HELSINKI", "WARSAW", "LISBON",
            "BANGKOK", "HANOI", "SINGAPORE", "TORONTO", "BOSTON", "CHICAGO", "SEATTLE", "MIAMI", "DENVER", "DALLAS",
            "MUMBAI", "DELHI", "JAKARTA", "MANILA", "KYOTO", "VENICE", "FLORENCE", "MILAN", "MUNICH", "GENEVA"
        ),
        "nature" to listOf(
            "RIVER", "FOREST", "VALLEY", "OCEAN", "DESERT", "JUNGLE", "MOUNTAIN", "GLACIER", "MEADOW", "CANYON",
            "ISLAND", "VOLCANO", "LAGOON", "STREAM", "GARDEN", "SUNRISE", "SUNSET", "RAINBOW", "THUNDER", "BREEZE",
            "FLOWER", "LEAF", "BRANCH", "BLOSSOM", "PINE", "OAK", "WILLOW", "BAMBOO", "CACTUS", "FERN",
            "MOSS", "REEF", "PEBBLE", "CLIFF", "SPRING", "WATERFALL", "GEYSER", "TUNDRA", "PRAIRIE", "OASIS"
        ),
        "house" to listOf(
            "KITCHEN", "BEDROOM", "WINDOW", "GARDEN", "DOOR", "ROOF", "ATTIC", "BASEMENT", "BALCONY", "PORCH",
            "CHAIR", "TABLE", "SOFA", "MIRROR", "CARPET", "CLOSET", "DRAWER", "PILLOW", "BLANKET", "CURTAIN",
            "STOVE", "FRIDGE", "OVEN", "SINK", "SHOWER", "PANTRY", "GARAGE", "CHIMNEY", "FENCE", "STAIRS",
            "LAMP", "SHELF", "DESK", "CLOCK", "RUG", "FAUCET", "HALLWAY", "CELLAR", "TERRACE", "FIREPLACE"
        ),
        "adjectives" to listOf(
            "GRIM", "SWEET", "FROZEN", "SUNNY", "STURDY", "SHY", "BRAVE", "BRIGHT", "CLEVER", "GENTLE",
            "HAPPY", "HONEST", "JOLLY", "KIND", "LIVELY", "NOBLE", "POLITE", "PROUD", "QUICK", "QUIET",
            "SHARP", "SMART", "STRONG", "SWIFT", "WARM", "WILD", "WISE", "ZEALOUS", "CALM", "BOLD",
            "VIBRANT", "EAGER", "LUCKY", "MIGHTY", "PEACEFUL", "RADIANT", "SILENT", "TENDER", "SERENE", "VALIANT"
        ),
        "tv_shows" to listOf(
            "FRIENDS", "LOST", "OFFICE", "CROWN", "DEXTER", "SHERLOCK", "FARGO", "NARCOS", "SUITS", "HOMELAND",
            "VIKINGS", "SEINFELD", "SCRUBS", "ARCHER", "HOUSE", "BONES", "CASTLE", "MONK", "LUTHER", "FLEABAG",
            "OZARK", "SUCCESSION", "CHERNOBYL", "DOWNTOWN", "BOARDWALK", "BILLIONS", "YELLOWSTONE", "SEVERANCE", "ANDOR", "TEDLASSO"
        ),
        "countries" to listOf(
            "FRANCE", "ITALY", "SPAIN", "GERMANY", "BRAZIL", "CANADA", "JAPAN", "MEXICO", "NORWAY", "SWEDEN",
            "GREECE", "EGYPT", "TURKEY", "INDIA", "CHINA", "KOREA", "CHILE", "PERU", "ARGENTINA", "POLAND",
            "AUSTRIA", "BELGIUM", "DENMARK", "FINLAND", "ICELAND", "IRELAND", "PORTUGAL", "SWISS", "MOROCCO", "KENYA",
            "VIETNAM", "THAILAND", "PANAMA", "COLOMBIA", "JORDAN", "NEPAL", "MONACO", "CROATIA", "NEWZEALAND", "AUSTRALIA"
        ),
        "monuments" to listOf(
            "COLOSSEUM", "PYRAMID", "TAJMAHAL", "PARTHENON", "BIGBEN", "EIFFEL", "LOUVRE", "ACROPOLIS", "STONEHENGE", "ALHAMBRA",
            "PETRA", "ANGKOR", "PISA", "COLOSSUS", "SPHINX", "KREMLIN", "ALCATRAZ", "SAGRADA", "VERSAILLES", "CARCASSONNE",
            "POMPEII", "MACHUPICCHU", "CHICHEN", "PANTHEON", "TREVI", "CARNIVAL", "OBELISK", "CITADEL", "BASTILLE", "ACADIA"
        ),
        "actors" to listOf(
            "SPIELBERG", "NOLAN", "SCORSESE", "TARANTINO", "CAMERON", "HITCHCOCK", "KUBRICK", "COPPOLA", "FINCHER", "VILLENEUVE",
            "HANKS", "PITT", "DICAPRIO", "DENIRO", "PACINO", "STREEP", "BLANCHETT", "WINSLET", "JOHANSSON", "LAWRENCE",
            "BALE", "CRUISE", "DAMON", "FREEMAN", "WASHINGTON", "JACKMAN", "DAYLEWIS", "HOFFMAN", "NICHOLSON", "FORD"
        ),
        "writers" to listOf(
            "SHAKESPEARE", "TOLSTOY", "DICKENS", "AUSTEN", "ORWELL", "HEMINGWAY", "TOLKIEN", "DOSTOYEVSKY", "HOMER", "VIRGIL",
            "JOYCE", "KAFKA", "POE", "WILDE", "WOOLF", "TWAIN", "MELVILLE", "FAULKNER", "FITZGERALD", "STEINBECK",
            "VOLTAIRE", "DUMAS", "HUGO", "GOETHE", "DANTE", "CHOMSKY", "CHRISTIE", "ASIMOV", "BRADBURY", "GARCIA"
        ),
        "history" to listOf(
            "EMPIRE", "DYNASTY", "CENTURY", "REVOLUTION", "TREATY", "PHARAOH", "KNIGHT", "CRUSADE", "RENAISSANCE", "REPUBLIC",
            "VIKING", "MONARCH", "CHARTER", "ALLIANCE", "REBELLION", "CONQUEST", "TRIBUNE", "SENATE", "GLADIATOR", "SAMURAI",
            "COLONY", "ARMADA", "DISCOVERY", "VOYAGE", "TRIBUTE", "ARTIFACT", "RELIC", "FORTRESS", "LEGION", "CRADLE"
        ),
        "space" to listOf(
            "EUROPE", "LEO", "MARS", "MOON", "PHOBOS", "RING", "SHIP", "EARTH", "VENUS", "JUPITER",
            "SATURN", "URANUS", "NEPTUNE", "PLUTO", "COMET", "METEOR", "ASTEROID", "ORBIT", "ROCKET", "GALAXY",
            "NEBULA", "PULSAR", "QUASAR", "COSMOS", "SOLAR", "LUNAR", "ECLIPSE", "TITAN", "APOLLO", "VOYAGER"
        ),
        "food" to listOf(
            "APPLE", "BANANA", "ORANGE", "MANGO", "BERRY", "PIZZA", "BURGER", "PASTA", "SALAD", "SOUP",
            "BREAD", "CHEESE", "BUTTER", "WAFFLE", "PANCAKE", "HONEY", "STEAK", "SUSHI", "NOODLE", "TACO",
            "AVOCADO", "TOMATO", "CARROT", "POTATO", "CHERRY", "PEACH", "LEMON", "MELON", "GRAPE", "COCOA"
        ),
        "sports" to listOf(
            "SOCCER", "TENNIS", "HOCKEY", "CRICKET", "RUGBY", "BASEBALL", "BOXING", "SKIING", "SURFING", "SWIMMING",
            "RUNNING", "CYCLING", "SKATING", "ARCHERY", "FENCING", "KARATE", "JUDO", "ROWING", "GOLF", "BOWLING"
        ),
        "science" to listOf(
            "ATOM", "MOLECULE", "ENERGY", "FORCE", "GRAVITY", "MAGNET", "CIRCUIT", "LASER", "OPTICS", "PHOTON",
            "GENOME", "CELL", "NUCLEUS", "FOSSIL", "ECOLOGY", "PLANET", "CLIMATE", "OXYGEN", "CARBON", "HELIUM"
        )
    )

    fun getAllDictionaryWords(): List<String> {
        return wordLists.values.flatten().distinct()
    }

    fun getWordsForTheme(themeId: String): List<String> {
        if (themeId.equals("vocabulary", ignoreCase = true)) {
            return getAllDictionaryWords()
        }
        return wordLists[themeId] ?: wordLists["animals"]!!
    }

    /**
     * Returns a curated list of words for the given theme, difficulty, and level number.
     * Guaranteed to be compatible with grid size and difficulty rules.
     * In vocabulary mode, draws from the 600,000-word Oxford English Dictionary corpus with 5 words per level across 120,000 levels.
     */
    fun getWordsForLevel(themeId: String, difficulty: com.example.model.Difficulty, level: Int): List<String> {
        val isVocab = themeId.equals("vocabulary", ignoreCase = true)
        val maxSize = difficulty.gridSize

        if (isVocab) {
            return OxfordDictionaryEngine.getWordsForLevel(level, maxSize)
        }

        val pool = getWordsForTheme(themeId)

        // Filter words that fit in the grid
        val validWords = pool.filter { it.length in 3..maxSize }.distinct()
        if (validWords.isEmpty()) return listOf("WORD", "SEARCH", "GAME", "PLAY", "EASY")

        // Deterministic slice based on level for other themes
        val startIndex = ((level - 1) * 3) % validWords.size
        val rotated = validWords.drop(startIndex) + validWords.take(startIndex)

        return rotated.take(difficulty.wordCount)
    }
}
