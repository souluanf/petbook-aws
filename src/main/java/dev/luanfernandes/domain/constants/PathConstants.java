package dev.luanfernandes.domain.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PathConstants {
    private static final String API_V1 = "/v1";

    private static final String PEOPLE_V1 = API_V1 + "/people";
    public static final String PERSON_CREATE = PEOPLE_V1;
    public static final String PERSON_FIND_ALL = PEOPLE_V1;
    public static final String PERSON_FIND_BY_ID = PEOPLE_V1 + "/{id}";
    public static final String PERSON_UPDATE = PERSON_FIND_BY_ID;
    public static final String PERSON_DELETE = PERSON_FIND_BY_ID;
    public static final String PERSON_UPLOAD_PHOTO = PERSON_FIND_BY_ID + "/photo";
    public static final String PERSON_DOWNLOAD_PHOTO = PERSON_FIND_BY_ID + "/photo/download";

    private static final String ANIMALS_V1 = API_V1 + "/animals";
    public static final String ANIMAL_CREATE = ANIMALS_V1;
    public static final String ANIMAL_FIND_ALL = ANIMALS_V1;
    public static final String ANIMAL_FIND_BY_ID = ANIMALS_V1 + "/{id}";
    public static final String ANIMAL_FIND_BY_PERSON = ANIMALS_V1 + "/person/{personId}";
    public static final String ANIMAL_UPDATE = ANIMAL_FIND_BY_ID;
    public static final String ANIMAL_DELETE = ANIMAL_FIND_BY_ID;
    public static final String ANIMAL_UPLOAD_PHOTO = ANIMAL_FIND_BY_ID + "/photo";
    public static final String ANIMAL_DOWNLOAD_PHOTO = ANIMAL_FIND_BY_ID + "/photo/download";
}
