public class Spell {
    public int id;
    public String name;
    public School school;

    public Spell(String id, String name, String hexValue) {
       this.id  = Integer.parseInt(id);
       this.name = name;
       this.school = School.findByHex(hexValue);
    }
}
