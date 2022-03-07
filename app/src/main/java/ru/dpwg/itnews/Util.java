package ru.dpwg.itnews;

public class Util {
    public static int getFlagByLangId(int langId) {
        int flagImageId;
        switch (langId) {
            case 1:
                flagImageId = R.drawable.ic_gb;
                break;
            case 2:
                flagImageId = R.drawable.ic_ru;
                break;
            case 3:
                flagImageId = R.drawable.ic_yt;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return flagImageId;
    }
}
