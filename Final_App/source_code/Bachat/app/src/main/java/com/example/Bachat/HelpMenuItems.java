package com.example.Bachat;

public class HelpMenuItems {
    private boolean expandable;
    //  int image,imagebutton;


    private String codeName, version,button;// description

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    //String description)
    public HelpMenuItems(String button, String codeName, String version) {
        this.codeName = codeName;
        //   this.image = image;
        this.version = version;
        this.button= button;

        // this.description = description;
        this.expandable = false;

    }
    public String getButton(){
        return button;
    }

    public void setButton(){
        this.button = button;
    }
    //  public int getImage() {
    //      return image;
    // }

    //  public void setImage(int image) {
    //     this.image = image;
    // }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    //  public String getDescription() {
    //     return description;
    // }

    // public void setDescription(String description) {
    //  this.description = description;
    // }

    @Override
    public String toString() {
        return "Versions{" +
                "codeName='" + codeName + '\'' +
                ", version='" + version + '\'' +
                //           ", image='" + image + '\'' +
                ", button='" + button + '\'' +

                //   ", description='" + description + '\'' +
                '}';
    }
}
