import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final long POPULATION = 10_000_000;

    public static void main(String[] args) {
        System.out.println("\tЗадача 2: Перепись населения\n");
        ArrayList<Person> persons = (ArrayList<Person>) generatePersons();
        System.out.println("\tpersons in list: " + persons.size());
        long minorsCounter = countMinors(persons);
        System.out.println("\tНесовершеннолетних: " + minorsCounter + " ("
                + Math.round((float) minorsCounter / POPULATION * 100) + "%)");
        List<String> conscripts = getConscripts(persons);
        System.out.println("\tПризывников: "
                + conscripts.size() + " ("
                + Math.round((float) conscripts.size() / POPULATION * 100) + "%)");
        List<String> workablePersons = getWorkablePersons(persons);
        System.out.println("\tТрудоспособных: "
                + workablePersons.size() + " ("
                + Math.round((float) workablePersons.size() / POPULATION * 100) + "%)");
    }

    private static Collection<Person> generatePersons() {
        System.out.println("\tНе завис - генерирую население...");
        long time = System.currentTimeMillis();
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < POPULATION; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Время генерации населения: " + time / 1000f + " с");
        return persons;
    }

    private static long countMinors(ArrayList<Person> persons) {
        long time = System.currentTimeMillis();
        long result = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        time = System.currentTimeMillis() - time;
        System.out.println("Время поиска несовершеннолетних: " + time + " мс");
        return result;
    }

    private static List<String> getConscripts(ArrayList<Person> persons) {
        long time = System.currentTimeMillis();
        List<String> conscripts = persons.stream()
                .filter(x -> x.getSex() == Sex.MAN && x.getAge() >= 18 && x.getAge() < 27)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        time = System.currentTimeMillis() - time;
        System.out.println("Время поиска призывников: " + time + " мс");
        return conscripts;
    }

    private static List<String> getWorkablePersons(ArrayList<Person> persons) {
        long time = System.currentTimeMillis();
        List<String> workablePersons = persons.stream()
                .filter(x -> x.getAge() >= 18
                        && (x.getEducation() == Education.HIGHER)
                        && ((x.getSex() == Sex.MAN && x.getAge() < 65)
                        || (x.getSex() == Sex.WOMAN && x.getAge() < 60)
                ))
                .map(Person::getFamily)
                .sorted()
                .collect(Collectors.toList());
        time = System.currentTimeMillis() - time;
        System.out.println("Время поиска трудоспособных: " + time + " мс");
        return workablePersons;
    }
}
