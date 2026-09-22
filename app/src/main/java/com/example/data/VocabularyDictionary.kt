package com.example.data

import com.example.engine.WordDataBank

data class DictionaryEntry(
    val word: String,
    val categoryId: String,
    val categoryName: String,
    val partOfSpeech: String,
    val definition: String,
    val example: String
)

object VocabularyDictionary {

    private val definitions = mapOf(
        // Animals
        "LION" to Pair("noun", "A large, powerful carnivorous feline of Africa and northwest India, often known as king of beasts."),
        "TIGER" to Pair("noun", "A large solitary cat with a yellow-brown coat striped with black, native to Asia."),
        "BEAR" to Pair("noun", "A large, heavy mammal with thick fur and a very short tail, found in temperate and polar regions."),
        "WOLF" to Pair("noun", "A wild carnivorous mammal of the dog family, living and hunting in packs."),
        "ZEBRA" to Pair("noun", "An African wild horse with black-and-white stripes and an erect mane."),
        "GIRAFFE" to Pair("noun", "A large African mammal with a very long neck and forelegs, having a coat patterned with brown patches."),
        "MONKEY" to Pair("noun", "A small to medium-sized primate that typically has a long tail, most often living in trees."),
        "PANDA" to Pair("noun", "A large bear-like mammal with characteristic black-and-white markings, native to bamboo forests of China."),
        "KOALA" to Pair("noun", "A bearlike arboreal Australian marsupial with thick gray fur and large round ears."),
        "EAGLE" to Pair("noun", "A large bird of prey with a massive hooked bill, broad wings, and keen vision."),
        "SHARK" to Pair("noun", "A long-bodied predominantly marine fish with a cartilaginous skeleton and prominent dorsal fin."),
        "WHALE" to Pair("noun", "A very large marine mammal with a streamlined hairless body and horizontal tail fin."),
        "DOLPHIN" to Pair("noun", "A small gregarious toothed whale that typically has a beaklike snout and curved dorsal fin."),
        "OTTER" to Pair("noun", "A semiaquatic fish-eating mammal with dense lustrous fur, webbed feet, and a long tail."),
        "RABBIT" to Pair("noun", "A burrowing plant-eating mammal with long ears, long hind legs, and a short tail."),
        "FOX" to Pair("noun", "A carnivorous mammal of the dog family with a pointed muzzle, upright ears, and a bushy tail."),
        "DEER" to Pair("noun", "A hoofed grazing animal with branched bony antlers that are shed annually."),
        "HORSE" to Pair("noun", "A solid-hoofed plant-eating domesticated mammal with a flowing mane and tail."),
        "CAMEL" to Pair("noun", "A large, long-legged mammal with one or two humps on the back, adapted to desert life."),
        "FROG" to Pair("noun", "A tailless amphibian with a short squat body, moist smooth skin, and long hind legs for leaping."),
        "SNAKE" to Pair("noun", "A long limbless reptile that moves with a sinuous serpentine motion."),
        "HAWK" to Pair("noun", "A bird of prey with broad rounded wings and a long tail, hunting by surprise."),
        "OWL" to Pair("noun", "A nocturnal bird of prey with large eyes, a facial disc, and silent flight."),
        "FALCON" to Pair("noun", "A bird of prey with long pointed wings and a swift agile flight."),
        "BEAVER" to Pair("noun", "A large semiaquatic rodent with webbed feet and a broad paddle-shaped tail, known for building dams."),
        "JAGUAR" to Pair("noun", "A large spotted cat of Central and South America, the third-largest feline."),
        "CHEETAH" to Pair("noun", "A large slender spotted cat of Africa, the fastest land mammal on Earth."),
        "RHINO" to Pair("noun", "A massive herbivorous mammal with thick skin and one or two upright horns on its snout."),
        "HIPPO" to Pair("noun", "A large thick-skinned semiaquatic African mammal with massive jaws and large tusks."),
        "LEMUR" to Pair("noun", "An arboreal primate with a pointed snout and typically a long bushy tail, native to Madagascar."),

        // Colors
        "RED" to Pair("adj", "Of a color at the end of the visible spectrum, next to orange, like blood or ripe strawberries."),
        "BLUE" to Pair("adj", "Of a color intermediate between green and violet, as of the clear daytime sky."),
        "GREEN" to Pair("adj", "Of the color between blue and yellow, like grass or emeralds."),
        "YELLOW" to Pair("adj", "Of the color between green and orange, like ripe lemons or the shining sun."),
        "ORANGE" to Pair("adj", "A bright color between red and yellow, like the fruit that bears its name."),
        "PURPLE" to Pair("adj", "A color intermediate between red and blue, traditionally associated with royalty."),
        "VIOLET" to Pair("adj", "A bluish-purple color seen at the highest frequency of visible light."),
        "INDIGO" to Pair("adj", "A rich deep color between blue and violet, historically made from natural dyes."),
        "CYAN" to Pair("adj", "A greenish-blue color, one of the primary subtractive colors in printing."),
        "MAGENTA" to Pair("adj", "A light purplish-red color, one of the four ink colors used in color printing."),
        "GOLD" to Pair("adj", "A deep lustrous yellow color resembling the precious metal gold."),
        "SILVER" to Pair("adj", "A shiny grayish-white color like polished silver metal."),
        "AMBER" to Pair("adj", "A warm honey-yellow color like fossilized tree resin."),
        "TEAL" to Pair("adj", "A dark greenish-blue color named after the Eurasian teal bird."),
        "EMERALD" to Pair("adj", "A bright vibrant green color like the precious gemstone emerald."),
        "SAPPHIRE" to Pair("adj", "A deep, intense blue color characteristic of the gemstone sapphire."),
        "RUBY" to Pair("adj", "A precious deep red color resembling the jewel ruby."),

        // Nature
        "RIVER" to Pair("noun", "A large natural stream of water flowing in a channel to the sea, a lake, or another stream."),
        "FOREST" to Pair("noun", "A large area covered chiefly with trees and undergrowth."),
        "VALLEY" to Pair("noun", "A low area of land between hills or mountains, typically with a river or stream running through it."),
        "OCEAN" to Pair("noun", "A very large expanse of sea, in particular each of the main areas into which the sea is divided."),
        "DESERT" to Pair("noun", "A dry, barren area of land, especially one covered with sand, with little or no rainfall."),
        "MOUNTAIN" to Pair("noun", "A large natural elevation of the earth's surface rising abruptly from the surrounding level."),
        "GLACIER" to Pair("noun", "A slowly moving mass or river of ice formed by the accumulation and compaction of snow on mountains."),
        "MEADOW" to Pair("noun", "A piece of grassland, especially one used for hay or wildflowers."),
        "CANYON" to Pair("noun", "A deep gorge, typically one with a river flowing through it, as found in North America."),
        "WATERFALL" to Pair("noun", "A cascade of water falling from a height, formed when a river flows over a precipice."),
        "RAINBOW" to Pair("noun", "An arch of colors formed in the sky in caused by the refraction and dispersion of the sun's light in rain."),
        "OASIS" to Pair("noun", "A fertile spot in a desert where water is found."),
        "VOLCANO" to Pair("noun", "A mountain or hill having a crater through which lava, rock fragments, and gas are erupted."),

        // Space
        "EARTH" to Pair("noun", "The planet on which we live; the third planet from the sun in our solar system."),
        "MARS" to Pair("noun", "The fourth planet from the sun, known as the Red Planet due to iron oxide on its surface."),
        "JUPITER" to Pair("noun", "The largest planet in the solar system, a gas giant with a Great Red Spot."),
        "SATURN" to Pair("noun", "The sixth planet from the sun, celebrated for its spectacular system of rings."),
        "GALAXY" to Pair("noun", "A system of millions or billions of stars, together with gas and dust, held together by gravitational attraction."),
        "COMET" to Pair("noun", "A celestial object consisting of a nucleus of ice and dust and, when near the sun, a 'tail' of gas."),
        "ORBIT" to Pair("noun", "The curved path of a celestial object or spacecraft around a star, planet, or moon."),
        "ROCKET" to Pair("noun", "A cylindrical projectile that can be propelled to a great height or into space by engine exhaust."),
        "COSMOS" to Pair("noun", "The universe seen as a well-ordered whole."),
        "SOLAR" to Pair("adj", "Relating to or determined by the sun."),
        "LUNAR" to Pair("adj", "Resembling or relating to the moon."),

        // House
        "KITCHEN" to Pair("noun", "A room or area where food is prepared and cooked."),
        "BEDROOM" to Pair("noun", "A room used for sleeping."),
        "WINDOW" to Pair("noun", "An opening in a wall or roof fitted with glass in a frame for admitting light or air."),
        "MIRROR" to Pair("noun", "A reflective surface, now typically of glass coated with a metal amalgam, that reflects a clear image."),
        "PILLOW" to Pair("noun", "A rectangular cloth bag stuffed with soft material, used to support the head when sleeping."),
        "CLOSET" to Pair("noun", "A cupboard or wardrobe for storing clothes or household supplies."),

        // Adjectives
        "BRAVE" to Pair("adj", "Ready to face and endure danger or pain; showing courage."),
        "BRIGHT" to Pair("adj", "Giving out or reflecting a lot of light; shining; intelligent and quick-witted."),
        "CLEVER" to Pair("adj", "Quick to understand, learn, and devise or apply ideas; intelligent."),
        "GENTLE" to Pair("adj", "Having or showing a mild, kind, or tender temperament or character."),
        "HONEST" to Pair("adj", "Free of deceit and untruthfulness; sincere."),
        "JOLLY" to Pair("adj", "Happy and cheerful; full of high spirits."),
        "NOBLE" to Pair("adj", "Belonging to a hereditary class with high social status; having fine personal qualities."),
        "PROUD" to Pair("adj", "Feeling deep pleasure or satisfaction as a result of one's own achievements or qualities."),
        "QUICK" to Pair("adj", "Moving fast or doing something in a short time."),
        "QUIET" to Pair("adj", "Making little or no noise; peaceful."),
        "SERENE" to Pair("adj", "Calm, peaceful, and untroubled; tranquil."),
        "VIBRANT" to Pair("adj", "Full of energy, enthusiasm, and brightness."),

        // Food
        "APPLE" to Pair("noun", "The round fruit of a tree of the rose family, typically with thin red or green skin and crisp flesh."),
        "BANANA" to Pair("noun", "A long curved fruit which grows in clusters and has soft pulpy flesh and yellow skin when ripe."),
        "CHEESE" to Pair("noun", "A food made from the pressed curds of milk, firm and elastic or soft in texture."),
        "HONEY" to Pair("noun", "A sweet, sticky yellowish-brown fluid made by bees and other insects from nectar collected from flowers."),
        "PIZZA" to Pair("noun", "A dish of Italian origin consisting of a flat round base of dough baked with toppings like tomatoes and cheese."),

        // Science
        "ATOM" to Pair("noun", "The basic unit of a chemical element, composed of protons, neutrons, and electrons."),
        "ENERGY" to Pair("noun", "The capacity for doing work, existing in potential, kinetic, thermal, electrical, or chemical forms."),
        "GRAVITY" to Pair("noun", "The force that attracts a body toward the center of the earth or any other physical body having mass."),
        "CIRCUIT" to Pair("noun", "A roughly circular line, route, or complete path around which an electric current flows."),
        "CELL" to Pair("noun", "The smallest structural and functional unit of an organism, typically microscopic."),

        // Sports
        "SOCCER" to Pair("noun", "A game played by two teams of eleven players with a round ball that may not be touched with hands."),
        "TENNIS" to Pair("noun", "A game in which two or four players strike a ball with rackets over a net stretched across a court."),
        "ARCHERY" to Pair("noun", "The sport or skill of shooting with a bow and arrows."),
        "SKIING" to Pair("noun", "The action of traveling over snow on skis, especially as a sport or recreation.")
    )

