package ar.edu.utn.mdp.utnapp.fetch.request;

public enum API_URL {
    BASE ("https://utn-api.herokuapp.com/api"),
    ACTIVITY ( BASE.getURL() +"/calendar/activity"),
    HOLIDAY ( BASE.getURL() +"/calendar/holiday"),
    ACTIVITY_NEXT ( BASE.getURL() +"/calendar/activity/next"),
    HOLIDAY_NEXT ( BASE.getURL() +"/calendar/holiday/next"),
    COMMISSION(BASE.getURL() + "/commission"),

    LOGIN (BASE.getURL() + "/auth/login"),
    SIGNUP (BASE.getURL() + "/auth/signup"),
    USER (BASE.getURL() + "/auth/user");

    private final String name;

    API_URL(String name){
        this.name = name;
    }
    public String getURL(){
        return name;
    }
}
/*
router.get('/:com/:year', verifyUser, validator.paramYearCom, validator.validYearCom, CRUD.getCommission)
router.put('/:com/:year', verifyUser, isTeacher, validator.paramYearCom, validator.validYearCom, validator.bodyObject, CRUD.putSubject)
router.post('/:com/:year', verifyUser, isAdmin, validator.paramYearCom, validator.validYearCom, validator.bodyObject, CRUD.postSubject)
router.delete('/:com/:year', verifyUser, isAdmin, validator.paramYearCom, validator.validYearCom, validator.bodyObject, CRUD.deleteSubject)

router.post('/collection/:com/:year', verifyUser, isAdmin, validator.paramYearCom, CRUD.postCommission)
router.delete('/collection/:com/:year', verifyUser, isAdmin, validator.paramYearCom, validator.validYearCom, CRUD.deleteCommission)

*/