package com.boot.accommodation.dto.view;

/**
 * Language dto
 *
 * @author tuanlt
 * @since: 11:28 AM 7/1/2016
 */
public class LanguagesDTO extends BaseDTO {

    // name that will show in spinner
    private String name;
    // values is languages in android, "en", 'vi'
    private String value;
    // national flag.
    private int icon;

    public LanguagesDTO( String nameLanguages, String valuesLanguages,
                         int iconLanguages ) {
        name = nameLanguages;
        value = valuesLanguages;
        icon = iconLanguages;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon( int icon ) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name;
    }

}
