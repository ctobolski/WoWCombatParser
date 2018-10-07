package events.parameters;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum School {
    PHYSICAL("0x1"),
    HOLY("0x2"),
    FIRE("0x4"),
    NATURE("0x8"),
    FROST("0x10"),
    SHADOW("0x20"),
    ARCANE("0x40"),
    HOLYSTRIKE("0x3"),
    FLAMESTRIKE("0x5"),
    HOLYFIRE("0x6"),
    STORMSTRIKE("0x9"),
    HOLYSTORM("0xA"),
    FIRESTORM("0xC"),
    FROSTSTRIKE("0x11"),
    HOLYFROST("0x12"),
    FROSTFIRE("0x14"),
    FROSTSTORM("0x18"),
    SHADOWSTRIKE("0x21"),
    SHADOWLIGHT("0x22"),
    SHADOWFLAME("0x24"),
    SHADOWSTORM("0x28"),
    SHADOWFROST("0x30"),
    SPELLSTRIKE("0x41"),
    DIVINE("0x42"),
    SPELLFIRE("0x44"),
    SPELLSTORM("0x48"),
    SPELLFROST("0x50"),
    SPELLSHADOW("0x60"),
    ELEMENTAL("0x1C"),
    CHROMATIC("0x7C"),
    MAGIC("0x7E"),
    CHAOS("0x7F"),
    UNKNOWN("0x0000");


    private final String hexValue;

    School(String hexValue) {
        this.hexValue = hexValue;
    }

    public String getHexValue() {
        return hexValue;
    }

    public static School findByHex(String hexValue) {
        List<School> school =  Arrays.stream(School.values())
                .filter(s-> s.hexValue.equals(hexValue))
                .collect(Collectors.toList());
        return school.size() == 1 ? school.get(0) : UNKNOWN;
    }
}
