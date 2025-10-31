public class Person {

    private String name;
    private String id;

    public Person(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return String.format("姓名: %s, 身份證字號: %s", name, id);
    }

}
