import events.types.CombatEvent;
import events.Mapper;
import events.types.SpellEvent;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Mapper mapper = new Mapper();
        List<CombatEvent> events = mapper.mapEvents("one_encounter.txt");
        List<SpellEvent> spellEvents = mapper.filterFrom(events, SpellEvent.class);
        List<SpellEvent> plague_bomb = spellEvents.stream().filter(e -> e.spellInfo.name.equals("Plague Bomb")).collect(Collectors.toList());
        System.out.println(plague_bomb.size());
    }

}
