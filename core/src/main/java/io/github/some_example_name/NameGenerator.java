package io.github.some_example_name;

import java.util.Random;

/** Picks a random fantasy name based on gender. */
public class NameGenerator {
    private static final String[] MALE_NAMES = {
        "Aldric", "Bram", "Cedric", "Doran", "Edmund", "Fenn", "Gareth", "Haldor",
        "Ivar", "Joren", "Kael", "Leofric", "Magnus", "Osric", "Rowan", "Tobias"
    };

    private static final String[] FEMALE_NAMES = {
        "Aelwyn", "Brienne", "Cora", "Daria", "Elara", "Freya", "Gwen", "Helena",
        "Isolde", "Jessa", "Kira", "Lyra", "Mira", "Nessa", "Rhea", "Sylvie"
    };

    private static final Random RANDOM = new Random();

    private NameGenerator() {}   // nothing to create: only static methods

    public static String randomName(Gender gender) {
        String[] names = (gender == Gender.MALE) ? MALE_NAMES : FEMALE_NAMES;
        return names[RANDOM.nextInt(names.length)];
    }
}
