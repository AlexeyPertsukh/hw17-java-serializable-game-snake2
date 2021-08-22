package prog_notebook;

import java.util.Comparator;

public class UserComparatorSurname implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        return user1.getSurname().compareTo(user2.getSurname());
    }
}
