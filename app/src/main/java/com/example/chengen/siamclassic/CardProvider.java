package com.example.chengen.siamclassic;

class CardProvider {
    private String name;
    private String image;
    private String category;
    private String lowestPrice;
    private String price;
    private String description;
    private String options;
    private String chooseSpicy;
    private int spicyLevel;
    CardProvider(String name, String image, String category, String lowestPrice, String price, String options,
                 String description, String chooseSpicy, int spicyLevel) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.lowestPrice = lowestPrice;
        this.price = price;
        this.description = description;
        this.options = options;
        this.chooseSpicy = chooseSpicy;
        this.spicyLevel = spicyLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSpicyLevel() {
        return spicyLevel;
    }

    public void setSpicyLevel(int spicyLevel) {
        this.spicyLevel = spicyLevel;
    }

    public String getChooseSpicy() {
        return chooseSpicy;
    }

    public void setChooseSpicy(String chooseSpicy) {
        this.chooseSpicy = chooseSpicy;
    }

}