    /**
     * Cache of all dictionary entries across all categories.
     */
    val allEntries: List<DictionaryEntry> by lazy {
        val list = mutableListOf<DictionaryEntry>()
        val seenWords = mutableSetOf<String>()

        WordDataBank.categories.forEach { theme ->
            val words = WordDataBank.getWordsForTheme(theme.id)
            words.forEach { word ->
                val upper = word.uppercase()
                if (!seenWords.contains(upper)) {
                    seenWords.add(upper)
                    val defPair = definitions[upper]
                    val partOfSpeech = defPair?.first ?: "noun"
                    val definition = defPair?.second
                        ?: "A notable vocabulary word in the ${theme.title} category with educational and linguistic significance."
                    val example = "Find '$upper' in the ${theme.title} word search grid!"

                    list.add(
                        DictionaryEntry(
                            word = upper,
                            categoryId = theme.id,
                            categoryName = theme.title,
                            partOfSpeech = partOfSpeech,
                            definition = definition,
                            example = example
                        )
                    )
                }
            }
        }
        list.sortedBy { it.word }
    }

    fun getWordsByTheme(themeId: String): List<DictionaryEntry> {
        return allEntries.filter { it.categoryId.equals(themeId, ignoreCase = true) }
    }

    fun searchWords(query: String, themeIdFilter: String? = null): List<DictionaryEntry> {
        val trimmed = query.trim()
        val baseList = if (themeIdFilter.isNullOrEmpty() || themeIdFilter == "all") {
            allEntries
        } else {
            allEntries.filter { it.categoryId.equals(themeIdFilter, ignoreCase = true) }
        }

        if (trimmed.isEmpty()) return baseList

        val matches = baseList.filter {
            it.word.contains(trimmed, ignoreCase = true) ||
            it.definition.contains(trimmed, ignoreCase = true) ||
            it.categoryName.contains(trimmed, ignoreCase = true)
        }.toMutableList()

        // If no direct matches or user typed a specific word, synthesize an Oxford English Dictionary entry
        if (trimmed.length >= 2 && matches.none { it.word.equals(trimmed, ignoreCase = true) }) {
            val defPair = definitions[trimmed.uppercase()] ?: com.example.engine.OxfordDictionaryEngine.getDefinitionForWord(trimmed)
            matches.add(
                0,
                DictionaryEntry(
                    word = trimmed.uppercase(),
                    categoryId = "vocabulary",
                    categoryName = "Oxford English Dictionary",
                    partOfSpeech = defPair.first,
                    definition = defPair.second,
                    example = "Found in the Oxford English Dictionary vocabulary game!"
                )
            )
        }

        return matches
    }
}
