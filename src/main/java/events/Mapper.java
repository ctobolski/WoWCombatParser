package events;

import events.types.CombatEvent;
import events.types.CombatEventType;
import events.parameters.SpellInfo;
import events.types.SpellEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {


    public List<CombatEvent> mapEvents(String fileName) {
        File fileToParse = getFile(fileName);
        BufferedReader reader = getBufferedReader(fileToParse);
        String line;
        List<CombatEvent> eventList = new ArrayList<>();
        try {
            while ((line = reader.readLine()) != null) {
                String[] split = getFields(line);
                String[] dateAndType = getDateAndTypeFields(split[0]);
                CombatEventType type = chooseType(dateAndType[2]);
                List<String> typesICareAbout = new ArrayList<>();
                typesICareAbout.add(CombatEventType.SPELL_AURA_APPLIED.toString());
                typesICareAbout.add(CombatEventType.SPELL_MISSED.toString());
                if (typesICareAbout.contains(type.toString())) {
                    eventList.add(buildEvent(split, dateAndType, type));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return eventList;

    }

    public File getFile(String filename) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filename).getFile());
    }


    private BufferedReader getBufferedReader(File fileToParse) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileToParse);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new BufferedReader(fileReader);
    }

    private String[] getDateAndTypeFields(String s) {
        return s.split("\\s+");
    }

    private String[] getFields(String line) {
        return line.split(",");
    }

    public <T extends CombatEvent> List<T> filterFrom(List<CombatEvent> unfilteredList, Class<T> targetClass) {
        List<T> filteredList = unfilteredList.stream()
                .filter(e ->  targetClass.isInstance(e))
                .map(e-> (T) e)
                .collect(Collectors.toList());
        return filteredList;
    }


    private CombatEvent buildEvent(String[] split, String[] dateAndType, CombatEventType type) {
        return SpellEvent.builder()
                .date(dateAndType[0])
                .time(dateAndType[1])
                .type(type)
                .sourceGUID(split[1])
                .sourceName(stripQuotes(split[2]))
                .sourceFlags(new Flags(split[3], split[4]))
                .destGUID(split[5])
                .destName(stripQuotes(split[6]))
                .destFlags(new Flags(split[7], split[8]))
                .spellInfo(new SpellInfo(split[9], stripQuotes(split[10]), split[11]))
                .build();
    }

    private String stripQuotes(String stringWithQuotes) {
        return stringWithQuotes.substring(1, stringWithQuotes.length() - 1);
    }

    private CombatEventType chooseType(String nameOfEvent) {
        return CombatEventType.valueOf(nameOfEvent);
    }
}

