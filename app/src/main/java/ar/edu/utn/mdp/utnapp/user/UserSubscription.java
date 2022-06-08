package ar.edu.utn.mdp.utnapp.user;

import ar.edu.utn.mdp.utnapp.fetch.models.User;

public class UserSubscription {

    public static boolean containSubjects(User user) {
        return user.getSubscription().size() != 0;
    }

    public static void addSubject(User user, String year, String numCommission, String subject) {
        String subscription = "";
        subscription = subscription.concat(year + "-com" + numCommission + "-" + subject);
        user.addSubscription(subscription);
    }

    public static void deleteSubject(User user, String year, String numCommission, String subject) {
        String subscription = "";
        subscription = subscription.concat(year + "-com" + numCommission + "-" + subject);
        boolean subjectFound = false;
        String indexSubs;
        int i = 0;

        while(i < user.getSubscription().size() && !subjectFound){
            indexSubs = user.getSubscription().get(i);
            if(subscription.equals(indexSubs)){
                user.getSubscription().remove(i);
                subjectFound = true;
            }
            i++;
        }
    }
}
