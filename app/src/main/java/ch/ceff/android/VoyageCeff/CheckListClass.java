package ch.ceff.android.VoyageCeff;

public class CheckListClass {
    String Titre;
    boolean checked;

    public CheckListClass(String titre, boolean checked) {
        Titre = titre;
        this.checked = checked;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
