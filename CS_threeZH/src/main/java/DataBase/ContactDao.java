package DataBase;


import java.util.ArrayList;
import java.util.List;
public class ContactDao {
    private static List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public static List<Contact> getAllContacts() {
        return contacts;
    }

    public static Contact getContactByIndex(int index) {
        return contacts.get(index);
    }

    public void updateContact(int index, Contact contact) {
        contacts.set(index, contact);
    }

    public void deleteContact(int index) {
        contacts.remove(index);
    }
}
