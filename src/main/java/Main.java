import events.Mapper;
import events.types.CombatEvent;
import events.types.CombatEventType;
import events.types.SpellEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Main {

    public static void main(String[] args) {
        Mapper mapper = new Mapper();
        List<CombatEvent> events = mapper.mapEvents("one_encounter.txt");
        List<SpellEvent> spellEvents = mapper.filterListFrom(events, SpellEvent.class);
        List<SpellEvent> plague_bomb = spellEvents.stream().filter(e -> e.spellInfo.name.equals("Plague Bomb")).collect(Collectors.toList());
        List<SpellEvent> immunities = plague_bomb
                .stream()
                .filter(e -> e.type.equals(CombatEventType.SPELL_MISSED))
                .collect(Collectors.toList());
        List<SpellEvent> applied = plague_bomb.stream()
                .filter(e -> e.type.equals(CombatEventType.SPELL_AURA_APPLIED))
                .collect(Collectors.toList());

        Map<String, List<SpellEvent>> soakedWithImmunityByPlayer = immunities.stream().collect(groupingBy(SpellEvent::getDestName));
        Map<String, List<SpellEvent>> soakedWithoutImmunityByPlayer = applied.stream().collect(groupingBy(SpellEvent::getDestName));
        Map<String, Soaks> super_soakers = new HashMap<>();
        soakedWithImmunityByPlayer.keySet().stream().forEach(name -> {
            int count = soakedWithImmunityByPlayer.get(name).size();
            updateSoaker(super_soakers, name, count, "immune");
        });
        soakedWithoutImmunityByPlayer.keySet().stream().forEach(name -> {
            int count = soakedWithoutImmunityByPlayer.get(name).size();
            updateSoaker(super_soakers, name, count, "reg");
        });
        System.out.println("NAME, IMMUNITY_SOAKS, REGULAR_SOAKS | TOTAL");
        super_soakers.keySet().forEach(k -> {
            Soaks soaks = super_soakers.get(k);
            System.out.println(k + "," + soaks.immunes + "," + soaks.regular + "," + soaks.getTotal());
        });
    }

    private static void updateSoaker(Map<String, Soaks> super_soakers, String name, int count, String type) {
        Soaks soak;
        if (super_soakers.get(name) == null) {
            soak = new Soaks();
            updateSoakCount(type, soak, count);
            super_soakers.put(name, soak);
        } else {
            soak = super_soakers.get(name);
            updateSoakCount(type, soak, count);
        }
    }

    private static void updateSoakCount(String type, Soaks soaks, int count) {
        if (type.equals("immune")) {
            soaks.immunes = count;
        } else {
            soaks.regular= count;
        }
    }

}

class Soaks {
    public int immunes;
    public int regular;

    public int getTotal() {
        return immunes + regular;
    }
}


