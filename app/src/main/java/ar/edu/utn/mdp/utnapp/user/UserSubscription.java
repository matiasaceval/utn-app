package ar.edu.utn.mdp.utnapp.user;

import ar.edu.utn.mdp.utnapp.fetch.models.User;

public class UserSubscription {

    public static boolean containSubjects(User user) {
        return user.getSubscription().size() != 0;
    }

    public static void addSubject(User user, String year, String numCommission, String subject) {
        final String subscription = year + "-com" + numCommission + "-" + subject;
        user.addSubscription(subscription);
    }

    public static void deleteSubject(User user, String year, String numCommission, String subject) {
        final String subscription = year + "-com" + numCommission + "-" + subject;
        user.getSubscription().removeIf(s -> s.equals(subscription));
    }
}
