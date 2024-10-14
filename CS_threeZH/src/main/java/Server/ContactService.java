package Server;

import  DataBase.*;
import  java.util.List;

public class ContactService {
    private ContactDao contactDAO = new ContactDao();

    public void addContact(Contact contact) {
        contactDAO.addContact(contact);
    }

    public List<Contact> getAllContacts() {
        return ContactDao.getAllContacts();
    }

    public Contact getContactByIndex(int index) {
        return ContactDao.getContactByIndex(index);
    }

    public void updateContact(int index, Contact contact) {
        contactDAO.updateContact(index, contact);
    }

    public void deleteContact(int index) {
        contactDAO.deleteContact(index);
    }
}
