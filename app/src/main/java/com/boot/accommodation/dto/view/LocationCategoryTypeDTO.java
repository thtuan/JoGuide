package com.boot.accommodation.dto.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * LocationCategoryTypeDTO to compare
 *
 * @author tuanlt1
 * @since: 11:28 AM 19/09/2016
 */
public enum LocationCategoryTypeDTO {
//    HOTEL(105),
//    RESTAURANT(106),
//    ATTRACTION(107),
//    MUSEUM(108),
//    BAR(109),
//    PARK(121),
//    NATURAL_FEATURE(122),
//    STADIUM(123),
//    ZOO(124),
    CULTURE(137),
    CHURCH(138),
    MUSEUM(139),
    PAGODA(140),
    OTHER(141),
    ENTERTAINMENT(142),
    ADVENTURE(143),
    VISIT(144),
    RELAX(145),
    ANO(146),
    HOTEL(147),
    HOSTEL(148),
    HOMESTAY(149),
    RESTAURANT(150),
    OTHERANO(151);

//    137;"Culture"
//            138;"Church"
//            139;"Museum"
//            140;"Pagoda"
//            141;"Other"
//            142;"Entertainment"
//            143;"Adventure"
//            144;"Visit(Tham quan)"
//            145;"Relax( Giai tri)"
//            146;"An o"
//            147;"Hotel"
//            148;"Hostel"
//            149;"Home stay"
//            150;"Restaurant"
//            151;"Other(Cafe ...)"


    private int RADIUS_PLACES = 500; //Radius get places
    private long value;
    private static Map<Long, LocationCategoryTypeDTO> valueMapping = new HashMap<>();

    static {
        for (LocationCategoryTypeDTO itemType : LocationCategoryTypeDTO.values()) {
            valueMapping.put(itemType.getValue(), itemType);
        }
    }

    LocationCategoryTypeDTO(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public static LocationCategoryTypeDTO parse(int value) {
        return valueMapping.get(value);
    }

    /**
     * Get enum name by value
     * @param value
     * @return
     */
    public static String getNameByValue(long value) {
        LocationCategoryTypeDTO[] values = LocationCategoryTypeDTO.values();
        long enumValue;
        for (LocationCategoryTypeDTO eachValue : values) {
            enumValue = eachValue.getValue();
//            if (ATTRACTION.getValue() == value) {
//                return "nature_feature";
//            }else if(PARK.getValue() == value){
//                return "amusement_park|park";
//            } else
            if (enumValue == value) {
                return eachValue.name().toLowerCase();
            }
        }
        return "";
    }

    /**
     * Validate place by category
     * @param categoryId
     * @param categoryPlaces
     * @return
     */
    public static boolean validatePlaceByCategory(long categoryId, List<String> categoryPlaces) {
        String lodging = "lodging";
        String healthy = "health";
        String store = "store";
//        if (LocationCategoryTypeDTO.ATTRACTION.getValue() == categoryId && !categoryPlaces.contains
//                (LocationCategoryTypeDTO.getNameByValue(categoryId))) {
//            return false;
//        } else if (LocationCategoryTypeDTO.RESTAURANT.getValue() == categoryId && categoryPlaces.contains
//                (lodging)) {
//            return false;
//        } else if (LocationCategoryTypeDTO.BAR.getValue() == categoryId && categoryPlaces.contains(lodging)) {
//            return false;
//        } else if (LocationCategoryTypeDTO.PARK.getValue() == categoryId) {
//            categoryPlaces.remove("park");
//            categoryPlaces.remove("point_of_interest");
//            categoryPlaces.remove("establishment");
//            categoryPlaces.remove("amusement_park");
//            if (categoryPlaces.size() > 0) {
//                return false;
//            }
//        }
        return true;
    }

    /**
     * Get radius
     * @param categoryId
     * @return
     */
    public static long getRadiusByCategory(long categoryId){
        int radius = 5000;
        if(LocationCategoryTypeDTO.RESTAURANT.getValue() == categoryId){
            radius = 5000;
        }
        return radius;
    }
}
